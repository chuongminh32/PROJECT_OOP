package tests;

import controllers.MemberController;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import models.Member;
import utils.DBConnection;

public class MemberControllerTest {

    /**
     * @return the list
     */
    public ArrayList<Member> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(ArrayList<Member> list) {
        this.list = list;
    }
    private ArrayList<Member> list ;
    public MemberControllerTest(){
    this.list = new ArrayList<Member>();
}
    public void inDSMH()throws SQLException, ClassNotFoundException{
        Connection conn = DBConnection.getConnection();
        this.setList((ArrayList<Member>) MemberController.PrintListMember(conn));
        int stt = 1;
        for (int i=0; i < getList().size(); i++){
            System.out.print(stt+ "  ");
            Member p = getList().get(i);
            System.out.print(p.getId() + "   " +p.getName());
 //           p.xuatMH();
            System.out.println();
            stt++;
        }  
    }
    public void insert(String id,String name,String email,String phone,Date membershipDate,String password)throws SQLException, ClassNotFoundException{
        Connection conn = DBConnection.getConnection();
        MemberController memberController = new MemberController();
        boolean success = memberController.addMember(id, name, email, phone, membershipDate, password);
        if(success){
            inDSMH();
        }
        
    }
}
