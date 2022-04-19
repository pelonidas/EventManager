package com.project.gui.model;

import com.google.zxing.WriterException;
import com.project.be.Ticket;
import com.project.bll.EventManager;
import com.project.bll.IEventManager;
import com.project.bll.util.BarCodeGen;
import javafx.scene.image.Image;

import java.io.IOException;
import java.sql.SQLException;

public class TicketModel {
    IEventManager manager;

    BarCodeGen codeGen;
    private static TicketModel ticketModel = null;


    private TicketModel() throws IOException {

        codeGen = new BarCodeGen();
        manager = EventManager.getInstance();
    }

    public Image getRandomBarCode() throws WriterException {
        return codeGen.generateRandomBarCode();
    }

    public Image getRandomQRCode() throws WriterException {
        return codeGen.generateRandomQRCode();
    }

    public Image getQRCode(Ticket createdTicket) throws WriterException {
        String code = createdTicket.getCode();
        return codeGen.generateQRCode(code);
    }

    public Image getBarCode(Ticket createdTicket) throws WriterException {
        String code = createdTicket.getCode();
        return codeGen.generateBarCode(code);
    }
    public static TicketModel getInstance() throws IOException, SQLException {
        if (ticketModel == null)
            ticketModel = new TicketModel();

        return ticketModel;
    }
    public Ticket getTicket(String qrCode) throws SQLException {
      return manager.getTicket(qrCode);
    }

    public void updateTicket(Ticket ticket) throws SQLException {
         manager.updateTicket(ticket);
    }
}
