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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.User;
import viewer.ShowMenu;

/**
 *
 * @author Administrator
 */
public class UserManagement {

    Scanner nhap = new Scanner(System.in);
    ShowMenu sm = new ShowMenu();
    int count = 0;
    public Map<Integer, User> usermap;

    public UserManagement() {
        this.usermap = new HashMap<>();
    }

    public UserManagement(Map<Integer, User> usermap) {
        this.usermap = usermap;
    }

    public Map<Integer, User> getUserMap() {
        return usermap;
    }

    public void writeID() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/input/IdUser.txt"))) {
            writer.println(count);
        } catch (IOException e) {
            System.out.println("Error writing to file: ");
        }
    }

    public void readID() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/input/IdUser.txt"))) {
            count = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.out.println("Error reading from file: ");
        }
    }

    public void userAction() {
        while (true) {
            sm.usermenu();
            int n = Integer.parseInt(nhap.nextLine());

            switch (n) {
                case 1: {
                    addUser();
                    break;
                }
                case 2: {
                    updateUser();
                    break;
                }
                case 3: {
                    deleteUser();
                    break;
                }
                case 4: {
                    showAll();
                    break;
                }
                case 5: {
                    return;
                }
            }
        }
    }

    public void addUser() {
        while (true) {
            readID();
            count++;
            User user = new User();
            System.out.print("Enter Name: ");
            user.setName(nhap.nextLine());
            while (true) {
                System.out.print("Enter Date (yyyy-MM-dd): ");
                try {
                    LocalDate day = LocalDate.parse(nhap.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    user.setDob("" + day);
                    break;
                } catch (Exception e) {
                    System.out.println("Date Not Valid");
                }
            }
            while (true) {
                System.out.print("Enter Number Phone (09XXXXXXXX) ");
                String number = nhap.nextLine();
                String regex = "^09\\d{8}$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(number);
                if (matcher.matches()) {
                    user.setPhone(number);
                    break;
                }
                System.out.println("Number Not Valid");
            }
            while (true) {
                System.out.print("Enter your email: ");
                String email = nhap.nextLine();
                String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);
                if (matcher.matches()) {
                    user.setEmail(email);
                    break;
                }
                System.out.println("Email Not Valid");
            }
            System.out.println("Add Successful");
            user.setStatus(true);
            user.setID(count);
            usermap.put(count, user);
            writeID();
            writeFile();
            while (true) {
                System.out.print("1/ Add Another User\n2/ Back to menu\nYour Options: ");
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

    public void updateUser() {
        int id = 0;
        while (true) {
            System.out.print("Enter ID: ");
            try {
                id = Integer.parseInt(nhap.nextLine());
            } catch (Exception e) {
            }
            if (usermap.get(id) == null) {
                System.out.println("User Not Exit");
            } else {
                User user = usermap.get(id);
                System.out.println("┌──────────────────────────────────────────────────────────────┐");
                System.out.printf("│ %-4s │ %-18s │ %-15s │ %-10s │ %-23s │ %-13s │%n", "ID", "Name", "Date of Birth", "Phone", "Email", "Status");
                System.out.println("├──────────────────────────────────────────────────────────────┤");
                System.out.printf("│ %-4s │ %-18s │ %-15s │ %-10s │ %-23s │ %-13s │%n", user.getID(), user.getName(), user.getDob(), user.getPhone(), user.getEmail(), user.isStatus() ? "Available" : "Not Available");
                System.out.println("└──────────────────────────────────────────────────────────────┘");
                System.out.print("Enter New Name: ");
                String name = nhap.nextLine();
                if (!name.trim().isEmpty()) {
                    usermap.get(id).setName(name);
                }
                while (true) {
                    System.out.print("Enter New Date (yyyy-MM-dd): ");
                    String check = nhap.nextLine();
                    if (check.trim().isEmpty()) {
                        break;
                    }
                    try {
                        LocalDate day = LocalDate.parse(check, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        usermap.get(id).setDob("" + day);
                        break;
                    } catch (Exception e) {
                        System.out.println("Date Not Valid");
                    }
                }
                while (true) {
                    System.out.print("Enter New Number Phone (09XXXXXXXX) ");
                    String check = nhap.nextLine();
                    if (check.trim().isEmpty()) {
                        break;
                    }
                    String number = check;
                    String regex = "^09\\d{8}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(number);
                    if (matcher.matches()) {
                        usermap.get(id).setPhone(number);
                        break;
                    }
                    System.out.println("Number Not Valid");
                }
                while (true) {
                    System.out.print("Enter New Email: ");
                    String email = nhap.nextLine();
                    if (email.trim().isEmpty()) {
                        break;
                    }
                    String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(email);
                    if (matcher.matches()) {
                        usermap.get(id).setEmail(email);
                        break;
                    }
                    System.out.println("Email Not Valid");
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
                            usermap.get(id).setStatus(true);
                            break;
                        }
                        if (k == 2) {
                            usermap.get(id).setStatus(false);
                            break;
                        }
                    } catch (Exception e) {

                    }
                    System.out.println("Option Not Valid !");
                }
                writeFile();
                System.out.println("Update Successful");
            }
            while (true) {
                System.out.print("1/ Update Another User\n2/ Back to menu\nYour Options: ");
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

    public void deleteUser() {
        int id = 0;
        while (true) {
            System.out.print("Enter ID: ");
            try {
                id = Integer.parseInt(nhap.nextLine());
            } catch (Exception e) {
            }
            if (usermap.get(id) == null) {
                System.out.println("User Not Found");
            } else {
                User user = usermap.get(id);
                System.out.println("┌──────────────────────────────────────────────────────────────┐");
                System.out.printf("│ %-4s │ %-18s │ %-15s │ %-10s │ %-23s │ %-13s │%n", "ID", "Name", "Date of Birth", "Phone", "Email", "Status");
                System.out.println("├──────────────────────────────────────────────────────────────┤");
                System.out.printf("│ %-4s │ %-18s │ %-15s │ %-10s │ %-23s │ %-13s │%n", user.getID(), user.getName(), user.getDob(), user.getPhone(), user.getEmail(), user.isStatus() ? "Available" : "Not Available");
                System.out.println("└──────────────────────────────────────────────────────────────┘");
                while (true) {
                    System.out.print("Are you sure you want to delete?\n1/Yes\n2/No\nYour Option: ");
                    try {
                        int choice = Integer.parseInt(nhap.nextLine());
                        if (choice == 1) {
                            usermap.get(id).setStatus(false);
                            System.out.println("Delete Successful !");
                            writeFile();
                            break;
                        }
                        if (choice == 2) {
                            break;
                        }
                    } catch (Exception e) {

                    }
                    System.out.println("Option Not Valid ");
                }
            }
            while (true) {
                System.out.print("1/Delete Another User\n2/Back to menu\nYour Option: ");
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

    public void writeFile() {
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("src/input/UserData.dat"))) {
            for (User user : usermap.values()) {
                outputStream.writeInt(user.getID());
                outputStream.writeUTF(user.getName());
                outputStream.writeUTF(user.getDob());
                outputStream.writeUTF(user.getPhone());
                outputStream.writeUTF(user.getEmail());
                outputStream.writeBoolean(user.isStatus());
            }
            outputStream.flush();
            //System.out.println("Save Successful!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void readFile() {
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream("src/input/UserData.dat"))) {
            while (inputStream.available() > 0) {
                User user = new User();
                // Đọc thông tin sự kiện dưới dạng chuỗi UTF
                user.setID(inputStream.readInt());
                user.setName(inputStream.readUTF());
                user.setDob(inputStream.readUTF());
                user.setPhone(inputStream.readUTF());
                user.setEmail(inputStream.readUTF());
                user.setStatus(inputStream.readBoolean());
                usermap.put(user.getID(), user);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: user " + e.getMessage());
        }
    }

    public void showAll() {
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.printf("│ %-4s │ %-18s │ %-15s │ %-10s │ %-23s │ %-13s │%n", "ID", "Name", "Date of Birth", "Phone", "Email", "Status");
        System.out.println("├──────────────────────────────────────────────────────────────┤");
        for (User user : usermap.values()) {
            System.out.printf("│ %-4s │ %-18s │ %-15s │ %-10s │ %-23s │ %-13s │%n", user.getID(), user.getName(), user.getDob(), user.getPhone(), user.getEmail(), user.isStatus() ? "Available" : "Not Available");
        }
        System.out.println("└──────────────────────────────────────────────────────────────┘");
    }

    public void sortYear() {
        ArrayList<User> listuser = new ArrayList<>(usermap.values());
        Collections.sort(listuser, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                // Compare based on salary in descending order
                return (u1.getDob().compareTo(u2.getDob()));
            }
        });
        for (User user : listuser) {
            System.out.println(user.getName() + " " + user.getDob());
        }
    }
}
