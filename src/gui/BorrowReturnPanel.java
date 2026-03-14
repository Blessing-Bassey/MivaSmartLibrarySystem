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

    public BorrowReturnPanel(LibraryDatabase database) {
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
            String borrowerId = borrowerIdField.getText();
            String itemId = itemIdField.getText();

            borrowController.borrowItem(borrowerId, itemId);
            outputArea.append("Borrow request processed for Borrower ID: " + borrowerId +
                    ", Item ID: " + itemId + "\n");
        });

        returnButton.addActionListener(e -> {
            String transactionId = transactionIdField.getText();

            borrowController.returnItem(transactionId);
            outputArea.append("Return request processed for Transaction ID: " + transactionId + "\n");
        });
    }
}