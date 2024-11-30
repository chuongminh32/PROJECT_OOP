package models;
import java.util.Date;

public class Borrow {
    private String borrowId;      // Mã mượn (duy nhất)
    private String memberId;      // Mã thành viên (người mượn)
    private String bookId;        // Mã sách (được mượn)
    private Date borrowDate;      // Ngày mượn
    private Date returnDate;      // Ngày trả (dự kiến)
    private Date actualReturnDate; // Ngày trả thực tế
    private String status;        // Trạng thái (Đang mượn, Đã trả, Quá hạn)

    // Constructor
    public Borrow() {
    }
    public Borrow(String borrowId, String memberId, String bookId, Date borrowDate, Date returnDate, 
                  Date actualReturnDate, String status) {
        this.borrowId = borrowId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.actualReturnDate = actualReturnDate;
        this.status = status;
    }

    // Getters and Setters
    public String getBorrowId() { return borrowId; }
    public void setBorrowId(String borrowId) { this.borrowId = borrowId; }

    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }

    public Date getBorrowDate() { return borrowDate; }
    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }

    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }

    public Date getActualReturnDate() { return actualReturnDate; }
    public void setActualReturnDate(Date actualReturnDate) { this.actualReturnDate = actualReturnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
