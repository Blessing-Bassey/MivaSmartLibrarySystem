package model;

import java.util.ArrayList;

public class UserAccount {
    private String userId;
    private String name;
    private String department;
    private int level;
    private ArrayList<BorrowTransaction> borrowHistory;

    public UserAccount(String userId, String name, String department, int level) {
        this.userId = userId;
        this.name = name;
        this.department = department;
        this.level = level;
        this.borrowHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<BorrowTransaction> getBorrowHistory() {
        return borrowHistory;
    }

    public void addBorrowTransaction(BorrowTransaction transaction) {
        borrowHistory.add(transaction);
    }
}