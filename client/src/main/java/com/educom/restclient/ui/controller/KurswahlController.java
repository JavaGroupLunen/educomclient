package com.educom.restclient.ui.controller;

import com.educom.restclient.client.KursClient;
import com.educom.restclient.model.Kurs;
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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class KurswahlController implements Initializable {
    private static Kurs auswahl;
   // private final WebClient webClient = WebClient.builder().build();
    private final RestTemplate restTemplate = new RestTemplate();
    @FXML
    private RadioButton rbtKursName1, rbtRaum1, rbtLehre1;
    @FXML
    private TextField tfKursSearch1;
    @FXML
    private TableView tbwKursAuswahlFensterTable;
    @FXML
    private TableColumn<Kurs, String> clmKursName, clmRaum, clmLehre;
    @FXML
    private TableColumn<Kurs, Double> clmKosten;
    @FXML
    private TableColumn<Kurs, Integer> clmLange, clmDauern;
    @FXML
    private TableColumn<Kurs, LocalDate> clmBeginAb, clmEndeBis;
    private  TableColumn chKursWahl = new TableColumn<>("Kurswahl");
    private ObservableList<Kurs> kurssData = observableArrayList();
    private List<Kurs> list = null;
    private Long updatedKursId;
    private KursClient kursClient=new KursClient();
    private ApplicationContext applicationContext;
    private UtilDate utildate=new UtilDate<Kurs>();

    private void getAllKurs() {
        list = kursClient.getKursList().collectList().block();

    }

    private void fillTableview() {
        if (tfKursSearch1.getText().trim().isEmpty()) {
            getAllKurs();
        }
        kurssData = FXCollections.observableList(list).sorted();
        tbwKursAuswahlFensterTable.setItems(kurssData);
    }

    private void deleteClient(Long id) {
        kursClient = new KursClient();
        kursClient.delete(id);
        getAllKurs();
        fillTableview();
    }

    private void findBy(String param) {
        kursClient = new KursClient();
        if (rbtKursName1.isSelected()) {
            list =kursClient.findByName(param).collectList().block();

        } else if (rbtRaum1.isSelected()) {
            list = kursClient.findByRaum(param);
        } else if (rbtLehre1.isSelected()) {
            list = kursClient.findByLehre(param);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillKursFenbsterTable();
        ToggleGroup searchKursGroup = new ToggleGroup();
        rbtRaum1.setToggleGroup(searchKursGroup);
        rbtKursName1.setToggleGroup(searchKursGroup);
        rbtLehre1.setToggleGroup(searchKursGroup);

    }
   private void fillKursFenbsterTable(){
        tbwKursAuswahlFensterTable.setEditable(true);
        tbwKursAuswahlFensterTable.getSelectionModel().setCellSelectionEnabled(true);
        clmKursName.setCellValueFactory(new PropertyValueFactory("name"));
        clmRaum.setCellValueFactory(new PropertyValueFactory("raum"));
        clmLehre.setCellValueFactory(new PropertyValueFactory("lehre"));
        clmLange.setCellValueFactory(new PropertyValueFactory("kurslang"));
        clmDauern.setCellValueFactory(new PropertyValueFactory("dauer"));
        clmBeginAb.setCellValueFactory(new PropertyValueFactory("anfangab"));
        clmEndeBis.setCellValueFactory(new PropertyValueFactory("endebis"));
        clmKosten.setCellValueFactory(new PropertyValueFactory<>("kosten"));
        chKursWahl.setCellFactory(ActionButtonTableCell.forTableColumn("wahlen", (Kurs p) -> {
            loadVertragController(p);
            return p;
        }));
       clmBeginAb.setCellFactory((TableColumn<Kurs, LocalDate> column) ->  utildate.convertColumn(column));
       clmEndeBis.setCellFactory((TableColumn<Kurs, LocalDate> column) -> utildate.convertColumn(column));

        tbwKursAuswahlFensterTable.getItems().setAll(kurssData);
        tbwKursAuswahlFensterTable.getColumns().setAll(clmKursName, clmRaum, clmLehre,clmLange,clmDauern,clmBeginAb,clmEndeBis, chKursWahl);
        fillTableview();

        tfKursSearch1.textProperty().addListener(new ChangeListener<String>() {
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
    }

    private void loadVertragController(Kurs auswahl) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vertrag.fxml"));
            Parent root = loader.load();
            VertragContoller vertragContoller = loader.getController();
            vertragContoller.fillKursField(auswahl);
            vertragContoller.expendAccordion();
            Scene rootScene = new Scene(root);
            Stage stage = vertragContoller.getVertragWindows();
            stage.setScene(rootScene);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }


    @FXML
    private void singoutHandle(ActionEvent event) throws IOException, URISyntaxException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

}
