package model;

public class BorrowTransaction {
    private String transactionId;
    private String borrowerId;
    private String itemId;
    private String dateBorrowed;
    private String dueDate;
    private String dateReturned;
    private String status;

    public BorrowTransaction(String transactionId, String borrowerId, String itemId,
                             String dateBorrowed, String dueDate, String dateReturned, String status) {
        this.transactionId = transactionId;
        this.borrowerId = borrowerId;
        this.itemId = itemId;
        this.dateBorrowed = dateBorrowed;
        this.dueDate = dueDate;
        this.dateReturned = dateReturned;
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getDateBorrowed() {
        return dateBorrowed;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDateReturned() {
        return dateReturned;
    }

    public String getStatus() {
        return status;
    }

    public void markReturned(String returnDate) {
        this.dateReturned = returnDate;
        this.status = "Returned";
    }

    public boolean isOverdue() {
        return status.equalsIgnoreCase("Overdue");
    }
}