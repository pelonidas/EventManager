package com.project.gui.model;

import com.google.zxing.WriterException;
import com.project.bll.util.BarCodeGen;
import javafx.scene.image.Image;

public class TicketModel {

    BarCodeGen codeGen;

    public TicketModel(){
        codeGen = new BarCodeGen();
    }

    public Image getRandomBarCode() throws WriterException {
        return codeGen.generateRandomBarCode();
    }

    public Image getRandomQRCode() throws WriterException {
        return codeGen.generateRandomQRCode();
    }
}
