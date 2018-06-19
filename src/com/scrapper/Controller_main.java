package com.scrapper;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
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
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static com.scrapper.Bishop.*;


public class Controller_main {

    //  Files
    private File file;
    private File saveFile;

    //  Booleans
    private Boolean wasSelected = false;
    private Boolean saveFileSelected = false;

    //  Views:
    public VBox vbox;

    // fxml Buttons and Labels:
    public RadioButton radioChoice;
    public Button fileButton;
    public Button runButton;
    public Label progressID;

    //  Menu Related Stuff:
    public Menu sort_menu;
    public MenuBar menu_bar;
    public MenuItem current_sort;
    public MenuItem main_salesforce;

    //  Comparators
    private Comparator<Bishop> currentCompare_main = COMPARE_BY_BISH_LAST;    // Default compare is by  bishop lastName

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
     * Returns a file as a user specified location. This file is not SAVED
     * Look in ExcelWrite for where the file is saved
     * @return A File at the chosen location
     */
    private void directoryChooser() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose location To Save Report");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File selectedFile = chooser.showSaveDialog(null);

        if (selectedFile != null) {
            saveFile = selectedFile;
            saveFileSelected = true;
        }
        else
            System.out.println("No File Selected");
    }

    /**
     * OnClick action for the "Run Button" This function handles all of the possible run cases.
     * @throws IOException when creating ExcelCompare object if the workbook can not be created
     * in the file
     */
    public void runProgram() throws IOException {


        //check to see if the radio button was pressed and not "choose file"
        if(!wasSelected && radioChoice.isSelected()) {
            runButton.setDisable(true);
            directoryChooser();

            //  If a save location was selected
            if (saveFileSelected) {
                //File file = directoryChooser();
                System.out.println("here");
                List<Bishop> bishopList = Sort.findAttributes();
                bishopList.sort(currentCompare_main);

                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() {

                        updateMessage("Please wait (Creating excel file)");
                        ExcelWrite excelWrite = new ExcelWrite(saveFile);
                        updateMessage("Please wait (Adding headers)");
                        excelWrite.addHeaders();
                        updateMessage("Please wait (Adding bishop data)");
                        excelWrite.addData(bishopList);
                        updateMessage("Please wait (Closing excel file)");
                        excelWrite.closeFile();
                        return null;
                    }
                };
                progressID.setTextFill(Color.BLACK);
                task.messageProperty().addListener((observable, oldValue, newValue) -> progressID.setText(newValue));
                new Thread(task).start();

                // java 8 construct, replace with java 7 code if using java 7.
                task.setOnSucceeded(e -> {
                    progressID.textProperty().unbind();
                    runButton.setDisable(false);
                    // this message will be seen.
                    progressID.setTextFill(Color.valueOf("#01C76C"));
                    progressID.setText("The file was successfully created");
                });
            }
            else{
                runButton.setDisable(false);
                progressID.setTextFill(Color.valueOf("C20004"));
                progressID.setText("No file was selected");

            }
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

                    // TODO("Add code for different sorts")
                    List<Bishop> bishopList = Sort.findAttributes();
                    bishopList.sort(currentCompare_main);


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

    @SuppressWarnings("Duplicates")
    public void changeCurrentOption(ActionEvent actionEvent) {
        CheckMenuItem item = (CheckMenuItem) actionEvent.getSource();
        String id = item.getId();

        switch (id){
            case "full_dio_menu":{
                setSelected("full_dio_menu");
                currentCompare_main = COMPARE_BY_DIO_FULL;
                System.out.println("Now doing Diocese Full Name Comparison");
                break;
            }

            case "short_dio_menu":{
                setSelected("short_dio_menu");
                currentCompare_main = COMPARE_BY_DIO_SHORT;
                System.out.println("Now doing Diocese Short Name Comparison");
                break;
            }

            case "city_dio_menu":{
                setSelected("city_dio_menu");
                currentCompare_main = COMPARE_BY_DIO_CITY;
                System.out.println("Now doing Diocese City Comparison");
                break;
            }

            case "state_dio_menu":{
                setSelected("state_dio_menu");
                currentCompare_main = COMPARE_BY_DIO_STATE;
                System.out.println("Now doing Diocese State Comparison");
                break;
            }

            case "first_bish_menu":{
                setSelected("first_bish_menu");
                currentCompare_main = COMPARE_BY_BISH_FIRST;
                System.out.println("Now doing First Comparison");
                break;
            }

            case "last_bish_menu":{
                setSelected("last_bish_menu");
                currentCompare_main = COMPARE_BY_BISH_LAST;
                System.out.println("Now doing Last Comparison");
                break;
            }

            case "title_bish_menu": {
                setSelected("title_bish_menu");
                currentCompare_main = COMPARE_BY_BISH_TITLE;
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
                    currentSelection = item.getText();
                    item.setSelected(true);
                }
                else
                    item.setSelected(false);
            }
        }

        // Set the text of the current Sort menu item
        current_sort.setText("Current Sort: " + currentSelection);
    }
}