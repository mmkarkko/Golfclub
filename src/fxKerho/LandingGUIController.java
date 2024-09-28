package fxKerho;

import javafx.event.ActionEvent;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * 
 * @author miiaa
 * @version 8.2.2023
 *
 */
public class LandingGUIController implements ModalControllerInterface<String> {

    @FXML private TextField textVastaus;
    @FXML private ImageView imageView;


    /**
     * Peruuta-painikkeen tapahtumakäsittelijä
     * @param event tapahtuma
     */
    @FXML void handleCancel(ActionEvent event) {
        ModalController.closeStage(textVastaus);
    }

    /**
     * OK-painikkeen tapahtumakäsittelijä
     * @param event tapahtuma
     */
    @FXML void handleOK(ActionEvent event) {
        vastaus = textVastaus.getText();
        ModalController.closeStage(textVastaus);
    }
    
    

    
    
    // =====================================================
    
    private String vastaus = null;
    
    @Override
    public String getResult() {
        return vastaus;
    }
    
    @Override
    public void setDefault(String oletus) {
        textVastaus.setText(oletus);       
    }
    
    @Override
    public void handleShown() {
        textVastaus.requestFocus();
    }
    
   
    
    /**
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä käytetään oletuksena
     * @return null, jos painetaan peruuta. Muuten kirjoitettu nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                LandingGUIController.class.getResource("LandingGUIView.fxml"),
                "Paras golfkerho",
                modalityStage, oletus);
    }
    
    

}
