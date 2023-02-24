/**
 * 
 */
package kerho;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Kierrokset-luokka
 * - Pitää yllä tietoja kaikista kierroksista eli
 *   osaa lisätä kierroksen
 * - Tietää, onko kierroksen poistaminen sallittua ja
 *   osaa tarvittaessa poistaa kierroksen
 * - Lukee ja kirjoittaa kerhojen tiedostoon
 * - Osaa etsiä ja lajitella kierroksia
 * - Avustajaluokat: Kierros
 * @author Miia Arkko
 * @version 24.2.2023
 *
 */
public class Kierrokset {
    
    private Collection<Kierros> alkiot = new ArrayList<Kierros>();

    
    /**
     * Haetaan kaikki pelaajan kierrokset
     * @param pelaajaNro pelaajan pelaajanumero, johon liittyviä kierroksia haetaan
     * @return tietorakenne, jossa viitteet löydettyihin kierroksiin
     * @example
     * <pre name="test">
     * #import java.util.*;
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
    public List<Kierros> annaKierrokset(int pelaajaNro) {
        List<Kierros> loydetyt = new ArrayList<Kierros>();
        for (Kierros har : alkiot) // iteraattori
            if (har.getPelaajaNro() == pelaajaNro) loydetyt.add(har);
        return loydetyt;
        
    }

    
    /**
     * Lisää uuden kierroksen tietorakenteeseen.  Ottaa kierroksen omistukseensa.
     * @param kierros lisättävän kierroksen viite. Huom. tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Kierros kierros){
        alkiot.add(kierros);
    }
    
    
    /**
     * Testataan
     * @param args ei käytossä
     * @throws SailoException jos liikaa
     */
    public static void main(String[] args) throws SailoException {
        Kierrokset kierrokset = new Kierrokset();
    
        
        Kierros k1 = new Kierros();
        k1.vastaaKierros(2);
        Kierros k2 = new Kierros();
        k2.vastaaKierros(1);
        Kierros k3 = new Kierros();
        k3.vastaaKierros(2);
        Kierros k4 = new Kierros();
        k4.vastaaKierros(2);
        
        kierrokset.lisaa(k1);
        kierrokset.lisaa(k2);
        kierrokset.lisaa(k3);
        kierrokset.lisaa(k2);
        kierrokset.lisaa(k4);
  
        System.out.println("============= Kierrokset testi =================");
        
        var kierrokset2 = kierrokset.annaKierrokset(1);
        for (Kierros k : kierrokset2) {
            System.out.print(k.getPelaajaNro() + " ");
            k.tulosta(System.out);
        }
    }
}
