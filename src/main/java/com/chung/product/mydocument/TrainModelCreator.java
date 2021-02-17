package com.chung.product.mydocument;

import com.chung.product.mydocument.service.MyDynamoDbService;
import com.chung.product.mydocument.vo.Document2;
import com.chung.product.mydocument.vo.Owner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TrainModelCreator extends Thread{
    private static final Logger logger = LoggerFactory.getLogger(TrainModelCreator.class);

    private boolean trainModelCreatorSwitch=false;

    @Autowired
    MyDynamoDbService myDynamoDbService;

    public void run(){
        while(this.isTrainModelCreatorSwitch()) {
            try {
                Thread.sleep(10000);
                logger.info("Execute method asynchronously for TrainModelCreator- "
                        + Thread.currentThread().getName());
                logger.info("call createModelAndSave()");
                this.createModelAndSave();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void createModelAndSave(){
        logger.info("createModelAndSave");
        List<Owner> ownerList = this.myDynamoDbService.getAllOwners();

        ownerList.stream().forEach(owner -> {
            logger.info("owner.getOwnerId(): "+owner.getOwnerId());
            List<Document2> documentList = this.myDynamoDbService.getAllDocuments(owner.getOwnerId());
            try {
                writeTrainFile(documentList,owner.getOwnerId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public boolean isTrainModelCreatorSwitch() {
        return trainModelCreatorSwitch;
    }

    public void setTrainModelCreatorSwitch(boolean trainModelCreatorSwitch) {
        this.trainModelCreatorSwitch = trainModelCreatorSwitch;
    }

    public void writeTrainFile(List<Document2> documentList,String ownerId) throws IOException {
        Function<Document2, String> document2ConcatenateCategortyAndTextFunction = d->d.getCategoryCode()+" "+d.getText();

        FileWriter myWriter = new FileWriter(ownerId+".train");

        List<String> lines = documentList.stream().map(Document2::getConcatenatedLineOfCatAndText).collect(Collectors.toList());
//        this.printLines(lines);
//        String allLines ="";
//        Files.write(Paths.get(ownerId+".train"), line.getBytes());
        lines.stream().forEach(line->{
            try {
                logger.info(line);
                line=line+"\n";
                myWriter.write(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        myWriter.close();
//        Files.write(Paths.get(ownerId+".train"), line.getBytes());
    }

    private void printLines(List<String> lines){
        lines.stream().forEach(System.out::println);
    }
}
