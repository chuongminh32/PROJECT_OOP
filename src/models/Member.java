package models;

import java.util.Date;

public class Member {
    private String id;
    private String name;
    private String email;
    private String phone;
    private Date membershipDate;
    private String passWord;

    // Constructor
    public Member() {
    }

    public Member(String id, String name, String email, String phone, Date membershipDate, String pass) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.membershipDate = membershipDate;
        this.passWord = pass;
    }

    // Print member information
    public String toString() {
        return "ID: " + getId() +
               ", Name: " + getName() +
               ", Email: " + getEmail() +
               ", Phone: " + getPhone() +
               ", membershipDate: " + getMembershipDate();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the membershipDate
     */
    public Date getMembershipDate() {
        return membershipDate;
    }

    /**
     * @param membershipDate the membershipDate to set
     */
    public void setMembershipDate(Date membershipDate) {
        this.membershipDate = membershipDate;
    }

    /**
     * @return the passWord
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * @param passWord the passWord to set
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

}
