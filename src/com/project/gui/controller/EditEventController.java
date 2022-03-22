package com.project.gui.controller;

import com.project.be.TicketType;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.swing.event.ChangeEvent;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class EditEventController implements Initializable {

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
    private TextField eventTime;

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

    @FXML
    void backView(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initIcons();
        initValidators();

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



}
