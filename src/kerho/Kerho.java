/**
 * 
 */
package kerho;

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
 * @version 24.2.2023
 *
 */
public class Kerho {

    private Pelaajat pelaajat = new Pelaajat();
    private Kierrokset kierrokset = new Kierrokset();
    
    
    /**
     * Lisätään uusi pelaaja
     * @param pelaaja lisättävä pelaaja
     * @throws SailoException jos lisääminen ei onnistu
     */
    public void lisaaPelaaja(Pelaaja pelaaja) throws SailoException {
        pelaajat.lisaa(pelaaja);
    }
    
    
    /**
     * Lisätään uusi kierros
     * @param har lisättävä kierros
     */
    public void lisaaKierros(Kierros har) {
        kierrokset.lisaa(har);
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
     * Kierros k1 = new Kierros(id1); kerho.lisaaKierros(k1);
     * Kierros k2 = new Kierros(id1); kerho.lisaaKierros(k2);
     * Kierros k3 = new Kierros(id2); kerho.lisaaKierros(k3);
     * Kierros k4 = new Kierros(id2); kerho.lisaaKierros(k4);
     * Kierros k5 = new Kierros(id2); kerho.lisaaKierros(k5);
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
     * @param args ei käytössä
     */ 
    public static void main(String[] args) {
        
        Kerho kerho = new Kerho();
        
        Pelaaja p1 = new Pelaaja();
        Pelaaja p2 = new Pelaaja();
        Pelaaja p3 = new Pelaaja();
        
        p1.rekisteroi();
        p1.vastaaAkuAnkka();
        
        p2.rekisteroi();
        p2.vastaaAkuAnkka();
        
        p3.rekisteroi();
        p3.vastaaAkuAnkka();
    
        
        try {
            kerho.lisaaPelaaja(p1);
            kerho.lisaaPelaaja(p2);
            kerho.lisaaPelaaja(p3);
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }
                
        for (int i = 0; i < kerho.getPelaajia(); i++) {
            Pelaaja pelaaja = kerho.annaPelaaja(i);
            pelaaja.tulosta(System.out);
        }

    }
    
}
