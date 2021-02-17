package com.chung.product.mydocument;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.chung.product.mydocument.documentjobmessage.Body;
import com.chung.product.mydocument.documentjobmessage.DocumentLocation;
import com.chung.product.mydocument.service.MyDynamoDbService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.objects.annotations.Constructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
@ConfigurationProperties(prefix="mydocument.aws.sqs")
public class MySqsMonitoringForPdf extends Thread{
    private static final Logger logger = LoggerFactory.getLogger(MySqsMonitoringForPdf.class);

    private String queueUrl;
    private String signingRegion;

    private AmazonSQS sqs;

    private boolean monitoringSwtich =false;

    private String jobId;

    @Autowired
    MyTextractCreator myTextractCreator;

    @Autowired
    MyDynamoDbService myDynamoDbService;

    public void sendMessage(String message){

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setMessageBody(message);
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message)
                .withDelaySeconds(5);
        sqs.sendMessage(send_msg_request);

    }

    public void run(){

        logger.info("Execute method asynchronously - "
                + Thread.currentThread().getName());
        try {
            while(this.monitoringSwtich) {
                Thread.sleep(10000);
                logger.info("check if there is any message arrived in SQS");
                this.receiveMessage();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

    }

    public void receiveMessage(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();

        logger.info("# of Messages received in SQS: "+messages.size());
        messages.stream().forEach(eachMsg->
                {   logger.info(eachMsg.getBody());
                    try {
                        Body messageBody = objectMapper.readValue(eachMsg.getBody(), Body.class);
                        logger.info(messageBody.getMessage());
                        com.chung.product.mydocument.documentjobmessage.Message message =objectMapper.readValue(messageBody.getMessage(),
                                                                                                                com.chung.product.mydocument.documentjobmessage.Message.class);
                        logger.info(message.getStatus());
                        logger.info(message.getDocumentLocation().toString());
                        if(MyDocumentConstants.JOB_SUCCESS.equals(message.getStatus())){
                            String docLocation = message.getDocumentLocation().toString();
                            DocumentLocation documentLocation = objectMapper.readValue(docLocation,DocumentLocation.class);
                            logger.info(documentLocation.getS3Buckect()+" : "+documentLocation.getS3ObjectName());

                            MyTextractFromPdf myTextractFromPdf = this.myTextractCreator.getMyTextractFromPdf();
                            String text = myTextractFromPdf.getTextByJobId(message.getJobId());
                            logger.info("Text: "+text);

                            myDynamoDbService.updateText(documentLocation.getS3ObjectName(),message.getJobId(),text);
                            this.deleteMessage(eachMsg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        logger.error(e.getMessage());
                    }
                }
        );

        logger.info("receiveMessage() ends");
    }

    @Bean
    public MyTextractCreator getMyTextractCreator(){
        return new MyTextractCreator();
    }

    public void deleteMessage(Message message){
        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest();
        String receiptHandle = message.getReceiptHandle();
        deleteMessageRequest.setReceiptHandle(receiptHandle);
        deleteMessageRequest.setQueueUrl(this.queueUrl);
        sqs.deleteMessage(deleteMessageRequest);
        logger.info("Message deleted for receiptHandle "+receiptHandle);
    }

    public boolean isMonitoringSwtich() {
        return monitoringSwtich;
    }

    public void setMonitoringSwtich(boolean monitoringSwtich) {
        this.monitoringSwtich = monitoringSwtich;
    }

    public String getQueueUrl() {
        return queueUrl;
    }

    public void setQueueUrl(String queueUrl) {
        this.queueUrl = queueUrl;
    }

    public String getSigningRegion() {
        return signingRegion;
    }

    public void setSigningRegion(String signingRegion) {
        this.signingRegion = signingRegion;
        this.sqs = AmazonSQSClient.builder().withRegion(signingRegion).build();
    }

}
