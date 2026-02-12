# Scholar AI Assistant

> **Production-Grade RAG Application for Academic Paper Analysis**

A sophisticated Retrieval-Augmented Generation (RAG) system built with Java 21 and Spring Boot 3.4, designed to analyze academic papers with high precision citations, privacy-first architecture, and clean code principles.

## 🎯 Project Goals

- **High Precision**: Accurate citation extraction and retrieval
- **Privacy-First**: Local/private deployment capabilities
- **Clean Architecture**: Hexagonal architecture with clear separation of concerns
- **Production-Ready**: Comprehensive testing, documentation, and deployment configuration

## 🏗️ Architecture

This project follows **Clean Architecture (Hexagonal)** principles:

```
src/
├── main/
│   ├── java/de/portfolio/scholarai/
│   │   ├── domain/                    # Core business logic (NO framework dependencies)
│   │   │   ├── model/                 # Domain entities and value objects
│   │   │   └── port/                  # Interfaces (Ports)
│   │   │       ├── in/                # Input ports (Use case interfaces)
│   │   │       └── out/               # Output ports (Repository interfaces)
│   │   │
│   │   ├── application/               # Use case implementations
│   │   │   ├── usecase/               # Use case orchestration
│   │   │   └── service/               # Application services
│   │   │
│   │   ├── infrastructure/            # External concerns (Adapters)
│   │   │   ├── adapter/
│   │   │   │   ├── in/rest/           # REST Controllers (Web adapters)
│   │   │   │   └── out/
│   │   │   │       ├── persistence/   # JPA repositories
│   │   │   │       ├── ai/            # Spring AI integration
│   │   │   │       └── pdf/           # PDF processing
│   │   │   └── config/                # Spring configuration
│   │   │
│   │   └── ScholarAiApplication.java  # Main application entry point
│   │
│   └── resources/
│       ├── application.yml
│       ├── application-test.yml
│       └── db/migration/              # Flyway migrations (if used)
│
└── test/
    ├── java/de/portfolio/scholarai/
    │   ├── domain/                    # Domain unit tests
    │   ├── application/               # Use case unit tests
    │   └── infrastructure/
    │       └── integration/           # Integration tests with Testcontainers
    └── resources/
        └── test-data/                 # Test fixtures
```

## 🛠️ Technology Stack

| Category | Technology |
|----------|------------|
| Language | Java 21 |
| Framework | Spring Boot 3.4.1 |
| AI Integration | Spring AI 1.0.0-M6 |
| Database | PostgreSQL 16 + pgvector |
| Build Tool | Maven |
| Testing | JUnit 5, Mockito, Testcontainers |
| PDF Processing | Apache PDFBox, Apache Tika |
| Code Quality | Lombok, Validation API |

## 🚀 Getting Started

### Prerequisites

- Java 21 JDK
- Docker & Docker Compose
- Maven 3.9+
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/scholar-ai-assistant.git
cd scholar-ai-assistant
```

### 2. Start Infrastructure

```bash
# Start PostgreSQL with pgvector
docker-compose up -d

# Optional: Start with PgAdmin for database management
docker-compose --profile tools up -d
```

### 3. Configure Environment

Create a `.env` file or set environment variables:

```bash
export OPENAI_API_KEY=your-actual-api-key
```

### 4. Build the Project

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

The application will be available at: `http://localhost:8080/api`

### 6. Run Tests

```bash
# Unit tests only
mvn test

# Integration tests with Testcontainers
mvn verify

# All tests
mvn clean verify
```

## 📝 Development Guidelines

### Code Quality Standards

1. **Strong Typing**: Use Java Records for immutable data structures
2. **Documentation**: All public methods must have Javadoc
3. **Testing**: Minimum 80% code coverage with meaningful tests
4. **No Anemic Domain Model**: Business logic belongs in the domain layer
5. **Dependency Rule**: Dependencies point inward (infrastructure → application → domain)

### Naming Conventions

- **Use Cases**: `<Action><Entity>UseCase` (e.g., `AnalyzePaperUseCase`)
- **Ports**: `<Action><Entity>Port` (e.g., `LoadPaperPort`, `SavePaperPort`)
- **Adapters**: `<Technology><Purpose>Adapter` (e.g., `PostgresPaperAdapter`)
- **Integration Tests**: `*IT.java`
- **Unit Tests**: `*Test.java`

### Commit Convention

Follow [Conventional Commits](https://www.conventionalcommits.org/):

```
feat: add paper upload endpoint
fix: correct citation extraction logic
docs: update architecture documentation
test: add integration tests for vector search
refactor: extract embedding logic to separate service
```

## 🔧 Configuration

### Application Properties

Key configuration in `application.yml`:

- **Database**: Connection pooling with HikariCP
- **Spring AI**: OpenAI integration for embeddings and chat
- **Vector Store**: pgvector configuration (HNSW index, cosine similarity)
- **File Upload**: Size limits and storage location
- **RAG Settings**: Chunk size, overlap, similarity threshold

### Docker Compose

- **PostgreSQL**: pgvector/pgvector:pg16 with persistent volume
- **PgAdmin**: Optional database management tool (profile: tools)
- **Networking**: Isolated bridge network for services

## 📦 Project Structure Explained

### Domain Layer (Pure Java)

- **NO Spring dependencies allowed**
- Contains business entities, value objects, and port interfaces
- Example: `Paper`, `Citation`, `Embedding`, `AnalyzePaperPort`

### Application Layer

- Implements use cases by orchestrating domain objects and ports
- Example: `AnalyzePaperUseCase` coordinates paper loading, chunking, embedding, and storage

### Infrastructure Layer

- Implements ports using external frameworks and libraries
- Spring configurations, REST controllers, JPA repositories, AI clients
- Example: `OpenAiEmbeddingAdapter`, `PostgresPaperRepository`

## 🧪 Testing Strategy

1. **Unit Tests**: Test domain logic and use cases with mocks
2. **Integration Tests**: Use Testcontainers for real database and service tests
3. **Contract Tests**: Verify adapter implementations fulfill port contracts
4. **End-to-End Tests**: Test complete workflows through REST API

## 📊 Monitoring & Observability

- Spring Boot Actuator endpoints for health checks and metrics
- Structured logging with correlation IDs
- Database query performance monitoring

## 🤝 Contributing

This is a portfolio project, but feedback and suggestions are welcome!

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👤 Author

**Your Name**  
Portfolio Project for German Job Market  
[LinkedIn](https://linkedin.com/in/yourprofile) | [GitHub](https://github.com/yourusername)

## 🙏 Acknowledgments

- Spring AI Team for the amazing AI integration framework
- PostgreSQL pgvector for efficient vector similarity search
- Clean Architecture principles by Robert C. Martin

---

**Built with ❤️ and Java 21 for the German job market**
