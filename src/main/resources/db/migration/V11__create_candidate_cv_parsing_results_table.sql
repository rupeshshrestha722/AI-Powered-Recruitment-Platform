ALTER TABLE candidate_documents
ADD CONSTRAINT uk_candidate_documents_id_agency
UNIQUE (id, agency_id);

CREATE TABLE candidate_cv_parsing_results (
    id UUID PRIMARY KEY,
    agency_id UUID NOT NULL,
    candidate_document_id UUID NOT NULL,
    parsed_data JSONB NOT NULL,
    model_name VARCHAR(100),
    parsing_status VARCHAR(30) NOT NULL DEFAULT 'COMPLETED',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_cv_parsing_agency
        FOREIGN KEY (agency_id)
        REFERENCES agencies(id),

    CONSTRAINT fk_cv_parsing_document
        FOREIGN KEY (candidate_document_id, agency_id)
        REFERENCES candidate_documents(id, agency_id),

    CONSTRAINT uk_cv_parsing_document
        UNIQUE (candidate_document_id),

    CONSTRAINT chk_cv_parsing_status
        CHECK (parsing_status IN ('COMPLETED', 'FAILED'))
);

CREATE INDEX idx_cv_parsing_agency
    ON candidate_cv_parsing_results(agency_id);
