package com.project.be;

public class Admin extends User{


    public Admin(int ID, String name, String email) {
        super(ID, name, email);
    }

    public Admin(String name, String email) {
        super(name, email);
    }
}
