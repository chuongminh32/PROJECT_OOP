package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import utils.DBConnection;

public class AuthController {
    
    // login method
    public static boolean login(String email, String password) {

        // Kiểm tra xem email và password có bị rỗng không
    if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
        return false; // Ngừng thực hiện nếu dữ liệu không hợp lệ
    }

    // Kết nối cơ sở dữ liệu
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    // Kết nối tới database
    try {
        conn = DBConnection.getConnection(); // Kết nối cơ sở dữ liệu

        // Câu lệnh SQL để kiểm tra email và mật khẩu
        String query = "SELECT * FROM account WHERE email = ? AND password = ?";
        pstmt = conn.prepareStatement(query); // Tạo câu lệnh SQL
        pstmt.setString(1, email); // Thay thế dấu ? thứ nhất bằng email
        pstmt.setString(2, password); // Thay thế dấu ? thứ hai bằng password

        rs = pstmt.executeQuery(); // Thực thi câu lệnh SQL

        // Kiểm tra kết quả truy vấn
        if (rs.next()) {
            return true; // Đăng nhập thành công
        } else {
            return false; // Đăng nhập thất bại
        }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi không tìm thấy driver!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Đảm bảo đóng kết nối sau khi hoàn tất
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false; // Mặc định trả về false nếu xảy ra lỗi
    }

    // register method
    public static boolean register(String email, String password, String confirmPassword) {

        // Kiểm tra xem email và password có bị rỗng không
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return false; // Ngừng thực hiện nếu dữ liệu không hợp lệ
        }
        else if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Mật khẩu không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false; // Ngừng thực hiện nếu mật khẩu không khớp
        }
        else if (email.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Email không được trùng với mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false; // Ngừng thực hiện nếu email trùng với mật khẩu
        }

        else if (email.equals("admin")) {
            JOptionPane.showMessageDialog(null, "Email không được trùng với tài khoản admin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false; // Ngừng thực hiện nếu email trùng với tài khoản admin
        }

        // check email không đúng định dạng
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            JOptionPane.showMessageDialog(null, "Email không đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false; // Ngừng thực hiện nếu email không đúng định dạng
        }
        
        // Kết nối cơ sở dữ liệu
        Connection conn = null;
        PreparedStatement pstmt = null;

        // Kết nối tới database
        try {
            conn = DBConnection.getConnection(); // Kết nối cơ sở dữ liệu

            // Câu lệnh SQL để kiểm tra email và mật khẩu
            String query = "INSERT INTO account(email, password) VALUES(?, ?)";
            pstmt = conn.prepareStatement(query); // Tạo câu lệnh SQL
            pstmt.setString(1, email); // Thay thế dấu ? thứ nhất bằng email
            pstmt.setString(2, password); // Thay thế dấu ? thứ hai bằng password

            int rowsAffected = pstmt.executeUpdate(); // Thực thi câu lệnh SQL

            if (rowsAffected > 0) {
                return true; // Đăng ký thành công
            } else {
                return false; // Đăng ký thất bại
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi không tìm thấy driver!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Đảm bảo đóng kết nối sau khi hoàn tất
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false; // Mặc định trả về false nếu xảy ra lỗi
    }
}
