package views;
import javax.swing.*;

import controllers.BookController;
import models.Book;
import utils.SQLServerConnection;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;

public class MainFrame {
    private JFrame frame;
    private JTextField txtBookID, txtName, txtAuthor, txtGenre, txtPublishYear;
    private JCheckBox chkStatus;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnRead;
    private JTextArea txtArea;

    private Connection conn;

    public MainFrame() {
        // Khởi tạo kết nối đến cơ sở dữ liệu (thay thế với kết nối thực tế của bạn)
        try {
            conn = SQLServerConnection.initializeDatabase();
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(frame, "Không thể kết nối tới cơ sở dữ liệu!");
        }

        frame = new JFrame("Book Management");
        frame.setLayout(new BorderLayout());
        frame.setSize(700, 700);
        
        // Thêm các thành phần giao diện
        JPanel panel = new JPanel(); // Panel chứa các textfield và label
        panel.setLayout(new GridLayout(6, 2)); // 6 hàng, 2 cột

        JLabel lblBookID = new JLabel("Book ID:");
        txtBookID = new JTextField();
        panel.add(lblBookID);
        panel.add(txtBookID);

        JLabel lblName = new JLabel("Name:");
        txtName = new JTextField();
        panel.add(lblName);
        panel.add(txtName);

        JLabel lblAuthor = new JLabel("Author:");
        txtAuthor = new JTextField();
        panel.add(lblAuthor);
        panel.add(txtAuthor);

        JLabel lblGenre = new JLabel("Genre:");
        txtGenre = new JTextField();
        panel.add(lblGenre);
        panel.add(txtGenre);

        JLabel lblPublishYear = new JLabel("Publish Year:");
        txtPublishYear = new JTextField();
        panel.add(lblPublishYear);
        panel.add(txtPublishYear);

        JLabel lblStatus = new JLabel("Status:");
        chkStatus = new JCheckBox("Available");
        panel.add(lblStatus);
        panel.add(chkStatus);

        frame.add(panel, BorderLayout.NORTH);

        txtArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(txtArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        btnRead = new JButton("Read");

        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnUpdate);
        buttonsPanel.add(btnDelete);
        buttonsPanel.add(btnClear);
        buttonsPanel.add(btnRead);

        frame.add(buttonsPanel, BorderLayout.SOUTH);

        // Các hành động của nút
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBook();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        btnRead.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBooks();
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Thêm sách
    private void addBook() {
        String id = txtBookID.getText();
        String name = txtName.getText();
        String author = txtAuthor.getText();
        String genre = txtGenre.getText();
        int publishYear = Integer.parseInt(txtPublishYear.getText());
        boolean status = chkStatus.isSelected();

        Book newBook = new Book(id, name, author, genre, publishYear, status);
        try {
            BookController.insertBook(conn, newBook);
            txtArea.setText("Sách đã được thêm thành công!\n");
            displayBooks();
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi thêm sách!");
        }
    }

    // Cập nhật sách
    private void updateBook() {
        String id = txtBookID.getText();
        String name = txtName.getText();
        String author = txtAuthor.getText();
        String genre = txtGenre.getText();
        int publishYear = Integer.parseInt(txtPublishYear.getText());
        boolean status = chkStatus.isSelected();

        Book updatedBook = new Book(id, name, author, genre, publishYear, status);
        try {
            BookController.updateBook(conn, updatedBook, id);
            txtArea.setText("Sách đã được cập nhật thành công!\n");
            displayBooks();
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi cập nhật sách!");
        }
    }

    // Xóa sách
    private void deleteBook() {
        String id = txtBookID.getText();
        try {
            BookController.DeleteBook(conn, id);
            txtArea.setText("Sách đã được xóa thành công!\n");
            displayBooks();
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi xóa sách!");
        }
    }

    // Làm mới giao diện
    private void clearFields() {
        txtBookID.setText("");
        txtName.setText("");
        txtAuthor.setText("");
        txtGenre.setText("");
        txtPublishYear.setText("");
        chkStatus.setSelected(false);
    }

    // Hiển thị danh sách sách
    private void displayBooks() {
        try {
            List<Book> books = BookController.printBook(conn);
            StringBuilder sb = new StringBuilder();
            for (Book b : books) {
                sb.append("ID: ").append(b.getBookID())
                        .append(", Name: ").append(b.getName())
                        .append(", Author: ").append(b.getAuthor())
                        .append(", Genre: ").append(b.getGenre())
                        .append(", Year: ").append(b.getPublishYear())
                        .append(", Status: ").append(b.getStatus() ? "Available" : "Not Available")
                        .append("\n");
            }
            txtArea.setText(sb.toString());
        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(frame, "Lỗi khi hiển thị danh sách sách!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
