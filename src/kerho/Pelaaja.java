/**
 * 
 */
package kerho;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;
import fi.jyu.mit.ohj2.Mjonot;
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
 * @version 7.3.2023
 *
 */
public class Pelaaja implements Cloneable {
    
    private int         pelaajaNro      = 0;
    private String      nimi            = "";
    private String      hetu            = "";
    private double      hcp             = 0;
    private String      puhNro          = "";
    private String      email           = "";
    private String      katuOs          = "";
    private String      postiOs         = "";
    private int         osakeNro        = 0;
    private String    jasenMaksu        = "OK";
    private String      pelaajanKerho   = "Paras Golfkerho";
    
    private HetunTarkistus hetut = new HetunTarkistus();
    
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
     * Palauttaa pelaajan tasoituksen
     * @return tasoitus
     */
    public double getTasoitus() {
        return hcp;
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
     * Palauttaa jäsenen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return jäsen tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Pelaaja pelaaja = new Pelaaja();
     *   pelaaja.parse("   3  |  Ankka Aku   | 030201-111C");
     *   pelaaja.toString().startsWith("3|Ankka Aku|030201-111C|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
        return "" +
                getpelaajaNro() + "|" +
                nimi + "|" +
                hetu + "|" +
                hcp  + "|" +
                puhNro + "|" +
                email +  "|" +
                katuOs + "|" +
                postiOs + "|" +
                getOsakeNro() + "|" +
                jasenMaksu + "|" + 
                pelaajanKerho + "| ";
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
     * Apumetodi, jolla saadaan täytettyä testiarvot pelaajalle
     * TODO: poista, kun kaikki toimii
     */
    public void vastaaAkuAnkka() {
        nimi          = "Pelaaja Petteri";
        hetu          = HetunTarkistus.arvoHetu();
        hcp           = HetunTarkistus.randDouble(4.0, 54.0);
        puhNro        = "000-9999999";
        email         = "petepelaaja@golffari.fi";
        katuOs        = "Pelimiehenkuja " + HetunTarkistus.rand(1,400);
        postiOs       = "11111 Pelilä";
        jasenMaksu    = "OK";
        pelaajanKerho = "Paras Golfkerho";
    }
    
    /**
     * Selvitää jäsenen tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta jäsenen tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Pelaaja pelaaja = new Pelaaja();
     *   pelaaja.parse("   1  |  Pelaaja Petteri   | 030201-111C");
     *   pelaaja.getpelaajaNro() === 1;
     *   pelaaja.toString().startsWith("1|Pelaaja Petteri|030201-111C|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *
     *   pelaaja.rekisteroi();
     *   int n = pelaaja.getpelaajaNro();
     *   pelaaja.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   pelaaja.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   pelaaja.getpelaajaNro() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        var sb = new StringBuilder(rivi);
        
        setPelaajaNro(Mjonot.erota(sb, '|', getpelaajaNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
        hetu = Mjonot.erota(sb, '|', hetu);
        //setTasoitus(Mjonot.erota(sb, '|', getTasoitus()));
        hcp = Mjonot.erota(sb, '|', hcp);
        puhNro = Mjonot.erota(sb, '|', puhNro);
        email = Mjonot.erota(sb, '|', email);     
        katuOs = Mjonot.erota(sb, '|', katuOs);
        postiOs = Mjonot.erota(sb, '|', postiOs);
        setOsakeNro(Mjonot.erota(sb, '|', getOsakeNro()));
        jasenMaksu = Mjonot.erota(sb, '|', jasenMaksu);
        pelaajanKerho = Mjonot.erota(sb, '|', pelaajanKerho);
    }
    
    
    /**
     * Asettaa pelaajan osakenumeron. Varmistaa, ettö
     * seur. numero on aina suurempi, kuin aiempi suurin
     * @param nro asetettava osakenumero
     */
    private void setOsakeNro(int nro) {
        osakeNro = nro;
        if (osakeNro >= seuraavaOsakeNro) seuraavaOsakeNro =osakeNro + 1;
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
     * @return pelaajan hetu
     */
    public String getHetu() {
        return hetu;
    }


    /**
     * 
     * @return pelaajan puhelinnumero
     */
    public String getPuh() {
        return puhNro;
    }


    /**
     * @return pelaajan sähköpostiosoite
     */
    public String getEmail() {
        return email;
    }


    /**
     * 
     * @return pelaajan katuosoite
     */
    public String getKatuos() {
        return katuOs;
    }

    
    /**
     * 
     * @return pelaajan postiosoite
     */
    public String getPostios() {
        return postiOs;
    }
    

    /**
     * 
     * @return pelaajan jasenmaksun tila
     */
    public String getJasMaksu() {
        return jasenMaksu;
    }


    /**
     * 
     * @return pelaajan kotikenttä
     */
    public String getKentta() {
        return pelaajanKerho;
    }
    
    
    /**
     * Kloonataan pelaaja
     */
    @Override
    public Pelaaja clone() throws CloneNotSupportedException {
        Pelaaja uusi = (Pelaaja) super.clone();
        return uusi;
    }
    

    /**
     * Asettaa pelaajalle nimen
     * @param s pelaajalle asetettava nimi
     * @return virheilmoitus, null jos ok
     */
    public String setNimi(String s) {
        nimi = s;
        return null;
    }

    
    /**
     * Asettaa pelaajalle henkilötunnuksen
     * @param s pelaajalle asetettava hetu
     * @return virheilmoitus. Jos ok, null.
     */
    public String setHetu(String s) {
        String virhe = hetut.tarkista(s);
        if (s != null) return virhe;
        hetu = s;
        return null;
    }
    
    
    /**
     * Asettaa pelaajalle tasoituksen
     * @param s Pelaajalle lisättävä tasoitus
     * @return virheilmoitus, null jos ok
     */
    public String setTasoitus(String s) {
        hcp = Double.valueOf(s);
        return null;
    }


    /**
     * Asettaa pelaajalle puhelinnumeron
     * @param s pelaajalle asetettava puhelinnumero
     * @return virheilmoitus, null jos ok
     */
    public String setPuh(String s) {
        puhNro = s;
        return null;
    }


    /**
     * Asettaa pelaajalle sähköpostiosoitteen
     * @param s pelaajalle asetettava sähköpostiosoite
     * @return virheilmoitus, null jos ok
     */
    public String setEmail(String s) {
        email = s;
        return null;
    }


    /**
     * Asettaa pelaajalle katuosoitteen
     * @param s pelaajalle laitettava katuosoite
     * @return virheilmoitus, null jos ok
     */
    public String setKatuos(String s) {
        katuOs = s;
        return null;
    }


    /**
     * Asettaa pelaajalle postiosoitteen
     * @param s pelaajalle asetettava postiosoite
     * @return virheilmoitus, null jos ok
     */
    public String setPostios(String s) {
        //Postinumeron tarkistus ei toimi tässä, koska kentässä on myös postitoimipaikka
        //if (!s.matches("[0-9]*")) return "Postinumero saa sisältää vain numeroita";
        postiOs = s;
        return null;
    }


    /**
     * Asettaa pelaajan jäsenmaksun tilan
     * @param s onko pelaajan jäsenmaksu ok
     * @return virheilmoitus, null, jos ok
     */
    public String setJasMaksu(String s) {
        jasenMaksu = s;
        return null;
    }


    /**
     * Asettaa pelaajalle kotikentän
     * @param s pelaajalle asetetttava kotikenttä
     * @return virheilmoitus, null jos ok
     */
    public String setKentta(String s) {
        pelaajanKerho = s;
        return null;
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
