
package com.projects.talentflow.candidate.service.impl;

import com.projects.talentflow.candidate.dto.CandidateDocumentResponse;
import com.projects.talentflow.candidate.entity.Candidate;
import com.projects.talentflow.candidate.entity.CandidateDocument;
import com.projects.talentflow.candidate.repository.CandidateDocumentRepository;
import com.projects.talentflow.candidate.repository.CandidateRepository;
import com.projects.talentflow.candidate.service.CandidateDocumentService;
import com.projects.talentflow.security.TenantContext;
import com.projects.talentflow.storage.service.StorageService;
import com.projects.talentflow.user.entity.User;
import com.projects.talentflow.user.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class CandidateDocumentServiceImpl implements CandidateDocumentService {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    private static final Set<String> ALLOWED_EXTENSIONS =
            Set.of("pdf", "doc", "docx");

    private final CandidateDocumentRepository documentRepository;
    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;
    private final StorageService storageService;

    public CandidateDocumentServiceImpl(
            CandidateDocumentRepository documentRepository,
            CandidateRepository candidateRepository,
            UserRepository userRepository,
            StorageService storageService
    ) {
        this.documentRepository = documentRepository;
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
        this.storageService = storageService;
    }

    @Override
    public CandidateDocumentResponse uploadCandidateDocument(
            UUID candidateId,
            MultipartFile file
    ) {
        UUID agencyId = TenantContext.getAgencyId();

        Candidate candidate = candidateRepository
                .findByIdAndAgencyId(candidateId, agencyId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Candidate not found"
                        )
                );

        validateFile(file);

        JwtAuthenticationToken authentication =
                (JwtAuthenticationToken) SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        UUID userId = UUID.fromString(
                authentication.getToken().getSubject()
        );

        User uploadedBy = userRepository
                .findByIdAndAgencyId(userId, agencyId)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Authenticated agency user not found"
                        )
                );

        String originalFilename = getSafeFilename(
                file.getOriginalFilename()
        );

        String storageFileName =
                agencyId + "/" + candidateId + "/" + originalFilename;

        String contentType = getContentType(originalFilename);

        String storageKey;

        try {
            storageKey = storageService.uploadFile(
                    storageFileName,
                    file.getBytes(),
                    contentType
            );
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Unable to read uploaded CV file",
                    ex
            );
        }

        CandidateDocument document = new CandidateDocument();

        document.setAgency(candidate.getAgency());
        document.setCandidate(candidate);
        document.setOriginalFilename(originalFilename);
        document.setStorageKey(storageKey);
        document.setContentType(contentType);
        document.setFileSize(file.getSize());
        document.setUploadedBy(uploadedBy);
        document.setParsingStatus("PENDING");

        CandidateDocument savedDocument =
                documentRepository.saveAndFlush(document);

        return mapToResponse(savedDocument);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateDocumentResponse> getCandidateDocuments(
            UUID candidateId
    ) {
        UUID agencyId = TenantContext.getAgencyId();

        candidateRepository.findByIdAndAgencyId(candidateId, agencyId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Candidate not found")
                );

        return documentRepository
                .findAllByCandidateIdAndAgencyIdOrderByUploadedAtDesc(
                        candidateId,
                        agencyId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateDocumentResponse getDocumentById(UUID documentId) {

        UUID agencyId = TenantContext.getAgencyId();

        CandidateDocument document = documentRepository
                .findByIdAndAgencyId(documentId, agencyId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Document not found")
                );

        return mapToResponse(document);
    }

    @Override
    public void deleteDocument(UUID documentId) {

        UUID agencyId = TenantContext.getAgencyId();

        CandidateDocument document = documentRepository
                .findByIdAndAgencyId(documentId, agencyId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Document not found")
                );

        storageService.deleteFile(document.getStorageKey());

        documentRepository.delete(document);
    }

    private void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please select a CV file"
            );
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    "CV file size must not exceed 10 MB"
            );
        }

        String filename = getSafeFilename(file.getOriginalFilename());

        int extensionIndex = filename.lastIndexOf('.');

        if (extensionIndex < 0) {
            throw new IllegalArgumentException(
                    "Only PDF, DOC and DOCX files are allowed"
            );
        }

        String extension = filename
                .substring(extensionIndex + 1)
                .toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException(
                    "Only PDF, DOC and DOCX files are allowed"
            );
        }
    }

    private String getSafeFilename(String filename) {

        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException(
                    "Original filename is required"
            );
        }

        String safeFilename = filename
                .replace("\\", "/");

        safeFilename = safeFilename.substring(
                safeFilename.lastIndexOf('/') + 1
        );

        if (safeFilename.isBlank()
                || safeFilename.equals(".")
                || safeFilename.equals("..")) {
            throw new IllegalArgumentException(
                    "Invalid filename"
            );
        }

        return safeFilename;
    }

    private String getContentType(String filename) {

        String extension = filename.substring(
                filename.lastIndexOf('.') + 1
        ).toLowerCase();

        return switch (extension) {
            case "pdf" -> "application/pdf";
            case "doc" -> "application/msword";
            case "docx" ->
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            default -> throw new IllegalArgumentException(
                    "Unsupported document format"
            );
        };
    }

    private CandidateDocumentResponse mapToResponse(
            CandidateDocument document
    ) {
        User user = document.getUploadedBy();

        String uploadedByName = user == null
                ? null
                : user.getFirstName() + " " + user.getLastName();

        return new CandidateDocumentResponse(
                document.getId(),
                document.getAgency().getId(),
                document.getCandidate().getId(),
                document.getOriginalFilename(),
                document.getContentType(),
                document.getFileSize(),
                document.getParsingStatus(),
                user == null ? null : user.getId(),
                uploadedByName,
                document.getUploadedAt()
        );
    }
}
