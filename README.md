# 🔐 SentinelStream - Real-Time Log Analytics & Threat Detection Platform

A production-grade SIEM-like platform built with modern microservices architecture. Ingests logs at high volume, processes them in real-time, detects suspicious patterns using rules + ML anomaly detection, and provides a comprehensive dashboard for search and alerts.

## 🎯 Key Features

- **Real-Time Log Ingestion**: High-throughput log pipeline with validation
- **Advanced Threat Detection**: Rule-based + ML-powered anomaly detection
- **Full-Text Search**: Elasticsearch/OpenSearch integration
- **Role-Based Access Control**: ADMIN, ANALYST, VIEWER roles
- **Alert Management**: Create, track, and resolve security incidents
- **Audit Trail**: Complete audit logging of all security events
- **Scalable Architecture**: Kafka-based event streaming, stateless microservices
- **Cloud-Ready**: Docker Compose for local dev, Kubernetes manifests for production

## 🏗️ Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                  React TypeScript Dashboard                  │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                    API Gateway (Spring Boot)                 │
│              - Routing, Auth, Rate Limiting                  │
└┬───────┬───────┬────────┬──────────┬────────────┬───────────┬┘
 │       │       │        │          │            │           │
 ▼       ▼       ▼        ▼          ▼            ▼           ▼
Auth   Ingest  Alert   Audit    Detection    Enrichment   Indexer
Service Service Service Service  Service      Service     Service
 │       │       │        │          │            │           │
 └───────┴───┬───┴────────┴──────────┴────────────┴────────────┘
             │
      ┌──────▼──────┐
      │    Kafka    │  (logs.raw → logs.enriched → alerts.generated)
      └──────┬──────┘
             │
    ┌────────┼────────┐
    ▼        ▼        ▼
Postgres  Elastic  ML Service
          Search   (Python FastAPI)
```

## 🧩 Microservices

| Service | Port | Purpose |
|---------|------|---------|
| api-gateway | 8080 | Request routing, auth middleware, rate limiting |
| auth-service | 8081 | JWT auth, user management, RBAC |
| ingestion-service | 8082 | REST endpoint for log ingestion |
| enrichment-service | 8083 | Log enrichment (timestamp, GeoIP, severity) |
| indexer-service | 8084 | Index logs into Elasticsearch/OpenSearch |
| detection-service | 8085 | Rule-based + ML-powered threat detection |
| alert-service | 8086 | Alert management and storage |
| audit-service | 8087 | Audit trail logging |
| ml-anomaly-service | 8088 | FastAPI service for ML scoring |

## 🧬 Kafka Topics

- `logs.raw`: Raw inbound logs
- `logs.enriched`: Enriched logs ready for indexing
- `alerts.generated`: Generated security alerts
- `audit.events`: Audit trail events

## ⚡ Quick Start

### Prerequisites
- Docker & Docker Compose
- Java 17+ (for local Spring Boot dev)
- Python 3.9+ (for ML service)
- Node 18+ (for frontend)

### Local Development with Docker Compose

```bash
docker compose up -d
```

Services will be available at:
- API Gateway: http://localhost:8080
- Dashboard: http://localhost:3000
- Elasticsearch: http://localhost:9200
- Kafka UI: http://localhost:8081

### Development Setup

```bash
# Backend
cd backend/api-gateway
mvn spring-boot:run

# ML Service
cd ml-service
pip install -r requirements.txt
python -m uvicorn app.main:app --reload --port 8088

# Frontend
cd frontend
npm install
npm run dev
```

## 📊 Database Schema

See `docs/database_schema.md` for PostgreSQL schema and `docs/elastic_mappings.md` for Elasticsearch mappings.

## 🔒 Security Features

- JWT token-based authentication
- Role-based access control
- Rate limiting and request validation
- Encrypted password storage (bcrypt)
- Complete audit trail
- Input sanitization across all services

## 📈 Scaling

- **Horizontal Scale**: Run multiple instances of each microservice
- **Kafka Partitioning**: 10 partitions per topic for parallel processing
- **Elasticsearch Sharding**: 5 primary + 1 replica per index
- **Connection Pooling**: HikariCP for database connections
- **Caching**: Redis for frequent queries (optional)

## 📚 Documentation

- [Architecture & Design](docs/architecture.md)
- [API Documentation](docs/api_docs.md)
- [Kafka Events Schema](docs/kafka_events.md)
- [Elasticsearch Mappings](docs/elastic_mappings.md)
- [Deployment Guide](docs/deployment.md)

## 🧪 Testing

```bash
# Run all tests
mvn test -pl backend

# Integration tests
mvn verify -pl backend

# Frontend tests
cd frontend && npm test
```

## 📝 License

MIT License - See LICENSE file

## 🤝 Contributing

1. Feature branches: `feature/feature-name`
2. Bug fixes: `fix/bug-description`
3. All PRs require tests and documentation

---

Built with ❤️ for security teams | Production-ready SIEM alternative
