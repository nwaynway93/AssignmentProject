package com.example.dell.myapplication;

import java.io.Serializable;

public class UserData implements Serializable{
    private int syskey;
    private String name;
    private int age;
    private String email;
    private String description;

    public int getSyskey() {
        return syskey;
    }

    public void setSyskey(int syskey) {
        this.syskey = syskey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
