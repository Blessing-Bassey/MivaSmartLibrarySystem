package controller;

import model.BorrowTransaction;
import model.LibraryDatabase;
import model.LibraryItem;

import java.util.HashMap;
import java.util.Map;

public class ReportGenerator {
    private LibraryDatabase database;

    public ReportGenerator(LibraryDatabase database) {
        this.database = database;
    }

    public String generateMostBorrowedReport() {
        Map<String, Integer> borrowCount = new HashMap<>();

        for (BorrowTransaction transaction : database.getTransactions()) {
            String itemId = transaction.getItemId();
            borrowCount.put(itemId, borrowCount.getOrDefault(itemId, 0) + 1);
        }

        StringBuilder report = new StringBuilder("Most Borrowed Items Report:\n");

        for (Map.Entry<String, Integer> entry : borrowCount.entrySet()) {
            LibraryItem item = database.findItemById(entry.getKey());
            if (item != null) {
                report.append(item.getTitle())
                        .append(" -> ")
                        .append(entry.getValue())
                        .append(" times\n");
            }
        }

        return report.toString();
    }

    public String generateOverdueUsersReport() {
        StringBuilder report = new StringBuilder("Overdue Users Report:\n");

        for (BorrowTransaction transaction : database.getTransactions()) {
            if (transaction.isOverdue()) {
                report.append("Borrower ID: ")
                        .append(transaction.getBorrowerId())
                        .append(", Item ID: ")
                        .append(transaction.getItemId())
                        .append("\n");
            }
        }

        return report.toString();
    }

    public String generateCategoryDistributionReport() {
        int books = 0;
        int magazines = 0;
        int journals = 0;

        for (LibraryItem item : database.getItems()) {
            switch (item.getItemType()) {
                case "Book":
                    books++;
                    break;
                case "Magazine":
                    magazines++;
                    break;
                case "Journal":
                    journals++;
                    break;
            }
        }

        return "Category Distribution Report:\n" +
                "Books: " + books + "\n" +
                "Magazines: " + magazines + "\n" +
                "Journals: " + journals + "\n";
    }
}