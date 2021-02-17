package com.chung.product.mydocument.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.chung.product.mydocument.MyDocumentUtil;
import com.chung.product.mydocument.vo.MyDocumentInS3;
import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@ConfigurationProperties("mydocument.aws.s3")
public class MyDocumentS3Service {
    private static final Logger logger = LoggerFactory.getLogger(MyDocumentS3Service.class);

    private AmazonS3 s3client;

    private String SUFFIX ="/";

    private String serviceEndpoint;
    private String signingRegion;
    private String systemBucket;
    private String catModelFile;

    /**
     * documentName is
     * @param documentName
     * @param bucketName
     * @return
     */
    public MyDocumentInS3 getDocument(String documentName, String bucketName, String ownerId){
        logger.info("getDocument for "+ownerId+"/"+documentName +" and " + "bucket: "+bucketName);

        this.s3client = this.getS3Client();

        MyDocumentInS3 myDocumentInS3 = new MyDocumentInS3();
        myDocumentInS3.setDocument(documentName);
        myDocumentInS3.setBucket(bucketName);

        // Get the document from S3
        S3Object s3object = this.s3client.getObject(bucketName, ownerId+this.SUFFIX+documentName);
        logger.info("s3object.getKey(): "+s3object.getKey());
        myDocumentInS3.setContentType(s3object.getObjectMetadata().getContentType());

        return myDocumentInS3;
    }

    /**
     * This is a method to upload a file to S3 bucket.
     * A new folder will be created with userId as its name.
     * documentName+SUFFIX+documentName is a key name for putting an object in S3.
     * If there ia a folder with the userId already existing, it won't do anything.
     * @param file
     * @param bucketName
     * @param documentName
     * @param ownerId
     */
    public void upload(File file, String bucketName, String documentName, String ownerId){

        logger.info("******************upload**************************");
        logger.info("this.serviceEndpoint: "+this.serviceEndpoint);
        logger.info("this.signingRegion: "+this.signingRegion);

        this.s3client = this.getS3Client();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        String fileExtension = MyDocumentUtil.getFileExtenstion(documentName);

        if(fileExtension.toLowerCase().equals("jpg")||fileExtension.toLowerCase().equals("jpeg")||fileExtension.toLowerCase().equals("png")){
            logger.info(documentName+" is "+ContentType.IMAGE_JPEG.toString());
            metadata.setContentType(ContentType.IMAGE_JPEG.toString());
        }else if(fileExtension.toLowerCase().equals("pdf")){
            logger.info(documentName+" is application/pdf");
            metadata.setContentType("application/pdf");
        }

        try {
            this.s3client.putObject(bucketName, ownerId+"/"+documentName, file);
        } catch (AmazonServiceException e) {
            logger.error(e.getErrorMessage());
            System.exit(1);
        }
    }

    private AmazonS3 getS3Client(){
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(this.serviceEndpoint,
                        this.signingRegion))
                .build();
    }

    public void retrieveCatModelFile(){

        AmazonS3 s3client = this.getS3Client();
        S3Object s3object = s3client.getObject(this.systemBucket,this.catModelFile);
        S3ObjectInputStream s3ObjectInputStream = s3object.getObjectContent();
        logger.info("Content-Type: " + s3object.getObjectMetadata().getContentType());
        logger.debug("Content: ");
        try {
            FileUtils.copyInputStreamToFile(s3ObjectInputStream, new File(this.catModelFile));
            if(logger.isDebugEnabled()) {
            displayTextInputStream(s3object.getObjectContent());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayTextInputStream(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while ((line = reader.readLine()) != null) {
            logger.debug(line);
        }
        System.out.println();
    }

    public String getServiceEndpoint() {
        return serviceEndpoint;
    }

    public void setServiceEndpoint(String serviceEndpoint) {
        this.serviceEndpoint = serviceEndpoint;
    }

    public String getSigningRegion() {
        return signingRegion;
    }

    public void setSigningRegion(String signingRegion) {
        this.signingRegion = signingRegion;
    }

    public String getSystemBucket() {
        return systemBucket;
    }

    public void setSystemBucket(String systemBucket) {
        this.systemBucket = systemBucket;
    }

    public String getCatModelFile() {
        return catModelFile;
    }

    public void setCatModelFile(String catModelFile) {
        this.catModelFile = catModelFile;
    }
}
