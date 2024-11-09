/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import java.sql.SQLException;
import java.util.Scanner;

import models.ManageBook;

/**
 *
 * @author chuon
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        ManageBook ListBook = new ManageBook();

        while (true) {
            // Hiển thị menu lựa chọn
            System.out.println("\n=== Quản lý Sản phẩm ===");
            System.out.println("1. Xem danh sách sản phẩm");
            System.out.println("2. Thêm sản phẩm mới");
            System.out.println("3. Xóa sản phẩm");
            System.out.println("4. Cập nhật sản phẩm");
            System.out.println("5. Tìm sản phẩm");
            System.out.println("6. Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    // Xem danh sách sản phẩm
                    System.out.println("Danh sách sản phẩm:");
                    ListBook.printListBook();
                    break;

                case 2:
                    // Thêm sản phẩm mới
//                    ManageBook.addBook();
                    System.out.println("Danh sách sản phẩm sau khi thêm:");
                    ListBook.printListBook();
                    break;

                case 3:
                    // Xóa sản phẩm
//                    ManageBook.delBook();
                    break;

                case 4:
                    // Cập nhật sản phẩm
//                    ManageBook.updateBook();
                    System.out.println("Danh sách sản phẩm sau khi cập nhật:");
                    ListBook.printListBook();
                    break;

                case 5:
                    // tim san pham
//                   ManageBook.findBookID();
                   break;

                case 6:
                    // Thoát chương trình
                    System.out.println("Thoát chương trình.");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }

}

