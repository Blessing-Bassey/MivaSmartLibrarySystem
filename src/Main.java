import controller.BorrowController;
import controller.LibraryManager;
import controller.ReminderService;
import controller.ReportGenerator;
import model.*;
import utils.FileHandler;
import utils.IDGenerator;
import utils.ValidationHelper;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LibraryDatabase database = new LibraryDatabase();

        LibraryManager manager = new LibraryManager(database);
        BorrowController borrowController = new BorrowController(database);
        ReportGenerator reportGenerator = new ReportGenerator(database);
        ReminderService reminderService = new ReminderService(database);

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

        UserAccount user1 = new UserAccount("U001", "Blessing Bassey", "Data Science", 200);

        manager.addItem(book1);
        database.addUser(user1);

        borrowController.borrowItem("U001", "B001");

        System.out.println(reportGenerator.generateMostBorrowedReport());
        System.out.println(reportGenerator.generateCategoryDistributionReport());

        reminderService.sendReminder();

        System.out.println("Generated Item ID: " + IDGenerator.generateItemId());
        System.out.println("Validation Test: " +
                ValidationHelper.validateItemInput("B002", "Algorithms", "Cormen"));

        List<Book> booksToSave = new ArrayList<>();
        booksToSave.add(book1);

        FileHandler.saveBooks(booksToSave, "books.txt");
        List<Book> loadedBooks = FileHandler.loadBooks("books.txt");

        System.out.println("Loaded books from file:");
        for (Book book : loadedBooks) {
            System.out.println(book.getTitle());
        }
    }
}