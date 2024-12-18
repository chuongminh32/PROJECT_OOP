package models;

import java.sql.Date;

public class Staff {
    private String id;            // Mã nhân viên (duy nhất)
    private String name;          // Tên nhân viên
    private String email;         // Email của nhân viên
    private String phoneNumber;   // Số điện thoại
    private String position;          // Vai trò (ví dụ: Admin, Librarian)
    private Date hire_date;     //Ngày bắt đầu làm viêc
    private String password;
    
    // Constructor
    public Staff() {
    }
    public Staff(String id, String name, String email, String phoneNumber, String position, Date hire_date, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.hire_date = hire_date;
        this.password = password;
    }

    public String toString() {
        return "ID: " + this.getId() +
               ", Name: " + this.getName() +
               ", Email: " + this.getEmail() +
               ", PhoneNumber: " + this.getPhoneNumber() +
               ", position: " + this.getPosition() +
               ", hire_date: " + this.getHire_date() +
                ", password: " + this.getPassword();
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

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
}
