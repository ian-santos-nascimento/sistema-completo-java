package com.example.java_udemy.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@Service
public class S3Service {

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${s3.bucket}")
    private String bucket;

    public URI uploadFile(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            InputStream in = multipartFile.getInputStream();
            String content = multipartFile.getContentType();
            return uploadFile(in, fileName, content);
        } catch (IOException e) {
            throw new RuntimeException("ERRO DE IO" + e.getMessage());
        }

    }

    public URI uploadFile(InputStream in, String fileName, String content) {
        try {
            ObjectMetadata obj = new ObjectMetadata();
            obj.setContentType(content);
            LOG.info("Inicando upload de arquivo");
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, in, obj));
            return amazonS3.getUrl(bucket, fileName).toURI();
        } catch (java.net.URISyntaxException e) {
            throw new RuntimeException("ERRO AO TRANSFORMAR URL PARA URI");
        }
    }
}
