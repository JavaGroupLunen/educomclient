package com.educom.restclient.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DashboardController implements Initializable {
    @FXML
    private AnchorPane rootPane,anpLogin;


    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            rootPane.getChildren().retainAll(root);
            rootPane.getChildren().addAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void loadLehre(ActionEvent event2)  {
        loadStage("/lehre.fxml");

    }
    @FXML
    private void loadSchuler(ActionEvent event2) {
        loadStage("/schuler.fxml");
    }

    @FXML
    private void loadKurs(ActionEvent event2) {

        loadStage("/kurs.fxml");
    }

    @FXML
    private void loadVertrag(ActionEvent event2) {
        loadStage("/vertrag.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadStage("/login.fxml");
    }
    @FXML
    private void singoutHandle() throws IOException, URISyntaxException {
        //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        System.exit(0);

    }
}
