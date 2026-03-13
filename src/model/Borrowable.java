package model;

public interface Borrowable {
    void borrowItem();
    void returnItem();
    boolean isAvailable();
}