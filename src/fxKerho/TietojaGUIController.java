package fxKerho;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

/**
 * @author Miia Arkko
 * @version 10.2.2023
 *
 */
public class TietojaGUIController  implements ModalControllerInterface<String> {
    
    @Override
    public String getResult() {
        return vastaus;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub
        
    }
    
    
    // ===================================================
    
    private String vastaus = null;
    
    
    
}