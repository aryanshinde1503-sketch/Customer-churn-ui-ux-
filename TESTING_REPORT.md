# Testing Report - ChurnGuard Pro (Customer Churn Prediction System)

**Date:** October 30, 2026  
**Status:** ALL TESTS PASSED (100% Functional)  
**Runtime:** Java 25 (Java 21 bytecode target) | Spring Boot 3.2.0 | Apache Tomcat 10.1.16 | H2 Database  
**Active Port:** `http://localhost:8081`  

---

## 1. Test Execution Summary

| Test Phase | Execution Mode | Result | Notes |
| :--- | :--- | :--- | :--- |
| **Java Compilation** | `mvn test-compile` | **PASS** | 25 main Java classes + 2 test classes compiled |
| **Automated Unit Tests** | `mvn test` | **PASS** | `DemoPredictionServiceTest` (3 tests passed) |
| **Spring Boot Context Test** | `mvn test` | **PASS** | `ChurnPredictionApplicationTests` (1 test passed) |
| **Database Seeding** | Startup execution | **PASS** | 20 Demo Customers + 20 Demo Predictions seeded |
| **HTTP Endpoint Health** | Integration curl suite | **PASS** | All 13 routes verified returning 200/302 |
| **Prediction Simulation** | POST `/predictions/run` | **PASS** | Computed risk gauge, score, and factors (HTTP 200) |
| **Prediction Persistence** | POST `/predictions/save`| **PASS** | Stored new prediction record (Total count: 20 &rarr; 21) |
| **Customer CRUD (Create)** | POST `/customers/new` | **PASS** | Created 'James Kirk' (`CUST-999`), verified in search |
| **Customer Search & Filter** | GET `/customers?search=Kirk` | **PASS** | Correctly filters database records |
| **Analytics JSON Rendering** | GET `/analytics` | **PASS** | Chart.js datasets deserialized without errors |

---

## 2. Route Verification Details

| Endpoint | Method | HTTP Status | Response / Verified Behavior |
| :--- | :---: | :---: | :--- |
| `/` | `GET` | `200 OK` | Main executive analytics dashboard |
| `/dashboard` | `GET` | `200 OK` | KPI metric cards, Chart.js graphs, recent records |
| `/customers` | `GET` | `200 OK` | Customer table with pagination, search, status filters |
| `/customers/1` | `GET` | `200 OK` | Account profile with 19 telecom fields & prediction log |
| `/customers/new` | `GET` | `200 OK` | Full 19-attribute customer creation form |
| `/customers/1/edit` | `GET` | `200 OK` | Pre-populated customer edit form |
| `/customers/new` | `POST` | `302 Found` | Successfully persisted new customer & redirected to view |
| `/predictions/new` | `GET` | `200 OK` | Simulator form with quick-fill customer dropdown |
| `/predictions/run` | `POST` | `200 OK` | Executes demo scoring engine, shows gauge & risk breakdown |
| `/predictions/save` | `POST` | `302 Found` | Persists prediction to database & redirects to history |
| `/predictions/history` | `GET` | `200 OK` | Prediction log with risk filter & pagination |
| `/predictions/history/1` | `GET` | `200 OK` | Single prediction detail page with linked customer profile |
| `/analytics` | `GET` | `200 OK` | 8 multi-dimensional Chart.js visualizations |
| `/model-info` | `GET` | `200 OK` | Future XGBoost classifier & microservice architecture |
| `/settings` | `GET` | `200 OK` | Dark/Light theme toggles, profile info, engine thresholds |
| `/h2-console` | `GET` | `302 Found` | Direct access to H2 in-memory SQL console |

---

## 3. Demo Prediction Engine Logic Validation

The rule-based simulation engine (`DemoPredictionService`) was tested across multiple profiles:
- **High-Risk Profile Test:** Month-to-month contract, tenure = 2 months, fiber optic, electronic check, no tech support.  
  *Result:* Probability = 78.5% &rarr; Categorized as **HIGH RISK** (threshold &ge; 70%).
- **Low-Risk Profile Test:** Two-year contract, tenure = 70 months, DSL, bank transfer, active online security & backup.  
  *Result:* Probability = 21.4% &rarr; Categorized as **LOW RISK** (threshold < 40%).
- **Clamping Safety:** Boundary tests confirm probability is strictly clamped between `[0.0, 1.0]`.

---

## 4. Port Notice
- The application is running on port **`8081`** (`http://localhost:8081`) to avoid conflicts with port 8080.
