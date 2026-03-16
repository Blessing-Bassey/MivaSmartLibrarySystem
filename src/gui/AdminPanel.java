package gui;

import controller.LibraryManager;
import model.Book;
import model.Journal;
import model.LibraryDatabase;
import model.LibraryItem;
import model.Magazine;

import javax.swing.*;
import java.awt.*;

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

    public AdminPanel(LibraryDatabase database, LibraryManager libraryManager, ViewItemsPanel viewItemsPanel) {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(10, 2, 8, 8));

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

        addButton = new JButton("Add Item");
        deleteButton = new JButton("Delete Item");
        undoButton = new JButton("Undo Last Action");

        formPanel.add(addButton);
        formPanel.add(deleteButton);

        add(formPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(undoButton, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        bottomPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.CENTER);

        updateExtraFieldLabels();

        itemTypeComboBox.addActionListener(e -> updateExtraFieldLabels());

        addButton.addActionListener(e -> {
            try {
                String type = (String) itemTypeComboBox.getSelectedItem();
                String itemId = itemIdField.getText().trim();
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String yearText = yearField.getText().trim();
                String quantityText = quantityField.getText().trim();

                if (itemId.isEmpty() || title.isEmpty() || author.isEmpty() || yearText.isEmpty() || quantityText.isEmpty()) {
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
                            title,
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
                            title,
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
                            title,
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
                    viewItemsPanel.refreshTable(database);
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
            viewItemsPanel.refreshTable(database);
            outputArea.append("Delete request processed for Item ID: " + itemId + "\n");
            JOptionPane.showMessageDialog(this, "Delete request processed.");
            clearFields();
        });

        undoButton.addActionListener(e -> {
            libraryManager.undoLastAction();
            viewItemsPanel.refreshTable(database);
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
}