package com.chung.product.mydocument.vo;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
import java.util.HashMap;

@DynamoDBTable(tableName="Document2")
public class Document2 {

    @Id
    private String documentId;
    private String ownerId;
    private String documentkey;
    private String bucket;
    private String categoryCode;
    private String documentNameInS3;
    private String text;
    private String jobId;
    private String concatenatedLineOfCatAndText;
    private String lastModifiedDate;

    private HashMap<String, AttributeValue> item = new HashMap<>();


    @DynamoDBHashKey(attributeName="documentId")
    @DynamoDBAutoGeneratedKey
    public String getDocumentId() {
        return documentId;
    }


    public void setDocumentId(String documentId) {
        item.put("documentId",new AttributeValue(documentId));
        this.documentId = documentId;

    }

    @DynamoDBAttribute
    public String getDocumentkey() {
        return documentkey;
    }

    public void setDocumentkey(String documentkey) {
        item.put("documentkey",new AttributeValue(documentkey));
        this.documentkey = documentkey;
    }

    @DynamoDBAttribute
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        item.put("ownerId",new AttributeValue(ownerId));
        this.ownerId = ownerId;
    }

    @DynamoDBAttribute
    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        item.put("bucket",new AttributeValue(bucket));
        this.bucket = bucket;
    }

    @DynamoDBAttribute
    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        item.put("categoryCode",new AttributeValue(categoryCode));
        this.categoryCode = categoryCode;
    }

    @DynamoDBAttribute
    public String getDocumentNameInS3() {
        return documentNameInS3;
    }

    public void setDocumentNameInS3(String documentNameInS3) {
        item.put("documentNameInS3",new AttributeValue(documentNameInS3));
        this.documentNameInS3 = documentNameInS3;
    }

    @DynamoDBAttribute
    public String getText() {
        return text;
    }

    public void setText(String text) {
        item.put("text",new AttributeValue(text));
        this.text = text;
    }

    @DynamoDBAttribute
    public String getJobId() {
        return jobId;
    }


    public void setJobId(String jobId) {
        item.put("jobId",new AttributeValue(jobId));
        this.jobId = jobId;
    }

    @DynamoDBAttribute
    public HashMap<String, AttributeValue> getItem() {
        return item;
    }

    public void setItem(HashMap<String, AttributeValue> item) {
        this.item = item;
    }

    @DynamoDBAttribute
    public String getConcatenatedLineOfCatAndText() {
        return this.categoryCode+ " "+this.getText();
    }

    public void setConcatenatedLineOfCatAndText(String concatenatedLineOfCatAndText) {
        this.concatenatedLineOfCatAndText = concatenatedLineOfCatAndText;
    }

    @DynamoDBAttribute
    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}