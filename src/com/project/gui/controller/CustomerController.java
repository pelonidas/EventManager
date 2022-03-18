package com.project.gui.controller;

import com.project.be.Event;
import com.project.be.User;
import com.project.gui.model.CustomerModel;
import javafx.beans.property.Property;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * - upcoming are those that customer didn't "purchased" the ticket
 * - purchased are those that customer did "purchased" the ticket
 * - filter (will) working just for upcoming table
 */


public class CustomerController implements Initializable {
    @FXML
    private TextField filter;
    @FXML
    private TableView<User> tableViewParticipantsOnClickedEvent;
    @FXML
    private TableView<Event> upcomingTable;
    @FXML
    private TableColumn<User, String> participantsNameColumn;
    @FXML
    private TableColumn<Event, String> upcomingEventNameColumn;
    @FXML
    private TableColumn<Event, Date> upcomingEventStartColumn;
    @FXML
    private TableColumn<Event, String> upcomingEventLocationColumn;

    private final CustomerModel customerModel;

    public CustomerController() {
        customerModel = new CustomerModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTableViewUpcomingEvents();
    }

    @FXML
    public void buyTicketOnAction(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/project/gui/view/Ticket.fxml"));
        root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Participants");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void filterOnAction(ActionEvent event) {
        //database filter
    }

    @FXML
    public void purchasedEventsButtonOnAction(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/project/gui/view/CustomerPurchasedEvents.fxml"));
        root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Participants");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void setTableViewUpcomingEvents(){
        upcomingEventNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        upcomingEventStartColumn.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        upcomingEventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        upcomingTable.setItems(customerModel.getEventObservableList());
    }

    private void setTableViewParticipantsOnClickedEvent(){
//        participantsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        tableViewParticipantsOnClickedEvent.setItems(customerModel.);
    }
}
