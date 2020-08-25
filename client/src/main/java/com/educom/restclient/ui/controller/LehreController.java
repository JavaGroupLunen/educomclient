package com.educom.restclient.ui.controller;


import com.educom.restclient.client.LehreClient;
import com.educom.restclient.client.WebClientStockClient;
import com.educom.restclient.model.Lehre;
import com.educom.restclient.util.ActionButtonTableCell;
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
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;


@Component
public class LehreController implements Initializable {
    private final RestTemplate restTemplate = new RestTemplate();
    @FXML
    private TableView tableView;
    private ObservableList<Lehre> lehresData = observableArrayList();

    @FXML
    private TableColumn<Lehre, String> clmVorname, clmName, clmEmail;
    @FXML
    private TextField tfFirstName, tfLastName, tfEmail, tfSearch;
    @FXML
    private TableColumn clmDelete, clmUpdate;
    @FXML
    private TableColumn<Lehre, Integer> clmAge;
    private final WebClient webClient = WebClient.builder().build();
    private List<Lehre> list = null;
    @FXML
    private RadioButton rbtVorname, rbtNachname, rbtEmail;
    @FXML
    private Button btnAdd, btnUpdate, btnDelete, btnSave;
    private Long updatedLehreId;

    @FXML
    private void addAction() throws IOException, URISyntaxException {

        Lehre lehre = new Lehre(tfFirstName.getText(), tfLastName.getText(), tfEmail.getText());
        String codeValue = new WebClientStockClient(webClient).saveLehre(lehre);
        System.out.println(codeValue);
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
        LehreClient restClientTemplate = new LehreClient(restTemplate);
        restClientTemplate.updateLehre(getUpdatedLehreId(),updatedLehre);
        getAllLehre();
        fillTableview();
        clearField();
    }


    private void getAllLehre() {
        list = new WebClientStockClient(webClient).getLehreList().collectList().block();

    }

    private void fillTableview() {
        if (tfSearch.getText().trim().isEmpty()) {
            getAllLehre();
        }

        lehresData = FXCollections.observableList(list).sorted();
        tableView.setItems(lehresData);
    }

    private void deleteClient(Lehre lehre) {
        LehreClient restClientTemplate = new LehreClient(restTemplate);
        restClientTemplate.deleteLehre(lehre);
        getAllLehre();
        fillTableview();
    }

    private void findBy(String param) {
        LehreClient restClientTemplate = new LehreClient(restTemplate);
        if (rbtVorname.isSelected()) {
            list = restClientTemplate.findByName(param);

        } else if (rbtNachname.isSelected()) {
            list = restClientTemplate.findByLastName(param);

        } else if (rbtEmail.isSelected()) {
            list = restClientTemplate.findByEmailId(param);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        btnAdd.getStyleClass().add("button-raised");
//        btnSave.getStyleClass().add("button-raised");
        tableView.setEditable(true);
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        clmVorname.setCellValueFactory(new PropertyValueFactory("firstName"));
        clmName.setCellValueFactory(new PropertyValueFactory("lastName"));
        clmEmail.setCellValueFactory(new PropertyValueFactory("email"));
        //  clmAge.setCellValueFactory(new PropertyValueFactory<Lehre,Integer>("age"));

        clmDelete.setCellFactory(ActionButtonTableCell.<Lehre>forTableColumn("Delete", (Lehre p) -> {
            deleteClient(p);
            return p;
        }));
        clmUpdate.setCellFactory(ActionButtonTableCell.<Lehre>forTableColumn("Update", (Lehre p) -> {
            fillFieldForUpdate(p);
            setUpdatedLehreId(p.getId());
            return p;
        }));
        tableView.getItems().setAll(lehresData);
        tableView.getColumns().setAll(clmVorname, clmName, clmEmail, clmAge, clmDelete, clmUpdate);
      //  getAllLehre();

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

    private void clearField() {
        tfFirstName.setText("");
        tfLastName.setText("");
        tfEmail.setText("");
    }
    private void setUpdatedLehreId(Long id){
            this.updatedLehreId=id;
    }
    private Long getUpdatedLehreId(){
            return updatedLehreId;
    }
    private void fillFieldForUpdate(Lehre p){
            tfFirstName.setText(p.getFirstName());
            tfLastName.setText(p.getLastName());
            tfEmail.setText(p.getEmail());
        }



}