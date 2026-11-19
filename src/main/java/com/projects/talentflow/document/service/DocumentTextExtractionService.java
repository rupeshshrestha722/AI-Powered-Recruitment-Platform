
package com.projects.talentflow.document.service;

import com.projects.talentflow.candidate.entity.CandidateDocument;
import com.projects.talentflow.candidate.repository.CandidateDocumentRepository;
import com.projects.talentflow.security.TenantContext;
import com.projects.talentflow.storage.service.StorageService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DocumentTextExtractionService {

    private final CandidateDocumentRepository documentRepository;
    private final StorageService storageService;
    private final DocumentTextExtractor textExtractor;

    public DocumentTextExtractionService(
            CandidateDocumentRepository documentRepository,
            StorageService storageService,
            DocumentTextExtractor textExtractor
    ) {
        this.documentRepository = documentRepository;
        this.storageService = storageService;
        this.textExtractor = textExtractor;
    }

    @Transactional(readOnly = true)
    public String extractCandidateDocumentText(UUID documentId) {

        long start = System.currentTimeMillis();

        System.out.println("1. Starting document extraction");

        UUID agencyId = TenantContext.getAgencyId();

        CandidateDocument document = documentRepository
                .findByIdAndAgencyId(documentId, agencyId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Candidate document not found"
                        )
                );

        System.out.println("2. Document retrieved in "
                + (System.currentTimeMillis() - start) + " ms");

        long downloadStart = System.currentTimeMillis();

        byte[] fileContent =
                storageService.downloadFile(document.getStorageKey());

        System.out.println("3. File downloaded in "
                + (System.currentTimeMillis() - downloadStart) + " ms");

        long extractionStart = System.currentTimeMillis();

        String extractedText = textExtractor.extractText(fileContent);

        System.out.println("4. Tika extraction completed in "
                + (System.currentTimeMillis() - extractionStart) + " ms");

        System.out.println("5. Extracted characters: " + extractedText.length());

        return extractedText;
    }
}
