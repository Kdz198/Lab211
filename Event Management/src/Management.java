
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class Management {

    Scanner nhap = new Scanner(System.in);
    private ArrayList<Event> EventList;
    int count = 0;

    public Management() {
        this.EventList = new ArrayList<>();
    }

    public Management(ArrayList<Event> EventList) {
        this.EventList = EventList;
    }

    public void writeID() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/input/Count.txt"))) {
            writer.println(count);
        } catch (IOException e) {
            System.out.println("Error writing to file: ");
        }
    }

    public void readID() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/input/Count.txt"))) {
            count = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.out.println("Error reading from file: ");
        }
    }

    public void showMenu() {
        System.out.println("\n┌───────────────────────────┐");
        System.out.println("│                 Event Management              │");
        System.out.println(" ────────────────────────────");
        System.out.println("│ 1/ Create New Event                           │");
        System.out.println("│ 2/ Check Existence of Event                   │");
        System.out.println("│ 3/ Search for an Event                        │");
        System.out.println("│ 4/ Update an Event                            │");
        System.out.println("│ 5/ Delete an Event                            │");
        System.out.println("│ 6/ Show All Events                            │");
        System.out.println("│ 7/ Search Event By Date                       │");
        System.out.println("│ 8/ Chronology                                 │");
        System.out.println("│ 9/ Exit                                       │");
        System.out.println("└───────────────────────────┘");
        System.out.print("\nYour option: ");
    }

    public void addEv() {
        while (true) {
            Event ev1 = new Event();
            LocalDate day;
            LocalTime time;
            readID();
            //System.out.println("Count: " + count);
            while (true) {
                System.out.print("Enter name: ");
                ev1.setName(nhap.nextLine());
                if (ev1.getName().length() >= 5 && !ev1.getName().contains(" ")) {
                    break;
                }
                System.out.println("Name not valid !");
            }
            //DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.STRICT); lock function leniet cua localdate
            // String d = day.format(dateFormatter);
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            while (true) {
                System.out.print("Enter Date (yyyy-MM-dd): ");
                try {
                    day = LocalDate.parse(nhap.nextLine(), dateFormatter);
                    
                    
                    LocalDate now = LocalDate.now();
                    if (day.isAfter(now)) {
                        break;
                    } else {
                        System.out.println("Time must be in the future");
                    }
                } catch (Exception e) {
                    System.out.println("Date Not Valid");
                }
            }
            while (true) {
                System.out.print("Enter Hour (HH:mm)");
                try {
                    time = LocalTime.parse(nhap.nextLine(), timeFormatter);
                    break;
                } catch (Exception e) {
                    System.out.println("Time Not Valid");
                }
            }
            String date = "" + day + " " + time;
            System.out.println("Date: " + date);
            ev1.setDate(date);
            while (true) {
                System.out.print("Enter Location: ");
                String location = nhap.nextLine();
                if (!location.trim().isEmpty()) {
                    ev1.setLocation(location);
                    break;
                }
                System.out.println("Please Provide Location");
            }
            while (true) {
                int number = 0;
                try {
                    System.out.print("Enter Number Of Attendees:");
                    number = Integer.parseInt(nhap.nextLine());
                } catch (Exception e) {
                }
                ev1.setNOA(number);
                if (ev1.getNOA() > 0) {
                    break;
                }
                System.out.println("Number Not Valid !");
            }

            while (true) {
                System.out.print("Enter Status: \n1/ Available\n2/ Not Available\nYour Options: ");
                int k = 0;
                try {
                    k = Integer.parseInt(nhap.nextLine());
                } catch (Exception e) {
                    System.out.println("Enter Error!");
                }
                if (k == 1) {
                    ev1.setStatus(true);
                    break;
                }
                if (k == 2) {
                    ev1.setStatus(false);
                    break;
                }
                System.out.println("Status Not Valid !");
            }
            ev1.setID("EV-" + String.format("%02d", count));
            EventList.add(ev1);
            count++;
            writeID();
            System.out.println("Create Successfully !");

            while (true) {
                System.out.print("1/ Create another event\n2/ Back to menu\nYour Options: ");
                int k = Integer.parseInt(nhap.nextLine());
                if (k == 1) {
                    break;
                } else if (k == 2) {
                    return;
                } else {
                    System.out.println("Option Not Valid !");
                }
            }
        }
    }

    public void checkExistence() {
        while (true) {
            System.out.print("Enter ID Event: ");
            String id = nhap.nextLine();
            int flag = 0;
            for (Event ev : EventList) {
                if (ev.getID().equals(id)) {
                    System.out.println("Exist Event");
                    flag++;
                    break;
                }
            }
            if (flag == 0) {
                System.out.println("No Event Found!");
            }
            while (true) {
                System.out.print("1/Another Check\n2/Back to menu\nYour Option: ");
                try {
                    int k = Integer.parseInt(nhap.nextLine());
                    if (k == 1) {
                        break;
                    } else if (k == 2) {
                        return;
                    } else {
                        System.out.println("Option Not Valid !");
                    }
                } catch (Exception e) {
                    System.out.println("Option Not Valid");
                }
            }
        }

    }

    public void searchEvent() {
        sortEventsByNOA();
        while (true) {
            System.out.print("Enter Event Location: ");
            String k = nhap.nextLine();
            boolean flag = true;
            for (Event ev : EventList) {
                if (ev.getLocation().contains(k)) {
                    //   System.out.println(ev.toString());
                    flag = false;
                }

            }
            if (flag) {
                System.out.println("No Event Found !");
            } else {
                System.out.println("\n┌─────────────────────────────────────────────────────┐");
                System.out.println("│   ID    │      Name           │        Date      │   Location │  NOA  │      Status     │");
                System.out.println("├─────────────────────────────────────────────────────┤");
                for (Event ev : EventList) {
                    if (ev.getLocation().contains(k)) {
                        if (ev.isStatus()) {
                            System.out.printf("│ %-8s│ %-20s│ %-17s│ %-11s│ %-6s│ %-16s│\n", ev.getID(), ev.getName(), ev.getDate(), ev.getLocation(), ev.getNOA(), "Available");
                        } else {
                            System.out.printf("│ %-8s│ %-20s│ %-17s│ %-11s│ %-6s│ %-16s│\n", ev.getID(), ev.getName(), ev.getDate(), ev.getLocation(), ev.getNOA(), "Not Available");
                        }
                    }
                }
                System.out.println("└─────────────────────────────────────────────────────┘");
            }
            while (true) {
                System.out.println("1/ Search Another Event ");
                System.out.println("2/ Back to Menu ");
                System.out.print("Your Option: ");
                try {
                    int choice = Integer.parseInt(nhap.nextLine());
                    if (choice == 2) {
                        return;
                    }
                    if (choice == 1) {
                        break;
                    }
                    System.out.println("Option Not Valid !");
                } catch (Exception e) {
                    System.out.println("Option Not Valiđ");
                }
            }
        }

    }

    public void updateEvent() {
        while (true) {
            System.out.print("Enter ID: ");
            String k = nhap.nextLine();
            int flag = 0;
            int noa = 0;
            int status = 0;
            for (Event ev : EventList) {
                if (ev.getID().equals(k)) {
                    flag++;
                    String[] date = ev.getDate().split(" ");
                    LocalDate day = LocalDate.parse(date[0]);
                    LocalTime time = LocalTime.parse(date[1]);
                    System.out.print("Enter New Name: ");
                    String name = nhap.nextLine();
                    if (!name.trim().isEmpty()) {
                        ev.setName(name);
                    }
                    while (true) {
                        System.out.print("Enter New Date (yyyy-MM-dd): ");
                        String check = nhap.nextLine();
                        if (!check.trim().isEmpty()) {
                            try {
                                day = LocalDate.parse(check, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                break;
                            } catch (Exception e) {
                                System.out.println("Date Not Valid");
                            }
                        } else {
                            break;
                        }
                    }
                    while (true) {
                        System.out.print("Enter New Time (HH:mm): ");
                        String check = nhap.nextLine();
                        if (check.trim().isEmpty()) {
                            break;
                        }
                        try {
                            time = LocalTime.parse(check, DateTimeFormatter.ofPattern("HH:mm"));
                            break;
                        } catch (Exception e) {
                            System.out.println("Time Not Valid");
                        }
                    }
                    ev.setDate("" + day + " " + time);
                    System.out.print("Enter New Location: ");
                    String location = nhap.nextLine();
                    if (!location.trim().isEmpty()) {
                        ev.setLocation(location);
                    }
                    while (true) {
                        System.out.print("Enter New Number of Attendees: ");
                        String check = nhap.nextLine();
                        if (check.trim().isEmpty()) {
                            break;
                        } else {
                            try {
                                noa = Integer.parseInt(check);
                            } catch (Exception e) {
                            }
                        }
                        if (noa > 0) {
                            ev.setNOA(noa);
                            break;
                        }
                        System.out.println("Number of Attendees Not Valid !");
                    }

                    while (true) {
                        System.out.print("Enter New Status \n1/ Available\n2/ Not Available\nYour Options: ");
                        String check = nhap.nextLine();
                        if (check.trim().isEmpty()) {
                            break;
                        } else {
                            status = Integer.parseInt(check);
                        }
                        if (status == 1) {
                            ev.setStatus(true);
                            break;
                        }
                        if (status == 2) {
                            ev.setStatus(false);
                            break;
                        }
                        System.out.println("Option Not Valid !");
                    }
                }
            }
            if (flag == 0) {
                System.out.println("Event Not Exist!");
            }
            while (true) {
                System.out.print("1/Update Event\n2/Back to menu\nYour Option: ");
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

    public void delEvent() {
        while (true) {
            int flag = 0;
            int fl = 0;
            System.out.print("Enter ID: ");
            String k = nhap.nextLine();
            for (Event ev : EventList) {
                if (ev.getID().equals(k)) {
                    flag++;
                    //   System.out.println(ev.toString());
                    while (true) {
                        System.out.print("Are you sure you want to delete?\n1/Yes\n2/No\nYour Option: ");
                        int choice = Integer.parseInt(nhap.nextLine());
                        if (choice == 1) {
                            EventList.remove(ev);
                            fl++;
                            System.out.println("Remove Successful");
                            writeFileBinary();
                            break;
                        }
                        if (choice == 2) {
                            break;
                        }
                        System.out.println("Option Not Valid!");
                    }
                }
                if (fl == 1) {
                    break;
                }
            }
            if (flag == 0) {
                System.out.println("Event Not Found!");
            }
            while (true) {
                System.out.print("1/Delete Another Event\n2/Back to menu\nYour Option: ");
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
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/input/EventData.dat"))) {
            for (Event ev : EventList) {
                //writer.println(ev.toString());
                writer.println(ev.getID() + "-" + ev.getName() + "-" + ev.getDate() + "-" + ev.getLocation() + "-" + ev.getNOA() + "-" + ev.isStatus());
            }
            System.out.println("Save Successful!");
        } catch (IOException e) {
            System.out.println("Error writing to file: ");
        }
    }

    public void writeFileBinary() {
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("src/input/EventDataBinary.dat"))) {
            for (Event ev : EventList) {

                outputStream.writeUTF(ev.getID());
                outputStream.writeChar('-');
                outputStream.writeUTF(ev.getName());
                outputStream.writeChar('-');
                outputStream.writeUTF(ev.getDate());
                outputStream.writeChar('-');
                outputStream.writeUTF(ev.getLocation());
                outputStream.writeChar('-');
                outputStream.writeInt(ev.getNOA());
                outputStream.writeChar('-');
                outputStream.writeBoolean(ev.isStatus());
            }
            outputStream.flush();
            System.out.println("Save Successful!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void readFileBinary() {
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream("src/input/EventDataBinary.dat"))) {
            while (inputStream.available() > 0) {
                Event ev = new Event();
                // Đọc thông tin sự kiện dưới dạng chuỗi UTF
                ev.setID(inputStream.readUTF());
                inputStream.readChar();
                ev.setName(inputStream.readUTF());
                inputStream.readChar();
                ev.setDate(inputStream.readUTF());
                inputStream.readChar();
                ev.setLocation(inputStream.readUTF());
                inputStream.readChar();
                ev.setNOA(inputStream.readInt());
                inputStream.readChar();
                ev.setStatus(inputStream.readBoolean());
                EventList.add(ev);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    public void readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/input/EventData.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] eventData = line.split("-");
                Event ev = new Event();
                int id = Integer.parseInt(eventData[1]);
                ev.setID("EV-" + String.format("%02d", id));
                ev.setName(eventData[2]);
                ev.setDate(eventData[3] + "-" + eventData[4] + "-" + eventData[5] + "-" + eventData[6]);
                ev.setLocation(eventData[7]);
                ev.setNOA(Integer.parseInt(eventData[8]));
                ev.setStatus(Boolean.parseBoolean(eventData[9]));
                EventList.add(ev);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: ");
        }
    }

    public void showAll() {
        sortEventsByDate();
        //sortID();
        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│   ID    │      Name           │        Date      │   Location │  NOA  │      Status     │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        for (Event ev : EventList) {

            if (ev.isStatus()) {
                System.out.printf("│ %-8s│ %-20s│ %-17s│ %-11s│ %-6s│ %-16s│\n", ev.getID(), ev.getName(), ev.getDate(), ev.getLocation(), ev.getNOA(), "Available");
            } else {
                System.out.printf("│ %-8s│ %-20s│ %-17s│ %-11s│ %-6s│ %-16s│\n", ev.getID(), ev.getName(), ev.getDate(), ev.getLocation(), ev.getNOA(), "Not Available");
            }
        }
        System.out.println("└─────────────────────────────────────────────────────┘");
    }

    public void sortEventsByDate() {
        Collections.sort(EventList, new Comparator<Event>() {
            @Override
            public int compare(Event ev1, Event ev2) {
                // Compare based on salary in descending order
                LocalDateTime d1 = LocalDateTime.parse(ev1.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                LocalDateTime d2 = LocalDateTime.parse(ev2.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                if (d1.compareTo(d2) == 0) {
                    return (ev1.getName().compareTo(ev2.getName()));
                }
                return (d1.compareTo(d2));
            }
        });
    }

    public void sortEventsByNOA() {
        Collections.sort(EventList, new Comparator<Event>() {
            @Override
            public int compare(Event ev1, Event ev2) {
                // Compare based on salary in descending order
                return (ev1.getNOA() - ev2.getNOA());
            }
        });
    }

    public void searchByDate() {
        String date;
        LocalDate sday, eday;
        LocalTime stime, etime;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        while (true) {
            System.out.print("Enter Start Date (yyyy-MM-dd): ");
            try {
                sday = LocalDate.parse(nhap.nextLine(), dateFormatter);
                if (sday.getYear() > 1975 && sday.getYear() < 2124) {
                    break;
                } else {
                    System.out.println("Year Not Valid");
                }
            } catch (Exception e) {
                System.out.println("Date Not Valid");
            }
        }
        while (true) {
            System.out.print("Enter Start Hour (HH:mm)");
            try {
                stime = LocalTime.parse(nhap.nextLine(), timeFormatter);
                break;
            } catch (Exception e) {
                System.out.println("Time Not Valid");
            }
        }
        date = "" + sday + " " + stime;
        LocalDateTime startday = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//        System.out.println(startday);

        while (true) {
            System.out.print("Enter End Date (yyyy-MM-dd): ");
            try {
                eday = LocalDate.parse(nhap.nextLine(), dateFormatter);
                if (eday.getYear() > 1975 && eday.getYear() < 2124) {
                    break;
                } else {
                    System.out.println("Year Not Valid");
                }
            } catch (Exception e) {
                System.out.println("Date Not Valid");
            }
        }
        while (true) {
            System.out.print("Enter End Hour (HH:mm)");
            try {
                etime = LocalTime.parse(nhap.nextLine(), timeFormatter);
                break;
            } catch (Exception e) {
                System.out.println("Time Not Valid");
            }
        }
        date = "" + eday + " " + etime;
        LocalDateTime endday = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//        System.out.println(endday);

        System.out.println("\n┌─────────────────────────────────────────────────────┐");
        System.out.println("│   ID    │      Name           │        Date      │   Location │  NOA  │      Status     │");
        System.out.println("├─────────────────────────────────────────────────────┤");
        for (Event ev : EventList) {
            LocalDateTime nowday = LocalDateTime.parse(ev.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            if (nowday.isAfter(startday) && nowday.isBefore(endday)) {
                if (ev.isStatus()) {
                    System.out.printf("│ %-8s│ %-20s│ %-17s│ %-11s│ %-6s│ %-16s│\n", ev.getID(), ev.getName(), ev.getDate(), ev.getLocation(), ev.getNOA(), "Available");
                } else {
                    System.out.printf("│ %-8s│ %-20s│ %-17s│ %-11s│ %-6s│ %-16s│\n", ev.getID(), ev.getName(), ev.getDate(), ev.getLocation(), ev.getNOA(), "Not Available");
                }
            }

        }
        System.out.println("└─────────────────────────────────────────────────────┘");
    }

    public void future() {

        System.out.println("\n┌─────────────────────────────────────────────────────────────┐");
        System.out.println("│   ID    │      Name           │        Date      │   Location │  NOA  │      Status     │  Time      │");
        System.out.println("├─────────────────────────────────────────────────────────────┤");
        for (Event ev : EventList) {
            LocalDateTime day = LocalDateTime.parse(ev.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime now = LocalDateTime.now();
            if (day.isAfter(now)) {
                if (ev.isStatus()) {
                    System.out.printf("│ %-8s│ %-20s│ %-17s│ %-11s│ %-6s│ %-16s│ %-11s│\n", ev.getID(), ev.getName(), ev.getDate(), ev.getLocation(), ev.getNOA(), "Available","Future");
                } else {
                    System.out.printf("│ %-8s│ %-20s│ %-17s│ %-11s│ %-6s│ %-16s│ %-11s│\n", ev.getID(), ev.getName(), ev.getDate(), ev.getLocation(), ev.getNOA(), "Not Available","Future");
                }
            } else {
                if (ev.isStatus()) {
                    System.out.printf("│ %-8s│ %-20s│ %-17s│ %-11s│ %-6s│ %-16s│ %-11s│\n", ev.getID(), ev.getName(), ev.getDate(), ev.getLocation(), ev.getNOA(), "Available","Past");
                } else {
                    System.out.printf("│ %-8s│ %-20s│ %-17s│ %-11s│ %-6s│ %-16s│ %-11s│\n", ev.getID(), ev.getName(), ev.getDate(), ev.getLocation(), ev.getNOA(), "Not Available","Past");
                }
            }
        }
        System.out.println("└─────────────────────────────────────────────────────────────┘");
    }
    public void sortID()
    {
        Collections.sort(EventList, new Comparator<Event>() {
            @Override
            public int compare(Event ev1, Event ev2) {
                return (ev1.getID().compareTo(ev2.getID()));
            }
        });
    }
}
