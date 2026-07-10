Credit Risk Scoring Engine

A containerized backend application that evaluates loan applications and generates explainable credit scores on a 300–850 scale. The engine analyzes five financial factors to estimate an applicant’s credit risk and provide a clear breakdown of how the final score was calculated.

Features
Generates credit scores ranging from 300 to 850
Evaluates applicants across five financial factors
Provides an explanation of the factors affecting each score
Exposes RESTful endpoints for submitting and retrieving loan evaluations
Uses idempotent request processing to prevent duplicate applications
Stores application and scoring data in PostgreSQL
Manages database schema changes with Flyway migrations
Runs the application and database through Docker containers
Tech Stack
Java
Spring Boot
PostgreSQL
Spring Data JPA
Flyway
Docker
Docker Compose
Maven
REST APIs

How It Works:
The engine receives a loan application containing an applicant’s financial information. It evaluates the application across five risk factors and assigns points based on the applicant’s financial profile.
The individual factor scores are combined to produce a final credit score between 300 and 850.


The scoring engine can evaluate factors such as:
Income
Debt-to-income ratio
Credit history
Employment history
Requested loan amount

Architecture

The application follows a layered Spring Boot architecture:

Controller Layer
      |
      v
Service Layer
      |
      v
Scoring Engine
      |
      v
Repository Layer
      |
      v
PostgreSQL Database
