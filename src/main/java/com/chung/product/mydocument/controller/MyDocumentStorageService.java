package com.chung.product.mydocument.controller;

import com.chung.product.mydocument.service.MyDocumentS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.apache.commons.io.IOUtils;

@Component
public class MyDocumentStorageService implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(MyDocumentStorageService.class);
    private String bucket = "docbankrepository";

    @Autowired
    MyDocumentS3Service myDocumentS3Service;

    @Override
    public void init() {

    }

    @Override
    public void store(MultipartFile file, String ownerId) {
        logger.info("store");
        logger.info("file name: "+file.getName());
        logger.info("original file name: "+file.getOriginalFilename());

        try {
            InputStream in = file.getInputStream();
            final File tempFile = File.createTempFile("aaaaa", "bbbbbb");
            tempFile.deleteOnExit();
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                IOUtils.copy(in, out);
            }
            myDocumentS3Service.upload(tempFile,this.bucket,file.getOriginalFilename(),ownerId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info(file.getName()+ " stored ");
    }

    @Override
    public Stream<Path> loadAll() {
        return Stream.of(Paths.get("first.txt"), Paths.get("second.txt"));
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
