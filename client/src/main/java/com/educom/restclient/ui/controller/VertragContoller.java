package com.educom.restclient.ui.controller;

import com.educom.restclient.client.KursClient;
import com.educom.restclient.client.VertragClient;
import com.educom.restclient.model.*;
import com.educom.restclient.util.ActionButtonTableCell;
import com.educom.restclient.util.UtilDate;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
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
    private GridPane grid=new GridPane();
    @FXML
    private  Accordion accordion;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label tfadres,lblvatername_error,lblSumme;
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
    private TableView tbwAngemeldeteKurse,tbwKurse;
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
    private TextFlow textFlow;
    @FXML
    private TextField tfAnfangsdatum, tfInstitute, tfEndeDatum, tfMonatlichPrice;
    @FXML
    private ChoiceBox<ZahlungsType> cbxZahlungsType;
    @FXML
    private TextField tfEinmaligePrice=new JFXTextField();
    @FXML
    private TextField tfAnmeldegebuhr, tfMaterialkosten, tfTotalprice, tfRabatprice,tfSumme,tfRestbetrag;
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
    Label error_label;
    @FXML
    TableColumn<Kurs, Integer> clmDauern;
    @FXML
    private TreeTableView trbwSchuler;
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
    private ValidationSupport validationSupport = new ValidationSupport();

    private static Double summe=0.0;
    private static Double monatlischePreise=0.0,einmaligepreise=0.0,materialkosten=0.0,rabatPrice=0.0,rabetPercent=0.0;

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
        if(!(tfEinmaligePrice.getText().trim().isEmpty())){
            Double einmaligekosten=Double.valueOf(tfEinmaligePrice.getText());
            neuvertrag.setEinmaligeKosten(einmaligekosten);

        }

        if(!(tfMaterialkosten.getText().trim().isEmpty())){
            Double materialkosten=Double.valueOf(tfMaterialkosten.getText());
            neuvertrag.setMaterialprice(materialkosten);
        }
        if(!(tfMonatlichPrice.getText().trim().isEmpty())){
            Double monatlischePreise=Double.valueOf(tfMonatlichPrice.getText());
            neuvertrag.setMonatlischeRate(monatlischePreise);
        }
        if(!(tfRabatprice.getText().trim().isEmpty())){
            Double rabat=Double.valueOf(tfRabatprice.getText());
            neuvertrag.setRabat(rabat);
        }
        if(!(tfRabatPercent.getText().trim().isEmpty())){
            Double rabatPercentes=Double.valueOf(tfRabatPercent.getText());
            neuvertrag.setRabatPercent(rabatPercentes);
        }
//        if(!(tfSumme.getText().trim().isEmpty())){
//            Double summe=Double.valueOf(tfSumme.getText());
//            neuvertrag.setSumme(summe);
//        }
        if(!(tfRestbetrag.getText().trim().isEmpty())){
            Double restbetrag=Double.valueOf(tfRestbetrag.getText());
            neuvertrag.setRestbetrag(restbetrag);
        }
        neuvertrag.setZahlungstype(cbxZahlungsType.getValue());
        if(validdation()){
            vertragClient.add(neuvertrag);
            clearField();
            clearKursAuswahlfield();
            getAllVertrage();

        }else {
            Notifications notificationBuilder = Notifications.create();
            notificationBuilder.owner(getVertragWindows());
            notificationBuilder.showWarning();
        }
    }

    public boolean isValidName(String s){
        String regex="[A-Za-z\\s]+";
        return s.matches(regex);//returns true if input and regex matches otherwise false;
    }

 private Text errormessage(String s){
       Text error= new Text(s);
       error.setFont(new Font("Times New Roman", 12));
       error.setFill(Color.RED);
       return error;
 }
 private void showWahrnungMessage(){
     textFlow.setVisible(true);
     if(tfVatername.getText().trim().isEmpty()){textFlow.getChildren().add(errormessage("Invalid Vater name\n"));}
     if(tfAnmeldegebuhr.getText().trim().isEmpty()){ textFlow.getChildren().add(errormessage("Invalid Anmelde Gebuhr\n"));}
     if(tfMaterialkosten.getText().trim().isEmpty()){ textFlow.getChildren().add(errormessage("Invalid Materialkosten\n"));}
     if(tfMonatlichPrice.getText().trim().isEmpty()){textFlow.getChildren().add(errormessage("Invalid MonatlichPrice\n"));}
     if(tfRabatprice.getText().trim().isEmpty()){ textFlow.getChildren().add(errormessage("Invalid Rabatprice\n"));}
     if(tfRabatPercent.getText().trim().isEmpty()){ textFlow.getChildren().add(errormessage("Invalid RabatPercent\n"));}
     if(tfMonatlichPrice.getText().trim().isEmpty()){textFlow.getChildren().add(errormessage("Invalid MonatlichPrice\n"));}
     if(cbmAnfangDatum.getValue()==null){ textFlow.getChildren().add(errormessage("Invalid Anfang Datum oder endedatum\n"));}
     if(tfRestbetrag.getText().trim().isEmpty()){ textFlow.getChildren().add(errormessage("Invalid Restbetrag\n"));}
//     if(cbxZahlungsType.getValue().name().isEmpty()){textFlow.getChildren().add(errormessage("Invalid Zahlungstype\n"));}
//     if(cmbGdatum.getValue().isAfter(ChronoLocalDate.from(LocalDateTime.now()))){ textFlow.getChildren().add(errormessage("Invalid Geburstdatum\n"));}
//     if(cbxGeschlecht.getValue().name().isEmpty()){ textFlow.getChildren().add(errormessage("Invalid Geschlecht\n"));}
     if(tfAdresse.getText().trim().isEmpty()){textFlow.getChildren().add(errormessage("Invalid Schuler adresse\n"));}
     if(tfTel.getText().trim().isEmpty()){ textFlow.getChildren().add(errormessage("Invalid tel\n"));}
     if(tfPlz.getText().trim().isEmpty()){ textFlow.getChildren().add(errormessage("Invalid Plz\n"));}

 }


    private boolean validdation(){
        textFlow.getChildren().clear();
        showWahrnungMessage();
        validationSupport.registerValidator(tfAnmeldegebuhr, Validator.createEmptyValidator("Text is required"));
        validationSupport.registerValidator(tfMaterialkosten, Validator.createEmptyValidator("Text is required"));
        validationSupport.registerValidator(tfMonatlichPrice, Validator.createEmptyValidator("Text is required"));
        validationSupport.registerValidator(tfRabatprice, Validator.createEmptyValidator("Text is required"));
        validationSupport.registerValidator(tfVatername, Validator.createEmptyValidator("Text is required"));
        validationSupport.registerValidator(tfMuttername, Validator.createEmptyValidator("Text is required"));
        validationSupport.registerValidator(tfName, Validator.createEmptyValidator("Text is required"));
        validationSupport.registerValidator(tfVorname, Validator.createEmptyValidator("Text is required"));
        Boolean name=tfName.getText().trim().isEmpty()?false:true;
        Boolean vorname=tfVorname.getText().trim().isEmpty()?false:true;
        Boolean kursname=tfKursName.getText().trim().isEmpty()?false:true;
        Boolean adresse=tfAdresse.getText().trim().isEmpty()?false:true;
        Boolean telf=tfTel.getText().trim().isEmpty()?false:true;
        Boolean plz=tfPlz.getText().trim().isEmpty()?false:true;
        Boolean stadt=tfStadt.getText().trim().isEmpty()?false:true;
        Boolean vater=tfVatername.getText().trim().isEmpty()?false:true;
        Boolean mutter=tfMuttername.getText().trim().isEmpty()?false:true;

        System.out.println(name&&vorname&&kursname&&adresse&&telf&&plz&&stadt&&vater&&mutter);
        return name&&vorname&&kursname&&adresse&&telf&&plz&&stadt&&vater&&mutter;
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


private ChangeListener<String> summeListener(){
    return new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
            if (!newValue.trim().isEmpty()) {          //  TODO:Düzgün calismiyor
                getFieldValue();
                    if(rabatPrice!=0){
                        summe=monatlischePreise*12+einmaligepreise+materialkosten-rabatPrice-((rabetPercent)*summe/100);
                    }
                summe =monatlischePreise*12+einmaligepreise+materialkosten-rabatPrice;
                tfRestbetrag.setText(String.valueOf(summe));
                lblSumme.setText(String.valueOf(summe));
                System.out.println(summe);
              }

    }
};
    }
    private void summeRechnen() {
        tfEinmaligePrice.textProperty().addListener(summeListener());
        tfRabatPercent.textProperty().addListener(summeListener());
        tfMonatlichPrice.textProperty().addListener(summeListener());
        tfMaterialkosten.textProperty().addListener(summeListener());
        tfRestbetrag.textProperty().addListener(summeListener());
    }

    private void getFieldValue(){
        if(!(tfMonatlichPrice.getText().trim().isEmpty())){ monatlischePreise =Double.valueOf(tfMonatlichPrice.getText());}
        if(!(tfEinmaligePrice.getText().trim().isEmpty())){ einmaligepreise= Double.valueOf(tfEinmaligePrice.getText());}
        if(!(tfMaterialkosten.getText().trim().isEmpty())){ materialkosten= Double.valueOf(tfMaterialkosten.getText());}
        if(!(tfRabatprice.getText().trim().isEmpty())){  rabatPrice= Double.valueOf(tfRabatprice.getText());}
        if(!(tfRabatPercent.getText().trim().isEmpty())){ rabetPercent=(Double.valueOf(tfRabatPercent.getText()));}
    }

    public void setMessage(Label l, String message, Color color){
        l.setText(message);
        l.setTextFill(color);
        l.setVisible(true);
    }


    private void clearField() {
        tfVorname.setText("");
        tfName.setText("");
        tfEmail.setText("");
        tfAdresse.setText("");
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
        summeRechnen();

    }


    private void fillcomboBox() {
        cbxGeschlecht.getItems().addAll(Gender.values());
        cbxZahlungsType.getItems().addAll(ZahlungsType.values());
    }

    public void expendAccordion() {
        accordion.setExpandedPane(tpKursinformationen);
    }
}
