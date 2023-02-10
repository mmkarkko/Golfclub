package fxKerho;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author Miia Arkko
 * @version 10.2.2023
 *
 */
public class MuokkaaJasenGUIController implements ModalControllerInterface<String> {

    @FXML  private TextField textPelaajanNimi;
    
    /**
     * Käsitellään peruuta-painikkeen painaminen
     * @param event
     */
    @FXML void handleCancel(ActionEvent event) {
        ModalController.closeStage(textPelaajanNimi);
    }
    
    /**
     * Käsitellään ok-painikkeen painaminen
     * @param event
     */
    @FXML void handleOK(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä");
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