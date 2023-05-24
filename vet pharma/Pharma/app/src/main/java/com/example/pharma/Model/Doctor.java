package com.example.pharma.Model;

public class Doctor {

    private String name,email,contact,qualification;

    public Doctor(){
    }

    public Doctor(String name, String email, String contact, String qualification) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.qualification = qualification;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getQualification() {
        return qualification;
    }
}
