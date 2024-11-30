/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests;

import java.sql.*;
import controllers.MemberController;
import utils.DBConnection;
import java.util.List;
import java.util.ArrayList;
import models.Member;

/**
 *
 * @author chuon
 */
public class AuthControllerTest {

    public static List<Member> list = new ArrayList<>();

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MemberController ls = new MemberController();
        ls.testConnection();
        
        // int stt = 1; 
        // for (Member l : instance.list) {
        //     System.out.printf("stt: %d%n", stt++);
        //     l.printMember(); 
        //     System.out.println(); 
        // }

    }
}
