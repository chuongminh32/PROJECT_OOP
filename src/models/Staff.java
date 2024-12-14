package models;

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

public class Staff {
    private String id;            // Mã nhân viên (duy nhất)
    private String name;          // Tên nhân viên
    private String email;         // Email của nhân viên
    private String phoneNumber;   // Số điện thoại
    private String position;          // Vai trò (ví dụ: Admin, Librarian)
    private Date hire_date;     //Ngày bắt đầu làm viêc

    // Constructor
    public Staff() {
    }
    public Staff(String id, String name, String email, String phoneNumber, String position, Date hire_date) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.hire_date = hire_date;
    }

    public String toString() {
        return "ID: " + this.getId() +
               ", Name: " + this.getName() +
               ", Email: " + this.getEmail() +
               ", PhoneNumber: " + this.getPhoneNumber() +
               ", position: " + this.getPosition() +
               ", hire_date: " + this.getHire_date();
    }
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    /**
     * @return the hire_date
     */
    public Date getHire_date() {
        return hire_date;
    }

    /**
     * @param hire_date the hire_date to set
     */
    public void setHire_date(Date hire_date) {
        this.hire_date = hire_date;
    }
}
