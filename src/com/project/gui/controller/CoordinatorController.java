package com.project.gui.controller;

import com.google.zxing.WriterException;
import com.project.be.Customer;
import com.project.be.Event;
import com.project.be.Ticket;
import com.project.be.TicketType;
import com.project.bll.exceptions.UserException;
import com.project.bll.util.CamTest;
import com.project.bll.util.DateTimeConverter;
import com.project.gui.model.CustomerModel;
import com.project.gui.model.ManageEventsModel;
import com.project.gui.view.Main;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.*;
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
    private TextField eventSearchFilter;
    @FXML
    private TextField allCustomersFilter;
    @FXML
    private TextField participantsFilter;

    @FXML
    private TableView<Customer> participantTable;
    @FXML
    private TableColumn participantName;
    @FXML
    private TableColumn participantSurname;
    @FXML
    private HBox buttonBox;
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
    @FXML
    private TextArea detailsTextarea;
    private Main main;
    private ObservableList<Customer> allCustomers;
    private ObservableList<Event> allEvents;

    private ManageEventsModel manageEventsModel;
    private CustomerModel customerModel;

    public CoordinatorController() throws IOException, SQLException, UserException {
        manageEventsModel = new ManageEventsModel();
        customerModel = new CustomerModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeParticipantTable();
        initIcons();
        try {
            initializeEventTable();
            initializeUserTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setupFilters();
    }

    private void setupFilters() {
        allCustomersFilter.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                ObservableList<Customer>search = FXCollections.observableArrayList();
                    for(Customer customer : getAllCustomers()){
                        if (customer.getFirstName().toLowerCase().contains(allCustomersFilter.getText().toLowerCase(Locale.ROOT))||customer.getLastName().toLowerCase(Locale.ROOT).contains(allCustomersFilter.getText().toLowerCase(Locale.ROOT))||String.valueOf(customer.getPhoneNumber()).contains(allCustomersFilter.getText())||customer.getEmail().toLowerCase().contains(allCustomersFilter.getText().toLowerCase(Locale.ROOT)))
                            search.add(customer);}

                userTable.setItems(search);
            }
        });

        participantsFilter.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Event selectedEvent = coordinatorTableView.getSelectionModel().getSelectedItem();
                if (selectedEvent==null)
                    return;
                ObservableList<Customer>search = FXCollections.observableArrayList();
                for(Customer customer : selectedEvent.getParticipants()){
                    if (customer.getFirstName().toLowerCase().contains(participantsFilter.getText().toLowerCase(Locale.ROOT))||customer.getLastName().toLowerCase(Locale.ROOT).contains(participantsFilter.getText().toLowerCase(Locale.ROOT))||String.valueOf(customer.getPhoneNumber()).contains(participantsFilter.getText())||customer.getEmail().toLowerCase().contains(participantsFilter.getText().toLowerCase(Locale.ROOT)))
                        search.add(customer);}
                participantTable.setItems(search);
            }
        });

        eventSearchFilter.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                ObservableList<Event>search = FXCollections.observableArrayList();
                    for(Event event1 : getAllEvents() ){
                        if (event1.getTitle().contains(eventSearchFilter.getText().toLowerCase(Locale.ROOT))||event1.getLocation().toLowerCase().contains(eventSearchFilter.getText().toLowerCase(Locale.ROOT))||event1.getDateAndTime().toString().contains(eventSearchFilter.getText().toLowerCase(Locale.ROOT)))
                            search.add(event1);}

                coordinatorTableView.setItems(search);
            }
        });
    }

    private void initializeParticipantTable() {
        participantName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        participantSurname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
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

    public void initializeUserTable() throws SQLException {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        userTable.setItems(allCustomers);
    }

    public void initializeEventTable() throws SQLException {
        name.setCellValueFactory(new PropertyValueFactory<>("title"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        attendance.setCellValueFactory(new PropertyValueFactory<>("description"));

        coordinatorTableView.setItems(getAllEvents());
        coordinatorTableView.refresh();

    }

    public void handleCreateEvent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("com/project/gui/view/NewEditEvent.fxml"));
        Parent root = loader.load();


        CreateEventViewController alertDialogController = loader.getController();
        alertDialogController.setCoordinatorController(this);
        alertDialogController.setManageEventsModel(manageEventsModel);

        Stage stage = new Stage();
        stage.setTitle("New/Edit Event");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleManageButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ManageUsersCoord.fxml"));
        Parent root =  loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }



    private void loadEventParticipants(Event e) throws Exception {
        participantTable.setItems(manageEventsModel.getParticipantsForEvent(e));
    }

    private void loadEventTickets(Event e) throws SQLException {
        ticketTypeList.setItems(manageEventsModel.getTicketTypesForEvent(e));
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

    public void handleDeleteButton(ActionEvent event) throws Exception {
        Event selectedEvent = getSelectedEvent();
        if (selectedEvent==null)
            return;
        int index = coordinatorTableView.getSelectionModel().getSelectedIndex();
        try {
            manageEventsModel.tryToDeleteEvent(selectedEvent);
            getAllEvents().remove(selectedEvent);
        } catch (UserException e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setHeaderText(e.getExceptionMessage());
            ButtonType continueButton = new ButtonType("Continue");
            ButtonType cancelButton = new ButtonType("Cancel");
            alert.getButtonTypes().setAll(continueButton,cancelButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == continueButton){
                manageEventsModel.deleteEvent(selectedEvent);
                getAllEvents().remove(selectedEvent);
            }
        }
        initializeEventTable();
        if (index > 0) {
             selectedEvent = coordinatorTableView.getItems().get(index - 1);
            updateEventInfo(selectedEvent);
            loadEventTickets(selectedEvent);
            loadEventParticipants(selectedEvent);
        } else
            participantTable.getItems().clear();
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
        editEventController.setModel(manageEventsModel);

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

        PrinterAttributes pa = printer.getPrinter().getPrinterAttributes();
        Paper paper = pa.getDefaultPaper();

        PageLayout pageLayout = printer.getPrinter().createPageLayout(paper,PageOrientation.PORTRAIT,0,0,0,0);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/TicketRedesign.fxml"));
        loader.load();

        TicketController ticketController = loader.getController();
        ticketController.setFields(createdTicket,selectedCustomer,selectedTicketType,selectedEvent);
        HBox rootNode = ticketController.getRoot();

        if (printer!=null){
            printer.showPrintDialog(ticketTypeList.getScene().getWindow());
            printer.printPage(pageLayout,rootNode);
            printer.endJob();
        }


    }

    private TicketType getSelectedTicketType() {
        return ticketTypeList.getSelectionModel().getSelectedItem();
    }

    private Customer getSelectedCustomer() {
        return userTable.getSelectionModel().getSelectedItem();
    }

    public void setMain(Main main) {
        this.main=main;
    }

    public void handleSelectEvent(MouseEvent mouseEvent) throws Exception {
        Event e = getSelectedEvent();
        if (e==null)
            return;
        updateEventInfo(e);
        loadEventTickets(e);
        loadEventParticipants(e);
    }

    @FXML
    private void handleScanQrCode(ActionEvent actionEvent) throws IOException {
        CamTest camTest = new CamTest();
        camTest.start(new Stage());
    }

    @FXML
    private void handleLogOut(ActionEvent actionEvent) throws Exception {
        main.initLogin();
    }

    public ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public void setAllCustomers(ObservableList<Customer> allCustomers) {
        this.allCustomers = allCustomers;
    }

    public ObservableList<Event> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(ObservableList<Event> allEvents) {
        this.allEvents = allEvents;
    }

    public void updateDetails(Event e) throws Exception {
        updateEventInfo(e);
        loadEventTickets(e);
        loadEventParticipants(e);
    }
}

