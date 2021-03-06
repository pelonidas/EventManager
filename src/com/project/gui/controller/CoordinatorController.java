package com.project.gui.controller;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.WriterException;
import com.jfoenix.controls.JFXComboBox;
import com.project.be.Customer;
import com.project.be.Event;
import com.project.be.Ticket;
import com.project.be.TicketType;
import com.project.bll.util.SendMailOutlook;
import com.project.bll.exceptions.UserException;
import com.project.bll.util.QrCapture;
import com.project.gui.model.CustomerModel;
import com.project.gui.model.ManageEventsModel;
import com.project.gui.model.TicketModel;
import com.project.gui.view.Main;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class CoordinatorController implements Initializable {
    @FXML
    private JFXComboBox<Webcam> selectCamera;
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
    private Ticket ticket;
    private TicketModel ticketModel;


    public CoordinatorController() throws IOException, SQLException {
        manageEventsModel = ManageEventsModel.getInstance();
        customerModel = CustomerModel.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeParticipantTable();
        initializeEventTable();
        initializeUserTable();
        setupFilters();
        /*selectCamera.getItems().addAll(Webcam.getWebcams());
        selectCamera.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scanQrCode();
            }});*/
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

//    private void initIcons() {
//        Text addTypeButton = GlyphsDude.createIcon(FontAwesomeIcons.REFRESH,"24");
//        addTypeButton.setOnMouseClicked(refreshData);
//
//        buttonBox.getChildren().add(addTypeButton);
//    }

    /*EventHandler refreshData = new EventHandler() {
        @Override
        public void handle(javafx.event.Event event) {
            try {
                manageEventsModel.refreshData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };*/

    public void initializeUserTable()  {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        userTable.setItems(allCustomers);
    }

    public void initializeEventTable()  {
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
        ObservableList<Customer> allParticipants;
        allParticipants = FXCollections.observableArrayList();
        if(e.getParticipants()!=null)
            allParticipants.addAll(e.getParticipants());
        participantTable.setItems(allParticipants);
    }

    private void loadEventTickets(Event e) {
        ObservableList<TicketType> allTicketTypes;
        allTicketTypes = FXCollections.observableArrayList();
        if (e.getAllTicketTypes()!=null)
            allTicketTypes.addAll(e.getAllTicketTypes());
        ticketTypeList.setItems(allTicketTypes);
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

        if (selectedEvent!=null && selectedCustomer!=null && selectedTicketType!=null){
            Ticket ticket = buyTicket(selectedEvent,selectedCustomer,selectedTicketType,selectedEvent);
            sendMail(ticket);
        }
    }

    private void sendMail(Ticket ticket) throws IOException, WriterException {
        SendMailOutlook sendMail = new SendMailOutlook();
        sendMail.captureAndSaveDisplay(ticket);
        sendMail.openOutlook(ticket);
    }

    private Ticket buyTicket(Event selectedEvent, Customer selectedCustomer, TicketType selectedTicketType, Event event) throws SQLException, IOException, WriterException {
        HBox rootNode=null;
        Ticket createdTicket = manageEventsModel.buyTicketForUser(selectedEvent,selectedTicketType,selectedCustomer);

        if (createdTicket!=null)
            rootNode = printTicket(createdTicket,selectedCustomer,selectedTicketType,event);
        else
            System.out.println("ticketType sold out");
        return ticket;
        //TODO handle tickets ran out
    }

    private HBox printTicket(Ticket createdTicket, Customer selectedCustomer, TicketType selectedTicketType, Event selectedEvent) throws IOException, WriterException {
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
        return rootNode;

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

    private void scanQrCode() {
        Alert alert;
        final Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try (QrCapture qr = new QrCapture(selectCamera.getSelectionModel().getSelectedItem())) {
                    try {
                        TicketModel ticketModel = TicketModel.getInstance();
                        ticket = ticketModel.getTicket(qr.getResult());
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                    if (ticket!=null){
                        if (ticket.isValid()){
                            ticket.setValid(false);
                            Thread threadUpdate = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        ticketModel.updateTicket(ticket);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            threadUpdate.start();

                            /**alert = new Alert(Alert.AlertType.NONE);
                            alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                            alert.setGraphic((GlyphsDude.createIcon(FontAwesomeIcons.CERTIFICATE)));
                            alert.setTitle("Valid ticket");
                            alert.setHeaderText(ticket.getCustomer().getFirstName()+" "+ticket.getCustomer().getLastName());
                            alert.setContentText("Event: "+ticket.getEvent().getTitle()+", ticket type: "+ticket.getTicketType().getTitle());*/

                            System.out.println("Valid ticket:\n"+"Customer "+ ticket.getCustomer().getFirstName()+" "+ticket.getCustomer().getLastName()+"\nEvent: "+ticket.getEvent().getTitle()+", ticket type: "+ticket.getTicketType().getTitle());
                        }else{
                            /**alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error dialog");
                            alert.setHeaderText("Qr code is used");
                            alert.setContentText("Ticket is already sold");
                            alert.showAndWait();*/
                            System.out.println("Ticket is already sold:\n"+"Customer "+ ticket.getCustomer().getFirstName()+" "+ticket.getCustomer().getLastName()+"\nEvent: "+ticket.getEvent().getTitle()+", ticket type: "+ticket.getTicketType().getTitle());
                        }
                    }else {
                        /**Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error dialog");
                        alert.setHeaderText("Invalid qr code");
                        alert.setContentText("Ticket not available");
                        alert.showAndWait();*/
                        System.out.println("Invalid qr code:\n"+"Customer "+ ticket.getCustomer().getFirstName()+" "+ticket.getCustomer().getLastName()+"\nEvent: "+ticket.getEvent().getTitle()+", ticket type: "+ticket.getTicketType().getTitle());

                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            };
        });
        thread.setDaemon(true);
        thread.start();
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

