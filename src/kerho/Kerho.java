/**
 * 
 */
package kerho;

import java.io.File;
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
 * @version 6.3.2023
 *
 */
public class Kerho {

    private Pelaajat pelaajat = new Pelaajat();
    private Kierrokset kierrokset = new Kierrokset();
    private String hakemisto = "koekelmit";
    
    
    /**
     * Lisätään uusi pelaaja
     * @param pelaaja lisättävä pelaaja
     * @throws SailoException jos lisääminen ei onnistu
     */
    public void lisaa(Pelaaja pelaaja) throws SailoException {
        pelaajat.lisaa(pelaaja);
    }
    
    
    /**
     * Lisätään uusi kierros
     * @param k lisättävä kierros
     */
    public void lisaa(Kierros k) {
        kierrokset.lisaa(k);
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
     * Kertoo i:nnen kierroksen
     * @param pelaaja jonka kierroksia haetaan
     * @return tietorakenne, jossa on viitteet löydettyihin kierroksiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * Kerho kerho = new Kerho();
     * Pelaaja p1 = new Pelaaja(), p2 = new Pelaaja(), p3 = new Pelaaja();
     * p1.rekisteroi(); p2.rekisteroi(); p3.rekisteroi();
     * int id1 = p1.getpelaajaNro();
     * int id2 = p2.getpelaajaNro();
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
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        File dir = new File(nimi);
        dir.mkdir();
        pelaajat = new Pelaajat(); // jos luetaan olemassa olevaan niin helpoin tyhjentää näin
        kierrokset = new Kierrokset();

        hakemisto = nimi;
        pelaajat.lueTiedostosta(nimi);
        kierrokset.lueTiedostosta(nimi);
    }


    /**
     * Tallettaa kerhon tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            pelaajat.tallenna(hakemisto);
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }

        try {
            kierrokset.tallenna(hakemisto);
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }

   
    /**
     * @param args ei käytössä
     */ 
    public static void main(String[] args) {
        Kerho kerho = new Kerho();
        
        try {
            kerho.lueTiedostosta("koekelmit");
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