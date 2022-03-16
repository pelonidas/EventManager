package com.project.be;

public class Coordinator extends User{


    public Coordinator(int ID, String name, String email) {
        super(ID, name, email);
    }

    public Coordinator(String name, String email) {
        super(name, email);
    }
}
