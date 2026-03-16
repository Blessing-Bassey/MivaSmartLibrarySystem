package gui;

import controller.BorrowController;
import model.LibraryDatabase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BorrowReturnPanel extends JPanel {
    private JTextField borrowerIdField;
    private JTextField itemIdField;
    private JTextField transactionIdField;
    private JButton borrowButton;
    private JButton returnButton;
    private JTextArea outputArea;

    public BorrowReturnPanel(LibraryDatabase database, ViewItemsPanel viewItemsPanel, MainWindow mainWindow) {
        BorrowController borrowController = new BorrowController(database);

        setLayout(new BorderLayout(20, 20));
        setBackground(UITheme.BG);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        RoundedPanel formCard = new RoundedPanel(Color.WHITE, 28);
        formCard.setLayout(new BorderLayout());
        formCard.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel title = UITheme.makeSectionTitle("Borrow / Return");
        formCard.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 12, 12));
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        formPanel.add(new JLabel("Borrower ID:"));
        borrowerIdField = new JTextField();
        formPanel.add(borrowerIdField);

        formPanel.add(new JLabel("Item ID:"));
        itemIdField = new JTextField();
        formPanel.add(itemIdField);

        formPanel.add(new JLabel("Transaction ID (for return):"));
        transactionIdField = new JTextField();
        formPanel.add(transactionIdField);

        borrowButton = UITheme.createPrimaryButton("Borrow Item");
        returnButton = UITheme.createSecondaryButton("Return Item");

        formPanel.add(borrowButton);
        formPanel.add(returnButton);

        formCard.add(formPanel, BorderLayout.CENTER);

        RoundedPanel outputCard = new RoundedPanel(Color.WHITE, 28);
        outputCard.setLayout(new BorderLayout());
        outputCard.setBorder(new EmptyBorder(20, 24, 24, 24));

        JLabel outputTitle = UITheme.makeSectionTitle("Activity");
        outputCard.add(outputTitle, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(UITheme.bodyFont(14));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        outputCard.add(scrollPane, BorderLayout.CENTER);

        add(formCard, BorderLayout.NORTH);
        add(outputCard, BorderLayout.CENTER);

        borrowButton.addActionListener(e -> {
            String borrowerId = borrowerIdField.getText().trim();
            String itemId = itemIdField.getText().trim();

            if (borrowerId.isEmpty() || itemId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Borrower ID and Item ID.");
                return;
            }

            borrowController.borrowItem(borrowerId, itemId);
            viewItemsPanel.refreshTable(database);
            mainWindow.refreshDashboard();

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
            mainWindow.refreshDashboard();

            outputArea.append("Return request processed for Transaction ID: " + transactionId + "\n");
            JOptionPane.showMessageDialog(this, "Return request processed.");
        });
    }
}