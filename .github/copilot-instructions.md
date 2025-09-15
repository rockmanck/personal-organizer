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
- [x] **Basic data models (User, Project, Task) implemented**
- [x] **MongoDB repositories and services created**
- [x] **Core API endpoints for tasks and projects implemented**
- [x] **REST API endpoints tested and functional**
- [ ] Frontend-backend API integration
- [ ] Basic authentication flow implementation
- [ ] Error handling and validation
- [ ] Frontend components for data management

## Basic Data Models Implementation Completed ✅

### Step 4: Data Models and API Layer Completed
**Date**: September 14, 2025

#### Data Models Implemented
- **User Entity**: Complete user model with OAuth2 integration, preferences, and Jedi Techniques settings
- **Project Entity**: Plan mode implementation with status workflow, priority management, and project settings
- **Task Entity**: Act mode implementation with status tracking, time management, and reflection data

#### Repository Layer
- **UserRepository**: MongoDB repository with custom query methods for user management
- **ProjectRepository**: Project-specific queries including active projects and status filtering
- **TaskRepository**: Complex task queries for actionable items, today's tasks, and reflection workflows

#### Service Layer
- **UserService**: Business logic for user management, OAuth user creation, and profile operations
- **ProjectService**: Project lifecycle management, statistics, and status transitions
- **TaskService**: Task workflow management, time tracking, and reflection handling

#### REST API Endpoints
- **User Controller**: `/api/v1/users/*` - User profile and preferences management
- **Project Controller**: `/api/v1/projects/*` - Complete CRUD operations for projects
- **Task Controller**: `/api/v1/tasks/*` - Task management with specialized endpoints for today's tasks

#### Key API Endpoints Available
- `GET /api/v1/users/me` - Current user profile ✅
- `GET /api/v1/projects` - List all projects ✅
- `POST /api/v1/projects` - Create new project ✅
- `GET /api/v1/tasks/today` - Today's actionable tasks ✅
- `GET /api/v1/tasks/actionable` - All actionable tasks ✅
- `POST /api/v1/tasks` - Create new task ✅

#### Database Integration
- **MongoDB Collections**: users, projects, tasks with proper indexing
- **Spring Data MongoDB**: Automatic index creation and management
- **Database Cleanup**: Resolved index conflicts and confirmed persistence

#### Testing Results
- ✅ All REST endpoints responding correctly
- ✅ JSON serialization working properly
- ✅ MongoDB integration functional
- ✅ Docker stack fully operational
- ✅ Nginx proxy routing working
- ✅ Full stack integration confirmed

## Next Steps
1. ~~Implement basic data models (User, Project, Task)~~ ✅ **COMPLETED**
2. ~~Create MongoDB repositories and services~~ ✅ **COMPLETED** 
3. ~~Add authentication endpoints (login, logout, user info)~~ ✅ **COMPLETED**
4. ~~Implement core API endpoints for tasks and projects~~ ✅ **COMPLETED**
5. ~~Connect frontend to backend APIs~~ ✅ **COMPLETED** (see Step 5 details below)
6. Add proper authentication flow (OAuth2)
7. Add error handling and validation
8. Implement frontend components for task and project management

### Step 5: Frontend-Backend Integration Completed ✅
**Date**: September 15, 2025

#### Frontend API Integration Implemented
- **TypeScript Interfaces**: Complete type definitions for User, Project, Task, and API responses aligned with backend models
- **API Service Layer**: Centralized HTTP client with error handling and CRUD operations for all entities
- **React Hooks**: Custom hooks for data fetching (useUser, useProjects, useTasks, useTodayTasks, useActionableTasks)
- **Component Integration**: Updated Dashboard, ActMode, and PlanMode to use real backend data
- **Task Creation**: Functional task creation form with proper enum handling

#### Key Implementations
- **API Client** (`api-client.ts`): Fetch-based HTTP client with error handling and response typing
- **Service Classes**: 
  - `user-service.ts`: User profile and preferences management
  - `project-service.ts`: Project CRUD operations and statistics
  - `task-service.ts`: Task management including completion and creation
- **React Hooks**: State management and async operations for all API interactions
- **Task Form Component**: Complete task creation interface with TaskType and TaskPriority selection

#### Type Alignment Achievements
- ✅ User interface matches backend JSON response structure
- ✅ Project interface compatible with backend entity
- ✅ TaskType enum updated to match backend values (ACTION, WAITING_FOR, REFERENCE, SOMEDAY_MAYBE)
- ✅ TaskStatus enum aligned with backend workflow
- ✅ API response types properly structured

#### Integration Test Results
- ✅ Frontend builds successfully with all components
- ✅ API endpoints accessible through service layer
- ✅ Real user data displays in Dashboard component
- ✅ Task creation API calls work correctly
- ✅ Hot module reload working for development

#### Next Priority Actions for Step 6
1. ✅ ~~Update repository queries to work without priority~~ **COMPLETED**
2. ✅ ~~Test complete frontend-backend flow~~ **COMPLETED**
3. Begin OAuth2 authentication implementation
4. Add error handling and validation improvements
