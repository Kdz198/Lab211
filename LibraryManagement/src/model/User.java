/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Administrator
 */
public class User {
    private int ID;
    private String name,dob,phone,email;
    private boolean status;

    public User() {
    }

    public User(int ID, String name, String dob, String phone, String email, boolean status) {
        this.ID = ID;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" + "ID=" + ID + ", name=" + name + ", dob=" + dob + ", phone=" + phone + ", email=" + email + ", status=" + status + '}';
    }
    
    
}
