package com.educom.restclient.ui.controller;

import com.educom.restclient.client.ClientAuthentication;
import com.educom.restclient.model.AuthenticationRequest;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController  implements Initializable {
    @FXML
    private AnchorPane anpLogin;

    @FXML
    private JFXTextField jfdBenutzerName;

    @FXML
    private JFXPasswordField jfdPassword;

    @FXML
    private JFXButton btnAnmelden;

    @FXML
    private JFXButton btnRegister;

    @FXML
    void loginHandle(ActionEvent event) {
        authenticationRequest.setUsername(jfdBenutzerName.getText());
        authenticationRequest.setPassword(jfdPassword.getText());
        authenticationText=clientAuthentication.postLogin(authenticationRequest);
        fieldClear();
        if(!authenticationText.isEmpty()){
            loadStage("/vertrag.fxml");
        }
    }

    @FXML
    void registerButtonHandle(ActionEvent event) {


        loadStage("/registerform.fxml");
    }

    private ClientAuthentication clientAuthentication=new ClientAuthentication();
    public static String authenticationText;
    private  AuthenticationRequest authenticationRequest=new AuthenticationRequest();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            anpLogin.getChildren().retainAll(root);
            anpLogin.getChildren().addAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void fieldClear() {
        jfdBenutzerName.setText("");
        jfdPassword.setText("");
    }


}
