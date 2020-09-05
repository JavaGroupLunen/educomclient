package com.educom.restclient.ui.controller;

import com.educom.restclient.client.ClientAuthentication;
import com.educom.restclient.model.SignupRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private Button btnSave,btnLogin;
@FXML
private Label lblWarnung;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane anpLogin;

    @FXML
    private TextField tfBenutzername;

    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField pswPassword;

    @FXML
    private PasswordField pswPaswordverify;
    private ClientAuthentication client;

    @FXML
    void saveHandle(ActionEvent event) {
         client= new ClientAuthentication();
        if((pswPassword.getText().trim().isEmpty()&&pswPaswordverify.getText().trim().isEmpty())||pswPassword.getText().equals(pswPaswordverify.getText())) {
            SignupRequest signupRequest = new SignupRequest(tfBenutzername.getText(), tfEmail.getText(), pswPassword.getText());
             client.add(signupRequest);
        }
        else {
            lblWarnung.setText("passwordfeld sollte  selber sein");
            System.out.println("passwordfeld sollte  selber sein");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void homeHandle(ActionEvent event) {
    loadStage("/login.fxml");
    }
    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            rootPane.getChildren().retainAll(root);
            rootPane.getChildren().addAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
