package com.chung.product.mydocument;

public class MyTextractFromEmpty implements MyTextract{

    @Override
    public String getText(String document, String bucket) {
        return null;
    }

    @Override
    public String getTextType() {
        return MyDocumentConstants.NONE;
    }
}
