package mx.com.booking.reservations;

import java.time.LocalDate;

public interface ReservationRepository {
    
    boolean isRoomAvailable(String roomId, LocalDate checkIn, LocalDate checkOut);
    
    void save(Reservation reservation);

    Reservation findById(String reservationId);
}