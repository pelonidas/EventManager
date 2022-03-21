package com.project.gui.controller;

import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class EditEventController implements Initializable {

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
    private TextArea eventNotesTxt;

    @FXML
    private TextField ticketTypeTxt;

    @FXML
    private TextArea ticketBenefitsTxt;

    @FXML
    private VBox ticketManagementButtons;

    @FXML
    private ListView<?> ticketTypeList;

    @FXML
    void backView(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Node moveRightButton = GlyphsDude.createIconButton(FontAwesomeIcons.ARROW_RIGHT);
        ticketManagementButtons.getChildren().add(moveRightButton);
    }
}
