
package com.projects.talentflow.ai.service;

import com.projects.talentflow.ai.dto.CvParsingResponse;
import com.projects.talentflow.candidate.entity.CandidateDocument;
import com.projects.talentflow.candidate.repository.CandidateDocumentRepository;
import com.projects.talentflow.document.service.DocumentTextExtractionService;

import com.projects.talentflow.security.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CvDocumentParsingService {

    private final CandidateDocumentRepository documentRepository;
    private final DocumentTextExtractionService extractionService;
    private final CvParsingService cvParsingService;
    private final CandidateCvParsingResultService resultService;

    public CvDocumentParsingService(
            CandidateDocumentRepository documentRepository,
            DocumentTextExtractionService extractionService,
            CvParsingService cvParsingService,
            CandidateCvParsingResultService resultService
    ) {
        this.documentRepository = documentRepository;
        this.extractionService = extractionService;
        this.cvParsingService = cvParsingService;
        this.resultService = resultService;
    }

    public CvParsingResponse parseDocument(UUID documentId) {

        UUID agencyId = TenantContext.getAgencyId();

        CandidateDocument document = documentRepository
                .findByIdAndAgencyId(documentId, agencyId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Candidate document not found"
                        )
                );

        try {
            document.setParsingStatus("PROCESSING");
            documentRepository.save(document);

            // Step 1: Extract CV text
            long extractionStart = System.currentTimeMillis();

            String cvText = extractionService
                    .extractCandidateDocumentText(documentId);

            System.out.println("CV extraction time: "
                    + (System.currentTimeMillis() - extractionStart) + " ms");

            // Step 2: Parse CV using Ollama
            long aiStart = System.currentTimeMillis();

            System.out.println("Starting Ollama parsing...");

            CvParsingResponse response =
                    cvParsingService.parseCv(cvText);

            System.out.println("Ollama parsing time: "
                    + (System.currentTimeMillis() - aiStart) + " ms");

            // Step 3: Save AI result
            long saveStart = System.currentTimeMillis();

            resultService.saveParsingResult(documentId, response);

            System.out.println("Database save time: "
                    + (System.currentTimeMillis() - saveStart) + " ms");

            document.setParsingStatus("COMPLETED");
            documentRepository.save(document);

            return response;

        } catch (RuntimeException exception) {

            exception.printStackTrace();

            document.setParsingStatus("FAILED");
            documentRepository.save(document);

            throw exception;
        }
    }
}
