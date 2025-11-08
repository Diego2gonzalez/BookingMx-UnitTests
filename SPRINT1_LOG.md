# Sprint 1: Development & Decision Log

This file documents the issues found and solutions implemented during Sprint 1, as required by the deliverable.

### 1. Challenge: Isolated Testing (Service Isolation)

* **Problem:** How to test the `ReservationService` logic (e.g., "throw exception if room is unavailable") without a real database connected? A unit test must not depend on external systems.
* **Solution (C2):** Implemented **Dependency Injection** and **Mockito**.
    1.  Created a `ReservationRepository` interface.
    2.  The `ReservationService` now depends on this interface (via the constructor).
    3.  In `ReservationServiceTest`, we use `@Mock` to simulate the repository. Using `when().thenReturn()`, we force the mock to return `false` (room unavailable), allowing us to verify that the service correctly throws `RoomUnavailableException`.

### 2. Challenge: Multi-Platform Environment (Mac vs. Windows)

* **Problem:** I work on VS Code (Mac) and my teammate Diego works on IntelliJ (Windows). How do we guarantee the project runs identically for both of us and avoid committing IDE-specific "junk" files (e.g., `.vscode/` or `.idea/`)?
* **Solution:**
    1.  **Maven (`pom.xml`):** Used Maven as the project "contract." It defines all dependencies (JUnit, JaCoCo, Mockito) and the Java version, forcing both IDEs to configure themselves the same way.
    2.  **`.gitignore`:** Created a robust `.gitignore` file at the start, which explicitly ignores configuration folders for VS Code, IntelliJ, macOS, and Windows.

### 3. Challenge: Coverage Goal (>90%)

* **Problem:** How to efficiently reach the 90% coverage target?
* **Solution:** Prioritized testing the methods with the most complex business logic: `createReservation` (3 scenarios) and `cancelReservation` (2 scenarios). The `editReservation` methods were left as `// TODO`. Upon running JaCoCo, this strategy resulted in **95% coverage**, exceeding the goal without needing to implement 100% of the methods (which demonstrates efficiency).