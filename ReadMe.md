# TalentFlow AI – Backend

**AI-Powered Recruitment & Talent Management Platform**

TalentFlow AI is an AI-powered recruitment platform designed to help recruitment agencies streamline candidate management, CV processing, and talent acquisition through intelligent automation and AI-assisted candidate evaluation.

The backend is built using **Java 21 and Spring Boot**, following a modular architecture with multi-tenant support, secure authentication, RESTful APIs, and LLM-powered CV processing.

## 🚀 Key Features

### Authentication & Authorization
- JWT-based authentication.
- Role-based access control.
- Platform administrator authentication.
- Agency-level user access and isolation.
- Support for ADMIN, MANAGER, RECRUITER, and PLATFORM_ADMIN roles.

### Multi-Tenant Architecture
- Agency-based tenant management.
- Tenant-level data isolation.
- Agency onboarding and management.
- Centralized platform administration.

### Candidate Management
- Create, retrieve, update, and manage candidate profiles.
- Candidate document management.
- CV upload and document storage.
- Candidate information management.

### AI-Powered CV Processing
- Automated CV document processing.
- Text extraction using Apache Tika.
- LLM-powered CV information extraction.
- Structured candidate information stored as JSON.
- AI processing status tracking.
- Review and approval workflow for extracted candidate information.

### AI &