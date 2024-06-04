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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Book;
import model.Book.subBook;
import viewer.ShowMenu;

/**
 *
 * @author Administrator
 */
public class BookManagement {

    Scanner nhap = new Scanner(System.in);
    ShowMenu sm = new ShowMenu();
    int count = 0;
    public Map<Integer, Book> bookmap;
    ArrayList<Boolean> statusList = new ArrayList<>();

    public BookManagement() {
        this.bookmap = new HashMap<>();
    }

    public BookManagement(Map<Integer, Book> bookmap) {
        this.bookmap = bookmap;
    }

    public Map<Integer, Book> getBookMap() {
        return bookmap;
    }

    public void writeID() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/input/ID.txt"))) {
            writer.println(count);
        } catch (IOException e) {
            System.out.println("Error writing to file: ");
        }
    }

    public void readID() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/input/ID.txt"))) {
            count = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.out.println("Error reading from file: ");
        }
    }

    public void bookAction() {
        while (true) {
            sm.bookmenu();
            int n = Integer.parseInt(nhap.nextLine());
            switch (n) {
                case 1: {
                    addBook();
                    break;
                }
                case 2: {
                    updateBook();
                    break;
                }
                case 3: {
                    delBook();
                    break;
                }
                case 4: {
                    showBook();
                    break;
                }
                case 5: {

                    return;
                }
            }
        }
    }

    public void addBook() {
        while (true) {
            Book book = new Book();
            readID();
            count++;
            while (true) {
                System.out.print("Enter Name: ");
                book.setTitle(nhap.nextLine());
                if (!book.getTitle().trim().isEmpty()) {
                    break;
                }
                System.out.println("Name Must Provided");
            }
            while (true) {
                System.out.print("Enter Author: ");
                book.setAuthor(nhap.nextLine());
                if (!book.getAuthor().trim().isEmpty()) {
                    break;
                }
                System.out.println("Author Must Provided");
            }
            while (true) {
                System.out.print("Enter Publication Year: ");
                try {
                    int year = Integer.parseInt(nhap.nextLine());
                    book.setPublicyear("" + year);
                    break;
                } catch (Exception e) {
                    System.out.println("Year Not Valid ");
                }
            }
            while (true) {
                System.out.print("Enter Publisher: ");
                book.setPublisher(nhap.nextLine());
                if (!book.getPublisher().trim().isEmpty()) {
                    break;
                }
                System.out.println("Publisher Must Provided");
            }
            while (true) {
                System.out.print("Enter ISBN Code (X-XXX-XXXXX-X): ");
                String ISBN = nhap.nextLine();
                String regex = "\\b\\d{1}-\\d{3}-\\d{5}-\\d{1}\\b";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(ISBN);
                if (matcher.matches()) {
                    book.setISBN(ISBN);
                    break;
                }
                System.out.println("ISBN Invalid");
            }
            System.out.print("Enter Amount: ");
            book.setAmount(Integer.parseInt(nhap.nextLine()));
            book.setID(count);
            book.setStatus(true);
            bookmap.put(count, book);
            book.createSubBooks(book.getAmount());
            writeID();
            System.out.println("Add Successful");
            writeFile();
            writeFileSubBook();

            while (true) {
                System.out.print("1/ Add another book\n2/ Back to menu\nYour Options: ");
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

    public void updateBook() {
        while (true) {
            int id = 0;
            System.out.print("Enter ID: ");
            try {
                id = Integer.parseInt(nhap.nextLine());
            } catch (Exception e) {
            }
            if (bookmap.get(id) != null) {
                //  System.out.println(bookmap.get(id));

                System.out.print("Enter New Name: ");
                String name = nhap.nextLine();
                if (!name.trim().isEmpty()) {
                    bookmap.get(id).setTitle(name);
                }

                System.out.print("Enter New Author: ");
                String author = nhap.nextLine();
                if (!author.trim().isEmpty()) {
                    bookmap.get(id).setAuthor(author);
                }

                while (true) {
                    System.out.print("Enter New Puclication Year: ");
                    String year = nhap.nextLine();
                    try {
                        if (!year.trim().isEmpty()) {
                            int check = Integer.parseInt(year);
                            bookmap.get(id).setPublicyear(year);
                            break;
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Year Not Valid");
                    }
                }
                System.out.print("Enter New Publisher: ");
                String publisher = nhap.nextLine();
                if (!publisher.trim().isEmpty()) {
                    bookmap.get(id).setPublisher(publisher);
                }
                while (true) {
                    System.out.print("Enter ISBN Code (X-XXX-XXXXX-X): ");
                    String ISBN = nhap.nextLine();
                    if (ISBN.trim().isEmpty()) {
                        break;
                    }
                    String regex = "\\b\\d{1}-\\d{3}-\\d{5}-\\d{1}\\b";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(ISBN);
                    if (matcher.matches()) {
                        bookmap.get(id).setISBN(ISBN);
                        break;
                    }
                    System.out.println("ISBN Invalid");
                }
                while (true) {
                    System.out.print("Add New Amount: ");
                    String amount = nhap.nextLine();
                    try {
                        if (!amount.trim().isEmpty()) {
                            int check = Integer.parseInt(amount);
                            if (check > 0) {
                                bookmap.get(id).updateSubBooks(check, bookmap.get(id));
                                bookmap.get(id).setAmount(check + bookmap.get(id).getAmount());
                            }
                            break;
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Amount Not Valid");
                    }
                }
                while (true) {
                    System.out.print("Enter New Status \n1/ Active\n2/ Inactive\nYour Options: ");
                    String check = nhap.nextLine();
                    int k = 0;
                    try {
                        if (check.trim().isEmpty()) {
                            break;
                        } else {
                            k = Integer.parseInt(check);
                        }
                        if (k == 1) {
                            bookmap.get(id).setStatus(true);
                            break;
                        }
                        if (k == 2) {
                            bookmap.get(id).setStatus(false);
                            break;
                        }
                    } catch (Exception e) {

                    }
                    System.out.println("Option Not Valid !");
                }
                System.out.println("Update Successful");
                for (subBook sb : bookmap.get(id).getSubBooks()) {
                    sb.setTitle(bookmap.get(id).getTitle());
                    sb.setAuthor(bookmap.get(id).getAuthor());
                    sb.setPublicyear(bookmap.get(id).getPublicyear());
                    sb.setPublisher(bookmap.get(id).getPublisher());
                    sb.setISBN(bookmap.get(id).getISBN());
                    //sb.setStatus(bookmap.get(id).isStatus());
                }
                writeFile();
                writeFileSubBook();

            } else {
                System.out.println("Book Not Found");
            }

            while (true) {
                System.out.print("1/ Update Another Book\n2/ Back to menu\nYour Options: ");
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

    public void delBook() {
        while (true) {
            int id = 0;
            System.out.print("Enter ID: ");
            try {
                id = Integer.parseInt(nhap.nextLine());
            } catch (Exception e) {
            }
            if (bookmap.get(id) != null) {
                //  System.out.println(bookmap.get(id));
                while (true) {
                    System.out.print("Are you sure you want to delete?\n1/Yes\n2/No\nYour Option: ");
                    try {
                        int choice = Integer.parseInt(nhap.nextLine());
                        if (choice == 1) {
                            bookmap.get(id).setStatus(false);
                            System.out.println("Delete Successful !");
                            for (subBook sb : bookmap.get(id).getSubBooks()) {
                                // sb.setStatus(false);
                            }
                            writeFile();
                            writeFileSubBook();
                            break;
                        }
                        if (choice == 2) {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Option Not Valid");
                    }
                    System.out.println("Option Not Valid ");
                }
            } else {
                System.out.println("Book Not Found");
            }
            while (true) {
                System.out.print("1/Delete Another Book\n2/Back to menu\nYour Option: ");
                try {
                    int choice = Integer.parseInt(nhap.nextLine());
                    if (choice == 1) {
                        break;
                    } else if (choice == 2) {
                        return;
                    } else {
                        System.out.println("Option Not Valid!");
                    }
                } catch (Exception e) {
                    System.out.println("Option Not Valid");
                }
            }
        }
    }

    public void showBook() {
        System.out.println("┌────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf("│ %-4s │ %-20s │ %-15s │ %-15s │ %-7s │ %-13s │ %-6s │ %-19s │%n", "ID", "Title", "Author", "Publisher", "Year", "ISBN", "Amount", "Status");
        System.out.println("├────────────────────────────────────────────────────────────────────────────┤");
        for (Book book : bookmap.values()) {
            System.out.printf("│  %02d  │ %-20s │ %-15s │ %-15s │ %-7s │ %-13s │ %-6d │ %-19s │%n", book.getID(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getPublicyear(), book.getISBN(), book.getAmount(), book.isStatus() ? "Available" : "Not Available");
        }
        System.out.println("└────────────────────────────────────────────────────────────────────────────┘");
        while (true) {
            System.out.println("1/ Show Detail ");
            System.out.println("2/ Back to menu ");
            System.out.print("Your Option: ");
            int choice = Integer.parseInt(nhap.nextLine());
            if (choice == 2) {
                break;
            }
            if (choice == 1) {
                System.out.print("Enter ID: ");

                int id = Integer.parseInt(nhap.nextLine());
                if (bookmap.get(id) != null) {
                    System.out.println("┌────────────────────────────────────────────────────────────────────────────┐");
                    System.out.printf("│ %-5s │ %-19s │ %-15s │ %-15s │ %-7s │ %-13s │ %-6s │ %-19s │%n", "ID", "Title", "Author", "Publisher", "Year", "ISBN", "Amount", "Status");
                    System.out.println("├────────────────────────────────────────────────────────────────────────────┤");
                    for (subBook sb : bookmap.get(id).getSubBooks()) {
                        System.out.printf("│ %-5s │ %-19s │ %-15s │ %-15s │ %-7s │ %-13s │ %-6d │ %-19s │%n", sb.getSubID(), sb.getTitle(), sb.getAuthor(), sb.getPublisher(), sb.getPublicyear(), sb.getISBN(), sb.getAmount(), sb.isStatus() ? "Available" : "Not Available");
                    }
                    System.out.println("└────────────────────────────────────────────────────────────────────────────┘");
                } else {
                    System.out.println("Book Not Found");
                }
            }
            while (true) {
                System.out.print("1/Show Another Book\n2/Back to menu\nYour Option: ");
                try {
                    int c = Integer.parseInt(nhap.nextLine());
                    if (c == 1) {
                        break;
                    } else if (c == 2) {
                        return;
                    } else {
                        System.out.println("Option Not Valid!");
                    }
                } catch (Exception e) {
                    System.out.println("Option Not Valid");
                }
            }
        }
    }

    public void writeFile() {
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("src/input/BookData.dat"))) {
            for (Book book : bookmap.values()) {
                outputStream.writeInt(book.getID());
                outputStream.writeUTF(book.getTitle());
                outputStream.writeUTF(book.getAuthor());
                outputStream.writeUTF(book.getPublicyear());
                outputStream.writeUTF(book.getPublisher());
                outputStream.writeUTF(book.getISBN());
                outputStream.writeInt(book.getAmount());
                outputStream.writeBoolean(book.isStatus());
            }
            outputStream.flush();
            //System.out.println("Save Successful!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void readFile() {
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream("src/input/BookData.dat"))) {
            while (inputStream.available() > 0) {
                Book book = new Book();
                // Đọc thông tin sự kiện dưới dạng chuỗi UTF
                book.setID(inputStream.readInt());
                book.setTitle(inputStream.readUTF());
                book.setAuthor(inputStream.readUTF());
                book.setPublicyear(inputStream.readUTF());
                book.setPublisher(inputStream.readUTF());
                book.setISBN(inputStream.readUTF());
                book.setAmount(inputStream.readInt());
                book.setStatus(inputStream.readBoolean());
                bookmap.put(book.getID(), book);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    public void writeFileSubBook() {
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("src/input/SubBookData.dat"))) {
            for (Book book : bookmap.values()) {
                for (subBook sb : book.getSubBooks()) {
                    outputStream.writeInt(sb.getID());
                    outputStream.writeUTF(sb.getSubID());
                    outputStream.writeUTF(sb.getTitle());
                    outputStream.writeUTF(sb.getAuthor());
                    outputStream.writeUTF(sb.getPublicyear());
                    outputStream.writeUTF(sb.getPublisher());
                    outputStream.writeUTF(sb.getISBN());
                    outputStream.writeInt(sb.getAmount());
                    outputStream.writeBoolean(sb.isStatus());
                }
            }
            outputStream.flush();
//            System.out.println("Save Successful!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void readFileSubBook() {
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream("src/input/SubBookData.dat"))) {
            while (inputStream.available() > 0) {
                int bookID = inputStream.readInt();
                Book book = bookmap.get(bookID);
                subBook sb = book.new subBook();
                sb.setID(bookID);
                sb.setSubID(inputStream.readUTF());
                sb.setTitle(inputStream.readUTF());
                sb.setAuthor(inputStream.readUTF());
                sb.setPublicyear(inputStream.readUTF());
                sb.setPublisher(inputStream.readUTF());
                sb.setISBN(inputStream.readUTF());
                sb.setAmount(inputStream.readInt());
                sb.setStatus(inputStream.readBoolean());
                book.addSubBook(sb);

            }
        } catch (IOException e) {
            System.out.println("Error reading from file:book " + e.getMessage());
        }
    }

    public void saveStatusSubBook() {
        for (Book book : bookmap.values()) {
            for (subBook sb : book.getSubBooks()) {
                statusList.add(sb.isStatus());
            }
        }
    }

    public void writeStatusSubBook() {
        int index = 0;
        for (Book book : bookmap.values()) {
            for (Book.subBook sb : book.getSubBooks()) {
                sb.setStatus(statusList.get(index));
                index++;
            }
        }
    }

    public void sortYear() {
        ArrayList<Book> listbook = new ArrayList<>(bookmap.values());
        Collections.sort(listbook, new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                // Compare based on salary in descending order
                return (b1.getPublicyear().compareTo(b2.getPublicyear()));
            }
        });
        for (Book book : listbook) {
            System.out.println(book.getTitle() + " " + book.getPublicyear());
        }
    }
}
