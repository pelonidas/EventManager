package com.project.gui.model;

import com.project.be.*;
import com.project.bll.EventManager;
import com.project.bll.IEventManager;
import com.project.bll.exceptions.UserException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerModel {
    IEventManager eventManager;
    ObservableList<com.project.be.Customer> allCustomers;
    ObservableList<com.project.be.Customer> sameEventCustomers;
    private static CustomerModel customerModel = null;


    private CustomerModel() throws IOException, SQLException {
        eventManager = EventManager.getInstance();
        allCustomers = FXCollections.observableArrayList();
        loadData();
    }

    private void loadData() throws SQLException {
        allCustomers.setAll(eventManager.getAllCustomers());

        /*Thread loadCustomersThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    allCustomers.setAll(eventManager.getAllCustomers());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        loadCustomersThread.start();

         */
    }

    public ObservableList<com.project.be.Customer> getAllCustomers() throws SQLException {
        return allCustomers;
    }

    public Customer createCustomer(String firstName, String lastName, String email, int phoneNumber) throws SQLException, UserException {
        return eventManager.createCustomer(firstName,lastName,email,phoneNumber);
    }

    public ObservableList<com.project.be.Customer> getAllCustomersOnSameEvent(int id) throws Exception {
        sameEventCustomers = FXCollections.observableArrayList();
        sameEventCustomers.addAll(eventManager.getAllCustomersFromSameEvent(id));
        return sameEventCustomers;
    }

    public ObservableList<TicketType> getAllTicketTypesForEvent(Event selectedEvent) throws SQLException {
        List<TicketType> ticketTypes = eventManager.getAllTicketTypesForEvent(selectedEvent);
        return FXCollections.observableArrayList(ticketTypes);
    }

    public void buyTicket(TicketType ticketType, User user, Event selectedEvent) throws SQLException {
        eventManager.createTicket(ticketType,user,selectedEvent);
    }

    public void deleteCustomer(Customer selectedCustomer) throws SQLException {
        eventManager.deleteCustomer(selectedCustomer);
    }

    public void editCustomer(Customer customer) throws SQLException {
        eventManager.editCustomer(customer);
    }

    public void editCoordinator(Coordinator coordinator) throws SQLException {
        eventManager.editCoordinator(coordinator);
    }
    public static CustomerModel getInstance() throws IOException, SQLException {
        if (customerModel == null)
            customerModel = new CustomerModel();

        return customerModel;
    }
}
