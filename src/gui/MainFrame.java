// package views;

// import javax.swing.*;

// import controllers.MemberController;

// import java.awt.*;

// public class MainFrame extends JFrame {

//     public MainFrame() {
//         setTitle("Library Management System");
//         setSize(800, 600);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null); // Center the window

//         // Create a JTabbedPane for multiple tabs
//         JTabbedPane tabbedPane = new JTabbedPane();
//         tabbedPane.addTab("Books", new BookPanel());
//         add(tabbedPane, BorderLayout.CENTER); // Add JTabbedPane to the center

//         // Create MemberPanel
//         MemberController memberController = new MemberController();
//         MemberPanel memberPanel = new MemberPanel(memberController);
//         add(memberPanel, BorderLayout.CENTER); // Add MemberPanel to the center

//         setVisible(true); // Show the window
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(MainFrame::new);
//     }
// }

package gui;

import javax.swing.*;
import java.awt.*;
import controllers.MemberController;
import java.sql.SQLException;

public class MainFrame extends JFrame {

    public MainFrame() throws SQLException, ClassNotFoundException {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Tạo đối tượng MemberController (giả sử bạn đã định nghĩa trong phần controllers)
        MemberController memberController = new MemberController();

        // Tạo và thêm MemberPanel vào cửa sổ chính
        MemberManage memberPanel = new MemberManage();
        add(memberPanel, BorderLayout.CENTER);

        // setVisible(true); // Hiển thị cửa sổ
    }

    // public static void main(String[] args) {
    //     // Khởi chạy ứng dụng trong một thread riêng biệt để đảm bảo giao diện người dùng không bị treo
    //     SwingUtilities.invokeLater(() -> new MainFrame());
    // }
}
