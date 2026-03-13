package controller;

import model.BorrowTransaction;
import model.LibraryDatabase;

public class ReminderService {
    private LibraryDatabase database;

    public ReminderService(LibraryDatabase database) {
        this.database = database;
    }

    public void checkOverdueItems() {
        boolean found = false;

        for (BorrowTransaction transaction : database.getTransactions()) {
            if (transaction.isOverdue()) {
                found = true;
                System.out.println("Reminder: Borrower " + transaction.getBorrowerId()
                        + " has an overdue item: " + transaction.getItemId());
            }
        }

        if (!found) {
            System.out.println("No overdue items found.");
        }
    }

    public void sendReminder() {
        checkOverdueItems();
    }
}