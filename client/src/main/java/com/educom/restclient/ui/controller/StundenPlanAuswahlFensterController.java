package com.educom.restclient.ui.controller;

import com.educom.restclient.client.SchulerClient;
import com.educom.restclient.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class StundenPlanAuswahlFensterController implements Initializable {

@FXML
private ComboBox<Lehre> cbxLehre;

    @FXML
    private ComboBox<Kurs> cbxKurs;

    @FXML
    private TextField tfVertragnum;

    @FXML
    private DatePicker dtpVertragDatum;

    @FXML
    private TableView<Stundenplanauswahl> tbwSchulerauswahl;

    @FXML
    private TableColumn<Stundenplanauswahl, String> clmSchulername;

    @FXML
    private TableColumn<Stundenplanauswahl, String> clmVorname;

    @FXML
    private TableColumn<Stundenplanauswahl, String> clmKursname;

    @FXML
    private TableColumn<Stundenplanauswahl, String> clmKlasse;

    @FXML
    private TableColumn<Stundenplanauswahl, String> clmSchulename;

    @FXML
    private TableColumn<Stundenplanauswahl, Boolean> clmauswahl;

    private ObservableList<Stundenplanauswahl> schulersData = observableArrayList();
    private List<Stundenplanauswahl> list = null;
    private final WebClient webClient = WebClient.builder().build();
    private final RestTemplate restTemplate = new RestTemplate();
    private SchulerClient schulerClient=new SchulerClient();
    private   Stundenplanauswahl st=null;
    private List<Schuler> listSchuler=new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllSchuler();
        tbwSchulerauswahl.setEditable(true);
        tbwSchulerauswahl.getSelectionModel().setCellSelectionEnabled(true);
        tbwSchulerauswahl.itemsProperty().setValue(schulersData);
        clmVorname.setCellValueFactory(new PropertyValueFactory("vorname"));
        clmSchulername.setCellValueFactory(new PropertyValueFactory("nachname"));
        clmKlasse.setCellValueFactory(new PropertyValueFactory("klasse"));
        clmSchulename.setCellValueFactory(new PropertyValueFactory("schulename"));
        clmKursname.setCellValueFactory(new PropertyValueFactory("kurs"));

        tbwSchulerauswahl.getColumns().setAll(clmVorname, clmSchulename,clmKlasse,clmSchulername,clmauswahl);
        fillTableview();

    }

    private void getAllSchuler() {
        list=new ArrayList<>();
        listSchuler = schulerClient.getSchulerList().collectList().block();
        listSchuler.forEach(
                (schuler)->{
                    System.out.println(schuler);
                   st= new Stundenplanauswahl(schuler.getFirstName(),schuler.getLastName(),schuler.getKlasse(),null,false, null);
                   list.add(st);
                }
        );

    }

    private void fillTableview() {
       // TODO:Convert Schuler
       // getAllSchuler();
        schulersData = FXCollections.observableList(list).sorted();
        tbwSchulerauswahl.setItems(schulersData);
    }



}
