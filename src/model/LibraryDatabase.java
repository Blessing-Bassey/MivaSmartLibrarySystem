package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class LibraryDatabase {
    private ArrayList<LibraryItem> items;
    private ArrayList<UserAccount> users;
    private ArrayList<BorrowTransaction> transactions;
    private Queue<ReservationEntry> reservationQueue;
    private Stack<AdminAction> undoStack;
    private LibraryItem[] frequentItems;

    public LibraryDatabase() {
        items = new ArrayList<>();
        users = new ArrayList<>();
        transactions = new ArrayList<>();
        reservationQueue = new LinkedList<>();
        undoStack = new Stack<>();
        frequentItems = new LibraryItem[10];
    }

    public ArrayList<LibraryItem> getItems() {
        return items;
    }

    public ArrayList<UserAccount> getUsers() {
        return users;
    }

    public ArrayList<BorrowTransaction> getTransactions() {
        return transactions;
    }

    public Queue<ReservationEntry> getReservationQueue() {
        return reservationQueue;
    }

    public Stack<AdminAction> getUndoStack() {
        return undoStack;
    }

    public LibraryItem[] getFrequentItems() {
        return frequentItems;
    }

    public void addItem(LibraryItem item) {
        items.add(item);
    }

    public void addUser(UserAccount user) {
        users.add(user);
    }

    public void addTransaction(BorrowTransaction transaction) {
        transactions.add(transaction);
    }

    public void addReservation(ReservationEntry reservation) {
        reservationQueue.add(reservation);
    }

    public void pushAdminAction(AdminAction action) {
        undoStack.push(action);
    }

    public LibraryItem findItemById(String itemId) {
        for (LibraryItem item : items) {
            if (item.getItemId().equalsIgnoreCase(itemId)) {
                return item;
            }
        }
        return null;
    }

    public UserAccount findUserById(String userId) {
        for (UserAccount user : users) {
            if (user.getUserId().equalsIgnoreCase(userId)) {
                return user;
            }
        }
        return null;
    }

    public BorrowTransaction findTransactionById(String transactionId) {
        for (BorrowTransaction transaction : transactions) {
            if (transaction.getTransactionId().equalsIgnoreCase(transactionId)) {
                return transaction;
            }
        }
        return null;
    }
}