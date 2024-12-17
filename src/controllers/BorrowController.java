//package controllers;
//
//import java.sql.*;
//import java.util.*;
//import models.Borrow;
//import utils.DBConnection;
//import java.sql.Date;
//import models.Book;
//import models.Member;
//
//public class BorrowController {
//
//  private Connection connection;
//
//  // Constructor - kết nối cơ sở dữ liệu
//  public BorrowController() {
//    try {
//      this.connection = DBConnection.getConnection();
//    } catch (SQLException | ClassNotFoundException e) {
//      e.printStackTrace();
//      System.out.println(e.getMessage());
//    }
//  }
//
//  // test connection
//  public void testConnection() {
//    if (this.connection != null) {
//      System.out.println("Connected to database.");
//    } else {
//      System.out.println("Connection failed.");
//    }
//  }
//
//  public static Book getBook(Connection conn, String bookId)
//      throws SQLException {
//    String sql = "Select * from Books where id=?";
//    PreparedStatement pstm = conn.prepareStatement(sql);
//    pstm.setString(1, bookId);
//    ResultSet rs = pstm.executeQuery();
//    while (rs.next()) {
//      String title = rs.getString("title");
//      String author = rs.getString("author");
//      String publisher = rs.getString("publisher");
//      int publishedyear = rs.getInt("publishedyear");
//      String category = rs.getString("category");
//      int totalCopies = rs.getInt("totalCopies");
//      int availableCopies = rs.getInt("availableCopies");
//      Book k = new Book(bookId, title, author, publisher, publishedyear, category, totalCopies, availableCopies);
//
//      return k;
//    }
//    return null;
//  }
//
//  public static Member getMember(Connection conn, String memberId)
//      throws SQLException {
//    String sql = "Select * from Member where id=?";
//    PreparedStatement pstm = conn.prepareStatement(sql);
//    pstm.setString(1, memberId);
//    ResultSet rs = pstm.executeQuery();
//    while (rs.next()) {
//      String name = rs.getString("name");
//      String email = rs.getString("email");
//      String phone = rs.getString("phone");
//      Date membershipDate = rs.getDate("membershipDate");
//      String password = rs.getString("password");
//
//      Member m = new Member(memberId, name, email, phone, membershipDate, password);
//
//      return m;
//    }
//    return null;
//  }
//
//  // in danh sach Mượn trả
//  public static List<Borrow> PrintList_MuonTra(Connection conn) throws SQLException {
//    List<Borrow> list = new ArrayList<>();
//    String sql = "SELECT * FROM Borrow";
//    try (Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery(sql)) {
//      while (rs.next()) {
//        Borrow b = new Borrow();
//        b.setId(rs.getInt("id"));
//        String bookId = rs.getString("bookId");
//        Book book = getBook(conn, bookId);
//        b.setBook(book);
//        String memberId = rs.getString("memberId");
//        Member mem = getMember(conn, memberId);
//        b.setMember(mem);
//        b.setBorrowDate(rs.getDate("borrowDate"));
//        b.setReturnDate(rs.getDate("returnDate"));
//        b.setDueDate(rs.getDate("dueDate"));
//        b.setStatus(rs.getString("status"));
//        list.add(b);
//      }
//    }
//    return list;
//  }
//
//  // Thêm mới
//  public boolean addBorrow(int id, String memberId, String bookId, Date borrowDate, Date returnDate, Date dueDate,
//      String status) {
//    String sql = "INSERT INTO Borrow (id, bookId, memberId, borrowDate, dueDate, returnDate, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
//    try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
//      statement.setInt(1, id);
//      statement.setString(2, bookId);
//      statement.setString(3, memberId);
//      statement.setDate(4, borrowDate);
//      statement.setDate(5, dueDate);
//      statement.setDate(6, returnDate);
//      statement.setString(7, status);
//
//      int rowsAffected = statement.executeUpdate();
//      return rowsAffected > 0; // Trả về true nếu đã thêm thành công
//    } catch (SQLException e) {
//      System.out.println("Error adding into Borrow: " + e.getMessage());
//      return false;
//    }
//  }
//
//  // Cập nhật thông tin Mượn_Trả
//  public boolean updateBorrow(int id, String memberId, String bookId, Date borrowDate, Date returnDate, Date dueDate,
//      String status) {
//    String sql = "UPDATE Borrow SET bookId = ?, memberId = ?, borrowDate = ?, dueDate = ?, returnDate=?, status=? WHERE id = ?";
//
//    try (PreparedStatement statement = connection.prepareStatement(sql)) {
//      statement.setString(1, bookId);
//      statement.setString(2, memberId);
//      statement.setDate(3, borrowDate);
//      statement.setDate(4, dueDate);
//      statement.setDate(5, returnDate);
//      statement.setString(6, status);
//      statement.setInt(7, id);
//
//      int rowsAffected = statement.executeUpdate();
//      return rowsAffected > 0; // Trả về true nếu đã cập nhật thành công
//    } catch (SQLException e) {
//      System.out.println("Error updating member: " + e.getMessage());
//      return false;
//    }
//  }
//
//  // Xóa
//  public boolean deleteBorrow(int id) {
//    String sql = "DELETE FROM Borrow WHERE id = ?";
//
//    try (PreparedStatement statement = connection.prepareStatement(sql)) {
//      statement.setInt(1, id);
//
//      int rowsAffected = statement.executeUpdate();
//      return rowsAffected > 0; // Trả về true nếu đã xóa thành công
//    } catch (SQLException e) {
//      System.out.println("Error deleting member: " + e.getMessage());
//      return false;
//    }
//  }
//
//  // Lấy thông tin thành viên theo memberId
//  public Borrow getBorrowBymemId(Connection conn, String memberId) {
//    String sql = "SELECT * FROM Borrow WHERE memberId = ?";
//
//    try (PreparedStatement statement = connection.prepareStatement(sql)) {
//      statement.setString(1, memberId);
//
//      try (ResultSet rs = statement.executeQuery()) {
//        if (rs.next()) {
//          int id = rs.getInt("id");
//          String bookId = rs.getString("bookId");
//          Book book = getBook(conn, bookId);
//          Member mem = getMember(conn, memberId);
//          Date borrowDate = rs.getDate("borrowDate");
//          Date dueDate = rs.getDate("dueDate");
//          Date returnDate = rs.getDate("returnDate");
//          String status = rs.getString("status");
//
//          Borrow b = new Borrow(id, mem, book, borrowDate, returnDate, dueDate, status);
//          return b;
//        }
//      }
//    } catch (SQLException e) {
//      System.out.println("Error retrieving member by id: " + e.getMessage());
//    }
//
//    return null; // Trả về null nếu không tìm thấy
//  }
//
//}
