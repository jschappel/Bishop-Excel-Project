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

public class Main extends JFrame implements ActionListener {

    private final static boolean shouldFill = true;
    private final static boolean shouldWeightX = true;
    private final static boolean RIGHT_TO_LEFT = false;
    private final JFileChooser openFileChooser;
    private Boolean isFileSelected = false;
    private JFrame frame;
    private JLabel messageLable;
    private BufferedImage ob;

    private Main() {
        // Make the fileChooser feel more at home
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        openFileChooser = new JFileChooser();
        openFileChooser.setCurrentDirectory(new File("c:\\temp"));
        openFileChooser.setFileFilter(new FileNameExtensionFilter("xlsx", "xlsx","xlsm","xlsb","xls","xlt"));

        //Create and set up the window.
        frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
        //frame.setSize(500,400);

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }


    public static void main (String[] args) throws IOException {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
                //createAndShowGUI();
            }
        });





        String webpage = "http://www.usccb.org/about/bishops-and-dioceses/all-dioceses.cfm";
        Sort sort = new Sort(webpage);
        sort.findAttributes();

    /*
        ArrayList<Dioceses> dioceseList = sort.returnDioceseObjectList();


        // Create a spreadsheet
       XSSFWorkbook workbook = new XSSFWorkbook();
       XSSFSheet spreadsheet = workbook.createSheet("Mock Bishop List");

        // Loop over the Diocese list and create a HashTable
        for (Dioceses obj : dioceseList) {

        }
    */


    }

    private void addComponentsToPane(Container pane) {

        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        JButton button;
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        JLabel programName = new JLabel("Bishop List");
        programName.setFont(new Font("Century",Font.BOLD,14));
        c.insets = new Insets(5,0,0,0);  //top padding
        c.gridx = 1;
        c.gridy= 0;
        c.fill = GridBagConstraints.CENTER;
        c.gridwidth = 3;
        pane.add(programName,c);

        JTextArea instructions = new JTextArea("Welcome to Bishop List. Please \nselect the Bishop Master List by \nclicking on the Choose File Button. \nThen press Run.");
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

        button = new JButton("Open File...");
        button.setActionCommand("Open");
        button.addActionListener(this);
        c.fill = GridBagConstraints.CENTER;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
       // c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridx = 0;       //aligned with button 2
        c.gridwidth = 2;   //2 columns wide
        c.gridy = 2;       //third row
        pane.add(button, c);


        messageLable = new JLabel("");
        messageLable.setFont(new Font("Century",Font.PLAIN,13));
        messageLable.setForeground(Color.RED);
        c.insets = new Insets(0,0,0,10);  //top padding
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;
        pane.add(messageLable,c);

     /*
        path = new JTextArea("ASDDDdDDDDDDDDDddddddkdkdkdkdsljfalksdjfa;klfj;alksdjfa;lksjf");
        path.setBackground(color);
        path.setEditable(true);
        path.setPreferredSize(new Dimension(60,30));
        JScrollPane scrollPane = new JScrollPane(path);
        c.fill = GridBagConstraints.CENTER;
        c.insets = new Insets(10,10,15,0);  //top padding
        c.gridx = 0;
        c.gridy = 3;
        pane.add(scrollPane,c);
    */

        JButton runButton = new JButton("Run");
        runButton.setActionCommand("run");
        runButton.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(5,0,0,0);  //top padding
        c.gridx = 3;       //aligned with button 2
        c.gridwidth = 1;   //2 columns wide
        c.gridy = 4;       //third row
        pane.add(runButton, c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String arg = e.getActionCommand();
        if(arg.equals("Open")){
            int returnValue = openFileChooser.showOpenDialog(frame);

            if (returnValue == JFileChooser.APPROVE_OPTION){
                try{
                    ob = ImageIO.read(openFileChooser.getSelectedFile());
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
        } else if(arg.equals("run") && !isFileSelected) {
            messageLable.setForeground(Color.RED);
            messageLable.setText("No File Chosen");
        }

    }
}
