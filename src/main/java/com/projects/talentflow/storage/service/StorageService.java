package com.projects.talentflow.storage.service;

public interface StorageService {

    void initializeBucket();

    String uploadFile(
            String fileName,
            byte[] fileContent,
            String contentType
    );

    void deleteFile(String fileKey);

    byte[] downloadFile(String fileKey);

}