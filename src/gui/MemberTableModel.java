package gui;
import javax.swing.table.AbstractTableModel;

import models.Member;

import java.util.List;
import java.util.ArrayList;
// MemberTableModel là lớp để quản lý và hiển thị dữ liệu thành viên trong bảng.
public class MemberTableModel extends AbstractTableModel {

    private List<Member> members;
    private String[] columnNames = {"ID", "Name", "Email", "Phone", "Membership Date"};

    public MemberTableModel() {
        this.members = new ArrayList<>();
    }

    public void setMembers(List<Member> members) {
        this.members = members;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return members.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Member member = members.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return member.getId();
            case 1:
                return member.getName();
            case 2:
                return member.getEmail();
            case 3:
                return member.getPhone();
            case 4:
                return member.getMembershipDate();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
