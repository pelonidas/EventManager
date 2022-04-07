package com.project.gui.controller;

import com.google.zxing.WriterException;
import com.project.be.Customer;
import com.project.be.Event;
import com.project.be.Ticket;
import com.project.be.TicketType;
import com.project.bll.exceptions.UserException;
import com.project.bll.util.DateTimeConverter;
import com.project.gui.model.EditEventModel;
import com.project.gui.model.ManageEventsModel;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class CoordinatorController implements Initializable {
    @FXML
    private HBox buttonBox;
    @FXML
    private TextField userSearchField;
    @FXML
    private TextField eventSearchField;
    @FXML
    private TableView<Customer> userTable;
    @FXML
    private ListView<TicketType> ticketTypeList;
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

    public CoordinatorController() throws IOException, SQLException {
        manageEventsModel = new ManageEventsModel();
        editEventModel = new EditEventModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initializeEventTable();
            initializeUserTable();
            initIcons();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void initIcons() {
        Text addTypeButton = GlyphsDude.createIcon(FontAwesomeIcons.REFRESH,"24");
        addTypeButton.setOnMouseClicked(refreshData);

        buttonBox.getChildren().add(addTypeButton);
    }

    EventHandler refreshData = new EventHandler() {
        @Override
        public void handle(javafx.event.Event event) {
            try {
                manageEventsModel.refreshData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    private void initializeUserTable() throws SQLException {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        refreshUserTable();
    }

    private void initializeEventTable() throws SQLException {
        name.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTitle()));
        date.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getDateAndTime().toString()));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        attendance.setCellValueFactory(new PropertyValueFactory<>("description"));

        refreshEventTable();
    }

    private void refreshUserTable() throws SQLException {
        userTable.setItems(manageEventsModel.getAllUsers());
    }

    public void refreshEventTable() throws SQLException {
        manageEventsModel.refreshData();
        coordinatorTableView.refresh();
        coordinatorTableView.setItems(manageEventsModel.getAllEvents());
    }

    public void handleCreateEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("com/project/gui/view/NewEditEvent.fxml"));
        Parent root = loader.load();


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

    public void handleDeleteButton(ActionEvent event) throws SQLException {
        Event selectedEvent = getSelectedEvent();
        if (selectedEvent==null)
            return;
        try {
            manageEventsModel.tryToDeleteEvent(selectedEvent);
        } catch (UserException e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText(e.getExceptionMessage());
            ButtonType continueButton = new ButtonType("Continue");
            ButtonType cancelButton = new ButtonType("Cancel");
            alert.getButtonTypes().setAll(continueButton,cancelButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == continueButton)
                manageEventsModel.deleteEvent(selectedEvent);
        }
        refreshEventTable();
    }

    public void handleEditButton(ActionEvent event) throws IOException {
        Event selectedEvent = getSelectedEvent();
        if (selectedEvent==null)
            return;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("com/project/gui/view/EditEventView.fxml"));
        Parent root = loader.load();


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

    public void handleBuyTicket(ActionEvent actionEvent) throws SQLException, IOException, WriterException {
        Event selectedEvent = getSelectedEvent();
        Customer selectedCustomer = getSelectedCustomer();
        TicketType selectedTicketType = getSelectedTicketType();

        if (selectedEvent!=null && selectedCustomer!=null && selectedTicketType!=null)
            buyTicket(selectedEvent,selectedCustomer,selectedTicketType,selectedEvent);
    }

    private void buyTicket(Event selectedEvent, Customer selectedCustomer, TicketType selectedTicketType, Event event) throws SQLException, IOException, WriterException {
        Ticket createdTicket = manageEventsModel.buyTicketForUser(selectedEvent,selectedTicketType,selectedCustomer);

        if (createdTicket!=null)
            printTicket(createdTicket,selectedCustomer,selectedTicketType,event);
        else
            System.out.println("tickettype sold out");
        //TODO handle tickets ran out
    }

    private void printTicket(Ticket createdTicket, Customer selectedCustomer, TicketType selectedTicketType, Event selectedEvent) throws IOException, WriterException {
        PrinterJob printer = PrinterJob.createPrinterJob();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/TicketRedesign.fxml"));
        loader.load();

        TicketController ticketController = loader.getController();
        ticketController.setFields(createdTicket,selectedCustomer,selectedTicketType,selectedEvent);
        HBox rootNode = ticketController.getRoot();

        if (printer!=null){
            printer.showPrintDialog(ticketTypeList.getScene().getWindow());
            printer.printPage(rootNode);
            printer.endJob();
        }


    }

    private TicketType getSelectedTicketType() {
        return ticketTypeList.getSelectionModel().getSelectedItem();
    }

    private Customer getSelectedCustomer() {
        return userTable.getSelectionModel().getSelectedItem();
    }

    public void searchUser(KeyEvent keyEvent) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        for (Customer customer : manageEventsModel.getAllUsers()) {
            if (customer.toString().toLowerCase(Locale.ROOT).contains(userSearchField.getText().toLowerCase(Locale.ROOT)))
                customers.add(customer);
        }
        userTable.setItems(FXCollections.observableArrayList(customers));
    }

    public void searchEvent(KeyEvent keyEvent) throws SQLException {
        List<Event> events = new ArrayList<>();
        for (Event event : manageEventsModel.getAllEvents()) {
            if (event.toString().toLowerCase(Locale.ROOT).contains(eventSearchField.getText().toLowerCase(Locale.ROOT)))
                events.add(event);
        }
        coordinatorTableView.setItems(FXCollections.observableArrayList(events));
    }

}

