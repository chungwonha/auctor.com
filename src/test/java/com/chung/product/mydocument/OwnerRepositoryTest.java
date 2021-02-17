package com.chung.product.mydocument;

import com.chung.product.mydocument.repository.OwnerRepository;
import com.chung.product.mydocument.vo.Owner;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Optional;

@SpringBootTest
//@WebAppConfiguration
//@ActiveProfiles("local")
//@TestPropertySource(properties = {
//        "amazon.dynamodb.endpoint=dynamodb.us-east-1.amazonaws.com"})
public class OwnerRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(MyDocumentInS3Test.class);

    String[] owenrs = {"chungha-test@yahoo.com"};//"abc@yahoo.com","cde@yahoo.com","efg@yahoo.com","hij@yahoo.com","opq@yahoo.com"};
    @Autowired
    OwnerRepository ownerRepository;

    @Test
    public void testCreateOwner(){
        logger.info("testCreateOwner begins");
        for(String o : owenrs) {
            Owner owner = new Owner();
            owner.setUserId(o);
            owner.setFirstName("Chung");
            owner.setLastName("Ha");
            this.ownerRepository.save(owner);

            Optional<Owner> chungOwner = this.ownerRepository.findByUserId(o);
            if (chungOwner.isPresent()) {
                logger.info(o+" ownerId: " + chungOwner.get().getOwnerId());
            } else {
                logger.info("nothing found by "+o);
            }
        }

    }

    @Test
    public void testDeleteOwner(){
        Optional<Owner> chungOwner = this.ownerRepository.findByUserId("chungha-test@yahoo.com");
        if(chungOwner.isPresent()) {
            logger.info("owner found by chungha-test@yahoo.com");
            this.ownerRepository.deleteByOwnerId(chungOwner.get().getOwnerId());
            logger.info("owner by userId chungha-test@yahoo.com is deleted");
        }else{
            logger.info("no owner found by chungha-test@yahoo.com");
        }
    }

}
