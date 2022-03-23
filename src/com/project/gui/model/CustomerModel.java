package com.project.gui.model;

import com.project.be.*;
import com.project.bll.EventManager;
import com.project.bll.IEventManager;
import com.project.bll.exceptions.UserException;
import com.project.dal.facadeDAO.DALController;
import com.project.dal.facadeDAO.IDALFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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

    public void createCustomer(String firstName, String lastName, String userName, String password, String email, LocalDate birthDate) throws SQLException, UserException {
        EMFacade.createCustomer(firstName,lastName,userName,password,email, java.sql.Date.valueOf(birthDate));
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
}
