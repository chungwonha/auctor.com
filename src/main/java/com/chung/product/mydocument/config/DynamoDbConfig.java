package com.chung.product.mydocument.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.chung.product.mydocument.repository")
public class DynamoDbConfig {

    @Value("${mydocument.aws.dynamodb.serviceEndPoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB
                = new AmazonDynamoDBClient(amazonAWSCredentials());

        if (!StringUtils.isEmpty(amazonDynamoDBEndpoint)) {
            amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
        }

        return amazonDynamoDB;
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        if(this.amazonAWSAccessKey==null||this.amazonAWSSecretKey==null) {
            AWSCredentialsProvider baseCredentials = new ProfileCredentialsProvider("default");
            this.amazonAWSAccessKey = baseCredentials.getCredentials().getAWSAccessKeyId();
            this.amazonAWSSecretKey = baseCredentials.getCredentials().getAWSSecretKey();
        }

        return new BasicAWSCredentials(
                amazonAWSAccessKey, amazonAWSSecretKey);
    }
}
