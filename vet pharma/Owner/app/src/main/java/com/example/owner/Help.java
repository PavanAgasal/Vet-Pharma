package com.example.owner;

public class Help
{

    private String name,email,phone,issue,date;

    public Help() { }

    public Help(String name, String email, String phone, String issue, String date) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.issue = issue;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

