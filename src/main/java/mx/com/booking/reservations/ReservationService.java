package mx.com.booking.reservations;

import java.time.LocalDate;

/**
 * Manages the business logic for creating, editing, and canceling reservations.
 * This service coordinates with the persistence layer (ReservationRepository)
 * to validate and save reservation data.
 */
public class ReservationService {

    /**
     * The data persistence layer for reservation operations.
     */
    private final ReservationRepository repository;

    /**
     * Constructs a new ReservationService with its required repository.
     * This uses Dependency Injection to decouple the service from a specific
     * database implementation.
     *
     * @param repository The repository implementation (e.g., for database access).
     */
    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a new reservation after validating business rules.
     *
     * @param userId   The ID of the user making the reservation.
     * @param roomId   The ID of the room to reserve.
     * @param checkIn  The check-in date.
     * @param checkOut The check-out date.
     * @return The newly created Reservation object.
     * @throws InvalidDatesException    if the check-out date is not after the check-in date.
     * @throws RoomUnavailableException if the room is not available for the selected dates.
     */
    public Reservation createReservation(String userId, String roomId, LocalDate checkIn, LocalDate checkOut) {
        
        // 1. Validate Dates
        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            throw new InvalidDatesException("Check-out date must be after check-in date");
        }

        // 2. Validate Availability
        if (!repository.isRoomAvailable(roomId, checkIn, checkOut)) {
            throw new RoomUnavailableException("Room is not available for the selected dates");
        }

        // 3. Create and save the reservation
        Reservation newReservation = new Reservation(userId + "_" + roomId);
        newReservation.setStatus("ACTIVE");
        repository.save(newReservation); // Tell the repo to save
        
        return newReservation;
    }
    
    /**
     * Edits an existing reservation.
     * (Note: Logic is not yet implemented).
     *
     * @param reservationId The ID of the reservation to edit.
     * @param newCheckIn    The new check-in date.
     * @param newCheckOut   The new check-out date.
     * @return The updated Reservation object.
     * @throws ReservationNotFoundException if the reservation is not found.
     */
    public Reservation editReservation(String reservationId, LocalDate newCheckIn, LocalDate newCheckOut) {
        // TODO: Implement editing logic
        return null;
    }

    /**
     * Cancels an existing reservation.
     * It finds the reservation, sets its status to "CANCELLED", and saves the change.
     *
     * @param reservationId The ID of the reservation to cancel.
     * @return true if the cancellation was successful.
     * @throws ReservationNotFoundException if the reservation is not found.
     */
    public boolean cancelReservation(String reservationId) {
        // 1. Find the reservation
        Reservation reservation = repository.findById(reservationId);

        // 2. Validate if it exists
        if (reservation == null) {
            throw new ReservationNotFoundException("Reservation not found with ID: " + reservationId);
        }

        // 3. Change status and save
        reservation.setStatus("CANCELLED");
        repository.save(reservation);
        return true;
    }
}