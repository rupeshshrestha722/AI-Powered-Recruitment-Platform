CREATE TABLE clients (
    id UUID PRIMARY KEY,
    agency_id UUID NOT NULL,

    company_name VARCHAR(200) NOT NULL,
    industry VARCHAR(150),
    email VARCHAR(255),
    phone VARCHAR(30),
    website VARCHAR(500),
    country VARCHAR(100),
    city VARCHAR(100),
    address TEXT,

    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_clients_agency
        FOREIGN KEY (agency_id) REFERENCES agencies(id)
);

CREATE INDEX idx_clients_agency_id
    ON clients(agency_id);

CREATE INDEX idx_clients_agency_company
    ON clients(agency_id, company_name);

CREATE INDEX idx_clients_agency_email
    ON clients(agency_id, email);