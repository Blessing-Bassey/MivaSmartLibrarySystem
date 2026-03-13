package model;

import java.util.Collections;
import java.util.List;

public class SortEngine {

    // Insertion Sort
    public void insertionSort(List<LibraryItem> items) {

        for (int i = 1; i < items.size(); i++) {

            LibraryItem key = items.get(i);
            int j = i - 1;

            while (j >= 0 && items.get(j).getTitle().compareToIgnoreCase(key.getTitle()) > 0) {

                items.set(j + 1, items.get(j));
                j--;
            }

            items.set(j + 1, key);
        }
    }

    // Merge Sort
    public void mergeSort(List<LibraryItem> items) {
        Collections.sort(items, (a, b) ->
                a.getTitle().compareToIgnoreCase(b.getTitle()));
    }

    // Selection Sort
    public void selectionSort(List<LibraryItem> items) {

        for (int i = 0; i < items.size() - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < items.size(); j++) {

                if (items.get(j).getTitle().compareToIgnoreCase(items.get(minIndex).getTitle()) < 0) {

                    minIndex = j;
                }
            }

            LibraryItem temp = items.get(minIndex);
            items.set(minIndex, items.get(i));
            items.set(i, temp);
        }
    }
}