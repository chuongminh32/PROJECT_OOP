/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

//import tests.HomePageAdmin;
import java.util.List;
import controllers.HomePageLogic;
import controllers.StaffController;
import controllers.BorrowController;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import models.Borrow;
import models.Staff;
import utils.DBConnection;

/**
 *
 * @author chuon
 */
public class BorrowManager extends javax.swing.JFrame {

    private DefaultTableModel tableModel = new DefaultTableModel();
    private String userName;

    /**
     * Creates new form home_page_user
     *
     * @param <error>
     */
    public BorrowManager(String userName) {
        this.userName = userName;
        initComponents();
        setUsername();
        displayCount();
        disPlayTableBorrow();
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

    public int getNextId() throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM Borrow";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1) + 1; // Số dòng hiện tại + 1
            } else {
                return 1; // Nếu bảng rỗng, ID bắt đầu từ 1
            }
        }
    }

    public void AddBorrow(String memberId, String bookId, Date borrowDate, Date returnDate, Date dueDate, String status)
            throws SQLException, ClassNotFoundException {
        BorrowController borrowController = new BorrowController();

        // Tạo ID mới
        int id = getNextId();

        // Thêm dữ liệu
        boolean success = borrowController.addBorrow(memberId, bookId, borrowDate, returnDate, dueDate, status);

        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

            // Tải lại dữ liệu bảng
            try {
                showData();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, "Lỗi khi tải lại dữ liệu", ex);
                JOptionPane.showMessageDialog(this, "Không thể tải lại dữ liệu. Chi tiết lỗi: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại. Vui lòng kiểm tra lại!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteBorrow(int id) throws SQLException, ClassNotFoundException {
        BorrowController borrowController = new BorrowController();

        // Thực hiện xóa bản ghi
        boolean success = borrowController.deleteBorrow(id);

        if (success) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

            // Tải lại dữ liệu bảng sau khi xóa thành công
            try {
                showData();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, "Lỗi khi tải lại dữ liệu", ex);
                JOptionPane.showMessageDialog(this, "Không thể tải lại dữ liệu. Chi tiết lỗi: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại. Vui lòng kiểm tra lại!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateBorrow(int id, String memberId, String bookId, Date borrowDate, Date returnDate, Date dueDate, String status)
            throws SQLException, ClassNotFoundException {
        BorrowController borrowController = new BorrowController();

        // Thực hiện cập nhật bản ghi
        boolean success = borrowController.updateBorrow(id, memberId, bookId, borrowDate, returnDate, dueDate, status);

        if (success) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

            // Làm mới lại dữ liệu bảng
            try {
                showData();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, "Lỗi khi tải lại dữ liệu", ex);
                JOptionPane.showMessageDialog(this, "Không thể tải lại dữ liệu. Chi tiết lỗi: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại. Vui lòng kiểm tra lại!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void SearchBorrow() throws SQLException, ClassNotFoundException {
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

    public void disPlayTableBorrow() {
        // Cấu hình bảng
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] cols = {"ID", "ID THÀNH VIÊN", "ID SÁCH", "NGÀY MƯỢN", "NGÀY TRẢ DỰ KIẾN", "NGÀY TRẢ THỰC TẾ", "TRẠNG THÁI"};
        tableModel.setColumnIdentifiers(cols);
        Table.setModel(tableModel);

        // Thêm sự kiện chuột
        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });

        // Gọi hàm showData để tải dữ liệu
        try {
            showData();
        } catch (SQLException | ClassNotFoundException e) {
            // Hiển thị lỗi khi xảy ra ngoại lệ
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showData() throws SQLException, ClassNotFoundException {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        // Lấy kết nối cơ sở dữ liệu
        Connection conn = DBConnection.getConnection();
        List<Borrow> list = BorrowController.PrintList_MuonTra(conn);

        // Duyệt qua danh sách và thêm vào bảng
        for (Borrow br : list) {
            String[] rows = new String[7];
            rows[0] = Integer.toString(br.getId());
            rows[1] = br.getMemberId().getId();
            rows[2] = br.getBookId().getId();
            rows[3] = br.getBorrowDate().toString();
            rows[4] = br.getDueDate().toString();
            rows[5] = (br.getReturnDate() != null) ? br.getReturnDate().toString() : "";
            rows[6] = br.getStatus();

            tableModel.addRow(rows);
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
        borrowPanel = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        StatusLabel = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        MIdLabel = new javax.swing.JLabel();
        IDLabel = new javax.swing.JLabel();
        ReDateLabel = new javax.swing.JLabel();
        DueDateLabel = new javax.swing.JLabel();
        BoDateLabel = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        AddButton = new javax.swing.JButton();
        UpdateButton = new javax.swing.JButton();
        DelButton = new javax.swing.JButton();
        jTextField7 = new javax.swing.JTextField();
        RelButton = new javax.swing.JButton();
        SearchButton = new javax.swing.JButton();
        SearchField = new javax.swing.JTextField();
        SearchOpt = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
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

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/setting.png"))); // NOI18N
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setDefaultCapable(false);
        jButton3.setFocusPainted(false);
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
                    .addComponent(userNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 5, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton6)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(userNameLabel)
                            .addGap(1, 1, 1)
                            .addComponent(jLabel3)))))
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
                .addContainerGap(449, Short.MAX_VALUE))
        );

        contentPanel.add(homePanel, "card2");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("New username:");

        btnConfirm.setBackground(new java.awt.Color(204, 204, 204));
        btnConfirm.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnConfirm.setText("Xác nhận");
        btnConfirm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnConfirm.setBorderPainted(false);
        btnConfirm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfirm.setDefaultCapable(false);
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

        btnOld.setBackground(new java.awt.Color(204, 204, 204));
        btnOld.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnOld.setText("Thông tin cũ");
        btnOld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnOld.setBorderPainted(false);
        btnOld.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOld.setDefaultCapable(false);
        btnOld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOldActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/eye.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setDefaultCapable(false);
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
                .addGap(250, 250, 250)
                .addGroup(InFoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InFoUserPanelLayout.createSequentialGroup()
                        .addGroup(InFoUserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(InFoUserPanelLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(40, 40, 40))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InFoUserPanelLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)))
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
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
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
                .addContainerGap(505, Short.MAX_VALUE))
        );

        contentPanel.add(InFoUserPanel, "card6");

        jTextField1.setForeground(new java.awt.Color(153, 153, 153));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        StatusLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        StatusLabel.setText("TRẠNG THÁI");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("ID SÁCH");

        MIdLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        MIdLabel.setText("ID THÀNH VIÊN");

        IDLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        IDLabel.setText("ID");

        ReDateLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ReDateLabel.setText("NGÀY TRẢ THỰC TẾ");

        DueDateLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DueDateLabel.setText("NGÀY TRẢ DỰ KIẾN");

        BoDateLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BoDateLabel.setText("NGÀY MƯỢN");

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
        jScrollPane1.setViewportView(Table);

        AddButton.setBackground(new java.awt.Color(204, 204, 204));
        AddButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AddButton.setText("THÊM");
        AddButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        UpdateButton.setBackground(new java.awt.Color(204, 204, 204));
        UpdateButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        UpdateButton.setText("CẬP NHẬT");
        UpdateButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        UpdateButton.setFocusPainted(false);
        UpdateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UpdateButtonMouseClicked(evt);
            }
        });
        UpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateButtonActionPerformed(evt);
            }
        });

        DelButton.setBackground(new java.awt.Color(204, 204, 204));
        DelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DelButton.setText("XÓA");
        DelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        RelButton.setBackground(new java.awt.Color(204, 204, 204));
        RelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        RelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        RelButton.setBorderPainted(false);
        RelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RelButton.setDefaultCapable(false);
        RelButton.setFocusPainted(false);
        RelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RelButtonMouseClicked(evt);
            }
        });
        RelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RelButtonActionPerformed(evt);
            }
        });

        SearchButton.setBackground(new java.awt.Color(204, 204, 204));
        SearchButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        SearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/find.png"))); // NOI18N
        SearchButton.setBorderPainted(false);
        SearchButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SearchButton.setDefaultCapable(false);
        SearchButton.setFocusPainted(false);
        SearchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchButtonMouseClicked(evt);
            }
        });
        SearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchButtonActionPerformed(evt);
            }
        });

        SearchOpt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "MEMBER ID", "BOOK ID", "BORROW DATE", "DUE DATE", "RETURN DATE", "STATUS" }));

        jLabel16.setForeground(new java.awt.Color(153, 153, 153));
        jLabel16.setText("  Copyright © 2024, Trường Đại Học Sư Phạm Kỹ Thuật - Tp.HCM");

        javax.swing.GroupLayout borrowPanelLayout = new javax.swing.GroupLayout(borrowPanel);
        borrowPanel.setLayout(borrowPanelLayout);
        borrowPanelLayout.setHorizontalGroup(
            borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borrowPanelLayout.createSequentialGroup()
                .addGroup(borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(borrowPanelLayout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addGroup(borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addGroup(borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(DelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ReDateLabel))
                            .addComponent(DueDateLabel))
                        .addGap(15, 15, 15)
                        .addGroup(borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(borrowPanelLayout.createSequentialGroup()
                                .addComponent(UpdateButton)
                                .addGap(58, 58, 58)
                                .addComponent(AddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(borrowPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(borrowPanelLayout.createSequentialGroup()
                                .addGroup(borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(IDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(MIdLabel)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(StatusLabel))
                                .addGap(15, 15, 15)
                                .addGroup(borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField2)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                                    .addComponent(jTextField3)
                                    .addComponent(jTextField4))
                                .addGap(94, 94, 94)
                                .addComponent(BoDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(borrowPanelLayout.createSequentialGroup()
                                .addComponent(RelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(SearchOpt, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(SearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(SearchButton))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 881, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(344, 350, Short.MAX_VALUE))
        );
        borrowPanelLayout.setVerticalGroup(
            borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borrowPanelLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SearchField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SearchOpt, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(RelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IDLabel)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BoDateLabel))
                .addGap(14, 14, 14)
                .addGroup(borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(MIdLabel)
                        .addComponent(DueDateLabel)
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)))
                .addGap(10, 10, 10)
                .addGroup(borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ReDateLabel)
                    .addComponent(jLabel15)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(borrowPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(StatusLabel)
                        .addComponent(DelButton, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                    .addComponent(UpdateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AddButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(351, 351, 351))
        );

        contentPanel.add(borrowPanel, "card5");

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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
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
        contentPanel.add(InFoUserPanel);
        contentPanel.repaint();
        contentPanel.revalidate();
    }//GEN-LAST:event_jButton6ActionPerformed

    // ham reload page khi sua thong tin ca nhan
    public void reloadPage(String newUsername) {
        BorrowManager b = new BorrowManager(newUsername);
        this.userName = b.userName;
        userNameLabel.setText(b.userName);
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
        title.setText("Mượn trả");
        contentPanel.removeAll();
        contentPanel.add(borrowPanel);
        contentPanel.repaint();
        contentPanel.revalidate();
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

    private void DelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DelButtonActionPerformed
        int id = Integer.parseInt(jTextField1.getText().trim());

        if (id <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập id (>0) cần xóa.",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String memberId = jTextField2.getText().trim();
        String bookId = jTextField3.getText().trim();
        String status = jTextField4.getText().trim();

        if (memberId.isEmpty() || bookId.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Một số trường không được bỏ trống. Vui lòng kiểm tra lại!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa thông tin mượn trả với ID: " + id + "?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                deleteBorrow(id);
                JOptionPane.showMessageDialog(this, "Thông tin mượn trả đã được xóa thành công!",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi cơ sở dữ liệu: " + ex.getMessage(),
                        "Lỗi Cơ Sở Dữ Liệu", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy lớp cơ sở dữ liệu: " + ex.getMessage(),
                        "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_DelButtonActionPerformed

    private void DelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DelButtonMouseClicked
        // TODO add your handling code here:
        int id = Integer.parseInt(jTextField1.getText().trim());

        if (id <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập cần xóa.",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa thông tin mượn trả với ID: " + id + "?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                deleteBorrow(id);
            } catch (SQLException ex) {
                Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_DelButtonMouseClicked

    private void UpdateButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UpdateButtonMouseClicked
        // TODO add your handling code here:
        int id = Integer.parseInt(jTextField1.getText().trim()); // idTextField là JTextField chứa ID
        String memberId = jTextField2.getText().trim();
        String bookId = jTextField3.getText().trim();
        String status = jTextField4.getText().trim();
        Date borrowDate;
        try {
            borrowDate = Date.valueOf(jTextField5.getText().trim());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Vui lòng nhập đúng định dạng yyyy-mm-dd.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Date dueDate;
        try {
            dueDate = Date.valueOf(jTextField6.getText().trim());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Vui lòng nhập đúng định dạng yyyy-mm-dd.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Date returnDate;
        try {
            returnDate = Date.valueOf(jTextField7.getText().trim());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Vui lòng nhập đúng định dạng yyyy-mm-dd.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            updateBorrow(id, memberId, bookId, borrowDate, returnDate, dueDate, status);
        } catch (SQLException ex) {
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_UpdateButtonMouseClicked

    private void AddButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddButtonMouseClicked
        // TODO add your handling code here:
        //        int id = Integer.parseInt(jTextField1.getText().trim()); // idTextField là JTextField chứa ID
        String memberId = jTextField2.getText().trim();
        String bookId = jTextField3.getText().trim();
        String status = jTextField4.getText().trim();
        Date borrowDate;
        try {
            borrowDate = Date.valueOf(jTextField5.getText().trim());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Vui lòng nhập đúng định dạng yyyy-mm-dd.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Date dueDate;
        try {
            dueDate = Date.valueOf(jTextField6.getText().trim());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Vui lòng nhập đúng định dạng yyyy-mm-dd.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Date returnDate;
        try {
            returnDate = Date.valueOf(jTextField7.getText().trim());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Vui lòng nhập đúng định dạng yyyy-mm-dd.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            AddBorrow(memberId, bookId, borrowDate, returnDate, dueDate, status);
        } catch (SQLException ex) {
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_AddButtonMouseClicked

    private void TableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableMouseClicked
        // TODO add your handling code here:
        int selectedRow = Table.getSelectedRow();

        if (selectedRow != -1) {
            jTextField1.setText(Table.getValueAt(selectedRow, 0).toString());
            jTextField2.setText(Table.getValueAt(selectedRow, 1).toString());
            jTextField3.setText(Table.getValueAt(selectedRow, 2).toString());
            jTextField4.setText(Table.getValueAt(selectedRow, 6).toString());
            jTextField5.setText(Table.getValueAt(selectedRow, 3).toString());
            jTextField6.setText(Table.getValueAt(selectedRow, 4).toString());
            jTextField7.setText(Table.getValueAt(selectedRow, 5).toString());

        }
    }//GEN-LAST:event_TableMouseClicked

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
        jTextField1.setEditable(false);
    }//GEN-LAST:event_jTextField1KeyPressed

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
        // TODO add your handling code here:
        String memberId = jTextField2.getText().trim();
        String bookId = jTextField3.getText().trim();
        String status = jTextField4.getText().trim();
        String borrowDateString = jTextField5.getText().trim();
        String dueDateString = jTextField6.getText().trim();
        String returnDateString = jTextField7.getText().trim();

        // Kiểm tra các trường bắt buộc không được bỏ trống
        if (memberId.isEmpty() || bookId.isEmpty() || status.isEmpty() || borrowDateString.isEmpty() || dueDateString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin vào các trường bắt buộc.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date borrowDate;
        try {
            borrowDate = Date.valueOf(borrowDateString);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Vui lòng nhập đúng định dạng yyyy-mm-dd.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date dueDate;
        try {
            dueDate = Date.valueOf(dueDateString);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Vui lòng nhập đúng định dạng yyyy-mm-dd.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date returnDate = null;
        if (!returnDateString.isEmpty()) {
            try {
                returnDate = Date.valueOf(returnDateString);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Vui lòng nhập đúng định dạng yyyy-mm-dd.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            AddBorrow(memberId, bookId, borrowDate, returnDate, dueDate, status);
        } catch (SQLException ex) {
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi trong quá trình thêm dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Không tìm thấy lớp xử lý dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_AddButtonActionPerformed

    private void RelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RelButtonMouseClicked
        try {
            // TODO add your handling code here:
            showData();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi Cơ Sở Dữ Liệu", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy lớp cơ sở dữ liệu: " + ex.getMessage(), "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_RelButtonMouseClicked

    private void SearchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchButtonMouseClicked

    }//GEN-LAST:event_SearchButtonMouseClicked

    private void RelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RelButtonActionPerformed
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        SearchField.setText("");
        disPlayTableBorrow();
    }//GEN-LAST:event_RelButtonActionPerformed

    private void SearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchButtonActionPerformed
        try {
            // TODO add your handling code here:
            SearchBorrow();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi Cơ Sở Dữ Liệu", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy lớp cơ sở dữ liệu: " + ex.getMessage(), "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_SearchButtonActionPerformed

    private void UpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateButtonActionPerformed
        // TODO add your handling code here:
        int id = Integer.parseInt(jTextField1.getText().trim()); // idTextField là JTextField chứa ID
        String memberId = jTextField2.getText().trim();
        String bookId = jTextField3.getText().trim();
        String status = jTextField4.getText().trim();

        if (id == 0 || memberId.isEmpty() || bookId.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Một số trường bị bỏ trống. Vui lòng kiểm tra lại!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date borrowDate;
        try {
            borrowDate = Date.valueOf(jTextField5.getText().trim());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Vui lòng nhập đúng định dạng yyyy-mm-dd.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Date dueDate;
        try {
            dueDate = Date.valueOf(jTextField6.getText().trim());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Vui lòng nhập đúng định dạng yyyy-mm-dd.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Date returnDate;
        try {
            returnDate = Date.valueOf(jTextField7.getText().trim());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Vui lòng nhập đúng định dạng yyyy-mm-dd.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            updateBorrow(id, memberId, bookId, borrowDate, returnDate, dueDate, status);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi cơ sở dữ liệu: " + ex.getMessage(), "Lỗi Cơ Sở Dữ Liệu", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy lớp cơ sở dữ liệu: " + ex.getMessage(), "Lỗi Kết Nối", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(BorrowManager.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_UpdateButtonActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

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
                BorrowManager b = new BorrowManager("Admin");
                b.setVisible(true);
                b.pack();
                b.setLocationRelativeTo(null); // Đặt cửa sổ ở giữa màn hình
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButton;
    private javax.swing.JLabel BoDateLabel;
    private javax.swing.JButton DelButton;
    private javax.swing.JLabel DueDateLabel;
    private javax.swing.JLabel IDLabel;
    private javax.swing.JPanel InFoUserPanel;
    private javax.swing.JLabel MIdLabel;
    private javax.swing.JLabel ReDateLabel;
    private javax.swing.JButton RelButton;
    private javax.swing.JButton SearchButton;
    private javax.swing.JTextField SearchField;
    private javax.swing.JComboBox<String> SearchOpt;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JTable Table;
    private javax.swing.JButton UpdateButton;
    private javax.swing.JButton bookbtn;
    private javax.swing.JPanel borrowPanel;
    private javax.swing.JButton borrowbtn;
    private javax.swing.JButton btnConfirm;
    private javax.swing.JButton btnOld;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JLabel countBooks;
    private javax.swing.JLabel countBorrow;
    private javax.swing.JLabel countMembers;
    private javax.swing.JLabel countStaff;
    private javax.swing.JTextField email;
    private javax.swing.JPanel homePanel;
    private javax.swing.JButton homebtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JButton logOutBtn;
    private javax.swing.JButton memberbtn;
    private javax.swing.JTextField name;
    private javax.swing.JPasswordField pass;
    private javax.swing.JTextField phone;
    private javax.swing.JButton staffbtn;
    private javax.swing.JLabel title;
    private javax.swing.JLabel userNameLabel;
    // End of variables declaration//GEN-END:variables
}
