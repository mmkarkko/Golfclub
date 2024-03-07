/**
 * 
 */
package kerho;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

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
 * @version 6.3.2023
 *
 */
public class Kierrokset {
    
    private Collection<Kierros> alkiot = new ArrayList<Kierros>();
    
    /**
     * Alustetaan
     */
    public Kierrokset() {
        //
    }
  
    /**
     * Haetaan kaikki pelaajan kierrokset
     * @param tunnusNro pelaajan pelaajanumero, johon liittyviä kierroksia haetaan
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
    public List<Kierros> annaKierrokset(int tunnusNro) {
        List<Kierros> loydetyt = new ArrayList<Kierros>();
        for (Kierros k : alkiot) // iteraattori
            if (k.getPelaajaNro() == tunnusNro) loydetyt.add(k);
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
     * Lukee harrastukset tiedostosta.
     * @param hakemisto tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Kierrokset kierrokset = new Kierrokset();
     *  Kierros pitsi21 = new Kierros(); pitsi21.vastaaKierros(2);
     *  Kierros pitsi11 = new Kierros(); pitsi11.vastaaKierros(1);
     *  Kierros pitsi22 = new Kierros(); pitsi22.vastaaKierros(2); 
     *  Kierros pitsi12 = new Kierros(); pitsi12.vastaaKierros(1); 
     *  Kierros pitsi23 = new Kierros(); pitsi23.vastaaKierros(2); 
     *  String tiedNimi = "testikelmit";
     *  File ftied = new File(tiedNimi + "/kierrokset.dat");
     *  ftied.delete();
     *  kierrokset.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  kierrokset.lisaa(pitsi21);
     *  kierrokset.lisaa(pitsi11);
     *  kierrokset.lisaa(pitsi22);
     *  kierrokset.lisaa(pitsi12);
     *  kierrokset.lisaa(pitsi23);
     *  kierrokset.tallenna(tiedNimi);
     *  kierrokset = new Kierrokset();
     *  kierrokset.lueTiedostosta(tiedNimi);
     *  Iterator<Kierros> i = kierrokset.iterator();
     *  i.next().toString() === pitsi21.toString();
     *  i.next().toString() === pitsi11.toString();
     *  i.next().toString() === pitsi22.toString();
     *  i.next().toString() === pitsi12.toString();
     *  i.next().toString() === pitsi23.toString();
     *  i.hasNext() === false;
     *  kierrokset.lisaa(pitsi23);
     *  kierrokset.tallenna(tiedNimi);
     *  ftied.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        String nimi = hakemisto + "/kierrokset.dat";
        File ftied = new File(nimi);
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) { // Jotta UTF8/ISO-8859 toimii'
            while ( fi.hasNext() ) {
                String s = fi.nextLine().trim();
                if ( "".equals(s) || s.charAt(0) == ';' ) continue;
                Kierros kier = new Kierros();
                kier.parse(s); // voisi olla virhekäsittely
                lisaa(kier);
            }
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Ei saa luettua tiedostoa " + nimi);
        }
    }
    
    
    /**
     * Tallentaa kierroksia tiedostoon
     * Tiedoston muoto:
     * <pre>
     * 1|Pelaaja Petteri|070819-5398|5,4|000-9999999|petepelaaja@golffari.fi|Pelimiehenkuja 1|11111 Pelilä|1|OK|1|
     * 2|Teetime Teemu|190895-943M |19,3 |111-2221111|tsteetime@golffari.fi|Tiikuja 1|11001 Tiiala||OK|1|
     * </pre>
     * @param hakemisto tallennettavan tiedoston hakemisto
     * @throws SailoException mikäli tallennus epäonnistuu
     */
    public void tallenna(String hakemisto) throws SailoException {
        File ftied = new File(hakemisto + "/kierrokset.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
            for(var kier: alkiot)
                fo.println(kier.toString());        
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath() +  " ei aukea");
        }
    }
    
    
    
    
    
    /**
     * Testataan
     * @param args ei käytossä
     * @throws SailoException jos liikaa
     */
    public static void main(String[] args) throws SailoException {
        Kierrokset kierrokset = new Kierrokset();   
        
        try  {
            kierrokset.lueTiedostosta("kierrokset");
        } catch (SailoException ex){
            System.err.println(ex.getMessage());
        }
        
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
        kierrokset.lisaa(k4);
  
        System.out.println("============= Kierrokset testi =================");
        
        var kierrokset2 = kierrokset.annaKierrokset(2);
        for (Kierros k : kierrokset2) {
            System.out.print(k.getPelaajaNro() + " ");
            k.tulosta(System.out);
        }
        
        try {
            kierrokset.tallenna("kierrokset");
        } catch (SailoException ex) {
            ex.printStackTrace();
        }
    }
}
