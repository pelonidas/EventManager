package com.project.gui.model;

import com.project.be.*;
import com.project.bll.EventManager;
import com.project.bll.IEventManager;
import com.project.bll.exceptions.UserException;
import com.project.dal.facadeDAO.DALController;
import com.project.dal.facadeDAO.IDALFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CustomerModel {
    IDALFacade EMFacade;//DELETE THIS LATER
    IEventManager eventManager;
    ObservableList<com.project.be.Customer> allCustomers;
    ObservableList<com.project.be.Customer> sameEventCustomers;

    public CustomerModel() throws IOException {
        EMFacade = new DALController();//DELETE THIS LATER
        eventManager = EventManager.getInstance();
    }

    public ObservableList<com.project.be.Customer> getAllCustomers() throws SQLException {
        allCustomers= FXCollections.observableArrayList();
        allCustomers.addAll(EMFacade.getAllCustomers());
        return allCustomers;
    }

    public void createCustomer(String firstName, String lastName, String email, int phoneNumber) throws SQLException, UserException {
        EMFacade.createCustomer(firstName,lastName,email,phoneNumber);
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
}
