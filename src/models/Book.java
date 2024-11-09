/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Scanner;

/**
 *
 * @author chuon
 */
public class Book {

    private String bookID;
    private String name;
    private String author;
    private String genre; // the loai
    private int publishYear; // nam xuat ban
    private Boolean status; // co san hay da muon

    private Scanner sc = new Scanner(System.in);

    public Book() {
    }

    public Book(String id, String name, String author, String genre, int publishYear, Boolean status) {
        this.bookID = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.publishYear = publishYear;
        this.status = status;
    }
    
     public void outputInformationBook() {
        System.out.println("ID: " + this.getBookID());
        System.out.println("Name: " + this.getName());
        System.out.println("Author: " + this.getAuthor());
        System.out.println("Genre: " + this.getGenre());
        System.out.println("Published Year: " + this.getPublishYear());
        System.out.println("Status: " + this.getStatus());

    }
    public void inputInformationBook() {
        System.out.print("ID: ");
        this.setBookID(getSc().nextLine());

        System.out.print("Name: ");
        this.setName(getSc().nextLine());

        System.out.print("Author: ");
        this.setAuthor(getSc().nextLine());

        System.out.print("Genre: ");
        this.setGenre(getSc().nextLine());
        
        System.out.print("Published Year: ");
        this.setPublish(getSc().nextInt());
        
        System.out.print("Status[0/1]: ");
        this.setStatus(getSc().nextBoolean());

        getSc().nextLine();
    }
    /**
     * @return the bookID
     */
    public String getBookID() {
        return bookID;
    }

    /**
     * @param bookID the bookID to set
     */
    public void setBookID(String bookID) {
        this.bookID = bookID;
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
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * @param genre the genre to set
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * @return the publishYear
     */
    public int getPublishYear() {
        return publishYear;
    }

    /**
     * @param publishYear the publishYear to set
     */
    public void setPublish(int publishYear) {
        this.publishYear = publishYear;
    }

    /**
     * @return the status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * @return the sc
     */
    public Scanner getSc() {
        return sc;
    }

    /**
     * @param sc the sc to set
     */
    public void setSc(Scanner sc) {
        this.sc = sc;
    }

   
    /**
     * @return the name
     */

}

