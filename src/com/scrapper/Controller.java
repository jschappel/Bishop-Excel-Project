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

    private Boolean isFileSelected = false;

    private JButton button;
    private JLabel messageLable;
    private JFrame frame;

    Controller(JButton button, JLabel messageLable){
        super();
        this.button = button;
        this.messageLable = messageLable;
    }

    Controller(JButton button, JLabel messageLable, JFrame frame){
        this.button = button;
        this.messageLable = messageLable;
        this.frame = frame;
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


            int returnValue = openFileChooser.showOpenDialog(frame);

            if (returnValue == JFileChooser.APPROVE_OPTION){


                try{
                    BufferedImage ob = ImageIO.read(openFileChooser.getSelectedFile());
                    messageLable.setText("Success");
                    messageLable.setForeground(Color.decode("#08753f"));
                    isFileSelected = true;

                    //File filePath = openFileChooser.getCurrentDirectory();
                    //path.setText(filePath.toString());
                } catch (IOException ioe){
                    messageLable.setForeground(Color.RED);
                    messageLable.setText("Failed");
                    isFileSelected = false;
                }
            } else {
                isFileSelected = false;
                messageLable.setForeground(Color.RED);
                messageLable.setText("No File Chosen");
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

            } catch (IOException io) {
                System.out.print("There is an error with the Website please retry later.");
                io.printStackTrace();
            }

        } else if(arg.equals("run") && !isFileSelected) {
            messageLable.setForeground(Color.RED);
            messageLable.setText("No File Chosen");
        }

    }
}
