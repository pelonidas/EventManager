package com.project.gui.model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.project.bll.EventManager;
import com.project.bll.IEventManager;
import com.project.dal.facadeDAO.DALController;
import com.project.dal.facadeDAO.IDALFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;


public class CustomerModel {
    IEventManager eventManager;
    IDALFacade EMFacade;//DELETE THIS LATER
    ObservableList<com.project.be.Customer> allCustomers ;
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

    public ObservableList<com.project.be.Customer> getAllCustomersOnSameEvent(int id) throws SQLServerException {
        sameEventCustomers = FXCollections.observableArrayList();
        sameEventCustomers.addAll(eventManager.getAllCustomersOnSameEvent(id));
        return sameEventCustomers;
    }
}
