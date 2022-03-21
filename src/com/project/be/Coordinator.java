package com.project.be;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Coordinator extends User{

    List<Event> myEvents = new ArrayList<>();


    public Coordinator(int id, String firstName, String lastName, String userName, String passWord, String email, String address, int phoneNumber, LocalDate birthDate) {
        super(id, firstName, lastName, userName, passWord, email, address, phoneNumber, birthDate);
    }

    public List<Event> getMyEvents() {
        return myEvents;
    }

    public void setMyEvents(List<Event> myEvents) {
        this.myEvents = myEvents;
    }
}
