# RetailFlow Event Intelligence Platform

RetailFlow Event Intelligence Platform is a Spring Boot backend system for ingesting operational events, maintaining current entity state, exposing query APIs, and supporting offline reconciliation of event audit data.

The platform is designed around event-driven backend patterns: idempotent processing, auditability, cached reads, dead-letter handling, GraphQL/REST query access, Kubernetes deployment manifests, and Apache Spark-based offline reconciliation.

## Features

- Event ingestion API for operational state changes
- Idempotent event processing to prevent duplicate updates
- Current-state read model for fast entity lookups
- Event audit history for traceability and debugging
- REST APIs for ingestion, current state, and history
- GraphQL query support for entity state reads
- Kafka producer/consumer configuration
- Dead-letter handling for failed event processing
- Redis caching for hot current-state reads
- PostgreSQL persistence using Spring Data JPA
- Spring Boot Actuator health and metrics endpoints
- Docker Compose setup for local infrastructure
- Kubernetes manifests for deployment, service discovery, probes, and autoscaling
- Apache Spark offline reconciliation job for audit analysis

## Tech Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Kafka
- Spring Cache
- Spring Data Redis
- Spring GraphQL
- Spring Boot Actuator
- PostgreSQL
- Redis
- Kafka
- Docker Compose
- Kubernetes
- Apache Spark / PySpark
- Maven
- JUnit

## Architecture Overview

```text
Client / Producer
      |
      v
Spring Boot API
      |
      +--> Event Processing Service
      |       |
      |       +--> Idempotency Check
      |       +--> Current State Update
      |       +--> Audit Record Write
      |
      +--> Kafka Publisher / Consumer
      |
      +--> REST APIs
      |
      +--> GraphQL Query
      |
      +--> Redis Cache
      |
      v
PostgreSQL
Offline reconciliation runs separately through Spark:

Event Audit Data
      |
      v
Apache Spark Reconciliation Job
      |
      +--> Duplicate Event Report
      +--> Source-System Volume Report
      +--> High-Change Entity Report
      +--> Latest State From Audit Report
Project Structure
src/main/java/com/retailflow/event_intelligence/
  api/                 REST controllers and API models
  config/              Kafka and application configuration
  domain/              JPA entities
  graphql/             GraphQL controller
  kafka/               Kafka consumer, publisher, and event models
  repository/          Spring Data repositories
  service/             Business logic and query services

src/main/resources/
  application.yml      Application configuration
  graphql/             GraphQL schema files

k8s/
  configmap.yaml       Kubernetes ConfigMap
  secret.yaml          Kubernetes Secret placeholder
  deployment.yaml      Kubernetes Deployment
  service.yaml         Kubernetes Service
  hpa.yaml             HorizontalPodAutoscaler

spark-jobs/
  reconciliation_job.py
  sample-data/
    event-audit.jsonl
Prerequisites
Install:

Java 21
Maven
Docker
Docker Compose
Kubernetes through Docker Desktop, Minikube, or Kind
Apache Spark, optional for running the Spark job locally
Local Infrastructure
Start local dependencies:

docker compose up -d postgres redis zookeeper kafka
This starts:

PostgreSQL
Redis
Zookeeper
Kafka
Run The Application
Build the project:

mvn clean package
Run the application:

mvn spring-boot:run
The application runs on:

http://localhost:8080
Health Checks
curl http://localhost:8080/actuator/health
Readiness:

curl http://localhost:8080/actuator/health/readiness
Liveness:

curl http://localhost:8080/actuator/health/liveness
REST API Examples
Create Event
curl -X POST http://localhost:8080/events \
  -H "Content-Type: application/json" \
  -d '{
    "eventId": "evt-1001",
    "entityId": "item-1001",
    "entityType": "ITEM",
    "eventType": "STATE_CHANGED",
    "sourceSystem": "STORE_APP",
    "status": "AVAILABLE"
  }'
Get Current Entity State
curl http://localhost:8080/entities/item-1001/state
Get Entity Event History
curl http://localhost:8080/entities/item-1001/history
GraphQL
GraphQL endpoint:

http://localhost:8080/graphql
Example query:

query {
  entityState(entityId: "item-1001") {
    entityId
    entityType
    currentStatus
    lastEventId
    lastSourceSystem
  }
}
If GraphiQL is enabled, it is available at:

http://localhost:8080/graphiql
Kubernetes
The k8s/ folder contains deployment manifests for running the Spring Boot service in Kubernetes.

Included resources:

Deployment
Service
ConfigMap
Secret placeholder
Readiness probe
Liveness probe
HorizontalPodAutoscaler
Build the application JAR:

mvn clean package
Build the Docker image:

docker build -t retailflow-event-intelligence:0.1.0 .
Apply Kubernetes manifests:

kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secret.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
kubectl apply -f k8s/hpa.yaml
Check resources:

kubectl get pods
kubectl get svc
kubectl get hpa
View logs:

kubectl logs deployment/retailflow-event-intelligence
Test through the Kubernetes service:

curl http://localhost:30080/actuator/health
Apache Spark Reconciliation
The spark-jobs/ folder contains an offline reconciliation job for event audit data.

The job answers:

Were duplicate events received?
Which source system produced unusual volume?
Which entities changed status too many times?
What is the latest state according to audit history?
Run the Spark job:

spark-submit spark-jobs/reconciliation_job.py \
  --events spark-jobs/sample-data/event-audit.jsonl \
  --output spark-jobs/out/reconciliation
Output folders:

spark-jobs/out/reconciliation/duplicate-events
spark-jobs/out/reconciliation/source-system-volume
spark-jobs/out/reconciliation/high-change-entities
spark-jobs/out/reconciliation/latest-state-from-audit
Testing
Run tests:

mvn test
Notes
For local development, Docker Compose is used for infrastructure dependencies. In a deployed environment, Kafka, PostgreSQL, and Redis can be provided as managed or separately provisioned services.

Kubernetes manifests in this repository focus on deploying the Spring Boot application service. The Spark reconciliation job is designed as a separate offline batch process.