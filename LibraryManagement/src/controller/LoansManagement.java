/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.User;
import model.Book;
import model.Loans;
import viewer.ShowMenu;

/**
 *
 * @author Administrator
 */
public class LoansManagement {

    Scanner nhap = new Scanner(System.in);
    ShowMenu sm = new ShowMenu();
    int count = 0;
    private UserManagement user;
    private BookManagement bm;
    private Map<Integer, Loans> loanmap;

    public LoansManagement() {
        this.loanmap = new HashMap<>();
    }

    public LoansManagement(Map<Integer, Loans> loanmap) {
        this.loanmap = loanmap;
    }

    public LoansManagement(BookManagement bm, UserManagement user) {
        this.bm = bm;
        this.user = user;
        this.loanmap = new HashMap<>();
    }

    public Book getBookById(int bookId) {
        return bm.getBookMap().get(bookId);
    }

    public void writeID() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/input/IdTransaction.txt"))) {
            writer.println(count);
        } catch (IOException e) {
            System.out.println("Error writing to file: ");
        }
    }

    public void readID() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/input/IdTransaction.txt"))) {
            count = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.out.println("Error reading from file: ");
        }
    }

    public void loanAction() {
        while (true) {
            sm.loanmenu();
            int n = Integer.parseInt(nhap.nextLine());

            switch (n) {
                case 1: {
                    addTransaction();
                    break;
                }
                case 2: {
                    updateTransaction();
                    break;
                }
                case 3: {
                    showAll();
                    break;
                }
                case 4: {
                    reportTransaction();
                    break;
                }
                case 5: {
                    return;
                }
            }

        }
    }

    public void addTransaction() {
        while (true) {
            readID();
            System.out.print("Enter User ID: ");
            int uid = Integer.parseInt(nhap.nextLine());
            
            if (user.getUserMap().get(uid) == null || user.getUserMap().get(uid).isStatus() == false) {
                System.out.println("User Not Exist");
            } else {
                //  System.out.println(user.getUserMap().get(uid).toString());
                System.out.print("Enter Book ID: ");
                int bid = Integer.parseInt(nhap.nextLine());
              
                if (bm.getBookMap().get(bid) == null || bm.getBookMap().get(bid).isStatus() == false) {
                    System.out.println("Book Not Exist");
                } else {
                    System.out.println("┌────────────────────────────────────────────────────────────────────────────┐");
                    System.out.printf("│ %-4s │ %-20s │ %-15s │ %-15s │ %-7s │ %-13s │ %-6s │ %-19s │%n", "ID", "Title", "Author", "Publisher", "Year", "ISBN", "Amount", "Status");
                    System.out.println("├────────────────────────────────────────────────────────────────────────────┤");
                    Book book = bm.getBookMap().get(bid);
                    for (Book.subBook sb : book.getSubBooks()) {
                         System.out.printf("│ %-4s │ %-20s │ %-15s │ %-15s │ %-7s │ %-13s │ %-6d │ %-19s │%n", sb.getSubID(), sb.getTitle(), sb.getAuthor(), sb.getPublisher(), sb.getPublicyear(), sb.getISBN(), sb.getAmount(), sb.isStatus() ? "Available" : "Not Available");
                    }
                     System.out.println("└────────────────────────────────────────────────────────────────────────────┘");
                    System.out.print("Choose Your Book:");
                    int choice = Integer.parseInt(nhap.nextLine());
                    try {
                        book.getSubBooks().get(choice-1);
                    }
                    catch (Exception e) {
                        System.out.println("Book Not Exit");
                    }
                    if (book.getSubBooks().get(choice - 1).isStatus()) {
                        //System.out.println("Book ban chon la: " + book.getSubBooks().get(choice - 1).toString());
                        System.out.println("Create Successful");
                        Loans loan = new Loans();
                        count++;
                        book.getSubBooks().get(choice - 1).setStatus(false);
                        loan.setUserID(uid);
                        loan.setBookID("" + book.getSubBooks().get(choice - 1).getSubID());
                        loan.setTransactionID(count);
                        loan.setStatus(false);
                        loan.setBorrowdate(LocalDateTime.now());
                        loan.setReturndate(LocalDateTime.MAX);
                        loan.setExpiredate(LocalDateTime.now().plusMonths(1));
                        // System.out.println(loan.toString());
                        loanmap.put(count, loan);
                        bm.writeFileSubBook();
                        writeID();
                        writeFile();
                    } else {
                        System.out.println("Book Not Available");
                    }
                }
            }
            while (true) {
                System.out.print("1/ Create Another Transaction\n2/ Back to menu\nYour Options: ");
                try {
                    int k = Integer.parseInt(nhap.nextLine());
                    if (k == 1) {
                        break;
                    } else if (k == 2) {
                        return;
                    } else {
                        System.out.println("Option Not Valid");
                    }
                } catch (Exception e) {
                    System.out.println("Option Not Valid");
                }
            }
        }
//        Book book = bm.getBookMap().get(1);
//        for (Book.subBook sb : book.getSubBooks()) {
//            System.out.println(sb.toString());
//        }
        //    System.out.println( bm.getBookMap().get(2));

        // System.out.println(user.getUserMap().get(3));
    }

    public void showAll() {
        System.out.println("┌────────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf("│ %-3s │ %-15s │ %-3s │ %-19s │ %-5s │ %-16s │ %-16s │ %-16s │%-10s │%n", "ID", "Member", "UID", "Book", "BID", "Borrow Date", "Return Date", "Expired", "Status");
        System.out.println("├────────────────────────────────────────────────────────────────────────────────┤");
        for (Loans loan : loanmap.values()) {
            String[] idb = loan.getBookID().split("\\.");
            Book book = bm.getBookMap().get(Integer.parseInt(idb[0]));
            String username = user.getUserMap().get(loan.getUserID()).getName();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            if (loan.getReturndate().isAfter(loan.getExpiredate()) && loan.isStatus() == false) {
                System.out.printf("│ %02d  │ %-15s │ %02d  │ %-19s │ %-5s │ %-16s │ %-16s │ %-16s │%-10s │%n", loan.getTransactionID(), username, loan.getUserID(), book.getTitle(), loan.getBookID(), loan.getBorrowdate().format(dateFormatter), "", loan.getExpiredate().format(dateFormatter), loan.isStatus() ? "Completed" : "Pending");
            } else {
                System.out.printf("│ %02d  │ %-15s │ %02d  │ %-19s │ %-5s │ %-16s │ %-16s │ %-16s │%-10s │%n", loan.getTransactionID(), username, loan.getUserID(), book.getTitle(), loan.getBookID(), loan.getBorrowdate().format(dateFormatter), loan.getReturndate().format(dateFormatter), loan.getExpiredate().format(dateFormatter), loan.isStatus() ? "Completed" : "Pending");
            }
        }
        System.out.println("└────────────────────────────────────────────────────────────────────────────────┘");
    }

    public void updateTransaction() {
        while (true) {
            System.out.println("1/ Return Book ");
            System.out.println("2/ Time Extension");
            System.out.print("Your Option: ");
            int choice = Integer.parseInt(nhap.nextLine());
            if (choice == 1) {
                System.out.print("Enter Transaction ID: ");
                int id = Integer.parseInt(nhap.nextLine());
                if (loanmap.get(id) != null) {
                    loanmap.get(id).setStatus(true);
                    loanmap.get(id).setReturndate(LocalDateTime.now());
                    String[] idb = loanmap.get(id).getBookID().split("\\.");
                    Book book = bm.getBookMap().get(Integer.parseInt(idb[0]));
                    book.getSubBooks().get(Integer.parseInt(idb[1]) - 1).setStatus(true);
                    System.out.println("Return Successful");
                    writeFile();
                    bm.writeFileSubBook();
                } else {
                    System.out.println("Transacton Not Found ");
                }
            }
            if (choice == 2) {
                System.out.print("Enter Transaction ID: ");
                int id = Integer.parseInt(nhap.nextLine());
                if (loanmap.get(id) != null) {
                    System.out.print("Enter Day You Want Extend: ");
                    int plus = Integer.parseInt(nhap.nextLine());
                    loanmap.get(id).setExpiredate(loanmap.get(id).getExpiredate().plusDays(plus));
                    System.out.println("Extend Successful");
                    writeFile();
                } else {
                    System.out.println("Transaction Not Found");
                }
            }
            while (true) {
                System.out.print("1/ Update Another Transaction\n2/ Back to menu\nYour Options: ");
                try {
                    int k = Integer.parseInt(nhap.nextLine());
                    if (k == 1) {
                        break;
                    } else if (k == 2) {
                        return;
                    } else {
                        System.out.println("Option Not Valid");
                    }
                } catch (Exception e) {
                    System.out.println("Option Not Valid");
                }
            }
        }
    }

    public void writeFile() {
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("src/input/TransactionData.dat"))) {
            for (Loans loan : loanmap.values()) {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String borrowdate = loan.getBorrowdate().format(dateFormatter);
                String returndate = loan.getReturndate().format(dateFormatter);
                String expiredate = loan.getExpiredate().format(dateFormatter);
                outputStream.writeInt(loan.getTransactionID());
                outputStream.writeInt(loan.getUserID());
                outputStream.writeUTF(borrowdate);
                outputStream.writeUTF(returndate);
                outputStream.writeUTF(expiredate);
                outputStream.writeUTF(loan.getBookID());
                outputStream.writeBoolean(loan.isStatus());
            }
            outputStream.flush();
            //System.out.println("Save Successful!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void readFile() {
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream("src/input/TransactionData.dat"))) {
            while (inputStream.available() > 0) {
                Loans loan = new Loans();
                // Đọc thông tin sự kiện dưới dạng chuỗi UTF
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                loan.setTransactionID(inputStream.readInt());
                loan.setUserID(inputStream.readInt());
                String bdate = inputStream.readUTF();
                LocalDateTime borrowdate = LocalDateTime.parse(bdate, dateFormatter);
                loan.setBorrowdate(borrowdate);
                String rdate = inputStream.readUTF();
                LocalDateTime returndate = LocalDateTime.parse(rdate, dateFormatter);
                loan.setReturndate(returndate);
                String edate = inputStream.readUTF();
                LocalDateTime expiredate = LocalDateTime.parse(edate, dateFormatter);
                loan.setExpiredate(expiredate);
                loan.setBookID(inputStream.readUTF());
                loan.setStatus(inputStream.readBoolean());
                loanmap.put(loan.getTransactionID(), loan);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    public void reportTransaction() {
        while (true) {
            System.out.println("1/ Show Borrowed Book ");
            System.out.println("2/ Show Book Overdue");
            System.out.print("Your Option: ");
            int choice = Integer.parseInt(nhap.nextLine());
            if (choice == 1) {
                System.out.println("┌──────────────────────────────────────────────────────────────────────┐");
                System.out.printf("│ %-3s │ %-15s │ %-3s │ %-20s │ %-4s │ %-16s │ %-16s │%-13s │%n", "ID", "Member", "UID", "Book", "BID", "Borrow Date", "Expired", "Remain");
                System.out.println("├──────────────────────────────────────────────────────────────────────┤");
                for (Loans loan : loanmap.values()) {
                    if (!loan.isStatus()) {
                        long remain = java.time.temporal.ChronoUnit.DAYS.between(loan.getBorrowdate(), loan.getExpiredate());
                        String[] idb = loan.getBookID().split("\\.");
                        Book book = bm.getBookMap().get(Integer.parseInt(idb[0]));
                        String username = user.getUserMap().get(loan.getUserID()).getName();
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        if (loan.getReturndate().isAfter(loan.getExpiredate()) && loan.isStatus() == false) {
                            System.out.printf("│ %02d  │ %-15s │ %02d  │ %-20s │ %-4s │ %-16s │ %-16s │%-13s │%n", loan.getTransactionID(), username, loan.getUserID(), book.getTitle(), loan.getBookID(), loan.getBorrowdate().format(dateFormatter), loan.getExpiredate().format(dateFormatter), remain + "(day)");
                        }
                    }
                }
                System.out.println("└──────────────────────────────────────────────────────────────────────┘");
            } else if (choice == 2) {
                System.out.println("┌──────────────────────────────────────────────────────────────────────┐");
                System.out.printf("│ %-3s │ %-15s │ %-3s │ %-20s │ %-4s │ %-16s │ %-16s │%-13s │%n", "ID", "Member", "UID", "Book", "BID", "Borrow Date", "Expired", "Late");
                System.out.println("├──────────────────────────────────────────────────────────────────────┤");
                for (Loans loan : loanmap.values()) {
                    if (loan.isStatus() == false && loan.getExpiredate().isBefore(LocalDateTime.now())) {
                        long late = java.time.temporal.ChronoUnit.DAYS.between(loan.getExpiredate(), LocalDateTime.now());
                        String[] idb = loan.getBookID().split("\\.");
                        Book book = bm.getBookMap().get(Integer.parseInt(idb[0]));
                        String username = user.getUserMap().get(loan.getUserID()).getName();
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        if (loan.getReturndate().isAfter(loan.getExpiredate()) && loan.isStatus() == false) {
                            System.out.printf("│ %02d  │ %-15s │ %02d  │ %-20s │ %-4s │ %-16s │ %-16s │%-13s │%n", loan.getTransactionID(), username, loan.getUserID(), book.getTitle(), loan.getBookID(), loan.getBorrowdate().format(dateFormatter), loan.getExpiredate().format(dateFormatter), late + "(day)");
                        }
                    }
                }
                System.out.println("└──────────────────────────────────────────────────────────────────────┘");
            } else {
                System.out.println("Choice Not Valid");
            }
            while (true) {
                System.out.print("1/ Report Another Transaction\n2/ Back to menu\nYour Options: ");
                try {
                    int k = Integer.parseInt(nhap.nextLine());
                    if (k == 1) {
                        break;
                    } else if (k == 2) {
                        return;
                    } else {
                        System.out.println("Option Not Valid");
                    }
                } catch (Exception e) {
                    System.out.println("Option Not Valid");
                }
            }
        }
    }
    
}
