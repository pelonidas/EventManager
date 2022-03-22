package com.project.be;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Customer extends User{

    ArrayList<Event>myEvents = new ArrayList<>();
    int category;

    public Customer(int id, String firstName, String lastName, String userName, String passWord, String email, Date birthDate) {
        super(id, firstName, lastName, userName, passWord, email, birthDate);
    }
    public Customer(int id, String firstName, String lastName, String userName, String passWord, String email, Date birthDate,int category) {
        super(id, firstName, lastName, userName, passWord, email, birthDate);
        this.category=category;
    }

    public ArrayList<Event> getMyEvents() {
        return myEvents;
    }
    public void setMyEvents(ArrayList<Event> myEvents) {
        this.myEvents = myEvents;
    }
}
