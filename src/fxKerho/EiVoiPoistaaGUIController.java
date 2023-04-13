package fxKerho;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author Miia Arkko
 * @version 10.2.2023
 *
 */
public class EiVoiPoistaaGUIController implements ModalControllerInterface<String>  {
    
    @FXML private Button textButton;
    
    
    /**
     * Käsittelee mitä tapahtuu, kun painetaan ok-painiketta
     */
    @FXML void handleOK() {
        ModalController.closeStage(textButton);
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