/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

//import tests.HomePageAdmin;
import controllers.BorrowController;
import java.util.List;
import controllers.HomePageLogic;
import controllers.MemberController;
import java.awt.Color;

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import models.Borrow;
import models.Member;
import utils.DBConnection;

/**
 *
 * @author chuon
 */
public class MemberManager extends javax.swing.JFrame {

    private DefaultTableModel tableModel = new DefaultTableModel();
    private DefaultTableModel tableModel2 = new DefaultTableModel();

    private String userName;

    /**
     * Creates new form home_page_user
     *
     * @param <error>
     */
    public MemberManager(String userName) {
        this.userName = userName;
        initComponents();
        setUsername();
        displayCount();
        displayTableMembers();
        disPlayTableBorrow();
//        displayTableMembers2();

    }

    private void displayCount() {
        int cnt = HomePageLogic.getCount("Staff");
        countStaff.setText(String.valueOf(cnt));
        int cnt2 = HomePageLogic.getCount("Books");
        countBooks.setText(String.valueOf(cnt2));
        int cnt3 = HomePageLogic.getCount("Members");
        countMember.setText(String.valueOf(cnt3));
        int cnt4 = HomePageLogic.getCount("Borrow");
        countBorrow.setText(String.valueOf(cnt4));
    }

    public void displayTableMembers() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] cols = {"ID", "NAME", "EMAIL", "PHONE", "MEMBERSHIP DATE", "PASSWORD"};
        tableModel.setColumnIdentifiers(cols);
        // Table.setEnabled(false);
        Table.setModel(tableModel);

        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });

        try {
            showData();
        } catch (SQLException | ClassNotFoundException e) {
            // Hiển thị lỗi khi xảy ra ngoại lệ
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void showData() throws SQLException, ClassNotFoundException {
        String[] cols = {"ID", "TÊN THÀNH VIÊN", "EMAIL", "SDT", "NGÀY ĐĂNG KÍ", "MẬT KHẨU"};
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
            rows[5] = mem.getPassWord();

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
                Logger.getLogger(MemberManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MemberManager.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(MemberManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MemberManager.class.getName()).log(Level.SEVERE, null, ex);
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
        tableModel.setRowCount(0);

        for (Object[] row : searchResults) {
            tableModel.addRow(row);
        }

        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả nào!", "Not Found",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // TABLE 2: add notification
    public void disPlayTableBorrow() {
        // Cấu hình bảng
        tableModel2 = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] cols = {"ID", "ID THÀNH VIÊN", "ID SÁCH", "NGÀY MƯỢN", "NGÀY TRẢ DỰ KIẾN", "NGÀY TRẢ THỰC TẾ", "TRẠNG THÁI", "TRỄ HẠN(NGÀY)"};
        tableModel2.setColumnIdentifiers(cols);
        Table2.setModel(tableModel2);

        // Thêm sự kiện chuột
        Table2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });

        // Gọi hàm showData để tải dữ liệu
        try {
            showDataBr();
        } catch (SQLException | ClassNotFoundException e) {
            // Hiển thị lỗi khi xảy ra ngoại lệ
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showDataBr() throws SQLException, ClassNotFoundException {
//        tableModel2.setRowCount(0); // Xóa dữ liệu cũ
//
//        // Lấy kết nối cơ sở dữ liệu
//        Connection conn = DBConnection.getConnection();
//        List<Borrow> list = BorrowController.PrintList_MuonTra(conn);
//
//        // Duyệt qua danh sách và thêm vào bảng
//        for (Borrow br : list) {
//            String[] rows = new String[7];
//            rows[0] = Integer.toString(br.getId());
//            rows[1] = br.getMemberId().getId();
//            rows[2] = br.getBookId().getId();
//            rows[3] = br.getBorrowDate().toString();
//            rows[4] = br.getDueDate().toString();
//            rows[5] = (br.getReturnDate() != null) ? br.getReturnDate().toString() : "";
//            rows[6] = br.getStatus();
//
//            tableModel2.addRow(rows);
//        }
        tableModel2.setRowCount(0); // Xóa dữ liệu cũ

// Lấy kết nối cơ sở dữ liệu
        Connection conn = DBConnection.getConnection();
        List<Borrow> list = BorrowController.PrintList_MuonTra(conn);

// Duyệt qua danh sách và thêm vào bảng
        for (Borrow br : list) {
            String[] rows = new String[8]; // Cần 8 cột, bao gồm cột "Days Late"

            // Gán giá trị cho các cột
            rows[0] = Integer.toString(br.getId());
            rows[1] = br.getMemberId().getId();
            rows[2] = br.getBookId().getId();
            rows[3] = br.getBorrowDate().toString();
            rows[4] = br.getDueDate().toString();
            rows[5] = (br.getReturnDate() != null) ? br.getReturnDate().toString() : "";

            // Chuyển đổi dueDate từ java.sql.Date sang LocalDate
            java.sql.Date dueDate = (java.sql.Date) br.getDueDate(); // Lấy dueDate từ Borrow
            LocalDate dueLocalDate = (dueDate != null)
                    ? Instant.ofEpochMilli(dueDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate()
                    : LocalDate.now();

            // Chuyển đổi returnDate từ java.sql.Date sang LocalDate (nếu cần)
            LocalDate returnLocalDate = (br.getReturnDate() != null)
                    ? Instant.ofEpochMilli(br.getReturnDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate()
                    : LocalDate.now();

            // Tính số ngày trễ
            long daysLate = ChronoUnit.DAYS.between(dueLocalDate, returnLocalDate);

            rows[6] = br.getStatus();
            rows[7] = (daysLate > 0) ? Long.toString(daysLate) : "0"; // Nếu ngày trễ <= 0, ghi "0"

            // Thêm hàng vào bảng
            tableModel2.addRow(rows);
        }

    }

    public void SearchBorrow() throws SQLException, ClassNotFoundException {
        String searchText = SearchField1.getText().trim().toLowerCase();
        String selectedCriteria = (String) SearchOpt1.getSelectedItem();

        showData();

        int columnIndex = -1;
        for (int i = 0; i < tableModel2.getColumnCount(); i++) {
            if (tableModel2.getColumnName(i).equalsIgnoreCase(selectedCriteria)) {
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

        for (int i = 0; i < tableModel2.getRowCount(); i++) {
            String cellValue = tableModel2.getValueAt(i, columnIndex).toString().toLowerCase();
            boolean match = false;
            if (selectedCriteria.equalsIgnoreCase("BORROW DATE") || selectedCriteria.equalsIgnoreCase("DUE DATE")
                    || selectedCriteria.equalsIgnoreCase("RETURN DATE")) {
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
                Object[] rowData = new Object[tableModel2.getColumnCount()];
                for (int j = 0; j < tableModel2.getColumnCount(); j++) {
                    rowData[j] = tableModel2.getValueAt(i, j);
                }
                searchResults.add(rowData);
            }
        }
        tableModel2.setRowCount(0);

        for (Object[] row : searchResults) {
            tableModel2.addRow(row);
        }

        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả nào!", "Not Found",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel23 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        logOutBtn = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        memberbtn = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        homebtn = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        borrowbtn = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        staffbtn = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        bookbtn = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        userNameLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();
        memberPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
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
        SearchOpt = new javax.swing.JComboBox<>();
        SearchField = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        reload = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        addMess = new javax.swing.JButton();
        mesPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        contentNoti = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        btnConfirmNoti = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        SearchField1 = new javax.swing.JTextField();
        SearchOpt1 = new javax.swing.JComboBox<>();
        reload1 = new javax.swing.JButton();
        homePanel = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        countMember = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        countBooks = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        countBorrow = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        countStaff = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        title = new javax.swing.JLabel();

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/biaSach1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/biaSach1.png"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        jPanel19.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        logOutBtn.setBackground(new java.awt.Color(153, 153, 153));
        logOutBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        logOutBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logout.png"))); // NOI18N
        logOutBtn.setText("Đăng xuất");
        logOutBtn.setBorder(null);
        logOutBtn.setBorderPainted(false);
        logOutBtn.setContentAreaFilled(false);
        logOutBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logOutBtn.setFocusPainted(false);
        logOutBtn.setMaximumSize(new java.awt.Dimension(89, 20));
        logOutBtn.setMinimumSize(new java.awt.Dimension(89, 20));
        logOutBtn.setPreferredSize(new java.awt.Dimension(89, 20));
        logOutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(logOutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logOutBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        jPanel6.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 426, -1, -1));

        jPanel13.setPreferredSize(new java.awt.Dimension(182, 41));

        memberbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        memberbtn.setText("Thành viên");
        memberbtn.setBorder(null);
        memberbtn.setBorderPainted(false);
        memberbtn.setContentAreaFilled(false);
        memberbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        memberbtn.setFocusPainted(false);
        memberbtn.setMaximumSize(new java.awt.Dimension(89, 20));
        memberbtn.setMinimumSize(new java.awt.Dimension(89, 20));
        memberbtn.setPreferredSize(new java.awt.Dimension(69, 20));
        memberbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memberbtnActionPerformed(evt);
            }
        });

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/member.png"))); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(memberbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(memberbtn, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)))
        );

        jPanel6.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        jPanel8.setForeground(new java.awt.Color(255, 255, 255));

        homebtn.setBackground(new java.awt.Color(204, 204, 255));
        homebtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        homebtn.setText("Trang chủ");
        homebtn.setBorder(null);
        homebtn.setBorderPainted(false);
        homebtn.setContentAreaFilled(false);
        homebtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homebtn.setFocusPainted(false);
        homebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homebtnActionPerformed(evt);
            }
        });

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/home.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(homebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(homebtn, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jPanel14.setPreferredSize(new java.awt.Dimension(182, 41));

        borrowbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        borrowbtn.setText("Mượn trả");
        borrowbtn.setBorder(null);
        borrowbtn.setBorderPainted(false);
        borrowbtn.setContentAreaFilled(false);
        borrowbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        borrowbtn.setFocusPainted(false);
        borrowbtn.setMaximumSize(new java.awt.Dimension(89, 20));
        borrowbtn.setMinimumSize(new java.awt.Dimension(89, 20));
        borrowbtn.setPreferredSize(new java.awt.Dimension(89, 20));
        borrowbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowbtnActionPerformed(evt);
            }
        });

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/book.png"))); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(borrowbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(borrowbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        jPanel10.setPreferredSize(new java.awt.Dimension(182, 41));

        staffbtn.setBackground(new java.awt.Color(235, 231, 242));
        staffbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        staffbtn.setText("Nhân viên");
        staffbtn.setBorder(null);
        staffbtn.setBorderPainted(false);
        staffbtn.setContentAreaFilled(false);
        staffbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        staffbtn.setFocusPainted(false);
        staffbtn.setMaximumSize(new java.awt.Dimension(89, 20));
        staffbtn.setMinimumSize(new java.awt.Dimension(89, 20));
        staffbtn.setPreferredSize(new java.awt.Dimension(89, 20));
        staffbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                staffbtnActionPerformed(evt);
            }
        });

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/staff.png"))); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(staffbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(staffbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel6.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        jPanel18.setPreferredSize(new java.awt.Dimension(182, 41));

        bookbtn.setBackground(new java.awt.Color(235, 231, 242));
        bookbtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        bookbtn.setText("Sách");
        bookbtn.setBorder(null);
        bookbtn.setBorderPainted(false);
        bookbtn.setContentAreaFilled(false);
        bookbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bookbtn.setFocusPainted(false);
        bookbtn.setMaximumSize(new java.awt.Dimension(89, 20));
        bookbtn.setMinimumSize(new java.awt.Dimension(89, 20));
        bookbtn.setPreferredSize(new java.awt.Dimension(89, 20));
        bookbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookbtnActionPerformed(evt);
            }
        });

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/book_menu.png"))); // NOI18N

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bookbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(bookbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 255, 153)));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/admin.png"))); // NOI18N
        jButton6.setBorderPainted(false);
        jButton6.setContentAreaFilled(false);
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.setDefaultCapable(false);
        jButton6.setFocusPainted(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        userNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        userNameLabel.setText("Admin");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 0));
        jLabel3.setText("Online");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton6)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(userNameLabel)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel3)))
                .addGap(0, 0, 0))
        );

        jPanel9.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(13, 70, 13));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("THƯ VIỆN SỐ");
        jLabel1.setOpaque(true);

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/book-icon-auth.png"))); // NOI18N
        jLabel6.setText("jLabel6");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        jPanel9.add(jPanel11, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        contentPanel.setBackground(new java.awt.Color(255, 255, 255));
        contentPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        contentPanel.setLayout(new java.awt.CardLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(1000, 597));

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

        EmailLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        EmailLabel.setText("EMAIL");

        PhoneLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PhoneLabel.setText("SDT");

        NameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        NameLabel.setText("TÊN");

        IdLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        IdLabel.setText("ID");

        MSLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        MSLabel.setText("NGÀY ĐĂNG KÍ");

        PassLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        PassLabel.setText("MẬT KHẨU");

        AddButton.setBackground(new java.awt.Color(204, 204, 204));
        AddButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AddButton.setText("THÊM");
        AddButton.setBorder(null);
        AddButton.setBorderPainted(false);
        AddButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AddButton.setDefaultCapable(false);
        AddButton.setFocusPainted(false);
        AddButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddButtonMouseClicked(evt);
            }
        });
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtonActionPerformed(evt);
            }
        });

        UpdButton.setBackground(new java.awt.Color(204, 204, 204));
        UpdButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        UpdButton.setText("CẬP NHẬT");
        UpdButton.setBorder(null);
        UpdButton.setBorderPainted(false);
        UpdButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        UpdButton.setDefaultCapable(false);
        UpdButton.setFocusPainted(false);
        UpdButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UpdButtonMouseClicked(evt);
            }
        });
        UpdButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdButtonActionPerformed(evt);
            }
        });

        DelButton.setBackground(new java.awt.Color(204, 204, 204));
        DelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DelButton.setText("XÓA");
        DelButton.setBorder(null);
        DelButton.setBorderPainted(false);
        DelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DelButton.setDefaultCapable(false);
        DelButton.setFocusPainted(false);
        DelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DelButtonMouseClicked(evt);
            }
        });
        DelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DelButtonActionPerformed(evt);
            }
        });

        SearchOpt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "NAME", "MEMBERSHIP DATE" }));
        SearchOpt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchOptActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(204, 204, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(102, 102, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/find.png"))); // NOI18N
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setDefaultCapable(false);
        jButton2.setFocusPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        reload.setBackground(new java.awt.Color(204, 204, 204));
        reload.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        reload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        reload.setText("Reload");
        reload.setBorder(null);
        reload.setBorderPainted(false);
        reload.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reload.setDefaultCapable(false);
        reload.setFocusPainted(false);
        reload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reloadMouseClicked(evt);
            }
        });
        reload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadActionPerformed(evt);
            }
        });

        jLabel15.setForeground(new java.awt.Color(153, 153, 153));
        jLabel15.setText("  Copyright © 2024, Trường Đại Học Sư Phạm Kỹ Thuật - Tp.HCM");

        addMess.setBackground(new java.awt.Color(204, 204, 204));
        addMess.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/add.png"))); // NOI18N
        addMess.setText("Notification");
        addMess.setBorder(null);
        addMess.setBorderPainted(false);
        addMess.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addMess.setDefaultCapable(false);
        addMess.setFocusPainted(false);
        addMess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMessActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(281, 281, 281)
                        .addComponent(jLabel15))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(NameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(EmailLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(IdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(PhoneLabel))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                                    .addComponent(jTextField2)
                                    .addComponent(jTextField3)
                                    .addComponent(jTextField4))
                                .addGap(41, 41, 41)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(58, 58, 58)
                                        .addComponent(DelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(88, 88, 88)
                                        .addComponent(UpdButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(AddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(MSLabel)
                                            .addComponent(PassLabel))
                                        .addGap(47, 47, 47)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(40, 40, 40)
                                    .addComponent(SearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(28, 28, 28)
                                    .addComponent(SearchOpt, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(reload, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(77, 77, 77)
                                    .addComponent(addMess))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 881, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SearchField)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SearchOpt, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                        .addComponent(reload, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(addMess)))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(IdLabel)
                        .addComponent(MSLabel)))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(PassLabel)
                        .addGap(33, 33, 33)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UpdButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NameLabel)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EmailLabel))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PhoneLabel))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addContainerGap())
        );

        javax.swing.GroupLayout memberPanelLayout = new javax.swing.GroupLayout(memberPanel);
        memberPanel.setLayout(memberPanelLayout);
        memberPanelLayout.setHorizontalGroup(
            memberPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(memberPanelLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 915, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1261, Short.MAX_VALUE))
        );
        memberPanelLayout.setVerticalGroup(
            memberPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(memberPanelLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 301, Short.MAX_VALUE))
        );

        contentPanel.add(memberPanel, "card5");

        Table2.setModel(new javax.swing.table.DefaultTableModel(
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
        Table2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table2MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table2);

        contentNoti.setColumns(20);
        contentNoti.setRows(5);
        jScrollPane3.setViewportView(contentNoti);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Nhập thông báo");

        btnConfirmNoti.setBackground(new java.awt.Color(204, 204, 204));
        btnConfirmNoti.setText("Gửi");
        btnConfirmNoti.setBorder(null);
        btnConfirmNoti.setBorderPainted(false);
        btnConfirmNoti.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfirmNoti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmNotiActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("ID người nhận");

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(204, 204, 204));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(102, 102, 102));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/find.png"))); // NOI18N
        jButton4.setBorder(null);
        jButton4.setBorderPainted(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        SearchOpt1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "NAME", "MEMBERSHIP DATE" }));
        SearchOpt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchOpt1ActionPerformed(evt);
            }
        });

        reload1.setBackground(new java.awt.Color(204, 204, 204));
        reload1.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        reload1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        reload1.setText("Reload");
        reload1.setBorder(null);
        reload1.setBorderPainted(false);
        reload1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reload1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reload1MouseClicked(evt);
            }
        });
        reload1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reload1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mesPanelLayout = new javax.swing.GroupLayout(mesPanel);
        mesPanel.setLayout(mesPanelLayout);
        mesPanelLayout.setHorizontalGroup(
            mesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mesPanelLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(mesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mesPanelLayout.createSequentialGroup()
                        .addGroup(mesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mesPanelLayout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(SearchField1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(SearchOpt1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(reload1)
                                .addGap(120, 120, 120))
                            .addGroup(mesPanelLayout.createSequentialGroup()
                                .addGroup(mesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(119, 119, 119)
                                .addGroup(mesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnConfirmNoti, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(1362, 1362, 1362))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mesPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 879, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1271, 1271, 1271))))
        );
        mesPanelLayout.setVerticalGroup(
            mesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mesPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(mesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addComponent(SearchField1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SearchOpt1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(reload1)))
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnConfirmNoti, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(329, Short.MAX_VALUE))
        );

        contentPanel.add(mesPanel, "card6");

        homePanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel17.setPreferredSize(new java.awt.Dimension(168, 178));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel29.setText("Tổng số thành viên:");

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/member_panel.png"))); // NOI18N

        countMember.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        countMember.setForeground(new java.awt.Color(102, 102, 102));
        countMember.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        countMember.setText("10");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addGap(22, 22, 22))
            .addComponent(countMember, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel38)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(countMember)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel12.setPreferredSize(new java.awt.Dimension(168, 178));

        jLabel39.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel39.setText("Tổng số lượng sách:");

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/book_panel.png"))); // NOI18N

        countBooks.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        countBooks.setForeground(new java.awt.Color(102, 102, 102));
        countBooks.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        countBooks.setText("10");
        countBooks.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        countBooks.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel41.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel39)
                .addGap(37, 37, 37))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel40))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel41))
                    .addComponent(countBooks, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(countBooks)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButton10.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/info.png"))); // NOI18N
        jButton10.setText("Info");
        jButton10.setBorder(null);
        jButton10.setBorderPainted(false);
        jButton10.setContentAreaFilled(false);
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jPanel20.setPreferredSize(new java.awt.Dimension(168, 178));

        jLabel42.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel42.setText("Số sách đã trả:");

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/book_da_tra_panel.png"))); // NOI18N

        countBorrow.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        countBorrow.setForeground(new java.awt.Color(102, 102, 102));
        countBorrow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        countBorrow.setText("10");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel42)
                    .addComponent(jLabel14))
                .addGap(30, 30, 30))
            .addComponent(countBorrow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(countBorrow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel18.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel18.setText("Tổng số nhân viên:");

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/staff_panel.png"))); // NOI18N

        countStaff.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        countStaff.setForeground(new java.awt.Color(102, 102, 102));
        countStaff.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        countStaff.setText("10");
        countStaff.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel27))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel18)))
                .addContainerGap(19, Short.MAX_VALUE))
            .addComponent(countStaff, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(countStaff)
                .addGap(18, 18, 18)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout homePanelLayout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);
        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(260, 260, 260)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(109, 109, 109)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1471, Short.MAX_VALUE))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(387, Short.MAX_VALUE))
        );

        contentPanel.add(homePanel, "card2");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel3.setPreferredSize(new java.awt.Dimension(90, 55));

        title.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        title.setText("Trang chủ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(title)
                .addContainerGap(796, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(title)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 912, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(contentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1112, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // nut dang xuat
    private void logOutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutBtnActionPerformed
        int response = javax.swing.JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận",
                javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
        if (response == javax.swing.JOptionPane.YES_OPTION) {
            LoginPage login = new LoginPage();
            login.setVisible(true);
            login.pack();
            login.setLocationRelativeTo(null);
            this.dispose();
        }
    }//GEN-LAST:event_logOutBtnActionPerformed

    // cai dat username cho tai khoan
    private void setUsername() {
        userNameLabel.setText(this.userName);
    }

    // avatar 
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        title.setText("Chỉnh sửa hồ sơ");
        contentPanel.removeAll();
        contentPanel.add(mesPanel);
        contentPanel.repaint();
        contentPanel.revalidate();
    }//GEN-LAST:event_jButton6ActionPerformed

    // ham reload page khi sua thong tin ca nhan
    public void reloadPage(String newUsername) {
        MemberManager m = new MemberManager(newUsername);
        userNameLabel.setText(m.userName);
    }

    private void memberbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memberbtnActionPerformed
        title.setText("Thành viên");
        contentPanel.removeAll();
        contentPanel.add(memberPanel);
        contentPanel.repaint();
        contentPanel.revalidate();
    }//GEN-LAST:event_memberbtnActionPerformed

    private void homebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homebtnActionPerformed
        title.setText("Trang chủ");
        contentPanel.removeAll();
        contentPanel.add(homePanel);
        contentPanel.repaint();
        contentPanel.revalidate();
    }//GEN-LAST:event_homebtnActionPerformed

    private void borrowbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowbtnActionPerformed
        BorrowManager b = new BorrowManager(userName);
        b.setVisible(true);
        b.pack();
        b.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_borrowbtnActionPerformed

    private void staffbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_staffbtnActionPerformed
        StaffManager s = new StaffManager(userName);
        s.setVisible(true);
        s.pack();
        s.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_staffbtnActionPerformed

    private void bookbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookbtnActionPerformed
        BookManager b = new BookManager(userName);
        b.setVisible(true);
        b.pack();
        b.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_bookbtnActionPerformed

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

    private void AddButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_AddButtonMouseClicked

    private void UpdButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UpdButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_UpdButtonMouseClicked

    private void DelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DelButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_DelButtonMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            // TODO add your handling code here:
            SearchMem();
        } catch (SQLException ex) {
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void reloadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reloadMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_reloadMouseClicked

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
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
            Logger.getLogger(MemberManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MemberManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_AddButtonActionPerformed

    private void UpdButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdButtonActionPerformed
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
            Logger.getLogger(MemberManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MemberManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_UpdButtonActionPerformed

    private void DelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DelButtonActionPerformed
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
                Logger.getLogger(MemberManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MemberManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_DelButtonActionPerformed

    private void reloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadActionPerformed
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jPasswordField1.setText("");
        SearchField.setText("");
        displayTableMembers();
    }//GEN-LAST:event_reloadActionPerformed

    private void SearchOptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchOptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchOptActionPerformed

    private void addMessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMessActionPerformed
        title.setText("Gửi thông báo");
        contentPanel.removeAll();
        contentPanel.add(mesPanel);
        contentPanel.repaint();
        contentPanel.revalidate();
    }//GEN-LAST:event_addMessActionPerformed

    private void reload1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reload1ActionPerformed
        jTextField6.setText("");
        disPlayTableBorrow();
    }//GEN-LAST:event_reload1ActionPerformed

    private void reload1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reload1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_reload1MouseClicked

    private void SearchOpt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchOpt1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchOpt1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            // TODO add your handling code here:
            SearchBorrow();
        } catch (SQLException ex) {
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        int cnt = HomePageLogic.getCount("Staff");
        countStaff.setText(String.valueOf(cnt));
        int cnt2 = HomePageLogic.getCount("Books");
        countBooks.setText(String.valueOf(cnt2));
        int cnt3 = HomePageLogic.getCount("Members");
        countMember.setText(String.valueOf(cnt3));
        int cnt4 = HomePageLogic.getCount("Borrow");
        countBorrow.setText(String.valueOf(cnt4));
        String message = "Thông tin quản lí " + userName + " :\n"
                + "1. Tổng số sách tồn kho: " + cnt2 + " .\n"
                + "2. Tổng số nhân viên:" + cnt + " .\n"
                + "3. Tổng số thành viên: " + cnt3 + " .\n"
                + " 4. Tổng số sách đã mượn: " + cnt4 + " .\n";
        // Hiển thị thông báo
        JOptionPane.showMessageDialog(this, message, "Thông Báo", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void btnConfirmNotiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmNotiActionPerformed
        // Lấy nội dung thông báo từ giao diện va id
        String message = contentNoti.getText();
        String id = jTextField6.getText();
        // Gửi thông báo qua phương thức updateMes
        Boolean result = MemberController.updateMes(message, id);

        // Hiển thị thông báo thành công hoặc thất bại
        if (result) {
            JOptionPane.showMessageDialog(null, "Gửi tin thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Gửi tin thất bại. Vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnConfirmNotiActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void Table2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table2MouseClicked
        int selectedRow = Table2.getSelectedRow();
        int s = Table2.getSelectedRow();
        if (selectedRow != -1) {
            jTextField6.setText(Table2.getValueAt(s, 1).toString()); // ID
        }
    }//GEN-LAST:event_Table2MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // Chạy giao diện người dùng trong Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Khởi tạo và hiển thị cửa sổ HomePageUser
                MemberManager homeUser = new MemberManager("Admin");
                homeUser.setVisible(true);
                homeUser.pack();
                homeUser.setLocationRelativeTo(null); // Đặt cửa sổ ở giữa màn hình
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
    private javax.swing.JTextField SearchField;
    private javax.swing.JTextField SearchField1;
    private javax.swing.JComboBox<String> SearchOpt;
    private javax.swing.JComboBox<String> SearchOpt1;
    private javax.swing.JTable Table;
    private javax.swing.JTable Table2;
    private javax.swing.JButton UpdButton;
    private javax.swing.JButton addMess;
    private javax.swing.JButton bookbtn;
    private javax.swing.JButton borrowbtn;
    private javax.swing.JButton btnConfirmNoti;
    private javax.swing.JTextArea contentNoti;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JLabel countBooks;
    private javax.swing.JLabel countBorrow;
    private javax.swing.JLabel countMember;
    private javax.swing.JLabel countStaff;
    private javax.swing.JPanel homePanel;
    private javax.swing.JButton homebtn;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JButton logOutBtn;
    private javax.swing.JPanel memberPanel;
    private javax.swing.JButton memberbtn;
    private javax.swing.JPanel mesPanel;
    private javax.swing.JButton reload;
    private javax.swing.JButton reload1;
    private javax.swing.JButton staffbtn;
    private javax.swing.JLabel title;
    private javax.swing.JLabel userNameLabel;
    // End of variables declaration//GEN-END:variables
}
