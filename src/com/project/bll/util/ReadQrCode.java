package com.project.bll.util;


import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadQrCode {

    public static void main(String[] args) throws Exception {
        System.out.println(Webcam.getWebcams().size());
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        ImageIO.write(webcam.getImage(), "PNG", new File("hello-world.png"));
    }

    public String readQrCode(String qrCodePath) throws IOException, NotFoundException {
        BufferedImage bf = ImageIO.read(new FileInputStream(qrCodePath));
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bf)));
        return new MultiFormatReader().decode(bitmap).getText();

    }
}
