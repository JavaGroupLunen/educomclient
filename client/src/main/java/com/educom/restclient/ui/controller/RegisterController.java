package com.educom.restclient.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private Button btnSave;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField tfBenutzername;

    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField pswPassword;

    @FXML
    private PasswordField pswPaswordverify;

    @FXML
    void saveHandle(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
