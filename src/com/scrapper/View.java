package com.scrapper;

import javax.swing.*;
import java.awt.*;

public class View {


    public View() {
        // Create a jFrame and display the panel in it

        JFrame frame = new JFrame("BishopList");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Set up the content pane.
        addPaneComponents(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void addPaneComponents(Container pane){

        /*
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        */

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        /*
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        */

        JLabel programName = new JLabel("Bishop List");
        programName.setFont(new Font("Century",Font.BOLD,14));
        c.insets = new Insets(5,0,0,0);  //top padding
        c.gridx = 1;
        c.gridy= 0;
        c.fill = GridBagConstraints.CENTER;
        c.gridwidth = 3;
        pane.add(programName,c);

        JTextArea instructions = new JTextArea("Welcome to Bishop List. Please \nselect the Bishop Master List by \nclicking on the Open File Button. \nThen press Run.");
        Color color = pane.getBackground();
        instructions.setFont(new Font("Century",Font.PLAIN,12));
        instructions.setEditable(false);
        instructions.setBackground(color);
        c.insets = new Insets(0,10,0,0);  //top padding
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        pane.add(instructions,c);

        JLabel piclabel = new JLabel();
        piclabel.setIcon(new ImageIcon(pane.getClass().getResource("/resources/images/RenewTree.png")));
        piclabel.setPreferredSize(new Dimension(100,79));
        c.fill = GridBagConstraints.LINE_END;
        c.weightx = 0.0;
        c.insets = new Insets(10,10,10,10);  //top padding
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 3;
        c.gridy = 1;
        pane.add(piclabel,c);

        JButton openFile = new JButton("Open File...");
        openFile.setActionCommand("Open");
        c.fill = GridBagConstraints.CENTER;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        // c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 2;   //2 columns wide
        c.gridy = 2;       //third row
        pane.add(openFile, c);

        JLabel messageLable = new JLabel("");
        messageLable.setFont(new Font("Century",Font.PLAIN,13));
        messageLable.setForeground(Color.RED);
        c.insets = new Insets(0,0,0,10);  //top padding
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;
        pane.add(messageLable,c);

        //Add action Listener to openFile
        Controller openController = new Controller(messageLable);
        openFile.addActionListener(openController);

        JButton runButton = new JButton("Run");
        runButton.setActionCommand("run");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(5,0,0,0);  //top padding
        c.gridx = 3;       //aligned with button 2
        c.gridwidth = 1;   //2 columns wide
        c.gridy = 4;       //third row
        pane.add(runButton, c);


        // Add actionListener to runButton
        runButton.addActionListener(openController);
    }
}
