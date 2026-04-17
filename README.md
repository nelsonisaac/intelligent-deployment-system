# Intelligent Deployment System 🚀

## 📌 Overview

A backend-driven deployment automation system that simulates real-world deployment workflows using Blue-Green and Canary strategies.

The system manages deployment lifecycle, executes deployment scripts, and performs automated rollback on failure.

---

## ⚙️ Tech Stack

### Backend

* Java + Spring Boot
* Spring Data JPA
* SQLite (for rapid development)

### DevOps Simulation

* Shell Scripts (deploy & rollback)
* ProcessBuilder (Java execution layer)

---

## 🧠 Core Features

### ✅ Deployment Lifecycle Management

Implements a state machine:
PENDING → DEPLOYING → SUCCESS
FAILED → ROLLED_BACK

---

### ✅ Script-Based Deployment Execution

* Executes deployment scripts via Java
* Streams logs during execution
* Determines success/failure based on exit code

---

### ✅ Automated Rollback

* Automatically triggers rollback on failure
* Ensures system stability

---

## 🌐 APIs

### Create Deployment

POST /api/v1/deployments/create

Query Params:

* serviceName
* version

---

### Start Deployment

POST /api/v1/deployments/{id}/start

---

## 🔄 Deployment Flow

1. Create deployment request
2. System sets status → PENDING
3. Start deployment
4. Status → DEPLOYING
5. Execute deploy.sh
6. If success → SUCCESS
   If failure → rollback → ROLLED_BACK

---

## 📁 Project Structure

backend/ → Spring Boot application
scripts/ → Deployment & rollback scripts
frontend/ → (to be implemented)
docs/ → Architecture diagrams (planned)

---

## 🚧 Current Status

✅ Backend setup complete
✅ Deployment state machine implemented
✅ Script execution integrated

⏳ Next:

* Blue-Green deployment using Nginx
* Docker integration
* React dashboard with WebSockets

---

## 🧠 Key Learnings

* State machine design for deployment lifecycle
* Script orchestration using Java
* Handling failure and rollback strategies
* Debugging real-world Hibernate + SQLite issues

---

## 🚀 Future Enhancements

* Canary deployment strategy
* Real-time monitoring dashboard
* Kubernetes integration
* Metrics-based rollback decisions
