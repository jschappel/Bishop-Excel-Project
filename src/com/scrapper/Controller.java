package com.scrapper;

import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public Stage primStage;

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

    /*
    public void saveFile() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel File","*.xlsm","*.xlsx","*xlsb","*xls","*xlt"));

        File file = fc.showSaveDialog(primaryStage);

        if(file != null){
            SaveFile("FileName", file);
        }

    }

    */

    private void SaveFile(String content, File file) {
        try {
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void runProgram() throws IOException, NoSuchMethodException {

        primStage = (Stage) runButton.getScene().getWindow();

        //check to see if the radio button was pressed and not "choose file"
        if(wasSelected == false && radioChoice.isSelected()) {
            excelWrite = new ExcelWrite();
            pIndicator.progressProperty().unbind();
            pIndicator.progressProperty().bind(excelWrite.progressProperty());
            new Thread(excelWrite).start();
        }
        // If both are selected go with the file
        else if(wasSelected == true && radioChoice.isSelected()){
            System.out.println("Add code to add to existing excel sheet");
        }
        // If just the "choose file" option happened
        else if(wasSelected == true && !radioChoice.isSelected()) {
            BishopList bishopList = Sort.findAttributes();
            ExcelCompare fileCompare = new ExcelCompare(file);
            fileCompare.compareAndWrite(fileCompare.cloneSheet(),bishopList );
            fileCompare.fileClose();
            AlertBox.finishedchanges(fileCompare.getChanges());
            primStage.close();
        }

        //Neither cases happened
        else {
            AlertBox.alertDisplay("Error","Please choose a file or check box before continuing.");
        }
    }
}
