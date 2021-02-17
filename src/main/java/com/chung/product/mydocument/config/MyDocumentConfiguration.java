package com.chung.product.mydocument.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix="com.chung.product.mydocument.config")
public class MyDocumentConfiguration {

    private String s3EndPoint;
    private String dynamoDbEndPoint;
    private String textractEndPoint;
    private String sqsEndPoint;
    private String s3Region;
    private String dynamoDbRegion;
    private String textractRegion;
    private String sqsRegion;
    private String sqsQueueUrl="https://sqs.us-east-1.amazonaws.com/079960855147/TextExtractForPDF";


    public String getS3EndPoint() {
        return s3EndPoint;
    }

    public void setS3EndPoint(String s3EndPoint) {
        this.s3EndPoint = s3EndPoint;
    }

    public String getDynamoDbEndPoint() {
        return dynamoDbEndPoint;
    }

    public void setDynamoDbEndPoint(String dynamoDbEndPoint) {
        this.dynamoDbEndPoint = dynamoDbEndPoint;
    }

    public String getTextractEndPoint() {
        return textractEndPoint;
    }

    public void setTextractEndPoint(String textractEndPoint) {
        this.textractEndPoint = textractEndPoint;
    }

    public String getSqsEndPoint() {
        return sqsEndPoint;
    }

    public void setSqsEndPoint(String sqsEndPoint) {
        this.sqsEndPoint = sqsEndPoint;
    }

    public String getS3Region() {
        return s3Region;
    }

    public void setS3Region(String s3Region) {
        this.s3Region = s3Region;
    }

    public String getDynamoDbRegion() {
        return dynamoDbRegion;
    }

    public void setDynamoDbRegion(String dynamoDbRegion) {
        this.dynamoDbRegion = dynamoDbRegion;
    }

    public String getTextractRegion() {
        return textractRegion;
    }

    public void setTextractRegion(String textractRegion) {
        this.textractRegion = textractRegion;
    }

    public String getSqsRegion() {
        return sqsRegion;
    }

    public void setSqsRegion(String sqsRegion) {
        this.sqsRegion = sqsRegion;
    }

    public String getSqsQueueUrl() {
        return sqsQueueUrl;
    }

    public void setSqsQueueUrl(String sqsQueueUrl) {
        this.sqsQueueUrl = sqsQueueUrl;
    }
}
