package model;

public class Magazine extends LibraryItem implements Borrowable {
    private int issueNumber;
    private String publisher;

    public Magazine(String itemId, String title, String author, int year, int quantity, String status,
                    int issueNumber, String publisher) {
        super(itemId, title, author, year, quantity, status);
        this.issueNumber = issueNumber;
        this.publisher = publisher;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public String getItemType() {
        return "Magazine";
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