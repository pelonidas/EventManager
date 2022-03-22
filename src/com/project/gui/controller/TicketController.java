package com.project.gui.controller;

import com.google.zxing.WriterException;
import com.project.be.Event;
import com.project.gui.model.TicketModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TicketController {

    @FXML
    private HBox mainPane;
    @FXML
    private ImageView qrCodeView;

    @FXML
    private Label eventNameLabel;

    @FXML
    private Label eventLocationLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private ImageView barcodeView;

    private TicketModel model;

    public TicketController(){
        model = new TicketModel();
    }

    public void setRandomCodes() throws WriterException {
        barcodeView.setImage(model.getRandomBarCode());
        qrCodeView.setImage(model.getRandomQRCode());
    }

    public void setFields(Event selectedEvent) {
        dateLabel.setText(selectedEvent.getDateAndTime().toString());
        eventNameLabel.setText(selectedEvent.getTitle());
        eventLocationLabel.setText(selectedEvent.getLocation());

    }

    public HBox getRoot() {
        return mainPane;
    }
}
