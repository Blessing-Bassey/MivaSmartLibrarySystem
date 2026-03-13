package utils;

import model.Book;
import model.BorrowTransaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static void saveBooks(List<Book> books, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Book book : books) {
                writer.write(book.getItemId() + "," +
                        book.getTitle() + "," +
                        book.getAuthor() + "," +
                        book.getYear() + "," +
                        book.getQuantity() + "," +
                        book.getStatus() + "," +
                        book.getIsbn() + "," +
                        book.getPurchaseDate() + "," +
                        book.getDonationDate() + "," +
                        book.getSourceType());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    public static List<Book> loadBooks(String filename) {
        List<Book> books = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                Book book = new Book(
                        data[0],
                        data[1],
                        data[2],
                        Integer.parseInt(data[3]),
                        Integer.parseInt(data[4]),
                        data[5],
                        data[6],
                        data[7],
                        data[8],
                        data[9]
                );

                books.add(book);
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }

        return books;
    }

    public static void saveTransactions(List<BorrowTransaction> transactions, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (BorrowTransaction transaction : transactions) {
                writer.write(transaction.getTransactionId() + "," +
                        transaction.getBorrowerId() + "," +
                        transaction.getItemId() + "," +
                        transaction.getDateBorrowed() + "," +
                        transaction.getDueDate() + "," +
                        transaction.getDateReturned() + "," +
                        transaction.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    public static List<BorrowTransaction> loadTransactions(String filename) {
        List<BorrowTransaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                BorrowTransaction transaction = new BorrowTransaction(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        data[5],
                        data[6]
                );

                transactions.add(transaction);
            }
        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }

        return transactions;
    }
}