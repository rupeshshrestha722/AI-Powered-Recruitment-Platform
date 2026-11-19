package com.projects.talentflow.storage.service.impl;

import com.projects.talentflow.storage.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {

    private final S3Client s3Client;

    @Value("${app.storage.bucket}")
    private String bucketName;

    public StorageServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void initializeBucket() {

        boolean bucketExists = s3Client.listBuckets()
                .buckets()
                .stream()
                .anyMatch(bucket -> bucket.name().equals(bucketName));

        if (!bucketExists) {
            s3Client.createBucket(
                    CreateBucketRequest.builder()
                            .bucket(bucketName)
                            .build()
            );
        }
    }

    @Override
    public String uploadFile(
            String fileName,
            byte[] fileContent,
            String contentType
    ) {

        String fileKey = UUID.randomUUID() + "-" + fileName;

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .contentType(contentType)
                .build();

        s3Client.putObject(
                request,
                RequestBody.fromBytes(fileContent)
        );

        return fileKey;
    }

    @Override
    public void deleteFile(String fileKey) {

        s3Client.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileKey)
                        .build()
        );
    }

    @Override
    public byte[] downloadFile(String fileKey) {

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build();

        ResponseBytes<GetObjectResponse> response =
                s3Client.getObjectAsBytes(request);

        return response.asByteArray();
    }
}