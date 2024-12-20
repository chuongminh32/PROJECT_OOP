/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;


/**
 *
 * @author chuon
 */
public class Book {
    private String id;            // Mã sách (duy nhất)
    private String title;         // Tiêu đề sách
    private String author;        // Tác giả
    private String publisher;     // Nhà xuất bản
    private int publishedYear;    // Năm xuất bản
    private String category;      // Thể loại sách
    private int totalCopies;      // Tổng số bản
    private int availableCopies;  // Số lượng bản còn sẵn

    // Constructor
    public Book() {
    }
    public Book(String id, String title, String author, String publisher, int publishedYear, 
                String category, int totalCopies, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }
    public String toString() {
        return "ID: " + getId() +
               ", Title: " + getTitle() +
               ", Author: " + getAuthor() +
               ", Publisher: " + getPublisher() +
               ", Published Year: " + getPublishedYear() +
               ", Category: " + getCategory() +
               ", Total Copies: " + getTotalCopies() +
               ", Available Copies: " + getAvailableCopies();
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @return the publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * @return the publishedYear
     */
    public int getPublishedYear() {
        return publishedYear;
    }

    /**
     * @param publishedYear the publishedYear to set
     */
    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the totalCopies
     */
    public int getTotalCopies() {
        return totalCopies;
    }

    /**
     * @param totalCopies the totalCopies to set
     */
    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    /**
     * @return the availableCopies
     */
    public int getAvailableCopies() {
        return availableCopies;
    }

    /**
     * @param availableCopies the availableCopies to set
     */
    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

}
