package gui;

import controller.LibraryManager;
import model.Book;
import model.Journal;
import model.LibraryDatabase;
import model.LibraryItem;
import model.Magazine;
import utils.FileHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdminPanel extends JPanel {
    private JComboBox<String> itemTypeComboBox;
    private JTextField itemIdField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField yearField;
    private JTextField quantityField;
    private JTextField extraField1;
    private JTextField extraField2;
    private JTextField extraField3;

    private JLabel extraLabel1;
    private JLabel extraLabel2;
    private JLabel extraLabel3;

    private JButton addButton;
    private JButton deleteButton;
    private JButton undoButton;
    private JTextArea outputArea;

    public AdminPanel(LibraryDatabase database, LibraryManager libraryManager, ViewItemsPanel viewItemsPanel, MainWindow mainWindow) {
        setLayout(new BorderLayout(20, 20));
        setBackground(UITheme.BG);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        RoundedPanel formCard = new RoundedPanel(Color.WHITE, 28);
        formCard.setLayout(new BorderLayout());
        formCard.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel title = UITheme.makeSectionTitle("Admin");
        formCard.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(10, 2, 8, 8));
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        formPanel.add(new JLabel("Item Type:"));
        itemTypeComboBox = new JComboBox<>(new String[]{"Book", "Magazine", "Journal"});
        formPanel.add(itemTypeComboBox);

        formPanel.add(new JLabel("Item ID:"));
        itemIdField = new JTextField();
        formPanel.add(itemIdField);

        formPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        formPanel.add(titleField);

        formPanel.add(new JLabel("Author:"));
        authorField = new JTextField();
        formPanel.add(authorField);

        formPanel.add(new JLabel("Year:"));
        yearField = new JTextField();
        formPanel.add(yearField);

        formPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        formPanel.add(quantityField);

        extraLabel1 = new JLabel("ISBN:");
        formPanel.add(extraLabel1);
        extraField1 = new JTextField();
        formPanel.add(extraField1);

        extraLabel2 = new JLabel("Purchase Date:");
        formPanel.add(extraLabel2);
        extraField2 = new JTextField();
        formPanel.add(extraField2);

        extraLabel3 = new JLabel("Donation Date:");
        formPanel.add(extraLabel3);
        extraField3 = new JTextField();
        formPanel.add(extraField3);

        addButton = UITheme.createPrimaryButton("Add Item");
        deleteButton = UITheme.createSecondaryButton("Delete Item");
        undoButton = UITheme.createSecondaryButton("Undo Last Action");

        formPanel.add(addButton);
        formPanel.add(deleteButton);

        formCard.add(formPanel, BorderLayout.CENTER);

        RoundedPanel bottomCard = new RoundedPanel(Color.WHITE, 28);
        bottomCard.setLayout(new BorderLayout());
        bottomCard.setBorder(new EmptyBorder(20, 24, 24, 24));

        bottomCard.add(undoButton, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(UITheme.bodyFont(14));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(new EmptyBorder(16, 0, 0, 0));
        bottomCard.add(outputScroll, BorderLayout.CENTER);

        add(formCard, BorderLayout.NORTH);
        add(bottomCard, BorderLayout.CENTER);

        updateExtraFieldLabels();
        itemTypeComboBox.addActionListener(e -> updateExtraFieldLabels());

        addButton.addActionListener(e -> {
            try {
                String type = (String) itemTypeComboBox.getSelectedItem();
                String itemId = itemIdField.getText().trim();
                String itemTitle = titleField.getText().trim();
                String author = authorField.getText().trim();
                String yearText = yearField.getText().trim();
                String quantityText = quantityField.getText().trim();

                if (itemId.isEmpty() || itemTitle.isEmpty() || author.isEmpty() || yearText.isEmpty() || quantityText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
                    return;
                }

                int year = Integer.parseInt(yearText);
                int quantity = Integer.parseInt(quantityText);

                LibraryItem newItem = null;

                if ("Book".equals(type)) {
                    if (extraField1.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter ISBN for the book.");
                        return;
                    }

                    newItem = new Book(
                            itemId,
                            itemTitle,
                            author,
                            year,
                            quantity,
                            "Available",
                            extraField1.getText().trim(),
                            extraField2.getText().trim(),
                            extraField3.getText().trim(),
                            "Purchased"
                    );
                } else if ("Magazine".equals(type)) {
                    if (extraField1.getText().trim().isEmpty() || extraField2.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter Issue Number and Publisher for the magazine.");
                        return;
                    }

                    newItem = new Magazine(
                            itemId,
                            itemTitle,
                            author,
                            year,
                            quantity,
                            "Available",
                            Integer.parseInt(extraField1.getText().trim()),
                            extraField2.getText().trim()
                    );
                } else if ("Journal".equals(type)) {
                    if (extraField1.getText().trim().isEmpty() || extraField2.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please enter Volume and Research Field for the journal.");
                        return;
                    }

                    newItem = new Journal(
                            itemId,
                            itemTitle,
                            author,
                            year,
                            quantity,
                            "Available",
                            Integer.parseInt(extraField1.getText().trim()),
                            extraField2.getText().trim()
                    );
                }

                if (newItem != null) {
                    libraryManager.addItem(newItem);
                    saveBooksToFile(database);
                    viewItemsPanel.refreshTable(database);
                    mainWindow.refreshDashboard();

                    outputArea.append("Added successfully: " + newItem.getTitle() + "\n");
                    JOptionPane.showMessageDialog(this, "Item added successfully.");
                    clearFields();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Year, Quantity, Issue Number, and Volume must be valid numbers.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding item: " + ex.getMessage());
            }
        });

        deleteButton.addActionListener(e -> {
            String itemId = itemIdField.getText().trim();

            if (itemId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an Item ID to delete.");
                return;
            }

            libraryManager.deleteItem(itemId);
            saveBooksToFile(database);
            viewItemsPanel.refreshTable(database);
            mainWindow.refreshDashboard();

            outputArea.append("Delete request processed for Item ID: " + itemId + "\n");
            JOptionPane.showMessageDialog(this, "Delete request processed.");
            clearFields();
        });

        undoButton.addActionListener(e -> {
            libraryManager.undoLastAction();
            saveBooksToFile(database);
            viewItemsPanel.refreshTable(database);
            mainWindow.refreshDashboard();

            outputArea.append("Undo action processed.\n");
            JOptionPane.showMessageDialog(this, "Undo completed.");
        });
    }

    private void updateExtraFieldLabels() {
        String selectedType = (String) itemTypeComboBox.getSelectedItem();

        if ("Book".equals(selectedType)) {
            extraLabel1.setText("ISBN:");
            extraLabel2.setText("Purchase Date:");
            extraLabel3.setText("Donation Date:");
            extraField3.setEnabled(true);
        } else if ("Magazine".equals(selectedType)) {
            extraLabel1.setText("Issue Number:");
            extraLabel2.setText("Publisher:");
            extraLabel3.setText("Not Used:");
            extraField3.setText("");
            extraField3.setEnabled(false);
        } else if ("Journal".equals(selectedType)) {
            extraLabel1.setText("Volume:");
            extraLabel2.setText("Research Field:");
            extraLabel3.setText("Not Used:");
            extraField3.setText("");
            extraField3.setEnabled(false);
        }
    }

    private void clearFields() {
        itemIdField.setText("");
        titleField.setText("");
        authorField.setText("");
        yearField.setText("");
        quantityField.setText("");
        extraField1.setText("");
        extraField2.setText("");
        extraField3.setText("");
    }

    private void saveBooksToFile(LibraryDatabase database) {
        List<Book> booksToSave = new ArrayList<>();

        for (LibraryItem item : database.getItems()) {
            if (item instanceof Book) {
                booksToSave.add((Book) item);
            }
        }

        FileHandler.saveBooks(booksToSave, "books.txt");
    }
}