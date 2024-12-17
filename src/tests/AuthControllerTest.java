///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package tests;
//
//import static controllers.HomePageLogic.getBook;
//import static controllers.HomePageLogic.getMember;
//import static controllers.HomePageLogic.getName;
//import java.sql.*;
//import controllers.MemberController;
//import utils.DBConnection;
//import java.util.List;
//import java.util.ArrayList;
//import java.util.Scanner;
//import javax.swing.JOptionPane;
//import models.Book;
//import models.Borrow;
//import models.Member;
//
///**
// *
// * @author chuon
// */
//public class AuthControllerTest {
//    public static List<Borrow> returnListBorrowData(String memberId) {
//        List<Borrow> list = new ArrayList<>();
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "SELECT id, bookId, borrowDate, duedate, returnDate, status FROM Borrow WHERE memberId = ? ";
//            PreparedStatement prsm = conn.prepareStatement(sql);
//            prsm.setString(1, memberId);
//            ResultSet rs = prsm.executeQuery();
//
//            while (rs.next()) {
//                // lay ten thanh vien
//                String nameMember = getName("Members", "memberId", memberId);
//                // lay ten sach
//                String bookId = rs.getString("bookId");
//                String nameBook = getName("Books", "bookId", bookId);
//                // lay du lieu 
//                int id = rs.getInt("id");
//                java.util.Date borrowDate = rs.getDate("borrowDate");
//                java.util.Date dueDate = rs.getDate("dueDate");
//                java.util.Date returnDate = rs.getDate("returnDate");
//                String status = rs.getString("status");
//                
//                // tao doi tuong moi qua id
//                Member newMember = getMember(memberId);
//                Book newBook = getBook(bookId);
//
//                Borrow newBorrow = new Borrow(id, newMember, newBook, borrowDate, returnDate, dueDate, status);
//                list.add(newBorrow);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu từ cơ sở dữ liệu!",
//                    "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return null;
//        }
//        return list;
//    }
////     public static Member getMember(String memberId) {
////        try (Connection conn = DBConnection.getConnection()) {
////            String sql = "SELECT id, name, email, phone, membershipDate FROM Members WHERE id = ?";
////            PreparedStatement prsm = conn.prepareStatement(sql);
////            prsm.setString(1, memberId);
////            ResultSet rs = prsm.executeQuery();
////
////            if (rs.next()) {
////                String id = rs.getString("id");
////                String name = rs.getString("name");
////                String email = rs.getString("email");
////                String phone = rs.getString("Phone");
////                java.util.Date membershipDate = rs.getDate("membershipDate");
////                Member newMember = new Member(id, name, email, phone, membershipDate);
////                return newMember;
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////            JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu từ cơ sở dữ liệu!",
////                    "Lỗi", JOptionPane.ERROR_MESSAGE);
////        }
////        return null;
////    }
//    
//
////    public static Book getBook(String bookId) {
////        try (Connection conn = DBConnection.getConnection()) {
////            String sql = "SELECT id, title, author, publisher, publishedYear, category, totalCopies, availableCopies FROM Books WHERE id = ?";
////            PreparedStatement prsm = conn.prepareStatement(sql);
////            prsm.setString(1, bookId);
////            ResultSet rs = prsm.executeQuery();
////
////            if (rs.next()) {
////                String id = rs.getString("id");
////                String title = rs.getString("title");
////                String author = rs.getString("author");
////                String publisher = rs.getString("publisher");
////                int publishedYear = rs.getInt("publishedYear");
////                int totalCopies = rs.getInt("totalCopies");
////                String category = rs.getString("category");
////                int availableCopies = rs.getInt("availableCopies");
////
////                Book newBook = new Book(id, title, author, publisher, publishedYear, category, totalCopies, availableCopies);
////                return newBook;
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////            JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu từ cơ sở dữ liệu!",
////                    "Lỗi", JOptionPane.ERROR_MESSAGE);
////        }
////        return null;
////    }
//
//
//        
//        
////        System.out.print(getMember("M001"));
////           String name = "";
////        
////            Connection conn = DBConnection.getConnection(); // Kết nối cơ sở dữ liệu
////            PreparedStatement pstmt = null;
////            ResultSet rs = null;
////            // Câu lệnh SQL để kiểm tra email và mật khẩu
////            String query = "SELECT * FROM staff WHERE email = ? AND password = ?";
////            pstmt = conn.prepareStatement(query); // Tạo câu lệnh SQL
////            pstmt.setString(1, "chuongminh32@staff.com"); // Thay thế dấu ? thứ nhất bằng email
////            pstmt.setString(2, "111"); // Thay thế dấu ? thứ hai bằng password
////
////            rs = pstmt.executeQuery(); // Thực thi câu lệnh SQL
////
////            // Kiểm tra kết quả truy vấn
////            if (rs.next()) {
////                name = rs.getString("name"); // Lấy thông tin từ cột name
////            }
////            System.out.print(name);
//
//                // test get id
////               String id = "";
////               try(Connection conn = DBConnection.getConnection()) {
////                   String sql = "SELECT * FROM Members WHERE name = ?";
////                   PreparedStatement prsm = conn.prepareStatement(sql);
////
////                   prsm.setString(1, "chuonguser");
////                   ResultSet rs = prsm.executeQuery();
////
////                   if (rs.next()) {
////                       id = rs.getString("id");
////                   }
////               }
////               catch (Exception e) {
////                       e.printStackTrace();
////                   }
////               System.out.print(id);
//
//                // test get book 
//         public static void main(String[] args) {
//         List<Borrow> list = new ArrayList<>();
//         list = returnListBorrowData("M001");
//        // Duyệt qua từng sản phẩm và in thông tin.
//        int stt = 1; // Biến đếm số thứ tự sản phẩm.
//        for (Borrow s : list) {
//            System.out.printf("stt: %d%n", stt++); // In số thứ tự sản phẩm.(d:int, n:newline)
//            System.out.print(s);
//            System.out.println(); // Dòng trống giữa các sản phẩm.
//        }
//    }
//                   
//   }
