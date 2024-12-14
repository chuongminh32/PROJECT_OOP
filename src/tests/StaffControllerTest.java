package tests;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection; // kết nối db
import java.sql.SQLException; // lỗi kết nối sql
import java.sql.Date;
import models.Staff; // import class Staff
import utils.DBConnection; // import class DBConnection
import controllers.StaffController;

public class StaffControllerTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException{
        Connection conn = DBConnection.getConnection();
        StaffController sc = new StaffController();
        ArrayList<Staff> list = new ArrayList();
        list = (ArrayList<Staff>) sc.getAllStaffs();
        int stt = 1;
        for (int i = 0; i < list.size(); i++){
            System.out.print(stt +"\t");
            Staff staff = list.get(i);
            System.out.print(staff.toString());
            System.out.println();
            stt++;
        }
        
    }
            
}
