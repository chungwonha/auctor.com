package com.chung.product.mydocument;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.chung.product.mydocument.config.DynamoDbConfig;
import com.chung.product.mydocument.repository.Document2Repository;
import com.chung.product.mydocument.service.MyDynamoDbService;
import com.chung.product.mydocument.vo.Document2;
//import org.aspectj.lang.annotation.Before;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;

@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("local")
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=dynamodb.us-east-1.amazonaws.com"})
public class Document2RepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(Document2RepositoryTest.class);

    @Autowired
    DynamoDbConfig dynamoDbConfig;

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    Document2Repository repository;

    @Autowired
    MyDynamoDbService myDynamoDbService;

    @Before
    public void setup() throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

//        CreateTableRequest tableRequest = dynamoDBMapper
//                .generateCreateTableRequest(Document2.class);
//        tableRequest.setProvisionedThroughput(
//                new ProvisionedThroughput(1L, 1L));
//        amazonDynamoDB.createTable(tableRequest);

        //...

//        dynamoDBMapper.batchDelete(
//                (List<Document2>)repository.findAll());
    }

    @Test
    public void testDocument2RepositoryMethods() {

        Optional<Document2> doc2Optional = this.myDynamoDbService.queryItemByDocumentId("003e18cd-2386-43c8-9bad-6a8c3ff2a9bb");
        Document2 doc2 = doc2Optional.get();
        logger.debug("doc2.getDocumentId(): "+doc2.getDocumentId());
        logger.debug("doc2.getText(): "+doc2.getText());
        Optional<Document2> doc2OptionalEmpty = this.myDynamoDbService.queryItemByDocumentId("aaaaaa");
        if(!doc2OptionalEmpty.isPresent()){
            logger.debug("No document found by aaaaa");
        }
//        Optional<Document2> doc1=repository.findByDocumentId("003e18cd-2386-43c8-9bad-6a8c3ff2a9bb");
//        if(doc1.isPresent()){
//
//            Document2 doc2 = doc1.get();
//            System.out.println(doc2.getText());
//
//        }else{
//            System.out.println("no doc found for 0a9978dd-23a5-411b-8260-8718f4c5b05a");
//        }

//        Document2 doc = new Document2();
//
//        String documentJpg = "IMG_486212345679.JPG";
//        String bucket = "docbankrepository";
//        doc.setOwnerId("99");
//        doc.setDocumentkey("99/"+documentJpg);
//        doc.setCategoryCode("CREDIT");
//        doc.setDocumentNameInS3(documentJpg);
//        doc.setBucket(bucket);
//        doc.setText("tex text 12345");
//        long randomNum = new Date().getTime();
//        doc.setJobId(Long.toString(randomNum));
//        repository.save(doc);
//
//        List<Document2> result = (List<Document2>) repository.findAll();
//        System.out.println("result.size(): "+result.size());
//
        List allDocs = repository.findByOwnerId("99");
        logger.debug("allDocs.size(): "+allDocs.size());

        List allDocs2 = repository.findByOwnerId("d32bf772-dc6b-4db3-bb71-6c5cd1d5ea25");
        logger.debug("allDocs2.size(): "+allDocs2.size());

        List allDocs3 = repository.findByCategoryCode("CONTRACT");
        logger.debug("allDocs3.size(): "+allDocs3.size());

        List allDocs4 = repository.findByBucket("docbankrepository");
        logger.debug("allDocs4.size(): "+allDocs4.size());
        Optional<Document2> docByJobId = repository.findByJobId("1591498132112");
        if(docByJobId.isPresent()){
            logger.debug(docByJobId.get().getText());
        }


//
//        List allDocsS3 = repository.findByDocumentNameInS3("IMG_486212345679.JPG");
//        System.out.println("allDocsS3.size(): "+allDocsS3.size());
//
//        Optional<Document2> document2 = repository.findByJobId(Long.toString(randomNum));
//        if(document2.isPresent()) {
//            System.out.println(document2 != null ? "document2 is found" + ": DocumentId: " + document2.get().getDocumentId() : "document2 not found");
//            Document2 doc2 = document2.get();
//            doc2.setText("this is changed for testing update method in the Document2Repository");
//            System.out.println(doc2.getDocumentId());
//            System.out.println(doc2.getOwnerId());
//            System.out.println(doc2.getDocumentkey());
//            System.out.println(doc2.getCategoryCode());
//            System.out.println(doc2.getDocumentNameInS3());
//            System.out.println(doc2.getBucket());
//            System.out.println(doc2.getText());
//            System.out.println(doc2.getJobId());
//
//            try {
//                /**
//                 * Seems DynamoDB needs some time to complete the transaction.
//                 * Intentionally give 10 secs so that addItem doesn't fail.
//                 */
//                Thread.sleep(10000);
////                repository.save(doc2);
////                myDynamoDbService.addItem(doc2.getItem());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
////            ObjectMapper objectMapper = new ObjectMapper();
////            Map<String, AttributeValue> docMapper = objectMapper.convertValue(Document2.class, Map.class);
//
//
//
//        }else{
//            System.out.println("docjment2 not found");
//        }




//        assertThat(result.size(), is(greaterThan(0)));
//        assertThat(result.get(0).getCost(), is(equalTo(EXPECTED_COST)));
    }
}
