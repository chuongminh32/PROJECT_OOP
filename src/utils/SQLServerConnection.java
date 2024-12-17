/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLServerConnection {
    private static final Logger logger = Logger.getLogger(SQLServerConnection.class.getName());

    private static final String DB_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433";
    private static final String DB_NAME = "Project_OOP";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "261";
    private static final String CONNECTION_URL = DB_URL + ";databaseName=" + DB_NAME
            + ";encrypt=true;trustServerCertificate=true";

    public static Connection initializeDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(CONNECTION_URL, DB_USERNAME, DB_PASSWORD);
            logger.info("Kết nối thành công!");
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Kết nối thất bại!", ex);
            throw ex;
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "Không tìm thấy lớp Driver", ex);
            throw ex;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Có lỗi xảy ra trong quá trình kết nối", ex);
            throw ex;
        }
        return conn;

    }
}
