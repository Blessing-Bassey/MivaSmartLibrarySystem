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

        formPanel.add(new JLabel("Extra Field 1:"));
        extraField1 = new JTextField();
        formPanel.add(extraField1);

        formPanel.add(new JLabel("Extra Field 2:"));
        extraField2 = new JTextField();
        formPanel.add(extraField2);

        formPanel.add(new JLabel("Extra Field 3:"));
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

        addButton.addActionListener(e -> {
            try {
                String type = (String) itemTypeComboBox.getSelectedItem();
                String itemId = itemIdField.getText();
                String title = titleField.getText();
                String author = authorField.getText();
                int year = Integer.parseInt(yearField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                LibraryItem newItem = null;

                if ("Book".equals(type)) {
                    newItem = new Book(
                            itemId,
                            title,
                            author,
                            year,
                            quantity,
                            "Available",
                            extraField1.getText(),
                            extraField2.getText(),
                            extraField3.getText(),
                            "Purchased"
                    );
                } else if ("Magazine".equals(type)) {
                    newItem = new Magazine(
                            itemId,
                            title,
                            author,
                            year,
                            quantity,
                            "Available",
                            Integer.parseInt(extraField1.getText()),
                            extraField2.getText()
                    );
                } else if ("Journal".equals(type)) {
                    newItem = new Journal(
                            itemId,
                            title,
                            author,
                            year,
                            quantity,
                            "Available",
                            Integer.parseInt(extraField1.getText()),
                            extraField2.getText()
                    );
                }

                if (newItem != null) {
                    libraryManager.addItem(newItem);
                    viewItemsPanel.refreshTable(database);
                    outputArea.append("Item added successfully: " + newItem.getTitle() + "\n");
                    clearFields();
                }

            } catch (Exception ex) {
                outputArea.append("Error adding item: " + ex.getMessage() + "\n");
            }
        });

        deleteButton.addActionListener(e -> {
            String itemId = itemIdField.getText();

            if (!itemId.isEmpty()) {
                libraryManager.deleteItem(itemId);
                viewItemsPanel.refreshTable(database);
                outputArea.append("Delete request processed for Item ID: " + itemId + "\n");
                clearFields();
            } else {
                outputArea.append("Please enter an Item ID to delete.\n");
            }
        });

        undoButton.addActionListener(e -> {
            libraryManager.undoLastAction();
            viewItemsPanel.refreshTable(database);
            outputArea.append("Undo action processed.\n");
        });
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