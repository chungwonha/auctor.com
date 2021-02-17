package com.chung.product.mydocument.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import com.chung.product.mydocument.MyDocumentConstants;
import com.chung.product.mydocument.MyDocumentUtil;
import com.chung.product.mydocument.MyTextractCreator;
import com.chung.product.mydocument.documentjobmessage.Body;
import com.chung.product.mydocument.nlp.MyDocumentCategorizer;
import com.chung.product.mydocument.repository.Document2Repository;
import com.chung.product.mydocument.repository.OwnerRepository;
import com.chung.product.mydocument.vo.Document2;
import com.chung.product.mydocument.vo.Owner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Service
@ConfigurationProperties(prefix="mydocument.aws.dynamodb")
public class MyDynamoDbService {
    private static final Logger logger = LoggerFactory.getLogger(MyDynamoDbService.class);

    private String serviceEndPoint;
    private String signingRegion;

    @Autowired
    Document2Repository document2Repository;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    MyDocumentCategorizer myDocumentCategorizer;

    public void addItem(Document2 document) {
        document2Repository.save(document);
    }


    public void addItem(HashMap dataToAdd) {

        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(serviceEndPoint,
                                                                                                     signingRegion);

        AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpoint).build();//AmazonDynamoDBClientBuilder.defaultClient();

//        String table_name = "Document";
        String table_name = "Document2";

        HashMap<String, AttributeValue> item_values = new HashMap<String, AttributeValue>();

        Set keySet = dataToAdd.keySet();

        keySet.stream().forEach(key->item_values.put((String)key,(AttributeValue)dataToAdd.get(key)));

        try {

            ddb.putItem(table_name, item_values);

        } catch (ResourceNotFoundException e) {
            System.err.format("Error: The table \"%s\" can't be found.\n", table_name);
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            System.exit(1);
        } catch (AmazonServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);

        }
    }

    public String getServiceEndPoint() {
        return serviceEndPoint;
    }

    public void setServiceEndPoint(String serviceEndPoint) {
        this.serviceEndPoint = serviceEndPoint;
    }

    public String getSigningRegion() {
        return signingRegion;
    }

    public void setSigningRegion(String signingRegion) {
        this.signingRegion = signingRegion;
    }

    public void updateText(String documentkey, String jobid, String text){

        Optional<Document2> document2 = this.document2Repository.findByJobId(jobid);

        Document2 doc2 = document2.get();
        doc2.setText(text);

        try {

            myDocumentCategorizer.setModelFileName("doc_cat_model2.bin");
            if(doc2.getCategoryCode().equals(MyDocumentConstants.CATEGORY_NOT_DECIDED_YET)) {
                String category = myDocumentCategorizer.categorize(text);
                doc2.setCategoryCode(category);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        doc2.setLastModifiedDate(timestamp.toString());
        this.addItem(doc2.getItem());
    }

    public Map<String, AttributeValue> getItem(String table_name, String documentkey) {
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(serviceEndPoint,
                                                                                                     this.signingRegion);
        AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpoint).build();//AmazonDynamoDBClientBuilder.defaultClient();

        HashMap<String, AttributeValue> key_to_get =
                new HashMap<String, AttributeValue>();

        key_to_get.put("documentkey", new AttributeValue(documentkey));
        key_to_get.put("ownerid", new AttributeValue(MyDocumentUtil.getOwnerIdFromDocumentKey(documentkey)));

        GetItemRequest request = new GetItemRequest()
                .withKey(key_to_get)
                .withTableName(table_name);


        try {
            Map<String, AttributeValue> returned_item = ddb.getItem(request).getItem();
            if (returned_item != null) {
                Set<String> keys = returned_item.keySet();
                for (String key : keys) {
                    logger.debug(returned_item.get(key).toString());
//                    System.out.format("%s: %s\n",
//                            key, returned_item.get(key).toString());
                }
                return returned_item;
            } else {
                System.out.format("No item found with the key %s!\n", documentkey);
            }
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        return new HashMap<>();
    }

    public Optional<Document2> queryItemByDocumentId(String documentId){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(serviceEndPoint,
                this.signingRegion);
        AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpoint).build();//AmazonDynamoDBClientBuilder.defaultClient();

//        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
//                .withRegion(this.signingRegion).build();
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(this.signingRegion).build();
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable("Document2");
        QuerySpec spec = new QuerySpec().withKeyConditionExpression("documentId = :v_id")
                .withValueMap(new ValueMap().withString(":v_id", documentId));

        Document2 document2 = null;
        Optional<Document2> docOptional = null;

        ItemCollection<QueryOutcome> items = table.query(spec);

        Iterator<Item> iterator = items.iterator();
        Item item = iterator.hasNext()?iterator.next():null;
        if (item != null){
            logger.debug(item.toJSONPretty());
            try {
                document2 = objectMapper.readValue(item.toJSON(), Document2.class);
                docOptional = Optional.of(document2);
                return docOptional;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
            docOptional = Optional.empty();
            return docOptional;
    }

//    public void getItems({
//        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(serviceEndPoint,
//                this.signingRegion);
//        AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(endpoint).build();//AmazonDynamoDBClientBuilder.defaultClient();
//
//        ScanRequest scanRequest = new ScanRequest();
//        scanRequest.a
//        ddb.scan()
//    }


    public Optional<Owner> getOwner(String userId){
        return this.ownerRepository.findByUserId(userId);
    }
    public List<Owner> getAllOwners(){return (List<Owner>)this.ownerRepository.findAll();}
    public List<Document2> getAllDocuments(String ownerId){return (this.document2Repository.findByOwnerId(ownerId));}
}
