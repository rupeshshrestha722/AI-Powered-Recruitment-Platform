ALTER TABLE clients
ADD CONSTRAINT uk_clients_id_agency
UNIQUE (id, agency_id);