package com.chung.product.mydocument.controller;

import org.apache.log4j.spi.ErrorCode;

public class StorageFileNotFoundException extends Exception {

    private final ErrorCode code;

    public StorageFileNotFoundException(ErrorCode code) {
        super();
        this.code = code;
    }

    public ErrorCode getCode(){
        return this.code;
    }
}
