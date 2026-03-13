package utils;

public class IDGenerator {
    private static int itemCounter = 100;
    private static int userCounter = 100;
    private static int transactionCounter = 100;
    private static int reservationCounter = 100;

    public static String generateItemId() {
        return "I" + itemCounter++;
    }

    public static String generateUserId() {
        return "U" + userCounter++;
    }

    public static String generateTransactionId() {
        return "T" + transactionCounter++;
    }

    public static String generateReservationId() {
        return "R" + reservationCounter++;
    }
}