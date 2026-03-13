package controller;

import model.AdminAction;
import model.LibraryDatabase;
import model.LibraryItem;

import java.util.ArrayList;

public class LibraryManager {
    private LibraryDatabase database;

    public LibraryManager(LibraryDatabase database) {
        this.database = database;
    }

    public void addItem(LibraryItem item) {
        database.addItem(item);
        database.pushAdminAction(new AdminAction("ADD", item, "Now"));
    }

    public void updateItem(LibraryItem updatedItem) {
        LibraryItem existingItem = database.findItemById(updatedItem.getItemId());

        if (existingItem != null) {
            existingItem.setQuantity(updatedItem.getQuantity());
            existingItem.setStatus(updatedItem.getStatus());
            database.pushAdminAction(new AdminAction("UPDATE", updatedItem, "Now"));
        }
    }

    public void deleteItem(String itemId) {
        LibraryItem item = database.findItemById(itemId);

        if (item != null) {
            database.getItems().remove(item);
            database.pushAdminAction(new AdminAction("DELETE", item, "Now"));
        }
    }

    public void undoLastAction() {
        if (!database.getUndoStack().isEmpty()) {
            AdminAction lastAction = database.getUndoStack().pop();
            String actionType = lastAction.getActionType();
            LibraryItem item = lastAction.getAffectedItem();

            if (actionType.equalsIgnoreCase("ADD")) {
                database.getItems().remove(item);
            } else if (actionType.equalsIgnoreCase("DELETE")) {
                database.addItem(item);
            }
        }
    }

    public ArrayList<LibraryItem> getAllItems() {
        return database.getItems();
    }
}