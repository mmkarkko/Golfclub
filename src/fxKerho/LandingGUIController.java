package fxKerho;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LandingGUIController {

    @FXML
    private TextField textVastaus;

    /**
     * Peruuta-painikkeen tapahtumakäsittelijä
     * @param event tapahtuma
     */
    @FXML void handleCancel(ActionEvent event) {
        //
    }

    /**
     * OK-painikkeen tapahtumakäsittelijä
     * @param event tapahtuma
     */
    @FXML void handleOK(ActionEvent event) {
        //
    }

}
