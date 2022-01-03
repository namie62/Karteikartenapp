package com.example.myapplication.objectclasses;


import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private List<Subject> subjects = new ArrayList<>();

    public User() {}

    public User(String username, String password) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void addSubject(Subject subject) {
        this.subjects.add(subject);
    }
}
