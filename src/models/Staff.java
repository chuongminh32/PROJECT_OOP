package models;
public class Staff {
    private String id;            // Mã nhân viên (duy nhất)
    private String name;          // Tên nhân viên
    private String email;         // Email của nhân viên
    private String phoneNumber;   // Số điện thoại
    private String role;          // Vai trò (ví dụ: Admin, Librarian)
    private String username;      // Tên đăng nhập
    private String password;      // Mật khẩu

    // Constructor
    public Staff() {
    }
    public Staff(String id, String name, String email, String phoneNumber, String role, 
                 String username, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.username = username;
        this.password = password;
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

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
