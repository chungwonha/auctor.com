package com.chung.product.mydocument;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TrainModelCreatorTest {

    @Autowired
    TrainModelCreator trainModelCreator;

    @Test
    public void testCreateModelAndSave(){
        trainModelCreator.createModelAndSave();
    }
}
