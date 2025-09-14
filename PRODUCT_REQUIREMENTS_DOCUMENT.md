# Product Requirements Document (PRD)
## Lifebook - Personal Efficiency Web Application

**Version:** 1.0  
**Date:** September 12, 2025  
**Author:** Product Owner  
**Status:** Draft

---

## Table of Contents
1. [Product Overview](#product-overview)
2. [Target Audience](#target-audience)
3. [Product Goals](#product-goals)
4. [Core Features](#core-features)
5. [Technical Requirements](#technical-requirements)
6. [User Experience Design](#user-experience-design)
7. [Success Metrics](#success-metrics)
8. [Implementation Phases](#implementation-phases)
9. [Future Considerations](#future-considerations)

---

## Product Overview

### Vision Statement
Jedi organizer is a web-based personal efficiency application that implements Maxim Dorofeev's Jedi Techniques methodology. It provides an efficient way to organize task execution by analyzing and planning the first step needed to reach goals, presented as a simple one-level task list.

### Core Value Proposition
- **Simplicity**: One-level task lists without nested complexity
- **Methodology-driven**: Based on proven Jedi Techniques for personal effectiveness
- **Multi-modal**: Three distinct modes for different productivity phases
- **Focus-oriented**: Minimalistic interfaces for concentrated work

### Product Philosophy
The application follows the principle that effective personal productivity comes from clear task definition, purposeful planning, focused execution, and thoughtful reflection - not from complex hierarchical structures or feature-heavy interfaces.

---

## Target Audience

### Primary Users
- **Professionals** seeking structured personal productivity systems
- **Knowledge workers** who need to balance planning and execution
- **Individuals** interested in personal effectiveness methodologies

### User Characteristics
- Familiar with basic productivity concepts
- Willing to adopt a structured approach to task management
- Prefer simplicity over feature complexity
- Value methodology-based approaches to productivity

---

## Product Goals

### Primary Objectives
1. Enable efficient task organization without complexity
2. Support the three-mode workflow: Plan → Act → Reflect
3. Provide clear visual separation between planning and execution
4. Facilitate regular reflection and goal assessment

### Success Criteria
- Daily engagement: Users interact with the system 3-4 times per day for task management
- Weekly engagement: Users engage in planning/reflection activities 1-3 times per week
- User retention: Consistent daily usage patterns
- Methodology adoption: Users successfully implement Jedi Techniques workflow

---

## Core Features

### 1. Task Management System

#### Task Data Model
```
Task:
- id: String (UUID)
- title: String (required)
- description: String (optional)
- state: Enum (DONE, TODO)
- createdAt: DateTime (system-generated)
- completedAt: DateTime (system-generated on completion)
- projectReferences: Array of ProjectReference
- userDefinedOrder: Integer

ProjectReference:
- projectId: String
- positionInProject: Integer
```

#### Task Operations
- Create tasks with title and optional description
- Mark tasks as done/not done with single click
- User-defined ordering (no automatic sorting)
- Associate tasks with multiple projects
- Track creation and completion timestamps

### 2. Project Management System

#### Project Data Model
```
Project:
- id: String (UUID)
- title: String (required)
- description: String (goal description)
- deadline: DateTime (optional)
- taskIds: Array of String (ordered list)
- createdAt: DateTime
- completedAt: DateTime (when all tasks completed)
- canvasPosition: Object {x: Number, y: Number}
```

#### Project Operations
- Create projects with goals and optional deadlines
- Manage ordered task lists within projects
- Track project progress based on completed tasks
- Position projects on planning canvas
- Calculate completion percentage

### 3. Three-Mode Interface System

#### PLAN Mode
**Purpose**: Strategic planning and project organization

**Features**:
- Canvas-based project dashboard with grid layout
- Drag-and-drop project positioning
- Project progress bars showing task completion percentage
- Project creation and goal definition interface
- Task-to-project association management

**UI Components**:
- Interactive canvas with project cards
- Project details panel
- Task assignment interface
- Progress visualization

#### ACT Mode
**Purpose**: Focused task execution

**Features**:
- Single, unified task list (all incomplete tasks)
- Minimalistic interface showing only task titles
- Icons indicating tasks with additional details
- Single-click task completion
- Optional toggle to show completed tasks with completion dates
- Task detail view accessible via click/tap

**UI Components**:
- Clean task list interface
- Task detail modal/panel
- Completion toggle control
- Visual indicators for task details

#### REFLECT Mode
**Purpose**: Review and analysis of productivity patterns

**Features**:
- Historical data visualization (completed tasks over time)
- Project completion timeline
- Weekly reflection templates with guided questions
- System-generated weekly reminders (Fridays at noon)
- User-initiated reflection sessions
- Goal progress assessment tools

**UI Components**:
- Data visualization charts
- Reflection question templates
- Historical timeline view
- Progress analytics dashboard

### 4. Pomodoro Timer Integration

#### Timer Features
- Available across all three modes
- Configurable work period duration
- Configurable short break duration
- Configurable long break duration
- Audio/visual notifications
- Cycle tracking

#### Timer Configuration
```
PomodoroSettings:
- workDuration: Integer (minutes, default: 25)
- shortBreakDuration: Integer (minutes, default: 5)
- longBreakDuration: Integer (minutes, default: 15)
- longBreakInterval: Integer (cycles, default: 4)
```

---

## Technical Requirements

### Architecture Overview
- **Pattern**: Contract-first, client-server application
- **API Design**: RESTful API with OpenAPI 3.0 specification
- **Client**: React-based single-page application
- **Server**: Java-based backend application
- **Database**: MongoDB for flexible schema and document storage

### Technology Stack

#### Frontend
- **Framework**: React (latest stable version)
- **Language**: TypeScript
- **State Management**: React Context API or Redux Toolkit
- **UI Library**: Material-UI or similar component library
- **Build Tool**: Vite or Turbopack
- **Testing**: Jest + React Testing Library

#### Backend
- **Framework**: Spring Boot 3.x
- **Language**: Java 21+
- **API Documentation**: OpenAPI 3.0 / Swagger
- **Database Driver**: Spring Data MongoDB
- **Authentication**: Spring Security with OAuth2
- **Testing**: JUnit 5 + Testcontainers

#### Database
- **Primary Database**: MongoDB
- **Rationale**: Document-based storage for flexible task/project schemas
- **Hosting**: MongoDB Atlas (cloud) or local MongoDB instance

#### Infrastructure
- **Deployment**: Docker containers
- **Web Server**: Nginx (for static asset serving)
- **SSL**: Let's Encrypt certificates
- **Monitoring**: Basic logging and health checks

### API Design Principles

#### RESTful Endpoints Structure
```
GET    /api/v1/tasks                    # Get all tasks
POST   /api/v1/tasks                    # Create task
PUT    /api/v1/tasks/{id}               # Update task
DELETE /api/v1/tasks/{id}               # Delete task
PATCH  /api/v1/tasks/{id}/complete      # Mark task complete
PATCH  /api/v1/tasks/{id}/reorder       # Reorder task

GET    /api/v1/projects                 # Get all projects
POST   /api/v1/projects                 # Create project
PUT    /api/v1/projects/{id}            # Update project
DELETE /api/v1/projects/{id}            # Delete project
PATCH  /api/v1/projects/{id}/position   # Update canvas position

GET    /api/v1/analytics/tasks          # Task completion analytics
GET    /api/v1/analytics/projects       # Project progress analytics

GET    /api/v1/pomodoro/settings        # Get timer settings
PUT    /api/v1/pomodoro/settings        # Update timer settings
```

### Data Persistence Strategy
- **Online Mode**: Real-time API synchronization
- **Offline Mode**: Local storage with background sync when connection restored
- **Conflict Resolution**: Last-write-wins for simplicity
- **Data Validation**: Client-side and server-side validation

### Authentication & Security
- **Primary**: Google OAuth 2.0
- **Session Management**: JWT tokens with refresh mechanism
- **Data Privacy**: User data isolation and GDPR compliance
- **API Security**: Rate limiting and input validation

---

## User Experience Design

### Design Principles
1. **Simplicity First**: Minimize cognitive load through clean interfaces
2. **Mode-Specific Design**: Each mode has distinct visual language
3. **Progressive Disclosure**: Show details only when needed
4. **Responsive Design**: Seamless experience across desktop and mobile
5. **Accessibility**: WCAG 2.1 AA compliance

### Visual Design Guidelines

#### PLAN Mode UI
- Canvas-style layout with project cards
- Drag-and-drop interactions
- Progress bars with clear percentage indicators
- Neutral color palette for focus
- Grid-based positioning system

#### ACT Mode UI
- Minimalistic list design
- High contrast for readability
- Large touch targets for mobile
- Visual indicators for task details
- Distraction-free environment

#### REFLECT Mode UI
- Data visualization with clear charts
- Warm color palette for reflection mood
- Guided question layouts
- Historical timeline representations
- Achievement highlighting

### Mobile Responsiveness
- Touch-optimized interactions
- Simplified navigation for smaller screens
- Swipe gestures for task completion
- Responsive canvas for project planning
- Mobile-first timer interface

### Navigation & Mode Switching
- **Primary Navigation**: Fixed mode selector (Plan/Act/Reflect)
- **Visual Indicators**: Clear mode identification
- **Transition Effects**: Smooth mode switching animations
- **Context Preservation**: Maintain user state across mode changes

---

## Success Metrics

### Engagement Metrics
- **Daily Active Users (DAU)**: Users engaging with ACT mode 3-4 times daily
- **Weekly Active Users (WAU)**: Users engaging with PLAN/REFLECT modes 1-3 times weekly
- **Session Duration**: Average time spent in each mode
- **Feature Adoption**: Usage rates for projects, reflection sessions, and timer

### Productivity Metrics
- **Task Completion Rate**: Percentage of created tasks marked as done
- **Project Progress**: Time from project creation to completion
- **Reflection Frequency**: Regular engagement with REFLECT mode
- **Planning Consistency**: Regular PLAN mode usage patterns

### Technical Metrics
- **Application Performance**: Page load times under 2 seconds
- **Offline Functionality**: Successful offline/online synchronization
- **Error Rates**: Minimal API errors and client-side failures
- **Mobile Usage**: Percentage of mobile vs. desktop usage

### User Satisfaction Metrics
- **User Retention**: 30-day and 90-day retention rates
- **Feature Feedback**: User satisfaction with three-mode workflow
- **Methodology Adoption**: Success in implementing Jedi Techniques
- **User Support**: Frequency and types of support requests

---

## Implementation Phases

### Phase 1: MVP (Minimum Viable Product)
**Goal**: Core functionality for all three modes

#### Features
- Basic task CRUD operations
- Simple project management
- Three-mode interface (basic versions)
- User authentication (Google OAuth)
- Basic Pomodoro timer
- Responsive web design

#### Success Criteria
- Users can create and complete tasks
- Users can switch between all three modes
- Basic offline functionality works
- Mobile experience is usable

### Phase 2: Enhanced Experience
**Goal**: Improved UX and advanced features

#### Features
- Enhanced canvas interface for PLAN mode
- Advanced reflection templates and reminders
- Historical data visualization
- Improved offline synchronization
- Performance optimizations
- User onboarding flow

#### Success Criteria
- Improved user engagement metrics
- Successful weekly reflection adoption
- Better mobile experience
- Reduced support requests

### Phase 3: Analytics & Polish
**Goal**: Data insights and user experience refinement

#### Features
- Advanced analytics dashboard
- Goal progress tracking
- Export functionality
- Advanced timer features
- UI/UX improvements based on user feedback
- Help center and video tutorials

#### Success Criteria
- Users actively use analytics features
- High user satisfaction scores
- Stable and performant application
- Comprehensive help resources

---

## Conclusion

Jedi organizer represents a focused approach to personal productivity applications, emphasizing methodology over features and simplicity over complexity. By implementing Maxim Dorofeev's Jedi Techniques in a digital format, the application aims to provide users with a structured yet flexible system for personal effectiveness.

The three-mode design (Plan/Act/Reflect) ensures that users can seamlessly transition between different productivity phases while maintaining focus and clarity. The technical architecture supports both current requirements and future scalability, enabling the product to grow with user needs.

Success will be measured primarily through user engagement and the effective adoption of the underlying productivity methodology, rather than through traditional feature usage metrics.

---

## Appendices

### Appendix A: Jedi Techniques Reference
- Link to Maxim Dorofeev's methodology documentation
- Key principles and their application in Jedi organizer
- Recommended reading and video resources

### Appendix B: Technical Architecture Diagrams
- System architecture overview
- Database schema design
- API endpoint documentation
- Authentication flow diagrams

### Appendix C: User Journey Maps
- New user onboarding flow
- Daily usage patterns
- Weekly planning and reflection cycles
- Task and project lifecycle management

---

*This document is a living specification that will be updated as requirements evolve and user feedback is incorporated.*
