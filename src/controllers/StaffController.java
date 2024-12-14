package controllers;
import java.util.List;
import java.util.ArrayList;
import java.sql.Statement; // truy vấn không tham số
import java.sql.PreparedStatement; // truy vấn có tham số
import java.sql.Connection; // kết nối db
import java.sql.SQLException; // lỗi kết nối sql
import java.sql.ResultSet; // lưu dữ liệu db
import java.sql.Date;
import models.Staff; // import class Book
import utils.DBConnection; // import class DBConnection

public class StaffController {
    
    public List<Staff> getAllStaffs() throws SQLException {
        String query = "SELECT * FROM Staff";

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery()) {
            List <Staff> staffs = new ArrayList<>();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phoneNumber = resultSet.getString("phone");
                String position = resultSet.getString("position");
                Date hire_date = resultSet.getDate("hireDate");
    
                Staff staff = new Staff(id, name, email, phoneNumber, position, hire_date);
                staffs.add(staff);
                
            }

            return staffs;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>(); // return an empty list
        }
    
    
    }
}
