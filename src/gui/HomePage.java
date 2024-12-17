//package gui;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import controllers.MemberController;
//import gui.MemberManage;
//import java.sql.SQLException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//
//public class HomePage extends JFrame {
//    private JButton manageBooksButton, manageUsersButton;
//    private MemberController memberController;
//
//    public HomePage(String username) {
//        setTitle("Library Management - Home");
//        setLayout(new GridLayout(4, 1, 10, 10));
//        
//        JLabel welcomeLabel = new JLabel("Welcome, " + username);
//        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        add(welcomeLabel);
//
//        // Show appropriate menu based on user role
//        if (username.equals("admin")) {
//            manageUsersButton = new JButton("Manage Users");
//            manageUsersButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {             
//                try {
//                    MemberManage manageUsersPage = new MemberManage();
//                    manageUsersPage.run();
//                } catch (SQLException ex) {
//                    Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (ClassNotFoundException ex) {
//                    Logger.getLogger(HomePage.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            });
//            add(manageUsersButton);
//        }
//   
//        manageBooksButton = new JButton("Manage Books");
//        manageBooksButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new ManageBooksPage().setVisible(true);
//                dispose();
//            }
//        });
//        add(manageBooksButton);
//
//
//        setSize(300, 200);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//    }
//
//    public static void main(String[] args) {
//        new HomePage("admin").setVisible(true);
//    }
//}
