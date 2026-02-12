# Quick Reference Card

## 🚀 Essential Commands

### Docker Operations
```powershell
# Start services
docker-compose up -d

# Stop services
docker-compose down

# View logs
docker-compose logs -f postgres

# Start with PgAdmin
docker-compose --profile tools up -d

# Reset everything
docker-compose down -v
docker-compose up -d
```

### Maven Commands
```powershell
# Build
mvn clean install

# Run application
mvn spring-boot:run

# Run unit tests
mvn test

# Run all tests
mvn verify

# Skip tests
mvn clean install -DskipTests
```

### Database Access
```powershell
# PostgreSQL CLI
docker-compose exec postgres psql -U scholar_user -d scholar_ai_db

# SQL Commands
\dt              # List tables
\dx              # List extensions
\d table_name    # Describe table
\q               # Quit
```

## 📦 Project Structure at a Glance

```
src/main/java/.../scholarai/
├── domain/          # 🏛️ Pure Java - NO Spring
│   ├── model/       # 7 entities created
│   └── port/        # 4 ports created
│       ├── in/      # Use case interfaces
│       └── out/     # Repository interfaces
├── application/     # 🔧 Use case implementations
├── infrastructure/  # 🔌 Spring & frameworks
└── ScholarAiApplication.java
```

## 🎯 Core Domain Models

| Class | Type | Purpose |
|-------|------|---------|
| `Paper` | Entity | Academic paper with business logic |
| `PaperId` | Value Object | Type-safe identifier |
| `Citation` | Value Object | Citation information |
| `Embedding` | Value Object | Vector representation |
| `TextChunk` | Value Object | Text segment for RAG |
| `PaperAnalysis` | Aggregate | Complete analysis result |

## 🔌 Ports (Interfaces)

### Input Ports (Use Cases)
- `AnalyzePaperUseCase` - Analyze a paper
- `UploadPaperUseCase` - Upload PDF paper

### Output Ports (Repositories)
- `LoadPaperPort` - Load paper from storage
- `SavePaperPort` - Persist paper

## 📄 Configuration Files

| File | Purpose |
|------|---------|
| `pom.xml` | Maven dependencies |
| `docker-compose.yml` | Infrastructure setup |
| `application.yml` | Main config |
| `application-test.yml` | Test config |

## 🧪 Testing Strategy

```powershell
# Unit tests (pure domain logic)
mvn test -Dtest=PaperTest

# Integration tests (with Testcontainers)
mvn test -Dtest=PaperRepositoryIT

# All tests
mvn verify
```

## 🔗 Important URLs

| Service | URL | Credentials |
|---------|-----|-------------|
| Application | http://localhost:8080/api | - |
| Health Check | http://localhost:8080/api/actuator/health | - |
| PgAdmin | http://localhost:5050 | admin@scholar-ai.local / admin |
| PostgreSQL | localhost:5432 | scholar_user / scholar_password |

## 📊 Dependencies Summary

```xml
Spring Boot:     3.4.1
Spring AI:       1.0.0-M6
Java:            21
PostgreSQL:      16
pgvector:        0.1.6
Testcontainers:  1.20.4
JUnit:           5.x
```

## 🎨 Architecture Rules

1. **Dependency Rule**: Dependencies point inward
   - Infrastructure → Application → Domain
   
2. **Domain Layer**: 
   - ❌ NO Spring annotations
   - ❌ NO JPA annotations
   - ✅ Pure Java only

3. **Naming Conventions**:
   - Use Cases: `*UseCase`
   - Ports: `*Port`
   - Adapters: `*Adapter`
   - Tests: `*Test` (unit), `*IT` (integration)

## 🔑 Key Design Patterns

- **Hexagonal Architecture** (Ports & Adapters)
- **Domain-Driven Design** (Rich domain model)
- **Repository Pattern** (Data access)
- **Use Case Pattern** (Application logic)
- **Value Object Pattern** (Type safety)

## 📝 Code Examples

### Creating a Domain Entity
```java
public record Paper(
    PaperId id,
    String title,
    List<String> authors
) {
    // Validation in compact constructor
    public Paper {
        Objects.requireNonNull(id);
        if (title.isBlank()) {
            throw new IllegalArgumentException("Invalid title");
        }
    }
    
    // Business logic methods
    public boolean hasDoi() { ... }
}
```

### Defining a Port
```java
public interface AnalyzePaperUseCase {
    PaperAnalysis analyze(PaperId paperId);
}
```

### Implementing a Use Case
```java
@Service
@RequiredArgsConstructor
public class AnalyzePaperService 
    implements AnalyzePaperUseCase {
    
    private final LoadPaperPort loadPaperPort;
    private final EmbeddingPort embeddingPort;
    
    @Override
    public PaperAnalysis analyze(PaperId id) {
        // Implementation
    }
}
```

## 🐛 Troubleshooting

### Port Already in Use
```powershell
netstat -ano | findstr :8080
taskkill /PID <pid> /F
```

### Docker Issues
```powershell
docker-compose down -v
docker system prune -a
docker-compose up -d
```

### Maven Issues
```powershell
mvn dependency:purge-local-repository
mvn clean install -U
```

## 📚 Documentation Files

1. **README.md** - Project overview
2. **ARCHITECTURE.md** - Detailed architecture
3. **PROJECT_STRUCTURE.md** - Folder structure
4. **GETTING_STARTED.md** - Setup guide
5. **SETUP_COMPLETE.md** - Summary & next steps
6. **QUICK_REFERENCE.md** - This file

## ✅ What's Done

- ✅ Project structure
- ✅ Maven configuration
- ✅ Docker setup
- ✅ Domain models (7 classes)
- ✅ Port interfaces (4 interfaces)
- ✅ Configuration files
- ✅ Documentation (6 files)
- ✅ Successfully compiles

## 🔲 Next TODO

1. Implement application services
2. Create infrastructure adapters
3. Write comprehensive tests
4. Add REST controllers
5. Implement Spring AI integration

---

**Keep this handy while developing!**
