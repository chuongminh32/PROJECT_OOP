package gui;

import controllers.BookController;
import models.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManageBooksPage extends JPanel {
    private JTable bookTable;
    private DefaultTableModel tableModel; // Table model for book data
    private BookController bookController;

    private JTextField txtId, txtTitle, txtAuthor, txtPublisher, txtYear, txtCategory, txtTotal, txtAvailable;
    private JButton btnAdd, btnUpdate, btnDelete, btnRefresh;

    public ManageBooksPage() {
        bookController = new BookController();

        setLayout(new BorderLayout());

        // Top Panel (Form Input)
        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Book Information"));

        inputPanel.add(new JLabel("ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Title:"));
        txtTitle = new JTextField();
        inputPanel.add(txtTitle);

        inputPanel.add(new JLabel("Author:"));
        txtAuthor = new JTextField();
        inputPanel.add(txtAuthor);

        inputPanel.add(new JLabel("Publisher:"));
        txtPublisher = new JTextField();
        inputPanel.add(txtPublisher);

        inputPanel.add(new JLabel("Year:"));
        txtYear = new JTextField();
        inputPanel.add(txtYear);

        inputPanel.add(new JLabel("Category:"));
        txtCategory = new JTextField();
        inputPanel.add(txtCategory);

        inputPanel.add(new JLabel("Total Copies:"));
        txtTotal = new JTextField();
        inputPanel.add(txtTotal);

        inputPanel.add(new JLabel("Available Copies:"));
        txtAvailable = new JTextField();
        inputPanel.add(txtAvailable);

        add(inputPanel, BorderLayout.NORTH);

        // Center Panel (Table)
        tableModel = new DefaultTableModel(new Object[]{"ID", "Title", "Author", "Publisher", "Year", "Category", "Total", "Available"}, 0);
        bookTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(bookTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Bottom Panel (Buttons)
        JPanel buttonPanel = new JPanel();
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button Action Listeners
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

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });

        // Load Initial Data
        refreshTable();
    }

    private void addBook() {
        try {
            String id = txtId.getText();
            String title = txtTitle.getText();
            String author = txtAuthor.getText();
            String publisher = txtPublisher.getText();
            int year = Integer.parseInt(txtYear.getText());
            String category = txtCategory.getText();
            int total = Integer.parseInt(txtTotal.getText());
            int available = Integer.parseInt(txtAvailable.getText());

            // Book book = new Book(id, title, author, publisher, year, category, total, available);
            bookController.addBook(id, title, author, publisher, year, category, total, available);

            JOptionPane.showMessageDialog(this, "Book added successfully!");
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage());
        }
    }

    private void updateBook() {
        try {
            String id = txtId.getText();
            String title = txtTitle.getText();
            String author = txtAuthor.getText();
            String publisher = txtPublisher.getText();
            int year = Integer.parseInt(txtYear.getText());
            String category = txtCategory.getText();
            int total = Integer.parseInt(txtTotal.getText());
            int available = Integer.parseInt(txtAvailable.getText());
            
            // Book book = new Book(id, title, author, publisher, year, category, total, available);
            bookController.updateBook(id, title, author, publisher, year, category, total, available);

            JOptionPane.showMessageDialog(this, "Book updated successfully!");
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage());
        }
    }

    private void deleteBook() {
        try {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a book to delete!");
                return;
            }

            String id = (String) tableModel.getValueAt(selectedRow, 0);
            bookController.deleteBook(id);

            JOptionPane.showMessageDialog(this, "Book deleted successfully!");
            refreshTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void refreshTable() {
        try {
            tableModel.setRowCount(0);
            List<Book> books = bookController.getAllBooks();
            for (Book book : books) {
                tableModel.addRow(new Object[]{
                        book.getId(), book.getTitle(), book.getAuthor(), book.getPublisher(),
                        book.getPublishedYear(), book.getCategory(), book.getTotalCopies(), book.getAvailableCopies()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
