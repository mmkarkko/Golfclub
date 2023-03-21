/**
 * 
 */
package kerho;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import fi.jyu.mit.ohj2.Mjonot;

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
 * @version 6.3.2023
 *
 */
public class Kierros {

    private int kierrosNro       = 0;
    private int pelaajaNro       = 0;
    private String pvm           = "";
    private String lahtoAika     = "";
    private String onkoTasoitus  = "";
    private int pelatutReiat     = 0;
    private double pelaajanHcp   = 0;
    private String pelattuKentta = "";
    static Calendar kalenteri    = Calendar.getInstance();
    
    private static int seuraavaKierrosNro = 1;    
    
    private int pv = 0;
    private int kk = 0;
    private int vv = 0;
    
    private int tunnit = 0;
    private int minuutit = 0;
    
    
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
     * Hakee päivämäärän
     * @return päivämäärä tänään
     */
    public String paivays() {
        pv = kalenteri.get(Calendar.DATE);
        kk = (kalenteri.get(Calendar.MONTH)+1);
        vv = kalenteri.get(Calendar.YEAR);
        return pv + "." + kk + "." + vv;
    }
    
    
    /**
     * Hakee kellonajan
     * @return kellonaika tällä hetkellä
     */
    public String kellonaika() {
        tunnit = kalenteri.get(Calendar.HOUR_OF_DAY);
        minuutit = kalenteri.get(Calendar.MINUTE);
        return tunnit + ":" + minuutit;
    }
    
    
    /**
     * Täyttää kierroksen kentät
     * TODO: poista, kun kaikki toimii
     * @param nro pelaajan numero
     */
    public void vastaaKierros(int nro) {
        pelaajaNro      = nro;
        pvm             = paivays();
        lahtoAika       = kellonaika();
        onkoTasoitus    = "ei";
        pelatutReiat    = (int) (Math.random() * (18 - 9) + 9);
        pelaajanHcp     = Math.round(Math.random() * (54 - 3) + 3);
        //pelaajanHcp     = Pelaaja.getTasoitus();
        pelattuKentta   = "Paras Golfkerho";
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
     * Selvitää harrastuksen tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta harrastuksen tiedot otetaan
     * @example
     * <pre name="test">
     *   Harrastus harrastus = new Harrastus();
     *   harrastus.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   harrastus.getJasenNro() === 10;
     *   harrastus.toString()    === "2|10|Kalastus|1949|22";
     *   
     *   harrastus.rekisteroi();
     *   int n = harrastus.getTunnusNro();
     *   harrastus.parse(""+(n+20));
     *   harrastus.rekisteroi();
     *   harrastus.getTunnusNro() === n+20+1;
     *   harrastus.toString()     === "" + (n+20+1) + "|10|Kalastus|1949|22";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        
        setKierrosNro(Mjonot.erota(sb, '|', getKierrosNro()));
        pelaajaNro = Mjonot.erota(sb, '|', pelaajaNro);
        pvm = Mjonot.erota(sb, '|', pvm);
        lahtoAika = Mjonot.erota(sb, '|', lahtoAika);
        onkoTasoitus = Mjonot.erota(sb, '|', onkoTasoitus);
        pelatutReiat = Mjonot.erota(sb, '|', pelatutReiat);
        pelaajanHcp = Mjonot.erota(sb, '|', pelaajanHcp);
        pelattuKentta = Mjonot.erota(sb, '|', pelattuKentta);     
    }

    
    /**
     * Asettaa pelaajan pelaajanumeron ja varmistaa, että
     * seuraava numero on aina suurempi, kuin tähän mennessä suurin.
     * @param nro asetettava pelaajanumero
     */
    private void setKierrosNro(int nro) {
        kierrosNro = nro;
        if ( kierrosNro >= seuraavaKierrosNro) seuraavaKierrosNro = kierrosNro +1;
    }


    /**
     * Palauttaa harrastuksen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return harrastus tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Kierros kierros = new Kierrokset();
     *   kierros.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   kierros.toString()    === "2|10|Kalastus|1949|22";
     * </pre>
     */
    @Override
    public String toString() {
        return "" + getKierrosNro() + "|" + 
                    pelaajaNro + "|" + 
                    pvm + "|" + 
                    lahtoAika + "|" + 
                    onkoTasoitus + '|' + 
                    pelatutReiat + '|' + 
                    pelaajanHcp + '|' + 
                    pelattuKentta;
    }

    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Kierros k1 = new Kierros();
        Kierros k2 = new Kierros();
        
        k1.rekisteroi();
        k1.vastaaKierros(1);
        k2.rekisteroi();
        k2.vastaaKierros(2);   
        
        System.out.println(k1);
        System.out.println(k2);
    }
}
