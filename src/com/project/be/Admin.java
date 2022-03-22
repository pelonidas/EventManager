package com.project.be;

import java.time.LocalDate;
import java.util.Date;

public class Admin extends User{

    public Admin(int id, String firstName, String lastName, String userName, String passWord, String email, Date birthDate) {
        super(id, firstName, lastName, userName, passWord, email, birthDate);
    }
}
