# 📊 ChurnGuard Pro - Customer Churn Management & Analytics System

[![Java Version](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1-green.svg)](https://www.thymeleaf.org/)
[![Database](https://img.shields.io/badge/Database-H2%20In--Memory-blue.svg)](https://www.h2database.com/)
[![Bootstrap](https://img.shields.io/badge/UI-Bootstrap%205%20%2B%20Custom%20CSS-purple.svg)](https://getbootstrap.com/)

An enterprise-grade Customer Churn Analytics and Retention Management System designed for telecommunications service providers. Built with **Spring Boot 3**, **Java 21**, **Thymeleaf**, and **Chart.js**, featuring an in-memory H2 database with complete PostgreSQL compatibility.

---

## 🚀 Key Features

### 1. Executive Analytics Dashboard (`/dashboard` & `/`)
- **Key Metrics (KPIs):** Total customers, active customers, churn count, overall churn rate, average customer tenure, average monthly charges, total predictions run, and high-risk flags.
- **Interactive Visualizations:**
  - Dynamic churn distribution doughnut chart (Active vs. Churned).
  - Risk distribution bar chart (Low, Medium, High risk customer segments).
- **Recent Activity Feeds:** Quick access tables for newly enrolled accounts and recent churn assessments.

### 2. Comprehensive Customer Management (`/customers`)
- **Full CRUD Operations:** Add, inspect, update, and delete customer records.
- **Dynamic Search & Filtering:** Filter simultaneously by text search (name, ID, email), contract type, internet service category, and churn status.
- **Server-Side Sorting & Pagination:** Configurable page size and multi-column direction sorting.
- **Detailed Account Profiles (`/customers/{id}`):** Visualizes all 19 telecommunications customer attributes, payment methods, contract statuses, and historical churn predictions.

### 3. Transparent Churn Prediction Simulator (`/predictions/new`)
- **19-Feature Input Form:** Accepts all standardized telecommunications attributes:
  - *Demographics:* Gender, Senior Citizen, Partner, Dependents.
  - *Account:* Tenure, Contract Type, Paperless Billing, Payment Method, Monthly Charges, Total Charges.
  - *Services:* Phone Service, Multiple Lines, Internet Service (DSL / Fiber Optic / No), Online Security, Online Backup, Device Protection, Tech Support, Streaming TV, Streaming Movies.
- **Customer Pre-filling:** Auto-populate form inputs from any existing customer account with a single dropdown selection.
- **Transparent Rule-Based Scoring Engine:**
  - Clearly designated as a **DEMONSTRATION ESTIMATE**.
  - Weighted business rules mirroring actual industry attrition drivers (Month-to-Month contracts, lack of tech support, high monthly charges, and short tenure).
  - Configurable risk thresholds:
    - **Low Risk:** Probability < 40%
    - **Medium Risk:** Probability 40% – 69%
    - **High Risk:** Probability ≥ 70%
- **Prediction Result & Persistence:** Visual gauge, risk level indicator, breakdown of scoring factors, optional notes, and instant saving to database history.

### 4. Historical Prediction Log (`/predictions/history`)
- Complete audit trail of all churn predictions performed across customers.
- Search by customer name/ID, filter by risk level (Low/Medium/High), sort by probability or execution date.
- Dedicated detail view for each historical prediction record (`/predictions/history/{id}`).

### 5. Multi-Dimensional Churn Analytics (`/analytics`)
- Visual analysis powered by **Chart.js** grounded in actual database records:
  - Churn by Contract Type (Month-to-Month vs One-Year vs Two-Year).
  - Churn by Internet Service (DSL vs Fiber Optic vs None).
  - Churn by Customer Tenure Buckets (0-12m, 13-24m, 25-36m, 37-48m, 49-60m, 60m+).
  - Churn by Payment Method (Electronic Check, Mailed Check, Bank Transfer, Credit Card).
  - Churn by Monthly Charges Distribution.
  - Overall Contract and Demographic Distribution.

### 6. Planned Machine Learning Architecture (`/model-info`)
- Detailed architectural blueprint for the future offline-trained **XGBoost Classifier** and **FastAPI inference microservice**.
- Documentation of the 19 input dimensions and the adapter pattern implemented in Java (`PredictionService` decoupling).

### 7. Administrative Preferences & Dark Theme (`/settings`)
- Instant toggle between light enterprise dashboard theme and high-contrast dark mode.
- System administrator profile controls.
- Review of active probability risk bands.

---

## 🛠️ Architecture & Tech Stack

```
com.churnprediction.app/
├── config/
│   └── DataSeeder.java              # Populates 20 demonstration customers & predictions on launch
├── controller/
│   ├── DashboardController.java     # / and /dashboard routes
│   ├── CustomerController.java      # /customers CRUD routes
│   ├── PredictionController.java    # /predictions scoring & history routes
│   ├── AnalyticsController.java     # /analytics Chart.js routes
│   └── InfoController.java          # /model-info & /settings routes
├── dto/
│   ├── CustomerDto.java             # Form validation & persistence transfer
│   ├── DashboardStatsDto.java       # Aggregated KPIs
│   ├── PredictionDto.java           # Prediction transfer & display
│   └── PredictionFormDto.java       # Decoupled scoring form transfer
├── entity/
│   ├── Customer.java                # JPA Customer entity with enums & relations
│   └── Prediction.java              # JPA Prediction entity (Many-to-One with Customer)
├── exception/
│   ├── CustomerNotFoundException.java
│   ├── DuplicateCustomerIdException.java
│   └── GlobalExceptionHandler.java  # User-friendly HTTP error handlers
├── repository/
│   ├── CustomerRepository.java      # Custom aggregation & filter JPQL queries
│   └── PredictionRepository.java    # Query operations for prediction history
└── service/
    ├── CustomerService.java         # Interface for customer operations
    ├── CustomerServiceImpl.java     # Transactional customer CRUD logic
    ├── PredictionService.java       # Prediction orchestration interface
    ├── PredictionServiceImpl.java   # Prediction persistence logic
    ├── DemoPredictionService.java   # Isolated transparent rule scoring engine
    ├── DashboardService.java        # KPI statistics aggregation
    └── AnalyticsService.java        # Multi-dimensional analytics queries
```

---

## 💻 Quick Start & Execution

### Prerequisites
- **Java 21** or later (Java 21+ supported)
- **Apache Maven 3.8+** (or use the included binary in this workspace)

### 1. Build the Application
Navigate to the project directory:
```bash
cd /Users/aryanshinde/.gemini/antigravity/scratch/churn-prediction-app
```

Run compilation and automated tests:
```bash
/Users/aryanshinde/.gemini/antigravity/scratch/apache-maven-3.9.6/bin/mvn clean test
```

### 2. Start the Server
Launch the Spring Boot application:
```bash
/Users/aryanshinde/.gemini/antigravity/scratch/apache-maven-3.9.6/bin/mvn spring-boot:run
```

*(Alternatively, if Maven is in your system PATH, simply run `mvn spring-boot:run`)*

### 3. Access the Web Application
Once started, open your web browser to:

| Destination | URL |
| :--- | :--- |
| **Main Dashboard** | [http://localhost:8080/](http://localhost:8080/) |
| **Customer Management** | [http://localhost:8080/customers](http://localhost:8080/customers) |
| **Prediction Simulator** | [http://localhost:8080/predictions/new](http://localhost:8080/predictions/new) |
| **Prediction History** | [http://localhost:8080/predictions/history](http://localhost:8080/predictions/history) |
| **Analytics Dashboard** | [http://localhost:8080/analytics](http://localhost:8080/analytics) |
| **Model Information** | [http://localhost:8080/model-info](http://localhost:8080/model-info) |
| **Settings & Preferences** | [http://localhost:8080/settings](http://localhost:8080/settings) |
| **H2 Database Console** | [http://localhost:8080/h2-console](http://localhost:8080/h2-console) |

#### H2 Database Console Credentials
- **JDBC URL:** `jdbc:h2:mem:churndb`
- **User Name:** `sa`
- **Password:** `password`

---

## 🧪 Demonstration Data
On initial startup, `DataSeeder.java` seeds **20 realistic telecommunication accounts** with varying tenures, contract tiers, payment formats, and initial historical predictions. All demonstration records are badged with a purple `Demo` label for clear identification.
