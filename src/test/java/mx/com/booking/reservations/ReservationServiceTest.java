package mx.com.booking.reservations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;


/**
 * Test suite for {@link ReservationService}.
 * <p>
 * This class uses JUnit 5 and Mockito to perform isolated unit testing.
 * It verifies all business logic, including happy paths and exception handling,
 * by mocking the {@link ReservationRepository} dependency.
 */
@DisplayName("Test Suite for ReservationService (C2 Strategy)")
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    /**
     * A mock implementation of the repository, managed by Mockito.
     */
    @Mock
    private ReservationRepository reservationRepository;

    /**
     * The instance of the service being tested.
     * Mockito will automatically inject the {@code reservationRepository} mock into this instance.
     */
    @InjectMocks
    private ReservationService service;
    
    // --- TEST CONSTANTS ---
    private final LocalDate CHECK_IN = LocalDate.of(2025, 12, 1);
    private final LocalDate CHECK_OUT = LocalDate.of(2025, 12, 5);
    private final String ROOM_ID = "101-A";
    private final String USER_ID = "user-123";
    private final String RESERVATION_ID = "res-456";

    // --- Tests for createReservation ---

    @Test
    @DisplayName("1. Should create a reservation successfully (Happy Path)")
    void testCreateReservation_Success() {
        // --- 1. ARRANGE ---
        when(reservationRepository.isRoomAvailable(anyString(), any(LocalDate.class), any(LocalDate.class)))
            .thenReturn(true);
        
        // --- 2. ACT ---
        Reservation reservation = service.createReservation(USER_ID, ROOM_ID, CHECK_IN, CHECK_OUT);

        // --- 3. ASSERT ---
        assertNotNull(reservation);
        assertEquals("ACTIVE", reservation.getStatus());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    @DisplayName("2. Should throw RoomUnavailableException if room is occupied")
    void testCreateReservation_Fail_RoomUnavailable() {
        // --- 1. ARRANGE ---
        when(reservationRepository.isRoomAvailable(ROOM_ID, CHECK_IN, CHECK_OUT))
            .thenReturn(false);

        // --- 2. ACT & 3. ASSERT ---
        RoomUnavailableException exception = assertThrows(
            RoomUnavailableException.class,
            () -> {
                service.createReservation(USER_ID, ROOM_ID, CHECK_IN, CHECK_OUT);
            }
        );
        
        assertEquals("Room is not available for the selected dates", exception.getMessage());
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    // --- ESTA ES LA PRUEBA MODIFICADA ---
    @ParameterizedTest(name = "[{index}] Should fail when check-out is {1}")
    @DisplayName("3. Should throw InvalidDatesException for all invalid date ranges")
    @CsvSource({
        "2025-11-30, 'before check-in'",  // Caso 1: check-out es ANTES
        "2025-12-01, 'equal to check-in'" // Caso 2: check-out es IGUAL
    })
    void testCreateReservation_Fail_InvalidDates(LocalDate badCheckOut, String scenario) {
        // --- 1. ARRANGE (CHECK_IN es 2025-12-01) ---
        
        // --- 2. ACT & 3. ASSERT ---
        InvalidDatesException exception = assertThrows(
            InvalidDatesException.class,
            () -> {
                service.createReservation(USER_ID, ROOM_ID, CHECK_IN, badCheckOut);
            }
        );
        
        assertEquals("Check-out date must be after check-in date", exception.getMessage());
        verify(reservationRepository, never()).isRoomAvailable(anyString(), any(), any());
        verify(reservationRepository, never()).save(any());
    }


    // --- Tests for editReservation ---
    
    @Test
    @DisplayName("4. (TODO) Should edit an existing reservation successfully")
    void testEditReservation_Success() {
        // TODO: Implement
    }

    @Test
    @DisplayName("5. (TODO) Should throw ReservationNotFoundException if reservation to edit does not exist")
    void testEditReservation_Fail_ReservationNotFound() {
        // TODO: Implement
    }

    // --- Tests for cancelReservation (IMPLEMENTED) ---

    @Test
    @DisplayName("6. Should cancel an existing reservation successfully")
    void testCancelReservation_Success() {
        // --- 1. ARRANGE ---
        Reservation mockReservation = new Reservation(RESERVATION_ID);
        mockReservation.setStatus("ACTIVE");
        
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(mockReservation);

        // --- 2. ACT ---
        boolean result = service.cancelReservation(RESERVATION_ID);

        // --- 3. ASSERT ---
        assertTrue(result);
        assertEquals("CANCELLED", mockReservation.getStatus());
        verify(reservationRepository, times(1)).save(mockReservation);
    }

    @Test
    @DisplayName("7. Should throw ReservationNotFoundException if reservation to cancel does not exist")
    void testCancelReservation_Fail_ReservationNotFound() {
        // --- 1. ARRANGE ---
        when(reservationRepository.findById(RESERVATION_ID)).thenReturn(null);
        
        // --- 2. ACT & 3. ASSERT ---
        ReservationNotFoundException exception = assertThrows(
            ReservationNotFoundException.class,
            () -> {
                service.cancelReservation(RESERVATION_ID);
            }
        );
        
        assertEquals("Reservation not found with ID: " + RESERVATION_ID, exception.getMessage());
        verify(reservationRepository, never()).save(any(Reservation.class));
    }
}