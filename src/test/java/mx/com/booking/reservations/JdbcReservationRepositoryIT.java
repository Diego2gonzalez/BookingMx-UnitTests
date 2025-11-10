package mx.com.booking.reservations;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Integration Tests for JdbcReservationRepository (MySQL)")
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
        // --- INICIO DE LA MEJORA C2 ---
        // 1. Leer la configuración de las Variables de Entorno del Sistema
        String dbUser = System.getenv("DB_USER_TEST");
        String dbPassword = System.getenv("DB_PASSWORD_TEST");

        // 2. Omitir (SKP) la prueba si las variables no están configuradas
        // Esto evita que el build falle, pero nos notifica.
        Assumptions.assumeTrue(dbUser != null && dbPassword != null, 
            "TEST OMITIDO: Las variables de entorno DB_USER_TEST y DB_PASSWORD_TEST no están configuradas.");

        // 3. Usar las variables para conectarse
        String dbUrl = "jdbc:mysql://localhost:3306/bookingmx";
        connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        // --- FIN DE LA MEJORA C2 ---


        // 2. Create the table schema for this test (sin cambios)
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


        // 3. Create the real repository instance (sin cambios)
        repository = new JdbcReservationRepository(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    
    // ... (El resto del archivo, con el helper y las 4 pruebas, queda EXACTAMENTE IGUAL) ...
    
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
        insertReservation("res-123", ROOM_A, DAY_1, DAY_5, "ACTIVE");

        // --- 2. ACT ---
        boolean isAvailable = repository.isRoomAvailable(ROOM_A, DAY_3, DAY_5.plusDays(1));

        // --- 3. ASSERT ---
        assertFalse(isAvailable, "Room should be unavailable because the dates overlap");
    }

    @Test
    @DisplayName("isRoomAvailable should return true when no dates overlap")
    void testIsRoomAvailable_ReturnsTrue() throws SQLException {
        // --- 1. ARRANGE ---
        insertReservation("res-123", ROOM_A, DAY_1, DAY_5, "ACTIVE");
        
        // --- 2. ACT ---
        boolean isAvailable = repository.isRoomAvailable(ROOM_A, DAY_5, DAY_5.plusDays(2));
        
        // --- 3. ASSERT ---
        assertTrue(isAvailable, "Room should be available starting on the check-out day");
    }
}