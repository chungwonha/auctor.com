package com.chung.product.mydocument.vo;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;

public class Document {
    private String ownerId;
    private String documentKey;
    private String categoryCode;
    private String documentNameInS3;
    private String bucket;
    private String text;
    private String jobId;

    private HashMap<String, AttributeValue> item = new HashMap<>();

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        item.put("ownerid",new AttributeValue(ownerId));
        this.ownerId = ownerId;
    }

    public String getDocumentKey() {
        return documentKey;
    }

    public void setDocumentKey(String documentKey) {
        item.put("documentkey", new AttributeValue(documentKey));
        this.documentKey = documentKey;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        item.put("CategoryCode",new AttributeValue(categoryCode));
        this.categoryCode = categoryCode;
    }

    public String getDocumentNameInS3() {
        return documentNameInS3;
    }

    public void setDocumentNameInS3(String documentNameInS3) {
        item.put("DocumentNameInS3",new AttributeValue(documentNameInS3));
        this.documentNameInS3 = documentNameInS3;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        item.put("Bucket",new AttributeValue(bucket));
        this.bucket = bucket;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        item.put("Text",new AttributeValue(text));
        this.text = text;
    }

    public HashMap<String, AttributeValue> getItem() {
        return item;
    }

    public void setItem(HashMap<String, AttributeValue> item) {
        this.item = item;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        item.put("JobId", new AttributeValue(jobId));
        this.jobId = jobId;
    }
}
