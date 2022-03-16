package com.project.be;

public class Customer extends User{


    public Customer(int ID, String name, String email) {
        super(ID, name, email);
    }

    public Customer(String name, String email) {
        super(name, email);
    }
}
