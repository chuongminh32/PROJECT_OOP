package models;

import java.util.Date;

public class Borrow {

    private int id;      // Mã mượn (duy nhất)
    private Member memberId;      // Mã thành viên (người mượn)
    private Book bookId;        // Mã sách (được mượn)
    private Date borrowDate;      // Ngày mượn
    private Date returnDate;      // Ngày trả thực tế
    private Date dueDate;           // Ngày trả (dự kiến)
    private String status;        // Trạng thái (Đang mượn, Đã trả, Quá hạn)

    // Constructor
    public Borrow() {
    }

    public Borrow(int id, Member memberId, Book bookId, Date borrowDate, Date returnDate,
            Date dueDate, String status) {
        this.id = id;
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Print member information
    public String toString() {
        return "ID: " + id
                + ", Ten thanh vien: " + memberId.getName()
                + ", Ten sach: " + bookId.getTitle()
                + ", Ngay muon: " + borrowDate
                + ", Ngay tra thuc te: " + returnDate
                + ", Ngay tra du kien: " + dueDate
                + ", Trang thai: " + status;
    }

//    // Getters and Setters
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
     * @return the memberId
     */
    public Member getMemberId() {
        return memberId;
    }

    /**
     * @param memberId the memberId to set
     */
    public void setMemberId(Member memberId) {
        this.memberId = memberId;
    }

    /**
     * @return the bookId
     */
    public Book getBookId() {
        return bookId;
    }

    /**
     * @param bookId the bookId to set
     */
    public void setBookId(Book bookId) {
        this.bookId = bookId;
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
}
