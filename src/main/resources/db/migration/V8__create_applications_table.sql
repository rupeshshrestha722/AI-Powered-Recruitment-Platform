ALTER TABLE candidates
ADD CONSTRAINT uk_candidates_id_agency
UNIQUE (id, agency_id);

CREATE TABLE applications (
    id UUID PRIMARY KEY,
    agency_id UUID NOT NULL,
    candidate_id UUID NOT NULL,
    job_id UUID NOT NULL,

    status VARCHAR(50) NOT NULL DEFAULT 'SOURCED',

    source VARCHAR(100),
    recruiter_notes TEXT,

    applied_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_applications_agency
        FOREIGN KEY (agency_id)
        REFERENCES agencies(id),

    CONSTRAINT fk_applications_candidate_agency
        FOREIGN KEY (candidate_id, agency_id)
        REFERENCES candidates(id, agency_id),

    CONSTRAINT fk_applications_job_agency
        FOREIGN KEY (job_id, agency_id)
        REFERENCES jobs(id, agency_id),

    CONSTRAINT uk_applications_candidate_job
        UNIQUE (candidate_id, job_id)
);

CREATE INDEX idx_applications_agency_id
    ON applications(agency_id);

CREATE INDEX idx_applications_agency_status
    ON applications(agency_id, status);

CREATE INDEX idx_applications_agency_candidate
    ON applications(agency_id, candidate_id);

CREATE INDEX idx_applications_agency_job
    ON applications(agency_id, job_id);