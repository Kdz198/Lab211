
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class Main {

    public static void main(String[] args) {
        Management em = new Management();
        int n = 0;
        Scanner nhap = new Scanner(System.in);
        // em.readFile();
        em.readFileBinary();
        while (true) {
             try {
            em.showMenu();
            n = Integer.parseInt(nhap.nextLine());
            switch (n) {
                case 1: {
                    em.addEv();
                    //em.writeFile();
                    em.writeFileBinary();
                    break;
                }
                case 2: {
                    em.checkExistence();
                    break;
                }
                case 3: {
                    em.searchEvent();
                    break;
                }
                case 4: {
                    em.updateEvent();
                    //em.writeFile();
                    em.writeFileBinary();
                    break;
                }
                case 5: {
                    em.delEvent();
                    em.writeFileBinary();
                    break;
                }
                case 6: {
                    em.showAll();
                    break;
                }
                case 7: {
                  em.searchByDate();
                  break;
                }
                case 8: {
                    em.future();
                    break;
                }
                case 9: {
                    return;
                }
            }
           } catch (Exception e) {
               System.out.println("Enter Error !");
            }
        }
        
    }
}
