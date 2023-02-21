/**
 * 
 */
package kerho;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Kierros-luokka
 * - Tietää kierroksen kentät (pvm, lahtoaika,
 *   pelaaja jne.)  
 * - Osaa tarkistaa kentän oikeellisuuden 
 * - Osaa muuttaa 1|3|11.7.2022|...| -merkkijonon
 *   kierroksen tiedoiksi 
 * - Osaa antaa merkkijonona kentän i tiedot
 * - Osaa laittaa merkkijonon i:neksi kierrokseksi
 * @author Miia Arkko
 * @version 21.2.2023
 *
 */
public class Kierros {

    private int kierrosNro       = 0;
    private int pelaajaNro       = 0;
    private String pvm           = "";
    private String lahtoAika     = "";
    private boolean onkoTasoitus = false;
    private int pelatutReiat     = 0;
    private double pelaajanHcp   = 0;
    private String pelattuKentta = "";
    
    private static int seuraavaKierrosNro = 1;
    
    // TODO: muuta tämä, kun homma toimii
    private static int seuraavaPelaajaNro = 1;
    
    
    /**
     * Palauttaa kierroksen numeron
     * @return kierroksen numero
     */
    public int getkierrosNro() {
        return kierrosNro;
    }
    
    
    /**
     * Alustaa kierroksen kentät tyhjiksi
     */
    public Kierros() {
        
    }
    
    
    /**
     * Antaa kierrokselle seuraavan kierrosnumeron
     * @return palauttaa kierroksen numeron
     * @example
     * <pre name="test">
     *   Kierros k1 = new Kierros();
     *   k1.getkierrosNro() === 0;
     *   k1.rekisteroi();
     *   Kierros k2 = new Kierros();
     *   k2.rekisteroi();
     *   int n1 = k1.getkierrosNro();
     *   int n2 = k2.getkierrosNro();
     *   n2 === n1+1;
     * </pre>
     */
    public int rekisteroi() {
        kierrosNro = seuraavaKierrosNro;
        seuraavaKierrosNro++;
        return kierrosNro;
    }
    
    
    /**
     * Tulostetaan kierroksen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Tulostaa Kierroksen tiedot
     * @param out tietovirta, mihin tulostetaan
     */
    public void tulosta(PrintStream out) {
        
        out.println("Kierrosnumero:  " + String.format("%03d", kierrosNro));
        out.println("   Päivämäärä:  " + pvm + ",  Lähtöaika:  " + lahtoAika);
        out.println("   Pelaajanumero:  " + String.format("%03d", pelaajaNro));
        out.println("   Pelaajan tasoitus  " + pelaajanHcp);
        out.println("   Golfkerho:  " + pelattuKentta);
        out.println("   Pelatut reiät  " + pelatutReiat);
        out.println("   Onko tasoituskierros: " + onkoTasoitus); 
        
    }
    
    
    /**
     * Täyttää kierroksen kentät
     * TODO: poista, kun kaikki toimii
     */
    public void vastaaAkuAnkka() {
        pelaajaNro      = seuraavaPelaajaNro;
        pvm             = "11.6.2022";
        lahtoAika       = "12.40";
        onkoTasoitus    = true;
        pelatutReiat    = (int) (Math.random() * (18 - 9) + 9);
        pelaajanHcp     = Math.round(Math.random() * (54 - 3) + 3);
        pelattuKentta   = "Paras Golfkerho";
        seuraavaPelaajaNro++;
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Kierros k1 = new Kierros();
        Kierros k2 = new Kierros();
        
        k1.rekisteroi();
        k2.rekisteroi();
        
        
        k1.tulosta(System.out);
        
        k1.vastaaAkuAnkka();
        k2.vastaaAkuAnkka();
        
        k1.tulosta(System.out);
        k2.tulosta(System.out);
    
    }

}
