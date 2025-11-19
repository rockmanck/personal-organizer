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
- [x] Frontend-backend API integration
- [x] Basic authentication flow implementation
- [x] Error handling and validation
- [ ] Frontend components for data management

## Basic Data Models Implementation Completed ✅

### Data Models and API Layer

#### Data Models
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

### Frontend-Backend Integration

#### Key Implementations
- **API Client** (`api-client.ts`): Fetch-based HTTP client with error handling and response typing
- **Service Classes**: 
  - `user-service.ts`: User profile and preferences management
  - `project-service.ts`: Project CRUD operations and statistics
  - `task-service.ts`: Task management including completion and creation
- **React Hooks**: State management and async operations for all API interactions
- **Task Form Component**: Complete task creation interface with TaskType and TaskPriority selection

### OAuth2 Authentication Implementation

#### Complete OAuth2 Authentication Flow Implemented
- **Spring Security OAuth2**: Full OAuth2 client configuration with Google provider integration
- **JWT Token Management**: Secure token generation, validation, and claims extraction using JJWT 0.12.3
- **Authentication Endpoints**: Complete REST API for authentication operations (/auth/me, /auth/refresh, /auth/logout)
- **Frontend Authentication**: React context-based auth state management with automatic token handling
- **Route Protection**: PrivateRoute component with authentication-aware routing and redirects

#### Backend Authentication Infrastructure
- **SecurityConfig.java**: OAuth2 login configuration with JWT authentication filter chain and endpoint security
- **JwtService.java**: Complete JWT operations including token generation, validation, claims extraction, and user details integration
- **JwtAuthenticationFilter.java**: Request filter for Bearer token extraction and SecurityContext authentication
- **OAuth2AuthenticationSuccessHandler.java**: Post-login handler for user creation/update and JWT token generation with frontend redirect
- **UserService.java**: UserDetailsService implementation with OAuth user management and Spring Security integration
- **AuthController.java**: REST endpoints for current user retrieval, token refresh, and logout operations

#### Frontend Authentication Components
- **AuthContext.tsx**: React authentication context with login/logout state, token storage, and user management
- **Login.tsx**: Material-UI Google OAuth login interface with environment-configurable OAuth URLs
- **AuthCallback.tsx**: OAuth redirect handler for token processing and error handling with user feedback
- **PrivateRoute.tsx**: Route protection component with loading states and authentication redirects
- **Layout.tsx**: Authentication-aware navigation with user menu, profile display, and logout functionality

#### Security Configuration Achievements
- ✅ Google OAuth2 provider configuration with proper scopes (openid, profile, email)
- ✅ JWT token security with configurable secret and expiration (24 hours default)
- ✅ Spring Security filter chain with OAuth2 and JWT authentication
- ✅ Stateless session management for scalable authentication
- ✅ CORS configuration for cross-origin frontend requests
- ✅ Environment-based configuration for OAuth client credentials

#### Frontend Integration Results
- ✅ React Router integration with authentication-aware route protection
- ✅ Material-UI components for consistent authentication UI
- ✅ Environment variable support for API and OAuth URL configuration
- ✅ TypeScript type definitions for authentication state and user data
- ✅ Local storage token persistence with automatic retrieval on app load
- ✅ Error handling for authentication failures and token expiration

#### Authentication Flow Implementation
1. **Login Initiation**: User clicks Google sign-in button → redirects to backend OAuth2 endpoint
2. **OAuth2 Processing**: Backend handles Google OAuth flow → creates/updates user in MongoDB
3. **JWT Generation**: Successful OAuth authentication → generates JWT token with user claims
4. **Frontend Redirect**: Backend redirects to frontend callback with JWT token
5. **Token Storage**: Frontend stores JWT in localStorage and updates authentication context
6. **Route Protection**: PrivateRoute components enforce authentication for protected pages
7. **API Authorization**: HTTP client includes JWT Bearer token in all API requests
8. **User Interface**: Layout component displays user information and logout functionality

#### Security Features Implemented
- **Token-based Authentication**: Stateless JWT tokens for scalable session management
- **OAuth2 Integration**: Secure third-party authentication with Google provider
- **Route Protection**: Client-side route guarding with authentication state validation
- **Automatic Token Refresh**: Infrastructure ready for token refresh implementation
- **Logout Functionality**: Complete user logout with token invalidation and context clearing
