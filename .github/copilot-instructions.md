# Jedi Organizer - Copilot Instructions

## Project Overview
This is the implementation of the Jedi Organizer, a web-based personal productivity application implementing Maxim Dorofeev's Jedi Techniques methodology.

## Technology Stack
- **Frontend**: React + TypeScript + Vite + Material-UI
- **Backend**: Java 21 + Spring Boot 3.x + MongoDB
- **Authentication**: Google OAuth 2.0
- **Deployment**: Docker + Nginx

## Architecture Decisions

### Phase 1: MVP Foundation - Project Infrastructure (In Progress)
**Date**: September 14, 2025

#### Repository Structure
Created the following directory structure according to the implementation plan:
```
jedi-organizer/
├── .github/workflows/        # CI/CD pipelines
├── docs/                     # Documentation
│   ├── api/                  # API documentation
│   ├── architecture/         # Architecture docs
│   └── user-guides/          # User documentation
├── backend/                  # Spring Boot application
├── frontend/                 # React application
└── infrastructure/           # Docker and deployment configs
    ├── nginx/                # Nginx configuration
    └── mongodb/              # MongoDB configuration
```

#### Key Decisions
1. **API-First Development**: Will define complete OpenAPI specification before implementation
2. **Mobile-First Approach**: Responsive design with touch-optimized interactions
3. **Offline-First Strategy**: Local storage with background synchronization
4. **Progressive Enhancement**: Core functionality works without JavaScript
5. **Simplified Security**: Temporarily disabled OAuth2 for development, will re-enable after frontend setup
6. **Code Quality**: Disabled Checkstyle/PMD/SpotBugs temporarily, will configure after MVP
7. **MongoDB Connection**: Application handles MongoDB connection failures gracefully

## Backend Implementation Completed ✅

### Technology Stack Implemented
- **Java 21** with Eclipse Temurin JVM
- **Spring Boot 3.2.0** with auto-configuration
- **Gradle 8.14** build system with wrapper
- **Spring Security** with configurable authentication
- **Spring Data MongoDB** for database operations
- **OpenAPI 3.0** documentation with Swagger UI
- **JaCoCo** for test coverage reporting

### Project Structure
```
backend/
├── src/main/java/com/jediorganizer/
│   ├── JediOrganizerApplication.java    # Main Spring Boot app
│   ├── controller/
│   │   └── HealthController.java        # Health check endpoints
│   └── config/
│       └── SecurityConfig.java          # Security configuration
├── src/main/resources/
│   └── application.properties           # App configuration
├── src/test/java/com/jediorganizer/
│   └── JediOrganizerApplicationTests.java # Integration tests
├── build.gradle                         # Gradle build configuration
├── settings.gradle                      # Gradle settings
└── gradlew                              # Gradle wrapper
```

### Key Endpoints Implemented
- **GET /api/v1/health** - Basic health check
- **GET /api/v1/health/info** - Detailed system information
- **GET /api/v1/actuator/health** - Spring Boot actuator health
- **GET /api/v1/swagger-ui.html** - API documentation

## Frontend Implementation Completed ✅

### Technology Stack Implemented
- **React 18** with TypeScript support
- **Vite 5.0** for fast development and building
- **Material-UI 5.15** component library
- **React Router 6.8** for navigation
- **React Query 3.39** for API state management
- **Emotion** for CSS-in-JS styling

### Project Structure
```
frontend/
├── src/
│   ├── components/
│   │   ├── common/
│   │   │   └── NotFound.tsx           # 404 error page
│   │   ├── layout/
│   │   │   └── Layout.tsx             # Main app layout
│   │   └── modes/
│   │       ├── Dashboard.tsx          # Mode selection
│   │       ├── act/
│   │       │   └── ActMode.tsx        # Task execution mode
│   │       ├── plan/
│   │       │   └── PlanMode.tsx       # Project planning mode
│   │       └── reflect/
│   │           └── ReflectMode.tsx    # Analytics/reflection mode
│   ├── hooks/                         # Custom React hooks
│   ├── services/                      # API services
│   ├── types/                         # TypeScript definitions
│   ├── utils/                         # Utility functions
│   ├── App.tsx                        # Main App component
│   ├── main.tsx                       # React app entry point
│   ├── theme.ts                       # Material-UI theme
│   └── index.css                      # Global styles
├── public/                            # Static assets
├── package.json                       # Dependencies and scripts
├── vite.config.ts                     # Vite configuration
├── tsconfig.json                      # TypeScript configuration
├── Dockerfile                         # Production Docker build
├── Dockerfile.dev                     # Development Docker build
└── nginx.conf                         # Nginx configuration
```

### Key Features Implemented
- **Three-Mode Interface**: Dashboard with Plan/Act/Reflect mode selection
- **Material-UI Design System**: Consistent, accessible UI components
- **Responsive Layout**: Mobile-first design with flexible grid system
- **TypeScript Support**: Type-safe development with strict configuration
- **React Router**: Client-side routing for SPA navigation
- **Theme System**: Customizable Material-UI theme with Jedi Organizer branding
- **Development Workflow**: Vite HMR for fast development iteration
- **Production Ready**: Optimized builds with Nginx serving

## Docker Development Environment Completed ✅

### Infrastructure Setup
- **Multi-service Architecture**: MongoDB + Backend + Frontend + Nginx
- **Docker Compose Configuration**: Complete development stack orchestration
- **Network Configuration**: Custom Docker network for service communication
- **Volume Management**: Persistent MongoDB data storage

### Service Configuration
```
jedi-organizer-mongo      mongo:7.0                     # Database service
jedi-organizer-backend    personal-organizer-backend    # Spring Boot API
jedi-organizer-frontend   personal-organizer-frontend   # React development server
jedi-organizer-nginx      nginx:alpine                  # Reverse proxy
```

### Port Mapping & Access
- **Application Access**: http://localhost (nginx proxy)
- **Direct Frontend**: http://localhost:3000 (Vite dev server)
- **Direct Backend**: http://localhost:8080 (Spring Boot)
- **Direct MongoDB**: localhost:27017 (MongoDB connection)

### Key Features Implemented
- **Hot Reload**: Frontend Vite HMR through nginx WebSocket proxy
- **API Proxying**: Seamless backend API access through nginx
- **Health Checks**: Automated container health monitoring
- **CORS Configuration**: Development-friendly cross-origin setup
- **Environment Variables**: Secure configuration management via .env
- **Persistent Data**: MongoDB data survives container restarts

### Docker Build Process
- **Backend**: Multi-stage build with Gradle dependency caching
- **Frontend**: Node.js build with npm dependency installation
- **Development Optimization**: Source code volume mounting for live reload
- **Production Ready**: Separate production Dockerfiles for optimized builds

## Development Guidelines

### Code Quality Standards
- **Frontend**: ESLint + Prettier + Husky
- **Backend**: Checkstyle + SpotBugs + PMD
- **Testing**: Minimum 80% code coverage
- **Documentation**: JSDoc (Frontend) + JavaDoc (Backend)

### Current Implementation Status
- [x] Repository structure created
- [x] Backend Spring Boot project setup
- [x] Backend build system working (Gradle 8.14 + Java 21)
- [x] Basic Spring Security configuration
- [x] Health check endpoints implemented
- [x] MongoDB configuration ready
- [x] OpenAPI documentation setup
- [x] Basic integration tests passing
- [x] Frontend React + TypeScript + Vite project setup
- [x] Material-UI component library configured
- [x] React Router for mode navigation
- [x] Responsive layout foundation
- [x] Three-mode interface (Plan/Act/Reflect) structure
- [x] Docker configuration for frontend and backend
- [x] Nginx configuration for development and production
- [x] Node.js dependencies installation (via Docker)
- [x] Docker development environment fully working
- [x] MongoDB running with Docker
- [x] Full stack integration through nginx proxy
- [ ] Basic authentication flow
- [ ] API endpoints beyond health checks

## Next Steps
1. Implement basic data models (User, Project, Task)
2. Create MongoDB repositories and services
3. Add authentication endpoints (login, logout, user info)
4. Implement core API endpoints for tasks and projects
5. Connect frontend to backend APIs
6. Add error handling and validation
