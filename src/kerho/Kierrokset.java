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
     * @param args ei käytossä
     * @throws SailoException jos liikaa
     */
    public static void main(String[] args) throws SailoException {
        Kierrokset kierrokset = new Kierrokset();
    
        
        Kierros k1 = new Kierros();
        Kierros k2 = new Kierros();
        Kierros k3 = new Kierros();
        
        k1.rekisteroi();
        k1.vastaaKierros(1);
        
        k2.rekisteroi();
        k2.vastaaKierros(2);
        
        k3.rekisteroi();
        k3.vastaaKierros(1);
        
//        try {
//            kierrokset.lisaa(k1);
//            kierrokset.lisaa(k2);
//            kierrokset.lisaa(k3);
//
//        } catch (SailoException e) {
//            System.err.println(e.getMessage());
//        }   
        
        var kierrokset2 = kierrokset.annaKierrokset(1);
        
        System.out.println("============= Jäsenet testi =================");
        
        for (Kierros har : kierrokset2) {
            System.out.print(har.getPelaajaNro() + " ");
            har.tulosta(System.out);
        
//        for (int i = 0; i < kierrokset.getLkm(); i++) {
//            Kierros kierros = kierrokset.anna(i);
//            System.out.println("Jäsen indeksi: " + i);
//            kierros.tulosta(System.out);
        } 
    }

}
