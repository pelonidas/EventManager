package com.project.be;

public class TicketType {

    private String title;//VIP, Luxury and such
    private String benefits; // what the customer will be getting with this ticket
    private double price; // price for this type of ticket

    public TicketType(String title, String benefits, double price) {
        this.title = title;
        this.benefits = benefits;
        this.price = price;
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
}
