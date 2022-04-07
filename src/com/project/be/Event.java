package com.project.be;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Event {

    private int id;
    private String title;
    private Date dateAndTime;
    private String location;
    private String description;
    private List<Customer> participants;
    private int maxCapacity;
    private int seatsAvailable;
    private List<Coordinator>allCoordinators;
    private String allCoordinatorsString;

    public Event(int id, String title, Date dateAndTime, String location, String description) {
        this.id = id;
        this.title = title;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.description = description;

    }


    public Event(String title, Date dateAndTime, String location, String des) {
        this.title = title;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.description = des;
        this.participants = new ArrayList<>();
    }


    public Event(int id, String title, Date dateAndTime, String location, String description,int seatsAvailable) {
        this.id = id;
        this.title = title;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.description = description;
        this.seatsAvailable= seatsAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Customer> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Customer> participants) {
        this.participants = participants;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public List<Coordinator> getAllCoordinators() {
        return allCoordinators;
    }

    public void setAllCoordinators(List<Coordinator> allCoordinators) {
        this.allCoordinators = allCoordinators;
    }

    public String getAllCoordinatorsString(){
        for (Coordinator coordinator:allCoordinators){
                allCoordinatorsString = allCoordinatorsString+", "+coordinator.getFirstName()+" "+coordinator.getLastName();
        }
        return allCoordinatorsString;
    }

    @Override
    public String toString() {
        return "title= " + title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return title.equals(event.title) && location.equals(event.location) && description.equals(event.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, location, description);
    }
}
