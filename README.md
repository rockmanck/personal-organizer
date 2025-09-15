# personal-organizer
Opinionated implementation of Jedi's Techniques by Max Dorofeev

## 1. Prerequisites
Install / have available:
- Docker (24+) & Docker Compose plugin
- Node.js 18 LTS (only if you want to run frontend locally outside Docker)
- Java 21 (Temurin) + Gradle Wrapper (backend already includes wrapper)
- Make sure ports 80, 3000, 8080, 27017 are free

## 2. Quick Start (Full Stack with Docker Compose)
This spins up MongoDB, Backend (Spring Boot), Frontend (Vite dev server with HMR), and Nginx reverse proxy.

```bash
git clone <repo-url>
cd personal-organizer
# (Optional) create .env with OAuth placeholders (see below)
docker compose up --build
```

Access:
- Unified entry (via Nginx): http://localhost
- Frontend dev server (direct, faster HMR): http://localhost:3000
- Backend API / OpenAPI UI: http://localhost:8080/api/v1/swagger-ui.html
- Health check (simple): http://localhost:8080/api/v1/health
- Actuator health: http://localhost:8080/api/v1/actuator/health
- MongoDB: mongodb://admin:password@localhost:27017 (db: jedi_organizer, authSource: admin)

Hot Reload:
- Frontend: File changes under `frontend/` trigger instant HMR reload via Vite.
- Backend: Spring DevTools restarts app on classpath changes (gradle `bootRun` inside container). Save Java/Kotlin files to trigger reload.

Stopping & Cleanup:
```bash
docker compose down          # stop containers
docker compose down -v       # stop and remove volumes (will wipe Mongo data)
```

## 3. Environment Variables
Docker Compose references optional Google OAuth variables. Create a `.env` file in the project root (same level as docker-compose.yml).

Example `.env` (do NOT commit real secrets):
```dotenv
GOOGLE_OAUTH_CLIENT_ID=your-google-client-id.apps.googleusercontent.com
GOOGLE_OAUTH_CLIENT_SECRET=your-google-client-secret
```
If omitted, the backend may run with auth shortcuts (dev mode). Replace with real credentials when implementing auth flow.

## 4. Service Overview (Compose Names)
| Service            | Container Name              | Port(s) | Purpose |
|--------------------|-----------------------------|---------|---------|
| MongoDB            | jedi-organizer-mongo        | 27017   | Data store |
| Backend (Spring)   | jedi-organizer-backend      | 8080    | REST API / OpenAPI |
| Frontend (Vite)    | jedi-organizer-frontend     | 3000    | React dev server |
| Nginx (Reverse Proxy)| jedi-organizer-nginx      | 80      | Consolidated entry |

## 5. Local Development Without Full Docker Stack
Useful if you want faster backend / frontend iteration while still using a containerized MongoDB.

Start only MongoDB:
```bash
docker compose up -d mongodb
```

Backend (host machine):
```bash
cd backend
./gradlew bootRun
```
(Ensure `SPRING_DATA_MONGODB_URI` is set if you changed defaults. Current code uses the value from compose; for local run export:)
```bash
export SPRING_DATA_MONGODB_URI="mongodb://admin:password@localhost:27017/jedi_organizer?authSource=admin"
./gradlew bootRun
```

Frontend (host machine):
```bash
cd frontend
npm ci
npm run dev
```
Set a custom API base URL if needed (defaults from compose):
```bash
export VITE_API_BASE_URL=http://localhost:8080/api/v1
```

Then open http://localhost:3000

## 6. Running Tests
Backend (JUnit + JaCoCo):
```bash
cd backend
./gradlew test
```
Coverage report: `backend/build/reports/jacoco/test/html/index.html`

Frontend (Vitest):
```bash
cd frontend
npm test              # run unit tests
npm run test:coverage # with coverage
```
Vitest UI (optional):
```bash
npm run test:ui
```

## 7. Common Tasks
Rebuild backend after dependency changes:
```bash
docker compose build backend && docker compose up -d backend
```
Install new frontend dependency (with running container):
```bash
docker compose exec frontend npm install <pkg>
```
Generate production frontend build (locally):
```bash
cd frontend
npm run build
```
Jar build (backend):
```bash
cd backend
./gradlew clean build
```
Artifacts appear in `backend/build/libs/`.

## 8. Troubleshooting
| Problem | Symptom | Fix |
|---------|---------|-----|
| Port in use | `bind: address already in use` | Free or change host port in docker-compose.yml |
| Backend cannot reach Mongo | Connection refused / auth errors | Ensure mongodb container healthy; check credentials & URI |
| OAuth redirect issues | 400 from Google | Verify authorized redirect URIs in Google Console |
| Frontend API 404 | Calls to /api/v1/* fail | Confirm backend running & correct VITE_API_BASE_URL |
| DevTools not reloading | Code changes ignored | Ensure volume mount intact; confirm gradle daemon not stuck; restart backend container |

Check container logs:
```bash
docker compose logs -f backend
```

## 9. Security Notes
- Do NOT commit real OAuth secrets.
- Use separate credentials for development.
- Rotate credentials before public deployment.

---
For detailed architecture, see `docs/` and `IMPLEMENTATION_PLAN.md`.
