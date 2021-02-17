package com.chung.product.mydocument.repository;

import com.chung.product.mydocument.vo.Document2;
import com.chung.product.mydocument.vo.Owner;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface OwnerRepository extends CrudRepository<Owner,String> {

    Owner findByOwnerId(String ownerId);
    Optional<Owner> findByUserId(String userId);
    void deleteByOwnerId(String ownerId);
}
