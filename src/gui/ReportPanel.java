package gui;

import controller.ReportGenerator;
import model.LibraryDatabase;

import javax.swing.*;
import java.awt.*;

public class ReportPanel extends JPanel {
    private JTextArea reportArea;
    private JButton mostBorrowedButton;
    private JButton overdueUsersButton;
    private JButton categoryDistributionButton;

    public ReportPanel(LibraryDatabase database) {
        ReportGenerator reportGenerator = new ReportGenerator(database);

        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout());

        mostBorrowedButton = new JButton("Most Borrowed Items");
        overdueUsersButton = new JButton("Overdue Users");
        categoryDistributionButton = new JButton("Category Distribution");

        buttonPanel.add(mostBorrowedButton);
        buttonPanel.add(overdueUsersButton);
        buttonPanel.add(categoryDistributionButton);

        add(buttonPanel, BorderLayout.NORTH);

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        mostBorrowedButton.addActionListener(e ->
                reportArea.setText(reportGenerator.generateMostBorrowedReport()));

        overdueUsersButton.addActionListener(e ->
                reportArea.setText(reportGenerator.generateOverdueUsersReport()));

        categoryDistributionButton.addActionListener(e ->
                reportArea.setText(reportGenerator.generateCategoryDistributionReport()));
    }
}