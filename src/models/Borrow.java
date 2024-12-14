package models;

import java.util.Date;

public class Borrow {

    private int id; // Mã mượn (duy nhất)
    private Member member; // Thành viên (người mượn)
    private Book book; // Sách (được mượn)
    private Date borrowDate; // Ngày mượn
    private Date returnDate; // Ngày trả thực tế
    private Date dueDate; // Ngày trả (dự kiến)
    private String status; // Trạng thái (Mượn, Đã trả, Quá hạn)

    // Constructor
    public Borrow() {
    }

    public Borrow(int id, Member member, Book book, Date borrowDate, Date returnDate, Date dueDate, String status) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Print member information
    public String toString() {
        return "ID: " + id
                + ", Ten thanh vien: " + getMember().getName()
                + ", Ten sach: " + getBook().getTitle()
                + ", Ngay muon: " + borrowDate
                + ", Ngay tra thuc te: " + returnDate
                + ", Ngay tra du kien: " + dueDate
                + ", Trang thai: " + status;
    }

    // // Getters and Setters
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the borrowDate
     */
    public Date getBorrowDate() {
        return borrowDate;
    }

    /**
     * @param borrowDate the borrowDate to set
     */
    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    /**
     * @return the returnDate
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * @param returnDate the returnDate to set
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the member
     */
    public Member getMember() {
        return member;
    }

    /**
     * @param member the member to set
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * @return the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * @param book the book to set
     */
    public void setBook(Book book) {
        this.book = book;
    }
}
