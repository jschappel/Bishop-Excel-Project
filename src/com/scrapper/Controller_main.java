package com.scrapper;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Controller_main {

    public File file;
    public Boolean wasSelected = false;

    // fxml Buttons
    public RadioButton radioChoice;
    public Button fileButton;
    public Button runButton;
    public VBox vbox;
    public Label progressID;
    public MenuItem main_salesforce;
    public MenuBar menu_bar;

    /**
     * Opens a user specified file
     */
    public void chooseFile(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Open File Dialog");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsm","*.xlsx","*xlsb","*xls","*xlt"));
        Stage stage = (Stage)vbox.getScene().getWindow();
        file = fc.showOpenDialog(stage);

        if (file != null){
            wasSelected = true;
            progressID.setText("File was successfully loaded");
            progressID.setTextFill(Color.valueOf("#01C76C"));
        }
    }

    /**
     * Returns a file as a user specified location. This file is not SAVEED
     * Look in ExcelWrite for where the file is saved
     * @return A File at the chosen location
     */
    private static File directoryChooser() {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose location To Save Report");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File selectedFile = null;
        while(selectedFile== null){
            selectedFile = chooser.showSaveDialog(null);
        }
        File file = new File(String.valueOf(selectedFile));
        return file;
    }

    /**
     * OnClick action for the "Run Button" This function handles all of the possible run cases.
     * @throws IOException when creating ExcelCompare object if the workbook can not be created
     * in the file
     * @throws NoSuchMethodException when dealing with the excelCompare
     */
    public void runProgram() throws IOException {


        //check to see if the radio button was pressed and not "choose file"
        if(!wasSelected && radioChoice.isSelected()) {
            runButton.setDisable(true);

            //File file = directoryChooser();
            File file = directoryChooser();

            Task <Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

                    updateMessage("Please wait (Many take a moment)");
                    ExcelWrite excelWrite = new ExcelWrite();
                    updateMessage("Please wait (Creating new Excel file)");
                    excelWrite.run(file);
                    return null;
                }
            };

            task.messageProperty().addListener((observable, oldValue, newValue) -> progressID.setText(newValue));
            new Thread(task).start();

            // java 8 construct, replace with java 7 code if using java 7.
            task.setOnSucceeded(e -> {
                progressID.textProperty().unbind();
                runButton.setDisable(false);
                // this message will be seen.
                progressID.setText("The file was successfully created");
            });
        }

        // If just the "choose file" option happened or both options were pressed
        else if(wasSelected && !radioChoice.isSelected() || wasSelected && radioChoice.isSelected()) {

            ArrayList<String> sheetList = ExcelCompare.getSheets(file);
            AlertBox box = new AlertBox();
            box.sheetDisplay("Advanced Options",sheetList,"RenewStyle.css");

            Task <Void> task = new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    updateMessage("Please wait (Scraping Website)");
                    BishopList bishopList = Sort.findAttributes();


                    // See which ExcelCompare constructor we are going to use
                    if(box.getSheet().equals("Default")) {
                        ExcelCompare fileCompare = new ExcelCompare(file);
                        updateMessage("Please wait (Comparing Data)");

                        fileCompare.compareAndWrite(fileCompare.cloneSheet(), bishopList);
                        fileCompare.fileClose();
                        updateMessage("Success. There were " + fileCompare.getChanges() + " changes");
                    }
                    else {
                        ExcelCompare fileCompare = new ExcelCompare(file,box.getSheet());
                        updateMessage("Please wait (Comparing Data)");

                        fileCompare.compareAndWrite(fileCompare.cloneSheet(), bishopList);
                        fileCompare.fileClose();
                        updateMessage("Success. There were " + fileCompare.getChanges() + " changes");
                    }
                    return null;
                }

            };

            task.messageProperty().addListener((observable, oldValue, newValue) -> progressID.setText(newValue));
            new Thread(task).start();


            // java 8 construct, replace with java 7 code if using java 7.
            task.setOnSucceeded(e -> {
                progressID.textProperty().unbind();
                runButton.setDisable(false);
            });
        }

        //Neither cases happened
        else {
            AlertBox.alertDisplay("Error","Please choose a file or check box before continuing.","RenewStyle.css");
        }
    }

    /**
     * Handles switch to the Salesforce UI
     * @throws IOException if te FXMLLoader can not get the salesforce_menu resource file
     */
    public void chooseProgram() throws IOException {
        Stage primStage = (Stage) menu_bar.getScene().getWindow();
        Parent salesforceScene = FXMLLoader.load(getClass().getResource("Salesforce_menu.fxml"));
        Scene scene = new Scene(salesforceScene);
        scene.getStylesheets().add("SalesforceStyle.css");
        primStage.setScene(scene);
    }

    /**
     * Opens the U.S.C.C.B website when called
     */
    public void openWeb() {
        try {
            Desktop.getDesktop().browse(new URI("http://www.usccb.org/about/bishops-and-dioceses/all-dioceses.cfm"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}