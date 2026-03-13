package model;

public class Book extends LibraryItem implements Borrowable {
    private String isbn;
    private String purchaseDate;
    private String donationDate;
    private String sourceType;

    public Book(String itemId, String title, String author, int year, int quantity, String status,
                String isbn, String purchaseDate, String donationDate, String sourceType) {
        super(itemId, title, author, year, quantity, status);
        this.isbn = isbn;
        this.purchaseDate = purchaseDate;
        this.donationDate = donationDate;
        this.sourceType = sourceType;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getDonationDate() {
        return donationDate;
    }

    public String getSourceType() {
        return sourceType;
    }

    @Override
    public String getItemType() {
        return "Book";
    }

    @Override
    public void borrowItem() {
        if (getQuantity() > 0) {
            setQuantity(getQuantity() - 1);
        }
    }

    @Override
    public void returnItem() {
        setQuantity(getQuantity() + 1);
    }

    @Override
    public boolean isAvailable() {
        return getQuantity() > 0;
    }
}