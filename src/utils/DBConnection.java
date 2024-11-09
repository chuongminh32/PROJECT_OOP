package utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Lớp quản lý kết nối cơ sở dữ liệu.
 * Kết nối với SQL Server.
 */
public class DBConnection {

    /**
     * Trả về kết nối tới cơ sở dữ liệu SQL Server.
     * @return Connection đối tượng kết nối.
     * @throws SQLException Nếu có lỗi khi kết nối cơ sở dữ liệu.
     * @throws ClassNotFoundException Nếu không tìm thấy driver SQL Server.
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Khởi tạo kết nối đến SQL Server
        return SQLServerConnection.initializeDatabase();
    }
}
