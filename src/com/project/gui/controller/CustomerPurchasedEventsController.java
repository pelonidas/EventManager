package com.project.gui.controller;

import com.google.zxing.WriterException;
import com.project.be.Admin;
import com.project.be.Customer;
import com.project.be.Event;
import com.project.be.User;
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
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Nothing here is done properly i will redo this when i have time but it's enough for the review
 */

public class CustomerPurchasedEventsController implements Initializable {

    @FXML
    private HBox mainPane;
    @FXML
    private TableView<User> eventsTable;
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

    private Event event;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initIcons();
        initTable();

    }




    //TODO make actually work
    private void initTable() {
        Date date = DateTimeConverter.parse_convertDateTime("2002-09-21 21:30");

        User user = new Customer(1,"Amine","Aouina","Silamin","Poop","easv@Amin.com",date);
        User user1 = new Customer(2,"Oliver","Souc","Olly","Password","Mail@Mail.de",date);
        User user2 = new Customer(3,"Renars","Mednieks","Renaldinho","123","Mail@Bussiness.com",date);

        event = new Event(2,"event",date,"NEw York","Description");

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("email"));


        eventsTable.getItems().setAll(new User[]{user,user1,user2});
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
        public void handle(javafx.event.Event actionEvent) {
            User selectedUser = eventsTable.getSelectionModel().getSelectedItem();
            if(selectedUser==null)
                return;
            try {
                openTicket(selectedUser,event);
            } catch (IOException | WriterException | SQLException e) {
                e.printStackTrace();
            }
        }
    };

    private void openTicket(User selectedUser,Event event) throws IOException, WriterException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Ticket.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        TicketController ticketController = loader.getController();
        ticketController.setRandomCodes();
        ticketController.setFields(((Customer) selectedUser),event);

        stage.show();
    }

    EventHandler printTicketAction = new EventHandler() {
        @Override
        public void handle(javafx.event.Event actionEvent) {
            User selectedUser = eventsTable.getSelectionModel().getSelectedItem();
            if(selectedUser==null)
                return;
            try {
                printTicket(((Customer) selectedUser),event);
            } catch (IOException | WriterException | SQLException e) {
                e.printStackTrace();

        }
    };

    private void printTicket(Customer selectedUser,Event selectedEvent) throws IOException, WriterException, SQLException {
        PrinterJob printer = PrinterJob.createPrinterJob();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Ticket.fxml"));
        Parent root = loader.load();

        TicketController ticketController = loader.getController();
        ticketController.setFields(selectedUser, selectedEvent);
        ticketController.setRandomCodes();
        HBox rootNode = ticketController.getRoot();

        if (printer!=null){
            printer.showPrintDialog(mainPane.getScene().getWindow());
            printer.printPage(rootNode);
            printer.endJob();
        }
    }

};

}
