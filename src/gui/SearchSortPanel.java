package gui;

import model.LibraryDatabase;
import model.LibraryItem;
import model.SearchEngine;
import model.SortEngine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SearchSortPanel extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    private JButton sortButton;
    private JTable resultsTable;
    private DefaultTableModel tableModel;

    private SearchEngine searchEngine;
    private SortEngine sortEngine;
    private LibraryDatabase database;

    public SearchSortPanel(LibraryDatabase database) {
        this.database = database;
        this.searchEngine = new SearchEngine();
        this.sortEngine = new SortEngine();

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());

        topPanel.add(new JLabel("Search Title:"));
        searchField = new JTextField(20);
        topPanel.add(searchField);

        searchButton = new JButton("Search");
        sortButton = new JButton("Sort by Title");

        topPanel.add(searchButton);
        topPanel.add(sortButton);

        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"Item ID", "Title", "Author", "Year", "Quantity", "Status", "Type"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultsTable = new JTable(tableModel);

        add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        loadAllItems();

        searchButton.addActionListener(e -> performSearch());
        sortButton.addActionListener(e -> performSort());
    }

    private void loadAllItems() {
        tableModel.setRowCount(0);

        for (LibraryItem item : database.getItems()) {
            addItemToTable(item);
        }
    }

    private void addItemToTable(LibraryItem item) {
        Object[] row = {
                item.getItemId(),
                item.getTitle(),
                item.getAuthor(),
                item.getYear(),
                item.getQuantity(),
                item.getStatus(),
                item.getItemType()
        };

        tableModel.addRow(row);
    }

    private void performSearch() {
        String title = searchField.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a title to search.");
            return;
        }

        List<LibraryItem> results = searchEngine.linearSearchByTitle(database.getItems(), title);

        tableModel.setRowCount(0);

        for (LibraryItem item : results) {
            addItemToTable(item);
        }

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No matching items found.");
        }
    }

    private void performSort() {
        List<LibraryItem> sortedItems = new ArrayList<>(database.getItems());

        sortEngine.insertionSort(sortedItems);

        tableModel.setRowCount(0);

        for (LibraryItem item : sortedItems) {
            addItemToTable(item);
        }

        JOptionPane.showMessageDialog(this, "Items sorted by title.");
    }
}