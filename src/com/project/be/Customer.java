package com.project.be;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer extends User{

    ArrayList<Event>myEvents = new ArrayList<>();
    int category;
    List<Event>eventHistory;

    public Customer(int id, String firstName, String lastName, String userName, String passWord, String email, Date birthDate) {
        super(id, firstName, lastName, userName, passWord, email, birthDate);
    }
    public Customer(int id, String firstName, String lastName, String userName, String passWord, String email, Date birthDate,int category) {
        super(id, firstName, lastName, userName, passWord, email, birthDate);
        this.category=category;
    }
    public Customer(int id, String firstName, String lastName, String userName, String passWord, String email, Date birthDate, List<Event>eventHistory) {
        super(id, firstName, lastName, userName, passWord, email, birthDate);
        this.eventHistory=eventHistory;
    }
    public Customer(int id, String firstName, String lastName, String email, int phoneNumber) {
        super(id, firstName, lastName, email, phoneNumber);
    }

    public ArrayList<Event> getMyEvents() {
        return myEvents;
    }
    public void setMyEvents(ArrayList<Event> myEvents) {
        this.myEvents = myEvents;
    }

    public List<Event> getEventHistory() {
        return eventHistory;
    }

    public void setEventHistory(List<Event> eventHistory) {
        this.eventHistory = eventHistory;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " " + getUserName()
                + " " + getEmail() + " " + getPhoneNumber();
    }
}
