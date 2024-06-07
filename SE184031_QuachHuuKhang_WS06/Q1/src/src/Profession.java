/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class Profession {
    private String name;
    private int age;
     private String major;

    public Profession() {
        this.name = "Nguyen Van A";
        this.major = "Student";
        this.age = 20;
    }

    public Profession(String name, int age, String major) {
        this.name = name;
        this.major = major;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty())
        name ="(no name)";   
        
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return name + "-"+age+"-"+major;
    }
    
}
