package com.project.gui.model;

import com.project.be.Admin;
import com.project.bll.EventManager;
import com.project.bll.exceptions.UserException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminModel {
    EventManager manager;

    public AdminModel() throws IOException {
        manager = EventManager.getInstance();
    }

    public List<Admin> getAllAdmins() throws SQLException, UserException {
        return manager.getAllAdmins();
    }
}
