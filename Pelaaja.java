/**
 * 
 */
package kerho;

import java.io.*;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;
import kanta.EmailTarkistus;
import kanta.HetunTarkistus;
import kanta.TarkistaNimi;
import kanta.Tietue;

import static kanta.HetunTarkistus.*;

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
 * @version 7.3.2023
 * @version 21.3.2023 sähköpostiosoitteen validointi toimii
 *
 */
public class Pelaaja implements Cloneable, Tietue {
    
    private int         pelaajaNro      = 0;
    private String      nimi            = "";
    private String      hetu            = "";
    private double      hcp             = 0;
    private String      puhNro          = "";
    private String      email           = "";
    private String      katuOs          = "";
    private String      postiOs         = "";
    private int         osakeNro        = 0;
    private String      jasenMaksu      = "OK";
    private String      pelaajanKerho   = "Paras Golfkerho";

    
    private static int seuraavaPelaajaNro  = 1;
    private static int seuraavaOsakeNro    = 1;
    
    /**
     * Luokka joka osaa verrata kahta pelaajaa
     * @author Miia Arkko
     * @version 13.4.2023
     *
     */
    public static class Vertailija implements Comparator<Pelaaja> {

        private int k;
                
        /**
         * @param k kenttä, minkä mukaan vertaillaan
         */
        public Vertailija(int k) {
            this.k = k;
        }
        
        
        @Override
        public int compare(Pelaaja p1, Pelaaja p2) {
            return p1.anna(k).compareTo(p2.anna(k));
        }
        //
    }
    
    /**
     * @return montako kenttaa lomakkeessa on
     */
    @Override
    public int getKenttia() {
        return 11;
    }

    
    /**
     * Eka kenttä joka on mielekäs kysyttäväksi
     * 1, koska käyttäjältä ei kysytä pelaajan numeroa
     * @return ekan kentän indeksi
     */
    @Override
    public int ekaKentta() {
        return 0;
    }
    
    
    /**
     * Palauttaa k:tta pelaajan kenttää vastaavan kysymyksen
     * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
     * @return k:netta kenttää vastaava kysymys
     */
    @Override
    public String getKysymys(int k) {
        switch ( k ) {
            case 0: return "Pelaajanumero";
            case 1: return "Nimi";
            case 2: return "Hetu";
            case 3: return "Tasoitus";
            case 4: return "Puhelinnumero";
            case 5: return "Sähköpostiosoite";
            case 6: return "Katuosoite";
            case 7: return "Postiosoite";
            case 8: return "Osakenumero";
            case 9: return "Jäsenmaksu";
            case 10: return "Pelaajan kerho";
        default: return "Vittu";
        }
    }

  
    /**
     * Hakee pelaajan nimen
     * @return palauttaa nimen
     * @example
     * <pre name="test">
     *   Pelaaja p = new Pelaaja();
     *   p.vastaaAkuAnkka();
     *   p.getNimi() =R= "Pelaaja Petteri";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }
    
    
    /**
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    @Override
    public String anna(int k) {
        switch ( k ) {
            case 0: return "" + pelaajaNro;
            case 1: return "" + nimi;
            case 2: return "" + hetu;
            case 3: return "" + hcp;
            case 4: return "" + puhNro;
            case 5: return "" + email;
            case 6: return "" + katuOs;
            case 7: return "" + postiOs;
            case 8: return "" + osakeNro;
            case 9: return "" + jasenMaksu;
            case 10: return "" + pelaajanKerho;
        default: return "Äääliö";
        }
    }
    
    
    /**
     * 
     * @param k kenttä
     * @return kentän sisältö merkkijonona
     */
    public String getAvain(int k) {
        switch ( k ) {
            case 0: return "" + pelaajaNro;
            case 1: return "" + nimi.toUpperCase();
            case 2: return "" + hetu;
            case 3: return "" + hcp;
            case 4: return "" + puhNro;
            case 5: return "" + email;
            case 6: return "" + katuOs.toUpperCase();
            case 7: return "" + postiOs.toLowerCase();
            case 8: return "" + osakeNro;
            case 9: return "" + jasenMaksu;
            case 10: return ""+ pelaajanKerho.toUpperCase();
        default: return "Äääliö";
        }
    }

    
    private HetunTarkistus hetut = new HetunTarkistus();
    
    
    /**
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kentän arvo asetetaan
     * @param jono joka asetetaan kentän arvoksi
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
     * @example
     * <pre name="test">
     *   Pelaaja pelaaja = new Pelaaja();
     *   pelaaja.aseta(1,"Ankka Aku") === null;
     *   pelaaja.aseta(2,"kissa") =R= "Hetu liian lyhyt"
     *   pelaaja.aseta(2,"030201-1111") === "Tarkistusmerkin kuuluisi olla C"; 
     *   pelaaja.aseta(2,"030201-111C") === null; 
     *   pelaaja.aseta(9,"ok") === null;
     * </pre>
     */
    @Override
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch ( k ) {
            case 0:
                setPelaajaNro(Mjonot.erota(sb, '§', getpelaajaNro()));
                return null;
            case 1:
                TarkistaNimi name = new TarkistaNimi();
                String virhe2 = name.tarkistaNimi(tjono);
                if (virhe2 != null) return virhe2; 
                nimi = tjono;
                return null;
            case 2:
                String virhe = hetut.tarkista(tjono);
                if ( virhe != null ) return virhe;
                hetu = tjono;
                return null;
            case 3:
                hcp = Double.parseDouble(tjono);
                return null;
            case 4:
                puhNro = tjono;
                return null;
            case 5:
                EmailTarkistus emaili = new EmailTarkistus();
                String virhe3 = emaili.tarkistaOsoite(tjono);
                if (virhe3 != null) return virhe3;
                email = tjono;
                return null;
            case 6:
                katuOs = tjono;
                return null;
            case 7:
                postiOs = tjono;
                return null;
            case 8:
                setOsakeNro(Mjonot.erota(sb, '$', getOsakeNro()));
                return null;
            case 9:
                jasenMaksu = tjono;
                return null;
            case 10:
                pelaajanKerho = tjono;
                return null;
            default:
                return "Ääliö";
        }
    }
    
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot pelaajalle
     * TODO: poista, kun kaikki toimii
     * @param apuhetu hetu joka pelaajalle annetaan
     */
    public void vastaaAkuAnkka(String apuhetu) {
        nimi          = "Pelaaja Petteri";
        hetu          = apuhetu;
        hcp           = HetunTarkistus.randDouble(4.0, 54.0);
        puhNro        = "000-9999999";
        email         = "petepelaaja@golffari.fi";
        katuOs        = "Pelimiehenkuja " + HetunTarkistus.rand(1,400);
        postiOs       = "11111 Pelilä";
        jasenMaksu    = "OK";
        pelaajanKerho = "Paras Golfkerho";
    }
    
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot pelaajalle.
     * Henkilötunnus arvotaan, jotta kahdella pelaajalla ei olisi
     * samoja tietoja.
     */
    public void vastaaAkuAnkka() {
        String apuhetu = arvoHetu();
        vastaaAkuAnkka(apuhetu);
    }
       
 
    /**
     * Tulostaa Pelaajan tiedot
     * @param out tietovirta, mihin tulostetaan
     */
    public void tulosta(PrintStream out) {     
        out.println(String.format("%03d", pelaajaNro) + "  " + nimi + "  " + hetu);
        out.println("  Tasoitus " + hcp);
        out.println("  Puhelinnumero: " + puhNro);
        out.println("  Email: " + email);
        out.println("  Osoite: " + katuOs + ",  " + postiOs);
        out.println("  Golfkerho: " + pelaajanKerho + ", Osakenumero: " + osakeNro);
        out.println("  Jasenmaksu: " + jasenMaksu); 
    }
    
    
    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
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
     * Palauttaa pelaajan jäsennumeron
     * @return pelaajan jäsennumero
     */
    public int getpelaajaNro() {
        return pelaajaNro;
    }
    
    
    /**
     * Asettaa pelaajan pelaajanumeron ja varmistaa, että
     * seuraava numero on aina suurempi, kuin tähän mennessä suurin.
     * @param nro asetettava pelaajanumero
     */
    private void setPelaajaNro(int nro) {
        pelaajaNro = nro;
        if ( pelaajaNro >= seuraavaPelaajaNro) seuraavaPelaajaNro = pelaajaNro +1;
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
//        // TODO: poista randomi, kun toimii
//        Random rd = new Random();
//        onkoOsake     = rd.nextBoolean();
//        if (onkoOsake == false) return 0;
//        
        osakeNro   = seuraavaOsakeNro;
        seuraavaOsakeNro++;
        return osakeNro;
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
     * Asettaa pelaajan osakenumeron. Varmistaa, että
     * seur. numero on aina suurempi, kuin aiempi suurin
     * @param nro asetettava osakenumero
     */
    private void setOsakeNro(int nro) {
        osakeNro = nro;
        if (osakeNro >= seuraavaOsakeNro) seuraavaOsakeNro =osakeNro + 1;
    }
    

    
    
    /**
     * Palauttaa jäsenen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return jäsen tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Pelaaja pelaaja = new Pelaaja();
     *   pelaaja.parse(" 3  | Teetime Tiina  | 010428-802Y |  11.0 |  010-0101010  | tiinateetime@golffari.fi | Tiikuja 1 |  11001 Tiiala  | 1  | OK   | 1  ");
     *   pelaaja.toString().startsWith("3|Teetime Tiina|010428-802Y|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
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
    
    
    /**
     * Selvitää pelaajan tiedot |-merkein erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta jäsenen tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Pelaaja pelaaja = new Pelaaja();
     *   pelaaja.parse(" 3  | Teetime Tiina  | 010428-802Y |  11.0 |  010-0101010  | tiinateetime@golffari.fi | Tiikuja 1 |  11001 Tiiala  | 1  | OK   | 1  ");
     *   pelaaja.getpelaajaNro() === 3;
     *   pelaaja.toString().startsWith("3|Teetime Tiina|010428-802Y|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *     
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));
    }
    
    
    /**
     * Tutkii onko pelaajan tiedot samat kuin parametrina tuodun pelaajan tiedot
     * @param pelaaja johon verrataan
     * @return true jos kaikki tiedot samat, false muuten
     * @example
     * <pre name="test">
     *   Pelaaja p1 = new Pelaaja();
     *   p1.parse("   1  | Pelaaja Petteri | 070819-5398 |5.4  | 000-9999999  | petepelaaja@golffari.fi  | Pelimiehenkuja 1 | 11111 Pelilä     | 1  | OK  |1  ");
     *   Pelaaja p2 = new Pelaaja();
     *   p2.parse("   1  | Pelaaja Petteri | 070819-5398 |5.4  | 000-9999999  | petepelaaja@golffari.fi  | Pelimiehenkuja 1 | 11111 Pelilä     | 1  | OK  |1  ");
     *   Pelaaja p3 = new Pelaaja();
     *   p3.parse("3   | Teetime Tiina  |010428-802Y | 11.0 | 010-0101010  | tiinateetime@golffari.fi | Tiikuja    |11001 Tiiala     |  1  |OK   |1     ");
     *   
     *   p1.equals(p2) === true;
     *   p2.equals(p1) === true;
     *   p1.equals(p3) === false;
     *   p3.equals(p2) === false;
     * </pre>
     */
    public boolean equals(Pelaaja pelaaja) {
        if ( pelaaja == null ) return false;
        for (int k = 0; k < getKenttia(); k++)
            if ( !anna(k).equals(pelaaja.anna(k)) ) return false;
        return true;
    }
    
    
    @Override
    public boolean equals(Object pelaaja) {
        if ( pelaaja instanceof Pelaaja ) return equals((Pelaaja)pelaaja);
        return false;
    }

    
    @Override
    public int hashCode() {
        return pelaajaNro;
    }
    
    
    /**
     * Kloonataan pelaaja tietoineen
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Pelaaja pelaaja = new Pelaaja();
     *   pelaaja.parse(" 3  | Teetime Tiina  | 010428-802Y |  11.0 |  010-0101010  | tiinateetime@golffari.fi | Tiikuja 1 |  11001 Tiiala  | 1  | OK   | 1  ");
     *   try {
     *    Pelaaja kopio = pelaaja.clone();
     *   } catch (CloneNotSupportedException e) {
     *      System.out.println("Tulee poikkeus");
     *   }
     *   kopio.toString() === pelaaja.toString();
     *   pelaaja.parse(" 8   | Ping Pinja    |040689-126E | 4.3  | 425-4250425  |  pingpin@golffari.fi   | Pinjatie 3   | 04255 Pinjala    | 5    | OK  |1     ");
     *   kopio.toString().equals(pelaaja.toString()) === false;
     * </pre>
     */
    @Override
    public Pelaaja clone() throws CloneNotSupportedException {
        
        Pelaaja uusi = (Pelaaja) super.clone();
        return uusi;
    }


    /**
     * 
     * @return pelaajan kotikenttä
     */
    public String getKentta() {
        return pelaajanKerho;
    }    

    
    /**
     * Testiohjelma pelaajalle
     * @param args ei käytössä
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
