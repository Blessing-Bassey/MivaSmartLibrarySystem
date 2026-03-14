package gui;

import model.LibraryDatabase;
import model.LibraryItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewItemsPanel extends JPanel {
    private JTable itemsTable;
    private DefaultTableModel tableModel;

    public ViewItemsPanel(LibraryDatabase database) {
        setLayout(new BorderLayout());

        String[] columnNames = {"Item ID", "Title", "Author", "Year", "Quantity", "Status", "Type"};
        tableModel = new DefaultTableModel(columnNames, 0);

        itemsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(itemsTable);

        add(scrollPane, BorderLayout.CENTER);

        refreshTable(database);
    }

    public void refreshTable(LibraryDatabase database) {
        tableModel.setRowCount(0);

        for (LibraryItem item : database.getItems()) {
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
    }
}