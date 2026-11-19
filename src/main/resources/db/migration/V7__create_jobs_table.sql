CREATE TABLE jobs (
    id UUID PRIMARY KEY,
    agency_id UUID NOT NULL,
    client_id UUID NOT NULL,

    title VARCHAR(200) NOT NULL,
    description TEXT,
    required_skills TEXT,

    min_experience NUMERIC(4,1),
    max_experience NUMERIC(4,1),

    employment_type VARCHAR(50),
    workplace_type VARCHAR(30),

    country VARCHAR(100),
    city VARCHAR(100),

    min_salary NUMERIC(12,2),
    max_salary NUMERIC(12,2),
    salary_currency VARCHAR(10) DEFAULT 'AED',

    openings INTEGER NOT NULL DEFAULT 1,

    status VARCHAR(30) NOT NULL DEFAULT 'OPEN',

    closing_date DATE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_jobs_agency
        FOREIGN KEY (agency_id) REFERENCES agencies(id),

    CONSTRAINT fk_jobs_client_agency
        FOREIGN KEY (client_id, agency_id)
        REFERENCES clients(id, agency_id),

    CONSTRAINT uk_jobs_id_agency
        UNIQUE (id, agency_id),

    CONSTRAINT chk_jobs_experience
        CHECK (
            min_experience IS NULL
            OR max_experience IS NULL
            OR min_experience <= max_experience
        ),

    CONSTRAINT chk_jobs_salary
        CHECK (
            min_salary IS NULL
            OR max_salary IS NULL
            OR min_salary <= max_salary
        ),

    CONSTRAINT chk_jobs_openings
        CHECK (openings > 0)
);

CREATE INDEX idx_jobs_agency_id
    ON jobs(agency_id);

CREATE INDEX idx_jobs_agency_client
    ON jobs(agency_id, client_id);

CREATE INDEX idx_jobs_agency_status
    ON jobs(agency_id, status);

CREATE INDEX idx_jobs_agency_title
    ON jobs(agency_id, title);