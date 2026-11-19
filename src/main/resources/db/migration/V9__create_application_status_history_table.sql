ALTER TABLE applications
ADD CONSTRAINT uk_applications_id_agency
UNIQUE (id, agency_id);

CREATE TABLE application_status_history (
    id UUID PRIMARY KEY,
    agency_id UUID NOT NULL,
    application_id UUID NOT NULL,

    previous_status VARCHAR(50),
    new_status VARCHAR(50) NOT NULL,

    changed_by UUID,
    notes TEXT,

    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_status_history_agency
        FOREIGN KEY (agency_id)
        REFERENCES agencies(id),

    CONSTRAINT fk_status_history_application
        FOREIGN KEY (application_id, agency_id)
        REFERENCES applications(id, agency_id),

    CONSTRAINT fk_status_history_user
        FOREIGN KEY (changed_by)
        REFERENCES users(id)
);

CREATE INDEX idx_status_history_application
    ON application_status_history(application_id, changed_at);

CREATE INDEX idx_status_history_agency
    ON application_status_history(agency_id);

CREATE INDEX idx_status_history_changed_by
    ON application_status_history(changed_by);
