CREATE TABLE agencies (
   id UUID PRIMARY KEY,

   name VARCHAR(200) NOT NULL,

   email VARCHAR(255),

   phone VARCHAR(30),

   country VARCHAR(100),

   address TEXT,

   status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',

   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

   CONSTRAINT uk_agencies_email UNIQUE (email)

)