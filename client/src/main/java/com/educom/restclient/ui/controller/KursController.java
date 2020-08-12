package com.educom.restclient.ui.controller;

import com.educom.restclient.client.KursClient;
import com.educom.restclient.client.WebClientStockClient;
import com.educom.restclient.model.Kurs;
import com.educom.restclient.model.Lehre;
import com.educom.restclient.model.Schuler;
import com.educom.restclient.util.ActionButtonTableCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

@Component
public class KursController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    private final WebClient webClient = WebClient.builder().build();
    private final RestTemplate restTemplate = new RestTemplate();
//    @Value("classpath:/lehre.fxml")
//    Resource  resource;
    @FXML
    private TableColumn clmDelete, clmUpdate;
    @FXML
    private TextField tfKursName;
    @FXML
    private ComboBox<String> cbxRaum;
    @FXML
    private ComboBox<Lehre> cbxLehre;
    @FXML
    private RadioButton rbtKursName;
    @FXML
    private RadioButton rbtRaum;
    @FXML
    private RadioButton rbtLehre;
    @FXML
    private TextField tfKursSearch;
    @FXML
    private TableView<Kurs> tbwKurs;
    @FXML
    private TableColumn<Kurs, String> clmKursName, clmRaum, clmLehre;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnSave;
    @FXML
    private TableView<Schuler> tbwSchuler;
    @FXML
    private TableColumn<Schuler, String> clmVorname, clmName, clmEmail, clmPhoneNumber, clmGender, clmAdres, clmPlz, clmStadt;
    @FXML
    private TableColumn<Schuler, Date> clmGDatum;
    @FXML
    private RadioButton rbtSchulerVorname;
    @FXML
    private RadioButton rbtSchulerNachname;
    @FXML
    private RadioButton rbtSchulerEmail;
    @FXML
    private TextField tfSchulerSearch;
    private ObservableList<Kurs> kurssData = observableArrayList();
    private List<Kurs> list = null;
    private Long updatedKursId;
    private KursClient kursClient;
    private ApplicationContext applicationContext;


    @FXML
    void addAction(ActionEvent event) {
        Kurs kurs = new Kurs(tfKursName.getText(), null, null);
        kurs.setRaum(cbxRaum.getValue());
        kurs.setLehre(cbxLehre.getValue());
        kursClient = new KursClient(restTemplate);
        System.out.println(kurs);
        kursClient.add(kurs);
        getAllKurs();
        fillTableview();
        clearField();
    }
    void fillLehreComboBox(){
        ObservableList<Lehre> lehreObservableList =FXCollections.observableList(getAllLehre());
        cbxLehre.setItems(lehreObservableList);

}

    private   List<Lehre>  getAllLehre() {
     return new WebClientStockClient(webClient).getLehreList().collectList().block();

    }
    void fillRaumComboBox(){
    ObservableList<String> raumlist =FXCollections.observableList(List.of("Raum 1","Raum 2")).sorted();
        cbxRaum.setItems(raumlist);
}
    @FXML
    void saveAction(ActionEvent event) {
        Kurs updatedKurs = new Kurs();

        kursClient = new KursClient(restTemplate);
        kursClient.update(getUpdatedKursId(), updatedKurs);
        getAllKurs();
        fillTableview();
        clearField();

    }

    private void getAllKurs() {
        list = new WebClientStockClient(webClient).getKursList().collectList().block();

    }

    private void fillTableview() {
        if (tfKursSearch.getText().trim().isEmpty()) {
            getAllKurs();
        }
        kurssData = FXCollections.observableList(list).sorted();
        tbwKurs.setItems(kurssData);
    }

    private void deleteClient(Long id) {
        kursClient = new KursClient(restTemplate);
        kursClient.delete(id);
        getAllKurs();
        fillTableview();
    }

    private void findBy(String param) {
        kursClient = new KursClient(restTemplate);
        if (rbtKursName.isSelected()) {
            list = kursClient.findByName(param);

        } else if (rbtRaum.isSelected()) {
            list = kursClient.findByRaum(param);
        } else if (rbtLehre.isSelected()) {
            list = kursClient.findByLehre(param);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillRaumComboBox();
        fillLehreComboBox();
        tbwKurs.setEditable(true);
        tbwKurs.getSelectionModel().setCellSelectionEnabled(true);
        clmKursName.setCellValueFactory(new PropertyValueFactory("name"));
        clmRaum.setCellValueFactory(new PropertyValueFactory("raum"));
        clmLehre.setCellValueFactory(new PropertyValueFactory("lehre"));
        TableColumn<Schuler, Integer> clmLange=new TableColumn<>("kurslange");
        clmLange.setCellValueFactory(new PropertyValueFactory("kurslang"));
        TableColumn<Schuler, Integer> clmDauern=new TableColumn<>("kursdauern");
        clmDauern.setCellValueFactory(new PropertyValueFactory("dauer"));
        TableColumn<Schuler, Date> clmBeginAb=new TableColumn<>("kursbegin ab");
        clmBeginAb.setCellValueFactory(new PropertyValueFactory("anfangAb)"));
        TableColumn<Schuler, Date> clmEndeBis=new TableColumn<>("kursende");
        clmEndeBis.setCellValueFactory(new PropertyValueFactory("endeBis)"));

        clmDelete.setCellFactory(ActionButtonTableCell.forTableColumn("Delete", (Kurs p) -> {
            deleteClient(p.getId());
            return p;
        }));
        clmUpdate.setCellFactory(ActionButtonTableCell.forTableColumn("Update", (Kurs p) -> {
            fillFieldForUpdate(p);
            setUpdatedKursId(p.getId());
            return p;
        }));
        tbwKurs.getItems().setAll(kurssData);
        tbwKurs.getColumns().setAll( clmKursName,clmRaum,clmLehre,clmDelete, clmUpdate);
        fillTableview();
        tfKursSearch.textProperty().addListener(new ChangeListener<String>() {
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
        ToggleGroup searchKursGroup = new ToggleGroup();
        rbtRaum.setToggleGroup(searchKursGroup);
        rbtKursName.setToggleGroup(searchKursGroup);
        rbtLehre.setToggleGroup(searchKursGroup);
    }

    private void clearField() {
tfKursName.setText("");
clmLehre.setText("");
clmRaum.setText("");

    }

    private Long getUpdatedKursId() {
        return updatedKursId;
    }

    private void setUpdatedKursId(Long id) {
        this.updatedKursId = id;
    }

    private void fillFieldForUpdate(Kurs p) {
        setUpdatedKursId(p.getId());

    }


}
