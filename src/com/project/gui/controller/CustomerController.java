package com.project.gui.controller;

import com.project.be.Customer;
import com.project.be.Event;
import com.project.be.TicketType;
import com.project.be.User;
import com.project.gui.model.CustomerModel;
import com.project.gui.model.ManageEventsModel;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * - upcoming are those that customer didn't "purchased" the ticket
 * - purchased are those that customer did "purchased" the ticket
 * - filter (will) working just for upcoming table
 */


public class CustomerController implements Initializable {
    @FXML
    private TableView<TicketType> ticketTypeTable;
    @FXML
    private TableColumn ticketTypeTitleColumn;
    @FXML
    private TableColumn ticketTypePriceColumn;
    @FXML
    private TableColumn ticketTypeBenefitsColumn;
    @FXML
    private TableColumn ticketTypeSeatsColumn;
    @FXML
    private TextArea additionalInfoTextArea;
    @FXML
    private TextField filter;
    @FXML
    private TableView<Customer> tableViewParticipantsOnClickedEvent;
    @FXML
    private TableColumn<Customer, String> firstNameColumn;
    @FXML
    private TableColumn<Customer, String> secondNameColumn;
    @FXML
    private TableView<Event> upcomingTable;
    @FXML
    private TableColumn<Event, String> upcomingEventNameColumn;
    @FXML
    private TableColumn<Event, Date> upcomingEventStartColumn;
    @FXML
    private TableColumn<Event, String> upcomingEventLocationColumn;

    private final ManageEventsModel manageEventsModel;
    private final CustomerModel customerModel;

    public CustomerController() throws Exception {
        this.manageEventsModel = new ManageEventsModel();
        this.customerModel = new CustomerModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setTableViewUpcomingEvents();
            setTableViewTicketTypes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setTableViewTicketTypes() {
        ticketTypeBenefitsColumn.setCellValueFactory(new PropertyValueFactory<>("benefits"));
        ticketTypePriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        ticketTypeSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("seatsAvailable"));
        ticketTypeTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
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

    private void setTableViewUpcomingEvents() throws SQLException {
        upcomingEventNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        upcomingEventStartColumn.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        upcomingEventLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        upcomingTable.setItems(manageEventsModel.getAllEvents());
    }

    private void setTableViewParticipantsOnClickedEvent(){

    }

    //putting notes into text are if event is clicked
    //---------------------------------------------------------------------------
    public void tableVIewOnMouseRelease(MouseEvent mouseEvent) throws Exception {
        Event selectedEvent = upcomingTable.getSelectionModel().getSelectedItem();
        int idOfSelectedItem = selectedEvent.getId();
        if (selectedEvent != null){
            additionalInfoTextArea.setText(selectedEvent.getDescription());
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            secondNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            tableViewParticipantsOnClickedEvent.setItems(customerModel.getAllCustomersOnSameEvent(idOfSelectedItem));
            displayAvailableTicketTypes(selectedEvent);
        }
    }

    private void displayAvailableTicketTypes(Event selectedEvent) throws SQLException {
        ticketTypeTable.setItems(customerModel.getAllTicketTypesForEvent(selectedEvent));
    }
}
