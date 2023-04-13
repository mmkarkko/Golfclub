package fxKerho;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author Miia Arkko
 * @version 10.2.2023
 *
 */
public class UusiJasenGUIController implements ModalControllerInterface<String> {

    @FXML  private TextField textPelaajanNimi;
    
    /**
     * Käsitellään peruuta-painikkeen painaminen

     */
    @FXML void handleCancel() {
        ModalController.closeStage(textPelaajanNimi);
    }
    
    /**
     * Käsitellään ok-painikkeen painaminen

     */
    @FXML void handleOK() {
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