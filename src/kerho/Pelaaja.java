/**
 * 
 */
package kerho;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

import kanta.HetunTarkistus;

/**
 * Pelaaja-luokka
 * - Tietää jäsenen kentät (nimi, hcp, säpo jne.)
 * - Osaa tarkistaa tietyn kentän syntaksin
 *   (oikeellisuuden) 
 * - Osaa muuttaa 1|Pelaaja Petteri|...| -merkkijonon
 *   pelaajan tiedoiksi
 * - Osaa antaa kentän i tiedot merkkijonona
 * - Osaa lisätä merkkijonon i:neksi kentäksi  
 * 
 * @author Miia Arkko
 * @version 24.2.2023
 *
 */
public class Pelaaja {
    
    private int         pelaajaNro      = 0;
    private String      nimi            = "";
    private String      hetu            = "";
    private double      hcp             = 0;
    private String      puhNro          = "";
    private String      email           = "";
    private String      katuOs          = "";
    private String      postiOs         = "";
    private int         osakeNro        = 0;
    private boolean     jasenMaksu      = true;
    private String      pelaajanKerho   = "";
    private boolean     onkoOsake       = true;
    
    private static int seuraavaPelaajaNro  = 1;
    private static int seuraavaOsakeNro    = 1;
    
    
    /**
     * Hakee pelaajan nimen
     * @return palauttaa nimen
     */
    public String getNimi() {
        return nimi;
    }
    
    
    /**
     * Palauttaa pelaajan jäsennumeron
     * @return pelaajan jäsennumero
     */
    public int getpelaajaNro() {
        return pelaajaNro;
    }
    
    
    /**
     * Palauttaa pelaajan osakenumeron
     * Mikäli pelaajalla ei ole osaketta, palautetaan 0.
     * @return osakenumero
     */
    public int getOsakeNro() {

        return osakeNro;
    }
    
    
    /**
     * Alustetaan jäsenen tiedot tyhjiksi
     */
    public Pelaaja() {
        
    }
    
    
    /**
     * Antaa pelaajalle seuraavan pejaalanumeron
     * @return pelaajan tunnusnumero
     * * @example
     * <pre name="test">
     *   Pelaaja p1 = new Pelaaja();
     *   p1.getpelaajaNro() === 0;
     *   p1.rekisteroi();
     *   Pelaaja p2 = new Pelaaja();
     *   p2.rekisteroi();
     *   int n1 = p1.getpelaajaNro();
     *   int n2 = p2.getpelaajaNro();
     *   n2 === n1+1;  //
     * </pre>
     */
    public int rekisteroi() {
        pelaajaNro = seuraavaPelaajaNro;
        seuraavaPelaajaNro++;
        return pelaajaNro;
    }
    
    
    /**
     * Antaa pelaajalle seuraavan osakenumeron
     * @return pelaajan osakkeen numero
     * @example
     * <pre name="test">
     *  Pelaaja p1 = new Pelaaja();
     *  p1.getOsakeNro() === 0;
     *  p1.rekisteroiOsake();
     *  Pelaaja p2 = new Pelaaja();
     *  p2.rekisteroiOsake();
     *  int n1 = 1;
     *  int n2 = 2;
     *  n2 === n1+1;
     * </pre>
     */
    public int rekisteroiOsake() {
        
        // TODO: poista randomi, kun toimii
        Random rd = new Random();
        onkoOsake     = rd.nextBoolean();
        if (onkoOsake == false) return 0;
        
        osakeNro   = seuraavaOsakeNro;
        seuraavaOsakeNro++;
        return osakeNro;
    }
    
    
    /**
     * Tulostaa Pelaajan tiedot
     * @param out tietovirta, mihin tulostetaan
     */
    public void tulosta(PrintStream out) {
        
        String jasenMaksunTila = "OK";
        if (!jasenMaksu) {
            jasenMaksunTila = "maksamatta";
        }
        
        String osake = "   Osakkeen numero: " + osakeNro;
        if (osakeNro == 0) {
            osake = "   Osakkeen numero: Ei osaketta";
        }
        
        out.println(String.format("%03d", pelaajaNro) + "  " + nimi + "  " + hetu);
        out.println("  Tasoitus " + hcp);
        out.println("  Puhelinnumero: " + puhNro);
        out.println("  Sähköposti: " + email);
        out.println("  Osoite: " + katuOs + ",  " + postiOs);
        out.println("  Golfkerho: " + pelaajanKerho + osake);
        out.println("  Jäsenmaksu " + jasenMaksunTila); 

    }
    
    
    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot pelaajalle
     * TODO: poista, kun kaikki toimii
     */
    public void vastaaAkuAnkka() {

        nimi          = "Pelaaja Petteri" + HetunTarkistus.rand(1,400);
        hetu          = HetunTarkistus.arvoHetu();
        hcp           = 5.4;
        puhNro        = "000-9999999";
        email         = "petepelaaja@golffari.fi";
        katuOs        = "Pelimiehenkuja " + HetunTarkistus.rand(1,400);
        postiOs       = "11111 Pelilä";
        jasenMaksu    = true;
        pelaajanKerho = "Paras Golfkerho";

    }

    
    /**
     * @param args ei käytössä
     * 
     */
    public static void main(String[] args) {
        Pelaaja p1 = new Pelaaja();
        Pelaaja p2 = new Pelaaja();
        
        p1.rekisteroi();
        p1.rekisteroiOsake();
        p1.vastaaAkuAnkka();
        
        p2.rekisteroi();
        p2.rekisteroiOsake();
        p2.vastaaAkuAnkka();
        
        p1.tulosta(System.out);
        p2.tulosta(System.out);
    
    }

}
