package com.project.be;

public class TicketType {

    private String title;//VIP, Luxury and such
    private String benefits; // what the customer will be getting with this ticket
    private double price; // price for this type of ticket
    private int seatsAvailable;
    private int id;

    public TicketType(int id,String title, String benefits, double price,int seatsAvailable) {
        this.title = title;
        this.benefits = benefits;
        this.price = price;
        this.seatsAvailable=seatsAvailable;
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return title;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public int getId() {
        return id;
    }
}
