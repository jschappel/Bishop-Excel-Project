package com.scrapper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AlertBox {

    private static String sheet;


    /**
     * A alert box for any application
     * @param title The title of the window
     * @param message The alert message that you would like to display
     */
    public static void alertDisplay(String title, String message,String cssStyle) {
        Stage window = new Stage();


        window.initModality(Modality.APPLICATION_MODAL); // This makes it so you must finish the alertBox before moving on
        window.setTitle(title);
        window.setMinWidth(350);
        window.setMinHeight(90);

        Label label = new Label(message);
        Button close = new Button("Close");
        close.setOnAction(event -> window.close());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(8,8,8,8));
        layout.getChildren().addAll(label,close);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(cssStyle);

        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * An alert box that also contains a choiceBox of all the sheet titles in an excel file
     * @param title The title of the alert window
     * @param sheetList An ArrayList of type string that contains all of the excel file sheet names
     */
    public void sheetDisplay(String title, ArrayList<String> sheetList, String cssStyle) {
        Stage window = new Stage();


        window.initModality(Modality.APPLICATION_MODAL); // This makes it so you must finish the alertBox before moving on
        window.setTitle(title);
        window.setMinWidth(350);
        window.setMinHeight(90);

        Label label = new Label("Would you like to Specify the excel sheet you would like to compare? Default option will use the last sheet in the Excel file");
        label.setWrapText(true);

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setPrefHeight(20);
        choiceBox.setMaxHeight(20);

        for(String name : sheetList)
            choiceBox.getItems().add(name);

        choiceBox.setValue(choiceBox.getItems().get(0));
        choiceBox.setPadding(new Insets(8,8,8,8));


        Button close = new Button("Continue");
        close.setOnAction(event -> {
            sheet = choiceBox.getValue();
            window.close();
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(8,8,8,8));
        layout.getChildren().addAll(label,choiceBox,close);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(cssStyle);

        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Used to retrieve the current sheet in an choiceBox
     * @return the current sheet of a choiceBox
     */
    public String getSheet(){
        return sheet;
    }



}
