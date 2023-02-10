package fxKerho;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * @author Miia Arkko
 * @version 10.2.2023
 *
 */
public class EiVoiPoistaaGUIController implements ModalControllerInterface<String>  {
    
    /**
     * Käsittelee mitä tapahtuu, kun painetaan ok-painiketta
     * @param event tapahtuma
     */
    @FXML void handleOK(ActionEvent event) {
        Dialogs.showMessageDialog("Malttia! Myö ei osata vielä!");
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