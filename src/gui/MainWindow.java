package gui;

import controller.LibraryManager;
import model.Book;
import model.Journal;
import model.LibraryDatabase;
import model.LibraryItem;
import model.Magazine;
import model.UserAccount;
import utils.FileHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

public class MainWindow extends JFrame {
    private final LibraryDatabase database;
    private final LibraryManager libraryManager;

    private CardLayout cardLayout;
    private JPanel contentPanel;

    private JLabel itemsCountLabel;
    private JLabel borrowedCountLabel;
    private JLabel usersCountLabel;
    private JLabel queueCountLabel;
    private JLabel undoCountLabel;

    private ViewItemsPanel viewItemsPanel;
    private BorrowReturnPanel borrowReturnPanel;
    private AdminPanel adminPanel;
    private SearchSortPanel searchSortPanel;
    private ReportPanel reportPanel;

    public MainWindow() {
        database = new LibraryDatabase();
        libraryManager = new LibraryManager(database);

        loadBooksFromFile();

        if (database.getItems().isEmpty()) {
            database.addItem(new Book("B001", "Things Fall Apart", "Chinua Achebe", 1958, 5, "Available",
                    "ISBN12345", "2023-01-10", "", "Purchased"));
            database.addItem(new Book("B002", "Half of a Yellow Sun", "Chimamanda Ngozi Adichie", 2006, 3, "Available",
                    "ISBN23456", "2023-02-11", "", "Purchased"));
            database.addItem(new Book("B003", "Clean Code", "Robert Martin", 2008, 2, "Available",
                    "ISBN34567", "2023-03-12", "", "Purchased"));
            database.addItem(new Magazine("M001", "Science Today", "Editorial Team", 2024, 3, "Available",
                    12, "Science Press"));
            database.addItem(new Journal("J001", "Medical Research Journal", "Research Board", 2022, 2, "Available",
                    5, "Health Science"));

            saveBooksToFile();
        }

        if (database.getUsers().isEmpty()) {
            database.addUser(new UserAccount("U001", "Blessing Bassey", "Data Science", 200));
            database.addUser(new UserAccount("U002", "Alice Johnson", "Computer Science", 300));
            database.addUser(new UserAccount("U003", "David Thomas", "Software Engineering", 150));
        }

        setTitle("Smart Library Circulation & Automation System");
        setSize(1320, 860);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG);

        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setBackground(UITheme.BG);

        topContainer.add(createHeaderPanel());
        topContainer.add(createStatsStrip());
        topContainer.add(createNavigationPanel());

        add(topContainer, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(UITheme.BG);
        contentPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        viewItemsPanel = new ViewItemsPanel(database);
        borrowReturnPanel = new BorrowReturnPanel(database, viewItemsPanel, this);
        adminPanel = new AdminPanel(database, libraryManager, viewItemsPanel, this);
        searchSortPanel = new SearchSortPanel(database);
        reportPanel = new ReportPanel(database);

        contentPanel.add(viewItemsPanel, "VIEW");
        contentPanel.add(borrowReturnPanel, "BORROW");
        contentPanel.add(adminPanel, "ADMIN");
        contentPanel.add(searchSortPanel, "SEARCH");
        contentPanel.add(reportPanel, "REPORTS");

        add(contentPanel, BorderLayout.CENTER);

        refreshDashboard();
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(31, 88, 69),
                        getWidth(), 0, new Color(17, 37, 33)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        header.setPreferredSize(new Dimension(1320, 140));
        header.setLayout(new BorderLayout());

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setBorder(new EmptyBorder(25, 30, 20, 20));
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Smart Library Circulation System");
        title.setFont(UITheme.titleFont(28));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("SLCAS — University Library Automation");
        subtitle.setFont(UITheme.bodyFont(15));
        subtitle.setForeground(new Color(220, 225, 220));

        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(8));
        textPanel.add(subtitle);

        header.add(textPanel, BorderLayout.WEST);

        return header;
    }

    private JPanel createStatsStrip() {
        JPanel strip = new JPanel(new FlowLayout(FlowLayout.LEFT, 28, 10));
        strip.setBackground(Color.WHITE);
        strip.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UITheme.BORDER),
                new EmptyBorder(2, 18, 2, 18)
        ));

        itemsCountLabel = createMiniBadge("Items", "0", strip);
        borrowedCountLabel = createMiniBadge("Borrowed", "0", strip);
        usersCountLabel = createMiniBadge("Users", "0", strip);
        queueCountLabel = createMiniBadge("Queue", "0", strip);
        undoCountLabel = createMiniBadge("Undo Stack", "0", strip);

        return strip;
    }

    private JLabel createMiniBadge(String text, String value, JPanel parent) {
        JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        itemPanel.setOpaque(false);

        JLabel label = new JLabel(text);
        label.setFont(UITheme.bodyFont(14));
        label.setForeground(UITheme.TEXT_MUTED);

        JLabel badge = new JLabel(value);
        badge.setOpaque(true);
        badge.setBackground(UITheme.MUTED);
        badge.setForeground(UITheme.TEXT_DARK);
        badge.setFont(UITheme.bodyBoldFont(14));
        badge.setBorder(new EmptyBorder(4, 10, 4, 10));

        itemPanel.add(label);
        itemPanel.add(badge);
        parent.add(itemPanel);

        return badge;
    }

    private JPanel createNavigationPanel() {
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 18));
        wrapper.setBackground(UITheme.BG);

        RoundedPanel navPanel = new RoundedPanel(new Color(241, 239, 235), 26);
        navPanel.setLayout(new GridLayout(1, 5, 18, 0));
        navPanel.setBorder(new EmptyBorder(14, 16, 14, 16));
        navPanel.setPreferredSize(new Dimension(1240, 64));

        navPanel.add(createNavButton("View Items", "VIEW"));
        navPanel.add(createNavButton("Borrow/Return", "BORROW"));
        navPanel.add(createNavButton("Admin", "ADMIN"));
        navPanel.add(createNavButton("Search & Sort", "SEARCH"));
        navPanel.add(createNavButton("Reports", "REPORTS"));

        wrapper.add(navPanel);
        return wrapper;
    }

    private JButton createNavButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(UITheme.bodyBoldFont(15));
        button.setForeground(UITheme.TEXT_DARK);
        button.setBackground(Color.WHITE);
        button.setBorder(new EmptyBorder(10, 16, 10, 16));

        button.addActionListener(e -> {
            cardLayout.show(contentPanel, cardName);
            refreshDashboard();
        });

        return button;
    }

    public void refreshDashboard() {
        itemsCountLabel.setText(String.valueOf(database.getItems().size()));
        borrowedCountLabel.setText(String.valueOf(getBorrowedCount()));
        usersCountLabel.setText(String.valueOf(database.getUsers().size()));
        queueCountLabel.setText(String.valueOf(database.getReservationQueue().size()));
        undoCountLabel.setText(String.valueOf(database.getUndoStack().size()));

        viewItemsPanel.refreshTable(database);
        reportPanel.refreshReports();
        revalidate();
        repaint();
    }

    private int getBorrowedCount() {
        int count = 0;
        for (LibraryItem item : database.getItems()) {
            if ("Borrowed".equalsIgnoreCase(item.getStatus())) {
                count++;
            }
        }
        return count;
    }

    private void loadBooksFromFile() {
        File file = new File("books.txt");
        if (file.exists()) {
            List<Book> books = FileHandler.loadBooks("books.txt");
            for (Book book : books) {
                database.addItem(book);
            }
        }
    }

    private void saveBooksToFile() {
        java.util.List<Book> booksToSave = new java.util.ArrayList<>();
        for (LibraryItem item : database.getItems()) {
            if (item instanceof Book) {
                booksToSave.add((Book) item);
            }
        }
        FileHandler.saveBooks(booksToSave, "books.txt");
    }
}