package gui;

import utils.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegisterPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton registerButton;

    public RegisterPage() {
        setTitle("Library Management - Register");
        setLayout(new GridLayout(5, 2, 10, 10));
        
        // Label and TextField for Username
        add(new JLabel("Username:"));
        usernameField = new JTextField(20);
        add(usernameField);

        // Label and PasswordField for Password
        add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        add(passwordField);

        // Label and PasswordField for Confirm Password
        add(new JLabel("Confirm Password:"));
        confirmPasswordField = new JPasswordField(20);
        add(confirmPasswordField);

        // Register Button
        registerButton = new JButton("Register");
        add(registerButton);

        // Button Action Listener
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            registerUser();
            }
        });

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validation: check if fields are empty and if passwords match
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        // Insert user into database
        boolean result = registerToDatabase(username, password);
        
        if (result) {
            JOptionPane.showMessageDialog(this, "User registered successfully!");
            // Navigate back to login page or home
            new LoginPage().setVisible(true);
            dispose();  // Close RegisterPage
        } else {
            JOptionPane.showMessageDialog(this, "Error registering user. Try again.");
        }
    }

    private boolean registerToDatabase(String username, String password) {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            // Establish connection to the database
            try {
                conn = DBConnection.getConnection();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;  // Return false if an error occurs
            }
            
            // SQL query to insert user into the database
            String query = "INSERT INTO Users (username, password) VALUES (?, ?)";
            pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            
            int rowsAffected = pst.executeUpdate();
            
            return rowsAffected > 0; // Return true if insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if an error occurs
        } finally {
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new RegisterPage().setVisible(true);
    }
}
