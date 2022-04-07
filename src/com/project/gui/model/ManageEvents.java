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
import java.util.List;

public class ManageEvents {
    IDALFacade EMFacade;//DELETE THIS LATER
    ObservableList<Event> allEvents;

    public ManageEvents() throws IOException {
        EMFacade = new DALController();//DELETE THIS LATER
    }

    public ObservableList<com.project.be.Event> getAllEvents() throws SQLException {
        allEvents= FXCollections.observableArrayList();
        allEvents.addAll(EMFacade.getAllEvents());
        return allEvents;
    }

    public void deleteEvent(Event event) throws SQLException, UserException {
        EMFacade.deleteEvent(event);
    }
}
