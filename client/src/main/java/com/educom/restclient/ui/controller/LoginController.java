package com.educom.restclient.ui.controller;

import com.educom.restclient.client.ClientAuthentication;
import com.educom.restclient.model.AuthenticationRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController  implements Initializable {
    @FXML
    private TextField txtBenutzername;
    @FXML
    private AnchorPane anpLogin;
    @FXML
    private Hyperlink linkAnmeldung;
    private ClientAuthentication clientAuthentication=new ClientAuthentication();
    @FXML
    private PasswordField pswPassword;
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
    @FXML
    void loginHandle(ActionEvent event) {
        authenticationRequest.setUsername(txtBenutzername.getText());
        authenticationRequest.setPassword(pswPassword.getText());
        authenticationText=clientAuthentication.postLogin(authenticationRequest);
        System.out.println(authenticationText);
        fieldClear();
        if(!authenticationText.isEmpty()){
            loadStage("/vertrag.fxml");
        }
    }

    private void fieldClear() {
        txtBenutzername.setText("");
        pswPassword.setText("");
    }

    @FXML
    void registerLoad(ActionEvent event){
        loadStage("/registerform.fxml");
    }
}
