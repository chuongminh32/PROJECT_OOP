package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Lớp quản lý kết nối đến cơ sở dữ liệu SQL Server.
 */
public class SQLServerConnection {

    private static final Logger logger = Logger.getLogger(SQLServerConnection.class.getName());

    // Thông tin cấu hình kết nối (có thể đưa ra file cấu hình để dễ dàng quản lý)
    private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433";
    private static final String DB_NAME = "Project";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "2357";
    private static final String CONNECTION_URL = DB_URL + ";databaseName=" + DB_NAME + ";encrypt=true;trustServerCertificate=true";

    /**
     * Khởi tạo kết nối đến cơ sở dữ liệu SQL Server.
     *
     * @return Kết nối đến cơ sở dữ liệu.
     * @throws SQLException nếu có lỗi xảy ra trong quá trình kết nối.
     * @throws ClassNotFoundException nếu không tìm thấy lớp Driver cần thiết.
     */
    public static Connection initializeDatabase() throws SQLException, ClassNotFoundException {
        // Biến kết nối
        Connection conn = null;

        // Tải driver SQL Server
        try {
            Class.forName(DB_DRIVER);

            // Mở kết nối đến cơ sở dữ liệu
            conn = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
            logger.info("Kết nối thành công!");

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Kết nối thất bại!", ex);
            throw ex; // Ném lại lỗi để lớp gọi có thể xử lý
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "Không tìm thấy lớp Driver!", ex);
            throw ex; // N ém lại lỗi để lớp gọi có thể xử lý
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Có lỗi xảy ra trong quá trình kết nối!", ex);
            throw ex; // Ném lại lỗi để lớp gọi có thể xử lý
        }

        return conn;
    }
}
