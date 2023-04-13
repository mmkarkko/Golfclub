package fxKerho;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * 
 * @author Miia Arkko
 * @version 8.2.2023
 *
 */
public class LandingGUIController implements ModalControllerInterface<String> {

    @FXML private TextField textVastaus;
    @FXML private ImageView imageView;
    private String vastaus = null;

    
    @FXML private void handleOK() {
        vastaus = textVastaus.getText();
        ModalController.closeStage(textVastaus);
    } 
    

    @FXML private void handleCancel() {
        ModalController.closeStage(textVastaus);
    }

   
    @Override
    public String getResult() {
        return vastaus;
    }
    
    @Override
    public void setDefault(String oletus) {
        textVastaus.setText(oletus);       
    }
    
    
    /**
     * Mitä tehdään, kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        textVastaus.requestFocus();
    }
     
    
    /**
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                LandingGUIController.class.getResource("LandingGUIView.fxml"),
                "Paras golfkerho",
                modalityStage, oletus);
    }    

}
