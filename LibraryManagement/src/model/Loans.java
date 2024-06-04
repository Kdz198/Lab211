/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author Administrator
 */
public class Loans {
    private LocalDateTime borrowdate,returndate,expiredate;
    private int userID,transactionID;
    private String bookID;
    private boolean status;
    
    public Loans() {
    }

    public Loans(LocalDateTime borrowdate, LocalDateTime returndate, LocalDateTime expiredate, int userID, int transactionID, String bookID, boolean status) {
        this.borrowdate = borrowdate;
        this.returndate = returndate;
        this.expiredate = expiredate;
        this.userID = userID;
        this.transactionID = transactionID;
        this.bookID = bookID;
        this.status = status;
    }


    public LocalDateTime getBorrowdate() {
        return borrowdate;
    }

    public LocalDateTime getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(LocalDateTime expiredate) {
        this.expiredate = expiredate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setBorrowdate(LocalDateTime borrowdate) {
        this.borrowdate = borrowdate;
    }

    public LocalDateTime getReturndate() {
        return returndate;
    }

    public void setReturndate(LocalDateTime returndate) {
        this.returndate = returndate;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    @Override
    public String toString() {
        return "Loans{" + "borrowdate=" + borrowdate + ", returndate=" + returndate + ", expiredate=" + expiredate + ", userID=" + userID + ", transactionID=" + transactionID + ", bookID=" + bookID + ", status=" + status + '}';
    }

 
  
}
