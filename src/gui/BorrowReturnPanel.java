package gui;

import controller.BorrowController;
import model.LibraryDatabase;

import javax.swing.*;
import java.awt.*;

public class BorrowReturnPanel extends JPanel {
    private JTextField borrowerIdField;
    private JTextField itemIdField;
    private JTextField transactionIdField;
    private JButton borrowButton;
    private JButton returnButton;
    private JTextArea outputArea;

    public BorrowReturnPanel(LibraryDatabase database, ViewItemsPanel viewItemsPanel) {
        BorrowController borrowController = new BorrowController(database);

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("Borrower ID:"));
        borrowerIdField = new JTextField();
        formPanel.add(borrowerIdField);

        formPanel.add(new JLabel("Item ID:"));
        itemIdField = new JTextField();
        formPanel.add(itemIdField);

        formPanel.add(new JLabel("Transaction ID (for return):"));
        transactionIdField = new JTextField();
        formPanel.add(transactionIdField);

        borrowButton = new JButton("Borrow Item");
        returnButton = new JButton("Return Item");

        formPanel.add(borrowButton);
        formPanel.add(returnButton);

        add(formPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        borrowButton.addActionListener(e -> {
            String borrowerId = borrowerIdField.getText().trim();
            String itemId = itemIdField.getText().trim();

            if (borrowerId.isEmpty() || itemId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Borrower ID and Item ID.");
                return;
            }

            borrowController.borrowItem(borrowerId, itemId);
            viewItemsPanel.refreshTable(database);
            outputArea.append("Borrow request processed for Borrower ID: " + borrowerId +
                    ", Item ID: " + itemId + "\n");
            JOptionPane.showMessageDialog(this, "Borrow request processed.");
        });

        returnButton.addActionListener(e -> {
            String transactionId = transactionIdField.getText().trim();

            if (transactionId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Transaction ID.");
                return;
            }

            borrowController.returnItem(transactionId);
            viewItemsPanel.refreshTable(database);
            outputArea.append("Return request processed for Transaction ID: " + transactionId + "\n");
            JOptionPane.showMessageDialog(this, "Return request processed.");
        });
    }
}