package com.educom.restclient.ui.controller;

import com.educom.restclient.client.KursClient;
import com.educom.restclient.client.LehreClient;
import com.educom.restclient.model.Kurs;
import com.educom.restclient.model.KursType;
import com.educom.restclient.model.Lehre;
import com.educom.restclient.util.ActionButtonTableCell;
import com.educom.restclient.util.UtilDate;
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

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

@Component
public class KursController implements Initializable {
   //private final WebClient webClient = WebClient.builder().build();
    private final RestTemplate restTemplate = new RestTemplate();
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableColumn clmDelete, clmUpdate;
    @FXML
    private TextField tfKursName;
    @FXML
    private ComboBox<String> cbxRaum;
    @FXML
    private ComboBox<KursType> cbxUnterrichttype;
    @FXML
    private ComboBox<Lehre> cbxLehre;
    @FXML
    private RadioButton rbtKursName;
    @FXML
    private RadioButton rbtRaum;
    @FXML
    private RadioButton rbtLehre;
    @FXML
    private TextField tfKursSearch, tfDauer, tfKurslang, tfPrice;
    @FXML
    private TableView tbwKurs;
    @FXML
    private TableColumn<Kurs, String> clmKursName, clmRaum, clmLehre, clmKursType;

    @FXML
    private TableColumn<Kurs, LocalDate> clmBeginAb;
    @FXML
    private TableColumn<Kurs, LocalDate> clmEndeBis;
    @FXML
    private TableColumn<Kurs, ?> clmLange;
    @FXML
    private TableColumn<Kurs, ?> clmDauern;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnSave;
    @FXML
    private DatePicker dtpAnfangAb, dtpEndeBis;

    private ObservableList<Kurs> kurssData = observableArrayList();
    private List<Kurs> list = null;
    private Long updatedKursId;
    private KursClient kursClient= new KursClient();
    private LehreClient lehreClient=new LehreClient(restTemplate);
    private ApplicationContext applicationContext;
    private  UtilDate utildate=new UtilDate<Kurs>();


    @FXML
    void addAction(ActionEvent event) {
        Kurs kurs = new Kurs(tfKursName.getText(), null, null);
        kurs.setRaum(cbxRaum.getValue());
        kurs.setLehre(cbxLehre.getValue());
        kurs.setAnfangab(dtpAnfangAb.getValue());
        kurs.setEndebis(dtpEndeBis.getValue());
        kurs.setDauer(Integer.valueOf(tfDauer.getText()));
        kurs.setKurslang(Integer.valueOf(tfKurslang.getText()));
        kurs.setKurstype(cbxUnterrichttype.getValue());
        kurs.setKosten(Double.valueOf(tfPrice.getText()));
        kursClient = new KursClient();
        kursClient.add(kurs);
        getAllKurs();
        fillTableview();
        clearField();
    }

    void fillLehreComboBox() {
        ObservableList<Lehre> lehreObservableList = FXCollections.observableList(getAllLehre());
        cbxLehre.setItems(lehreObservableList);

    }

    void fillUnterrichttypeComboBox() {
        cbxUnterrichttype.setItems(FXCollections.observableArrayList(KursType.values()));
    }

    private List<Lehre> getAllLehre() {
        return lehreClient.getLehreList().collectList().block();

    }

    void fillRaumComboBox() {
        ObservableList<String> raumlist = FXCollections.observableList(List.of("Raum 1", "Raum 2")).sorted();
        cbxRaum.setItems(raumlist);
    }

    @FXML
    void saveAction(ActionEvent event) {
        Kurs updatedKurs = new Kurs();
        kursClient = new KursClient();
        kursClient.update(getUpdatedKursId(), updatedKurs);
        getAllKurs();
        fillTableview();
        clearField();

    }

    private void getAllKurs() {
        list = kursClient.getKursList().collectList().block();
    }

    private void fillTableview() {
        if (tfKursSearch.getText().trim().isEmpty()) {
            getAllKurs();
        }
        kurssData = FXCollections.observableList(list).sorted();
        tbwKurs.setItems(kurssData);
    }

    private void deleteClient(Long id) {
        kursClient = new KursClient();
        kursClient.delete(id);
        getAllKurs();
        fillTableview();
    }

    private void findBy(String param) {
           kursClient = new KursClient();
        if (rbtKursName.isSelected()) {
            list =kursClient.findByName(param).collectList().block();

        } else if (rbtRaum.isSelected()) {
            list = kursClient.findByRaum(param);
        } else if (rbtLehre.isSelected()) {
            list = kursClient.findByLehre(param);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillUnterrichttypeComboBox();
        fillRaumComboBox();
        fillLehreComboBox();
        tbwKurs.setEditable(true);
        tbwKurs.getSelectionModel().setCellSelectionEnabled(true);
        clmKursName.setCellValueFactory(new PropertyValueFactory("name"));
        clmRaum.setCellValueFactory(new PropertyValueFactory("raum"));
        clmLehre.setCellValueFactory(new PropertyValueFactory("lehre"));
        clmLange.setCellValueFactory(new PropertyValueFactory("kurslang"));
        clmDauern.setCellValueFactory(new PropertyValueFactory("dauer"));
        clmKursType.setCellValueFactory(new PropertyValueFactory("kurstype"));
        clmBeginAb.setCellValueFactory(new PropertyValueFactory<>("anfangab"));
        clmEndeBis.setCellValueFactory(new PropertyValueFactory<>("endebis"));
        clmBeginAb.setCellFactory((TableColumn<Kurs, LocalDate> column) ->  utildate.convertColumn(column));
        clmEndeBis.setCellFactory((TableColumn<Kurs, LocalDate> column) -> utildate.convertColumn(column));

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
        tbwKurs.getColumns().setAll(clmKursName, clmRaum, clmLehre, clmLange, clmDauern,clmKursType, clmBeginAb, clmEndeBis, clmDelete, clmUpdate);
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
        tfKurslang.setText("");
        tfDauer.setText("");
        tfPrice.setText("");
        cbxUnterrichttype.setValue(null);
        cbxLehre.setValue(null);
        cbxRaum.setValue(null);
        dtpEndeBis.setValue(null);
        dtpAnfangAb.setValue(null);

    }

    private Long getUpdatedKursId() {
        return updatedKursId;
    }

    private void setUpdatedKursId(Long id) {
        this.updatedKursId = id;
    }

    private void fillFieldForUpdate(Kurs p) {

        tfKursName.setText(p.getName());
        cbxLehre.setValue(p.getLehre());
        tfKurslang.setText(String.valueOf(p.getKurslang()));
        tfDauer.setText(String.valueOf(p.getDauer()));
        cbxUnterrichttype.setValue(p.getKurstype());
        cbxLehre.setValue(p.getLehre());
        cbxRaum.setValue(p.getRaum());
        tfPrice.setText(String.valueOf(p.getKosten()));
        dtpAnfangAb.setValue(p.getAnfangab());
        dtpEndeBis.setValue(p.getEndebis());
        setUpdatedKursId(p.getId());

    }


}
