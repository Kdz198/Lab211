/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewer;

import controller.BookManagement;
import controller.LoansManagement;
import controller.UserManagement;
import java.util.Scanner;

/**
 *
 * @author Administrator
 */
public class Main {

    public static void main(String[] args) {
        ShowMenu sm = new ShowMenu();
        BookManagement bm = new BookManagement();
        UserManagement um = new UserManagement();
        LoansManagement lm = new LoansManagement(bm, um);
        Scanner nhap = new Scanner(System.in);
        int n;
        bm.readFile();
        bm.readFileSubBook();
        um.readFile();
        lm.readFile();
        while (true)
        {
            sm.mainMenu();
            n = Integer.parseInt(nhap.nextLine());
            switch (n){
                case 1: {
                    bm.bookAction();
                    break;
                }
                case 2: {
                    um.userAction();
                    break;
                }
                case 3 : {
                    lm.loanAction();
                    break;
                }
                case 4: {
                    return;
                }
            }
        }
    }
}
