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

public class LogInModel {
    IDALFacade EMFacade;

    public LogInModel() throws IOException {
        EMFacade = new DALController();
    }
    public Coordinator logInCoordinatorCredentials(String userName) throws SQLException, UserException {
        return EMFacade.logInCoordinatorCredentials(userName);
    }

    public Customer logInCustomerCredentials(String text) throws SQLException, UserException {
        return EMFacade.logInCustomerCredentials(text);
    }

    public Admin logInAdminCredentials(String text) throws SQLException, UserException {
        return EMFacade.logInAdminCredentials(text);
    }
}
