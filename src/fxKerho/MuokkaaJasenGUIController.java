package fxKerho;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kerho.Pelaaja;

/**
 * Kysytään jäsenen tiedot luomalla sille uusi dialogi
 * @author Miia Arkko
 * @version 7.3.2023
 *
 */
public class MuokkaaJasenGUIController implements ModalControllerInterface<Pelaaja>, Initializable {

    @FXML private TextField editEmail;
    @FXML private TextField editHcp;
    @FXML private TextField editHetu;
    @FXML private TextField editJasMaksu;
    @FXML private TextField editKatuos;
    @FXML private TextField editKotiKentta;
    @FXML private TextField editOsakeNro;
    @FXML private TextField editPelaajanNimi;
    @FXML private TextField editPelaajanNumero;
    @FXML private TextField editPostios;
    @FXML private TextField editPuh;

    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();       
    }
    
    
    /**
     * Käsitellään peruuta-painikkeen painaminen
     * @param event
     */
    @FXML void handleCancel(ActionEvent event) {
        ModalController.closeStage(editPelaajanNimi);
    }
    
    
    /**
     * Käsitellään ok-painikkeen painaminen
     * @param event
     */
    @FXML void handleOK(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä");
    }

   
    //-----------------------------------------------------
    
    private Pelaaja pelaajaKohdalla;
    private TextField edits[];

    
    @Override
    public void setDefault(Pelaaja oletus) {
        pelaajaKohdalla = oletus;
        naytaPelaaja(edits, pelaajaKohdalla);
    }
    
    
    @Override
    public Pelaaja getResult() {
        return pelaajaKohdalla;
    }

    
    /**
     * Mitä tehdään, kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        editPelaajanNimi.requestFocus();       
    }
    
    
    /**
     * Tekee tarvittavat muut alustukset.
     */
    protected void alusta() {
        edits = new TextField[] {editPelaajanNumero,
                                editPelaajanNimi, 
                                editHetu, 
                                editHcp, 
                                editPuh, 
                                editEmail, 
                                editKatuos, 
                                editPostios, 
                                editOsakeNro, 
                                editJasMaksu, 
                                editKotiKentta};
    }
    
    
    /**
     * Näytetään pelaajan tiedot TextField-komponentteihin
     * @param edits taulukko, jossa tekstikenttiä
     * @param pelaaja jonka tiedot näytetään
     */
    public static void naytaPelaaja(TextField[] edits, Pelaaja pelaaja) {
        if (pelaaja == null) return;
        
        edits[0].setText(Integer.toString(pelaaja.getpelaajaNro()));
        edits[1].setText(pelaaja.getNimi());
        edits[2].setText(pelaaja.getHetu());
        edits[3].setText(Double.toString(pelaaja.getTasoitus()));
        edits[4].setText(pelaaja.getPuh());
        edits[5].setText(pelaaja.getEmail());
        edits[6].setText(pelaaja.getKatuos());
        edits[7].setText(pelaaja.getPostios());
        edits[8].setText(Integer.toString(pelaaja.getOsakeNro()));
        edits[9].setText(pelaaja.getJasMaksu());
        edits[10].setText(pelaaja.getKentta());       
    }
    
    
    /**
     * Näytetään pelaajan tiedot textfield-komponentteihin
     * @param pelaaja jonka tiedot näytetään
     */
    public void naytaPelaaja(Pelaaja pelaaja) {
        naytaPelaaja(edits, pelaaja);
    }
    
    
    /**
     * @param modalityStage mille ollaan modaalisia, null=sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @return null, jos painetaan cancel, muuten täytetty tietue
     */
    public static Pelaaja kysyPelaaja(Stage modalityStage, Pelaaja oletus) {
        return ModalController.<Pelaaja, MuokkaaJasenGUIController>showModal(
                MuokkaaJasenGUIController.class.getResource("MuokkaaJasenGUIView.fxml"),
                "Kerho", //TODO: tarkista kerho-kohta
                modalityStage, oletus, null 
            );
    }

    
    /**
     * Tyhjentään tekstikentät 
     * @param edits taulukko jossa tyhjennettäviä tekstikenttiä
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit: edits)
            edit.setText("");
    }
     
}