package com.chung.product.mydocument.repository;

import com.chung.product.mydocument.vo.Document2;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.Optional;

@EnableScan
public interface Document2Repository extends CrudRepository<Document2,String> {

    Optional<Document2> findByDocumentId(String documentId);
    List<Document2> findByOwnerId(String ownerId);
    List<Document2> findByDocumentNameInS3(String documentNameInS3);
    Optional<Document2> findByJobId(String jobId);
    List<Document2> findByCategoryCode(String categoryCode);
    List<Document2> findByBucket(String bucket);
}
