// package views;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;
// import java.util.List;
// import models.MemberTableModel; // Custom table model to handle member data
// import controllers.MemberController;
// import models.Member;

// // Giao diện người dùng:

// // Lớp MemberPanel kế thừa từ JPanel và sử dụng BorderLayout để sắp xếp các thành phần.
// // Một bảng JTable để hiển thị danh sách thành viên.
// // Các trường nhập liệu (JTextField) để nhập thông tin thành viên (ID, tên, email, phone).
// // Các nút (JButton) để thêm, cập nhật, xóa và xem thành viên.
// // Tạo và cập nhật bảng:

// // MemberTableModel là một lớp tùy chỉnh (custom table model) để quản lý dữ liệu bảng (sẽ được tạo trong bước tiếp theo).
// // Bảng hiển thị thông tin thành viên được làm mới mỗi khi người dùng thực hiện một thao tác (thêm, cập nhật, xóa).
// // Sự kiện Button:

// // Thêm thành viên: Kiểm tra xem các trường nhập liệu có trống hay không. Sau đó gọi addMember() từ MemberController để thêm thành viên vào cơ sở dữ liệu.
// // Cập nhật thành viên: Kiểm tra xem người dùng đã chọn thành viên nào chưa. Sau đó gọi updateMember() từ MemberController để cập nhật thông tin thành viên.
// // Xóa thành viên: Kiểm tra xem người dùng đã chọn thành viên nào chưa. Sau đó gọi deleteMember() từ MemberController để xóa thành viên.
// // Xem danh sách thành viên: Gọi viewMembers() để lấy danh sách tất cả thành viên và cập nhật bảng.

// public class MemberPanel extends JPanel {

//     private JTextField idField, nameField, emailField, phoneField;
//     private JButton addButton, updateButton, deleteButton, viewButton;
//     private JTable memberTable;
//     private MemberTableModel tableModel;  // Custom table model to handle member data

//     private MemberController memberController;

//     // Constructor
//     public MemberPanel(MemberController memberController) {
//         this.memberController = memberController;

//         // Set the layout
//         setLayout(new BorderLayout());

//         // Create the form panel (for adding or updating members)
//         JPanel formPanel = new JPanel(new GridLayout(5, 2));
//         formPanel.setBorder(BorderFactory.createTitledBorder("Member Information"));

//         // Create form fields and labels
//         formPanel.add(new JLabel("ID:"));
//         idField = new JTextField();
//         formPanel.add(idField);

//         formPanel.add(new JLabel("Name:"));
//         nameField = new JTextField();
//         formPanel.add(nameField);

//         formPanel.add(new JLabel("Email:"));
//         emailField = new JTextField();
//         formPanel.add(emailField);

//         formPanel.add(new JLabel("Phone:"));
//         phoneField = new JTextField();
//         formPanel.add(phoneField);

//         // Add buttons for actions
//         addButton = new JButton("Add Member");
//         updateButton = new JButton("Update Member");
//         deleteButton = new JButton("Delete Member");
//         viewButton = new JButton("View Members");

//         // Add buttons to form panel
//         formPanel.add(addButton);
//         formPanel.add(updateButton);
//         formPanel.add(deleteButton);
//         formPanel.add(viewButton);

//         // Add form panel to the main panel
//         add(formPanel, BorderLayout.NORTH);

//         // Create and add the member table
//         tableModel = new MemberTableModel();
//         memberTable = new JTable(tableModel);
//         JScrollPane scrollPane = new JScrollPane(memberTable);
//         add(scrollPane, BorderLayout.CENTER);

//         // Button actions
//         addButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 addMember();
//             }
//         });

//         updateButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 updateMember();
//             }
//         });

//         deleteButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 deleteMember();
//             }
//         });

//         viewButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 viewMembers();
//             }
//         });
//     }

//     // Add a new member
//     private void addMember() {
//         String id = idField.getText();
//         String name = nameField.getText();
//         String email = emailField.getText();
//         String phone = phoneField.getText();

//         // Check if fields are empty
//         if (id.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
//             JOptionPane.showMessageDialog(this, "Please fill in all fields.");
//             return;
//         }

//         // Convert date to appropriate format
//         java.sql.Date membershipDate = new java.sql.Date(System.currentTimeMillis());

//         boolean result = memberController.addMember(id, name, email, phone, membershipDate);
//         if (result) {
//             JOptionPane.showMessageDialog(this, "Member added successfully.");
//             viewMembers();  // Refresh the table view
//         } else {
//             JOptionPane.showMessageDialog(this, "Error adding member.");
//         }
//     }

//     // Update an existing member
//     private void updateMember() {
//         int selectedRow = memberTable.getSelectedRow(); // Get the selected row
//         if (selectedRow != -1) {
//             String id = (String) memberTable.getValueAt(selectedRow, 0);
//             String name = nameField.getText();
//             String email = emailField.getText();
//             String phone = phoneField.getText();

//             if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
//                 JOptionPane.showMessageDialog(this, "Please fill in all fields.");
//                 return;
//             }

//             java.sql.Date membershipDate = new java.sql.Date(System.currentTimeMillis());

//             boolean result = memberController.updateMember(id, name, email, phone, membershipDate);
//             if (result) {
//                 JOptionPane.showMessageDialog(this, "Member updated successfully.");
//                 viewMembers();  // Refresh the table view
//             } else {
//                 JOptionPane.showMessageDialog(this, "Error updating member.");
//             }
//         } else {
//             JOptionPane.showMessageDialog(this, "Please select a member to update.");
//         }
//     }

//     // Delete a selected member
//     private void deleteMember() {
//         int selectedRow = memberTable.getSelectedRow();
//         if (selectedRow != -1) {
//             String id = (String) memberTable.getValueAt(selectedRow, 0);
//             int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this member?");
//             if (confirm == JOptionPane.YES_OPTION) {
//                 boolean result = memberController.deleteMember(id);
//                 if (result) {
//                     JOptionPane.showMessageDialog(this, "Member deleted successfully.");
//                     viewMembers();  // Refresh the table view
//                 } else {
//                     JOptionPane.showMessageDialog(this, "Error deleting member.");
//                 }
//             }
//         } else {
//             JOptionPane.showMessageDialog(this, "Please select a member to delete.");
//         }
//     }

//     // View all members
//     private void viewMembers() {
//         List<Member> members = memberController.getAllMembers();
//         tableModel.setMembers(members);  // Update the table with new member data
//     }
// }

package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import controllers.MemberController;
import models.Member;

public class MemberManage extends JPanel {

    private JTextField idField, nameField, emailField, phoneField, passwordField;
    private JButton addButton, updateButton, deleteButton, viewButton;
    private JTable memberTable;
    private MemberTableModel tableModel; // Custom table model to handle member data

    private MemberController memberController;

    // Constructor
    public MemberManage(MemberController memberController) {
        this.memberController = memberController;

        // Set the layout
        setLayout(new BorderLayout());

        // Create the form panel (for adding or updating members)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Member Information"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components
        gbc.anchor = GridBagConstraints.WEST;

        // Create form fields and labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("ID:"), gbc);
        idField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Name:"), gbc);
        nameField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Phone:"), gbc);
        phoneField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Password:"), gbc);
        phoneField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // Add buttons for actions
        addButton = new JButton("Add Member");
        updateButton = new JButton("Update Member");
        deleteButton = new JButton("Delete Member");
        viewButton = new JButton("View Members");

        // Add buttons to form panel
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(addButton, gbc);

        gbc.gridx = 1;
        formPanel.add(updateButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(deleteButton, gbc);

        gbc.gridx = 1;
        formPanel.add(viewButton, gbc);

        // Add form panel to the main panel
        add(formPanel, BorderLayout.NORTH);

        // Create and add the member table
        tableModel = new MemberTableModel();
        memberTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(memberTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMember();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMember();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteMember();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewMembers();
            }
        });
    }

    // Add a new member
    private void addMember() {
        String id = idField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();

        // Check if fields are empty
        if (id.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        // Convert date to appropriate format
        java.sql.Date membershipDate = new java.sql.Date(System.currentTimeMillis());

        boolean result = memberController.addMember(id, name, email, phone, membershipDate, password);
        if (result) {
            JOptionPane.showMessageDialog(this, "Member added successfully.");
            viewMembers(); // Refresh the table view
        } else {
            JOptionPane.showMessageDialog(this, "Error adding member.");
        }
    }

    // Update an existing member
    private void updateMember() {
        int selectedRow = memberTable.getSelectedRow(); // Get the selected row
        if (selectedRow != -1) {
            String id = (String) memberTable.getValueAt(selectedRow, 0);
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String password = passwordField.getText();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            java.sql.Date membershipDate = new java.sql.Date(System.currentTimeMillis());

            boolean result = memberController.updateMember(id, name, email, phone, membershipDate, password);
            if (result) {
                JOptionPane.showMessageDialog(this, "Member updated successfully.");
                viewMembers(); // Refresh the table view
            } else {
                JOptionPane.showMessageDialog(this, "Error updating member.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a member to update.");
        }
    }

    // Delete a selected member
    private void deleteMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow != -1) {
            String id = (String) memberTable.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this member?");
            if (confirm == JOptionPane.YES_OPTION) {
                boolean result = memberController.deleteMember(id);
                if (result) {
                    JOptionPane.showMessageDialog(this, "Member deleted successfully.");
                    viewMembers(); // Refresh the table view
                } else {
                    JOptionPane.showMessageDialog(this, "Error deleting member.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a member to delete.");
        }
    }

    // View all members
    private void viewMembers() {
        List<Member> members = memberController.getAllMembers();
        tableModel.setMembers(members); // Update the table with new member data
    }

    public void run() {
        JFrame frame = new JFrame("Manage Users");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new MemberManage(new MemberController()));
        frame.setVisible(true);
    }

}
