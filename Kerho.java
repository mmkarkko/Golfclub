/**
 * 
 */
package kerho;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Kerho-luokka
 * - hoitaa kaiken näyttöön tulevan tekstin 
 * - hoitaa kaiken tiedon pyytämisen käyttäjältä
 *   (ei tiedä kerhon eikä pelaajan yksityiskohtia)
 * Avustajaluokat:  Kerho
 *                  Kierros
 *                  Pelaaja
 *                               
 * @author Miia Arkko
 * @version 3.4.2023
 *
 */
public class Kerho {

    private Pelaajat pelaajat = new Pelaajat();
    private Kierrokset kierrokset = new Kierrokset();
    private Kentat kentat = new Kentat();
    //private String hakemisto = "koekelmit";

    
    /**
     * Lisää kerhoon uuden kentän
     * @param kentta lisättävä kenttä
     * @throws SailoException jos ei onnistu
     */
    public void lisaa(Kentta kentta) throws SailoException {
        kentat.lisaa(kentta);
    }
    
    
    /**
     * Korvaa kentan tietorakenteessa.
     * Etsitään samalla tunnusnumerolla oleva kenttä. Jos ei löydy,
     * lisätään uutena kenttänä.
     * @param kentta lisättävän kentän viite
     * @throws SailoException poikkeus
     */
    public void korvaaTaiLisaa(Kentta kentta) throws SailoException {
        kentat.korvaaTaiLisaa(kentta);
    }   
    
    
    
    /**
     * Lisätään uusi pelaaja
     * @param pelaaja lisättävä pelaaja
     * @throws SailoException jos lisääminen ei onnistu
     *  @example
     * <pre name="test">
     * #THROWS SailoException
     * Kerho kerho = new Kerho();
     * Pelaaja aku1 = new Pelaaja(), aku2 = new Pelaaja();
     * kerho.lisaa(aku1); 
     * kerho.lisaa(aku2); 
     * kerho.lisaa(aku1);
     * Collection<Pelaaja> loytyneet = kerho.etsi("",-1); 
     * Iterator<Pelaaja> it = loytyneet.iterator();
     * it.next() === aku1;
     * it.next() === aku2;
     * it.next() === aku1;

     */
    public void lisaa(Pelaaja pelaaja) throws SailoException {
        pelaajat.lisaa(pelaaja);
    }
    
    
    /**
     * Korvaa pelaajan tietorakenteessa.
     * Etsitään samalla pelaajanumerolla oleva pelaaja. Jos ei löydy,
     * lisätään uutena pelaajana.
     * @param pelaaja lisättävän pelaajan viite
     * @throws SailoException poikkeus
     */
    public void korvaaTaiLisaa(Pelaaja pelaaja) throws SailoException {
        pelaajat.korvaaTaiLisaa(pelaaja);
    }
    
   
    /**
     * Lisätään uusi kierros
     * @param k lisättävä kierros
     * @throws SailoException jos tulee ongelmia
     */
    public void lisaa(Kierros k) throws SailoException {
        kierrokset.lisaa(k);
    }
    
    
    /** 
     * Korvaa kierroksen tietorakenteessa.  Ottaa kierroksen omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva kierros.  Jos ei löydy, 
     * niin lisätään uutena kierroksena. 
     * @param kierros lisärtävän kierroksen viite.  Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo täynnä 
     */ 
    public void korvaaTaiLisaa(Kierros kierros) throws SailoException { 
        kierrokset.korvaaTaiLisaa(kierros); 
    } 

    
    /** 
     * Palauttaa listan pelaajista jotkva vastaavat hakuehtoon 
     * @param ehto hakuehto   
     * @param k kentän indeksi jonka mukaan etsitään
     * @return löytyneet pelaajat
     */ 
    public Collection<Pelaaja> etsi(String ehto, int k) { 
        return pelaajat.etsi(ehto, k); 
    }
    
    
    /**
     * Poistaa pelaajista ja kierroksista pelaajan tiedot
     * @param pelaaja joka poistetaan
     * @return montako pelaajaa poistettiin
     * Kerho kerho = new Kerho();
     * Pelaaja p1 = new Pelaaja();
     * p1.rekisteroi();
     * kerho.lisaa(p1);
     * kerho.getPelaajia() === 1;
     * kerho.poista(p1) === 1;
     * </pre>
     */
    public int poista(Pelaaja pelaaja) {
        if (pelaaja == null) return 0;
        int ret = pelaajat.poista(pelaaja.getpelaajaNro());
        kierrokset.poistaPelaajanKierrokset(pelaaja.getpelaajaNro());
        return ret;
    }
    
    
    /**
     * Poistaa tämän kierroksen
     * @param kierros poistettva kierros
     * TODO: lisää testis https://www.mit.jyu.fi/demowww/ohj2/ht19/vesal/vaihe8.1/
     */
    public void poistaKierros(Kierros kierros) {
        kierrokset.poista(kierros);
    }
    
    
    /**
     * Kertoo i:nnen kierroksen
     * @param pelaaja jonka kierroksia haetaan
     * @return tietorakenne, jossa on viitteet löydettyihin kierroksiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     * Kerho kerho = new Kerho();
     * Pelaaja p1 = new Pelaaja(), p2 = new Pelaaja(), p3 = new Pelaaja();
     * p1.rekisteroi(); p2.rekisteroi(); p3.rekisteroi();
     * 
     * int id1 = p1.getpelaajaNro();
     * int id2 = p2.getpelaajaNro();
     * 
     * Kierros k1 = new Kierros(id1); kerho.lisaa(k1);
     * Kierros k2 = new Kierros(id1); kerho.lisaa(k2);
     * Kierros k3 = new Kierros(id2); kerho.lisaa(k3);
     * Kierros k4 = new Kierros(id2); kerho.lisaa(k4);
     * Kierros k5 = new Kierros(id2); kerho.lisaa(k5);
     * 
     * List<Kierros> loytyneet;
     * loytyneet = kerho.annaKierrokset(p3);
     * loytyneet.size() === 0;
     * loytyneet = kerho.annaKierrokset(p1);
     * loytyneet.size() === 2;
     * loytyneet.get(0) == k1 === true;
     * loytyneet.get(1) == k2 === true;
     * loytyneet = kerho.annaKierrokset(p2);
     * loytyneet.size() === 3;
     * loytyneet.get(0) == k3 === true;
     * </pre>
     */
    public List<Kierros> annaKierrokset(Pelaaja pelaaja) {
        return kierrokset.annaKierrokset(pelaaja.getpelaajaNro());
    }
    
    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "Paras Golfkerho";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        pelaajat.setTiedostonPerusNimi(hakemistonNimi + "pelaajat");
        kierrokset.setTiedostonPerusNimi(hakemistonNimi + "kierrokset");
        kentat.setTiedostonPerusNimi(hakemistonNimi + "kerhot");
    }
    
    
    /**
     * Tarkistaa pelaajien lukumäärän
     * @return palauttaa pelaajien lukumäärän
     */
    public int getPelaajia() {
        return pelaajat.getLkm();
    }
    
    
    /**
     * Kertoo i:nnen pelaajan
     * @param i pelaajan indeksi
     * @return palauttaa i:nnen pelaajan
     */
    public Pelaaja annaPelaaja(int i) {
        return pelaajat.anna(i);
    }
    
  
    /**
     * Lukee kerhon tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;
     * 
     *  Kerho kerho = new Kerho();
     *  
     *  Pelaaja aku1 = new Pelaaja(); aku1.vastaaAkuAnkka(); aku1.rekisteroi();
     *  Pelaaja aku2 = new Pelaaja(); aku2.vastaaAkuAnkka(); aku2.rekisteroi();
     *  Kierros pitsi21 = new Kierros(); pitsi21.vastaaKierros(aku2.getpelaajaNro());
     *  Kierros pitsi11 = new Kierros(); pitsi11.vastaaKierros(aku1.getpelaajaNro());
     *  Kierros pitsi22 = new Kierros(); pitsi22.vastaaKierros(aku2.getpelaajaNro()); 
     *  Kierros pitsi12 = new Kierros(); pitsi12.vastaaKierros(aku1.getpelaajaNro()); 
     *  Kierros pitsi23 = new Kierros(); pitsi23.vastaaKierros(aku2.getpelaajaNro());
     *   
     *  String hakemisto = "koekerho";
     *  File dir = new File(hakemisto);
     *  File ftied  = new File(hakemisto+"/pelaajat.dat");
     *  File fhtied = new File(hakemisto+"/kierrokset.dat");
     *  dir.mkdir();  
     *  ftied.delete();
     *  fhtied.delete();
     *  kerho.lueTiedostosta(hakemisto); #THROWS SailoException
     *  kerho.lisaa(aku1);
     *  kerho.lisaa(aku2);
     *  kerho.lisaa(pitsi21);
     *  kerho.lisaa(pitsi11);
     *  kerho.lisaa(pitsi22);
     *  kerho.lisaa(pitsi12);
     *  kerho.lisaa(pitsi23);
     *  kerho.tallenna();
     *  kerho = new Kerho();
     *  kerho.lueTiedostosta(hakemisto);
     *  Collection<Pelaaja> kaikki = kerho.etsi("",-1); 
     *  Iterator<Pelaaja> it = kaikki.iterator();
     *  it.next() === aku1;
     *  it.next() === aku2;
     *  it.hasNext() === false;
     *  List<Kierros> loytyneet = kerho.annaKierrokset(aku1);
     *  Iterator<Kierros> ih = loytyneet.iterator();
     *  ih.next() === pitsi11;
     *  ih.next() === pitsi12;
     *  ih.hasNext() === false;
     *  loytyneet = kerho.annaKierrokset(aku2);
     *  ih = loytyneet.iterator();
     *  ih.next() === pitsi21;
     *  ih.next() === pitsi22;
     *  ih.next() === pitsi23;
     *  ih.hasNext() === false;
     *  kerho.lisaa(aku2);
     *  kerho.lisaa(pitsi23);
     *  kerho.tallenna();
     *  ftied.delete()  === true;
     *  fhtied.delete() === true;
     *  File fbak = new File(hakemisto+"/pelaajat.bak");
     *  File fhbak = new File(hakemisto+"/kierrokset.bak");
     *  fbak.delete() === true;
     *  fhbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        pelaajat = new Pelaajat(); // jos luetaan olemassa olevaan niin helpoin tyhjentää näin
        kierrokset = new Kierrokset();
        kentat = new Kentat();

        setTiedosto(nimi);
        pelaajat.lueTiedostosta(nimi);
        kierrokset.lueTiedostosta(nimi);
        kentat.lueTiedostosta(nimi);
    }


    /**
     * Vaikka pelaajien tallettamien epäonistuisi, niin yritetään silti tallettaa
     * kierroksia ennen poikkeuksen heittämistä.
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            pelaajat.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }

        try {
            kierrokset.tallenna();
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        
        try {
            kentat.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }

   
    /**
     * @param args ei käytössä
     */ 
    public static void main(String[] args) {
        Kerho kerho = new Kerho();
        
        try {
            kerho.lueTiedostosta("Paras Golfkerho");
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
 
        Pelaaja p1 = new Pelaaja();
        Pelaaja p2 = new Pelaaja();
        p1.rekisteroi();
        p1.rekisteroiOsake();
        p1.vastaaAkuAnkka();        
        p2.rekisteroi();
        p2.rekisteroiOsake();
        p2.vastaaAkuAnkka();
        
        try {
            kerho.lisaa(p1);
            kerho.lisaa(p2);

            for (int i=0; i<kerho.getPelaajia(); i++) {
                Pelaaja pelaaja = kerho.annaPelaaja(i);
                pelaaja.tulosta(System.out);
            }
            
            kerho.tallenna();
        } catch (SailoException e) {
            // e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}