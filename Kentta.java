package kerho;

import java.io.OutputStream;
import java.io.PrintStream;
import fi.jyu.mit.ohj2.Mjonot;
import kanta.Tietue;

/**
 * @author Miia Arkko
 * @version 14.4.2023
 *
 */
public class Kentta implements Cloneable, Tietue{
    
    private int    kerhoId      = 0;
    private String kerhonNimi    = "";
    private String kerhonKatuOs  = "";
    private String kerhonPostiOs = "";
    private String kerhonPuhNro  = "";

    private static int seuraavaKerhoNro = 1;
    
    /**
     * Oletusmuodostaja
     */
    public Kentta() {
        
    }
    
    
    /**
     * 
     * @param k kenttä
     * @return kentän sisältö merkkijonona
     */
    public String getAvain(int k) {
        switch ( k ) {
            case 0: return "" + kerhoId;
            case 1: return "" + kerhonNimi.toUpperCase();
            case 2: return "" + kerhonKatuOs.toUpperCase();
            case 3: return "" + kerhonPostiOs.toLowerCase();
            case 4: return "" + kerhonPuhNro;
        default: return "Äääliö";
        }
    }
    
    
    @Override
    public String getKysymys(int k) {
        switch ( k ) {
            case 0: return "Kerho ID";
            case 1: return "Kerhon nimi";
            case 2: return "Kerhon katuosoite";
            case 3: return "Kerhon postiosoite";
            case 4: return "Kerhon puhelinnumero";
        default: return "Äääliö";
        }
    }


    @Override
    public String anna(int k) {
        switch ( k ) {
            case 0: return "" + kerhoId;
            case 1: return "" + kerhonNimi.toUpperCase();
            case 2: return "" + kerhonKatuOs.toUpperCase();
            case 3: return "" + kerhonPostiOs.toLowerCase();
            case 4: return "" + kerhonPuhNro;
            default: return "Äääliö";
        }
    }
    
    
    /**
     * Rekisteröi uuden kentän
     * @return kentan tunnusnumero
     * @example
     * <pre name="test">
     *  Kentta p1 = new Kentta();
     *  p1.getKerhoId() === 0;
     *  p1.rekisteroiKentta();
     *  Kentta p2 = new Kentta();
     *  p2.rekisteroiKentta();
     *  int n1 = 1;
     *  int n2 = 2;
     *  n2 === n1+1;
     * </pre>
     */
    public int rekisteroiKentta() {
        kerhoId = seuraavaKerhoNro;
        seuraavaKerhoNro++;
        return kerhoId;        
    }
    
    
    /**
     * Palauttaa kerhon tunnusnumeron
     * @return tunnusnumero
     */
    public int getKerhoId() {
        return kerhoId;
    }
    
    
    /**
     * Hakee kentän nimen
     * @return palauttaa kentän nimen
     */
    public String getKerhonNimi() {
        return kerhonNimi;
    }
    
    
    /**
     * Asettaa kentän tunnusnumeron ja varmistaa, että
     * seuraava numero on aina suurempi, kuin tähän mennessä suurin.
     * @param nro asetettava kenttäId
     */
    public void setKerhoId(int nro) {
        kerhoId = nro;
        if (kerhoId >= seuraavaKerhoNro) seuraavaKerhoNro = kerhoId +1;
    }
    
    
    /**
     * @param s Kentälle asetettava nimi
     * @return virheilmoitus. Null, jos ok.
     */
    public String setKerhonNimi(String s) {
        kerhonNimi = s;
        return null;
    }
    
    
    /**
     * @param s Kentälle asetettava nimi
     * @return virheilmoitus. Null, jos ok.
     */
    public String setKerhonKatuOs(String s) {
        kerhonKatuOs = s;
        return null;
    }
    
    
    /**
     * @param s Kentälle asetettava nimi
     * @return virheilmoitus. Null, jos ok.
     */
    public String setKerhonPostiOs(String s) {
        kerhonPostiOs = s;
        return null;
    }
    
    
    /**
     * @param s Kentälle asetettava nimi
     * @return virheilmoitus. Null, jos ok.
     */
    public String setKerhonPuhNro(String s) {
        kerhonPuhNro = s;
        return null;
    }
    
    
    /**
     * @return montako kenttaa lomakkeessa on
     */
    @Override
    public int getKenttia() {
        return 5;
    }
    
    
    /**
     * Eka kenttä joka on mielekäs kysyttäväksi
     * @return ekan kentän indeksi
     */
    @Override
    public int ekaKentta() {
        return 0;
    }
    
    
    /**
     * Selvittää kentän tiedot
     * Pitää huolen, että seuraanNro on suurempi kuin tuleva tunnusnumero
     * @param rivi josta kentän tiedot otetaan
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));
    }
    
    
    /**
     * Palauttaa kentän tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return jäsen tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Kentta k1 = new Kentta();
     *   k1.parse("   1  |  Uusi Golfkerho   | Uusiosoite 2  |  11111 Golfkunta  | 000-1111111 ");
     *   k1.toString().startsWith("1|Uusi Golfkerho|Uusiosoite 2|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
        return "" +
                getKerhoId() + "|" +
                kerhonNimi + "|" +
                kerhonKatuOs + "|" +
                kerhonPostiOs  + "|" +
                kerhonPuhNro + "|";
    }
    
    
    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    
    /**
     * Kloonataan kenttä
     */
    @Override
    public Kentta clone() throws CloneNotSupportedException {
        Kentta uusi = (Kentta) super.clone();
        return uusi;
    }
    
    
    @Override
    public int hashCode() {
        return kerhoId;
    }

    
    
    /**
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kentön arvo asetetaan
     * @param jono joka kentän arvoksi asetetaan
     * @return null, jos asettaminen onnistuu. Muuten vastaava virheilmoitus.
     * @example
     * <pre name="test">
     *   Kentta kentta = new Kentta();
     *   kentta.aseta(1,"Golfkerho") === null;
     *   kentta.aseta(2,"Osoite 1") === null;
     *   kentta.aseta(3,"55555 kunta") === null;
     * </pre>
     */
    @Override
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch ( k ) {
            case 0: 
                setKerhoId(Mjonot.erota(sb, '§', getKerhoId()));
                return null;
            case 1:
                kerhonNimi = tjono;
                return null;
            case 2:
                kerhonKatuOs = tjono;
                return null;
            case 3:
                kerhonPostiOs = tjono;
                return null;
            case 4:
                kerhonPuhNro = tjono;
                return null;
        default:
            return "Ääliö";
        }
    }
    
    
    /**
     * täyttää kerhon kentät
     */
    public void vastaaAkuAnkka() {
        kerhonNimi    = "Uusi golfkerho";
        kerhonKatuOs  = "Uusiosoite 2";
        kerhonPostiOs = "11111 Golfkunta";
        kerhonPuhNro  = "000-1111111";
    }
    
    /**
     * Tulostaa kentän tiedot
     * @param out tietovirta mihin tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(kerhoId);
        out.println(kerhonNimi);
        out.println(kerhonKatuOs);
        out.println(kerhonPostiOs);
        out.println(kerhonPuhNro);      
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kentta kentta1 = new Kentta();
        Kentta kentta2 = new Kentta();
        
        kentta1.rekisteroiKentta();
        kentta2.rekisteroiKentta();
        
        kentta1.vastaaAkuAnkka();
        kentta2.vastaaAkuAnkka();
        
        kentta1.tulosta(System.out);
        kentta2.tulosta(System.out);   
       
    }

}
