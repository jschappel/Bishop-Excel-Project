package com.scrapper;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {


    public static void display(String title, String message) {
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
        scene.getStylesheets().add("RenewStyle.css");

        window.setScene(scene);
        window.showAndWait();

    }
}