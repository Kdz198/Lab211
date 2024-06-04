/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer;

/**
 *
 * @author Administrator
 */
public class ShowMenu {

    public void mainMenu() {
        System.out.println("┌──────────────────┐");
        System.out.println("│          MAIN MENU           │");
        System.out.println("├──────────────────┤");
        System.out.println("│ 1/ Manage Book               │");
        System.out.println("│ 2/ Manage User               │");
        System.out.println("│ 3/ Manage Loan               │");
        System.out.println("│ 4/ Exit                      │");
        System.out.println("└──────────────────┘");
        System.out.print("Your Option: ");
    }

    public void bookmenu() {
        System.out.println("┌──────────────────┐");
        System.out.println("│          BOOK MENU           │");
        System.out.println("├──────────────────┤");
        System.out.println("│ 1/ Add New Book              │");
        System.out.println("│ 2/ Update Book               │");
        System.out.println("│ 3/ Delete Book               │");
        System.out.println("│ 4/ Show All                  │");
        System.out.println("│ 5/ Back To Main Menu         │");
        System.out.println("│                              │");
        System.out.println("└──────────────────┘");
        System.out.print("Your Option: ");
    }

    public void usermenu() {
        System.out.println("┌──────────────────┐");
        System.out.println("│          USER MENU           │");
        System.out.println("├──────────────────┤");
        System.out.println("│ 1/ Add New User              │");
        System.out.println("│ 2/ Update User               │");
        System.out.println("│ 3/ Delete User               │");
        System.out.println("│ 4/ Show All                  │");
        System.out.println("│ 5/ Back To Main Menu         │");
        System.out.println("│                              │");
        System.out.println("└──────────────────┘");
        System.out.print("Your Option: ");
    }

    public void loanmenu() {
        System.out.println("┌──────────────────┐");
        System.out.println("│          LOANS MENU          │");
        System.out.println("├──────────────────┤");
        System.out.println("│ 1/ Add Transaction           │");
        System.out.println("│ 2/ Update Transaction        │");
        System.out.println("│ 3/ Show All                  │");
        System.out.println("│ 4/ Report Book               │");
        System.out.println("│ 5/ Back To Main Menu         │");
        System.out.println("│                              │");
        System.out.println("└──────────────────┘");
        System.out.print("Your Option: ");
    }
}
