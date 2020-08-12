package com.educom.restclient.ui.controller;

import com.educom.restclient.client.KursClient;
import com.educom.restclient.client.WebClientStockClient;
import com.educom.restclient.model.Kurs;
import com.educom.restclient.util.ActionButtonTableCell;
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
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class KurswahlController implements Initializable {
    private static Kurs auswahl;
    private final WebClient webClient = WebClient.builder().build();
    private final RestTemplate restTemplate = new RestTemplate();
    @FXML
    private RadioButton rbtKursName1, rbtRaum1, rbtLehre1;
    @FXML
    private TextField tfKursSearch1;
    @FXML
    private TableView<Kurs> tbwKurs1;
    @FXML
    private TableColumn<Kurs, String> clmKursName, clmRaum, clmLehre;
    @FXML
    private TableColumn<Kurs, Double> clmKosten;
    @FXML
    private TableColumn<Kurs, Integer> clmLange, clmDauern;
    @FXML
    private TableColumn<Kurs, Date> clmBeginAb, clmEndeBis;
    private ObservableList<Kurs> kurssData = observableArrayList();
    private List<Kurs> list = null;
    private Long updatedKursId;
    private KursClient kursClient;
    private ApplicationContext applicationContext;

    private void getAllKurs() {
        list = new WebClientStockClient(webClient).getKursList().collectList().block();

    }

    private void fillTableview() {
        if (tfKursSearch1.getText().trim().isEmpty()) {
            getAllKurs();
        }
        kurssData = FXCollections.observableList(list).sorted();
        tbwKurs1.setItems(kurssData);
    }

    private void deleteClient(Long id) {
        kursClient = new KursClient(restTemplate);
        kursClient.delete(id);
        getAllKurs();
        fillTableview();
    }

    private void findBy(String param) {
        kursClient = new KursClient(restTemplate);
        if (rbtKursName1.isSelected()) {
            list = kursClient.findByName(param);

        } else if (rbtRaum1.isSelected()) {
            list = kursClient.findByRaum(param);
        } else if (rbtLehre1.isSelected()) {
            list = kursClient.findByLehre(param);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbwKurs1.setEditable(true);
        tbwKurs1.getSelectionModel().setCellSelectionEnabled(true);
        clmKursName.setCellValueFactory(new PropertyValueFactory("name"));
        clmRaum.setCellValueFactory(new PropertyValueFactory("raum"));
        clmLehre.setCellValueFactory(new PropertyValueFactory("lehre"));
        clmLange.setCellValueFactory(new PropertyValueFactory("kurslang"));
        clmDauern.setCellValueFactory(new PropertyValueFactory("dauer"));
        clmBeginAb.setCellValueFactory(new PropertyValueFactory("anfangAb)"));
        clmBeginAb.setCellValueFactory(new PropertyValueFactory("endeBis)"));
        TableColumn chKursWahl = new TableColumn<>("Kurswahl");
        chKursWahl.setCellFactory(ActionButtonTableCell.forTableColumn("wahlen", (Kurs p) -> {
            loadVertragController(p);
            return p;
        }));

        tbwKurs1.getItems().setAll(kurssData);
        tbwKurs1.getColumns().setAll(clmKursName, clmRaum, clmLehre, chKursWahl);
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
        ToggleGroup searchKursGroup = new ToggleGroup();

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
