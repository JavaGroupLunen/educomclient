package com.educom.restclient.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class StundenPlanAuswahlFensterController implements Initializable {

    @FXML
    private ComboBox<?> cbxLehre;
@FXML
private TableView tbwSchulerauswahl;
    @FXML
    private ComboBox<?> cbxKurs;

    @FXML
    private TableColumn<?, ?> clmSchulername;

    @FXML
    private TableColumn<?, ?> clmVorname;

    @FXML
    private TableColumn<?, ?> clmKursname;

    @FXML
    private TableColumn<?, ?> clmKlasse;

    @FXML
    private TableColumn<?, ?> clmSchulename;

    @FXML
    private TableColumn<?, ?> clmauswahl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
