package com.chung.product.mydocument;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyTextactFromImageTest {

    private static final Logger logger = LoggerFactory.getLogger(MyTextactFromImageTest.class);

    @Autowired
    MyTextractCreator myTextractCreator;

    @Test
    public void testGetText(){
        String document = "IMG_4862.JPG";//2017 Disclosure Report Master.pdf";//Weekly Memo 05-27-19.pdf";//M67 Qtr2 Study Guide Solutions.pdf";
        String bucket = "docbankrepository";

        MyTextractFromImage myTextractFromImage = this.myTextractCreator.getMyTextractFromImage();
        String s = myTextractFromImage.getText("99/"+document,bucket);
        logger.info("testGetText getText result: "+s);
    }
}
