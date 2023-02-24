/**
 * 
 */
package kerho;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

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
 * @version 24.2.2023
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
    
    
    /**
     * Alustetaan kierros.
     */
    public Kierros() {
        //
    }
    
    
    /**
     * Alustaa tietyn pelaajan kierros
     * @param nro mille pelaajalle kierros kuuluu
     */
    public Kierros(int nro) {
        this.pelaajaNro = nro;
    }
    
    
    /**
     * Täyttää kierroksen kentät
     * TODO: poista, kun kaikki toimii
     * @param nro pelaajan numero
     */
    public void vastaaKierros(int nro) {
        Random rd = new Random();
        pelaajaNro      = nro;
        pvm             = "11.6.2022";
        lahtoAika       = "12.40";
        onkoTasoitus    = rd.nextBoolean();
        pelatutReiat    = (int) (Math.random() * (18 - 9) + 9);
        pelaajanHcp     = Math.round(Math.random() * (54 - 3) + 3);
        pelattuKentta   = "Paras Golfkerho";
    }
    
    
    /**
     * Tulostaa Kierroksen tiedot
     * @param out tietovirta, mihin tulostetaan
     */
    public void tulosta(PrintStream out) {
        
        String tasoitusKierros = "Kyllä";
        if(!onkoTasoitus) {
            tasoitusKierros = "Ei"; 
        }
        out.println("Kierrosnumero:  " + String.format("%03d", kierrosNro));
        out.println("   Päivämäärä:  " + pvm + ",  Lähtöaika:  " + lahtoAika);
        out.println("   Pelaajanumero:  " + String.format("%03d", pelaajaNro));
        out.println("   Pelaajan tasoitus  " + pelaajanHcp);
        out.println("   Golfkerho:  " + pelattuKentta);
        out.println("   Pelatut reiät  " + pelatutReiat);
        out.println("   Onko tasoituskierros: " + tasoitusKierros);      
    }
    
    
    
    /**
     * Tulostetaan kierroksen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Antaa kierrokselle seuraavan kierrosnumeron
     * @return palauttaa kierroksen numeron
     * @example
     * <pre name="test">
     *   Kierros k1 = new Kierros();
     *   k1.getKierrosNro() === 0;
     *   k1.rekisteroi();
     *   Kierros k2 = new Kierros();
     *   k2.rekisteroi();
     *   int n1 = k1.getKierrosNro();
     *   int n2 = k2.getKierrosNro();
     *   n2 === n1+1;
     * </pre>
     */
    public int rekisteroi() {
        kierrosNro = seuraavaKierrosNro;
        seuraavaKierrosNro++;
        return kierrosNro;
    }
    
    
    /**
     * Palautetaan kierroksen id
     * @return kierroksen id
     */
    public int getKierrosNro() {
        return kierrosNro;
    }
  
       
    /**
     * Palautetaan, mille pelaajalle kierros kuuluu
     * @return pelaajan jäsennumeron
     */
    public int getPelaajaNro() {       
        return pelaajaNro;
    }
    

    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Kierros k1 = new Kierros();
        Kierros k2 = new Kierros();
        
        //k1.rekisteroi();
        k1.vastaaKierros(1);
        //k2.rekisteroi();
        k2.vastaaKierros(2);   
    }

}
