package fxKerho;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kerho.Kentta;
/**
 * @author Miia Arkko
 * @version 14.4.2023
 */


public class UusiKerhoGUIController implements ModalControllerInterface<Kentta>, Initializable {

    @FXML private TextField textKerhonNumero;
    @FXML private TextField textKerhonPostiOs;
    @FXML private TextField textKerhonPuh;
    @FXML private TextField textKerhonKatuOs;
    @FXML private TextField textKerhonNimi;
    @FXML private TextField textKerhonPuhNro;
    @FXML private Label     labelVirhe;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }


    @FXML void handleCancel() {
        ModalController.closeStage(textKerhonNimi);
    }

    /**
     * Käsittelijä kun painetaan ok-näppäintä
     */
    @FXML void handleTallennaKerho() {

       ModalController.closeStage(labelVirhe);
    }

    @Override
    public Kentta getResult() {
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub      
    }

    @Override
    public void setDefault(Kentta oletus) {
        kerho = oletus;
        
    }
    
    
    // -----------------------------------------------------------
    
    private TextField[] edits;
    private int kentta;
    private Kentta kerho;
    
    
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }


    /**
     * Tekee tarvittavat muut alustukset.
     */
    protected void alusta() {
        edits = new TextField[] {textKerhonNumero,
                                textKerhonNimi,
                                textKerhonKatuOs,
                                textKerhonPostiOs, 
                                textKerhonPuhNro};

        int i = 0;
        for (TextField edit: edits) {
            final int k = ++i;
            edit.setOnKeyReleased(e -> kasitteleMuutosKenttaan(k, (TextField)(e.getSource())));
        }
    }
    
    
    /**
     * @param modalityStage mille ollaan modaalisia, null=sovellukselle
     * @param oletus mitä dataan näytetään oletuksena
     * @return null, jos painetaan cancel, muuten täytetty tietue
     */
    public static Kentta kysyKentta(Stage modalityStage, Kentta oletus) {
        return ModalController.<Kentta, UusiKerhoGUIController>showModal(
                UusiKerhoGUIController.class.getResource("UusiKerhoGUIView.fxml"),
                "Kerho", //TODO: tarkista kerho-kohta
                modalityStage, oletus, null 
            );
    }
    
    
    /**
     * Käsitellään pelaajaan tullut muutos
     * @param k 
     * @param edit muuttunut kenttä
     */
    private void kasitteleMuutosKenttaan(int k, TextField edit) {
        if (kerho == null) return;
        String s = edit.getText();
        String virhe = null;
        switch(k) {
        case  1: virhe = kerho.setKerhonNimi(s);    break;
        case  2: virhe = kerho.setKerhonKatuOs(s);  break;
        case  3: virhe = kerho.setKerhonPostiOs(s); break;
        case  4: virhe = kerho.setKerhonPuhNro(s);  break;
        default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().add(virhe);
            naytaVirhe(virhe);
        }
    }
    
}