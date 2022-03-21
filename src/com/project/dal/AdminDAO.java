package com.project.dal;

import com.project.dal.connectorDAO.DBConnector;

import java.io.IOException;


public class AdminDAO {

    DBConnector dbConnector;
    public AdminDAO() throws IOException {
        dbConnector = new DBConnector();
    }

}
