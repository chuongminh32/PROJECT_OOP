

```markdown

# ĐỒ ÁN OOP

### Thành viên 1
- **Họ và tên**: Phạm Hàn Minh Chương.
- **MSSV**: 23110187.
- **Vai trò**: code GUI - [LoginPage,RegisterPage,HomePage,AdminHomePage,UserHomePage]; xây dựng logic auth[AuthController,LogicController], tích hợp.

### Thành viên 2
- **Họ và tên**: Nguyễn Thanh Bình Minh.
- **MSSV**: 23110266.
- **Vai trò**: code GUI - [StaffManage,BookManage]; thiết kế lớp [Staff,Book]; xây dựng logic lớp [StaffController,BookController], kiểm thử.

### Thành viên 3
- **Họ và tên**: Nguyễn Thị Thanh Thùy.
- **MSSV**: 23110336.
- **Vai trò**: code GUI - [BorrowManage,MemeberManage]; thiết kế lớp [Borrow,Member]; xây dựng logic lớp [BorrowController,MemberController], kiểm thử.


```
library_management_app/
├── src/                               -> Thư mục chứa mã nguồn của ứng dụng
│   ├── main/                          -> Điểm bắt đầu chính của ứng dụng
│   │   └── Main.java                  -> Điểm bắt đầu chính của ứng dụng
│   ├── models/                        -> Các mô hình dữ liệu của ứng dụng
│   │   ├── Book.java                  -> Mô hình cho sách
│   │   ├── Member.java                -> Mô hình cho thành viên
│   │   ├── Borrow.java                -> Mô hình cho trạng thái mượn trả
│   │   └── Staff.java                 -> Mô hình cho nhân viên
│   ├── gui/                           -> Thành phần giao diện người dùng (UI)
│   │   ├── LoginPage.java             -> Giao diện đăng nhập
│   │   ├── RegisterPage.java          -> Giao diện đăng ký
│   │   ├── HomePage.java              -> Giao diện trang chủ sau khi đăng nhập
│   │   ├── AdminHomePage.java         -> Giao diện cho Admin (Toàn quyền)
│   │   ├── UserHomePage.java          -> Giao diện cho người dùng (Chỉ xem)
│   │   ├── BookManage.java            -> Giao diện quản lý sách
│   │   ├── MainFrame.java             -> run gui
│   │   ├── MemberManage.java          -> Giao diện quản lý thành viên
│   │   ├── BorrowManage.java          -> Giao diện quản lý mượn trả
│   │   └── StaffManage.java           -> Giao diện quản lý nhân viên
│   ├── controllers/                   -> Các bộ điều khiển logic của ứng dụng
│   │   ├── LogicController.java       -> Bộ điều khiển logic phân quyền người dùng
│   │   ├── BookController.java        -> Bộ điều khiển logic quản lý sách
<<<<<<< HEAD
=======
│   │   ├── HomePageLogic.java        -> Bộ điều khiển logic quản lí trang chủ
>>>>>>> 8883d96c935412c3fb121fabe11cd22574a6d2a2
│   │   ├── MemberController.java      -> Bộ điều khiển logic quản lý thành viên
│   │   ├── BorrowController.java      -> Bộ điều khiển logic quản lý mượn trả
│   │   └── StaffController.java       -> Bộ điều khiển logic quản lý nhân viên
│   │   ├── AuthController.java        -> Bộ điều khiển xử lý đăng nhập và phân quyền người dùng
│   ├── utils/                         -> Các lớp tiện ích
│   │   ├── DBConnection.java         -> Tiện ích kết nối với cơ sở dữ liệu (SQL Server)
│   │   └── SQLServerConnection.java  -> Quản lý kết nối SQL Server
│   ├── tests/                         -> Thư mục chứa các bài kiểm tra cho controllers và logic
│   │   ├── BookControllerTest.java    -> Kiểm tra đơn vị cho BookController
│   │   ├── MemberControllerTest.java  -> Kiểm tra đơn vị cho MemberController
│   │   ├── BorrowControllerTest.java  -> Kiểm tra đơn vị cho BorrowController
│   │   └── StaffControllerTest.java   -> Kiểm tra đơn vị cho StaffController
│   │   └── AuthControllerTest.java    -> Kiểm tra đơn vị cho AuthController (đăng nhập và phân quyền)
└── resources/                         -> Thư mục chứa các tài nguyên của ứng dụng (icon, hình ảnh, tệp cấu hình)
    └── icons/                         -> Lưu trữ các icon và hình ảnh cho giao diện người dùng



# Define the logic diagram 
* Logic tổng quan
            +--------------------------+
            |        Người dùng        |
            | (Nhập thông tin đăng nhập)|
            +-----------+--------------+
                        |
                        v
            +--------------------------+
            |      LoginPage.java      |<-----------------------------------------------------<---------------------------|
            | (Giao diện đăng nhập)    |                                                                                  |   
            +-----------+--------------+                                                                                  |      
                        |                                                                                                 |  
                        v                                                                                                 |          
            +--------------------------+                                                                                  |  
            |    LoginController.java  |                                                                                  |  
            | (Xử lý thông tin đầu vào)|                                                                                  |  
            +-----------+--------------+                                                                                  |      
                        |                                                                                                 |  
                        v                                                                                                 |  
            +--------------------------+                                                                                  |  
            |   DBConnection.java      |                                                                                  |  
            | (Kết nối cơ sở dữ liệu)  |                                                                                  |  
            +-----------+--------------+                                                                                  |  
                        |                                                                                                 |  
                        v                                                                                                 |  
            +--------------------------+                                                                                  |               
            |       SQL Server         |    success                                                                       |   
            | (Xác thực tài khoản      |---------------->                                                                 |  
            |  và vai trò người dùng)  |                |                                                                 |      
            +-----------+--------------+                |                                                                 |       
                        |                               |                                                                 |  
                        |                               |                                                                 |
                        |                               |                                                                 |      
                        v                               |                                                                 |  
            +------------------------+                  |                                                                 |          
            |   Truy vấn thất bại    |                  |                                                                 |  
            |       hoặc lỗi         |                  v                                                                 |  
            +------------------------+                  |                                                                 |  
                        |                               |                                                                 |          
                        |                               |                                                                 |  
                        |                               |                                                                 |  
                        |                               |                                                                 |  
                        v                               |                                                                 |  
            +--------------------------+                |                                                                 |  
            |   Chuyển sang            |                |                                                                 |  
            | RegisterPage.java        |                |                                                                 |  
            +-----------+--------------+                |                                                                 |              
                        |                               |                                                                 |  
                        |                               |                                                                 |  
                        |                               |                                                                 |   
                        v                               |                                                                 |  
                        |                               |                                                                 |  
                        |                               |                                                                 |  
            +-----------+                               v                                                                 |  
            |                                   +--------------------+                                                    |  
            |                                   | Phân tích vai trò  |                                                    |  
            |                                   | LoginController    |                                                    |  
            |                                   +---------+----------+                                                    |   
            |                                             |                                                               |          
            |                   +--------<----------------+-------------->-----------------+                              |  
            |                   |                                                          |                              ^  
            |             Admin Role                                                   User Role                          |  
            |                   |                                                          |                              |  
            |    +-------------------------------+                        +------------------------------+                |  
            |    | AdminHomePage.java            |                        | UserHomePage.java            |                |  
            |    | (Toàn quyền chỉnh sửa dữ liệu)|                        | (Chỉ xem, không chỉnh sửa)   |                |  
            |    +-------------------------------+                        +------------------------------+                |  
            |                   |                                                          |                              | 
            |                   v                                                          v                              | 
            |      +-------------------------+                              +-------------------------+                   |  
            |      | Các thao tác quản lý    |                              | Các thao tác xem dữ liệu|                   |  
            |      | Sách, Thành viên,       |                              |                         |                   |  
            |      | Mượn trả, Nhân viên     |                              |                         |                   |      
            |      | thông qua:              |                              |                         |                   |  
            |      | + BookManage.java       |                              |                         |                   |      
            |      | + MemberManage.java     |                              |                         |                   |  
            |      | + BorrowManage.java     |                              |                         |                   |  
            |      | + StaffManage.java      |                              |                         |                   |  
            |      +-------------------------+                              +-------------------------+                   |  
            |                   |                                                          |                              |  
            |                   v                                                          v                              |  
            |    +-----------------------------+                           +-----------------------------+                | 
            |    | Gửi thay đổi dữ liệu        |                           | Xem thông tin dữ liệu       |                | 
            |    | đến DB thông qua Controllers|                           |                             |                | 
            |    +-----------------------------+                           +-----------------------------+                |      
            |                  |                                                                                          |  
            |                  v                                                                                          |      
            |    +---------------------------+                                                                            |  
            |    | controllers/              |                                                                            | 
            |    | (Xử lý logic quản lý các  |                                                                            |  
            |    | chức năng: Sách, Mượn trả,|                                                                            |  
            |    | Thành viên, Nhân viên)    |                                                                            |              
            |    +---------------------------+                                                                            |      
            |              |                                                                                              |          
            |              v                                                                                              |  
            +--------------------------+  quay lại trang đăng nhập nếu đăng kí thành công                                 |  
            |   DBConnection.java      |-------------------------------------------------------------------------->-------|
            | (Lưu thông tin vào CSDL) |
            +-----------+--------------+
                          |
            +--------------------------+
            |     SQL Server           |
            | (Cập nhật dữ liệu mới)   |
            +--------------------------+
      



# Library Management App

Ứng dụng quản lý thư viện cung cấp các chức năng cơ bản và nâng cao để quản lý sách, thành viên, mượn trả và nhân viên. Dưới đây là chi tiết các chức năng và thiết kế giao diện của ứng dụng.

## 1. Chức Năng Quản Lý Thư Viện

Ứng dụng sẽ cung cấp các chức năng cơ bản và nâng cao sau:

### **Chức Năng Chính:**
- **Đăng nhập và đăng ký:**
  - Cho phép người dùng đăng nhập với quyền **Admin** hoặc **User**.
  - **Admin** có thể chỉnh sửa thông tin sách, thành viên, mượn trả, nhân viên. **User** chỉ có thể xem thông tin và lọc.
  
- **Quản lý sách (Admin):**
  - Thêm, sửa, xóa sách.
  - Tìm kiếm sách theo tên, tác giả, thể loại.
  - Hiển thị thông tin chi tiết của sách (Tên, tác giả, thể loại, số lượng, trạng thái còn hay hết).

- **Quản lý thành viên (Admin):**
  - Thêm, sửa, xóa thành viên.
  - Cập nhật thông tin thành viên (Tên, địa chỉ, số điện thoại, email).
  - Theo dõi lịch sử mượn trả sách của thành viên.

- **Quản lý mượn trả sách (Admin):**
  - Cho phép thành viên mượn và trả sách.
  - Tính toán thời gian mượn, phạt nếu trả muộn.
  - Cập nhật tình trạng sách (Đang mượn, có sẵn, đã trả).
  - Quản lý sách bị hỏng hoặc mất.

- **Quản lý nhân viên (Admin):**
  - Thêm, sửa, xóa nhân viên quản lý thư viện.
  - Giao quyền cho nhân viên để quản lý sách, thành viên, mượn trả.

### **Chức Năng Nâng Cao:**
- **Tìm kiếm và lọc nâng cao:**
  - Người dùng có thể tìm kiếm sách theo tên, tác giả, thể loại, năm xuất bản.

- **Hệ thống thông báo:**
  - Cảnh báo về sách sắp hết hạn mượn, sách bị hư hỏng, hoặc sắp hết hạn đăng ký thành viên.

- **Lịch sử giao dịch:**
  - Theo dõi lịch sử mượn trả sách của mỗi thành viên.

---

## 2. Thiết Kế Giao Diện

### **Trang Đăng Nhập (LoginPage.java):**
- Giao diện đơn giản và trực quan.
- **Form nhập thông tin đăng nhập:**
  - Trường nhập tài khoản (username) và mật khẩu (password).
  - Nút đăng nhập (Login) và chuyển hướng đến trang đăng ký (Register).
  - Hiển thị thông báo lỗi nếu đăng nhập không thành công.
  - Chuyển hướng đến trang chính sau khi đăng nhập thành công (HomePage).

### **Trang Đăng Ký (RegisterPage.java):**
- **Form đăng ký với các trường thông tin:**
  - Tên, tài khoản, mật khẩu, email, số điện thoại, loại người dùng (Admin/User).
  - Nút "Đăng ký" để gửi thông tin đến cơ sở dữ liệu.
  - Sau khi đăng ký thành công, tự động chuyển sang trang đăng nhập (LoginPage).

### **Trang Chính (HomePage.java):**
- Giao diện chính sau khi đăng nhập thành công.
- **Menu điều hướng:** Cung cấp các liên kết đến các chức năng chính (Quản lý sách, thành viên, mượn trả, nhân viên).
- **Hiển thị thông tin người dùng:** Tên người dùng, vai trò (Admin/User), các thông báo mới.
- **Nút thoát** để đăng xuất khỏi ứng dụng.

### **Quản Lý Sách (BookManage.java):**
- **Bảng danh sách sách:** Các cột như Tên sách, Tác giả, Thể loại, Số lượng, Trạng thái (Có sẵn, Đang mượn, Hết hạn).
- **Tìm kiếm và lọc sách:** Cho phép tìm kiếm theo tên, tác giả, thể loại.
- **Các nút hành động:**
  - Thêm sách mới (Dành cho Admin).
  - Chỉnh sửa sách hiện có (Dành cho Admin).
  - Xóa sách (Dành cho Admin).

### **Quản Lý Thành Viên (MemberManage.java):**
- **Danh sách thành viên:** Các cột như Tên thành viên, Địa chỉ, Số điện thoại, Email, Trạng thái (Đang hoạt động, Đã hết hạn).
- **Tìm kiếm và lọc thành viên.**
- **Các nút hành động:**
  - Thêm thành viên mới (Admin).
  - Chỉnh sửa thành viên (Admin).
  - Xóa thành viên (Admin).

### **Quản Lý Mượn Trả (BorrowManage.java):**
- **Danh sách mượn trả:** Hiển thị các sách đang mượn, ngày mượn, ngày trả, tình trạng trả sách.
- **Thêm mới mượn trả:** Cung cấp form nhập sách, thành viên, ngày mượn, ngày trả.
- **Phạt muộn:** Tính toán số ngày muộn và phạt nếu có.

### **Quản Lý Nhân Viên (StaffManage.java):**
- **Danh sách nhân viên:** Các cột như Tên nhân viên, Vai trò, Trạng thái (Đang làm việc, Đã nghỉ).
- **Tìm kiếm và lọc nhân viên.**
- **Các nút hành động:**
  - Thêm nhân viên mới (Admin).
  - Chỉnh sửa thông tin nhân viên (Admin).
  - Xóa nhân viên (Admin).

<<<<<<< HEAD
=======

###  [super advanced (hard)]
- User -> co the muon sach bang cach add cuon sach vao db (user tu nhap thong tin cua user, chon sach -> click nut borrow-> add thong tin sach &  
 thong tin user(member) vao db )-> tra ve thoi han muon sach va thong bao muon thanh cong !
>>>>>>> 8883d96c935412c3fb121fabe11cd22574a6d2a2
---

## 3. Tính Năng Phân Quyền

- **Admin:**
  - Có quyền truy cập và chỉnh sửa tất cả các phần của hệ thống: quản lý sách, thành viên, mượn trả, nhân viên.
  
- **User:**
  - Có quyền xem thông tin sách và mượn trả nhưng không thể chỉnh sửa.
  - Cấm sửa đổi thông tin sách, thành viên hoặc nhân viên.

---

## 4. Công Nghệ và Công Cụ Sử Dụng

- **Java Swing:** Để xây dựng giao diện người dùng.
- **MySQL hoặc SQL Server:** Quản lý cơ sở dữ liệu cho các chức năng quản lý sách, thành viên, mượn trả và nhân viên.
- **JDBC:** Để kết nối và thao tác với cơ sở dữ liệu.
- **JUnit:** Để kiểm tra và thử nghiệm các chức năng trong controllers.

---

## 5. Cài Đặt và Sử Dụng

### **Cài Đặt:**
1. **Clone repository:**
   ```bash
   git clone https://github.com/chuongcoder/LibraryManagementApp.git
