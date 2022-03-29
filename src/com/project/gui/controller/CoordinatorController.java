package com.project.gui.controller;

import com.project.be.Customer;
import com.project.be.Event;
import com.project.bll.util.DateTimeConverter;
import com.project.gui.model.EditEventModel;
import com.project.gui.model.ManageEventsModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CoordinatorController implements Initializable {
    @FXML
    private TableView userTable;
    @FXML
    private ListView ticketTypeList;
    @FXML
    private TableColumn<Customer,String> nameColumn,lastNameColumn,emailColumn;
    @FXML
    private TableView<Event> coordinatorTableView;
    @FXML
    private TableColumn<Event, String> name, attendance, location, date;
    private ManageEventsModel manageEventsModel;
    private EditEventModel editEventModel;
    @FXML
    private TextArea detailsTextarea;
    private DateTimeConverter dateTimeConverter = new DateTimeConverter();

    public CoordinatorController() throws IOException {
        manageEventsModel = new ManageEventsModel();
        editEventModel = new EditEventModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            refreshEventTable();
            refreshUserTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void refreshUserTable() throws SQLException {
        userTable.getItems().clear();
        userTable.refresh();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userTable.setItems(manageEventsModel.getAllUsers());
    }

    public void refreshEventTable() throws SQLException {
        coordinatorTableView.getItems().clear();
        coordinatorTableView.refresh();
        name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTitle()));
        date.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDateAndTime().toString()));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        attendance.setCellValueFactory(new PropertyValueFactory<>("description"));
        coordinatorTableView.setItems(manageEventsModel.getAllEvents());
    }


    public void handleCreateEvent(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("com/project/gui/view/NewEditEvent.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println(e);
        }

        CreateEventViewController alertDialogController = loader.getController();
        alertDialogController.setCoordinatorController(this);

        Stage stage = new Stage();
        stage.setTitle("New/Edit Song");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleManageButton(ActionEvent event) throws SQLException {
    }

    public void handleTableview(MouseEvent mouseEvent) throws SQLException {
        Event e = getSelectedEvent();
        if (e==null)
            return;
        updateEventInfo(e);
        loadEventTickets(e);
    }

    private void loadEventTickets(Event e) throws SQLException {
        ticketTypeList.setItems(manageEventsModel.getTicketsForEvent(e));
    }

    private void updateEventInfo(Event e) {
        detailsTextarea.setText
                (
                        "Event title: " + e.getTitle() + "\n"
                                + "Event location: " + e.getLocation() + "\n"
                                + "Seats available: " + e.getSeatsAvailable() + "\n"
                                + "Date: " + e.getDateAndTime().toString() + "\n"
                                + "Description: " + e.getDescription() + "\n"
                );
    }

    public void handleDeleteButton(ActionEvent event) {
        Event selectedEvent = getSelectedEvent();
        if (selectedEvent==null)
            return;
        try {
            editEventModel.deleteEvent(selectedEvent);
            refreshEventTable();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void handleEditButton(ActionEvent event) {
        Event selectedEvent = getSelectedEvent();
        if (selectedEvent==null)
            return;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("com/project/gui/view/EditEventView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println(e);
        }

        EditEventController editEventController = loader.getController();
        editEventController.setCoordinatorController(this);

        try {
            editEventController.setEventToBeUpdated(selectedEvent);
        } catch (Exception exception) {
            System.out.println(exception);
        }


        Stage stage = new Stage();
        stage.setTitle("New/Edit Event");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public Event getSelectedEvent(){
       return coordinatorTableView.getSelectionModel().getSelectedItem();
    }

    public void handleBuyTicket(ActionEvent actionEvent) {
    }
}

