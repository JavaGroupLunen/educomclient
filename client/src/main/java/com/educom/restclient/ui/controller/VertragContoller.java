package com.educom.restclient.ui.controller;

import com.educom.restclient.client.KursClient;
import com.educom.restclient.client.VertragClient;
import com.educom.restclient.model.*;
import com.educom.restclient.util.ActionButtonTableCell;
import com.educom.restclient.util.UtilDate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;

public class VertragContoller implements Initializable {
    private static Stage vertragWindows;
    private final WebClient webClient = WebClient.builder().build();
    private final RestTemplate restTemplate = new RestTemplate();
    @FXML
    private  Accordion accordion;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label tfadres;
    @FXML
    private TextField tfVorname;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfGeburstdatum;
    @FXML
    private TextField tfAdresse;
    @FXML
    private ComboBox<Gender> cbxGeschlecht;
    @FXML
    private TextField tfPlz;
    @FXML
    private TextField tfStadt;
    @FXML
    private TextField tfTel;
    @FXML
    private TextField tfSchule;
    @FXML
    private TextField tfKlasse;
    @FXML
    private TextField tfRaum;
    @FXML
    private TextField tfLehre;
    @FXML
    private TextField tfKursName;
    @FXML
    private Button btnAdd, btnKursFenster,btnSave;
    @FXML
    private TextField tfDauer;
    @FXML
    private TextField tfKurslang;
    @FXML
    private TextField tfAnfangenAb;
    @FXML
    private TextField tfEndeBis;
    @FXML
    private TextField tfKosten, tfEmail;
    @FXML
    private TableView tbwAngemeldeteKurse;
    @FXML
    private TableColumn<Kurs, String> clmKursName;
    @FXML
    private TableColumn<Kurs, String> clmRaum;
    @FXML
    private TableColumn<Kurs, Lehre> clmLehre;
    @FXML
    private TableColumn clmKursDelete;
    @FXML
    private Label lblvertragNo;
    @FXML
    private TextField tfAnfangsdatum, tfInstitute, tfEndeDatum, tfMonatlichPrice;
    @FXML
    private ChoiceBox<ZahlungsType> cbxZahlungsType;
    @FXML
    private TextField tfEinmaligePrice, tfAnmeldegebuhr, tfMaterialkosten, tfTotalprice, tfRabatprice,tfSumme,tfRestbetrag;
    @FXML
    private TextField tfVatername, tfRabatPercent, tfMuttername, tfElternAdres, tfElternPlz, tfIban, tfBic, tfElternTel, tfElternStadt, tfInhaber, tfVertragSearch;
    @FXML
    private TableView<Vertrag> tbwVertrag;
    @FXML
    private DatePicker cmbGdatum;
    @FXML
    private DatePicker cbmAnfangDatum;
    @FXML
    private DatePicker cbmEndeDatum;
    @FXML
    private TableColumn<Vertrag, String> clmSchuler, clmZahlungstype;
    @FXML
    private TableColumn<Vertrag, Date> clmVertragsdatum, clmVertragsbegin, clmVertragsende;
    @FXML
    private TableColumn<Vertrag, Double> clmEinmaligeKosten, clmAnmeldegebuhr, clmMaterialprice, clmSumme, clmMonatlischeRate, clmRestbetrag, clmRabat;
    @FXML
    private AnchorPane vertragRechtPanel;
    @FXML
    private  TitledPane tpKursinformationen,tpSchulerinformationen,tpElterninformationen;
    @FXML
    private TableColumn<Vertrag, Integer> clmRabatPercent;
    @FXML
    TableColumn<Kurs, Integer> clmLange;
    @FXML
    TableColumn<Kurs, Integer> clmDauern;
    @FXML
    TableColumn<Kurs, Double> clmKursKosten;
    @FXML
    TableColumn<Kurs, String> clmBeginAb;
    @FXML
    TableColumn<Kurs, String> clmEndeBis;
    @FXML
    private Button btnVertragAdd;
    @FXML
    private RadioButton rbtVertragnum,rbtSchuler,rbtEltern,rbtStatus;
    private ObservableList<Vertrag> vertragsDatei = observableArrayList();
    private  List<Kurs> selectedKursList=new ArrayList<>();
    private ObservableList<Kurs> kurssAuswahlData =  observableArrayList();
    private VertragClient vertragClient=new VertragClient(restTemplate);
    private ApplicationContext applicationContext;
    private Kurs selectedKurse;
    private KursClient kursClient;
    private List<Vertrag> list = null;
    String pattern = "dd-MM-yyyy";
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
    private UtilDate utildate=new UtilDate<Kurs>();
    @FXML
    void addVertragAction(ActionEvent event) {
        vertragClient=new VertragClient(restTemplate);
        Schuler schuler = new Schuler();
        schuler.setFirstName(tfVorname.getText());
        schuler.setLastName(tfName.getText());
        schuler.setEmail(tfEmail.getText());
        schuler.setAdresse(tfAdresse.getText());
        schuler.setGeburstDatum(cmbGdatum.getValue());
        schuler.setGender(cbxGeschlecht.getValue());
        schuler.setStadt(tfStadt.getText());
        schuler.setPlz(tfPlz.getText());
        schuler.setPhoneNumber(tfTel.getText());
        schuler.setVater(tfVatername.getText());
        schuler.setMutter(tfMuttername.getText());
        schuler.setKlasse(tfKlasse.getText());
        System.out.println(schuler);
        Set<Kurs> list = new ArrayList<Kurs>().stream().collect(Collectors.toSet());
        schuler.setKurses(list);
        Vertrag neuvertrag=new Vertrag();
        neuvertrag.setSchuler(schuler);
        neuvertrag.setVertragsbegin(cbmAnfangDatum.getValue());
        neuvertrag.setVertragsende(cbmEndeDatum.getValue());
        neuvertrag.setAnmeldegebuhr(Double.valueOf(tfAnmeldegebuhr.getText()));
        neuvertrag.setEinmaligeKosten(Double.valueOf(tfEinmaligePrice.getText()));
        neuvertrag.setMaterialprice(Double.valueOf(tfMaterialkosten.getText()));
        neuvertrag.setMonatlischeRate(Double.valueOf(tfMonatlichPrice.getText()));
        neuvertrag.setRabat(Integer.valueOf(tfRabatprice.getText()));
        neuvertrag.setRabatPercent(Integer.valueOf(tfRabatPercent.getText()));
        neuvertrag.setSumme(Double.valueOf(tfSumme.getText()));
        neuvertrag.setRestbetrag(Double.valueOf(tfRestbetrag.getText()));
        neuvertrag.setZahlungstype(cbxZahlungsType.getValue());
        vertragClient.add(neuvertrag);
        clearField();
        clearKursAuswahlfield();
        getAllVertrage();
    }

//    private void fillTableview() {
//        if (tfVertragSearch.getText().trim().isEmpty()) {
//            getAllVertrage();
//        }
//        vertragsDatei = FXCollections.observableList(list).sorted();
//        tbwVertrag.setItems(vertragsDatei);
//    }

//    private void getAllVertag(){
//       vertragClient=new VertragClient(restTemplate);
//       vertragsDatei= (ObservableList<Vertrag>) vertragClient.getAllVertrag().stream().collect(Collectors.toList());
//       tbwVertrag.setItems(vertragsDatei);
//    }

    private void clearField() {
        tfVorname.setText("");
        tfName.setText("");
        tfEmail.setText("");
        tfAdresse.setText("");
        //tfGeburstdatum.setText("");
        tfTel.setText("");
        tfPlz.setText("");
        tfStadt.setText("");
        tfVatername.setText("");
        tfMuttername.setText("");
    }

    private void vertragTableFill() {
        clmSchuler.setCellValueFactory(new PropertyValueFactory("schuler"));
        clmVertragsdatum.setCellValueFactory(new PropertyValueFactory("vertragsdatum"));
        clmVertragsbegin.setCellValueFactory(new PropertyValueFactory("vertragsbegin"));
        clmVertragsende.setCellValueFactory(new PropertyValueFactory("vertragsende"));
        clmZahlungstype.setCellValueFactory(new PropertyValueFactory("zahlungstype"));
        clmEinmaligeKosten.setCellValueFactory(new PropertyValueFactory("einmaligeKosten"));
        clmAnmeldegebuhr.setCellValueFactory(new PropertyValueFactory("anmeldegebuhr"));
        clmMaterialprice.setCellValueFactory(new PropertyValueFactory("materialprice"));
        clmMonatlischeRate.setCellValueFactory(new PropertyValueFactory("monatlischeRate"));
        clmRabat.setCellValueFactory(new PropertyValueFactory("rabat"));
        clmRabatPercent.setCellValueFactory(new PropertyValueFactory("rabatPercent"));
        clmSumme.setCellValueFactory(new PropertyValueFactory("summe"));
        tbwVertrag.getItems().setAll(vertragsDatei);
        tbwVertrag.getColumns().setAll(clmSchuler, clmVertragsdatum, clmVertragsbegin, clmVertragsende, clmZahlungstype, clmEinmaligeKosten, clmAnmeldegebuhr, clmMaterialprice, clmMonatlischeRate, clmRabat, clmRabatPercent, clmSumme);
        //table fill here
        fillVertragTableview();
        tfVertragSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                System.out.println(" Text Changed to  " + newValue + "\n");
                if (!newValue.trim().isEmpty()) {
                    findBy(newValue);
                }
                   fillVertragTableview();
            }
        });
         ToggleGroup searchKursGroup = new ToggleGroup();
         rbtEltern.setToggleGroup(searchKursGroup);
         rbtVertragnum.setToggleGroup(searchKursGroup);
         rbtStatus.setToggleGroup(searchKursGroup);
         rbtSchuler.setToggleGroup(searchKursGroup);
    }

    private void findBy(String param) {
        vertragClient = new VertragClient(restTemplate);
        if (rbtVertragnum.isSelected()) {
            list =vertragClient.getVertragById(Long.valueOf(param)).collectList().block();
        } else if (rbtSchuler.isSelected()) {
            list = vertragClient.getSchulerByName(param).collectList().block();
        } else if (rbtEltern.isSelected()) {
            list = vertragClient.getVertragByEltern(param).collectList().block();

    }
    }

    @FXML
    private void addToKursAuswahlTable() {
        if(selectedKurse!=null) {
            selectedKursList.add(selectedKurse);
            refreshKursAuswahlTable();
        }
        clearKursAuswahlfield();
    }

    private void clearKursAuswahlfield() {
        tfKursName.setText("");
        tfRaum.setText("");
        tfLehre.setText("");
        tfDauer.setText("");
        tfKurslang.setText("");
        tfAnfangenAb.setText("");
        tfEndeBis.setText("");
        tfKosten.setText("");
    }

    private void refreshKursAuswahlTable(){
        kurssAuswahlData = FXCollections.observableList(selectedKursList).sorted();
        tbwAngemeldeteKurse.setItems(kurssAuswahlData);
        tbwAngemeldeteKurse.refresh();
    }


    private void tbwKursAuswahlFill() {
        tbwAngemeldeteKurse.setEditable(true);
        tbwAngemeldeteKurse.getSelectionModel().setCellSelectionEnabled(true);
        tbwAngemeldeteKurse.editableProperty().setValue(true);
        clmKursName.setCellValueFactory(new PropertyValueFactory("name"));
        clmRaum.setCellValueFactory(new PropertyValueFactory("raum"));
        clmLehre.setCellValueFactory(new PropertyValueFactory("lehre"));
        clmLange.setCellValueFactory(new PropertyValueFactory("kurslang"));
        clmDauern.setCellValueFactory(new PropertyValueFactory("dauer"));
        clmBeginAb.setCellValueFactory(new PropertyValueFactory("anfangab"));
        clmEndeBis.setCellValueFactory(new PropertyValueFactory("endebis"));
        clmKursKosten.setCellValueFactory(new PropertyValueFactory<>("kosten"));
        clmKursDelete.setCellFactory(ActionButtonTableCell.forTableColumn("Delete", (Kurs p) -> {
            kursdeleteFromAuswahlTable(p);
            return p;
        }));
        clmBeginAb.setCellFactory((column) ->  utildate.convertColumn(column));
        clmEndeBis.setCellFactory((column) -> utildate.convertColumn(column));

        tbwAngemeldeteKurse.getItems().setAll(kurssAuswahlData);
        tbwAngemeldeteKurse.getColumns().setAll(clmKursName, clmRaum, clmLehre, clmLange,clmDauern,clmBeginAb,clmEndeBis,clmKursDelete);
    }

    private void kursdeleteFromAuswahlTable(Kurs p) {
        selectedKursList.remove(p);
        selectedKurse=null;
        refreshKursAuswahlTable();
    }

    public Stage getVertragWindows() {
        return vertragWindows;
    }
    private void fillVertragTableview() {
        if (tfVertragSearch.getText().trim().isEmpty()) {
            getAllVertrage();
        }
        vertragsDatei = FXCollections.observableList(list).sorted();
        tbwVertrag.setItems(vertragsDatei);
    }

    @FXML
    private void loadKursAuswahFenster(ActionEvent event) throws IOException {
        vertragWindows = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(vertragWindows.getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/kurswahl.fxml"));
        Parent root = loader.load();
        KurswahlController kurswahlController = loader.getController();
        dialog.getDialogPane().setContent(root);
        dialog.showAndWait();

    }

    /**
     *
     * @param auswahl
     * @apiNote bla bla
     */
    public void fillKursField(Kurs auswahl) {
        selectedKurse = auswahl;
        tfKursName.setText(auswahl.getName() != null ? auswahl.getName() : "");
        tfRaum.setText(auswahl.getRaum() != null ? auswahl.getRaum() : "");
        tfLehre.setText(auswahl.getLehre() != null ? auswahl.getLehre().toString() : "");
        tfDauer.setText(String.valueOf(auswahl.getDauer()) != null ? String.valueOf(auswahl.getDauer()) : "");
        tfKurslang.setText(String.valueOf(auswahl.getKurslang()));
        tfAnfangenAb.setText(String.valueOf(auswahl.getAnfangab()));
        tfEndeBis.setText(String.valueOf(auswahl.getEndebis()));
        tfKosten.setText(String.valueOf(auswahl.getKosten()));
    }


    private void getAllVertrage() {
        list = vertragClient.getVertragList().collectList().block();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillcomboBox();
        vertragTableFill();
        tbwKursAuswahlFill();
        accordion.setExpandedPane(tpSchulerinformationen);
        //  tbwKursAuswahlFill();
    }

    private void fillcomboBox() {
        cbxGeschlecht.getItems().addAll(Gender.values());
        cbxZahlungsType.getItems().addAll(ZahlungsType.values());
    }

    public void expendAccordion() {
        accordion.setExpandedPane(tpKursinformationen);
    }
}
