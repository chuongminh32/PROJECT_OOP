package controllers;
import java.util.List;
import java.util.ArrayList;
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
    
    
    public void updateStaff(Staff staff, String id) throws SQLException, ClassNotFoundException {
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
    
    //CÁC TRUY VẤN LỌC
    public List<Staff> findStaffByPartialFields(String id, String name, String email, String phoneNumber, String position, String hireDate, String password) throws SQLException, ClassNotFoundException {
        StringBuilder sql = new StringBuilder("SELECT * FROM Staff WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        if (id != null && !id.trim().isEmpty()) {
            sql.append(" AND id LIKE ?");
            parameters.add("%" + id.trim() + "%");
        }
        if (name != null && !name.trim().isEmpty()) {
            sql.append(" AND name LIKE ?");
            parameters.add("%" + name.trim() + "%");
        }
        if (email != null && !email.trim().isEmpty()) {
            sql.append(" AND email LIKE ?");
            parameters.add("%" + email.trim() + "%");
        }
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            sql.append(" AND phone LIKE ?");
            parameters.add("%" + phoneNumber.trim() + "%");
        }
        if (position != null && !position.trim().isEmpty()) {
            sql.append(" AND position LIKE ?");
            parameters.add("%" + position.trim() + "%");
        }
        if (hireDate != null && !hireDate.trim().isEmpty()) {
            sql.append(" AND hireDate LIKE ?");
            parameters.add("%" + hireDate + "%");
        }
        if (password != null && !password.trim().isEmpty()) {
            sql.append(" AND password LIKE ?");
            parameters.add("%" + password.trim() + "%");
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                pstm.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = pstm.executeQuery()) {
                List<Staff> staffs = new ArrayList<>();
                while (rs.next()) {
                    String staffId = rs.getString("id");
                    String staffName = rs.getString("name");
                    String staffEmail = rs.getString("email");
                    String staffPhoneNumber = rs.getString("phone");
                    String staffPosition = rs.getString("position");
                    Date staffHireDate = rs.getDate("hireDate");
                    String staffPassword = rs.getString("password");

                    Staff staff = new Staff(staffId, staffName, staffEmail, staffPhoneNumber, staffPosition, staffHireDate, staffPassword);
                    staffs.add(staff);
                }
                return staffs;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}


    
    

