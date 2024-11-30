package gui;

import javax.swing.*;

import gui.RegisterPage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import utils.DBConnection;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    
    public LoginPage() {
        setTitle("Library Management - Login");
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login");
        add(loginButton);
        
        registerButton = new JButton("Register");
        add(registerButton);
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Handle login validation and user authorization here
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (validateLogin(username, password)) {
                // After successful login, open the home screen
                new HomePage(username).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid login credentials!");
            }
            }
        });
        
        // Register button handler
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to the registration page
                new RegisterPage().setVisible(true);
                dispose();
            }
        });
        
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
    }

    private boolean validateLogin(String username, String password) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Establish a connection to the database
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            String query = "SELECT * FROM Users WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return true; // User found with matching username and password
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return false; // User not found or error occurred
    }

    public static void main(String[] args) {
        new LoginPage().setVisible(true);
    }
}
