package com.educom.restclient.ui.controller;


import com.educom.restclient.client.LehreClient;
import com.educom.restclient.model.Gender;
import com.educom.restclient.model.Lehre;
import com.educom.restclient.util.ActionButtonTableCell;
import com.educom.restclient.util.UtilDate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;


@Component
public class LehreController implements Initializable {
    private final RestTemplate restTemplate = new RestTemplate();
    private ObservableList<Lehre> lehresData = observableArrayList();
    @FXML
    private TableColumn<Lehre, Integer> clmAge;
    private final WebClient webClient = WebClient.builder().build();
    private List<Lehre> list = null;
    private Long updatedLehreId;
    @FXML
    private TableColumn<Lehre, String> clmVorname, clmName, clmEmail, clmPhoneNumber, clmGender, clmAdres, clmPlz, clmStadt;
    @FXML
    private TextField tfFirstName, tfLastName, tfEmail, tfAdres, tfStadt, tfPlz, tfPhoneNumber, tfSearch;
    @FXML
    private ComboBox<Gender> cmbGender;
    @FXML
    private DatePicker cmbGdatum;
    @FXML
    private TableColumn clmDelete, clmUpdate;
    @FXML
    private TableColumn<Lehre, LocalDate> clmGDatum;
    @FXML
    private TableView<Lehre> tableView;
    @FXML
    private RadioButton rbtVorname, rbtNachname, rbtEmail;
    @FXML
    private Button btnAdd, btnUpdate, btnDelete, btnSave, btnLehrer, btnSchuler, btnVertrag, btnKurs, btnSignOut;

private UtilDate utildate=new UtilDate<Lehre>();
private  LehreClient restClientTemplate = new LehreClient(restTemplate);
    @FXML
    private void addAction() throws IOException, URISyntaxException {
        Lehre lehre = new Lehre();
        lehre.setFirstName(tfFirstName.getText());
        lehre.setLastName(tfLastName.getText());
        lehre.setEmail(tfEmail.getText());
        lehre.setAdresse(tfAdres.getText());
        lehre.setGeburstDatum(cmbGdatum.getValue());
        lehre.setGender(cmbGender.getValue());
        lehre.setStadt(tfStadt.getText());
        lehre.setPlz(tfPlz.getText());
        lehre.setPhoneNumber(tfPhoneNumber.getText());   //   String codeValue = new WebClientStockClient(webClient).saveLehre(lehre);
        restClientTemplate.add(lehre);
        getAllLehre();
        fillTableview();
        clearField();
    }


    @FXML
    private void saveAction() throws IOException ,URISyntaxException {
        Lehre updatedLehre=new Lehre();
        updatedLehre.setLastName(tfLastName.getText());
        updatedLehre.setFirstName(tfFirstName.getText());
        updatedLehre.setEmail(tfEmail.getText());
        restClientTemplate.update(getUpdatedLehreId(),updatedLehre);
        getAllLehre();
        fillTableview();
        clearField();
    }


    private void getAllLehre() {
        list = restClientTemplate.getLehreList().collectList().block();

    }

    private void fillTableview() {
        if (tfSearch.getText().trim().isEmpty()) {
            getAllLehre();
        }
        lehresData = FXCollections.observableList(list).sorted();
        tableView.setItems(lehresData);
    }

    private void deleteClient(Lehre lehre) {
        restClientTemplate = new LehreClient(restTemplate);
        restClientTemplate.delete(lehre.getId());
        getAllLehre();
        fillTableview();
    }

    private void findBy(String param) {
         restClientTemplate = new LehreClient(restTemplate);
        if (rbtVorname.isSelected()) {
            list =restClientTemplate.findByFirstName(param).collectList().block();

        } else if (rbtNachname.isSelected()) {
            list =restClientTemplate.findByName(param).collectList().block();

        } else if (rbtEmail.isSelected()) {
            list =restClientTemplate.findByEmail(param).collectList().block();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillcomboBox();
        btnAdd.getStyleClass().add("button-raised");
        btnSave.getStyleClass().add("button-raised");
        tableView.setEditable(true);
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        clmVorname.setCellValueFactory(new PropertyValueFactory("firstName"));
        clmName.setCellValueFactory(new PropertyValueFactory("lastName"));
        clmEmail.setCellValueFactory(new PropertyValueFactory("email"));
        clmPhoneNumber.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
        clmGDatum.setCellValueFactory(new PropertyValueFactory("geburstDatum"));
        clmGender.setCellValueFactory(new PropertyValueFactory("gender"));
        clmAdres.setCellValueFactory(new PropertyValueFactory("adresse"));
        clmPlz.setCellValueFactory(new PropertyValueFactory("plz"));
        clmStadt.setCellValueFactory(new PropertyValueFactory("stadt"));
        clmGDatum.setCellFactory((TableColumn<Lehre, LocalDate> column) -> utildate.convertColumn(column));
        clmDelete.setCellFactory(ActionButtonTableCell.forTableColumn("Delete", (Lehre p) -> {
            deleteClient(p);
            return p;
        }));
        clmUpdate.setCellFactory(ActionButtonTableCell.forTableColumn("Update", (Lehre p) -> {
            fillFieldForUpdate(p);
            setUpdatedLehreId(p.getId());
            return p;
        }));
        tableView.getItems().setAll(lehresData);
        tableView.getColumns().setAll(clmVorname, clmName, clmEmail, clmGender, clmGDatum, clmAdres, clmPlz, clmStadt, clmPhoneNumber, clmDelete, clmUpdate);
        fillTableview();
        tfSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                System.out.println(" Text Changed to  " + newValue + "\n");
                if (!newValue.trim().isEmpty()) {
                    findBy(newValue);
                }
                fillTableview();
            }
        });

        ToggleGroup searchGroup = new ToggleGroup();
        rbtEmail.setToggleGroup(searchGroup);
        rbtVorname.setToggleGroup(searchGroup);
        rbtNachname.setToggleGroup(searchGroup);

    }
    private void fillcomboBox() {
        cmbGender.getItems().addAll(Gender.values());
    }
    private void clearField() {
        tfFirstName.setText("");
        tfLastName.setText("");
        tfEmail.setText("");
        tfAdres.setText("");
        tfPhoneNumber.setText("");
        tfPlz.setText("");
        tfStadt.setText("");
    }
    private void setUpdatedLehreId(Long id){
            this.updatedLehreId=id;
    }
    private Long getUpdatedLehreId(){
            return updatedLehreId;
    }
    private void fillFieldForUpdate(Lehre p){
        setUpdatedLehreId(p.getId());
        tfFirstName.setText(p.getFirstName());
        tfLastName.setText(p.getLastName());
        tfEmail.setText(p.getEmail());
        tfAdres.setText(p.getAdresse());
        cmbGdatum.setValue(p.getGeburstDatum());
        tfPhoneNumber.setText(p.getPhoneNumber());
        tfPlz.setText(p.getPlz());
        tfStadt.setText(p.getStadt());
        }



}