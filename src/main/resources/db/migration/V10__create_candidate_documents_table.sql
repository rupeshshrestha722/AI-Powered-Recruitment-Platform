CREATE TABLE candidate_documents (

    id UUID PRIMARY KEY,

    agency_id UUID NOT NULL,
    candidate_id UUID NOT NULL,

    original_filename VARCHAR(255) NOT NULL,
    storage_key VARCHAR(500) NOT NULL UNIQUE,

    content_type VARCHAR(150) NOT NULL,
    file_size BIGINT NOT NULL,

    uploaded_by UUID,

    parsing_status VARCHAR(30) NOT NULL DEFAULT 'PENDING',

    uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_candidate_documents_agency
        FOREIGN KEY (agency_id)
        REFERENCES agencies(id),

    CONSTRAINT fk_candidate_documents_candidate
        FOREIGN KEY (candidate_id, agency_id)
        REFERENCES candidates(id, agency_id),

    CONSTRAINT fk_candidate_documents_user
        FOREIGN KEY (uploaded_by)
        REFERENCES users(id),

    CONSTRAINT chk_candidate_document_file_size
        CHECK (file_size > 0),

    CONSTRAINT chk_candidate_document_parsing_status
        CHECK (parsing_status IN (
            'PENDING',
            'PROCESSING',
            'COMPLETED',
            'FAILED'
        ))
);

CREATE INDEX idx_candidate_documents_agency
    ON candidate_documents(agency_id);

CREATE INDEX idx_candidate_documents_candidate
    ON candidate_documents(candidate_id, agency_id);

CREATE INDEX idx_candidate_documents_uploaded_at
    ON candidate_documents(uploaded_at);