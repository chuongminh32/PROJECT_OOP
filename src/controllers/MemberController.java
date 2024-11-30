package controllers;

import java.sql.*;
import java.util.*;
import models.Member;
import utils.DBConnection;
import java.sql.Date;

public class MemberController {

    private Connection connection;

    // Constructor - kết nối cơ sở dữ liệu
    public MemberController() {
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // test connection
    public void testConnection() {
        if (this.connection != null) {
            System.out.println("Connected to database.");
        } else {
            System.out.println("Connection failed.");
        }
    }

    // in danh sach member
    public static List<Member> PrintListThanhVien(Connection conn) throws SQLException {
        List<Member> list = new ArrayList<>();
        String sql = "SELECT * FROM Members";
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Member mh = new Member();
                mh.setId(rs.getString("id"));
                mh.setName(rs.getString("name"));
                mh.setEmail(rs.getString("email"));
                mh.setPhone(rs.getString("phone"));
                mh.setMembershipDate(rs.getDate("membershipDate"));
                list.add(mh);
            }
        }
        return list;
    }

    // Thêm mới một thành viên
    public boolean addMember(String id, String name, String email, String phone, Date membershipDate) {
        String sql = "INSERT INTO Members (id, name, email, phone, membershipDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, email);
            statement.setString(4, phone);
            statement.setDate(5, membershipDate);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu đã thêm thành công
        } catch (SQLException e) {
            System.out.println("Error adding member: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật thông tin thành viên
    public boolean updateMember(String id, String name, String email, String phone, Date membershipDate) {
        String sql = "UPDATE Members SET name = ?, email = ?, phone = ?, membershipDate = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setDate(4, membershipDate);
            statement.setString(5, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu đã cập nhật thành công
        } catch (SQLException e) {
            System.out.println("Error updating member: " + e.getMessage());
            return false;
        }
    }

    // Xóa một thành viên
    public boolean deleteMember(String id) {
        String sql = "DELETE FROM Members WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu đã xóa thành công
        } catch (SQLException e) {
            System.out.println("Error deleting member: " + e.getMessage());
            return false;
        }
    }

    // Lấy danh sách tất cả thành viên
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM Members";

        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                Date membershipDate = resultSet.getDate("membershipDate");

                Member member = new Member(id, name, email, phone, membershipDate);
                members.add(member);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving members: " + e.getMessage());
        }

        return members;
    }

    // Lấy thông tin thành viên theo ID
    public Member getMemberById(String id) {
        String sql = "SELECT * FROM Members WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String phone = resultSet.getString("phone");
                    Date membershipDate = resultSet.getDate("membershipDate");

                    return new Member(id, name, email, phone, membershipDate);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving member by id: " + e.getMessage());
        }

        return null; // Trả về null nếu không tìm thấy thành viên
    }
}
