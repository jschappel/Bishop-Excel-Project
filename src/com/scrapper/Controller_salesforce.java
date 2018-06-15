package com.scrapper;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Controller_salesforce {

    //  Views
    public VBox vbox;
    public MenuBar menuBar;

    //  Buttons
    public Button runButton;
    public Button fileButton;

    //  Labels
    public Label status;

    //  Files
    private File file;

    //  Booleans
    private boolean fileLoded = false;


    //  IO Exception if not file found
    public void runProgram() throws IOException {

        //  If the file was successfully loaded
        if (fileLoded){
            ArrayList<String> sheetList = ExcelCompare.getSheets(file);
            AlertBox box = new AlertBox();
            box.sheetDisplay("Advanced Options",sheetList,"SalesforceStyle.css");


            Task<Void> task = new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    updateMessage("Please wait (Scraping Website)");
                    BishopList bishopList = Sort.findAttributes();


                    // See which ExcelCompare constructor we are going to use
                    if(box.getSheet().equals("Default")) {
                        ExcelCompare fileCompare = new ExcelCompare(file);
                        updateMessage("Please wait (Comparing Data)");

                        fileCompare.compareAndWrite_salesforce(fileCompare.cloneSheet(), bishopList);
                        fileCompare.fileClose();
                        updateMessage("Success. There were " + fileCompare.getChanges() + " changes");
                    }
                    else {
                        ExcelCompare fileCompare = new ExcelCompare(file,box.getSheet());
                        updateMessage("Please wait (Comparing Data)");

                        fileCompare.compareAndWrite_salesforce(fileCompare.cloneSheet(), bishopList);
                        fileCompare.fileClose();
                        updateMessage("Success. There were " + fileCompare.getChanges() + " changes");
                    }
                    return null;
                }

            };

            task.messageProperty().addListener((observable, oldValue, newValue) -> status.setText(newValue));
            new Thread(task).start();

            task.setOnSucceeded(e -> {
                status.textProperty().unbind();
                runButton.setDisable(true);
            });
        }

        //  Just run button was pressed
        else {
            AlertBox.alertDisplay("Error","Please select a file before continuing.","SalesforceStyle.css");
        }
    }

    public void chooseFile(){
            FileChooser fc = new FileChooser();
            fc.setTitle("Open Files Dialog");
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsm","*.xlsx","*xlsb","*xls","*xlt"));
            Stage stage = (Stage)vbox.getScene().getWindow();
            file = fc.showOpenDialog(stage);

            if (file != null){
                fileLoded = true;
                status.setText("File was successfully loaded");
                status.setTextFill(Color.valueOf("#01C76C"));
        }
    }


    /**
     * Handles switch to the Salesforce UI
     * @throws IOException if te FXMLLoader can not get the menu.fxml resource file
     */
    public void chooseProgram() throws IOException {

        Stage primStage = (Stage) menuBar.getScene().getWindow();
        Parent salesforceScene = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Scene scene = new Scene(salesforceScene);
        scene.getStylesheets().add("RenewStyle.css");
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