package com.project.gui.model;

import com.google.zxing.WriterException;
import com.project.be.Customer;
import com.project.be.Event;
import com.project.be.Ticket;
import com.project.be.User;
import com.project.bll.EventManager;
import com.project.bll.IEventManager;
import com.project.bll.util.BarCodeGen;
import com.project.dal.facadeDAO.IDALFacade;
import javafx.scene.image.Image;

import java.io.IOException;
import java.sql.SQLException;

public class TicketModel {

    BarCodeGen codeGen;

    IEventManager manager;

    public TicketModel() throws IOException {
        codeGen = new BarCodeGen();
        manager = EventManager.getInstance();
    }

    public Image getRandomBarCode() throws WriterException {
        return codeGen.generateRandomBarCode();
    }

    public Image getRandomQRCode() throws WriterException {
        return codeGen.generateRandomQRCode();
    }

    public Ticket getTicket(Customer selectedUser, Event selectedEvent) throws SQLException {
        return manager.getTicket(selectedUser,selectedEvent);
    }
}
