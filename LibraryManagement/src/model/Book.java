/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
//Book ID, book title, author, publication year,
//publisher, ISBN, and active_book (boolean field)
public class Book {

    private String title, author, publisher, publicyear, ISBN;
    private boolean status;
    private int ID, amount;
    private ArrayList<subBook> subBooks;

    public Book() {
        subBooks = new ArrayList<>();
    }

    public Book(ArrayList<subBook> subBooks) {
        this.subBooks = subBooks;
    }

    public Book(String title, String author, String publisher, String publicyear, String ISBN, boolean status, int ID, int amount) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicyear = publicyear;
        this.ISBN = ISBN;
        this.status = status;
        this.ID = ID;
        this.amount = amount;
        subBooks = new ArrayList<>();

    }

    public void createSubBooks(int amount) {
        
        for (int i = 1; i <= amount; i++) {
            String subBookId = ID + "." + String.format("%02d", i);
            subBook subBook = new subBook(title, author, publisher, publicyear, ISBN, status, ID, 1, subBookId);
            subBooks.add(subBook);
           // System.out.println("SubBook: " + subBook.toString());
        }
    }

    public void updateSubBooks(int number, Book book) {
        int k = subBooks.size();
        for (int i = 1; i <= number; i++) {
             String subBookId = ID + "." + String.format("%02d", k + i);
            subBook sb = book.new subBook(title, author, publisher, publicyear, ISBN, status, ID, 1, subBookId);
            subBooks.add(sb);
            //System.out.println("SubBook: " + sb.toString());
        }
    }

    public void addSubBook(subBook subBook) {
        subBooks.add(subBook);
    }

    public class subBook extends Book {

        public String subID;

        public subBook(String subID) {
            this.subID = subID;
        }

        public subBook() {
            super();
        }

        public subBook(String title, String author, String publisher, String publicyear, String ISBN, boolean status, int ID, int amount, String subID) {
            super(title, author, publisher, publicyear, ISBN, status, ID, amount);
            this.subID = subID;
        }

        public String getSubID() {
            return subID;
        }

        public void setSubID(String subID) {
            this.subID = subID;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("subBook{");
            sb.append("subID='").append(subID).append("'");
            sb.append(", ").append(super.toString()); // Gọi toString() của Book
            sb.append("}");
            return sb.toString();
        }

    }

    public ArrayList<subBook> getSubBooks() {
        return subBooks;
    }

    public void setSubBooks(ArrayList<subBook> subBooks) {
        this.subBooks = subBooks;
    }

    public int getID() {
        return ID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublicyear() {
        return publicyear;
    }

    public void setPublicyear(String publicyear) {
        this.publicyear = publicyear;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" + "title=" + title + ", author=" + author + ", publisher=" + publisher + ", publicyear=" + publicyear + ", ISBN=" + ISBN + ", status=" + status + ", ID=" + ID + ", amount=" + amount + '}';
    }

}
