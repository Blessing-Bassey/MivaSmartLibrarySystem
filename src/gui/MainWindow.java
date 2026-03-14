package gui;

import controller.LibraryManager;
import model.Book;
import model.Journal;
import model.LibraryDatabase;
import model.Magazine;
import model.UserAccount;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private LibraryDatabase database;
    private LibraryManager libraryManager;

    public MainWindow() {
        database = new LibraryDatabase();
        libraryManager = new LibraryManager(database);

        // sample data for testing
        database.addItem(new Book("B001", "Java Programming", "James Gosling", 2020, 5, "Available",
                "ISBN12345", "2023-01-10", "", "Purchased"));

        database.addItem(new Magazine("M001", "Science Today", "Editorial Team", 2024, 3, "Available",
                12, "Science Press"));

        database.addItem(new Journal("J001", "Medical Research Journal", "Research Board", 2022, 2, "Available",
                5, "Health Science"));

        database.addUser(new UserAccount("U001", "Blessing Bassey", "Data Science", 200));

        setTitle("Smart Library Circulation & Automation System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        ViewItemsPanel viewItemsPanel = new ViewItemsPanel(database);
        BorrowReturnPanel borrowReturnPanel = new BorrowReturnPanel(database);
        AdminPanel adminPanel = new AdminPanel(database, libraryManager, viewItemsPanel);

        tabbedPane.addTab("View Items", viewItemsPanel);
        tabbedPane.addTab("Borrow / Return", borrowReturnPanel);
        tabbedPane.addTab("Admin", adminPanel);
        tabbedPane.addTab("Search & Sort", new JPanel());
        tabbedPane.addTab("Reports", new JPanel());

        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }
}