package com.example.java_udemy.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3Service {

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${s3.bucket}")
    private String bucket;

    public void uploadFile(String localFile) {
        try {
            File file = new File(localFile);
            LOG.info("Inicando upload de arquivo");
            amazonS3.putObject(new PutObjectRequest(bucket, "Teste", file));
        } catch (AmazonServiceException e) {
            LOG.info("ERRO AMAZON SERVICE : " + e.getMessage());
            LOG.info("ERRO AMAZON SERVICE  CODE: " + e.getErrorCode());
        } catch (AmazonClientException e) {
            LOG.info("ERRO AMAZON CLIENTE :" + e.getMessage());
        }
    }
}
