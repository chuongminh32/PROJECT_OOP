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
        return "ID: " + id +
               ", Title: " + title +
               ", Author: " + author +
               ", Publisher: " + publisher +
               ", Published Year: " + publishedYear +
               ", Category: " + category +
               ", Total Copies: " + totalCopies +
               ", Available Copies: " + availableCopies;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public int getPublishedYear() { return publishedYear; }
    public void setPublishedYear(int publishedYear) { this.publishedYear = publishedYear; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }

    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
}
