package com.project.be;

import java.time.LocalDate;
import java.util.ArrayList;

public class Customer extends User{

    ArrayList<Event>myEvents = new ArrayList<>();

    public Customer(int id, String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, LocalDate birthDate) {
        super(id, firstName, lastName, userName, passWord, email, address, phoneNumber, birthDate);
    }

    public ArrayList<Event> getMyEvents() {
        return myEvents;
    }

    public void setMyEvents(ArrayList<Event> myEvents) {
        this.myEvents = myEvents;
    }
}
