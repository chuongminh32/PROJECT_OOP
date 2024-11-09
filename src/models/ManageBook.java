/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.util.Scanner;

import controllers.BookController;
import utils.DBConnection;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author chuon
 */
public class ManageBook {
    
    Scanner sc = new Scanner(System.in);
    private List<Book> list = new ArrayList<>();
    
    public void printListBook() throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.getConnection();
        this.list = BookController.printBook(conn);
        
        // Duyệt qua từng sản phẩm và in thông tin.
        int stt = 1; // Biến đếm số thứ tự sản phẩm.
        for (Book b : list) {
            System.out.printf("stt: %d%n", stt++); // In số thứ tự sản phẩm.
            b.outputInformationBook(); // In thông tin sản phẩm.
            System.out.println(); // Dòng trống giữa các sản phẩm.
        }
    }
    
//    public void addBook() throws SQLException, ClassNotFoundException {
//        System.out.println("Nhập thông tin cho sách mới:");
//        Book b = new Book();
//        b.NhapBook();
//        Connection conn = DBConnection.getConnection();
//        DBUtils.InsertBook(conn, b);
//    }
//    
//    public void delBook() throws SQLException, ClassNotFoundException {
//        System.out.print("Nhập mã sách cần xóa: ");
//        String id = sc.nextLine();
//        Connection conn = DBConnection.getConnection();
//        DBUtils.DeleteBook(conn, id);
//    }
//    
//    public void updateBook() throws SQLException, ClassNotFoundException {
//        System.out.print("Nhập mã sách cần update: ");
//        String id = sc.nextLine();
//        System.out.println("Nhập thông tin cập nhật cho sách: ");
//        Book b = new Book();
//        b.NhapBook();
//        Connection conn = DBConnection.getConnection();
//        DBUtils.UpdateBook(conn, b, id);
//    }
//    
//    public void findBookID() throws SQLException, ClassNotFoundException {
//        System.out.print("Nhập mã sách cần tìm: ");
//        String id = sc.nextLine();
//        Connection conn = DBConnection.getConnection();
//        Book b = DBUtils.FindBookID(conn, id);
//        if (b!=null) {
//            System.out.println("Đã tìm thấy Sách có ID = " + id);
//            b.XuatBook();
//        }
//        else {
//            System.out.println("Không tìm thấy sách có id = " + id);
//        }
//    }
}
