package com.project.gui.controller;

import com.google.zxing.WriterException;
import com.project.be.Event;
import com.project.bll.util.DateTimeConverter;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Nothing here is done properly i will redo this when i have time but it's enough for the review
 */

public class CustomerPurchasedEventsController implements Initializable {

    @FXML
    private HBox mainPane;
    @FXML
    private TableView<Event> eventsTable;
    @FXML
    private VBox buttonPane;
    @FXML
    private TableColumn titleColumn;
    @FXML
    private TableColumn dateColumn;
    @FXML
    private TableColumn locationColumn;
    @FXML
    private TableColumn capacityColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initIcons();
        initTable();
    }


    //TODO make actually work
    private void initTable() {
        Date date = DateTimeConverter.parse_convertDateTime("2002-09-21 21:30");
        Event event1 = new Event(1,"Concert",date,"1600 Pennsylvania Avenue NW","Micheal Jackson concert",200);
        Event event2 = new Event(1,"Theater",date,"1681 Broadway New York City","Premiere of Cinderella",1000);
        Event event3 = new Event(1,"Festival",date,"Belgium","A little festival in belgium",20000);

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("seatsAvailable"));

        eventsTable.getItems().setAll(new Event[]{event1,event2,event3});
    }

    private void initIcons() {
        Text viewTicketIcon = GlyphsDude.createIcon(FontAwesomeIcons.EYE,"48");
        buttonPane.getChildren().add(viewTicketIcon);
        viewTicketIcon.setOnMouseClicked(viewTicketAction);


        Text printTicketIcon = GlyphsDude.createIcon(FontAwesomeIcons.PRINT,"48");
        buttonPane.getChildren().add(printTicketIcon);
        printTicketIcon.setOnMouseClicked(printTicketAction);

        Text logoutIcon = GlyphsDude.createIcon(FontAwesomeIcons.SIGN_OUT,"48");
        buttonPane.getChildren().add(logoutIcon);

    }



    EventHandler viewTicketAction = new EventHandler() {
        @Override
        public void handle(javafx.event.Event event) {
            Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
            if(selectedEvent==null)
                return;
            try {
                openTicket(selectedEvent);
            } catch (IOException | WriterException e) {
                e.printStackTrace();
            }
        }
    };

    private void openTicket(Event selectedEvent) throws IOException, WriterException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Ticket.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        TicketController ticketController = loader.getController();
        //ticketController.setFields(selectedEvent);

        stage.show();
    }

    EventHandler printTicketAction = new EventHandler() {
        @Override
        public void handle(javafx.event.Event event) {
            Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
            if(selectedEvent==null)
                return;
            try {
                printTicket(selectedEvent);
            } catch (IOException | WriterException e) {
                e.printStackTrace();

        }
    };

    private void printTicket(Event selectedEvent) throws IOException, WriterException {
        PrinterJob printer = PrinterJob.createPrinterJob();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Ticket.fxml"));
        Parent root = loader.load();

        TicketController ticketController = loader.getController();
        //.setFields(selectedEvent);
        //ticketController.setRandomCodes();
        HBox rootNode = ticketController.getRoot();

        if (printer!=null){
            printer.showPrintDialog(mainPane.getScene().getWindow());
            printer.printPage(rootNode);
            printer.endJob();
        }
    }

};
}
