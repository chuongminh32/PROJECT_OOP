//package controllers;
//
//import java.util.Date;
//import java.text.SimpleDateFormat;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import javax.swing.JOptionPane;
//import utils.DBConnection;
//
//public class AuthController {
//    
//    
//
//    // ham lay ten tu db thong qua email va password
//    public static String getName(String tableName, String email, String password) {
//        String name = "";
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "SELECT * FROM " + tableName + " WHERE email = ? AND password = ?";
//            PreparedStatement prsm = conn.prepareStatement(sql);
//           
//            prsm.setString(1, email);
//            prsm.setString(2, password);
//            
//            ResultSet rs = prsm.executeQuery();
//
//            if (rs.next()) {
//                name = rs.getString("name");
//            }
//
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Lỗi không tìm thấy driver!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//        return name;
//    }
//
//    public static boolean connectTableLogin(String tableName, String email, String password) {
//        // Kết nối cơ sở dữ liệu
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        try {
//            conn = DBConnection.getConnection(); // Kết nối cơ sở dữ liệu
//
//            // Câu lệnh SQL để kiểm tra email và mật khẩu
//            String query = "SELECT * FROM " + tableName + " WHERE email = ? AND password = ?";
//            pstmt = conn.prepareStatement(query); // Tạo câu lệnh SQL
//            pstmt.setString(1, email); // Thay thế dấu ? thứ nhất bằng email
//            pstmt.setString(2, password); // Thay thế dấu ? thứ hai bằng password
//
//            rs = pstmt.executeQuery(); // Thực thi câu lệnh SQL
//
//            // Kiểm tra kết quả truy vấn
//            if (rs.next()) {
//                return true; // Đăng nhập thành công
//            } else {
//                JOptionPane.showMessageDialog(null, "Sai mật khẩu hoặc tên đăng nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//                return false; // Đăng nhập thất bại
//            }
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Lỗi không tìm thấy driver!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        } finally {
//            // Đảm bảo đóng kết nối sau khi hoàn tất
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return false; // Mặc định trả về false nếu xảy ra lỗi
//    }
//
//    public static boolean login(String email, String password) {
//        // Kiểm tra lỗi
//        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
//            return false; // Ngừng thực hiện nếu dữ liệu không hợp lệ
//        }
//
//        // check mail -> connect toi dung bang can kiem tra email & pass
//        boolean result = false;
//        if (email.endsWith("@user.com")) {
//            result = connectTableLogin("Members", email, password);
//        } else if (email.endsWith("@staff.com")) {
//            result = connectTableLogin("Staff", email, password);
//        } else {
//            JOptionPane.showMessageDialog(null, "Email không đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//        return result;
//    }
//
//    public static boolean connectTableRegister(String tableName, String name, String email, String phone, String password) {
//
//        // Kết nối cơ sở dữ liệu
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//
//        // Kết nối tới database
//        try {
//            conn = DBConnection.getConnection(); // Kết nối cơ sở dữ liệu
//            int cnt = HomePageLogic.getCount(tableName); // so dong hien tai trong bang du lieu
//            // Câu lệnh SQL để kiểm tra email và mật khẩu
//            String query = "INSERT INTO " + tableName + " (id, name, email, phone, password, membershipDate) VALUES(?, ?, ?, ?, ?, ?)";
//            pstmt = conn.prepareStatement(query); // Tạo câu lệnh SQL
//            // Tạo ID theo định dạng "M0" + số thứ tự dòng
//            String id = "M0" + String.valueOf(cnt);
//
//            // Lấy ngày hiện tại
//            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//
//            pstmt.setString(1, id);
//            pstmt.setString(2, name);
//            pstmt.setString(3, email);
//            pstmt.setString(4, phone);
//            pstmt.setString(5, password);
//            pstmt.setString(6, currentDate);
//
//            int rowsAffected = pstmt.executeUpdate(); // Thực thi câu lệnh SQL
//
//            if (rowsAffected > 0) {
//                return true; // Đăng ký thành công
//            } else {
//                return false; // Đăng ký thất bại
//            }
//        } catch (ClassNotFoundException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Lỗi không tìm thấy driver!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        } finally {
//            // Đảm bảo đóng kết nối sau khi hoàn tất
//            try {
//                if (pstmt != null) {
//                    pstmt.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return false; // Mặc định trả về false nếu xảy ra lỗi
//    }
//
//    // register method
//    public static boolean register(String name, String password, String email, String phone) {
//
//        // Kiểm tra xem email và password có bị rỗng không
//        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()
//                || phone == null || phone.trim().isEmpty() || name == null || name.trim().isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return false; // Ngừng thực hiện nếu dữ liệu không hợp lệ
//        } else if (!email.endsWith("@user.com")) {
//            JOptionPane.showMessageDialog(null, "Nhập sai định dạng email: " + email + " !", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return false; // Ngừng thực hiện nếu dữ liệu không hợp lệ
//        } else if (phone == null || !phone.matches("\\d{10}")) {
//            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ: " + phone + " !", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return false; // Ngừng thực hiện nếu dữ liệu không hợp lệ
//        } else if (email.endsWith("staff.com")) {
//            JOptionPane.showMessageDialog(null, "Email không được trùng với tài khoản admin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return false; // Ngừng thực hiện nếu email trùng với tài khoản admin
//        }
//
//        return (connectTableRegister("Members", name, email, phone, password));
//
//    }
//
//}
