package com.chung.product.mydocument.documentjobmessage;

import com.fasterxml.jackson.annotation.JsonSetter;

public class DocumentLocation {
    private String s3ObjectName;
    private String s3Buckect;


    public String getS3ObjectName() {
        return s3ObjectName;
    }

    @JsonSetter("S3ObjectName")
    public void setS3ObjectName(String s3ObjectName) {
        this.s3ObjectName = s3ObjectName;
    }

    public String getS3Buckect() {
        return s3Buckect;
    }

    @JsonSetter("S3Bucket")
    public void setS3Buckect(String s3Buckect) {
        this.s3Buckect = s3Buckect;
    }
}
