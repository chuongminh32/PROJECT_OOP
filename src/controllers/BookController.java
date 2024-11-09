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

/**
 *
 * @author chuon
 */
public class BookController {

    // print list book
    public static List<Book> printBook(Connection conn) throws SQLException, ClassNotFoundException {
        String sql_print = "SELECT * FROM dbo.Books";
        Statement stsm = conn.createStatement(); // tạo lớp truy vấn không có tham số 
        ResultSet rs = stsm.executeQuery(sql_print); // lưu dữ liệu từ sql vào rs 

        List<Book> list = new ArrayList<>();

        // lần lượt duyệt từng dòng dữ liệu trong db
        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("name");
            String author = rs.getString("author");
            String genre = rs.getString("genre");
            int puplishedYear = rs.getInt("publishYear");
            Boolean status = rs.getBoolean("status");

            Book newBook = new Book(id, name, author, genre, puplishedYear, status);
            list.add(newBook);
        }

        return list;
    }

    // insert new book
    public static void insertBook(Connection conn, Book b) throws SQLException, ClassNotFoundException {
        String sql_insert = "INSERT INTO Books(id, name, author, genre, publish, status ) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement prsm = conn.prepareStatement(sql_insert); // tạo lớp truy vấn có tham số

        // chèn đối số vào truy vấn 
        prsm.setString(1, b.getBookID());
        prsm.setString(2, b.getName());
        prsm.setString(3, b.getAuthor());
        prsm.setString(4, b.getGenre());
        prsm.setInt(5, b.getPublishYear());
        prsm.setBoolean(6, b.getStatus());

        // thực thi update dữ liệu
        int row = prsm.executeUpdate();
        if (row > 0) {
            System.out.print("Them thanh cong");
        }
        else {
            System.out.print("Them khong thanh cong");
        }
    }

    // find book by ID
    public static Book FindBookID(Connection conn, String id) throws SQLException, ClassNotFoundException {
        String sql_find = "SELECT * FROM Books WHERE id = ?";
        PreparedStatement prsm = conn.prepareStatement(sql_find);

        // chèn đối số id vào truy vấn 
        prsm.setString(1, id);

        // lấy dữ liệu thu thập được 
        ResultSet rs = prsm.executeQuery();

        // kiểm tra dữ liệu lấy được là khác null 
        if (rs.next()) {
            String name = rs.getString("name");
            String author = rs.getString("author");
            String genre = rs.getString("genre");
            int year = rs.getInt("publishYear");
            Boolean status = rs.getBoolean("satus");

            return new Book(id, name, author, genre, year, status);
        } else {
            return null;
        }
    }

    // delete book
    public static void DeleteBook(Connection conn, String id) throws SQLException, ClassNotFoundException {
        String sql_del = "DELETE FROM Books WHERE id = ?";
        PreparedStatement prsm = conn.prepareStatement(sql_del);

        // chèn đối số 
        prsm.setString(1, id);

        // sử lí xóa
        int rowAffected = prsm.executeUpdate();
        if (rowAffected > 0) {
            System.out.println("Delete Succesfull!");
        } else {
            System.out.println("Not found Book with id = " + id);
        }
    }

    // update book 
    public static void updateBook(Connection conn, Book newBook, String id) throws SQLException, ClassNotFoundException {
        Book oldBook = FindBookID(conn, id);
        if (oldBook != null) {
            String sql_update = "UPDATE book SET name = ?, author =  ?, genre = ?, publishYear = ?, status = ? WHERE id = ?";
            PreparedStatement prsm = conn.prepareStatement(sql_update);

            // chèn đối số 
            prsm.setString(1, newBook.getName());
            prsm.setString(2, newBook.getAuthor());
            prsm.setString(3, newBook.getGenre());
            prsm.setInt(4, newBook.getPublishYear());
            prsm.setBoolean(5, newBook.getStatus());
            prsm.setString(6, newBook.getBookID());

            // sử lí cập nhật
            int rowAffected = prsm.executeUpdate();
            if (rowAffected > 0) {
                System.out.println("Update Succesfull!");
            } else {
                System.out.println("Not found Book with id = " + id);
            }
        } else {
            System.out.print("Khong tim thay sach, da them sach moi vao!");
            insertBook(conn, newBook);
        }
    }
}
