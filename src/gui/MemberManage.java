
package gui;

import java.sql.*;
import java.sql.Date;
import javax.swing.*;
import java.util.List;
import utils.DBConnection;

import controllers.MemberController;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import models.Member;

/**
 *
 * @author admin
 */
public class MemberManage extends javax.swing.JFrame {
    // private JTextField idField, nameField, emailField,
    // phoneField,membershipDateField;
    // private JPasswordField passwordField;
    // private JButton addButton, updateButton, deleteButton, viewButton;
    private JTable memberTable;
    private DefaultTableModel tableModel = new DefaultTableModel();
    // private MemberTableModel tableModel; // Custom table model to handle member
    // data

    private MemberController memberController;

    public MemberManage() throws SQLException, ClassNotFoundException {
        initComponents();
        setLocationRelativeTo(null);
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] cols = { "ID", "NAME", "EMAIL", "PHONE", "MEMBERSHIP DATE", "PASSWORD" };
        tableModel.setColumnIdentifiers(cols);
        // Table.setEnabled(false);
        Table.setModel(tableModel);

        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });

        // Table.getColumnModel().getColumn(2).setPreferredWidth(400);
        showData();
        javax.swing.table.TableColumn emailColumn = Table.getColumnModel().getColumn(2);
        emailColumn.setPreferredWidth(150);
        emailColumn.setMaxWidth(150);
        emailColumn.setMinWidth(120);
    }

    public void showData() throws SQLException, ClassNotFoundException {
        String[] cols = { "ID", "NAME", "EMAIL", "PHONE", "MEMBERSHIP DATE", "PASSWORD" };
        tableModel.setColumnIdentifiers(cols);
        tableModel.setRowCount(0);
        Connection conn = DBConnection.getConnection();
        List<Member> list = MemberController.PrintListMember(conn);
        for (int i = 0; i < list.size(); i++) {
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

    public void AddMember(String id, String name, String email, String phone, Date membershipDate, String password)
            throws SQLException, ClassNotFoundException {
        MemberController memberController = new MemberController();
        boolean success = memberController.addMember(id, name, email, phone, membershipDate, password);
        if (success) {
            JOptionPane.showMessageDialog(this, "Thành viên đã được thêm thành công!", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            try {
                showData();
            } catch (SQLException ex) {
                Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thành viên thất bại. Vui lòng kiểm tra lại!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteMember(String id) throws SQLException, ClassNotFoundException {
        MemberController memberController = new MemberController();
        boolean success = memberController.deleteMember(id);
        if (success) {
            JOptionPane.showMessageDialog(this, "Thành viên đã được xóa thành công!", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            try {
                showData();
            } catch (SQLException ex) {
                Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thành viên thất bại. Vui lòng kiểm tra lại!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateMember(String id, String name, String email, String phone, Date membershipDate, String password)
            throws SQLException, ClassNotFoundException {
        MemberController memberController = new MemberController();
        boolean success = memberController.updateMember(id, name, email, phone, membershipDate, password);
        if (success) {
            JOptionPane.showMessageDialog(this, "Thành viên đã được cập nhật thành công!", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thành viên thất bại. Mail đã tồn tại!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
        showData(); // Làm mới lại bảng
    }

    public void SearchMem() throws SQLException, ClassNotFoundException {
        String searchText = SearchField.getText().trim().toLowerCase();
        String selectedCriteria = (String) SearchOpt.getSelectedItem();

        showData();

        int columnIndex = -1;
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            if (tableModel.getColumnName(i).equalsIgnoreCase(selectedCriteria)) {
                columnIndex = i;
                break;
            }
        }
        if (columnIndex == -1) {
            JOptionPane.showMessageDialog(this, "Tên trường không được chọn.", "Có lỗi xảy ra!!!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Object[]> searchResults = new ArrayList<>();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String cellValue = tableModel.getValueAt(i, columnIndex).toString().toLowerCase();
            boolean match = false;
            if (selectedCriteria.equalsIgnoreCase("MEMBERSHIP DATE")) {
                try {
                    Date searchDate = Date.valueOf(searchText);
                    Date cellDate = Date.valueOf(cellValue);
                    match = cellDate.equals(searchDate);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(this, "Sai định dạng! Hãy nhập định dạng: YYYY-MM-DD.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                match = cellValue.contains(searchText);
            }
            if (match) {
                Object[] rowData = new Object[tableModel.getColumnCount()];
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    rowData[j] = tableModel.getValueAt(i, j);
                }
                searchResults.add(rowData);
            }
        }

        /*
         * for (int i = 0; i < tableModel.getRowCount(); i++) {
         * String cellValue = tableModel.getValueAt(i,
         * columnIndex).toString().toLowerCase();
         * if (cellValue.contains(searchText)) {
         * Object[] rowData = new Object[tableModel.getColumnCount()];
         * for (int j = 0; j < tableModel.getColumnCount(); j++) {
         * rowData[j] = tableModel.getValueAt(i, j);
         * }
         * searchResults.add(rowData);
         * }
         * }
         */
        tableModel.setRowCount(0);

        for (Object[] row : searchResults) {
            tableModel.addRow(row);
        }

        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả nào!", "Not Found",
                    JOptionPane.INFORMATION_MESSAGE);
        }
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
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jTextField5 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        EmailLabel = new javax.swing.JLabel();
        PhoneLabel = new javax.swing.JLabel();
        NameLabel = new javax.swing.JLabel();
        IdLabel = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        MSLabel = new javax.swing.JLabel();
        PassLabel = new javax.swing.JLabel();
        AddButton = new javax.swing.JButton();
        UpdButton = new javax.swing.JButton();
        DelButton = new javax.swing.JButton();
        ReturnButton = new javax.swing.JButton();
        SearchOpt = new javax.swing.JComboBox<>();
        SearchField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        reload = new javax.swing.JButton();

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(153, 204, 255));

        jPanel1.setBackground(new java.awt.Color(58, 93, 156));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE));
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 15, Short.MAX_VALUE));

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));

        jLabel1.setBackground(new java.awt.Color(102, 153, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUẢN LÍ THÀNH VIÊN");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 206,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(7, Short.MAX_VALUE)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1000, 597));

        Table.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null },
                        { null, null, null, null }
                },
                new String[] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }));
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table);

        EmailLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        EmailLabel.setText("EMAIL");

        PhoneLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PhoneLabel.setText("PHONE");

        NameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        NameLabel.setText("NAME");

        IdLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        IdLabel.setText("ID");

        MSLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        MSLabel.setText("MEMBERSHIP DATE");

        PassLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PassLabel.setText("PASSWORD");

        AddButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AddButton.setText("ADD");
        AddButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddButtonMouseClicked(evt);
            }
        });

        UpdButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        UpdButton.setText("UPDATE");
        UpdButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UpdButtonMouseClicked(evt);
            }
        });

        DelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DelButton.setText("DELETE");
        DelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DelButtonMouseClicked(evt);
            }
        });

        ReturnButton.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        ReturnButton.setText("RETURN");
        ReturnButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReturnButtonMouseClicked(evt);
            }
        });

        SearchOpt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "NAME", "MEMBERSHIP DATE" }));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 102, 102));
        jButton1.setText("SEARCH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        reload.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        reload.setText("RELOAD");
        reload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reloadMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(NameLabel, javax.swing.GroupLayout.Alignment.LEADING,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(EmailLabel, javax.swing.GroupLayout.Alignment.LEADING,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
                                        .addComponent(IdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(PhoneLabel))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(257, 257, 257)
                                                .addComponent(PassLabel))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jTextField3,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 170,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(70, 70, 70)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jButton1,
                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(reload,
                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                86,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(SearchField,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 178,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(SearchOpt,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 84,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(jTextField1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                170,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                false)
                                                                                .addComponent(jTextField4,
                                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        170, Short.MAX_VALUE)
                                                                                .addComponent(jTextField2,
                                                                                        javax.swing.GroupLayout.Alignment.LEADING)))
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        Short.MAX_VALUE)
                                                                                .addComponent(jPasswordField1,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        170,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                                .addGap(54, 54, 54)
                                                                                .addComponent(MSLabel)
                                                                                .addPreferredGap(
                                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        Short.MAX_VALUE)
                                                                                .addComponent(jTextField5,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        170,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(31, 31, 31)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                jPanel2Layout.createSequentialGroup()
                                                        .addComponent(AddButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(UpdButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(DelButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(ReturnButton))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 909,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 341,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jButton1)
                                                        .addComponent(SearchOpt, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(SearchField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jTextField4,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(PhoneLabel)
                                                        .addComponent(reload)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(IdLabel)
                                                        .addComponent(jTextField1,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(MSLabel)
                                                        .addComponent(jTextField5,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(AddButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(UpdButton,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 39,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGap(24, 24, 24)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jTextField2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(NameLabel)
                                                                        .addComponent(PassLabel)
                                                                        .addComponent(jPasswordField1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addGroup(jPanel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGap(25, 25, 25)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jTextField3,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(EmailLabel)))
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(DelButton,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 41,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(ReturnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(31, 31, 31)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 909,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 588, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_TableMouseClicked
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
    }// GEN-LAST:event_TableMouseClicked

    private void AddButtonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_AddButtonMouseClicked
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
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Vui lòng nhập đúng định dạng yyyy-mm-dd.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            AddMember(id, name, email, phone, membershipDate, password);
        } catch (SQLException ex) {
            Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_AddButtonMouseClicked

    private void UpdButtonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_UpdButtonMouseClicked
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
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Vui lòng nhập đúng định dạng yyyy-mm-dd.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
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
    }// GEN-LAST:event_UpdButtonMouseClicked

    private void DelButtonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_DelButtonMouseClicked
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
    }// GEN-LAST:event_DelButtonMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            SearchMem();
        } catch (SQLException ex) {
            Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_jButton1ActionPerformed

    private void ReturnButtonMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_ReturnButtonMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }// GEN-LAST:event_ReturnButtonMouseClicked

    private void reloadMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_reloadMouseClicked
        try {
            // TODO add your handling code here:
            showData();
        } catch (SQLException ex) {
            Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MemberManage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_reloadMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MemberManage.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MemberManage.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MemberManage.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MemberManage.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>

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
    private javax.swing.JButton ReturnButton;
    private javax.swing.JTextField SearchField;
    private javax.swing.JComboBox<String> SearchOpt;
    private javax.swing.JTable Table;
    private javax.swing.JButton UpdButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JButton reload;
    // End of variables declaration//GEN-END:variables
}
