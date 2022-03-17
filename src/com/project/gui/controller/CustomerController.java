package com.project.gui.controller;

import com.project.be.User;
import javafx.beans.property.Property;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


import java.util.Date;

/**
 * - upcoming are those that customer didn't "purchased" the ticket
 * - purchased are those that customer did "purchased" the ticket
 * - filter (will) working just for upcoming table
 */


public class CustomerController {
    @FXML
    private TextField filter;
    @FXML
    private TableView<User> tableViewParticipantsOnClickedEvent;
    @FXML
    private TableView<Event> upcomingTable;
    @FXML
    public TableColumn<User, String> participantsNameColumn;
    @FXML
    private TableColumn<Event, String> upcomingEventNameColumn;
    @FXML
    private TableColumn<Event, Date> upcomingEventStartColumn;
    @FXML
    private TableColumn<Event, String> upcomingEventLocationColumn;

    @FXML
    public void buyTicketOnAction(ActionEvent event) {
    }

    @FXML
    public void filterOnAction(ActionEvent event) {
    }

    @FXML
    public void purchasedEventsOnAction(ActionEvent event) {
    }

    private void setTableViewUpcomingEvents(){
        upcomingEventNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        upcomingEventStartColumn.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        upcomingEventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
    }
}
