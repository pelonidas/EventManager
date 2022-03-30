package com.project.gui.controller;

import com.google.zxing.WriterException;
import com.project.be.Customer;
import com.project.be.Event;
import com.project.be.Ticket;
import com.project.be.TicketType;
import com.project.gui.model.TicketModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class TicketController {

    @FXML
    private HBox root;
    @FXML
    private Label eventNameLabel;

    @FXML
    private Label admissionTypeLabel;

    @FXML
    private Label customerNameLabel;

    @FXML
    private ImageView qrImageView;

    @FXML
    private Label eventDateLabel;

    @FXML
    private Label eventTimeLabel;

    @FXML
    private Label eventNameLabelMini;

    @FXML
    private Label benefitsLabel;

    @FXML
    private ImageView barcodeView;

    TicketModel model;

    public TicketController(){
        model = new TicketModel();
    }


    public void setFields(Ticket createdTicket, Customer selectedCustomer, TicketType selectedTicketType, Event selectedEvent) throws WriterException {
        eventNameLabel.setText(selectedEvent.getTitle());
        eventNameLabelMini.setText(selectedEvent.getTitle());
        eventDateLabel.setText(selectedEvent.getDateAndTime().toString());//idk about this one
        eventTimeLabel.setText(selectedEvent.getDateAndTime().toString());

        admissionTypeLabel.setText(selectedTicketType.getTitle());
        benefitsLabel.setText(selectedTicketType.getBenefits());

        customerNameLabel.setText(selectedCustomer.getFirstName() + " " + selectedCustomer.getLastName());

        qrImageView.setImage(model.getQRCode(createdTicket));
        barcodeView.setImage(model.getBarCode(createdTicket));
    }

    public HBox getRoot() {
        return root;
    }
}
