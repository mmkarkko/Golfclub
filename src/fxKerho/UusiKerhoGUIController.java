package fxKerho;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
/**
 * @author Miia Arkko
 * @version 10.2.2023
 */


public class UusiKerhoGUIController implements ModalControllerInterface<String> {

    @FXML private TextField kerhonPono;

    @FXML private TextField kerhonPuh;

    @FXML private TextField textKerhonKatu;

    @FXML private TextField textKerhonNimi;

    @FXML private TextField textKerhonNumero;

    /**
     * Tapahtumakäsittelijä peruuta-painikkeen painamiselle

     */
    @FXML void handleCancel() {
        ModalController.closeStage(textKerhonNimi);
    }

    /**
     * Käsittelijä kun painetaan ok-näppäintä
     */
    @FXML void handleTallennaKerho() {
        Dialogs.showMessageDialog("Ei osata vielä tallentaa uutta kerhoa");
    }

    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub
        
    }

}