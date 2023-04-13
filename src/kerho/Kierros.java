/**
 * 
 */
package kerho;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import fi.jyu.mit.ohj2.Mjonot;
import kanta.Tietue;
import static kanta.HetunTarkistus.rand;

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
public class Kierros implements Cloneable, Tietue{

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
     * @return kierroksen kenttien lukumäärä
     */
    @Override
    public int getKenttia() {
        return 8;
    }


    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     */
    @Override
    public int ekaKentta() {
        return 2;
    }
    
    
    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     */
    @Override
    public String getKysymys(int k) {
        switch (k) {
            case 0:
                return "kierrosId";
            case 1:
                return "pelaajaId";
            case 2:
                return "päivämäärä";
            case 3:
                return "lähtöaika";
            case 4:
                return "onko tasoitus?";
            case 5:
                return "pelattujen reikien lkm";
            case 6:
                return "pelaajan tasoitus";
            case 7:
                return "kenttä";
            default:
                return "???";
        }
    }
    
    
    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   Kierros k = new Kierros();
     *   k.parse("   1   |   2   |  13.4.2023  |   12.20  | ei | 18   |   Paras Golfkerho ");
     *   k.anna(0) === "1";   
     *   k.anna(1) === "2";   
     *   k.anna(2) === "13.4.2023";   
     *   k.anna(3) === "12.20";   
     *   k.anna(4) === "ei";
     *   k.anna(5) === "18";
     *   k.anna(6) === "Paras Golfkerho";    
     * </pre>
     */
    @Override
    public String anna(int k) {
        switch (k) {
            case 0: return "" + kierrosNro;
            case 1: return "" + pelaajaNro;
            case 2: return "" + pvm;
            case 3: return "" + lahtoAika;
            case 4: return "" + onkoTasoitus;
            case 5: return "" + pelatutReiat;
            case 6: return "" + pelaajanHcp;
            case 7: return "" + pelattuKentta;
        default: return "???";
        }
    }

    
    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     *   Harrastus har = new Harrastus();
     *   har.aseta(3,"kissa") === "aloitusvuosi: Ei kokonaisluku (kissa)";
     *   har.aseta(3,"1940")  === null;
     *   har.aseta(4,"kissa") === "h/vko: Ei kokonaisluku (kissa)";
     *   har.aseta(4,"20")    === null;
     * </pre>
     */
    @Override
    public String aseta(int k, String s) {
        String st = s.trim();
        StringBuffer sb = new StringBuffer(st);
        switch (k) {
            case 0:
                setKierrosNro(Mjonot.erota(sb, '$', getKierrosNro()));
                return null;
            case 1:
                pelaajaNro = Mjonot.erota(sb, '$', pelaajaNro);
                return null;
            case 2:
                pvm = Mjonot.erota(sb, '$', pvm);
                return null;
            case 3:
                lahtoAika = Mjonot.erota(sb, '$', lahtoAika);
                return null;
            case 4:
                onkoTasoitus = Mjonot.erota(sb, '$', onkoTasoitus);
                return null;
            case 5:
                pelatutReiat = Mjonot.erota(sb,  '$', pelatutReiat);
                return null;
            case 6:
                pelaajanHcp = Mjonot.erota(sb,  '$', pelaajanHcp);
                return null;
            case 7:
                pelattuKentta = Mjonot.erota(sb, '$', pelattuKentta);
                return null;
            default:
                return "Väärä kentän indeksi";
        }
    }


    /**
     * Tehdään identtinen klooni jäsenestä
     * @return Object kloonattu jäsen
     * TODO: korjaa testit
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Harrastus har = new Harrastus();
     *   har.parse("   2   |  10  |   Kalastus  | 1949 | 22 t ");
     *   Harrastus kopio = har.clone();
     *   kopio.toString() === har.toString();
     *   har.parse("   1   |  11  |   Uinti  | 1949 | 22 t ");
     *   kopio.toString().equals(har.toString()) === false;
     * </pre>
     */
    @Override
    public Kierros clone() throws CloneNotSupportedException { 
        return (Kierros)super.clone();
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
        out.println(kierrosNro + " " + pelaajaNro + " " + pvm + " " + lahtoAika + " " + onkoTasoitus +  " " + pelatutReiat +  " " + pelaajanHcp + " " + pelattuKentta);    
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
     * Asettaa pelaajan pelaajanumeron ja varmistaa, että
     * seuraava numero on aina suurempi, kuin tähän mennessä suurin.
     * @param nro asetettava pelaajanumero
     */
    private void setKierrosNro(int nro) {
        kierrosNro = nro;
        if ( kierrosNro >= seuraavaKierrosNro) seuraavaKierrosNro = kierrosNro +1;
    }
    
    
    /**
     * Selvitää harrastuksen tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
     * TODO: korjaa testit
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
        for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|')); 
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
        StringBuffer sb = new StringBuffer("");
        String erotin = "";
        for (int k = 0; k < getKenttia(); k++) {
            sb.append(erotin);
            sb.append(anna(k));
            erotin = "|";
        }
        return sb.toString();
    }
       

    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) return false;
        return this.toString().equals(obj.toString());
    }
    

    @Override
    public int hashCode() {
        return kierrosNro;
    }

   
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Kierros k = new Kierros();
        k.vastaaKierros(2);
        k.tulosta(System.out);
    }
}
