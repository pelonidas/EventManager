package com.project.gui.controller;

import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

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
        initIcons();



    }

    private void initIcons() {
        Text moveRightIcon = GlyphsDude.createIcon(FontAwesomeIcons.ARROW_RIGHT,"24");

        //moveRightIcon.setFill(Paint.valueOf("BLACK"));


        Text deleteRightIcon = GlyphsDude.createIcon(FontAwesomeIcons.TRASH,"24");

        ticketManagementButtons.getChildren().add(moveRightIcon);
        ticketManagementButtons.getChildren().add(deleteRightIcon);
    }
}
