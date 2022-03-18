package com.project.bll.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class BarCodeGen {

    private final int QR_SIZE = 50;
    private final int BARCODE_HEIGHT = 50;
    private final int BARCODE_WIDTH = 120;


    public Image generateQRCode(String information) throws WriterException {
        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrWriter.encode(information, BarcodeFormat.QR_CODE,QR_SIZE,QR_SIZE);

        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return SwingFXUtils.toFXImage(qrImage,null);
    }

    public Image generateBarCode(String information) throws WriterException {
        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrWriter.encode(information, BarcodeFormat.CODE_128,BARCODE_WIDTH,BARCODE_HEIGHT);

        BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return SwingFXUtils.toFXImage(barcodeImage,null);
    }
}
