package com.project.gui.controller;


import com.project.be.TicketType;
import com.project.gui.model.ManageEventsModel;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
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

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CreateEventViewController implements Initializable {
    @FXML
    private TextField ticketTypeCount;
    @FXML
    private ComboBox hoursBox, minutesBox;
    @FXML
    private TextField ticketBenefitsTxt, eventCapacityTxt, ticketPriceTxt, eventTitleTxt, eventLocationTxt, eventNotesTxt, ticketTypeTxt;

    @FXML
    private DatePicker eventDate;

    @FXML
    private VBox ticketManagementButtons;

    @FXML
    private ListView<TicketType> ticketTypeList;

    private ManageEventsModel manageEventsModel;
    CoordinatorController coordinatorController;


    public CreateEventViewController() {
        /**manageEventsModel = new ManageEventsModel();
        /model = new EditEventModel();*/
    }
    public void setCoordinatorController(CoordinatorController coordinatorController){
        this.coordinatorController = coordinatorController;
    }

    public void setManageEventsModel(ManageEventsModel manageEventsModel) {
        this.manageEventsModel = manageEventsModel;
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
     */
    private void initValidators() {
        eventCapacityTxt.setTextFormatter(intFormatter);
        ticketPriceTxt.setTextFormatter(intFormatter1);
        ticketTypeCount.setTextFormatter(intFormatter2);
        //ticketTypeTxt.setTextFormatter(ticketTypeFormatter);
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
        public void handle(javafx.event.Event event) {
            if (ticketTypeTxt.getText().isBlank() || ticketBenefitsTxt.getText().isBlank() || ticketPriceTxt.getText().isBlank() || ticketTypeCount.getText().isBlank())
                return;
            String ticketTypeName = ticketTypeTxt.getText();
            double ticketTypePrice = Double.parseDouble(ticketPriceTxt.getText());
            String ticketTypeBenefits = ticketBenefitsTxt.getText();
            int seatsAvailable = Integer.parseInt(ticketTypeCount.getText());

            eventCapacityTxt.setText(String.valueOf(manageEventsModel.getTotalSeatCount(ticketTypeList.getItems(),seatsAvailable)));

            ticketTypeList.getItems().add(new TicketType(0,ticketTypeName,ticketTypeBenefits,ticketTypePrice,seatsAvailable));

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

    TextFormatter intFormatter2 = new TextFormatter<Object>(change -> {
        if (change.getText().matches("[0-9]*"))
            return change;
        return null;
    });


    public void handleSaveEvent(ActionEvent actionEvent) throws SQLException {
        if (eventDate.getValue()==null || hoursBox.getSelectionModel().getSelectedItem() == null
            || minutesBox.getSelectionModel().getSelectedItem() == null || eventCapacityTxt.getText().isEmpty()
            ||eventLocationTxt.getText().isEmpty() || eventNotesTxt.getText().isEmpty() || eventTitleTxt.getText().isEmpty()
            || ticketTypeList.getItems().isEmpty())
            return;
        String date = eventDate.getValue().toString();

        String hours = hoursBox.getSelectionModel().getSelectedItem().toString();
        String minutes = minutesBox.getSelectionModel().getSelectedItem().toString();

        String time = hours+":"+minutes;

        String eventTitle = eventTitleTxt.getText();
        Date dateAndTime = manageEventsModel.parse_convertDateTime(date + " " + time);
        Integer capacity = Integer.parseInt(eventCapacityTxt.getText());
        String location = eventLocationTxt.getText();
        String description = eventNotesTxt.getText();
        List<TicketType> ticketTypes = ticketTypeList.getItems();

        com.project.be.Event newEvent = manageEventsModel.createEvent(eventTitle,dateAndTime,location,description,capacity,ticketTypes);
        coordinatorController.getAllEvents().add(newEvent);
        coordinatorController.initializeEventTable();
        manageEventsModel.getAllEvents().add(newEvent);


        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void backView(ActionEvent actionEvent) {
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