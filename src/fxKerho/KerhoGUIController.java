package fxKerho;

import static fxKerho.TietueGUIController.getFieldId;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import kerho.Kerho;
import kerho.Kierros;
import kerho.Pelaaja;
import kerho.SailoException;
import java.net.URI;
import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.*;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.scene.layout.GridPane;


/**
 * Luokka Golfkerhon käyttöliittymien tapahtumien hoitamiseksi
 * 
 * @author Miia Arkko
 * @version 3.4.2023
 *
 */
public class KerhoGUIController implements Initializable {
    
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
    
    @FXML private GridPane gridPelaaja;
    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private Label labelVirhe;
    @FXML private ScrollPane panelPelaaja;   
    @FXML private ListChooser<Pelaaja> chooserPelaajat;
    @FXML private StringGrid<Kierros> tableKierrokset;
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
     

    @FXML private void handleHakuehto() {
        hae(0);
    }
    

    @FXML private void handleUusiKerho() {
        ModalController.showModal(UusiKerhoGUIController.class.getResource("UusiKerhoGUIView.fxml"), "Golfkerho", null, "");
    }


    @FXML private void handleLisaaKierros() {
        uusiKierros();
    }
    
    
    @FXML private void handleMuokkaaKierros() {
        muokkaaKierrosta();
    }

    
    @FXML private void handlePoistaKierros() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa");
    }
    
    
    @FXML private void handleApua() {
        avustus();
    }


    @FXML private void handleAvaa() {
        avaa();
    }

    
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    

    @FXML private void handleUusiJasen() {
        uusiPelaaja();
    }

    
    @FXML private void handleMuokkaaJasen() {
        muokkaa(kentta);
    }


    @FXML private void handlePoistaJasen() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa");
    }


    @FXML private void handleTallenna() {
        tallenna();
    }


    @FXML private void handleTietoja() {
        ModalController.showModal(LandingGUIController.class.getResource("TietojaGUIView.fxml"), "Tietoja", null, "");
    }


    @FXML void handleTulosta() { //TODO: katso vesan gitistä koodista tämän teko
        //ModalController.showModal(TulostusGUIController.class.getResource("TulostusGUIView.fxml"), "Tulosta", null, "");
        TulostusGUIController tulostusCtrl = TulostusGUIController.tulosta(null);
        tulostaValitut(tulostusCtrl.getTextArea());
    }

    
    
    // ============================================================
    
    
    private String kerhonNimi = "Paras Golfkerho";
    private Kerho kerho;
    private TextField edits[];
    private int kentta = 0;
    private static Kierros apukierros = new Kierros();
    private static Pelaaja apupelaaja = new Pelaaja();
    
    
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa jäsenten tiedot.
     * Alustetaan myös pelaajalistan kuuntelija 
     */
    protected void alusta() {
        panelPelaaja.setFitToHeight(true);            
        chooserPelaajat.clear();
        
        cbKentat.clear();
        for (int k  = apupelaaja.ekaKentta(); k < apupelaaja.getKenttia(); k++) {
            cbKentat.add(apupelaaja.getKysymys(k));
        }
        cbKentat.setSelectedIndex(0); // valitaan oletuksena nimi hakuehdoksi
        
        chooserPelaajat.addSelectionListener(e -> naytaPelaaja());
        
        edits = TietueGUIController.luoKentat(gridPelaaja, new Pelaaja());      
        for (TextField edit: edits)   
            if ( edit != null ) {   
                edit.setEditable(false);   
                edit.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaa(getFieldId(edit,kentta)); });  
                edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta)); 
            }

        int eka = apukierros.ekaKentta(); 
        int lkm = apukierros.getKenttia(); 
        String[] headings = new String[lkm-eka]; 
        for (int i=0, k=eka; k<lkm; i++, k++) headings[i] = apukierros.getKysymys(k); 
        tableKierrokset.initTable(headings); 
        tableKierrokset.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        tableKierrokset.setEditable(false); 
        tableKierrokset.setPlaceholder(new Label("Ei vielä kierroksia")); 
         
        // Tämä on vielä huono, ei automaattisesti muutu jos kenttiä muutetaan. 
        tableKierrokset.setColumnSortOrderNumber(1); 
        tableKierrokset.setColumnSortOrderNumber(2); 
        tableKierrokset.setColumnWidth(1, 60); 
        tableKierrokset.setColumnWidth(2, 60); 
        
        tableKierrokset.setEditable(false);
        tableKierrokset.setOnMouseClicked( e -> { if ( e.getClickCount() > 1 ) muokkaaKierrosta(); } );
        tableKierrokset.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaaKierrosta();}); 

    }
    
    
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    

    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }
    
    
    /**
     * Alustaa kerhon lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta kerhon tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    protected String lueTiedosto(String nimi) {
        kerhonNimi = nimi;
        setTitle("Kerho - " + kerhonNimi);
        try {
            kerho.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }

    }
    
    
    /**
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnstui, false jo ei
     */
    public boolean avaa() {
        String uusinimi = LandingGUIController.kysyNimi(null, kerhonNimi);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }
    
    
    /**
     * Tietojen tallennus
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    private String tallenna() {
        try {
            kerho.tallenna();
            return null;
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + e.getMessage());
            return e.getMessage();
        }
    }
    
    
    /**
     * Tarkistetaan onko tallennus tehtyja voiko sovelluksen sulkea
     * @return true jos saa sulkaa sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    
    /**
     * Näyttää listasta valitun pelaajan tiedot tekstikenttiin
     */
    private void naytaPelaaja() {
        Pelaaja pelaaja = chooserPelaajat.getSelectedObject();
        
        if (pelaaja == null) return;
        TietueGUIController.naytaTietue(edits, pelaaja);
        naytaKierrokset(pelaaja);
    }
    
    
    private void naytaKierrokset(Pelaaja pelaaja) {
        tableKierrokset.clear();
        if(pelaaja == null) return;
        
            List<Kierros> kierrokset = kerho.annaKierrokset(pelaaja);
            if(kierrokset.size() == 0) return;
            for (Kierros k : kierrokset)
                naytaKierros(k);
    }
    
    
    private void naytaKierros(Kierros kierros) {
        int kenttia = kierros.getKenttia(); 
        String[] rivi = new String[kenttia-kierros.ekaKentta()]; 
        for (int i=0, k = kierros.ekaKentta(); k < kenttia; i++, k++) 
            rivi[i] = kierros.anna(k); 
        tableKierrokset.add(kierros,rivi);

    }
    
    
    private void muokkaaKierrosta() {
        Pelaaja pelaajaKohdalla = chooserPelaajat.getSelectedObject(); 
        int r = tableKierrokset.getRowNr();
        if ( r < 0 ) return; // klikattu ehkä otsikkoriviä
        Kierros ki = tableKierrokset.getObject();
        if ( ki == null ) return;
        int k = tableKierrokset.getColumnNr()+ki.ekaKentta();
        try {
            ki = TietueGUIController.kysyTietue(null, ki.clone(), k);
            if ( ki == null ) return;
            kerho.korvaaTaiLisaa(ki); 
            naytaKierrokset(pelaajaKohdalla); 
            tableKierrokset.selectRow(r);  // järjestetään sama rivi takaisin valituksi
        } catch (CloneNotSupportedException  e) { /* clone on tehty */  
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä: " + e.getMessage());
        }

    }
    
    
    /**
     * Muokataan pelaajan tietoja, kloonilla, jotta voidaan tarvittaessa peruuttaa
     */
    private void muokkaa(int k) {
        Pelaaja jasen = chooserPelaajat.getSelectedObject(); 
        if (jasen == null) return;
        try {
            jasen = jasen.clone();
        } catch (CloneNotSupportedException e) {
            // Ei voi tapahtua
        }
        jasen = TietueGUIController.kysyTietue(null, jasen, k);
        if (jasen == null) return;
        try {
            kerho.korvaaTaiLisaa(jasen);
        } catch (SailoException e) {
            // TODO: näytä dialogi virheestä
        }
        hae(jasen.getpelaajaNro());

    }
    
    
    /**
     * Haetaan pelaajat uudelleen
     * @param jnr mikä pelaaja valitaan aktiiviseksi, jos 0 niin aktivoidaan nykyinen pelaaja
     */
    private void hae(final int jnr) {
        int jnro = jnr;
        
        if (jnro == 0) {
            Pelaaja kohdalla = chooserPelaajat.getSelectedObject();
            if (kohdalla != null) jnro = kohdalla.getpelaajaNro();
        }
        
        int k = cbKentat.getSelectionModel().getSelectedIndex() + apupelaaja.ekaKentta();
        
        chooserPelaajat.clear();  
        String ehto = hakuehto.getText(); 
        if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";
        
        Collection<Pelaaja> pelaajat = kerho.etsi(ehto, k);
        
        int index = 0;
        int ci = 0;
        for (Pelaaja pelaaja: pelaajat) {
            if (pelaaja.getpelaajaNro() == jnro) index = ci;
            chooserPelaajat.add("" + pelaaja.getNimi(), pelaaja);
            ci++;
        }
        chooserPelaajat.setSelectedIndex(index);
    }
        
    
    /**
     * Luo uuden pelaajan, jota aletaan editoimaan
     */
    protected void uusiPelaaja() {
        Pelaaja uusi = new Pelaaja();

        uusi = TietueGUIController.kysyTietue(null, uusi, uusi.ekaKentta()); 
        if (uusi == null) return;
        uusi.rekisteroi();
        uusi.rekisteroiOsake();
        try {
            kerho.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        hae(uusi.getpelaajaNro());

    }

    
    private void uusiKierros() {
        Pelaaja pelaajaKohdalla = chooserPelaajat.getSelectedObject(); 
        if ( pelaajaKohdalla == null ) return;

        Kierros uusi = new Kierros(pelaajaKohdalla.getpelaajaNro());
        uusi = TietueGUIController.kysyTietue(null, uusi, 0);
        if ( uusi == null ) return;
        uusi.rekisteroi();
        try {
            kerho.lisaa(uusi);
        } catch (SailoException e) {
            // Näytä dialogi
        }
        naytaKierrokset(pelaajaKohdalla); 
        tableKierrokset.selectRow(1000);  // järjestetään viimeinen rivi valituksi
    }
    

    /**
     * @param kerho jota käytetään tässä käyttöliittymässä
     */
    public void setKerho(Kerho kerho) {
        this.kerho = kerho;
        naytaPelaaja();
    }
    
    
    /**
     * Näytetään ohjelman suunnitelma erillisessä selaimessa.
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2023k/ht/mmkarkko");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
    
    
    /**
     * Tulostaa pelaajan tiedot
     * @param os tietovirta johon tulostetaan
     * @param pelaaja tulostettava pelaaja
     */
    public void tulosta(PrintStream os, final Pelaaja pelaaja) {
        os.println("----------------------------------------------");
        pelaaja.tulosta(os);
        os.println("----------------------------------------------");
        List<Kierros> kierrokset = kerho.annaKierrokset(pelaaja);
        for (Kierros k:kierrokset) 
            k.tulosta(os);     
    }
    
    
    /**
     * Tulostaa listassa olevat jäsenet tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan kaikki jäsenet");
            Collection<Pelaaja> pelaajat = kerho.etsi("", -1); 
            for (Pelaaja pelaaja:pelaajat) { 
                tulosta(os, pelaaja);
                os.println("\n\n");
            }
        }
    }
}