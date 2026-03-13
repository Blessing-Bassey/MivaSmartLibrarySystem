package model;

import java.util.ArrayList;
import java.util.List;

public class SearchEngine {

    // Linear Search
    public List<LibraryItem> linearSearchByTitle(List<LibraryItem> items, String title) {
        List<LibraryItem> results = new ArrayList<>();

        for (LibraryItem item : items) {
            if (item.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(item);
            }
        }

        return results;
    }

    // Binary Search (requires sorted list)
    public LibraryItem binarySearchByTitle(List<LibraryItem> items, String title) {
        int left = 0;
        int right = items.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            LibraryItem midItem = items.get(mid);
            int comparison = midItem.getTitle().compareToIgnoreCase(title);

            if (comparison == 0) {
                return midItem;
            }

            if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
    }

    // Recursive Search
    public List<LibraryItem> recursiveSearchByTitle(List<LibraryItem> items, String title) {
        List<LibraryItem> results = new ArrayList<>();
        recursiveHelper(items, title.toLowerCase(), 0, results);
        return results;
    }

    private void recursiveHelper(List<LibraryItem> items, String title, int index, List<LibraryItem> results) {

        if (index >= items.size()) {
            return;
        }

        LibraryItem item = items.get(index);

        if (item.getTitle().toLowerCase().contains(title)) {
            results.add(item);
        }

        recursiveHelper(items, title, index + 1, results);
    }
}