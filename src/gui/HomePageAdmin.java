package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controllers.MemberController;
import gui.MemberManage;


public class HomePageAdmin extends JFrame {
    private JButton manageBooksButton, manageUsersButton;
    private MemberController memberController;

    public HomePageAdmin(String username) {
        setTitle("Library Management - Home");
        setLayout(new GridLayout(4, 1, 10, 10));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel);

        // Show appropriate menu based on user role
        if (username.equals("admin")) {
            manageUsersButton = new JButton("Manage Users");
            manageUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {             
                MemberManage manageUsersPage = new MemberManage(memberController);
                manageUsersPage.run();
            }
            });
            add(manageUsersButton);
        }
   
        manageBooksButton = new JButton("Manage Books");
        manageBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageBooksPage().setVisible(true);
                dispose();
            }
        });
        add(manageBooksButton);


        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new HomePageAdmin("admin").setVisible(true);
    }
}
