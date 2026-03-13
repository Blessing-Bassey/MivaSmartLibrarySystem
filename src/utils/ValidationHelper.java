package utils;

public class ValidationHelper {

    public static boolean validateItemInput(String itemId, String title, String author) {
        return itemId != null && !itemId.isEmpty()
                && title != null && !title.isEmpty()
                && author != null && !author.isEmpty();
    }

    public static boolean validateUserInput(String userId, String name, String department) {
        return userId != null && !userId.isEmpty()
                && name != null && !name.isEmpty()
                && department != null && !department.isEmpty();
    }

    public static boolean validateTransactionInput(String transactionId, String borrowerId, String itemId) {
        return transactionId != null && !transactionId.isEmpty()
                && borrowerId != null && !borrowerId.isEmpty()
                && itemId != null && !itemId.isEmpty();
    }
}