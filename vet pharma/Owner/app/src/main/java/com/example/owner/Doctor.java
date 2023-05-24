package com.example.owner;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Doctor implements Serializable {

    @Exclude
    String id;

    private String name,email,contact,qualification;

    public Doctor(){
    }

    public Doctor(String name, String email, String contact, String qualification) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.qualification = qualification;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
