package com.project.gui.controller;

import com.project.be.TicketType;
import com.project.bll.util.DateTimeConverter;
import com.project.gui.model.EditEventModel;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EditEventController implements Initializable {

    @FXML
    private ComboBox hoursBox;
    @FXML
    private ComboBox minutesBox;
    @FXML
    private TextField ticketBenefitsTxt;
    @FXML
    private TextField eventCapacityTxt;
    @FXML
    private TextField ticketPriceTxt;
    @FXML
    private VBox box;

    @FXML
    private TextField eventTitleTxt;

    @FXML
    private DatePicker eventDate;

    @FXML
    private Spinner<?> eventDurationTxt;

    @FXML
    private VBox box1;

    @FXML
    private TextField eventLocationTxt;

    @FXML
    private TextField eventNotesTxt;

    @FXML
    private TextField ticketTypeTxt;


    @FXML
    private VBox ticketManagementButtons;

    @FXML
    private ListView<TicketType> ticketTypeList;


    private EditEventModel model;


    public EditEventController() throws IOException {
        model = new EditEventModel();
    }


    @FXML
    void backView(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initIcons();
        initValidators();
        initTimeBoxes();
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
        //TODO setup validators for both time field and price field
    }

    private void initIcons() {
        Text addTypeButton = GlyphsDude.createIcon(FontAwesomeIcons.ARROW_RIGHT,"24");
        addTypeButton.setOnMouseClicked(addTicketType);

        //addTypeButton.setFill(Paint.valueOf("BLACK"));

        Text deleteTypeButton = GlyphsDude.createIcon(FontAwesomeIcons.TRASH,"24");
        deleteTypeButton.setOnMouseClicked(deleteTicketType);


        ticketManagementButtons.getChildren().add(addTypeButton);
        ticketManagementButtons.getChildren().add(deleteTypeButton);
    }

    EventHandler addTicketType = new EventHandler() {
        @Override
        public void handle(Event event) {
            if (ticketTypeTxt.getText().isBlank() || ticketBenefitsTxt.getText().isBlank() || ticketPriceTxt.getText().isBlank())
                return;
            String ticketTypeName = ticketTypeTxt.getText();
            double ticketTypePrice = Double.parseDouble(ticketPriceTxt.getText());
            String ticketTypeBenefits = ticketBenefitsTxt.getText();
            int seatsAvailable = 10;

            ticketTypeList.getItems().add(new TicketType(1,ticketTypeName,ticketTypeBenefits,ticketTypePrice,seatsAvailable));
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


    public void handleSaveEvent(ActionEvent actionEvent) throws SQLException {
        String date = eventDate.getValue().toString();

        String hours = hoursBox.getSelectionModel().getSelectedItem().toString();
        String minutes = minutesBox.getSelectionModel().getSelectedItem().toString();

        String time = hours+":"+minutes;
        System.out.println(time);

        String eventTitle = eventTitleTxt.getText();
        Date dateAndTime = model.parse_convertDateTime(date + " " + time);
        Integer capacity = Integer.parseInt(eventCapacityTxt.getText());
        String location = eventLocationTxt.getText();
        String description = eventNotesTxt.getText();
        List<TicketType> ticketTypes = ticketTypeList.getItems();

        model.createEvent(eventTitle,dateAndTime,location,description,capacity,ticketTypes);
    }
}
