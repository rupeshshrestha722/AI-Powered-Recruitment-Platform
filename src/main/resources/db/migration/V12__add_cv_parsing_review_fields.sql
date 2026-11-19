
ALTER TABLE candidate_cv_parsing_results
ADD COLUMN review_status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
ADD COLUMN reviewed_by UUID,
ADD COLUMN reviewed_at TIMESTAMP;

ALTER TABLE candidate_cv_parsing_results
ADD CONSTRAINT fk_cv_parsing_reviewed_by
FOREIGN KEY (reviewed_by)
REFERENCES users(id);

ALTER TABLE candidate_cv_parsing_results
ADD CONSTRAINT chk_cv_parsing_review_status
CHECK (review_status IN ('PENDING', 'REVIEWED', 'APPROVED', 'REJECTED'));

CREATE INDEX idx_cv_parsing_review_status
ON candidate_cv_parsing_results(agency_id, review_status);
