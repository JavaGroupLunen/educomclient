package com.educom.restclient.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class StundenPlanController implements Initializable {
@FXML
private GridPane gridStundenPlan;
@FXML
private Button btnPlanHinzufuegen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
@FXML
   private void planAuswahlWindowoffnen(ActionEvent event){
//    try {
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(getClass().getResource("/stundenplanauswahl.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
//        Stage stage = new Stage();
//        stage.setTitle("New Window");
//        stage.setScene(scene);
//        stage.show();
//    } catch (IOException e) {
//        System.out.println("acilmadi");
//    }

    try {
        Stage stage =  new Stage();
        stage.initStyle(StageStyle.DECORATED);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/stundenplanauswahl.fxml"));
        Parent load = fxmlLoader.load();
        Scene scene=new Scene(load, 1200, 600);
        scene.getStylesheets().add("/ui.css");
        stage.setScene(scene);
        stage.setTitle("Schuleauswahl");
        stage.show();

    } catch (IOException e) {
        throw new RuntimeException(e);
    }

}
   }

