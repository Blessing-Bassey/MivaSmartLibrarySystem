package controller;

import model.BorrowTransaction;
import model.LibraryDatabase;

public class ReportGenerator {

    private LibraryDatabase database;

    public ReportGenerator(LibraryDatabase database) {
        this.database = database;
    }

    public String generateOverdueUsersReport() {
        StringBuilder report = new StringBuilder("Overdue Users Report:\n");
        boolean found = false;

        for (BorrowTransaction transaction : database.getTransactions()) {
            if (transaction.isOverdue()) {
                found = true;

                report.append("Borrower ID: ")
                        .append(transaction.getBorrowerId())
                        .append(", Item ID: ")
                        .append(transaction.getItemId())
                        .append("\n");
            }
        }

        if (!found) {
            report.append("No overdue users found.\n");
        }

        return report.toString();
    }
}