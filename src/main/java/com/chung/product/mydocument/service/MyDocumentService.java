package com.chung.product.mydocument.service;

import com.chung.product.mydocument.*;
import com.chung.product.mydocument.nlp.MyDocumentCategorizer;
import com.chung.product.mydocument.vo.Document;
import com.chung.product.mydocument.vo.Document2;
import com.chung.product.mydocument.vo.MyDocumentInS3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@Service
public class MyDocumentService {
    private static final Logger logger = LoggerFactory.getLogger(MyDocumentService.class);

    @Autowired
    MyDynamoDbService myDynamoDbService;

    @Autowired
    MyDocumentS3Service myDocumentS3Service;

    @Autowired
    MyTextractCreator myTextractCreator;

    @Autowired
    MyDocumentCategorizer myDocumentCategorizer;

    /**
     * This is to persist metadata for a document uploaded.
     * If the document is an image file, texts in the file will be saved with other data.
     * If the document is PDF, a job ID will be returned for Textract to extract text asynchronously.
     * @param document
     */
    public Document2 persistDocument(Document2 document) {
        logger.info("-------------------persistDocument begins-----------------");
//        MyDocumentS3Service myDocumentS3Service = new MyDocumentS3Service();
        MyDocumentInS3 myDocumentInS3 = myDocumentS3Service.getDocument(document.getDocumentNameInS3(),
                                                                        document.getBucket(),
                                                                        document.getOwnerId());
        myDocumentInS3.setDocument(document.getDocumentkey());
        myDocumentInS3.setBucket(document.getBucket());

        MyTextract myTextract = myTextractCreator.getMyTextract(myDocumentInS3);

        /**
         * TODO: How to make the model file paramerarized?
         */
        myDocumentCategorizer.setModelFileName("doc_cat_model2.bin");
        logger.info("document.getCategoryCode(): "+document.getCategoryCode());
        String category = document.getCategoryCode();

        if (myTextract.getTextType().equals(MyDocumentConstants.IMAGE)){
            String text = myTextract.getText(document.getDocumentkey(),document.getBucket());
            document.setText(text);
            try {
                if(category.isEmpty()) {
                    category = myDocumentCategorizer.categorize(text);
                    document.setCategoryCode(category);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            document.setLastModifiedDate(timestamp.toString());

            myDynamoDbService.addItem(document);
        }else if(myTextract.getTextType().equals(MyDocumentConstants.PDF)){
            String jobId = myTextract.getText(document.getDocumentkey(),document.getBucket());
            logger.info("after myTextract.getText for PDF");
            if (jobId != null) {
               logger.info("jobId in persistDocument method: " + jobId);
               document.setJobId(jobId);
                if(category.isEmpty()) {
                    document.setCategoryCode(MyDocumentConstants.CATEGORY_NOT_DECIDED_YET);
                }

               myDynamoDbService.addItem(document);

            } else {
                logger.info("jobId is null");
            }
        }
        logger.info("-------------------persistDocument ends-----------------");
        return document;
    }

    public List<Document2> getAllDocuments(String ownerId){
        return this.myDynamoDbService.getAllDocuments(ownerId);
    }

    public Document2 getDocument(String documentId){
        Optional<Document2> doc = this.myDynamoDbService.queryItemByDocumentId(documentId);
        if(doc.isPresent()){
            return doc.get();
        }else{
            /**
             * TODO: better to throw an exception
             */
            return new Document2();
        }
    }

}
