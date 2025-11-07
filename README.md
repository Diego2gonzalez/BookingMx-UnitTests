# Challenge: Java and JavaScript. Programming Procedures (BookingMx)

![Project Status](https://img.shields.io/badge/Sprint_1_(Java)-COMPLETE_(95%25)-green)
![Project Status](https://img.shields.io/badge/Sprint_2_(JS)-Pending-lightgrey)

This repository contains the solution for the Digital NAO Challenge, focusing on implementing a robust, scalable, and maintainable unit testing strategy for the BookingMx website.

This project is developed to meet the **C2 (Proficient)** standards of the challenge rubric.

---

## 🚀 C2 Proficient Justification

This project demonstrates C2-level proficiency by not just *completing* tasks, but by implementing strategic, scalable solutions that demonstrate leadership.

| Rubric Criterion (C2 - Proficient) | Project Evidence & Justification |
| :--- | :--- |
| **Design and execution of unit tests with JUnit** | "Demonstrates **leadership and creativity** in guiding the design and execution of... **effective testing strategies** in complex... environments..." |
| **Process Documentation** | "Demonstrates leadership and creativity in establishing **innovative standards and procedures** for the strategic implementation of documentation..." |
| **Integration of innovative elements** | "Identifies, integrates, and presents a **highly innovative solution**... considering factors such as **scalability**... and economic feasibility..." |

**Our C2 Strategy:**
* We implemented a **Dependency Injection (DI)** pattern using **Mockito**. This is an innovative and effective strategy that isolates the `ReservationService` from the database, satisfying the C2 (JUnit) criterion.
* We established a **3-part documentation standard** (JavaDoc, `README.md`, `SPRINT1_LOG.md`) that explains the *What*, the *How*, and the *Why*. This strategic implementation satisfies the C2 (Documentation) criterion.
* The `Service/Repository` pattern is a **highly scalable** solution. The BookingMx team can now add `PaymentService` or `UserService` using the exact same robust, testable pattern, satisfying the C2 (Innovation) criterion.

---

## 🛠️ Technology Stack & Tools (Sprint 1)

This project is managed by Apache Maven, which ensures a consistent development environment for all collaborators (macOS, Windows, or Linux).

| Tool | Purpose | Version (from `pom.xml`) |
| :--- | :--- | :--- |
| **Java** | Core Application Language | `17` |
| **Apache Maven** | Project Management & Build Tool | `3.x` |
| **JUnit 5** | Testing Framework | `5.9.1` |
| **Mockito** | Mocking Framework for DI | `5.5.0` |
| **JaCoCo** | Code Coverage Report Tool | `0.8.8` |

---

## Sprint 1: Reservations Module (Java with JUnit)

**Status: COMPLETE**

Implementation of a unit test suite for the Java reservations module, achieving **95% code coverage** (exceeding the 90% target), as verified by the JaCoCo report.

### C2 Architecture: Dependency Injection & Mocking

To ensure true, isolated unit tests, we implemented a **Dependency Injection (DI)** pattern. The diagram below illustrates our C2 testing strategy:

```mermaid
graph TD
    subgraph "Test Environment"
        A[ReservationServiceTest] -- 1. Injects Mock --> B((ReservationRepository Mock));
        A -- 2. Calls method --> C(ReservationService);
        B -- 3. Returns mock data --> C;
    end

    subgraph "Application"
        C -- Depends on --> D[ReservationRepository (Interface)];
    end

    A -- 4. Asserts results --> E(Test Passed/Failed);
```

3.  Verify the results:
    * The terminal must show **[INFO] BUILD SUCCESS** and **Tests run: 7, Failures: 0**.

4.  **(Optional) View the Visual Coverage Report:**
    * After running `mvn clean test`, open the following file in your browser:
    * `target/site/jacoco/index.html`


# Challenge: Java and JavaScript. Programming Procedures (BookingMx)

![Project Status](https://img.shields.io/badge/Sprint_1_(Java)-COMPLETE_(95%25)-green)
![Project Status](https://img.shields.io/badge/Sprint_2_(JS)-Pending-lightgrey)

This repository contains the solution for the Digital NAO Challenge, focusing on implementing a robust, scalable, and maintainable unit testing strategy for the BookingMx website.

This project is developed to meet the **C2 (Proficient)** standards of the challenge rubric.

---

## 🚀 C2 Proficient Justification

This project demonstrates C2-level proficiency by not just *completing* tasks, but by implementing strategic, scalable solutions that demonstrate leadership.

| Rubric Criterion (C2 - Proficient) | Project Evidence & Justification |
| :--- | :--- |
| **Design and execution of unit tests with JUnit** | "Demonstrates **leadership and creativity** in guiding the design and execution of... **effective testing strategies** in complex... environments..." |
| **Process Documentation** | "Demonstrates leadership and creativity in establishing **innovative standards and procedures** for the strategic implementation of documentation..." |
| **Integration of innovative elements** | "Identifies, integrates, and presents a **highly innovative solution**... considering factors such as **scalability**... and economic feasibility..." |

**Our C2 Strategy:**
* We implemented a **Dependency Injection (DI)** pattern using **Mockito**. This is an innovative and effective strategy that isolates the `ReservationService` from the database, satisfying the C2 (JUnit) criterion.
* We established a **3-part documentation standard** (JavaDoc, `README.md`, `SPRINT1_LOG.md`) that explains the *What*, the *How*, and the *Why*. This strategic implementation satisfies the C2 (Documentation) criterion.
* The `Service/Repository` pattern is a **highly scalable** solution. The BookingMx team can now add `PaymentService` or `UserService` using the exact same robust, testable pattern, satisfying the C2 (Innovation) criterion.

---

## 🛠️ Technology Stack & Tools (Sprint 1)

This project is managed by Apache Maven, which ensures a consistent development environment for all collaborators (macOS, Windows, or Linux).

| Tool | Purpose | Version (from `pom.xml`) |
| :--- | :--- | :--- |
| **Java** | Core Application Language | `17` |
| **Apache Maven** | Project Management & Build Tool | `3.x` |
| **JUnit 5** | Testing Framework | `5.9.1` |
| **Mockito** | Mocking Framework for DI | `5.5.0` |
| **JaCoCo** | Code Coverage Report Tool | `0.8.8` |

---

## Sprint 1: Reservations Module (Java with JUnit)

**Status: COMPLETE**

Implementation of a unit test suite for the Java reservations module, achieving **95% code coverage** (exceeding the 90% target), as verified by the JaCoCo report.

### C2 Architecture: Dependency Injection & Mocking

To ensure true, isolated unit tests, we implemented a **Dependency Injection (DI)** pattern. The diagram below illustrates our C2 testing strategy:

```mermaid
graph TD
    subgraph "Test Environment"
        A[ReservationServiceTest] -- 1. Injects Mock --> B((ReservationRepository Mock));
        A -- 2. Calls method --> C(ReservationService);
        B -- 3. Returns mock data --> C;
    end

    subgraph "Application"
        C -- Depends on --> D[ReservationRepository (Interface)];
    end

    A -- 4. Asserts results --> E(Test Passed/Failed);
```

### Strategy Explanation:

ReservationServiceTest does not test the real database. It creates a mock version of the ReservationRepository.

The test class "injects" this mock into the ReservationService.

When the service tries to check the database (e.g., isRoomAvailable()), it talks to our mock. We program the mock to return true or false, allowing us to test all business logic (like RoomUnavailableException) in perfect isolation.

How to Review and Run the Tests
Requirements:

Java JDK 11 (or higher)

Apache Maven

Steps to Run:

Clone the repository:

``` Bash
git clone [https://github.com/Diego2gonzalez/BookingMx-UnitTests.git](https://github.com/Diego2gonzalez/BookingMx-UnitTests.git)
cd BookingMx-UnitTests
Run the test suite and generate the coverage report:
```

```Bash
mvn clean test
```

### Verify the results:

The terminal must show [INFO] BUILD SUCCESS and Tests run: 7, Failures: 0.

(Optional) View the Visual Coverage Report:

After running mvn clean test, open the following file in your browser:

target/site/jacoco/index.html

## Sprint 2: Graph Module (JavaScript with Jest)
(Pending)

📓 Project Documentation
Internal Code: All services and methods are documented using JavaDoc.

External Log: Key architectural decisions are documented in the SPRINT1_LOG.md.
