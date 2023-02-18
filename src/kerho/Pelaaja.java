/**
 * 
 */
package kerho;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Pelaaja luokka
 * @author miiaa
 * @version 18.2.2023
 *
 */
public class Pelaaja {
    
    private int         pelaajaNro      = 0;
    private String      nimi            = "";
    private double      hcp             = 0;
    private String      puhNro          = "";
    private String      email           = "";
    private String      katuOs          = "";
    private String      postiOs         = "";
    private int         osakeNro        = 0;
    private boolean     jasenMaksu      = true;
    private String      pelaajanKerho   = "";
    
    private static int seuraavaNro  = 1;
    
    
    /**
     * Palauttaa pelaajan jäsennumeron
     * @return pelaajan jäsennumero
     */
    public int getpelaajaNro() {
        return pelaajaNro;
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
        pelaajaNro = seuraavaNro;
        seuraavaNro++;
        return pelaajaNro;
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
        
        out.println(String.format("%03d", pelaajaNro) + "  " + nimi);
        out.println("  Tasoitus " + hcp);
        out.println("  k: " + puhNro);
        out.println("  k: " + email);
        out.println("  " + katuOs + ",  " + postiOs);
        out.println("  Osakkeen numero " + osakeNro + ",  Golfkerho: " + pelaajanKerho);
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
        nimi          = "Pelaaja Petteri";
        hcp           = 5.4;
        puhNro        = "000-9999999";
        email         = "petepelaaja@golffari.fi";
        katuOs        = "Pelimiehenkuja 1";
        postiOs       = "11111 Pelilä";
        osakeNro      = 1;
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
        p2.rekisteroi();
        
        p1.tulosta(System.out);
        
        p1.vastaaAkuAnkka();
        p2.vastaaAkuAnkka();
        
        p1.tulosta(System.out);
        p2.tulosta(System.out);
    
    }

}
