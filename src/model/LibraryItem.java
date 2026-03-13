package model;

public abstract class LibraryItem {
    private String itemId;
    private String title;
    private String author;
    private int year;
    private int quantity;
    private String status;

    public LibraryItem(String itemId, String title, String author, int year, int quantity, String status) {
        this.itemId = itemId;
        this.title = title;
        this.author = author;
        this.year = year;
        this.quantity = quantity;
        this.status = status;
    }

    public String getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract String getItemType();
}