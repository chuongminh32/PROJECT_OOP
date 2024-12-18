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
        
        list.insert("M001", "B009", Date.valueOf("2024-12-1"), Date.valueOf("2024-12-17"), Date.valueOf("2024-12-30") , "Đã trả" );
    }
    
}
