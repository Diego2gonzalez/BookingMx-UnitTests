package mx.com.booking.reservations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * C2 Improvement: Integration Test (IT)
 * This test runs against a REAL (in-memory) H2 database.
 * It does NOT use Mockito. It tests the real SQL.
 * It will be run by the 'maven-failsafe-plugin'.
 */
@DisplayName("Integration Tests for JdbcReservationRepository (H2)")
class JdbcReservationRepositoryIT {

    private Connection connection;
    private JdbcReservationRepository repository;

    // --- Test Data ---
    private final String ROOM_A = "101-A";
    private final LocalDate DAY_1 = LocalDate.of(2025, 12, 1);
    private final LocalDate DAY_3 = LocalDate.of(2025, 12, 3);
    private final LocalDate DAY_5 = LocalDate.of(2025, 12, 5);

    @BeforeEach
    void setUp() throws SQLException {
        // 1. Create a unique MySQL database
        String dbUrl = "jdbc:mysql://localhost:3306/bookingmx";
        String user = "root"; // o el usuario que uses
        String password = "123456"; // cámbialo según tu configuración
        connection = DriverManager.getConnection(dbUrl, user, password);


        // 2. Create the table schema for this test
        try (Statement s = connection.createStatement()) {
            s.execute("DROP TABLE IF EXISTS reservations");
            s.execute("CREATE TABLE reservations ("
                    + "reservation_id VARCHAR(255) PRIMARY KEY, "
                    + "room_id VARCHAR(255), "
                    + "user_id VARCHAR(255), "
                    + "check_in DATE, "
                    + "check_out DATE, "
                    + "status VARCHAR(50)"
                    + ")");
        }


        // 3. Create the real repository instance
        repository = new JdbcReservationRepository(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Close the connection, H2 will wipe the in-memory DB
        if (connection != null) {
            connection.close();
        }
    }
    
    // --- Helper Method to pre-load data ---
    private void insertReservation(String id, String room, LocalDate in, LocalDate out, String status) throws SQLException {
         String sql = "INSERT INTO reservations (reservation_id, room_id, user_id, check_in, check_out, status) " +
                  "VALUES (?, ?, 'user-test', ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, room);
            ps.setDate(3, Date.valueOf(in));
            ps.setDate(4, Date.valueOf(out));
            ps.setString(5, status);
            ps.executeUpdate();
        }
    }


    @Test
    @DisplayName("Should save and find a reservation by ID")
    void testSaveAndFindById() throws SQLException {
        // --- 1. ARRANGE ---
        insertReservation("res-123", ROOM_A, DAY_1, DAY_5, "ACTIVE");

        // --- 2. ACT ---
        Reservation found = repository.findById("res-123");

        // --- 3. ASSERT ---
        assertNotNull(found);
        assertEquals("res-123", found.getReservationId());
        assertEquals("ACTIVE", found.getStatus());
    }

    @Test
    @DisplayName("Should return null when reservation is not found")
    void testFindById_NotFound() {
        // --- 1. ARRANGE (Empty DB) ---
        
        // --- 2. ACT ---
        Reservation found = repository.findById("res-999");

        // --- 3. ASSERT ---
        assertNull(found);
    }

    @Test
    @DisplayName("isRoomAvailable should return false when dates overlap")
    void testIsRoomAvailable_ReturnsFalseWhenOccupied() throws SQLException {
        // --- 1. ARRANGE ---
        // A reservation exists from Day 1 to Day 5
        insertReservation("res-123", ROOM_A, DAY_1, DAY_5, "ACTIVE");

        // --- 2. ACT ---
        // Try to book a new reservation from Day 3 to Day (any)
        boolean isAvailable = repository.isRoomAvailable(ROOM_A, DAY_3, DAY_5.plusDays(1));

        // --- 3. ASSERT ---
        assertFalse(isAvailable, "Room should be unavailable because the dates overlap");
    }

    @Test
    @DisplayName("isRoomAvailable should return true when no dates overlap")
    void testIsRoomAvailable_ReturnsTrue() throws SQLException {
        // --- 1. ARRANGE ---
        // A reservation exists from Day 1 to Day 5
        insertReservation("res-123", ROOM_A, DAY_1, DAY_5, "ACTIVE");
        
        // --- 2. ACT ---
        // Try to book a new reservation starting on Day 5 (check-out day)
        boolean isAvailable = repository.isRoomAvailable(ROOM_A, DAY_5, DAY_5.plusDays(2));
        
        // --- 3. ASSERT ---
        assertTrue(isAvailable, "Room should be available starting on the check-out day");
    }
}