package main;

import gui.LoginPage;

public class Main {
    public static void main(String[] args) {
        LoginPage login = new LoginPage();
        login.setVisible(true);
        login.pack();
        login.setLocationRelativeTo(null);
    }
}
