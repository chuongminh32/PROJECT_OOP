/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.util.List;
import java.util.ArrayList;
import java.sql.Statement; // truy vấn không tham số
import java.sql.PreparedStatement; // truy vấn có tham số
import java.sql.Connection; // kết nối db
import java.sql.SQLException; // lỗi kết nối sql
import java.sql.ResultSet; // lưu dữ liệu db

import models.Book; // import class Book
import utils.DBConnection; // import class DBConnection

/**
 *
 * @author chuon
 */
public class BookController {

    public List<Book> getAllBooks() {
        String query = "SELECT * FROM Books";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            List <Book> books = new ArrayList<>();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                int publishedYear = resultSet.getInt("publishedYear");
                String category = resultSet.getString("category");
                int totalCopies = resultSet.getInt("totalCopies");
                int availableCopies = resultSet.getInt("availableCopies");

                Book book = new Book(id, title, author, publisher, publishedYear, category, totalCopies,
                        availableCopies);
                books.add(book);
            }

            return books;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>(); // return an empty list
        }
    }

    public void deleteBook(String id) {
        String query = "DELETE FROM Books WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Book deleted successfully!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addBook(String id, String title, String author, String publisher, int publishedYear, String category,
            int totalCopies, int availableCopies) {
        String query = "INSERT INTO Books (id, title, author, publisher, publishedYear, category, totalCopies, availableCopies) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, id);
            statement.setString(2, title);
            statement.setString(3, author);
            statement.setString(4, publisher);
            statement.setInt(5, publishedYear);
            statement.setString(6, category);
            statement.setInt(7, totalCopies);
            statement.setInt(8, availableCopies);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new book was inserted successfully!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(String id, String title, String author, String publisher, int publishedYear, String category,
            int totalCopies, int availableCopies) {
        String query = "UPDATE Books SET title = ?, author = ?, publisher = ?, publishedYear = ?, category = ?, totalCopies = ?, availableCopies = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, publisher);
            statement.setInt(4, publishedYear);
            statement.setString(5, category);
            statement.setInt(6, totalCopies);
            statement.setInt(7, availableCopies);
            statement.setString(8, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Book updated successfully!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
