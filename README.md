# 🏛️ Architecture Validation Engine (AVE) Microservice Project

The Architecture Validation Engine (AVE) is a microservices-based system designed to prevent **cascading failures** in complex service-oriented architectures. By maintaining a secure, **graph-based ledger** of service dependencies, AVE can instantly predict the full impact (**blast radius**) of any single service outage using a **Reverse Depth-First Search (Reverse DFS)** algorithm.

## 🎯 Project Goal

The primary goal is to provide **real-time, predictive insight** into architectural integrity, drastically reducing Mean Time To Recovery (**MTTR**) during critical incidents by shifting incident response from reactive tracing to **proactive prediction**.

---

## 🧱 Architecture and Technology Stack

This project uses a **polyglot microservice architecture** with three distinct services residing in separate folders:

| Service Folder      | Primary Technology      | Core Functionality                                                                                                                            | Data Handling / Port                 |
| :------------------ | :---------------------- | :-------------------------------------------------------------------------------------------------------------------------------------------- | :----------------------------------- |
| `core-registry/`    | **Java Spring Boot 21** | **The Ledger & DSA Engine.** Stores the immutable dependency graph (Nodes/Edges) and executes the **Reverse DFS** algorithm.                  | MySQL (**8080**)                     |
| `realtime-gateway/` | **Node.js (Express)**   | **API Gateway & Real-Time Data.** Ingests health metrics, manages Socket.IO broadcasting, and routes prediction requests to the Java service. | MongoDB (**4000**)                   |
| `ave-frontend/`     | **React / TypeScript**  | **Dashboard.** Real-time health visualization and a user interface to trigger and display failure predictions.                                | Socket.IO (Client) & HTTP (**3000**) |

---

## 🛠️ Setup and Running the Project

The easiest way to run the entire system is by using **Docker Compose**, which handles networking and startup order for all three services and their corresponding databases (MySQL and MongoDB).

### Prerequisites

1.  **Docker** and **Docker Compose** installed on your system.
2.  Basic knowledge of **Postman** for initial data seeding.

### Step 1: Ensure Docker Setup is Correct

Verify that your `docker-compose.yml` file is correctly configured for **MySQL** (using image `mysql:8.0` on port `3306`) and that all services correctly reference each other by their service names (e.g., Node.js refers to Java as `http://core-registry:8080`).

### Step 2: Build and Run

Navigate to the project root directory (`AVE-Project/`) and execute:

```bash
# 1. Build all custom images (Java, Node, React)
docker-compose build

# 2. Start all services in detached mode
docker-compose up -d
```

### Step 3: Access and Verification

1. Frontend Dashboard: Open your browser to `http://localhost:3000`.

2. Java Core Registry: `http://localhost:8080` (Internal API)

3. Node.js Gateway: `http://localhost:4000` (Public API)

### 🚀 Testing the End-to-End Flow (Postman)

The system is empty upon startup and requires dependency data before prediction can work.

### Phase A: Seed the Dependency Graph (Java Core Registry)

Create Services (Nodes):

Method: `POST`

URL: `http://localhost:8080/api/v1/services`

Action: Create services like "Auth-Service," "Billing-Service," etc. (e.g., `{"name": "Auth-Service", "ownerId": "MarkC"}`)

Create Dependencies (Edges):

Method: `POST`

URL: `http://localhost:8080/api/v1/dependencies`

Example: To model "Order requires Auth," send `{"requesterId": 3, "providerId": 1}`.

### Phase B: Test Real-Time Health (Node.js Gateway)

Simulate Status Update:

Method: `POST`

URL: `http://localhost:4000/api/v1/health-ingest`

Example:` {"serviceId": "1", "status": "OUTAGE", "latencyMs": 5000}`

Verification: The dashboard on `http://localhost:3000 `should instantly update the service status to OUTAGE via Socket.IO.

### Phase C: Run Failure Prediction (Integration Test)

Execute Reverse DFS:

Method: `GET`

URL: `http://localhost:4000/api/v1/predict-outage/1` (Using the ID of the service you want to simulate failing).

Verification: The response will contain a JSON array listing all services transitively impacted.
