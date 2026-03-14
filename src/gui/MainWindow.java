package gui;

import model.Book;
import model.LibraryDatabase;
import model.Magazine;
import model.Journal;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private LibraryDatabase database;

    public MainWindow() {
        database = new LibraryDatabase();

        // sample data for testing
        database.addItem(new Book("B001", "Java Programming", "James Gosling", 2020, 5, "Available",
                "ISBN12345", "2023-01-10", "", "Purchased"));

        database.addItem(new Magazine("M001", "Science Today", "Editorial Team", 2024, 3, "Available",
                12, "Science Press"));

        database.addItem(new Journal("J001", "Medical Research Journal", "Research Board", 2022, 2, "Available",
                5, "Health Science"));

        setTitle("Smart Library Circulation & Automation System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("View Items", new ViewItemsPanel(database));
        tabbedPane.addTab("Borrow / Return", new JPanel());
        tabbedPane.addTab("Admin", new JPanel());
        tabbedPane.addTab("Search & Sort", new JPanel());
        tabbedPane.addTab("Reports", new JPanel());

        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }
}