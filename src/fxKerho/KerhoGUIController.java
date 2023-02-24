package fxKerho;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import kerho.Kerho;
import kerho.Pelaaja;
import kerho.SailoException;

import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.*;

/**
 * Luokka Golfkerhon käyttöliittymien tapahtumien hoitamiseksi
 * 
 * @author Miia Arkko
 * @version 24.2.2023
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
    @FXML private TextField textPelaajanPelaajamaksu;
    @FXML private TextField textPelaajanKotiKentta;
    @FXML private ListChooser<Pelaaja> chooserPelaajat;
    @FXML private ScrollPane panelPelaaja;
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
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
        uusiPelaaja();
        //ModalController.showModal(UusiPelaajaGUIController.class.getResource("UusiPelaajaGUIView.fxml"), "Lisää jäsen", null, "");
    }
    
    
    
    // ============================================================
    
    
    
    private Kerho kerho;
    private TextArea areaPelaaja = new TextArea(); // TODO: poista lopuksi
    
    
    /**
     * Alustetaan
     */
    private void alusta() {
        chooserPelaajat.clear();
        areaPelaaja.setFont(new Font("Courier New", 12));
        panelPelaaja.setContent(areaPelaaja);
        panelPelaaja.setFitToHeight(true);
        chooserPelaajat.addSelectionListener(e -> naytaPelaaja());
        
    }
    
    
    /**
     * Näyttää pelaajan tiedot
     */
    private void naytaPelaaja() {
        Pelaaja pelaajaKohdalla = chooserPelaajat.getSelectedObject();
        
        if (pelaajaKohdalla == null) return;
        areaPelaaja.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaPelaaja)) {
            pelaajaKohdalla.tulosta(os);
        }  
    }
    
    
    /**
     * Avataan 
     * @return false, jos painetaan peruuta
     */
    public boolean avaa() {
        String uusinimi = LandingGUIController.kysyNimi(null, "Paras golfkerho");
        if (uusinimi == null)return false;
        //lueTiedosto(uusinimi);
        return true;
    }
    
    
    /**
     * Haetaan jäsenet uudelleen
     * @param jnro mikä jäsen valitaan aktiiviseksi
     */
    private void hae(int jnro) {
        chooserPelaajat.clear();
    
        int index = 0;
        for (int i = 0; i < kerho.getPelaajia(); i++) {
            Pelaaja pelaaja = kerho.annaPelaaja(i);
            if (pelaaja.getpelaajaNro() == jnro) index = i;
            chooserPelaajat.add(pelaaja.getNimi(), pelaaja);
        }
        chooserPelaajat.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää pelaajan
    }

     
    /**
     * Asetetaan käytettävä kerho
     * @param kerho jota käytetään
     */
    public void setKerho(Kerho kerho) {
        this.kerho = kerho;
    }
    
    
    /**
     * Tietojen tallennus
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetaan! Mutta ei toimi vielä");
    }

    
    /**
     * Lisätään kerhoon uusi pelaaja
     */
    private void uusiPelaaja() {
        Pelaaja uusi = new Pelaaja();
        uusi.rekisteroi();
        uusi.rekisteroiOsake();
        uusi.vastaaAkuAnkka();
        try {
            kerho.lisaa(uusi);
        } catch (SailoException e) { 
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
        }
        hae(uusi.getpelaajaNro());
    }
  
    
    /**
     * Tarkistetaan onko tallennus tehtyja voiko sovelluksen sulkea
     * @return true jos saa sulkaa sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }

}