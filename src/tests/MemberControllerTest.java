package tests;

import controllers.BorrowController;
import controllers.MemberController;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import models.Borrow;
import models.Member;
import utils.DBConnection;

public class MemberControllerTest {

     /**
     * @return the list
     */
    public ArrayList<Borrow> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(ArrayList<Borrow> list) {
        this.list = list;
    }
    private ArrayList<Borrow> list ;
    public MemberControllerTest(){
    this.list = new ArrayList<Borrow>();
}
    public void inDSMH()throws SQLException, ClassNotFoundException{
        Connection conn = DBConnection.getConnection();
        this.setList((ArrayList<Borrow>) BorrowController.PrintList_MuonTra(conn));
        int stt = 1;
        for (int i=0; i < getList().size(); i++){
            System.out.print(stt+ "  ");
            Borrow p = getList().get(i);
            System.out.print(p.getId() + "   " +p.getStatus());
 //           p.xuatMH();
            System.out.println();
            stt++;
        }  
    }
    public void insert(String memberId, String bookId, Date borrowDate, Date returnDate, Date dueDate, String status)
            throws SQLException, ClassNotFoundException{
        Connection conn = DBConnection.getConnection();
        BorrowController memberController = new BorrowController();
        boolean success = memberController.addBorrow(memberId, bookId, borrowDate, returnDate, dueDate, status);
        if(success){
            inDSMH();
        }

        
    }
}
