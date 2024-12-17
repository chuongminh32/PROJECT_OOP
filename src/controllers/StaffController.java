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
            return new ArrayList<>(); // return an empty list
        }
    } // CLOSE getAllStaffs
    
    public void deleteStaff(String id) throws SQLException {
        String query = "DELETE FROM Staff WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
                
            PreparedStatement statement = connection.prepareStatement(query))
        {
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
    
    public void insertStaff(Staff staff) throws SQLException, ClassNotFoundException {
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
        } catch (SQLException | ClassNotFoundException e){      
                throw e;           
        }
        
    }
    
    public List<Staff> find_staff_byname(String name) throws SQLException, ClassNotFoundException{
        String sql = "Select * from Staff WHERE name=?";
        
        try (Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);){
            pstm.setString(1,  name);
        
            ResultSet rs = pstm.executeQuery();
            List <Staff> staffs = new ArrayList<>();

            while (rs.next()) {
                String id = rs.getString("id");
                String names = rs.getString("name");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone");
                String position = rs.getString("position");
                Date hire_date = rs.getDate("hireDate");
                String password = rs.getString("password");
    
                Staff staff = new Staff(id, name, email, phoneNumber, position, hire_date, password);
                staffs.add(staff);  
            }
            return staffs;
        }   catch (SQLException | ClassNotFoundException e){      
                e.printStackTrace();
                return null;   
        }
    }
    
    public void updateStaff(Staff staff, String id) {
        String query = "UPDATE Staff SET name = ?, email = ?, phone = ?, position = ?, hireDate = ?, password = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, staff.getName());
            statement.setString(2, staff.getEmail());
            statement.setString(3, staff.getPhoneNumber());
            statement.setString(4, staff.getPosition());
            statement.setDate(5, staff.getHire_date());
            statement.setString(6, staff.getPassword());
            statement.setString(7, staff.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Staff updated successfully!");
            }
            else {
                this.insertStaff(staff);
            }
        } catch (SQLException | ClassNotFoundException e) {
        }
    }
    
    public boolean isEmailExists(String email) throws SQLException, ClassNotFoundException {
        //KIỂM TRA EMAIL CÓ PHẢI LÀ DUY NHẤT
    String query = "SELECT COUNT(*) FROM Staff WHERE email = ?";
    try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, email);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Email đã tồn tại nếu count > 0
            }
        }
    }
    return false; // Email không tồn tại
}

    
    
}
