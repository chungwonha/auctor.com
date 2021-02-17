package com.chung.product.mydocument;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.chung.product.mydocument.service.MyDocumentService;
import com.chung.product.mydocument.service.MyDynamoDbService;
//import com.chung.product.mydocument.vo.Document;
import com.chung.product.mydocument.vo.Document2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.HashMap;

//@SpringBootTest
public class MyDocumentServiceTests {


//    @Autowired
//    MyDocumentService myDocumentService;
//
//    @Autowired
//    MyDynamoDbService myDynamoDbService;

//    @Test
//    public void testPersistDocument(){
//
//        String document = "IMG_4862.JPG";//2017 Disclosure Report Master.pdf";//Weekly Memo 05-27-19.pdf";//M67 Qtr2 Study Guide Solutions.pdf";
//        String bucket = "docbankrepository";
//
//
//        Document doc = new Document();
//        doc.setOwnerId("99");
//        doc.setDocumentKey("99/"+document);
//        doc.setCategoryCode("CREDIT");
//        doc.setDocumentNameInS3(document);
//        doc.setBucket(bucket);
//
//
//
//        myDocumentService.persistDocument(doc);
//
//        HashMap item = (HashMap)myDynamoDbService.getItem("Document",doc.getDocumentKey());
//        Assert.isTrue(item!=null && !item.isEmpty(),"Item Found");
//
//    }

//    @Test
//public void testPersistDocument(){
//
//    String document = "IMG_4862.JPG";//2017 Disclosure Report Master.pdf";//Weekly Memo 05-27-19.pdf";//M67 Qtr2 Study Guide Solutions.pdf";
//    String bucket = "docbankrepository";
//
//
//    Document2 doc = new Document2();
//    doc.setOwnerId("99");
//    doc.setDocumentkey("99/"+document);
//    doc.setCategoryCode("CREDIT");
//    doc.setDocumentNameInS3(document);
//    doc.setBucket(bucket);
//
//    myDocumentService.persistDocument(doc);
//
//    HashMap item = (HashMap)myDynamoDbService.getItem("Document",doc.getDocumentkey());
//    Assert.isTrue(item!=null && !item.isEmpty(),"Item Found");
//
//}

}
