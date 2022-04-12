package com.project.gui.controller;

import com.project.be.Customer;
import com.project.be.Event;
import com.project.bll.exceptions.UserException;
import com.project.gui.model.ManageEventsModel;
import com.project.gui.view.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

public class ManageEventsController implements Initializable {
    @FXML
    private ListView<Customer> listParticipants;
    @FXML
    private TextField searchFilterEvents;
    @FXML
    private TableColumn<com.project.be.Event, String> titleColumn, locationColumn, descriptionColumn;
    @FXML
    private TableColumn<com.project.be.Event, LocalDate> dateColumn;
    @FXML
    private TableColumn<com.project.be.Event, Integer> ticketsAvailableColumn;
    @FXML
    private TableView<com.project.be.Event> eventsTable;
    Main main;
    private ManageEventsModel manageEventsModel;
    private ObservableList<com.project.be.Event> allEvents = FXCollections.observableArrayList();

    private com.project.be.Event eventSelected;
    private Integer test = 1;


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
        eventsTable.setEditable(true);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        titleColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<com.project.be.Event, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<com.project.be.Event, String> event) {
                com.project.be.Event event0 = event.getRowValue();
                event0.setTitle((event.getNewValue()));
                try {
                    manageEventsModel.editEvent(event0);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        locationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        locationColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<com.project.be.Event, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<com.project.be.Event, String> event) {
                com.project.be.Event event0 = event.getRowValue();
                event0.setLocation((event.getNewValue()));
                try {
                    manageEventsModel.editEvent(event0);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<com.project.be.Event, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<com.project.be.Event, String> event) {
                com.project.be.Event event0 = event.getRowValue();
                event0.setDescription((event.getNewValue()));
                try {
                    manageEventsModel.editEvent(event0);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        ticketsAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("seatsAvailable"));
        ticketsAvailableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return String.valueOf(object);
            }

            @Override
            public Integer fromString(String string) {
                try {
                    test = Integer.parseInt(string);
                } catch (NumberFormatException nfe) {
                    test = -1;
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText("Number format exception");
                    alert.setContentText("Please find a number available seats.");
                    alert.showAndWait();
                }
                return test;
            }
        }));
        ticketsAvailableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Event, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Event, Integer> event) {
                Event event1 = event.getRowValue();
                if (test >= 0) {
                    event1.setSeatsAvailable(event.getNewValue());
                    try {
                        manageEventsModel.editEvent(event1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    test = 1;
                    eventsTable.refresh();
                }
            }
        });
        eventsTable.setItems(getAllEvents());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ticketsAvailableColumn.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    ticketsAvailableColumn.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        eventsTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEventSelected(eventsTable.getSelectionModel().getSelectedItem());
                ObservableList<Customer> allParticipants;
                allParticipants = FXCollections.observableArrayList();
                try {
                    allParticipants.addAll(eventSelected.getParticipants());
                } catch (NullPointerException ignored) {
                }
                listParticipants.setItems(allParticipants);
            }
        });

        searchFilterEvents.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                ObservableList<com.project.be.Event> search = FXCollections.observableArrayList();
                for (com.project.be.Event event1 : allEvents) {
                    if (event1.getTitle().contains(searchFilterEvents.getText().toLowerCase(Locale.ROOT)))
                        search.add(event1);
                }
                eventsTable.setItems(search);

            }
        });

    }

    public void deleteEvent(ActionEvent actionEvent) throws SQLException, UserException {
        manageEventsModel.deleteEvent(getEventSelected());
        allEvents.remove(getEventSelected());
        populateEventsTableView();
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
