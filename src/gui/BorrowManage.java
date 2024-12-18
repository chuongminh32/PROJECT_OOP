package gui;

import java.sql.*;
import java.sql.Date;
import javax.swing.*;
import java.util.List;
import utils.DBConnection;

import controllers.BorrowController;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import models.Member;
import models.Book;
import models.Borrow;

/**
 *
 * @author admin
 */
public class BorrowManage extends javax.swing.JFrame {
    private DefaultTableModel tableModel = new DefaultTableModel();

    public BorrowManage() throws SQLException, ClassNotFoundException{
        initComponents();
        setLocationRelativeTo(null);
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] cols = { "ID", "MEMBER ID", "BOOK ID", "BORROW DATE", "DUE DATE", "RETURN DATE", "STATUS" };
        tableModel.setColumnIdentifiers(cols);
        Table.setModel(tableModel);

        Table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
        });
        
        showData();
    }
    
    public void showData() throws SQLException, ClassNotFoundException {
        String[] cols = { "ID", "MEMBER ID", "BOOK ID", "BORROW DATE", "DUE DATE", "RETURN DATE", "STATUS" };
        tableModel.setColumnIdentifiers(cols);
        tableModel.setRowCount(0);
        Connection conn = DBConnection.getConnection();
        List<Borrow> list = BorrowController.PrintList_MuonTra(conn);
        for (int i = 0; i < list.size(); i++) {
            Borrow br = list.get(i);
            String rows[] = new String[7];
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
    public void AddBorrow(String memberId, String bookId, Date borrowDate, Date returnDate, Date dueDate, String status)
            throws SQLException, ClassNotFoundException {
        BorrowController borrowController = new BorrowController();
        boolean success = borrowController.addBorrow(memberId, bookId, borrowDate, returnDate, dueDate, status);
        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            try {
                showData();
            } catch (SQLException ex) {
                Logger.getLogger(BorrowManage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BorrowManage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại. Vui lòng kiểm tra lại!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public void deleteBorrow(int id) throws SQLException, ClassNotFoundException {
        BorrowController borrowController = new BorrowController();
        boolean success = borrowController.deleteBorrow(id);
        if (success) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            try {
                showData();
            } catch (SQLException ex) {
                Logger.getLogger(BorrowManage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BorrowManage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại. Vui lòng kiểm tra lại!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
     public void updateBorrow(int id, String memberId, String bookId, Date borrowDate, Date returnDate, Date dueDate, String status)
            throws SQLException, ClassNotFoundException {
        BorrowController borrowController = new BorrowController();
        boolean success = borrowController.updateBorrow(id, memberId, bookId, borrowDate, returnDate, dueDate, status);
        if (success) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại. Mail đã tồn tại!", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
        showData(); // Làm mới lại bảng
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



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        LabelTT = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        IDLabel = new javax.swing.JLabel();
        MIdLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        StatusLabel = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        BoDateLabel = new javax.swing.JLabel();
        DueDateLabel = new javax.swing.JLabel();
        ReDateLabel = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        AddButton = new javax.swing.JButton();
        UpdateButton = new javax.swing.JButton();
        DelButton = new javax.swing.JButton();
        ReturnButton = new javax.swing.JButton();
        SearchButton = new javax.swing.JButton();
        RelButton = new javax.swing.JButton();
        SearchField = new javax.swing.JTextField();
        SearchOpt = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(58, 93, 156));

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));

        LabelTT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LabelTT.setForeground(new java.awt.Color(255, 255, 255));
        LabelTT.setText("THÔNG TIN MƯỢN TRẢ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(LabelTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(56, 56, 56))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LabelTT, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setForeground(new java.awt.Color(153, 204, 255));

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

        IDLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        IDLabel.setText("ID");

        MIdLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        MIdLabel.setText("MEMBER ID");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("BOOK ID");

        StatusLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        StatusLabel.setText("STATUS");

        jTextField1.setForeground(new java.awt.Color(153, 153, 153));
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        BoDateLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BoDateLabel.setText("BORROW DATE");

        DueDateLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DueDateLabel.setText("DUE DATE");

        ReDateLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ReDateLabel.setText("RETURN DATE");

        AddButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        AddButton.setText("ADD");
        AddButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddButtonMouseClicked(evt);
            }
        });

        UpdateButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        UpdateButton.setText("UPDATE");
        UpdateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UpdateButtonMouseClicked(evt);
            }
        });

        DelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        DelButton.setText("DELETE");
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

        ReturnButton.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        ReturnButton.setText("RETURN");
        ReturnButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReturnButtonMouseClicked(evt);
            }
        });

        SearchButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        SearchButton.setText("SEARCH");
        SearchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchButtonMouseClicked(evt);
            }
        });

        RelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        RelButton.setText("RELOAD");
        RelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RelButtonMouseClicked(evt);
            }
        });

        SearchOpt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "MEMBER ID", "BOOK ID", "BORROW DATE", "DUE DATE", "RETURN DATE", "STATUS" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(DelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(StatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2)
                            .addComponent(jTextField3)
                            .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                        .addGap(84, 84, 84)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(BoDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(36, 36, 36)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(ReDateLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(DueDateLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(45, 45, 45)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField6)
                                        .addComponent(jTextField7))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(SearchButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SearchField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SearchOpt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(RelButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ReturnButton)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(AddButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(UpdateButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(24, 24, 24))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IDLabel)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BoDateLabel)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(AddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(MIdLabel)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(DueDateLabel)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(ReDateLabel)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(ReturnButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(StatusLabel)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(SearchButton)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(SearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(SearchOpt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DelButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DelButtonActionPerformed

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
            Logger.getLogger(BorrowManage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BorrowManage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_AddButtonMouseClicked

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
            Logger.getLogger(BorrowManage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BorrowManage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_UpdateButtonMouseClicked

    private void DelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DelButtonMouseClicked
        // TODO add your handling code here:
        int id = Integer.parseInt(jTextField1.getText().trim());

        if (id <=0) {
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
                Logger.getLogger(BorrowManage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BorrowManage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_DelButtonMouseClicked

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

    private void ReturnButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReturnButtonMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_ReturnButtonMouseClicked

    private void RelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RelButtonMouseClicked
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            showData();
        } catch (SQLException ex) {
            Logger.getLogger(BorrowManage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BorrowManage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_RelButtonMouseClicked

    private void SearchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchButtonMouseClicked
        try {
            // TODO add your handling code here:
            SearchBorrow();
        } catch (SQLException ex) {
            Logger.getLogger(BorrowManage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BorrowManage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SearchButtonMouseClicked

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // TODO add your handling code here:
        jTextField1.setEditable(false);
    }//GEN-LAST:event_jTextField1KeyPressed

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
            java.util.logging.Logger.getLogger(BorrowManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BorrowManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BorrowManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BorrowManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new BorrowManage().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(BorrowManage.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(BorrowManage.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButton;
    private javax.swing.JLabel BoDateLabel;
    private javax.swing.JButton DelButton;
    private javax.swing.JLabel DueDateLabel;
    private javax.swing.JLabel IDLabel;
    private javax.swing.JLabel LabelTT;
    private javax.swing.JLabel MIdLabel;
    private javax.swing.JLabel ReDateLabel;
    private javax.swing.JButton RelButton;
    private javax.swing.JButton ReturnButton;
    private javax.swing.JButton SearchButton;
    private javax.swing.JTextField SearchField;
    private javax.swing.JComboBox<String> SearchOpt;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JTable Table;
    private javax.swing.JButton UpdateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    // End of variables declaration//GEN-END:variables
}
