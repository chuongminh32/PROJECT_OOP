/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import models.Member;
import models.Book;


import models.Borrow;
import javax.swing.JOptionPane;

import utils.DBConnection;

/**
 *
 * @author chuon
 */
public class HomePageLogic {
    
    // them du lieu muon sach cua user vao bang:
    

    // lay doi tuong member qua id
    public static Member getMember(String memberId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, name, email, phone, membershipDate FROM Members WHERE id = ?";
            PreparedStatement prsm = conn.prepareStatement(sql);
            prsm.setString(1, memberId);
            ResultSet rs = prsm.executeQuery();

            if (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("Phone");
                Date membershipDate = rs.getDate("membershipDate");
                Member newMember = new Member(id, name, email, phone, membershipDate);
                return newMember;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu từ cơ sở dữ liệu!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    // lay doi tuong sach qua id
    public static Book getBook(String bookId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, title, author, publisher, publishedYear, category, totalCopies, availableCopies FROM Books WHERE id = ?";
            PreparedStatement prsm = conn.prepareStatement(sql);
            prsm.setString(1, bookId);
            ResultSet rs = prsm.executeQuery();

            if (rs.next()) {
                String id = rs.getString("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                int publishedYear = rs.getInt("publishedYear");
                int totalCopies = rs.getInt("totalCopies");
                String category = rs.getString("category");
                int availableCopies = rs.getInt("availableCopies");

                Book newBook = new Book(id, title, author, publisher, publishedYear, category, totalCopies, availableCopies);
                return newBook;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu từ cơ sở dữ liệu!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    // lay ten qua ten bang, ten cot(truong), id
    public static String getName(String tableName, String nameField, String id) {
        String name = "";
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM " + tableName + " WHERE " + nameField + " = ?";

            PreparedStatement prsm = conn.prepareStatement(sql);
            prsm.setString(1, id);
            ResultSet rs = prsm.executeQuery();

            if (rs.next()) {
                if (tableName == "Books") {
                    name = rs.getString("title");
                }
                else {
                    name = rs.getString("name");
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu từ cơ sở dữ liệu!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return name;
    }

    // return list borrow (thong tin muon tra cho user)
    public static List<Borrow> returnListBorrowData(String memberId) {
        List<Borrow> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, bookId, borrowDate, duedate, returnDate, status FROM Borrow WHERE memberId = ? ";
            PreparedStatement prsm = conn.prepareStatement(sql);
            prsm.setString(1, memberId);
            ResultSet rs = prsm.executeQuery();

            while (rs.next()) {
                // lay ten thanh vien
                // String nameMember = getName("Members", "id", memberId);
                // lay ten sach
                String bookId = rs.getString("bookId"); // ma sach
                // String nameBook = getName("Books", "id", bookId);
                // lay du lieu trong bang borrow
                int id = rs.getInt("id");
                Date borrowDate = rs.getDate("borrowDate");
                Date dueDate = rs.getDate("dueDate");
                Date returnDate = rs.getDate("returnDate");
                String status = rs.getString("status");
                
                // tao doi tuong moi qua id
                Member newMember = getMember(memberId);
                Book newBook = getBook(bookId);
                
                // them vao list
                Borrow newBorrow = new Borrow(id, newMember, newBook, borrowDate, returnDate, dueDate, status);
                list.add(newBorrow);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu từ cơ sở dữ liệu!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    // lay id tu ten bang va name 
    public static String getId(String tableName, String name) {
        String id = "";
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM " + tableName + " WHERE name = ?";
            PreparedStatement prsm = conn.prepareStatement(sql);

            prsm.setString(1, name);
            ResultSet rs = prsm.executeQuery();

            if (rs.next()) {
                id = rs.getString("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu từ cơ sở dữ liệu!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return id;
    }

    // hien thi so luong sach trang home admin
    public static int getCount(String nameTable) {
        int cnt = 0;

        if (nameTable == "Borrow") {
            String sql = "SELECT COUNT(*) AS total FROM Borrow WHERE status = ?";
            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "Da tra");

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    cnt = rs.getInt("total");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu từ cơ sở dữ liệu!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else if (nameTable == "Books") {
            String query = "SELECT SUM(availableCopies) FROM Books";
            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(query);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    cnt = rs.getInt(1); // Lấy kết quả tổng số sách từ cột đầu tiên
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu từ cơ sở dữ liệu!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else if (nameTable == "Members") {
            // Truy vấn SQL để đếm số memberId khác nhau
            String query = "SELECT COUNT(DISTINCT memberId) FROM Borrow";
            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(query);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    cnt = rs.getInt(1); // Lấy kết quả tổng số sách từ cột đầu tiên
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu từ cơ sở dữ liệu!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            String sql = "SELECT COUNT(*) FROM " + nameTable;
            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    cnt = rs.getInt(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu từ cơ sở dữ liệu!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        return cnt;
    }

    // hien thi so luong sach cua member (trang user) thong qua id : lay id tu bang member thong name , bo vao memberid cua bang borrow -> lay so luong
    public static int getCountUser(String nameTable, String id, String status) {
        int cnt = 0;
        String sql = "SELECT COUNT(*) FROM Borrow WHERE memberId = ?";

        // Nếu có status, thêm điều kiện vào câu lệnh SQL
        if (status != null && !status.isEmpty()) {
            sql += " AND status = ?";
        }

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);  // Gán giá trị cho memberId

            // Nếu có status, gán giá trị cho tham số thứ hai
            if (status != null && !status.isEmpty()) {
                ps.setString(2, status);
            }

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cnt = rs.getInt(1);  // Lấy giá trị từ cột đầu tiên (do COUNT(*) trả về 1 cột duy nhất)
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu từ cơ sở dữ liệu!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return cnt;
    }

//    
// public static void main(String[] args) {
//     int cnt = 0;
//        String query = "SELECT SUM(availableCopies) FROM Books";
//              try (Connection conn = DBConnection.getConnection()) {
//                  PreparedStatement ps = conn.prepareStatement(query);
//                  
//                  ResultSet rs = ps.executeQuery();
//                  
//                  if (rs.next()) {
//                     cnt = rs.getInt(1);
//                  }
//              }
//              catch (Exception e) {
//                e.printStackTrace();
//                JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu từ cơ sở dữ liệu!",
//                        "Lỗi", JOptionPane.ERROR_MESSAGE);
//            }
//              System.out.print(cnt);
//    }
    
//    test borrow data 
//     public static void main(String[] args) {
//         List<Borrow> list = new ArrayList<>();
//         list = returnListBorrowData("M001");
//        // Duyệt qua từng sản phẩm và in thông tin.
//        int stt = 1; // Biến đếm số thứ tự sản phẩm.
//        for (Borrow s : list) {
//            System.out.printf("stt: %d%n", stt++); // In số thứ tự sản phẩm.(d:int, n:newline)
//            System.out.print(s);
//            System.out.println(); // Dòng trống giữa các sản phẩm.
//        }
//     }
}
