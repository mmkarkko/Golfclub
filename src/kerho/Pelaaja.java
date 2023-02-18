/**
 * 
 */
package kerho;

import java.io.PrintStream;

/**
 * Pelaaja luokka
 * @author miiaa
 * @version 18.2.2023
 *
 */
public class Pelaaja {
    
    private int tunnusNro       = 0;
    private String nimi         = "";
    private double hcp          = 0;
    private String puhNro       = "";
    private String email        = "";
    private String katuOs       = "";
    private String postiOs      = "";
    private int osakeNro        = 0;
    private boolean jasenMaksu  = true;
    private int pelaajanKerho   = 0;
    
    
    /**
     * Alustetaan jäsenen tiedot tyhjiksi
     */
    public Pelaaja() {
        
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
        
        out.println(String.format("%03d", tunnusNro) + "  " + nimi);
        out.println("  Tasoitus " + hcp);
        out.println("  k: " + puhNro);
        out.println("  k: " + email);
        out.println("  " + katuOs + ",  " + postiOs);
        out.println("  Osakkeen numero " + osakeNro + ",  Golfkerho: " + pelaajanKerho);
        out.println("  Jäsenmaksu " + jasenMaksunTila); 

    }

    /**
     * @param args ei käytössä
     * 
     */
    public static void main(String[] args) {
        Pelaaja p1 = new Pelaaja();
        Pelaaja p2 = new Pelaaja();
        
        //p1.rekisteroi();
        //p2.rekisteroi();
        
        p1.tulosta(System.out);
        
        //p1.vastaaAkuAnkka();
        //p2.vastaaAkuAnkka();
        
        p1.tulosta(System.out);
        p2.tulosta(System.out);
    
    }

}
