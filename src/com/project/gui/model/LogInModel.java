package com.project.gui.model;

import com.project.be.Admin;
import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.bll.exceptions.UserException;
import com.project.dal.facadeDAO.DALController;
import com.project.dal.facadeDAO.IDALFacade;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.sql.SQLException;
import java.util.List;

public class LogInModel {
    IDALFacade EMFacade;

    public LogInModel() throws IOException {
        EMFacade = new DALController();
    }
    public List<Coordinator> getAllCoordinators() throws SQLException, UserException {
        return EMFacade.getAllCoordinators();
    }

    public List<Customer> getAllCustomers() throws SQLException, UserException {
        return EMFacade.getAllCustomers();
    }

    public List<Admin> getAllAdmins() throws SQLException, UserException {
        return EMFacade.getAllAdmins();
    }
}
