package com.project.gui.controller;

import com.project.be.Event;
import com.project.bll.util.DateTimeConverter;
import com.project.gui.model.CoordinatorModel;
import com.project.gui.model.EditEventModel;
import com.project.gui.model.ManageEventsModel;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void refreshTable() throws SQLException {
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

    public void handleTableview(MouseEvent mouseEvent) {
        Event e = coordinatorTableView.getSelectionModel().getSelectedItem();
        detailsTextarea.setText
                (
                        "Event title: " + e.getTitle() + "\n"
                        + "Event location: " + e.getLocation() + "\n"
                        + "Event attendance: " + e.getParticipants() + "\n"
                        + "Date: " + e.getDateAndTime().toString() + "\n"
                        + "Description: " + e.getDescription() + "\n"
                );
    }

    public void handleDeleteButton(ActionEvent event) {
        try {
            editEventModel.deleteEvent(coordinatorTableView.getSelectionModel().getSelectedItem());
            refreshTable();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}

