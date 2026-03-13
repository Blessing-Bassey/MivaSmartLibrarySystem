import controller.BorrowController;
import controller.LibraryManager;
import model.*;

public class Main {
    public static void main(String[] args) {
        LibraryDatabase database = new LibraryDatabase();

        LibraryManager manager = new LibraryManager(database);
        BorrowController borrowController = new BorrowController(database);

        Book book1 = new Book(
                "B001",
                "Java Programming",
                "James Gosling",
                2020,
                2,
                "Available",
                "ISBN12345",
                "2023-01-10",
                "",
                "Purchased"
        );

        Magazine magazine1 = new Magazine(
                "M001",
                "Science Today",
                "Editorial Team",
                2024,
                1,
                "Available",
                12,
                "Science Press"
        );

        UserAccount user1 = new UserAccount("U001", "Blessing Bassey", "Data Science", 200);

        manager.addItem(book1);
        manager.addItem(magazine1);
        database.addUser(user1);

        System.out.println("Before borrowing:");
        for (LibraryItem item : database.getItems()) {
            System.out.println(item.getTitle() + " - Quantity: " + item.getQuantity());
        }

        borrowController.borrowItem("U001", "B001");

        System.out.println("\nAfter borrowing:");
        for (LibraryItem item : database.getItems()) {
            System.out.println(item.getTitle() + " - Quantity: " + item.getQuantity());
        }

        borrowController.returnItem("T1");

        System.out.println("\nAfter returning:");
        for (LibraryItem item : database.getItems()) {
            System.out.println(item.getTitle() + " - Quantity: " + item.getQuantity());
        }
    }
}