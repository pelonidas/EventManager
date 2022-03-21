package com.project.be;

public class TicketType {

    private int title;//VIP, Luxury and such
    private String benefits;
    private double price;

    public TicketType(int title, String benefits, double price) {
        this.title = title;
        this.benefits = benefits;
        this.price = price;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
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
}
