/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class Event {
    private String name;
    private String date;
    private String location;
    private int NOA;
    private boolean status;
    private String ID;


    public Event() {
        
    }

    public Event(String name, String date, String location, int NOA, boolean status, String ID) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.NOA = NOA;
        this.status = status;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNOA() {
        return NOA;
    }

    public void setNOA(int NOA) {
        this.NOA = NOA;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Event{" + "name=" + name + ", date=" + date + ", location=" + location + ", NOA=" + NOA + ", status=" + status + ", ID=" + ID + '}';
    }


}
