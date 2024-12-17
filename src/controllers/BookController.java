/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.util.List;
import java.util.ArrayList;
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

    public List<Book> getAllBooks() throws SQLException {
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

    public boolean canDeleteBook(String bookId) throws SQLException, ClassNotFoundException {
        String checkQuery = "SELECT COUNT(*) FROM Borrow WHERE bookId = ?";
        
        try (Connection connection = DBConnection.getConnection();
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {

            checkStatement.setString(1, bookId);
            ResultSet rs = checkStatement.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }
            return true;
        }
    }

    public void deleteBook(String bookId) throws SQLException, ClassNotFoundException {
        String deleteQuery = "DELETE FROM Books WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {

            deleteStatement.setString(1, bookId);
            int rowsDeleted = deleteStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Book deleted successfully!");
            }
        } catch (SQLException e) {
            //NẾU ĐANG CÓ NGƯỜI MƯỢN SÁCH THÌ KHÔNG THỂ XÓA SÁCH ĐÓ ĐƯỢC
            throw new SQLException("Cannot delete book. There are related records in the Borrow table.");
        }
    }


    public void insertBook(Book book)  throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Books (id, title, author, publisher, publishedYear, category, totalCopies, availableCopies) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, book.getId());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getPublisher());
            statement.setInt(5, book.getPublishedYear());
            statement.setString(6, book.getCategory());
            statement.setInt(7, book.getTotalCopies());
            statement.setInt(8, book.getAvailableCopies());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Book insert successfully!");
            }
            else System.out.println("Can't insert record in table!");
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Book book, String id) throws SQLException, ClassNotFoundException {
        String query = "UPDATE Books SET title = ?, author = ?, publisher = ?, publishedYear = ?, category = ?, totalCopies = ?, availableCopies = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getPublishedYear());
            statement.setString(5, book.getCategory());
            statement.setInt(6, book.getTotalCopies());
            statement.setInt(7, book.getAvailableCopies());
            statement.setString(8, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Book updated successfully!");
                }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public List<Book> searchBooks(String id, String title, String author, String publisher, int publishedYear, String category, int totalCopies, int availableCopies) throws SQLException, ClassNotFoundException {
        StringBuilder sql = new StringBuilder("SELECT * FROM Books WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        if (id != null && !id.trim().isEmpty()) {
            sql.append(" AND id LIKE ?");
            parameters.add("%" + id.trim() + "%");
        }
        if (title != null && !title.trim().isEmpty()) {
            sql.append(" AND title LIKE ?");
            parameters.add("%" + title.trim() + "%");
        }
        if (author != null && !author.trim().isEmpty()) {
            sql.append(" AND author LIKE ?");
            parameters.add("%" + author.trim() + "%");
        }
        if (publisher != null && !publisher.trim().isEmpty()) {
            sql.append(" AND publisher LIKE ?");
            parameters.add("%" + publisher.trim() + "%");
        }
        if (publishedYear != -1) {
            sql.append(" AND publishedYear LIKE ?");
            parameters.add("%" + publishedYear + "%");
        }
        if (category != null && !category.trim().isEmpty()) {
            sql.append(" AND category LIKE ?");
            parameters.add("%" + category.trim() + "%");
        }
        if (totalCopies != -1) {
            sql.append(" AND totalCopies LIKE ?");
            parameters.add("%" + totalCopies + "%");
        }
        if (availableCopies != -1) {
            sql.append(" AND availableCopies LIKE ?");
            parameters.add("%" + availableCopies + "%");
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = statement.executeQuery()) {
                List<Book> books = new ArrayList<>();
                while (rs.next()) {
                    String bookId = rs.getString("id");
                    String bookTitle = rs.getString("title");
                    String bookAuthor = rs.getString("author");
                    String bookPublisher = rs.getString("publisher");
                    int bookPublishedYear = rs.getInt("publishedYear");
                    String bookCategory = rs.getString("category");
                    int bookTotalCopies = rs.getInt("totalCopies");
                    int bookAvailableCopies = rs.getInt("availableCopies");

                    Book book = new Book(bookId, bookTitle, bookAuthor, bookPublisher, bookPublishedYear, bookCategory, bookTotalCopies, bookAvailableCopies);
                    books.add(book);
                }
                return books;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}
