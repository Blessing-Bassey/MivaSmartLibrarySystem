package model;

public class ReservationEntry {
    private String reservationId;
    private String userId;
    private String itemId;
    private String reservedDate;

    public ReservationEntry(String reservationId, String userId, String itemId, String reservedDate) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.itemId = itemId;
        this.reservedDate = reservedDate;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getReservedDate() {
        return reservedDate;
    }

    public String getReservationDetails() {
        return "Reservation ID: " + reservationId +
                ", User ID: " + userId +
                ", Item ID: " + itemId +
                ", Reserved Date: " + reservedDate;
    }
}