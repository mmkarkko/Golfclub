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
    
    /**
     * Tulostaa Pelaajan tiedot
     * @param out tietovirta, mihin tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("Aku");
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
