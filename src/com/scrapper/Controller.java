package com.scrapper;


import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {

    private File file;
    private Boolean wasSelected = false;
    private ExcelWrite excelWrite;

    // fxml Buttons
    public RadioButton radioChoice;
    public Button fileButton;
    public Button runButton;
    public VBox vbox;
    public ProgressBar pIndicator;

    public void chooseFile(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Open File Dialog");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsm","*.xlsx","*xlsb","*xls","*xlt"));
        Stage stage = (Stage)vbox.getScene().getWindow();
        file = fc.showOpenDialog(stage);

        if (file != null){
            wasSelected = true;
            System.out.println("File Success");

        }
    }

    public void runProgram(){

        //check to see if the radio button was pressed and not "choose file"
        if(wasSelected == false && radioChoice.isSelected()) {
            excelWrite = new ExcelWrite();
            pIndicator.progressProperty().unbind();
            pIndicator.progressProperty().bind(excelWrite.progressProperty());

            new Thread(excelWrite).start();

            //ExcelWrite.newExcelDocument("Test_File");
            System.out.println("Add code here to create a blank excel sheet");
        }
        // If both are selected go with the file
        else if(wasSelected == true && radioChoice.isSelected()){
            System.out.println("Add code to add to existing excel sheet");
        }
        // If just the "choose file" option happened
        else if(wasSelected == true && !radioChoice.isSelected()){
            System.out.println("Add code to add to existing excel sheet");
        }

        //Neither cases happened
        else {
            AlertBox.display("Error","Please choose a file or check box before continuing.");
        }
    }

    public void displayInstructions(){


    }


}
