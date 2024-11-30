```markdown
# Library Management App

## Project Structure

```
library_management_app/
├── src/
│   ├── main/
│   │   └── Main.java             -> Điểm bắt đầu chính của ứng dụng
│   ├── models/
│   │   ├── Book.java             -> Lớp mô hình cho sách
│   │   ├── Member.java           -> Lớp mô hình cho thành viên
│   │   ├── Borrow.java           -> Lớp mô hình cho trạng thái mượn trả
│   │   └── Staff.java            -> Lớp mô hình cho nhân viên
│   ├── views/
│   │   ├── MainFrame.java        -> Giao diện chính của ứng dụng
│   │   ├── BookPanel.java        -> Giao diện quản lý sách
│   │   ├── MemberPanel.java      -> Giao diện quản lý thành viên
│   │   ├── BorrowPanel.java      -> Giao diện quản lý mượn trả
│   │   └── StaffPanel.java       -> Giao diện quản lý nhân viên
│   ├── controllers/
│   │   ├── BookController.java   -> Logic điều khiển quản lý sách
│   │   ├── MemberController.java -> Logic điều khiển quản lý thành viên
│   │   ├── BorrowController.java -> Logic điều khiển quản lý mượn trả
│   │   └── StaffController.java  -> Logic điều khiển quản lý nhân viên
│   ├── utils/
│   │   ├── DBConnection.java         -> Kết nối với SQL Server.
│   │   └── SQLServerConnecion.java   -> Lớp quản lý kết nối đến cơ sở dữ liệu SQL Server
│   ├── tests/
│   │   ├── BookControllerTest.java   -> Testcase cho BookController
│   │   ├── MemberControllerTest.java -> Testcase cho MemberController
│   │   ├── BorrowControllerTest.java -> Testcase cho BorrowController
│   │   └── StaffControllerTest.java  -> Testcase cho StaffController
└── resources/                    -> Thư mục chứa các tài nguyên khác (icon, hình ảnh, tệp cấu hình)
    └── icons/                    -> Lưu trữ icon và hình ảnh cho giao diện

```

## Task Assignment

## Phân Công Công Việc

### Thành Viên 1: Xây Dựng Logic Điều Khiển và Kết Nối Cơ Sở Dữ Liệu
- **controllers/**: Phát triển các lớp điều khiển để xử lý logic quản lý sách, thành viên, mượn trả và nhân viên.
    - `BookController.java`
    - `MemberController.java`
    - `BorrowController.java`
    - `StaffController.java`
- **utils/**: Xây dựng và thiết lập kết nối cơ sở dữ liệu.
    - `DatabaseConnection.java`

### Thành Viên 2: Phát Triển Giao Diện Người Dùng
- **views/**: Xây dựng các lớp giao diện chính cho ứng dụng.
    - `MainFrame.java`
    - `BookPanel.java`
    - `MemberPanel.java`
    - `BorrowPanel.java`
    - `StaffPanel.java`

### Thành Viên 3: Xây Dựng Mô Hình Dữ Liệu và Điểm Bắt Đầu Ứng Dụng
- **models/**: Thiết kế và xây dựng các lớp mô hình dữ liệu cho sách, thành viên, mượn trả và nhân viên.
    - `Book.java`
    - `Member.java`
    - `Borrow.java`
    - `Staff.java`
- **main/**: Phát triển điểm bắt đầu chính của ứng dụng và tích hợp các thành phần.
    - `Main.java`

## Cài Đặt và Chạy Ứng Dụng

1. Clone repo:
   ```bash
   git clone https://github.com/yourusername/library-management-app.git

```