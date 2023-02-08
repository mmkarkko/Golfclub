package fxKerho;
import javafx.event.ActionEvent;
//import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.*;

/**
 * Luokka Golfkerhon käyttöliittymien tapahtumien hoitamiseksi
 * 
 * @author Miia Arkko
 * @version 30.1.2023
 *
 */
public class KerhoGUIController implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        //
    }
    
    /**
     * Käsitellään uuden kierroksen lisääminen
     * @param event //
     */
    @FXML public void handleLisaaKierros(ActionEvent event) {
        //
    }

    
    /**
     * Käsitellään kierroksen poistaminen
     * @param event //
     */
    @FXML public void handlePoistaKierros(ActionEvent event) {
        //
    }
    
    /**
     * Käsitellään apua-toiminto
     * @param event 
     */
    @FXML void handleApua(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä auttaa");
    }

    /**
     * Käsitellään tietojen avaaminen
     * @param event
     */
    @FXML void handleAvaa(ActionEvent event) {
        //Dialogs.showMessageDialog("Ei osata vielä avata");
        avaa();
    }

    /**
     * Käsitellään lopetuskäsky
     * @param event
     */
    @FXML void handleLopeta(ActionEvent event) {
        //
    }

    /**
     * Käsitellään tietojen muokkaaminen
     * @param event
     */
    @FXML void handleMuokkaaJasen(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä muokata");
        //ModalController.showModal(MuokkaaJasenGUIController.class.getResource("MuokkaaJasenGUIView.fxml"), "Jäsen", modalityStage, oletus);
    }

    /**
     * Käsitellään jäsenen tietojen poistaminen
     * @param event
     */
    @FXML void handlePoistaJasen(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä poistaa");
    }

    /**
     * Käsitellään tallennuskäsky
     * @param event
     */
    @FXML void handleTallenna(ActionEvent event) {
        //
    }

    /**
     * Käsitellään tietoja-käsky
     * @param event
     */
    @FXML void handleTietoja(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä näyttää tietoja");
    }

    /**
     * Käsitellään tulostuskäsky
     * @param event
     */
    @FXML void handleTulosta(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä tulostaa");
    }
    
    /**
     * Käsitellään uuden jäsenen lisääminen
     * @param event 
     */
    @FXML void handleUusiJasen(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä lisätä");
    }
    
    // ============================================================
    
    /**
     * Tietojen tallennus
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetetaan! Mutta ei toimi vielä");
    }
  
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkaa sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    
    /**
     * @return false, jos painetaan peruuta
     */
    public boolean avaa() {
        String uusinimi = LandingGUIController.kysyNimi(null, "Paras golfkerho");
        if (uusinimi == null)return false;
        //lueTiedosto(uusinimi);
        return true;
    }


}