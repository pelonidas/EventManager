package com.project.be;

public class Ticket {
    private Event event;
    Customer customer;
    int id ;

    private String code; //the code used for generating barcode and qr code

    public Ticket(int id, Event event,Customer customer,String code){
        this.event=event;
        this.id=id;
        this.customer= customer;
        this.code = code;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    

    public String getCode() {
        return code;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
