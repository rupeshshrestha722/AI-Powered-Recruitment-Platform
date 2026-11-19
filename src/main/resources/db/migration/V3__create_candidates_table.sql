CREATE TABLE candidates (
    id UUID PRIMARY KEY,
    agency_id UUID NOT NULL,

    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(30),

    location VARCHAR(150),
    current_designation VARCHAR(150),
    current_company VARCHAR(150),

    total_experience NUMERIC(4,1),
    notice_period VARCHAR(100),

    linkedin_url VARCHAR(500),
    summary TEXT,

    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_candidates_agency
        FOREIGN KEY (agency_id) REFERENCES agencies(id)
);

CREATE INDEX idx_candidates_agency_id
    ON candidates(agency_id);

CREATE INDEX idx_candidates_agency_email
    ON candidates(agency_id, email);

CREATE INDEX idx_candidates_agency_name
    ON candidates(agency_id, first_name, last_name);