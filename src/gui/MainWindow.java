package gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Smart Library Circulation & Automation System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("View Items", new JPanel());
        tabbedPane.addTab("Borrow / Return", new JPanel());
        tabbedPane.addTab("Admin", new JPanel());
        tabbedPane.addTab("Search & Sort", new JPanel());
        tabbedPane.addTab("Reports", new JPanel());

        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }
}