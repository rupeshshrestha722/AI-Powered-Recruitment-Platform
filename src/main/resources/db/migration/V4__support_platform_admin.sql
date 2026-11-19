-- Allow PLATFORM_ADMIN users to exist without an agency
ALTER TABLE users
ALTER COLUMN agency_id DROP NOT NULL;

-- ENFORCE agency assignment based on user role
ALTER TABLE users
ADD CONSTRAINT chk_platform_admin_agency
CHECK (
(role = 'PLATFORM_ADMIN' AND agency_id IS NULL)
OR
(role <> 'PLATFORM_ADMIN' AND agency_id IS NOT NULL)
);

-- Ensure platform admin email addresses are globally unique
CREATE UNIQUE INDEX uk_platform_admin_email
ON users (LOWER(email))
WHERE role = 'PLATFORM_ADMIN';