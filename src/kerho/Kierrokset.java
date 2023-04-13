/**
 * 
 */
package kerho;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;



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
public class Kierrokset implements Iterable<Kierros>{
    
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "";

    private final List<Kierros> alkiot = new ArrayList<Kierros>();
    
    /**
     * Alustetaan
     */
    public Kierrokset() {
        //
    }
    
    
    /**
     * Lisää uuden kierroksen tietorakenteeseen.  Ottaa kierroksen omistukseensa.
     * @param kierros lisättävän kierroksen viite. Huom. tietorakenne muuttuu omistajaksi
     */
    public void lisaa(Kierros kierros){
        alkiot.add(kierros);
        muutettu = true;
    }
    
    
    /**
     * TODO: korjaa testit
     * Korvaa harrastuksen tietorakenteessa.  Ottaa harrastuksen omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva harrastus.  Jos ei löydy,
     * niin lisätään uutena harrastuksena.
     * @param kierros lisättävän kierroksen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Harrastukset harrastukset = new Harrastukset();
     * Harrastus har1 = new Harrastus(), har2 = new Harrastus();
     * har1.rekisteroi(); har2.rekisteroi();
     * harrastukset.getLkm() === 0;
     * harrastukset.korvaaTaiLisaa(har1); harrastukset.getLkm() === 1;
     * harrastukset.korvaaTaiLisaa(har2); harrastukset.getLkm() === 2;
     * Harrastus har3 = har1.clone();
     * har3.aseta(2,"kkk");
     * Iterator<Harrastus> i2=harrastukset.iterator();
     * i2.next() === har1;
     * harrastukset.korvaaTaiLisaa(har3); harrastukset.getLkm() === 2;
     * i2=harrastukset.iterator();
     * Harrastus h = i2.next();
     * h === har3;
     * h == har3 === true;
     * h == har1 === false;
     * </pre>
     */ 
    public void korvaaTaiLisaa(Kierros kierros) throws SailoException {
        int id = kierros.getKierrosNro();
        for (int i = 0; i < alkiot.size(); i++) {
            if (alkiot.get(i).getKierrosNro() == id) {
                alkiot.set(i, kierros);
                muutettu = true;
                return;
            }
        }
        lisaa(kierros);
    }

  
    
    
    
    
    
      
    /**
     * Lukee harrastukset tiedostosta.
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
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
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Kierros har = new Kierros();
                har.parse(rivi); // voisi olla virhekäsittely
                lisaa(har);
            }
            muutettu = false;

        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }

    }
    
    
    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }

    
    
    /**
     * Tallentaa kierroksia tiedostoon
     * Tiedoston muoto:
     * <pre>
     * 1|Pelaaja Petteri|070819-5398|5,4|000-9999999|petepelaaja@golffari.fi|Pelimiehenkuja 1|11111 Pelilä|1|OK|1|
     * 2|Teetime Teemu|190895-943M |19,3 |111-2221111|tsteetime@golffari.fi|Tiikuja 1|11001 Tiiala||OK|1|
     * </pre>
     * @throws SailoException mikäli tallennus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); //  if ... System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); //  if ... System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Kierros k : this) {
                fo.println(k.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;

    }
    
    
    /**
     * Palauttaa kerhon harrastusten lukumäärän
     * @return harrastusten lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied + "/kierrokset";
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    
    
    /**
     * Iteraattori kaikkien harrastusten läpikäymiseen
     * @return harrastusiteraattori
     * TODO: korjaa testit
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Harrastukset harrasteet = new Harrastukset();
     *  Harrastus pitsi21 = new Harrastus(2); harrasteet.lisaa(pitsi21);
     *  Harrastus pitsi11 = new Harrastus(1); harrasteet.lisaa(pitsi11);
     *  Harrastus pitsi22 = new Harrastus(2); harrasteet.lisaa(pitsi22);
     *  Harrastus pitsi12 = new Harrastus(1); harrasteet.lisaa(pitsi12);
     *  Harrastus pitsi23 = new Harrastus(2); harrasteet.lisaa(pitsi23);
     * 
     *  Iterator<Harrastus> i2=harrasteet.iterator();
     *  i2.next() === pitsi21;
     *  i2.next() === pitsi11;
     *  i2.next() === pitsi22;
     *  i2.next() === pitsi12;
     *  i2.next() === pitsi23;
     *  i2.next() === pitsi12;  #THROWS NoSuchElementException  
     *  
     *  int n = 0;
     *  int jnrot[] = {2,1,2,1,2};
     *  
     *  for ( Harrastus har:harrasteet ) { 
     *    har.getJasenNro() === jnrot[n]; n++;  
     *  }
     *  
     *  n === 5;
     *  
     * </pre>
     */
    @Override
    public Iterator<Kierros> iterator() {
        return alkiot.iterator();
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
        
        List<Kierros> harrastukset2 = kierrokset.annaKierrokset(2);

        for (Kierros har : harrastukset2) {
            System.out.print(har.getPelaajaNro() + " ");
            har.tulosta(System.out);
        }

    }
}
