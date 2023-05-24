package com.example.doctor;

import com.google.firebase.firestore.Exclude;

public class Appointment {
    @Exclude
    String id;

    private String docname,ownername, animaltype, animalage, date, time,current;


    public Appointment(String docname,String ownername, String animaltype, String animalage, String date, String time,String current) {
        this.docname = docname;
        this.ownername = ownername;
        this.animaltype = animaltype;
        this.animalage = animalage;
        this.date = date;
        this.time = time;
        this.current = current;
    }

    public String getCurrent() {
        return current;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocname() {
        return docname;
    }

    public String getOwnername() {
        return ownername;
    }

    public String getAnimaltype() {
        return animaltype;
    }

    public String getAnimalage() {
        return animalage;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }


    public Appointment(){

    }
}