CREATE TABLE users (
    id UUID PRIMARY KEY,

    agency_id UUID NOT NULL,

    first_name VARCHAR(100) NOT NULL,

    last_name VARCHAR(100) NOT NULL,

    email VARCHAR(255) NOT NULL,

    password_hash VARCHAR(255) NOT NULL,

    role VARCHAR(50) NOT NULL DEFAULT 'RECRUITER',

    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',

    last_login_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

      CONSTRAINT fk_users_agency
            FOREIGN KEY (agency_id)
            REFERENCES agencies(id),

    CONSTRAINT uk_users_agency_email UNIQUE (agency_id, email)
);

CREATE INDEX idx_users_agency_id ON users(agency_id);

