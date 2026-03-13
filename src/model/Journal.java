package model;

public class Journal extends LibraryItem implements Borrowable {
    private int volume;
    private String researchField;

    public Journal(String itemId, String title, String author, int year, int quantity, String status,
                   int volume, String researchField) {
        super(itemId, title, author, year, quantity, status);
        this.volume = volume;
        this.researchField = researchField;
    }

    public int getVolume() {
        return volume;
    }

    public String getResearchField() {
        return researchField;
    }

    @Override
    public String getItemType() {
        return "Journal";
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