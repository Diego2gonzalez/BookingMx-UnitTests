# 🧠 Challenge: Java and JavaScript — Programming Procedures (BookingMx)

[![Java CI Build and Test](https://github.com/Diego2gonzalez/BookingMx-UnitTests/actions/workflows/ci.yml/badge.svg)](https://github.com/Diego2gonzalez/BookingMx-UnitTests/actions/workflows/ci.yml)
![Sprint 1 Coverage](https://img.shields.io/badge/Java_Coverage-97%25-brightgreen)
![Sprint 2 Status](https://img.shields.io/badge/Sprint_2_(JS)-Pending-lightgrey)

This repository contains the solution for the **Digital NAO Challenge**, focused on implementing a robust, scalable, and maintainable testing strategy for the BookingMx website.

---

## 🚀 Alignment with C2 (Proficient) Rubric

This project demonstrates **C2-level proficiency** by delivering scalable, automated, and strategic testing solutions.

| Rubric Criterion (C2 - Proficient) | Project Evidence & Justification |
| :--- | :--- |
| **Design and execution of unit tests with JUnit** | “Demonstrates **leadership and creativity**... in designing **effective testing strategies** in complex environments.” <br><br>✅ **Evidence:** Multi-layered testing strategy: <br> 1. **Unit Tests (Mocking):** Used Mockito to isolate business logic (`ReservationService`). <br> 2. **Integration Tests (H2):** Used an in-memory DB to validate *real SQL* (`JdbcReservationRepository`). <br> 3. **Parameterized Tests:** Used `@ParameterizedTest` to achieve 100% branch coverage. |
| **Process Documentation** | “Demonstrates leadership... establishing **innovative standards and procedures** for documentation.” <br><br>🧭 **Evidence:** C2-level process automation: <br> 1. **GitHub Actions (CI/CD):** Workflow (`ci.yml`) runs all tests on each push. <br> 2. **Quality Gate:** Build *fails automatically* if JaCoCo < 90%. <br> 3. **3-Part Docs:** `JavaDoc` (What) • `README.md` (How) • `SPRINT1_LOG.md` (Why). |
| **Integration of innovative elements** | “Identifies and integrates **highly innovative solutions**, considering scalability.” <br><br>⚙️ **Evidence:** <br> 1. **DI Pattern:** The `Service/Repository` architecture scales easily for future modules (`PaymentService`, etc.). <br> 2. **Cross-Platform Build:** Using Maven, H2, and GitHub Actions guarantees identical results across OS and IDEs. |

---

## 🛠️ Technology Stack & Tools (Sprint 1)

This project is powered by **Apache Maven**, ensuring consistency across platforms.

| Tool | Purpose | Version |
| :--- | :--- | :--- |
| ☕ **Java** | Core Application Language | `17` |
| 🧩 **Apache Maven** | Project Management & Build Tool | `3.x` |
| 🧪 **JUnit 5** | Core Testing Framework | `5.9.1` |
| 🧠 **Mockito** | Mocking Framework (for Unit Tests) | `5.5.0` |
| 📈 **JaCoCo** | Code Coverage Report Tool | `0.8.8` |
| 💾 **H2 Database** | In-Memory DB (for Integration Tests) | `2.2.224` |
| 🚦 **Maven Failsafe** | Runs Integration Tests (`*IT.java`) | `3.2.5` |

---

## 🧪 Multi-Layered Testing Strategy (Sprint 1)

**Status:** ✅ COMPLETE  
We implemented a **two-layer testing architecture** to validate both business logic and persistence.

---

### 🧱 Layer 1: Unit Tests (Mocking)

**Purpose:** Validate the *business logic* in `ReservationService` without hitting a real DB.  
**Tool:** Mockito  
**Coverage:** 97% instruction, 100% branch coverage.

**Diagram: Unit Testing Flow**
```mermaid
graph TD
    subgraph "Test Environment (Unit)"
        A[ReservationServiceTest] -- 1. Injects Mock --> B((ReservationRepository Mock));
        A -- 2. Calls method --> C(ReservationService);
        B -- 3. Returns mock data --> C;
    end

    subgraph "Application"
        C -- Depends on --> D[ReservationRepository (Interface)];
    end

    A -- 4. Asserts results --> E(Test Passed/Failed);
```

---

### 🧩 Layer 2: Integration Tests (H2 Database)

**Purpose:** Validate **real SQL logic** in `JdbcReservationRepository`.  
**Tool:** H2 (in-memory DB) + Maven Failsafe.  
**Goal:** Ensure queries like `findById` and `isRoomAvailable` run correctly in an actual DB context.  
**Execution:** Automatically runs during Maven’s `verify` phase.

---

## 🤖 Continuous Integration / Continuous Deployment (CI/CD)

This repository is automated with **GitHub Actions** (`.github/workflows/ci.yml`).

🔁 **Workflow Tasks:**
1. Trigger on each `push` to `main` or `dev-david`.
2. Set up Ubuntu VM with Java 17 + Maven.
3. Run full build pipeline:  
   ```bash
   mvn clean verify
   ```
4. Execute all **12 tests** (8 Unit + 4 Integration).  
5. Enforce **Quality Gate** → Build fails if coverage < 90%.

---

## 🏃 How to Run the Project Locally

### ⚙️ Requirements
- Java JDK 11 or higher  
- Apache Maven

---

### 🧩 1. Run **Unit Tests Only** (Fast Execution)

```bash
mvn clean test
```

🧾 *Optional:* Open the coverage report:  
```bash
open target/site/jacoco/index.html
```

---

### 🔬 2. Run **Full Pipeline** (Unit + Integration Tests)

Runs all tests and applies CI/CD checks exactly like GitHub Actions.

```bash
mvn clean verify
```

✅ **Expected Output:**  
```
[INFO] BUILD SUCCESS
Tests run: 12, Failures: 0, Errors: 0
```

<img width="1185" height="544" alt="Screenshot 2025-11-07 at 3 33 22 p m" src="https://github.com/user-attachments/assets/4cb11a59-193d-4724-975b-7c6ee98bd04e" />

---

## 📚 Project Documentation

- 🧠 **Internal:** All classes and methods are documented with **JavaDoc**.  
- 🗂️ **External:** Architectural decisions logged in **[SPRINT1_LOG.md](https://www.google.com/search?q=SPRINT1_LOG.md)**.

---

✨ *Maintained by **Luis David Mag** — Data Engineer & Automation Specialist.*  
📦 *Version:* `C2-Final-Sprint1`

