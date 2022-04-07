package com.project.gui.controller;

import com.project.be.Coordinator;
import com.project.be.Customer;
import com.project.gui.model.ManageEventsModel;
import com.project.gui.view.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ManageEventsController implements Initializable {
    public VBox mainBox;
    @FXML
    private ListView<Customer> listParticipants;
    @FXML
    private TextField searchFilterEvents;
    @FXML
    private TableColumn<Event,String> titleColumn,locationColumn,descriptionColumn;
    @FXML
    private TableColumn<Event, LocalDate> dateColumn;
    @FXML
    private TableColumn<Event,Integer> ticketsAvailableColumn;
    @FXML
    private TableView<com.project.be.Event> eventsTable;
    Main main;
    private ManageEventsModel manageEventsModel;
    private  ObservableList <com.project.be.Event> allEvents =FXCollections.observableArrayList();

    private com.project.be.Event eventSelected;

    @FXML
    private void backView(ActionEvent actionEvent) {
        main.setLayoutChosen("admin");
        try {
            main.initRootLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void logOut(ActionEvent actionEvent) throws Exception {
        main.initLogin();
    }
    public void populateEventsTableView() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        ticketsAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("seatsAvailable"));

        eventsTable.setItems(getAllEvents());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /**try {
            manageEventsModel = new ManageEventsModel();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }*/

        eventsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEventSelected(eventsTable.getSelectionModel().getSelectedItem());
                ObservableList<Customer> allParticipants;
                allParticipants= FXCollections.observableArrayList();
                try {
                    allParticipants.addAll(eventSelected.getParticipants());
                }catch (NullPointerException ignored){}
                listParticipants.setItems(allParticipants);
            }
        });

        searchFilterEvents.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                ObservableList<com.project.be.Event>search = FXCollections.observableArrayList();
                for(com.project.be.Event event1 : allEvents){
                    if (event1.getTitle().contains(searchFilterEvents.getText().toLowerCase(Locale.ROOT)))
                        search.add(event1);
                }
                eventsTable.setItems(search);

            }
        });

    }

    public void deleteEvent(ActionEvent actionEvent) {
        /*manageEventsModel.deleteEvent(getEventSelected());
        allEvents.remove(getEventSelected());
        populateEventsTableView();*/
    }

    public com.project.be.Event getEventSelected() {
        return eventSelected;
    }

    public void setEventSelected(com.project.be.Event eventSelected) {
        this.eventSelected = eventSelected;
    }

    public ObservableList<com.project.be.Event> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(ObservableList<com.project.be.Event> allEvents) {
        this.allEvents = allEvents;
    }
}
