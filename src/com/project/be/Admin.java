package com.project.be;

import java.time.LocalDate;

public class Admin extends User{

    public Admin(int id, String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, LocalDate birthDate) {
        super(id, firstName, lastName, userName, passWord, email, address, phoneNumber, birthDate);
    }
}
