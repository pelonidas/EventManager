package com.project.bll.util;
import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.project.be.Ticket;
import com.project.dal.TicketDAO;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class CamTest extends Application {

    private WebCamService service ;
    private Webcam cam;
    private Result result;
    Ticket ticket;
    JFXComboBox<Webcam> jfxComboBox;

    public CamTest() throws IOException {
    }

    @Override
    public void init() throws IOException {
        jfxComboBox = new JFXComboBox<>();
        jfxComboBox.setPromptText("Select your camera");
        jfxComboBox.getItems().addAll(Webcam.getWebcams());
        jfxComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cam = jfxComboBox.getSelectionModel().getSelectedItem();
            }
        });
        cam = Webcam.getDefault();
        service = new WebCamService(cam);
    }

    @Override
    public void start(Stage primaryStage) {

        JFXButton startStop = new JFXButton();
        startStop.textProperty().bind(Bindings.
                when(service.runningProperty()).
                then("Stop").
                otherwise("Start"));

        startStop.setOnAction(e -> {
            if (service.isRunning()) {
                service.cancel();
            } else {
                service.restart();
            }
        });

        WebCamView view = new WebCamView(service);
        ButtonBar buttonBar = new ButtonBar();
        JFXButton scan_qr_code = new JFXButton("Scan qr code");


        buttonBar.getButtons().add(jfxComboBox);
        buttonBar.getButtons().add(startStop);
        buttonBar.getButtons().add(scan_qr_code);

        scan_qr_code.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Path currentRelativePath = Paths.get("");
                String path = currentRelativePath.toAbsolutePath().toString();
                path= path+ "QrCode.jpg";
                if (cam.isOpen()){
                    try {
                        ImageIO.write(cam.getImage(),"JPG",new File(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    BufferedImage bf = ImageIO.read(new FileInputStream(path));
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bf)));
                    result = new MultiFormatReader().decode(bitmap);
                }catch (Exception ignored){}
                System.out.println(result.getText());
                try {
                    TicketDAO ticketDAO = new TicketDAO();
                    ticket = ticketDAO.getTicket(result.getText());
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
                if (ticket!=null){
                    if (ticket.isValid()){
                        System.out.println("valid ticket, customer="+ticket.getCustomer().getFirstName()+" "+ticket.getCustomer().getLastName()+", event: "+ticket.getEvent().getTitle()+", ticket type: "+ticket.getTicketType().getTitle());
                    }else System.out.println("ticket not valid anymore"+ticket.getCustomer().getFirstName()+" "+ticket.getCustomer().getLastName()+", event: "+ticket.getEvent().getTitle());
                }else System.out.println("not valid qr code");
                result=null;
            }
        });


        BorderPane root = new BorderPane(view.getView());
        BorderPane.setAlignment(startStop, Pos.CENTER);
        BorderPane.setMargin(buttonBar, new Insets(5));
        root.setBottom(buttonBar);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
