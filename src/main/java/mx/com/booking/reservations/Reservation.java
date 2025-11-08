package mx.com.booking.reservations;

public class Reservation {
    private String reservationId;
    private String status; // "ACTIVE", "CANCELLED"
    
    // Constructor, getters, setters...
    // (Podemos dejarlos vacíos por ahora)
    
    public Reservation(String id) {
        this.reservationId = id;
        this.status = "ACTIVE";
    }

    public String getStatus() { return status; }
    public String getReservationId() { return reservationId; }
    public void setStatus(String status) { this.status = status; }
}