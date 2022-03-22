package com.project.bll.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.UUID;

public class BarCodeGen {

    private final int QR_SIZE = 300;
    private final int BARCODE_HEIGHT = 200;
    private final int BARCODE_WIDTH = 5;


    public Image generateRandomQRCode() throws WriterException {
        String uniqueID = UUID.randomUUID().toString();

        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrWriter.encode(uniqueID, BarcodeFormat.QR_CODE,QR_SIZE,QR_SIZE);

        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return SwingFXUtils.toFXImage(qrImage,null);
    }

    public Image generateRandomBarCode() throws WriterException {
        String uniqueID = UUID.randomUUID().toString();

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(uniqueID, BarcodeFormat.CODE_39,BARCODE_WIDTH,BARCODE_HEIGHT);

        BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return SwingFXUtils.toFXImage(barcodeImage,null);
    }

    public Image generateBarCode(String information) throws WriterException {

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(information, BarcodeFormat.CODE_39,BARCODE_WIDTH,BARCODE_HEIGHT);

        BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return SwingFXUtils.toFXImage(barcodeImage,null);

    }

    public Image generateQRCode(String information) throws WriterException {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = multiFormatWriter.encode(information, BarcodeFormat.QR_CODE,QR_SIZE,QR_SIZE);

        BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return SwingFXUtils.toFXImage(barcodeImage,null);
    }
}
