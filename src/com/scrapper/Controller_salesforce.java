package com.scrapper;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.scrapper.Bishop.*;

public class Controller_salesforce {

    //  Views
    public VBox vbox;
    public MenuBar menuBar;

    //  Buttons
    public Button runButton;
    public Button fileButton;

    //  Menu Items
    public Menu sort_menu;
    public MenuItem current_sort;

    //  Labels
    public Label status;

    //  Files
    private File file;

    //  Booleans
    private boolean fileLoded = false;

    //  Comparators
    private Comparator<Bishop> currentCompare = COMPARE_BY_BISH_LAST;    //  Default compare is by lastName

    @SuppressWarnings("Duplicates")
    public void changeCurrentOption(ActionEvent actionEvent) {
        CheckMenuItem item = (CheckMenuItem) actionEvent.getSource();
        String id = item.getId();

        switch (id){
            case "full_dio_menu":{
                setSelected("full_dio_menu");
                currentCompare = COMPARE_BY_DIO_FULL;
                System.out.println("Now doing Diocese Full Name Comparison");
                break;
            }

            case "short_dio_menu":{
                setSelected("short_dio_menu");
                currentCompare = COMPARE_BY_DIO_SHORT;
                System.out.println("Now doing Diocese Short Name Comparison");
                break;
            }

            case "city_dio_menu":{
                setSelected("city_dio_menu");
                currentCompare = COMPARE_BY_DIO_CITY;
                System.out.println("Now doing Diocese City Comparison");
                break;
            }

            case "state_dio_menu":{
                setSelected("state_dio_menu");
                currentCompare = COMPARE_BY_DIO_STATE;
                System.out.println("Now doing Diocese State Comparison");
                break;
            }

            case "first_bish_menu":{
                setSelected("first_bish_menu");
                currentCompare = COMPARE_BY_BISH_FIRST;
                System.out.println("Now doing First Comparison");
                break;
            }

            case "last_bish_menu":{
                setSelected("last_bish_menu");
                currentCompare = COMPARE_BY_BISH_LAST;
                System.out.println("Now doing Last Comparison");
                break;
            }

            case "title_bish_menu": {
                setSelected("title_bish_menu");
                currentCompare = COMPARE_BY_BISH_TITLE;
                System.out.println("Now doing Title Comparison");
                break;
            }
        }
    }

    /**
     * Places a check mark next to the current selected box. Removes the previous mark
     * @param newSelection the id name of the item you wish to select
     */
    @SuppressWarnings("Duplicates")
    private void setSelected(String newSelection){
        int outterSize = 2;
        String currentSelection = "";
        for(int i = 0; i < outterSize; i++){
            Menu innerMenu = (Menu) sort_menu.getItems().get(i);
            int innerSize = innerMenu.getItems().size();
            for (int j = 0; j < innerSize; j++){
                CheckMenuItem item = (CheckMenuItem) innerMenu.getItems().get(j);
                if (item.getId().equals(newSelection)) {
                    String text = item.getText();
                    currentSelection = text;
                    item.setSelected(true);
                }
                else
                    item.setSelected(false);
            }
        }

        // Set the text of the current Sort menu item
        current_sort.setText("Current Sort: " + currentSelection);
    }


    //  IO Exception if not file found
    public void runProgram() throws IOException {

        //  If the file was successfully loaded
        if (fileLoded) {
            runButton.setDisable(true);

            ArrayList<String> sheetList = ExcelCompare.getSheets(file);
            AlertBox box = new AlertBox();
            box.sheetDisplay("Advanced Options",sheetList,"SalesforceStyle.css");

            Task<Void> task = new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    updateMessage("Please wait (Scraping Website)");
                    List<Bishop> bishopList = Sort.findAttributes();
                    Collections.sort(bishopList, COMPARE_BY_BISH_LAST);

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