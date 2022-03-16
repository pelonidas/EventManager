package com.project.be;

public abstract class User {
    private int ID;
    private String name;
    private String email;

    public User(int ID, String name, String email) {
        this.ID = ID;
        this.name = name;
        this.email = email;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
