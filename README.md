# Challenge: Java and JavaScript. Programming Procedures (BookingMx)

This repository contains the solution for the Digital NAO Challenge, focusing on implementing a robust unit testing strategy for the BookingMx website.

This project demonstrates a Proficient (C2) level approach, prioritizing system resilience, code maintainability, and a scalable testing model.

## Sprint 1: Reservations Module (Java with JUnit)

Implementation of a unit test suite for the Java reservations module, achieving **95% code coverage** (exceeding the 90% target).

### Testing Strategy (C2 Approach)

To ensure true, isolated unit tests, a **Dependency Injection (DI)** pattern was implemented.

1.  **`ReservationRepository` (Interface):** Defines the "contract" for how the service communicates with the database.
2.  **`ReservationService` (Class):** Depends on the *interface*, not a concrete class.
3.  **`ReservationServiceTest` (Test):** Uses **Mockito** to create a *mock* of the `ReservationRepository`. This allows us to test the business logic (`createReservation`, `cancelReservation`) in total isolation, with no need for a live database.

### How to Review and Run the Tests

This project is built with **Apache Maven**, ensuring it runs on any OS (macOS, Windows, Linux) and any IDE (VS Code, IntelliJ).

**Requirements:**
* Java JDK 11 (or higher)
* Apache Maven

**Steps to Run:**

1.  Clone the repository:
    ```bash
    git clone [https://github.com/Diego2gonzalez/BookingMx-UnitTests.git](https://github.com/Diego2gonzalez/BookingMx-UnitTests.git)
    cd BookingMx-UnitTests
    ```

2.  Run the test suite and generate the coverage report:
    ```bash
    mvn clean test
    ```

3.  Verify the results:
    * The terminal must show **[INFO] BUILD SUCCESS** and **Tests run: 7, Failures: 0**.

4.  **(Optional) View the Visual Coverage Report:**
    * After running `mvn clean test`, open the following file in your browser:
    * `target/site/jacoco/index.html`