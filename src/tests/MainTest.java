/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tests;

import java.sql.SQLException;
import java.sql.Date;
import java.util.Scanner;

/**
 *
 * @author admin
 */
public class MainTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException{
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        
        MemberControllerTest list = new MemberControllerTest();
        list.inDSMH();
        
 //       list.insert("M999", "Test", "email", "012345", Date.valueOf("2024-12-17") , "123" );
    }
    
}
