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
     * Käsitellään apua-toiminto
     * @param event 
     */
    @FXML void handleApua(ActionEvent event) {
        //
    }

    /**
     * Käsitellään tietojen avaaminen
     * @param event
     */
    @FXML void handleAvaa(ActionEvent event) {
        //
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
    @FXML void handleMuokkaa(ActionEvent event) {
        //
    }

    /**
     * Käsitellään jäsenen tietojen poistaminen
     * @param event
     */
    @FXML void handlePoistaJasen(ActionEvent event) {
        //
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
        //
    }

    /**
     * Käsitellään tulostuskäsky
     * @param event
     */
    @FXML void handleTulosta(ActionEvent event) {
        //
    }
    
    /**
     * Käsitellään uuden jäsenen lisääminen
     * @param event
     */
    @FXML void handleUusiJasen(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä lisätä");
    }
    
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
}