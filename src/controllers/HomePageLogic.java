/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.sql.*;
import java.sql.Connection;
import java.time.LocalDate;
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
    
    // chinh sua thong tin ca nhan
    public static Boolean editInfo(String id, String name, String email, String phone, String pass) {
    String sql = "UPDATE Members SET name = ?, email = ?, phone = ?, password = ? WHERE id = ?";
    try (Connection conn = DBConnection.getConnection()) {
        PreparedStatement prsm = conn.prepareStatement(sql);
        prsm.setString(1, name);
        prsm.setString(2, email);
        prsm.setString(3, phone);
        prsm.setString(4, pass);
        prsm.setString(5, id);
        
        int r = prsm.executeUpdate(); // Use executeUpdate() for modifying data
        if (r > 0) return true; // If rows affected, return true
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace(); // Log the exception for debugging
    }
    return false; // Return false if update failed or exception occurred
}

    // lay sach theo ten
    public static Book getBookWithName(String name) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, title, author, publisher, publishedYear, category, totalCopies, availableCopies FROM Books WHERE title = ?";
            PreparedStatement prsm = conn.prepareStatement(sql);
            prsm.setString(1, name);
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

    // loc du lieu theo commboBox 
    public static List<Book> filterData(String nameField, String val) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books WHERE " + nameField + " = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement prsm = conn.prepareStatement(sql)) {

            // Set the parameter value in the PreparedStatement
            prsm.setString(1, val);

            // Execute the query
            ResultSet rs = prsm.executeQuery();

            // Process the result set
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getString("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setPublishedYear(rs.getInt("publishedYear"));
                book.setCategory(rs.getString("category"));
                book.setTotalCopies(rs.getInt("totalCopies"));
                book.setAvailableCopies(rs.getInt("availableCopies"));
                books.add(book);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Log the exception or handle it appropriately
        }

        return books;
    }

    // lay du lieu khong trung trong Books de tao CommboBox 
    public static List<String> getDataNotDuplicates(String nameField) {
        List<String> s = new ArrayList<>(); // Danh sách để lưu các giá trị không trùng

        // Câu lệnh SQL để chọn dữ liệu không trùng lặp
        String sql = "SELECT DISTINCT " + nameField + " FROM Books";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement prsm = conn.prepareStatement(sql); ResultSet rs = prsm.executeQuery()) {

            // Lặp qua kết quả và thêm vào danh sách
            while (rs.next()) {
                s.add(rs.getString(nameField)); // Thêm giá trị vào danh sách không trùng
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi không tìm thấy lớp: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        return s; // Trả về danh sách đã lọc
    }

    // ham tinh tien phat 
    public static int cashFine(String username) {
        String sql = "SELECT returnDate, dueDate FROM Borrow WHERE memberId = ?";
        int finePerDay = 50000; // Tiền phạt 50k/ngày
        int totalFine = 0;

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Lấy memberId từ username
            String memberId = HomePageLogic.getId("Members", username);
            pstmt.setString(1, memberId);

            // Thực thi truy vấn
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Date returnDate = rs.getDate("returnDate"); // ngay tra thuc te (có thể null)
                Date dueDate = rs.getDate("dueDate"); // ngay tra du kien
                if (returnDate == null) {
                    returnDate = new Date(System.currentTimeMillis()); // Nếu returnDate là null -> Lấy ngày hôm nay
                }
                // So sánh ngày và tính số ngày trễ hạn
                if (returnDate != null && dueDate != null && returnDate.after(dueDate)) {
                    long diffInMillies = returnDate.getTime() - dueDate.getTime();
                    long daysLate = diffInMillies / (1000 * 60 * 60 * 24); // Chuyển từ mili giây sang ngày

                    // Tính tiền phạt
                    int fine = (int) (daysLate * finePerDay);
                    totalFine += fine;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Lỗi kết nối cơ sở dữ liệu!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Đã xảy ra lỗi trong quá trình tính tiền phạt!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
        return totalFine;
    }

    // ham check user da muon sach do hay chua 
    public static Boolean daMuonSach(String bookId, String memberId) {
        try (Connection conn = DBConnection.getConnection()) {
            // Truy vấn kiểm tra sách đã được mượn bởi thành viên này hay chưa
            String sql = "SELECT * FROM Borrow WHERE bookId = ? AND memberId = ? AND returnDate IS NULL";
            PreparedStatement prsm = conn.prepareStatement(sql);
            prsm.setString(1, bookId);    // Đặt giá trị cho bookId
            prsm.setString(2, memberId); // Đặt giá trị cho memberId

            ResultSet rs = prsm.executeQuery();
            // Nếu tồn tại bản ghi chưa trả, sách đã được mượn
            if (rs.next()) {
                return true; // Đã mượn
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Chưa mượn
    }

    // ham giam so luong sach 
    public static Boolean daGiamSoSach(String nameBook) {
        try (Connection conn = DBConnection.getConnection()) {
            // Truy vấn để lấy số lượng bản sao khả dụng
            String s = "SELECT availableCopies FROM Books WHERE title = ?";
            PreparedStatement p = conn.prepareStatement(s);
            p.setString(1, nameBook);

            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                int a = rs.getInt("availableCopies");

                // Kiểm tra nếu còn sách khả dụng
                if (a > 0) {
                    // Cập nhật số lượng sách
                    String sql = "UPDATE Books SET availableCopies = availableCopies - 1 WHERE title = ?";
                    PreparedStatement prsm = conn.prepareStatement(sql);
                    prsm.setString(1, nameBook);

                    int r = prsm.executeUpdate();
                    if (r > 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                System.out.println("Không tìm thấy sách với tiêu đề: " + nameBook);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Đã xảy ra lỗi khi giảm số lượng sách!");
        }
        return false;
    }

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
                } else {
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

    // return list book data 
    public static List<Book> returnListBookData() {
        List<Book> l = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Books";
            PreparedStatement prsm = conn.prepareStatement(sql);
            ResultSet rs = prsm.executeQuery();

            while (rs.next()) {

                String id = rs.getString("id");
                String t = rs.getString("title");
                String a = rs.getString("author");
                String p = rs.getString("publisher");
                int py = rs.getInt("publishedYear");
                String c = rs.getString("category");
                int av = rs.getInt("availableCopies");
                int to = rs.getInt("totalCopies");

                Book b = new Book(id, t, a, p, py, c, to, av);
                l.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể tải dữ liệu từ cơ sở dữ liệu!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return l;
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

                // tao doi tuong moi qua id : member va book
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
            String sql = "";
            if (tableName == "Books") {
                sql = "SELECT * FROM " + tableName + " WHERE title = ?";
            } else {
                sql = "SELECT * FROM " + tableName + " WHERE name = ?";
            }
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
