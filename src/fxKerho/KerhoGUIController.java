package fxKerho;
import javafx.event.ActionEvent;
//import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.*;

/**
 * Luokka Golfkerhon käyttöliittymien tapahtumien hoitamiseksi
 * 
 * @author Miia Arkko
 * @version 10.2.2023
 *
 */
public class KerhoGUIController implements Initializable {
    @FXML private TextField textPelaajaEmail;
    @FXML private TextField textPelaajaHCP;
    @FXML private TextField textPelaajaID;
    @FXML private TextField textPelaajaKatu;
    @FXML private TextField textPelaajaNimi;
    @FXML private TextField textPelaajaOsakeNro;
    @FXML private TextField textPelaajaPono;
    @FXML private TextField textPelaajaPuh;
    @FXML private TextField textPelaajanJasenmaksu;
    @FXML private TextField textPelaajanKotiKentta;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        ModalController.showModal(LandingGUIController.class.getResource("LandingGUIView.fxml"), "Golfkerho", null, "Paras golfkerho");
    }
    
    /**
     * Käsitellään uuden kerhon luominen
     * @param event tapahtuma
     */
    @FXML void handleUusiKerho(ActionEvent event) {
        ModalController.showModal(UusiKerhoGUIController.class.getResource("UusiKerhoGUIView.fxml"), "Golfkerho", null, "");
    }

    
    /**
     * Käsitellään uuden kierroksen lisääminen
     * @param event tapahtuma
     */
    @FXML public void handleLisaaKierros(ActionEvent event) {
        ModalController.showModal(MuokkaaKierrosGUIController.class.getResource("MuokkaaKierrosGUIView.fxml"), "Lisää kierros", null, "");
    }

    
    /**
     * Käsitellään kierroksen poistaminen
     * @param event tapahtuma
     */
    @FXML public void handlePoistaKierros(ActionEvent event) {
        ModalController.showModal(EiVoiPoistaaGUIController.class.getResource("EiVoiPoistaaGUIView.fxml"), "Poista kierros", null, "");
    }
    
    /**
     * Käsitellään apua-toiminto
     * @param event  tapahtuma
     */
    @FXML void handleApua(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä auttaa");
    }

    /**
     * Käsitellään tietojen avaaminen
     * @param event tapahtuma
     */
    @FXML void handleAvaa(ActionEvent event) {
        avaa();
    }

    /**
     * Käsitellään lopetuskäsky
     * @param event tapahtuma
     */
    @FXML void handleLopeta(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata lopettaa!");
    }

    /**
     * Käsitellään tietojen muokkaaminen
     * @param event tapahtuma
     */
    @FXML void handleMuokkaaJasen(ActionEvent event) {
        ModalController.showModal(MuokkaaJasenGUIController.class.getResource("MuokkaaJasenGUIView.fxml"), "Jäsen", null, "");
    }

    /**
     * Käsitellään jäsenen tietojen poistaminen
     * @param event tapahtuma
     */
    @FXML void handlePoistaJasen(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä poistaa");
    }

    /**
     * Käsitellään tallennuskäsky
     * @param event tapahtuma
     */
    @FXML void handleTallenna(ActionEvent event) {
        Dialogs.showMessageDialog("Ei osata vielä tallentaa");
    }

    /**
     * Näyttää 'tietoja' ikkunan
     * @param event tapahtuma
     */
    @FXML void handleTietoja(ActionEvent event) {
        ModalController.showModal(LandingGUIController.class.getResource("TietojaGUIView.fxml"), "Tietoja", null, "");
    }

    /**
     * Käsitellään tulostuskäsky
     * @param event tapahtuma
     */
    @FXML void handleTulosta(ActionEvent event) {
        ModalController.showModal(TulostusGUIController.class.getResource("TulostusGUIView.fxml"), "Tulosta", null, "");
        //TulostusController tulostusCtrl = TulostusController.tulosta(null);
        //tulostaValitut(tulostusCtrl.getTextArea());
    }
    
    /**
     * Käsitellään uuden jäsenen lisääminen
     * @param event  tapahtuma
     */
    @FXML void handleUusiJasen(ActionEvent event) {
        ModalController.showModal(UusiJasenGUIController.class.getResource("UusiJasenGUIView.fxml"), "Lisää jäsen", null, "");
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