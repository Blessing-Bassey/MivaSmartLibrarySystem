package controller;

import model.*;

public class BorrowController {
    private LibraryDatabase database;

    public BorrowController(LibraryDatabase database) {
        this.database = database;
    }

    public void borrowItem(String borrowerId, String itemId) {
        UserAccount user = database.findUserById(borrowerId);
        LibraryItem item = database.findItemById(itemId);

        if (user == null) {
            System.out.println("Borrower not found.");
            return;
        }

        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        if (item instanceof Borrowable) {
            Borrowable borrowableItem = (Borrowable) item;

            if (borrowableItem.isAvailable()) {
                borrowableItem.borrowItem();

                BorrowTransaction transaction = new BorrowTransaction(
                        "T" + (database.getTransactions().size() + 1),
                        borrowerId,
                        itemId,
                        "2026-03-13",
                        "2026-03-20",
                        "",
                        "Borrowed"
                );

                database.addTransaction(transaction);
                user.addBorrowTransaction(transaction);

                System.out.println("Item borrowed successfully.");
            } else {
                ReservationEntry reservation = new ReservationEntry(
                        "R" + (database.getReservationQueue().size() + 1),
                        borrowerId,
                        itemId,
                        "2026-03-13"
                );

                database.addReservation(reservation);
                System.out.println("Item unavailable. Borrower added to reservation queue.");
            }
        }
    }

    public void returnItem(String transactionId) {
        BorrowTransaction transaction = database.findTransactionById(transactionId);

        if (transaction == null) {
            System.out.println("Transaction not found.");
            return;
        }

        LibraryItem item = database.findItemById(transaction.getItemId());

        if (item != null && item instanceof Borrowable) {
            Borrowable borrowableItem = (Borrowable) item;
            borrowableItem.returnItem();
            transaction.markReturned("2026-03-13");

            System.out.println("Item returned successfully.");
        }
    }

    public void reserveItem(String borrowerId, String itemId) {
        ReservationEntry reservation = new ReservationEntry(
                "R" + (database.getReservationQueue().size() + 1),
                borrowerId,
                itemId,
                "2026-03-13"
        );

        database.addReservation(reservation);
        System.out.println("Reservation added successfully.");
    }
}