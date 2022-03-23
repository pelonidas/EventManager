package com.project.gui.controller;

import com.google.zxing.WriterException;
import com.project.be.Customer;
import com.project.be.Event;
import com.project.be.Ticket;
import com.project.be.User;
import com.project.gui.model.TicketModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.SQLException;

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

    public TicketController() throws IOException {
        model = new TicketModel();
    }

    public void setRandomCodes() throws WriterException {
        barcodeView.setImage(model.getRandomBarCode());
        qrCodeView.setImage(model.getRandomQRCode());
    }


    //TODO make change the actualy ticket
    public void setFields(Customer selectedUser, Event selectedEvent) throws SQLException {
        Ticket ticket = model.getTicket(selectedUser,selectedEvent);
    }

    public HBox getRoot() {
        return mainPane;
    }
}
