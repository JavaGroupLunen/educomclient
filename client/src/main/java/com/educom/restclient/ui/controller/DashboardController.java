package com.educom.restclient.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DashboardController implements Initializable {
    @FXML
    private AnchorPane rootPane,anpLogin;


    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            rootPane.getChildren().retainAll(root);
            rootPane.getChildren().addAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void loadLehre(ActionEvent event2)  {
        loadStage("/lehre.fxml");

    }
    @FXML
    private void loadSchuler(ActionEvent event2) {
        loadStage("/schuler.fxml");
    }

    @FXML
    private void loadKurs(ActionEvent event2) {

        loadStage("/kurs.fxml");
    }

    @FXML
    private void loadVertrag(ActionEvent event2) {
        loadStage("/vertrag.fxml");

    }
    @FXML
    private void loadStundenplan(ActionEvent event2) {
        loadStage("/stundenplan.fxml");

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadStage("/loginform.fxml");
    }
    @FXML
    private void singoutHandle(ActionEvent event) throws IOException, URISyntaxException {
       // Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //System.exit(0);
        getStage().close();
    }

    private Stage getStage() {
        return (Stage) rootPane.getScene().getWindow();
    }

//    private void initDrawer() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/assistant/ui/main/toolbar/toolbar.fxml"));
//            VBox toolbar = loader.load();
//            drawer.setSidePane(toolbar);
//            ToolbarController controller = loader.getController();
//            controller.setBookReturnCallback(this);
//        } catch (IOException ex) {
//            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
//        task.setRate(-1);
//        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
//            drawer.toggle();
//        });
//        drawer.setOnDrawerOpening((event) -> {
//            task.setRate(task.getRate() * -1);
//            task.play();
//            drawer.toFront();
//        });
//        drawer.setOnDrawerClosed((event) -> {
//            drawer.toBack();
//            task.setRate(task.getRate() * -1);
//            task.play();
//        });
//    }

}
