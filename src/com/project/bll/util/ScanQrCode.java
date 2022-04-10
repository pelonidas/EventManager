package com.project.bll.util;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.project.dal.CustomerDAO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class ScanQrCode {


    public static void main(String[] args) throws Exception {
        BufferedImage bf = ImageIO.read(new FileInputStream("src/com/project/zebnafelimeyhebnawelimeyhebnazebnafih.png"));
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bf)));
       Result result = new MultiFormatReader().decode(bitmap);
       System.out.println(result.getText());
    }}
