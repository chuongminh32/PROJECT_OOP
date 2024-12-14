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
                String password = resultSet.getString("password");
    
                Staff staff = new Staff(id, name, email, phoneNumber, position, hire_date, password);
                staffs.add(staff);
                
            }

            return staffs;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>(); // return an empty list
        }
    } // CLOSE getAllStaffs
    
    public void deleteStaff(String id) throws SQLException {
        String query = "DELETE FROM Staff WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
                
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Staff deleted successfully!");
            }
            else System.out.println("Can't find record in table!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void insertStaff(Staff staff) throws SQLException {
        String query = "Insert Staff(id,name,email,phone,position,hireDate,password)"
                + "values (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
                
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, staff.getId());
            statement.setString(2, staff.getName());
            statement.setString(3, staff.getEmail());
            statement.setString(4, staff.getPhoneNumber());
            statement.setString(5, staff.getPosition());
            statement.setDate(6, staff.getHire_date());
            statement.setString(7, staff.getPassword());
            
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Staff insert successfully!");
            }
            else System.out.println("Can't insert record in table!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    }
    
    
}
