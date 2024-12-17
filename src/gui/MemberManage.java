
package gui;

import java.sql.*;
import java.sql.Date;
import javax.swing.*;
import java.util.List;
import utils.DBConnection;

import controllers.MemberController;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import models.Member;

/**
 *
 * @author admin
 */
public class MemberManage extends javax.swing.JFrame {
//    private JTextField idField, nameField, emailField, phoneField,membershipDateField;
 //   private JPasswordField passwordField;
    private JButton addButton, updateButton, deleteButton, viewButton;
    private JTable memberTable;
    private DefaultTableModel tableModel = new DefaultTableModel();
 //   private MemberTableModel tableModel; // Custom table model to handle member data

    private MemberController memberController;

    public MemberManage() throws SQLException, ClassNotFoundException {
        initComponents();
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        String[] cols = {"ID", "NAME", "EMAIL", "PHONE", "MEMBERSHIP DATE", "PASSWORD"};
        tableModel.setColumnIdentifiers(cols);
//        Table.setEnabled(false);
        Table.setModel(tableModel);
        
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        
//        Table.getColumnModel().getColumn(2).setPreferredWidth(400);
        showData();
        javax.swing.table.TableColumn emailColumn = Table.getColumnModel().getColumn(2);
        emailColumn.setPreferredWidth(150);
        emailColumn.setMaxWidth(150);
        emailColumn.setMinWidth(120);
    }
    public void showData() throws SQLException, ClassNotFoundException{
        String []cols = {"ID", "NAME", "EMAIL", "PHONE", "MEMBERSHIP DATE", "PASSWORD"};
        tableModel.setColumnIdentifiers(cols);
        tableModel.setRowCount(0);
        Connection conn = DBConnection.getConnection();
        List<Member> list = MemberController.PrintListMember(conn);
        for(int i=0; i< list.size(); i++){
            Member mem = list.get(i);
            String rows[] = new String[6];
            rows[0] = mem.getId();
            rows[1] = mem.getName();
            rows[2] = mem.getEmail();
            rows[3] = mem.getPhone();
            rows[4] = mem.getMembershipDate().toString();
            rows[5] = mem.getPassword();
            
            tableModel.addRow(rows);
        }
    }
    public void AddMember(String id,String name,String email,String phone,Date membershipDate,String password) throws SQLException, ClassNotFoundException{
        MemberController memberController = new MemberController();
        boolean success = memberController.addMember(id, name, email, phone, membershipDate, password);
        if (success) {
            JOptionPane.showMessageDialog(this, "Thành viên đã được thêm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            try {
                showData();
            } catch (SQLException ex) {
                Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thành viên thất bại. Vui lòng kiểm tra lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void deleteMember(String id) throws SQLException, ClassNotFoundException {
        MemberController memberController = new MemberController();
        boolean success = memberController.deleteMember(id);
        if (success) {
            JOptionPane.showMessageDialog(this, "Thành viên đã được xóa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            try {
                showData();
            } catch (SQLException ex) {
                Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thành viên thất bại. Vui lòng kiểm tra lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void updateMember(String id, String name, String email, String phone, Date membershipDate, String password) throws SQLException, ClassNotFoundException {
        MemberController memberController = new MemberController();
        boolean success = memberController.updateMember(id, name, email, phone, membershipDate, password);
        if (success) {
            JOptionPane.showMessageDialog(this, "Thành viên đã được cập nhật thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thành viên thất bại. Mail đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        showData(); // Làm mới lại bảng
    }
    
    public void run() throws SQLException, ClassNotFoundException {
        JFrame frame = new JFrame("Manage Users");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new MemberManage());
        frame.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        IdLabel = new javax.swing.JLabel();
        NameLabel = new javax.swing.JLabel();
        EmailLabel = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        PhoneLabel = new javax.swing.JLabel();
        MSLabel = new javax.swing.JLabel();
        PassLabel = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        AddButton = new javax.swing.JButton();
        UpdButton = new javax.swing.JButton();
        DelButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "THÔNG TIN QUẢN LÝ MEMBER", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 102, 255))); // NOI18N

        IdLabel.setText("ID");

        NameLabel.setText("NAME");

        EmailLabel.setText("EMAIL");

        PhoneLabel.setText("PHONE");

        MSLabel.setText("MEMBERSHIP DATE");

        PassLabel.setText("PASSWORD");

        AddButton.setText("ADD");
        AddButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddButtonMouseClicked(evt);
            }
        });

        UpdButton.setText("UPDATE");
        UpdButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UpdButtonMouseClicked(evt);
            }
        });

        DelButton.setText("DELETE");
        DelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DelButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(AddButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(UpdButton)
                        .addGap(18, 18, 18)
                        .addComponent(DelButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(MSLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(PassLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPasswordField1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(PhoneLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(EmailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(IdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(NameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(14, 14, 14))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IdLabel)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmailLabel))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PhoneLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MSLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PassLabel)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AddButton)
                    .addComponent(UpdButton)
                    .addComponent(DelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 21, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void AddButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddButtonMouseClicked
        // TODO add your handling code here:
        String id = jTextField1.getText().trim(); // idTextField là JTextField chứa ID
        String name = jTextField2.getText().trim();
        String email = jTextField3.getText().trim();
        String phone = jTextField4.getText().trim();
        String password = new String(jPasswordField1.getPassword());
        Date membershipDate;
        try {
            membershipDate = Date.valueOf(jTextField5.getText().trim());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Vui lòng nhập đúng định dạng yyyy-mm-dd.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            AddMember(id, name, email, phone, membershipDate, password);   
        } catch (SQLException ex) {
            Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_AddButtonMouseClicked

    private void DelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DelButtonMouseClicked
        // TODO add your handling code here:
    
        String id = jTextField1.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ID của thành viên cần xóa.", 
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa thành viên với ID: " + id + "?", 
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                deleteMember(id);
            } catch (SQLException ex) {
                Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_DelButtonMouseClicked

    private void UpdButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UpdButtonMouseClicked
        // TODO add your handling code here:
        String id = jTextField1.getText().trim();
        String name = jTextField2.getText().trim();
        String email = jTextField3.getText().trim();
        String phone = jTextField4.getText().trim();
        String password = new String(jPasswordField1.getPassword());
        Date membershipDate;
        try {
            membershipDate = Date.valueOf(jTextField5.getText().trim());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Vui lòng nhập đúng định dạng yyyy-mm-dd.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID trống.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            updateMember(id, name, email, phone, membershipDate, password);
        } catch (SQLException ex) {
            Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_UpdButtonMouseClicked

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        // TODO add your handling code here:
        int selectedRow = Table.getSelectedRow(); 
    
        if (selectedRow != -1) { 
            jTextField1.setText(Table.getValueAt(selectedRow, 0).toString()); // ID
            jTextField2.setText(Table.getValueAt(selectedRow, 1).toString()); // NAME
            jTextField3.setText(Table.getValueAt(selectedRow, 2).toString()); // EMAIL
            jTextField4.setText(Table.getValueAt(selectedRow, 3).toString()); // PHONE
            jTextField5.setText(Table.getValueAt(selectedRow, 4).toString()); // MEMBERSHIP DATE
            jPasswordField1.setText(Table.getValueAt(selectedRow, 5).toString()); // PASSWORD
        }
    }//GEN-LAST:event_TableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MemberManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MemberManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MemberManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MemberManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MemberManage().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButton;
    private javax.swing.JButton DelButton;
    private javax.swing.JLabel EmailLabel;
    private javax.swing.JLabel IdLabel;
    private javax.swing.JLabel MSLabel;
    private javax.swing.JLabel NameLabel;
    private javax.swing.JLabel PassLabel;
    private javax.swing.JLabel PhoneLabel;
    private javax.swing.JTable Table;
    private javax.swing.JButton UpdButton;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
