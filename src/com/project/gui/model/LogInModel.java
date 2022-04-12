package com.project.gui.model;

import com.project.be.Admin;
import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.be.Event;
import com.project.bll.EventManager;
import com.project.bll.exceptions.UserException;
import com.project.dal.facadeDAO.DALController;
import com.project.dal.facadeDAO.IDALFacade;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.sql.SQLException;
import java.util.List;

public class LogInModel {
    EventManager manager;

    public LogInModel() throws IOException {
        manager = EventManager.getInstance();
    }
    public List<Coordinator> getAllCoordinators() throws SQLException, UserException {
        return manager.getAllCoordinators();
    }

    public List<Customer> getAllCustomers() throws SQLException, UserException {
        return manager.getAllCustomers();
    }

    public List<Admin> getAllAdmins() throws SQLException, UserException {
        return manager.getAllAdmins();
    }

    public List<Event> getAllEvents() throws SQLException {
        return manager.getAllEvents();
    }
}
