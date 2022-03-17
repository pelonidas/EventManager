package com.project.gui.controller;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


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
    private TableView<Event> upcomingTable, purchasedTable;
    @FXML
    private TableColumn<Event, String> upcomingNameEventColumn, purchasedNameEventColumn;
    @FXML
    private TableColumn<Event, Date> upcomingStartEventColumn, purchasedStartEventColumn;
    @FXML
    private TableColumn<Event, String> upcomingLocationEventColumn, purchasedLocationEventColumn;
    @FXML
    private TableColumn<Event, String> upcomingAdditionalInfoEventColumn, purchasedAdditionalInfoEventColumn;

    public void buyTicketOnAction(ActionEvent event) {
    }

    public void filterOnAction(ActionEvent event) {
    }
}
