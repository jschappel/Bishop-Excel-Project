package com.scrapper;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controller implements ActionListener {

    private Boolean isFileSelected = true;

    private JLabel messageLabel;

    // open button, run button
    Controller(JLabel messageLable){
        super();
        this.messageLabel = messageLable;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String arg = e.getActionCommand();

        if(arg.equals("Open")){

            // Make the fileChooser feel more at home
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //set up the File Chooser
            JFileChooser openFileChooser = new JFileChooser();
            openFileChooser.setCurrentDirectory(new File("c:\\temp"));
            openFileChooser.setAcceptAllFileFilterUsed(false);
            openFileChooser.setFileFilter(new FileNameExtensionFilter("Excel Spreadsheet", "xlsx","xlsm","xlsb","xls","xlt"));

            int returnValue = openFileChooser.showOpenDialog(new Frame());

            if (returnValue == JFileChooser.APPROVE_OPTION){

                try{
                    BufferedImage ob = ImageIO.read(openFileChooser.getSelectedFile());
                    messageLabel.setText("Success");
                    messageLabel.setForeground(Color.decode("#08753f"));
                    isFileSelected = true;

                    ExcelWrite write = new ExcelWrite();
                    try {
                        write.extract();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } catch (IOException ioe){
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Failed");
                    isFileSelected = false;
                }
            } else {
                isFileSelected = false;
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("No File Chosen");
            }
        }
        if (arg.equals("run") && isFileSelected){
            System.out.println("Working");

            try{
                //TODO
                // Shrink this code to be more readable
                String webpage = "http://www.usccb.org/about/bishops-and-dioceses/all-dioceses.cfm";
                Sort sort = new Sort(webpage);
                sort.findAttributes();

                System.out.println("Finished");

            } catch (IOException io) {
                System.out.print("There is an error with the Website please retry later.");
                io.printStackTrace();
            }

        } else if(arg.equals("run") && !isFileSelected) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("No File Chosen");
        }

    }
}
