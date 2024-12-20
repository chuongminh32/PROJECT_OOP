/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import java.util.List;
import controllers.HomePageLogic;
import controllers.StaffController;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import models.Staff;

/**
 *
 * @author chuon
 */
public class StaffManager extends javax.swing.JFrame {

    private StaffController staffcontroller = new StaffController();
    private DefaultTableModel dtablemodel;
    private boolean isEditing = false;

    private String userName;

    /**
     * Creates new form home_page_user
     *
     * @param <error>
     */
    public StaffManager(String userName) {
        this.userName = userName;
        initComponents();
        setUsername();
        displayCount();
        displayTableStaff();
    }

    private void displayCount() {
        int cnt = HomePageLogic.getCount("Staff");
        countStaff.setText(String.valueOf(cnt));
        int cnt2 = HomePageLogic.getCount("Books");
        countBooks.setText(String.valueOf(cnt2));
        int cnt3 = HomePageLogic.getCount("Members");
        countMembers.setText(String.valueOf(cnt3));
        int cnt4 = HomePageLogic.getCount("Borrow");
        countBorrow.setText(String.valueOf(cnt4));
    }

    private void setData(List<Staff> staff_list) throws SQLException, ClassNotFoundException {
        dtablemodel.setRowCount(0);
        //Truy xuất dữ liệu từ database
        for (Staff staff : staff_list) {
            dtablemodel.addRow(new Object[]{staff.getId(), staff.getName(), staff.getEmail(),
                staff.getPhoneNumber(), staff.getRole(), staff.getHireDate(), staff.getPassword()});
        } //close for

    }

    private void add_Staff(Staff staff) throws SQLException, ClassNotFoundException {
        //Thêm mới người dùng vào database và hiển thị ra màn hình
        this.staffcontroller.insertStaff(staff);
        setData(staffcontroller.getAllStaffs());
    }

    private void delete_Staff(String id) throws SQLException, ClassNotFoundException {
        //Thêm mới người dùng vào database và hiển thị ra màn hình
        this.staffcontroller.deleteStaff(id);
        setData(staffcontroller.getAllStaffs());
    }

    private void update_Staff(String id, Staff staff) throws SQLException, ClassNotFoundException {
        //Cập nhật người dùng, nếu không tìm thấy id thì thêm mới
        this.staffcontroller.updateStaff(staff, id);
        setData(staffcontroller.getAllStaffs());
    }

    private void set_field(boolean root) {
        this.nameField.setEnabled(root);
        this.idField.setEnabled(root);
        this.phoneField.setEnabled(root);
        this.emailField.setEnabled(root);
        this.hireDateField.setEnabled(root);
        this.PasswordField.setEnabled(root);
        this.positionField.setEnabled(root);
    }

    private void set_field_value(int row) {
        this.idField.setText((String) this.Staff_Table.getValueAt(row, 0));
        this.nameField.setText((String) this.Staff_Table.getValueAt(row, 1));
        this.emailField.setText((String) this.Staff_Table.getValueAt(row, 2));
        this.phoneField.setText((String) this.Staff_Table.getValueAt(row, 3));
        this.positionField.setText((String) this.Staff_Table.getValueAt(row, 4));
        this.hireDateField.setText(this.Staff_Table.getValueAt(row, 5).toString()); // Chuyển đổi giá trị ngày tháng thành chuỗi
        this.PasswordField.setText((String) this.Staff_Table.getValueAt(row, 6));
    }

    private boolean is_nonnull_field() {
        if (this.nameField.getText().trim().isEmpty()
                || this.hireDateField.getText().trim().isEmpty()
                || this.PasswordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(StaffManager.this, "You have to fill in the blank!", "Input Error", JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }

    public void displayTableStaff() {
        /* HÀM HIỂN THỊ BẢNG QUẢN LÝ NHÂN VIÊN */
        try {
            this.dtablemodel = new DefaultTableModel() {
                // Không cho phép người dùng sửa trực tiếp trên bảng
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            this.Staff_Table.setModel(dtablemodel);

            dtablemodel.addColumn("ID");
            dtablemodel.addColumn("TÊN");
            dtablemodel.addColumn("EMAIL");
            dtablemodel.addColumn("SDT");
            dtablemodel.addColumn("VỊ TRÍ");
            dtablemodel.addColumn("NGÀY ĐĂNG KÍ");
            dtablemodel.addColumn("MẬT KHẨU");

            // Thử lấy dữ liệu và đặt vào bảng
            setData(staffcontroller.getAllStaffs());

            // Đảm bảo bảng luôn chiếm hết chiều rộng của JScrollPane 
            Staff_Table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Log lỗi cho việc debug
            // Hiển thị thông báo lỗi cho người dùng
            String errorMessage = (e instanceof SQLException)
                    ? "Lỗi kết nối cơ sở dữ liệu!"
                    : "Không tìm thấy driver JDBC! Vui lòng kiểm tra lại thư viện.";
            JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi không xác định
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi không xác định.", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
        jButton3 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();
        staffPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Staff_Table = new javax.swing.JTable();
        add_button = new javax.swing.JButton();
        refresh_button1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        idField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        emailField = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        phoneField = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        hireDateField = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        PasswordField = new javax.swing.JPasswordField();
        delete_button = new javax.swing.JButton();
        update_button = new javax.swing.JButton();
        positionField = new javax.swing.JTextField();
        search_button = new javax.swing.JButton();
        refresh_button2 = new javax.swing.JButton();
        add_button1 = new javax.swing.JButton();
        delete_button1 = new javax.swing.JButton();
        update_button1 = new javax.swing.JButton();
        search_button1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        homePanel = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        countStaff = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        countMembers = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        countBorrow = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        countBooks = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        InFoUserPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnConfirm = new javax.swing.JButton();
        name = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        phone = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        pass = new javax.swing.JPasswordField();
        btnOld = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
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
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/setting.png"))); // NOI18N
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setDefaultCapable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(userNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3))
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
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(userNameLabel)
                            .addComponent(jButton3))
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

        Staff_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Staff_Table.setToolTipText("");
        Staff_Table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        Staff_Table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Staff_Table.setFillsViewportHeight(true);
        Staff_Table.setShowGrid(false);
        jScrollPane1.setViewportView(Staff_Table);

        add_button.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add_button.setText("ADD");
        add_button.setPreferredSize(new java.awt.Dimension(75, 30));
        add_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_buttonActionPerformed(evt);
            }
        });

        refresh_button1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        refresh_button1.setText("REFRESH");
        refresh_button1.setPreferredSize(new java.awt.Dimension(75, 30));
        refresh_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refresh_button1ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("TÊN");

        nameField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nameField.setAutoscrolls(false);
        nameField.setEnabled(false);
        nameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameFieldActionPerformed(evt);
            }
        });

        idField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        idField.setAutoscrolls(false);
        idField.setEnabled(false);
        idField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idFieldActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("ID:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("VAI TRÒ");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("EMAIL");

        emailField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        emailField.setAutoscrolls(false);
        emailField.setEnabled(false);
        emailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailFieldActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setText("SDT");

        phoneField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        phoneField.setAutoscrolls(false);
        phoneField.setEnabled(false);
        phoneField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneFieldActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("NGÀY ĐĂNG KÍ");

        hireDateField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        hireDateField.setToolTipText("");
        hireDateField.setAutoscrolls(false);
        hireDateField.setEnabled(false);
        hireDateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hireDateFieldActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setText("MẬT KHẨU");

        PasswordField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        PasswordField.setToolTipText("");
        PasswordField.setEnabled(false);

        delete_button.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        delete_button.setText("DELETE");
        delete_button.setAutoscrolls(true);
        delete_button.setPreferredSize(new java.awt.Dimension(75, 30));
        delete_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_buttonActionPerformed(evt);
            }
        });

        update_button.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        update_button.setText("UPDATE");
        update_button.setAutoscrolls(true);
        update_button.setPreferredSize(new java.awt.Dimension(75, 30));
        update_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_buttonActionPerformed(evt);
            }
        });

        positionField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        positionField.setToolTipText("");
        positionField.setEnabled(false);

        search_button.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        search_button.setText("SEARCH");
        search_button.setAutoscrolls(true);
        search_button.setPreferredSize(new java.awt.Dimension(75, 30));
        search_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_buttonActionPerformed(evt);
            }
        });

        refresh_button2.setBackground(new java.awt.Color(204, 204, 204));
        refresh_button2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        refresh_button2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        refresh_button2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        refresh_button2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        refresh_button2.setPreferredSize(new java.awt.Dimension(75, 30));
        refresh_button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refresh_button2ActionPerformed(evt);
            }
        });

        add_button1.setBackground(new java.awt.Color(204, 204, 204));
        add_button1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        add_button1.setText("THÊM");
        add_button1.setBorder(null);
        add_button1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add_button1.setPreferredSize(new java.awt.Dimension(75, 30));
        add_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_button1ActionPerformed(evt);
            }
        });

        delete_button1.setBackground(new java.awt.Color(204, 204, 204));
        delete_button1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        delete_button1.setText("XÓA");
        delete_button1.setAutoscrolls(true);
        delete_button1.setBorder(null);
        delete_button1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        delete_button1.setPreferredSize(new java.awt.Dimension(75, 30));
        delete_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_button1ActionPerformed(evt);
            }
        });

        update_button1.setBackground(new java.awt.Color(204, 204, 204));
        update_button1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        update_button1.setText("SỬA");
        update_button1.setAutoscrolls(true);
        update_button1.setBorder(null);
        update_button1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        update_button1.setPreferredSize(new java.awt.Dimension(75, 30));
        update_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_button1ActionPerformed(evt);
            }
        });

        search_button1.setBackground(new java.awt.Color(204, 204, 204));
        search_button1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        search_button1.setText("TÌM");
        search_button1.setAutoscrolls(true);
        search_button1.setBorder(null);
        search_button1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        search_button1.setPreferredSize(new java.awt.Dimension(75, 30));
        search_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_button1ActionPerformed(evt);
            }
        });

        jLabel15.setForeground(new java.awt.Color(153, 153, 153));
        jLabel15.setText("  Copyright © 2024, Trường Đại Học Sư Phạm Kỹ Thuật - Tp.HCM");

        javax.swing.GroupLayout staffPanelLayout = new javax.swing.GroupLayout(staffPanel);
        staffPanel.setLayout(staffPanelLayout);
        staffPanelLayout.setHorizontalGroup(
            staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffPanelLayout.createSequentialGroup()
                .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(staffPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(staffPanelLayout.createSequentialGroup()
                                .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(refresh_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(staffPanelLayout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(refresh_button2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(224, 224, 224)
                                .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(staffPanelLayout.createSequentialGroup()
                                        .addComponent(add_button, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(delete_button, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(update_button, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(search_button, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(staffPanelLayout.createSequentialGroup()
                                        .addGap(168, 168, 168)
                                        .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel22)))))
                            .addGroup(staffPanelLayout.createSequentialGroup()
                                .addGap(645, 645, 645)
                                .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(PasswordField, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                    .addComponent(hireDateField, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                    .addComponent(positionField)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, staffPanelLayout.createSequentialGroup()
                                .addComponent(update_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(search_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(staffPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(staffPanelLayout.createSequentialGroup()
                                .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(staffPanelLayout.createSequentialGroup()
                                        .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(79, 79, 79)
                                        .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(idField, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(staffPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(79, 79, 79)
                                        .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, staffPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel21)
                                        .addGap(104, 104, 104)
                                        .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(delete_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(phoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(add_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 879, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 348, Short.MAX_VALUE))
            .addGroup(staffPanelLayout.createSequentialGroup()
                .addGap(271, 271, 271)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        staffPanelLayout.setVerticalGroup(
            staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(staffPanelLayout.createSequentialGroup()
                        .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(staffPanelLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(idField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(staffPanelLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(staffPanelLayout.createSequentialGroup()
                        .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(staffPanelLayout.createSequentialGroup()
                                .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(positionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(staffPanelLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(hireDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(7, 7, 7)
                .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refresh_button2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(add_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(delete_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(update_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(search_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(39, 39, 39)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
                .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refresh_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add_button, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete_button, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(update_button, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search_button, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        contentPanel.add(staffPanel, "card5");

        homePanel.setBackground(new java.awt.Color(255, 255, 255));

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

        jPanel16.setPreferredSize(new java.awt.Dimension(168, 178));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel28.setText("Tổng số thành viên:");

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/member_panel.png"))); // NOI18N

        countMembers.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        countMembers.setForeground(new java.awt.Color(102, 102, 102));
        countMembers.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        countMembers.setText("10");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel28)
                .addGap(22, 22, 22))
            .addComponent(countMembers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel37)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(countMembers)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel17.setPreferredSize(new java.awt.Dimension(168, 178));

        jLabel38.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel38.setText("Số sách đã trả:");

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/book_da_tra_panel.png"))); // NOI18N

        countBorrow.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        countBorrow.setForeground(new java.awt.Color(102, 102, 102));
        countBorrow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        countBorrow.setText("10");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel38)
                    .addComponent(jLabel14))
                .addGap(30, 30, 30))
            .addComponent(countBorrow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(countBorrow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        javax.swing.GroupLayout homePanelLayout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);
        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(109, 109, 109)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(581, Short.MAX_VALUE))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(369, Short.MAX_VALUE))
        );

        contentPanel.add(homePanel, "card2");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("New username:");

        btnConfirm.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConfirm.setText("Xác nhận");
        btnConfirm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnConfirm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        name.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nameMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nameMouseEntered(evt);
            }
        });
        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Chỉnh sửa thông tin cá nhân");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("New email:");

        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("New phone:");

        phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("New password:");

        pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passActionPerformed(evt);
            }
        });

        btnOld.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnOld.setText("Thông tin cũ");
        btnOld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnOld.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOldActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/eye.png"))); // NOI18N
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout InFoUserPanelLayout = new javax.swing.GroupLayout(InFoUserPanel);
        InFoUserPanel.setLayout(InFoUserPanelLayout);
        InFoUserPanelLayout.setHorizontalGroup(
            InFoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InFoUserPanelLayout.createSequentialGroup()
                .addGap(261, 261, 261)
                .addGroup(InFoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InFoUserPanelLayout.createSequentialGroup()
                        .addGroup(InFoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel7))
                        .addGap(8, 8, 8)
                        .addGroup(InFoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(InFoUserPanelLayout.createSequentialGroup()
                                .addGroup(InFoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(InFoUserPanelLayout.createSequentialGroup()
                                        .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(btnOld, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)
                                .addComponent(jButton1))
                            .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(InFoUserPanelLayout.createSequentialGroup()
                        .addGroup(InFoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(InFoUserPanelLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(32, 32, 32))
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(3, 3, 3)
                        .addGroup(InFoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4)
                            .addComponent(name, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                            .addComponent(email, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap(577, Short.MAX_VALUE))
        );
        InFoUserPanelLayout.setVerticalGroup(
            InFoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InFoUserPanelLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(jLabel4)
                .addGap(54, 54, 54)
                .addGroup(InFoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(InFoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(InFoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(InFoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(InFoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32)
                .addGroup(InFoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOld, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(425, Short.MAX_VALUE))
        );

        contentPanel.add(InFoUserPanel, "card6");

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

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1112, 650));

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
        contentPanel.add(InFoUserPanel);
        contentPanel.repaint();
        contentPanel.revalidate();
    }//GEN-LAST:event_jButton6ActionPerformed

    // ham tang so luong sach khi tra sach thanh cong
    private void updateAvailableCopies(Connection conn, String bookTitle) throws SQLException {
        String updateBookSql = "UPDATE Books SET availableCopies = availableCopies + 1 WHERE title = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateBookSql)) {
            pstmt.setString(1, bookTitle);
            pstmt.executeUpdate();
        }
    }

    // ham update dong du lieu trong bang Borrow khi tra sach
    private Boolean updateBorrowRecord(Connection conn, String memberId, String bookId) throws SQLException {
        // Câu lệnh SQL để cập nhật returnDate
        String updateBorrowSql = "UPDATE Borrow SET returnDate = ?, status = ? WHERE memberId = ? AND bookId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateBorrowSql)) {
            // Đặt giá trị returnDate là ngày hôm nay
            pstmt.setDate(1, new java.sql.Date(System.currentTimeMillis())); // Lấy ngày hiện tại
            pstmt.setString(2, "Da tra");
            pstmt.setString(3, memberId);
            pstmt.setString(4, bookId);

            // Thực thi cập nhật
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    // ham reload page khi sua thong tin ca nhan
    public void reloadPage(String newUsername) {
        StaffManager s = new StaffManager(newUsername);
        this.userName = s.userName;
        userNameLabel.setText(s.userName);
    }

    // nut xac nhan chinh sua thong tin: lay thong tin tu textField -> cap nhat sql (co check loi) && reloadPage
    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed

        try {
            // Lấy ID dựa trên tên người dùng (giả sử HomePageLogic.getId() trả về ID hợp lệ)
            String id = HomePageLogic.getId("Staff", this.userName);

            // Lấy các thông tin mới từ các trường nhập liệu
            String newName = name.getText();
            String newMail = email.getText();
            String newPhone = phone.getText();
            String newPass = String.valueOf(pass.getPassword());

            // Kiểm tra các trường nhập liệu không bị rỗng
            if (newName.isEmpty() || newMail.isEmpty() || newPhone.isEmpty() || newPass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                return; // Dừng lại nếu có trường thông tin bị bỏ trống
            }

            // Gọi phương thức cập nhật thông tin trong cơ sở dữ liệu
            boolean isUpdated = HomePageLogic.editInfo(id, newName, newMail, newPhone, newPass, "Staff");

            if (isUpdated) {
                // Hiển thị thông báo thành công nếu cập nhật thành công
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                // Tải lại trang (hoặc giao diện)
                reloadPage(newName); // Gọi phương thức để tải lại trang
            } else {
                // Hiển thị thông báo lỗi nếu cập nhật không thành công
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            // Xử lý ngoại lệ cho các lỗi không mong muốn
            e.printStackTrace(); // In chi tiết lỗi để phục vụ việc gỡ lỗi
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnConfirmActionPerformed

    private void passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passActionPerformed

    private void nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameActionPerformed

    }//GEN-LAST:event_nameActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
    }//GEN-LAST:event_emailActionPerformed

    private void phoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneActionPerformed

    }//GEN-LAST:event_phoneActionPerformed

    private void nameMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nameMouseEntered

    }//GEN-LAST:event_nameMouseEntered

    private void nameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nameMouseClicked
    }//GEN-LAST:event_nameMouseClicked

    // nut thong tin cu (hien thi lai thong tin cu truoc khi sua thong tin moi)
    private void btnOldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOldActionPerformed
        String id = HomePageLogic.getId("Staff", this.userName);
        Staff s = StaffController.getAStaff(id);
        name.setText(s.getName());
        email.setText(s.getEmail());
        phone.setText(s.getPhoneNumber());
    }//GEN-LAST:event_btnOldActionPerformed

    // icon con mat show password
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        pass.setEchoChar((char) 0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void add_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_buttonActionPerformed
        //HÀM BUTTON THÊM MỚI DỮ LIỆU VÀO DATABASE
        if (isEditing == false) {
            this.set_field(true);
            this.isEditing = true;
        } else {
            if (this.idField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID field cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (!this.is_nonnull_field()) {
                    String email = this.emailField.getText().trim();

                    try {
                        // Kiểm tra nếu email đã tồn tại trong cơ sở dữ liệu
                        if (this.staffcontroller.isEmailExists(email)) {
                            JOptionPane.showMessageDialog(this, "Error: Email " + email + " already exists.", "Database Error", JOptionPane.ERROR_MESSAGE);
                            return; // Dừng thực hiện nếu email đã tồn tại
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {

                        Staff new_staff = new Staff();
                        new_staff.setId(this.idField.getText());
                        new_staff.setName(this.nameField.getText());
                        new_staff.setEmail(this.emailField.getText());
                        new_staff.setPhoneNumber(this.phoneField.getText());
                        new_staff.setRole(this.positionField.getText());
                        char[] cpassword = this.PasswordField.getPassword();
                        String password = new String(cpassword);
                        new_staff.setPassword(password);
                        Date hireDate = Date.valueOf(this.hireDateField.getText());
                        new_staff.setHireDate(hireDate);

                        this.add_Staff(new_staff);

                        JOptionPane.showMessageDialog(this, "Staff inserted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        this.set_field(false);
                        this.isEditing = false;
                    } catch (SQLException | ClassNotFoundException ex) {
                        if (ex.getMessage().contains("Violation of PRIMARY KEY constraint")) {
                            JOptionPane.showMessageDialog(this, "Error: ID " + this.idField.getText() + " already exists.", "Database Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (IllegalArgumentException ex) {
                        // Xử lý lỗi khi nhập dữ liệu không đúng định dạng cho hireDate
                        JOptionPane.showMessageDialog(this, "Invalid hire date format. Please enter the date in the format yyyy-mm-dd.", "Input Error", JOptionPane.ERROR_MESSAGE);

                    }
                }

            }
        }
    }//GEN-LAST:event_add_buttonActionPerformed

    private void refresh_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refresh_button1ActionPerformed
        //HÀM BUTTON REFRESH DỮ LIỆU TỪ DATABASE
        try {
            dtablemodel.setRowCount(0);
            this.setData(staffcontroller.getAllStaffs());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            String errorMessage = (e instanceof SQLException) ? "Lỗi kết nối cơ sở dữ liệu!" : "Không tìm thấy driver JDBC! Vui lòng kiểm tra lại thư viện.";
            JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_refresh_button1ActionPerformed

    private void nameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameFieldActionPerformed

    private void idFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idFieldActionPerformed

    private void emailFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailFieldActionPerformed

    private void phoneFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneFieldActionPerformed

    private void hireDateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hireDateFieldActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_hireDateFieldActionPerformed

    private void delete_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_buttonActionPerformed
        /*BUTTON XÓA STAFF RA KHỎI DATABASE
        - Có thể xóa từng bản ghi hoặc nhiều bản ghi cùng lúc
        - Chọn trực tiếp trên bản và ấn vào button "DELETE"
         */
        int[] rows = this.Staff_Table.getSelectedRows();

        if (rows.length < 1) {
            JOptionPane.showMessageDialog(StaffManager.this, "You haven't choose any staff!");
        } else {
            int confirm = JOptionPane.showConfirmDialog(StaffManager.this, "Are you sure to delete this staff?");

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    boolean delete = false;
                    String staff_id;
                    //Điều chỉnh giá trị index khi xóa lần lượt từng bản ghi
                    for (int i = 0; i < rows.length; i++) {
                        if (!delete) {
                            staff_id = String.valueOf(this.Staff_Table.getValueAt(rows[i] - i, 0));
                        } else {
                            staff_id = String.valueOf(this.Staff_Table.getValueAt(rows[i], 0));
                            delete = true;
                        }
                        this.delete_Staff(staff_id);
                    }

                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                    String errorMessage = (e instanceof SQLException) ? "Lỗi kết nối cơ sở dữ liệu!" : "Không tìm thấy driver JDBC! Vui lòng kiểm tra lại thư viện.";
                    JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
    }//GEN-LAST:event_delete_buttonActionPerformed

    private void update_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_buttonActionPerformed
        // BUTTON UPDATE THÔNG TIN STAFF
        int row = this.Staff_Table.getSelectedRow();
        //        Staff staff = this.Staff_Table.getValueAt(row);
        if (row == -1) {
            JOptionPane.showMessageDialog(StaffManager.this, "You haven't choose any staff!");
        } else {
            if (!isEditing) {
                this.set_field_value(row);
                this.set_field(true);
                this.idField.setEnabled(false);
                isEditing = true;
            } else {
                try {
                    this.set_field(false);
                    isEditing = false;
                    Staff new_staff = new Staff();
                    new_staff.setId(this.idField.getText());
                    new_staff.setName(this.nameField.getText());
                    new_staff.setEmail(this.emailField.getText());
                    new_staff.setPhoneNumber(this.phoneField.getText());
                    new_staff.setRole(this.positionField.getText());
                    char[] cpassword = this.PasswordField.getPassword();
                    String password = new String(cpassword);
                    new_staff.setPassword(password);
                    Date hireDate = Date.valueOf(this.hireDateField.getText());
                    new_staff.setHireDate(hireDate);

                    this.update_Staff(new_staff.getId(), new_staff);

                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                    String errorMessage = (e instanceof SQLException) ? "Lỗi kết nối cơ sở dữ liệu!" : "Không tìm thấy driver JDBC! Vui lòng kiểm tra lại thư viện.";
                    JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
    }//GEN-LAST:event_update_buttonActionPerformed

    private void search_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_buttonActionPerformed
        // BUTTON TÌM KIẾM THEO CÁC TRƯỜNG, KHÔNG PHÂN BIỆT HOA THƯỜNG
        if (!this.isEditing) {
            this.set_field(true);
            this.isEditing = true;
        } else {
            this.isEditing = false;
            this.set_field(false);
            String id = this.idField.getText();
            String name = this.nameField.getText();
            String email = this.emailField.getText();
            String phone = this.phoneField.getText();
            String position = this.positionField.getText();
            char[] cpassword = this.PasswordField.getPassword();
            String password = new String(cpassword);
            String hireDate = this.hireDateField.getText();

            List<Staff> staffs = null;
            try {
                staffs = this.staffcontroller.findStaffByPartialFields(id, name, email, phone, position, hireDate, password);

                this.setData(staffs);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                String errorMessage = (e instanceof SQLException) ? "Lỗi kết nối cơ sở dữ liệu!" : "Không tìm thấy driver JDBC! Vui lòng kiểm tra lại thư viện.";
                JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_search_buttonActionPerformed

    private void memberbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memberbtnActionPerformed
        MemberManager m = new MemberManager(userName);
        m.setVisible(true);
        m.pack();
        m.setLocationRelativeTo(null);
        this.dispose();
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
        title.setText("Nhân viên");
        contentPanel.removeAll();
        contentPanel.add(staffPanel);
        contentPanel.repaint();
        contentPanel.revalidate();
    }//GEN-LAST:event_staffbtnActionPerformed

    private void bookbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookbtnActionPerformed
        BookManager b = new BookManager(userName);
        b.setVisible(true);
        b.pack();
        b.setLocationRelativeTo(null);
        this.dispose();
    }//GEN-LAST:event_bookbtnActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        int cnt = HomePageLogic.getCount("Staff");
        countStaff.setText(String.valueOf(cnt));
        int cnt2 = HomePageLogic.getCount("Books");
        countBooks.setText(String.valueOf(cnt2));
        int cnt3 = HomePageLogic.getCount("Members");
        countMembers.setText(String.valueOf(cnt3));
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

    private void refresh_button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refresh_button2ActionPerformed
        //HÀM BUTTON REFRESH DỮ LIỆU TỪ DATABASE
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        positionField.setText("");
        hireDateField.setText("");
        PasswordField.setText("");

        try {
            dtablemodel.setRowCount(0);
            this.setData(staffcontroller.getAllStaffs());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            String errorMessage = (e instanceof SQLException) ? "Lỗi kết nối cơ sở dữ liệu!" : "Không tìm thấy driver JDBC! Vui lòng kiểm tra lại thư viện.";
            JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_refresh_button2ActionPerformed

    private void add_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_button1ActionPerformed
        //HÀM BUTTON THÊM MỚI DỮ LIỆU VÀO DATABASE
        if (!isEditing) {
            this.set_field(true);
            this.isEditing = true;
        } else {
            if (this.idField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Trường ID không được để trống.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            } else if (this.nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            } else if (this.emailField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Email không được để trống.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            } else if (this.phoneField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            } else {
                if (!this.is_nonnull_field()) {
                    String email = this.emailField.getText().trim();
                    if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                        JOptionPane.showMessageDialog(this, "Email không đúng định dạng!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        // Kiểm tra nếu email đã tồn tại trong cơ sở dữ liệu
                        if (this.staffcontroller.isEmailExists(email)) {
                            JOptionPane.showMessageDialog(this, "Lỗi: Email " + email + " đã tồn tại.", "Lỗi cơ sở dữ liệu", JOptionPane.ERROR_MESSAGE);
                            return; // Dừng thực hiện nếu email đã tồn tại
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi kiểm tra email: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        // Kiểm tra định dạng số điện thoại
                        String phone = this.phoneField.getText().trim();
                        if (!phone.matches("^\\d{10}$")) {
                            JOptionPane.showMessageDialog(this, "Số điện thoại phải đúng 10 chữ số!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Staff new_staff = new Staff();
                        new_staff.setId(this.idField.getText());
                        new_staff.setName(this.nameField.getText());
                        new_staff.setEmail(this.emailField.getText());
                        new_staff.setPhoneNumber(phone);
                        new_staff.setRole(this.positionField.getText());
                        char[] cpassword = this.PasswordField.getPassword();
                        String password = new String(cpassword);
                        new_staff.setPassword(password);
                        Date hireDate = Date.valueOf(this.hireDateField.getText());
                        new_staff.setHireDate(hireDate);

                        this.add_Staff(new_staff);

                        JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        this.set_field(false);
                        this.isEditing = false;
                    } catch (SQLException | ClassNotFoundException ex) {
                        if (ex.getMessage().contains("Violation of PRIMARY KEY constraint")) {
                            JOptionPane.showMessageDialog(this, "Lỗi: ID " + this.idField.getText() + " đã tồn tại.", "Lỗi cơ sở dữ liệu", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi thêm nhân viên: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IllegalArgumentException ex) {
                        // Xử lý lỗi khi nhập dữ liệu không đúng định dạng cho hireDate
                        JOptionPane.showMessageDialog(this, "Ngày thuê không đúng định dạng. Vui lòng nhập theo định dạng yyyy-MM-dd.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }


    }//GEN-LAST:event_add_button1ActionPerformed

    private void delete_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_button1ActionPerformed
        /*BUTTON XÓA STAFF RA KHỎI DATABASE
        - Có thể xóa từng bản ghi hoặc nhiều bản ghi cùng lúc
        - Chọn trực tiếp trên bản và ấn vào button "DELETE"
         */
        int[] rows = this.Staff_Table.getSelectedRows();

        if (rows.length < 1) {
            JOptionPane.showMessageDialog(StaffManager.this, "Bạn chưa chọn nhân viên nào để xóa!");
        } else {
            int confirm = JOptionPane.showConfirmDialog(StaffManager.this, "Bạn chắc chắn muốn xóa nhân viên này?");

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    boolean delete = false;
                    String staff_id;

                    // Điều chỉnh giá trị index khi xóa lần lượt từng bản ghi
                    for (int i = 0; i < rows.length; i++) {
                        if (!delete) {
                            staff_id = String.valueOf(this.Staff_Table.getValueAt(rows[i] - i, 0));
                        } else {
                            staff_id = String.valueOf(this.Staff_Table.getValueAt(rows[i], 0));
                            delete = true;
                        }
                        this.delete_Staff(staff_id);
                    }

                    JOptionPane.showMessageDialog(this, "Nhân viên đã được xóa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                    String errorMessage = (e instanceof SQLException) ? "Lỗi kết nối cơ sở dữ liệu!" : "Không tìm thấy driver JDBC! Vui lòng kiểm tra lại thư viện.";
                    JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }//GEN-LAST:event_delete_button1ActionPerformed

    private void update_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_button1ActionPerformed
        // BUTTON UPDATE THÔNG TIN STAFF
        int row = this.Staff_Table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(StaffManager.this, "Bạn chưa chọn nhân viên nào!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            if (!isEditing) {
                this.set_field_value(row);
                this.set_field(true);
                this.idField.setEnabled(false);
                isEditing = true;
            } else {
                try {
                    // Lấy dữ liệu từ các trường nhập
                    String id = this.idField.getText().trim();
                    String name = this.nameField.getText().trim();
                    String email = this.emailField.getText().trim();
                    String phone = this.phoneField.getText().trim();
                    String role = this.positionField.getText().trim();
                    char[] cpassword = this.PasswordField.getPassword();
                    String password = new String(cpassword);
                    String hireDateString = this.hireDateField.getText().trim();

                    // Kiểm tra các trường trống
                    if (id.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty() || role.isEmpty() || password.isEmpty() || hireDateString.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Kiểm tra định dạng email
                    if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                        JOptionPane.showMessageDialog(this, "Email không đúng định dạng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Kiểm tra định dạng số điện thoại (10 chữ số)
                    if (!phone.matches("^\\d{10}$")) {
                        JOptionPane.showMessageDialog(this, "Số điện thoại phải đúng 10 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Kiểm tra định dạng ngày thuê (yyyy-MM-dd)
                    Date hireDate;
                    try {
                        hireDate = Date.valueOf(hireDateString);
                    } catch (IllegalArgumentException e) {
                        JOptionPane.showMessageDialog(this, "Ngày thuê không đúng định dạng! Vui lòng nhập theo định dạng yyyy-MM-dd.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Tạo đối tượng Staff
                    Staff new_staff = new Staff();
                    new_staff.setId(id);
                    new_staff.setName(name);
                    new_staff.setEmail(email);
                    new_staff.setPhoneNumber(phone);
                    new_staff.setRole(role);
                    new_staff.setPassword(password);
                    new_staff.setHireDate(hireDate);

                    // Cập nhật thông tin Staff
                    this.set_field(false);
                    isEditing = false;
                    this.update_Staff(new_staff.getId(), new_staff);

                    // Thông báo cập nhật thành công
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                    String errorMessage = (e instanceof SQLException)
                            ? "Lỗi kết nối cơ sở dữ liệu!"
                            : "Không tìm thấy driver JDBC! Vui lòng kiểm tra lại thư viện.";
                    JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_update_button1ActionPerformed

    private void search_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_button1ActionPerformed
        // BUTTON TÌM KIẾM THEO CÁC TRƯỜNG, KHÔNG PHÂN BIỆT HOA THƯỜNG
        if (!this.isEditing) {
            this.set_field(true);
            this.isEditing = true;
        } else {
            this.isEditing = false;
            this.set_field(false);
            String id = this.idField.getText();
            String name = this.nameField.getText();
            String email = this.emailField.getText();
            String phone = this.phoneField.getText();
            String position = this.positionField.getText();
            char[] cpassword = this.PasswordField.getPassword();
            String password = new String(cpassword);
            String hireDate = this.hireDateField.getText();

            List<Staff> staffs = null;
            try {
                staffs = this.staffcontroller.findStaffByPartialFields(id, name, email, phone, position, hireDate, password);

                this.setData(staffs);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                String errorMessage = (e instanceof SQLException) ? "Lỗi kết nối cơ sở dữ liệu!" : "Không tìm thấy driver JDBC! Vui lòng kiểm tra lại thư viện.";
                JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_search_button1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        title.setText("Chỉnh sửa hồ sơ");
        contentPanel.removeAll();
        contentPanel.add(InFoUserPanel);
        contentPanel.repaint();
        contentPanel.revalidate();
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // Chạy giao diện người dùng trong Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Khởi tạo và hiển thị cửa sổ HomePageUser
                StaffManager homeUser = new StaffManager("Admin");
                homeUser.setVisible(true);
                homeUser.pack();
                homeUser.setLocationRelativeTo(null); // Đặt cửa sổ ở giữa màn hình
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel InFoUserPanel;
    private javax.swing.JPasswordField PasswordField;
    private javax.swing.JTable Staff_Table;
    private javax.swing.JButton add_button;
    private javax.swing.JButton add_button1;
    private javax.swing.JButton bookbtn;
    private javax.swing.JButton borrowbtn;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JButton btnOld;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JLabel countBooks;
    private javax.swing.JLabel countBorrow;
    private javax.swing.JLabel countMembers;
    private javax.swing.JLabel countStaff;
    private javax.swing.JButton delete_button;
    private javax.swing.JButton delete_button1;
    private javax.swing.JTextField email;
    private javax.swing.JTextField emailField;
    private javax.swing.JTextField hireDateField;
    private javax.swing.JPanel homePanel;
    private javax.swing.JButton homebtn;
    private javax.swing.JTextField idField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logOutBtn;
    private javax.swing.JButton memberbtn;
    private javax.swing.JTextField name;
    private javax.swing.JTextField nameField;
    private javax.swing.JPasswordField pass;
    private javax.swing.JTextField phone;
    private javax.swing.JTextField phoneField;
    private javax.swing.JTextField positionField;
    private javax.swing.JButton refresh_button1;
    private javax.swing.JButton refresh_button2;
    private javax.swing.JButton search_button;
    private javax.swing.JButton search_button1;
    private javax.swing.JPanel staffPanel;
    private javax.swing.JButton staffbtn;
    private javax.swing.JLabel title;
    private javax.swing.JButton update_button;
    private javax.swing.JButton update_button1;
    private javax.swing.JLabel userNameLabel;
    // End of variables declaration//GEN-END:variables
}
