package com.project.bll.util;

import com.github.sarxos.webcam.Webcam;
import com.project.be.Ticket;
import com.project.dal.TicketDAO;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


@SuppressWarnings("serial")
public class WebcamQRCodeExample2 extends JFrame {

    Ticket ticket;

    public WebcamQRCodeExample2() {

        setTitle("Main frame");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JButton(new AbstractAction("CAPTURE QR") {

            @Override
            public void actionPerformed(ActionEvent e) {
                final Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try (QrCapture qr = new QrCapture(Webcam.getDefault())) {
                            try {
                                TicketDAO ticketDAO = new TicketDAO();
                                 ticket = ticketDAO.getTicket(qr.getResult());
                            } catch (SQLException | IOException e) {
                                e.printStackTrace();
                            }
                            if (ticket!=null){
                                if (ticket.isValid()){
                                    System.out.println("valid ticket, customer="+ticket.getCustomer().getFirstName()+" "+ticket.getCustomer().getLastName()+", event: "+ticket.getEvent().getTitle()+", ticket type: "+ticket.getTicketType().getTitle());
                                    new WebcamQRCodeExample2();
                                }else {System.out.println("ticket not valid anymore"+ticket.getCustomer().getFirstName()+" "+ticket.getCustomer().getLastName()+", event: "+ticket.getEvent().getTitle());
                           new WebcamQRCodeExample2();     }
                            }else {System.out.println("not valid qr code");
                            new WebcamQRCodeExample2();}

                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    };
                });
                thread.setDaemon(true);
                thread.start();
            }
        }));

        pack();
        setVisible(true);
    }

    private void showMessage(String text) {
        JOptionPane.showMessageDialog(null, text);
    }

    public static void main(String[] args) {
        new WebcamQRCodeExample2();
    }
}