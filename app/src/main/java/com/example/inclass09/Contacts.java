package com.example.inclass09;

public class Contacts {
    String id;
    String name;
    String phone;
    String email;
    String phoneType;

    public Contacts(){

    }

    public Contacts(String id, String name, String email, String phone, String phoneType) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.phoneType = phoneType;
    }

    @Override
    public String toString() {
        return name;
    }
}
