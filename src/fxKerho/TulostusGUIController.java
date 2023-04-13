package fxKerho;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Luokka, joka hoitaa tulostuksen
 * @author Miia Arkko
 * @version 13.4.2023
 *
 */
public class TulostusGUIController  implements ModalControllerInterface<String>{

    @FXML  private TextArea tulostusAlue;

    @FXML void handleOK() {
        ModalController.closeStage(tulostusAlue);
    }

    @FXML void handleTulosta() {
        Dialogs.showMessageDialog("Ei osata vielä tulostaa");
    }

    @Override
    public String getResult() {
        return null;
    }

    @Override
    public void handleShown() {
        //
        
    }

    @Override
    public void setDefault(String oletus) {
        tulostusAlue.setText(oletus);       
    }
    
    
    /**
     * @return alue johon tulostetaan
     */
    public TextArea getTextArea() {
        return tulostusAlue;
    }
    
    
    /**
     * Näyttää tulostusalueessa tekstin
     * @param tulostus tulostettava teskti
     * @return kontrolleri, jolta voidaan pyytää lisää tietoa
     */
    public static TulostusGUIController tulosta(String tulostus) {
        TulostusGUIController tulostusCtrl = 
          ModalController.showModeless(TulostusGUIController.class.getResource("TulostusGUIView.fxml"),
                                       "Tulostus", tulostus);
        return tulostusCtrl;
    }

}