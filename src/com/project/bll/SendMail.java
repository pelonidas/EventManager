package com.project.bll;

import com.project.be.Ticket;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SendMail {
    AnchorPane anchorPane;
    Ticket ticketSold;

    public void captureAndSaveDisplay(){
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

        //Prompt user to select a file
        //File file = fileChooser.showSaveDialog(null);

        String fileName = "resources/TempTickets/"+ticketSold.getId()+".png";
        File file = new File(fileName);
        if(file != null){
            try {
                //Pad the capture area
                WritableImage writableImage = new WritableImage((int)anchorPane.getWidth() + 20,
                        (int)anchorPane.getHeight() + 20);
                anchorPane.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                //Write the snapshot to the chosen file
                ImageIO.write(renderedImage, "png", file);

            } catch (IOException ex) { ex.printStackTrace(); }
        }
    }
    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane=anchorPane;
    }

    public String getOutlook() {
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

    public void openOutlook()
    {
        String outlook = getOutlook();
        Runtime rt = Runtime.getRuntime();

        try {
            String attachment = "resources/TempTickets/"+ticketSold.getId()+".png";
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
