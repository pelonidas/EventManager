package com.project.bll.util;

import com.google.zxing.WriterException;
import com.project.be.Ticket;
import com.project.gui.controller.TicketController;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendMailOutlook {
    public void captureAndSaveDisplay(Ticket ticket) throws IOException, WriterException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/TicketRedesign.fxml"));
        loader.load();

        TicketController ticketController = loader.getController();
        ticketController.setFields(ticket,ticket.getCustomer(),ticket.getTicketType(),ticket.getEvent());
        HBox rootNode = ticketController.getRoot();


        String fileName = "resources/TempTickets/"+ticket.getId()+".png";
        File file = new File(fileName);


                //Pad the capture area
                WritableImage writableImage = new WritableImage((int)rootNode.getWidth() + 20,
                        (int)rootNode.getHeight() + 20);
                rootNode.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                //Write the snapshot to the chosen file
                ImageIO.write(renderedImage, "png", file);

    }

    private String getOutlook() {
        try {
            Process p = Runtime.getRuntime()
                    .exec(new String[]{"cmd.exe", "/c", "assoc", ".pst"});
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String extensionType = input.readLine();
            input.close();
            // extract type
            if (extensionType == null) {
                outlookNotFoundMessage("File type PST not associated with Outlook.");
            } else {
                String[] fileType = extensionType.split("=");

                p = Runtime.getRuntime().exec(
                        new String[]{"cmd.exe", "/c", "ftype", fileType[1]});
                input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String fileAssociation = input.readLine();
                // extract path
                Pattern pattern = Pattern.compile("\".*?\"");
                Matcher m = pattern.matcher(fileAssociation);
                if (m.find()) {
                    String outlookPath = m.group(0);
                    System.out.println(outlookPath);
                    return outlookPath;
                } else {
                    outlookNotFoundMessage("Error parsing PST file association");
                }
            }

        } catch (Exception err) {
            err.printStackTrace();
            outlookNotFoundMessage(err.getMessage());
        }
        return null;
    }

    private static void outlookNotFoundMessage(String errorMessage) {
        System.out.println("Could not find Outlook: \n" + errorMessage);
    }

    public void openOutlook(Ticket ticket)
    {
        String outlook = getOutlook();
        Runtime rt = Runtime.getRuntime();

        try {
            String attachment = "resources/TempTickets/"+ticket.getId()+".png";
            String subject = "Ticket%20Email"; //%20 is used in place of a space
            String email = "cchesberg@gmail.com"; //participant.getEmail();
            String emailSubjectCombined = email+"?subject="+subject;
            File file = new File(attachment);
            rt.exec(new String[]{"cmd.exe","/c", outlook, "/m", emailSubjectCombined, "/a", file.getAbsolutePath()});

        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
