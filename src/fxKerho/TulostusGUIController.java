package fxKerho;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * @author Miia Arkko
 * @version 10.2.2023
 *
 */
public class TulostusGUIController  implements ModalControllerInterface<String>{

    @FXML  private TextArea tulostusAlue;

    @FXML void handleOK(ActionEvent event) {
        ModalController.closeStage(tulostusAlue);
    }

    @FXML void handleTulosta(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä tulostaa");
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