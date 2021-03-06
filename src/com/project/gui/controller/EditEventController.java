package com.project.gui.controller;

import com.project.be.TicketType;
import com.project.bll.exceptions.UserException;
import com.project.gui.model.ManageEventsModel;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class EditEventController implements Initializable {

    @FXML
    private TextField ticketTypeCount;
    @FXML
    private ComboBox hoursBox, minutesBox;
    @FXML
    private TextField ticketBenefitsTxt, eventCapacityTxt, ticketPriceTxt, eventTitleTxt, eventLocationTxt, eventNotesTxt, ticketTypeTxt;

    @FXML
    private DatePicker eventDate;

    @FXML
    private Spinner<?> eventDurationTxt;
    
    @FXML
    private VBox ticketManagementButtons;

    @FXML
    private ListView<TicketType> ticketTypeList;


    private ManageEventsModel model;
    private com.project.be.Event thisEvent;
    CoordinatorController coordinatorController;

    public EditEventController() throws IOException{
    }

    public void setModel(ManageEventsModel model) {
        this.model = model;
    }

    public void setCoordinatorController(CoordinatorController coordinatorController){
        this.coordinatorController = coordinatorController;
    }
    public void setEventToBeUpdated(com.project.be.Event e) throws SQLException {
        this.thisEvent = e;
        eventTitleTxt.setText(e.getTitle());
        eventCapacityTxt.setText(String.valueOf(e.getSeatsAvailable()));
        eventLocationTxt.setText(e.getLocation());
        eventNotesTxt.setText(e.getDescription());

        String[] splitDateTime = e.getDateAndTime().toString().split(" ");
        String[] splitTime = splitDateTime[1].split(":");
        hoursBox.setValue(splitTime[0]);
        minutesBox.setValue(splitTime[1]);

        eventDate.setValue(model.parseDate(splitDateTime[0]));

        ObservableList<TicketType> allTicketTypes;
        allTicketTypes = FXCollections.observableArrayList();
        allTicketTypes.addAll(e.getAllTicketTypes());
        ticketTypeList.setItems(allTicketTypes);
        ticketTypeList.setItems(allTicketTypes);
    }

    @FXML
    void backView(ActionEvent event) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initIcons();
        initValidators();
        initTimeBoxes();
        setLimitsDatePicker(eventDate);
    }

    private void initTimeBoxes() {
        /*Integer[] hoursOfTheDay = new Integer[24];
        Integer[] minutesInAnHour = new Integer[60];
        for (int i = 0; i < hoursOfTheDay.length; i++) {
            hoursOfTheDay[i] = i;
        }
        for (int i = 0; i < minutesInAnHour.length ; i++) {
            minutesInAnHour[i] = i;
        }
        hoursBox.getItems().setAll(hoursOfTheDay);
        minutesBox.getItems().setAll(minutesInAnHour);
         */

        String[] hoursOfTheDay = {"00","01","02","03","04","05","06","07",
                                  "08","09","10","11","12","13","14","15","16"
                                  ,"17","18","19","20","21","22","23"};
        String[] minutesInAnHour = {"00","01","02","03","04","05","06","07","08"
                                    ,"09","10","11","12","13","14","15","16","17"
                                    ,"18","19","20","21","22","23","24","25","26",
                                    "27","28","29","30","31","32","33","34","35",
                                    "36","37","38","39","40","41","42","43","44",
                                    "45","46","47","48","49","50","51","52","53",
                                    "54","55","56","57","58","59"};

        hoursBox.getItems().setAll(hoursOfTheDay);
        minutesBox.getItems().setAll(minutesInAnHour);

    }


    /**
     * Initializes input validators for different fields to check if the correct value is input
     * Also sets up event handlers
     */
    private void initValidators() {
        eventCapacityTxt.setTextFormatter(intFormatter);
        //ticketPriceTxt.setTextFormatter(priceFormatter);
        //ticketTypeCount.setTextFormatter(intFormatter1);

        ticketTypeList.setOnMouseClicked(selectTicketType);
        //TODO setup validators for both time field and price field
    }

    private void initIcons() {
        Text addTypeButton = GlyphsDude.createIcon(FontAwesomeIcons.ARROW_RIGHT,"24");
        addTypeButton.setOnMouseClicked(addTicketType);

        Text editTypeButton = GlyphsDude.createIcon(FontAwesomeIcons.PENCIL_SQUARE,"24");
        editTypeButton.setOnMouseClicked(editTicketType);

        //addTypeButton.setFill(Paint.valueOf("BLACK"));

        Text deleteTypeButton = GlyphsDude.createIcon(FontAwesomeIcons.TRASH,"24");
        deleteTypeButton.setOnMouseClicked(deleteTicketType);


        ticketManagementButtons.getChildren().add(addTypeButton);
        ticketManagementButtons.getChildren().add(deleteTypeButton);
        ticketManagementButtons.getChildren().add(editTypeButton);
    }

    EventHandler addTicketType = new EventHandler() {
        @Override
        public void handle(Event event) {
            if (ticketTypeTxt.getText().isBlank() || ticketBenefitsTxt.getText().isBlank() || ticketPriceTxt.getText().isBlank() || ticketTypeCount.getText().isBlank())
                return;
            String ticketTypeName = ticketTypeTxt.getText();
            double ticketTypePrice = Double.parseDouble(ticketPriceTxt.getText());
            String ticketTypeBenefits = ticketBenefitsTxt.getText();
            int seatsAvailable = Integer.parseInt(ticketTypeCount.getText());

            eventCapacityTxt.setText(String.valueOf(model.getTotalSeatCount(ticketTypeList.getItems(),seatsAvailable)));

            ticketTypeList.getItems().add(new TicketType(0,ticketTypeName,ticketTypeBenefits,ticketTypePrice,seatsAvailable));
        }
    };

    EventHandler deleteTicketType = new EventHandler() {
        @Override
        public void handle(Event event) {
            int selectedIndex = ticketTypeList.getSelectionModel().getSelectedIndex();
            if (selectedIndex==-1)
                return;
            ticketTypeList.getItems().remove(selectedIndex);
        }
    };

    EventHandler editTicketType = new EventHandler() {
        @Override
        public void handle(Event event) {
            TicketType selectedTicketType = ticketTypeList.getSelectionModel().getSelectedItem();
            if (ticketTypeTxt.getText().isBlank() || ticketBenefitsTxt.getText().isBlank() ||
                    ticketPriceTxt.getText().isBlank() || ticketTypeCount.getText().isBlank() || selectedTicketType == null)
                return;
            selectedTicketType.setTitle(ticketTypeTxt.getText());
            selectedTicketType.setBenefits(ticketBenefitsTxt.getText());
            selectedTicketType.setPrice(Double.parseDouble(ticketPriceTxt.getText()));
            selectedTicketType.setSeatsAvailable(Integer.parseInt(ticketTypeCount.getText()));

            eventCapacityTxt.setText(String.valueOf(model.getTotalSeatCount(ticketTypeList.getItems(),0)));
        }
    };

    EventHandler selectTicketType = new EventHandler() {
        @Override
        public void handle(Event event) {
            TicketType selectedTicketType = ticketTypeList.getSelectionModel().getSelectedItem();
            if (selectedTicketType==null)
                return;
            ticketTypeTxt.setText(selectedTicketType.getTitle());
            ticketPriceTxt.setText(String.valueOf(selectedTicketType.getPrice()));
            ticketTypeCount.setText(String.valueOf(selectedTicketType.getSeatsAvailable()));
            ticketBenefitsTxt.setText(selectedTicketType.getBenefits());
        }
    };

    TextFormatter intFormatter = new TextFormatter<Object>(change -> {
        if (change.getText().matches("[0-9]*"))
            return change;
        return null;
    });

    TextFormatter intFormatter1 = new TextFormatter<Object>(change -> {
        if (change.getText().matches("[0-9]*"))
            return change;
        return null;
    });

    TextFormatter priceFormatter = new TextFormatter<Object>(change -> {
        if (change.getText().matches("\\d[\\d\\,\\.]+"))
            return change;
        return null;
    });


    //TODO handle user exception
    public void handleSaveEvent(ActionEvent actionEvent) throws Exception, UserException {
        String date = eventDate.getValue().toString();

        String hours = hoursBox.getSelectionModel().getSelectedItem().toString();
        String minutes = minutesBox.getSelectionModel().getSelectedItem().toString();

        String time = hours+":"+minutes;

        String eventTitle = eventTitleTxt.getText();
        LocalDateTime localDateTime = model.parseDateTime(date + " " + time);
        int capacity = Integer.parseInt(eventCapacityTxt.getText());
        String location = eventLocationTxt.getText();
        String description = eventNotesTxt.getText();
        List<TicketType> ticketTypes = ticketTypeList.getItems();

        for (com.project.be.Event event : coordinatorController.getAllEvents()){
            if (event.equals(thisEvent)){
                event.setTitle(eventTitle);
                event.setSeatsAvailable(capacity);
                event.setLocation(location);
                event.setDescription(description);
                event.setDateAndTime(Timestamp.valueOf(localDateTime));

                model.updateEvent(event, ticketTypes);

                event.setAllTicketTypes(model.getTicketTypesForEvent(event));

                coordinatorController.initializeEventTable();
                coordinatorController.updateDetails(thisEvent);
            }
        }

        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    private void setLimitsDatePicker(DatePicker datePicker) {
        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(empty || item.compareTo(LocalDate.now()) < 0);
                    }
                };
            }
        });
    }
}
