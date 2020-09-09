package com.educom.restclient.ui.controller;


import com.educom.restclient.client.SchulerClient;
import com.educom.restclient.client.WebClientStockClient;
import com.educom.restclient.model.Gender;
import com.educom.restclient.model.Schuler;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
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
public class SchulerController implements Initializable {

    @FXML
    private TableView<Schuler> tableView;
    private ObservableList<Schuler> schulersData = observableArrayList();
    @FXML
    private TableColumn<Schuler, String> clmVorname, clmName, clmEmail, clmPhoneNumber, clmGender, clmAdres, clmPlz, clmStadt;
    @FXML
    private TextField tfFirstName, tfLastName, tfEmail, tfAdres, tfStadt, tfPlz, tfPhoneNumber, tfSearch;
    @FXML
    private ComboBox<Gender> cmbGender;
    @FXML
    private DatePicker cmbGdatum;
    @FXML
    private TableColumn clmDelete, clmUpdate;
    @FXML
    private TableColumn<Schuler, LocalDate> clmGDatum;
    private List<Schuler> list = null;

    @FXML
    private RadioButton rbtVorname, rbtNachname, rbtEmail;
    @FXML
    private Button btnAdd, btnUpdate, btnDelete, btnSave, btnLehrer, btnSchuler, btnVertrag, btnKurs, btnSignOut;
    private Long updatedSchulerId;
    private final WebClient webClient = WebClient.builder().build();
    private final RestTemplate restTemplate = new RestTemplate();
    private SchulerClient schulerClient;
    private ApplicationContext applicationContext;
    @Value("classpath:/lehre.fxml")
    Resource  resource;

private UtilDate utildate=new UtilDate<Schuler>();

    @FXML
    private void addAction() throws IOException, URISyntaxException {

        Schuler schuler = new Schuler();
        schuler.setFirstName(tfFirstName.getText());
        schuler.setLastName(tfLastName.getText());
        schuler.setEmail(tfEmail.getText());
        schuler.setAdresse(tfAdres.getText());
        schuler.setGeburstDatum(cmbGdatum.getValue());
        schuler.setGender(cmbGender.getValue());
        schuler.setStadt(tfStadt.getText());
        schuler.setPlz(tfPlz.getText());
        schuler.setPhoneNumber(tfPhoneNumber.getText());
        schulerClient = new SchulerClient();
        System.out.println(schuler);
        schulerClient.add(schuler);
        getAllSchuler();
        fillTableview();
        clearField();
    }

    @FXML
    private void saveAction() throws IOException, URISyntaxException {
        Schuler updatedSchuler = new Schuler();
        updatedSchuler.setLastName(tfLastName.getText());
        updatedSchuler.setFirstName(tfFirstName.getText());
        updatedSchuler.setEmail(tfEmail.getText());
        updatedSchuler.setAdresse(tfAdres.getText());
        updatedSchuler.setStadt(tfStadt.getText());
        updatedSchuler.setPhoneNumber(updatedSchuler.getPhoneNumber());
        updatedSchuler.setGeburstDatum(cmbGdatum.getValue());
        updatedSchuler.setPlz(tfPlz.getText());
        schulerClient = new SchulerClient();
        schulerClient.updateschuler(getUpdatedSchulerId(), updatedSchuler);
        getAllSchuler();
        fillTableview();
        clearField();
    }

    private void getAllSchuler() {
        list = new WebClientStockClient(webClient).getSchulerList().collectList().block();

    }

    private void fillTableview() {
        if (tfSearch.getText().trim().isEmpty()) {
            getAllSchuler();
        }
        schulersData = FXCollections.observableList(list).sorted();
        tableView.setItems(schulersData);
    }

    private void deleteClient(Long id) {
        schulerClient= new SchulerClient();
        schulerClient.delete(id);
        getAllSchuler();
        fillTableview();
    }

    private void findBy(String param) {
        schulerClient = new SchulerClient();
        if (rbtVorname.isSelected()) {
            list = schulerClient.findByName(param);

        } else if (rbtNachname.isSelected()) {
            list = schulerClient.findByLastName(param);

        } else if (rbtEmail.isSelected()) {
            list = schulerClient.findByEmail(param);
        }
    }

    private void fillcomboBox() {
        cmbGender.getItems().addAll(Gender.values());
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
        clmGDatum.setCellFactory((TableColumn<Schuler, LocalDate> column) -> utildate.convertColumn(column));
        clmDelete.setCellFactory(ActionButtonTableCell.forTableColumn("Delete", (Schuler p) -> {
            deleteClient(p.getId());
            return p;
        }));
        clmUpdate.setCellFactory(ActionButtonTableCell.forTableColumn("Update", (Schuler p) -> {
            fillFieldForUpdate(p);
            setUpdatedSchulerId(p.getId());
            return p;
        }));
        tableView.getItems().setAll(schulersData);
        tableView.getColumns().setAll(clmVorname, clmName, clmEmail, clmGender, clmGDatum, clmAdres, clmPlz, clmStadt, clmPhoneNumber, clmDelete, clmUpdate);
        fillTableview();
        tfSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

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

    private void clearField() {
        tfFirstName.setText("");
        tfLastName.setText("");
        tfEmail.setText("");
        tfAdres.setText("");
        tfPhoneNumber.setText("");
        tfPlz.setText("");
        tfStadt.setText("");

    }

    private Long getUpdatedSchulerId() {
        return updatedSchulerId;
    }

    private void setUpdatedSchulerId(Long id) {
        this.updatedSchulerId = id;
    }

    private void fillFieldForUpdate(Schuler p) {
        setUpdatedSchulerId(p.getId());
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