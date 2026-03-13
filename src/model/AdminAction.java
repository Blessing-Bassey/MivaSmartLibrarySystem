package model;

public class AdminAction {
    private String actionType;
    private LibraryItem affectedItem;
    private String timestamp;

    public AdminAction(String actionType, LibraryItem affectedItem, String timestamp) {
        this.actionType = actionType;
        this.affectedItem = affectedItem;
        this.timestamp = timestamp;
    }

    public String getActionType() {
        return actionType;
    }

    public LibraryItem getAffectedItem() {
        return affectedItem;
    }

    public String getTimestamp() {
        return timestamp;
    }
}