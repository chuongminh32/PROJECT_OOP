package controllers;

import java.sql.*;
import java.util.*;
import models.Member;
import utils.DBConnection;
import java.sql.Date;

public class MemberController {

    private Connection connection;

    // ham return message 
    public static String returnMes(String id) {
        try (Connection conn = DBConnection.getConnection()) {
            String s = "SELECT message FROM Members WHERE id = ?";
            PreparedStatement p = conn.prepareStatement(s);
            p.setString(1, id);

            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                return rs.getString("message");
            } else {
                return null; // or some appropriate message if no match is found
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Handle exceptions and return null or some indication of failure
            e.printStackTrace();
            return null;
        }
    }

    // update message 
    public static Boolean updateMes(String mes, String id) {
        try (Connection conn = DBConnection.getConnection()) {
            String s = "UPDATE Members SET message = ? WHERE id = ?";
            PreparedStatement p = conn.prepareStatement(s);
            p.setString(1, mes);
            p.setString(2, id);

            // Dùng executeUpdate() cho lệnh UPDATE
            int rowsUpdated = p.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);

            // Trả về true nếu có ít nhất một dòng được cập nhật
            return rowsUpdated > 0;
        } catch (SQLException | ClassNotFoundException e) {
            // Xử lý ngoại lệ và trả về false
            e.printStackTrace();
            return false;
        }
    }

    // Constructor - kết nối cơ sở dữ liệu
    public MemberController() {
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
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
    public static List<Member> PrintListMember(Connection conn) throws SQLException {
        List<Member> list = new ArrayList<>();
        String sql = "SELECT * FROM Members";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Member mh = new Member();
                mh.setId(rs.getString("id"));
                mh.setName(rs.getString("name"));
                mh.setEmail(rs.getString("email"));
                mh.setPhone(rs.getString("phone"));
                mh.setMembershipDate(rs.getDate("membershipDate"));
                mh.setPassWord(rs.getString("password"));
                list.add(mh);
            }
        }
        return list;
    }

    // Thêm mới một thành viên
    public boolean addMember(String id, String name, String email, String phone, Date membershipDate, String password) {
        String sql = "INSERT INTO Members (id, name, email, phone, membershipDate, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, email);
            statement.setString(4, phone);
            statement.setDate(5, membershipDate);
            statement.setString(6, password);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding member: " + e.getMessage());
            return false;
        }
    }

    public Member findMember(String email) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.getConnection();
        String query = "SELECT * FROM Members WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Member member = new Member();
                    member.setId(rs.getString("id"));
                    member.setName(rs.getString("name"));
                    member.setEmail(rs.getString("email"));
                    member.setPhone(rs.getString("phone"));
                    member.setMembershipDate(rs.getDate("membershipDate"));
                    member.setPassWord(rs.getString("password"));
                    return member;
                }
            }
        }
        return null;
    }

    // Cập nhật thông tin thành viên
    public boolean updateMember(String id, String name, String email, String phone, Date membershipDate,
            String password) throws SQLException, ClassNotFoundException {
        Member fmem = findMember(email);
        if (fmem != null) {
            String sql = "UPDATE Members SET name = ?, email = ?, phone = ?, membershipDate = ?, password=? WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, phone);
                statement.setDate(4, membershipDate);
                statement.setString(5, password);
                statement.setString(6, id);

                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0; // Trả về true nếu đã cập nhật thành công
            } catch (SQLException e) {
                System.out.println("Error updating member: " + e.getMessage());
                return false;
            }
        } else {
            return addMember(id, name, email, phone, membershipDate, password);
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

        try (Statement statement = connection.createStatement(); ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                Date membershipDate = rs.getDate("membershipDate");
                String password = rs.getString("password");

                Member member = new Member(id, name, email, phone, membershipDate, password);
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

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String phone = rs.getString("phone");
                    Date membershipDate = rs.getDate("membershipDate");
                    String password = rs.getString("password");

                    return new Member(id, name, email, phone, membershipDate, password);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving member by id: " + e.getMessage());
        }

        return null;
    }
}
