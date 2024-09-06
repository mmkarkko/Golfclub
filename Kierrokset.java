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
    private String kokoNimi = "";

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
     *      * @example
     * <pre name="test">
     * Kierrokset kierrokset = new Kierrokset();
     * Kierros k1 = new Kierros(), k2 = new Kierros();
     * kierrokset.getLkm() === 0;
     * kierrokset.lisaa(k1); kierrokset.getLkm() === 1;
     * kierrokset.lisaa(k2); kierrokset.getLkm() === 2;
     * kierrokset.lisaa(k1); kierrokset.getLkm() === 3;
     * kierrokset.lisaa(k1); kierrokset.getLkm() === 4;
     * kierrokset.lisaa(k1); kierrokset.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Kierros kierros){
        alkiot.add(kierros);
        muutettu = true;
    }
    
    
    /**
     * Korvaa harrastuksen tietorakenteessa.  Ottaa harrastuksen omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva harrastus.  Jos ei löydy,
     * niin lisätään uutena harrastuksena.
     * @param kierros lisättävän kierroksen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Kierrokset kierrokset = new Kierrokset();
     * Kierros har1 = new Kierros(), har2 = new Kierros();
     * har1.rekisteroi(); har2.rekisteroi();
     * kierrokset.getLkm() === 0;
     * kierrokset.korvaaTaiLisaa(har1); kierrokset.getLkm() === 1;
     * kierrokset.korvaaTaiLisaa(har2); kierrokset.getLkm() === 2;
     * Kierros har3 = har1.clone();
     * har3.aseta(2,"kkk");
     * Iterator<Kierros> i2=kierrokset.iterator();
     * i2.next() === har1;
     * kierrokset.korvaaTaiLisaa(har3); kierrokset.getLkm() === 2;
     * i2=kierrokset.iterator();
     * Kierros h = i2.next();
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
     * Poistaa valitun kierroksen
     * @param kierros joka poistetaan
     * @return tosi, jos löytyi poistettava kierros
     * @example
     * <pre name="test">
     *  #import java.io.File;
     *  Kierrokset kierrokset = new Kierrokset();
     *  Kierros k1 = new Kierros(); k1.vastaaKierros(2);
     *  Kierros k2 = new Kierros(); k2.vastaaKierros(1);
     *  Kierros k3 = new Kierros(); k3.vastaaKierros(2);
     *  kierrokset.lisaa(k1);
     *  kierrokset.poista(k1) === true;
     *  kierrokset.poista(k2) === false;
     * </pre>
     */
    public boolean poista(Kierros kierros) {
        boolean ret = alkiot.remove(kierros);
        if (ret) muutettu = true;
        return ret;
    }
    
    
    /**
     * Poistaa kaikki tietyn pelaajan kierrokset
     * @param tunnusNro viite siihe, mihin pelaajaan liittyvät kierrokset poistetaan
     * @return montako poistettiin
     * @example
     * <pre name="test">
     *  Kierrokset kierrokset = new Kierrokset();
     *  Kierros pitsi21 = new Kierros(); pitsi21.vastaaKierros(2);
     *  Kierros pitsi11 = new Kierros(); pitsi11.vastaaKierros(1);
     *  Kierros pitsi22 = new Kierros(); pitsi22.vastaaKierros(2);
     *  Kierros pitsi12 = new Kierros(); pitsi12.vastaaKierros(1);
     *  Kierros pitsi23 = new Kierros(); pitsi23.vastaaKierros(2);
     *  kierrokset.lisaa(pitsi21);
     *  kierrokset.lisaa(pitsi11);
     *  kierrokset.lisaa(pitsi22);
     *  kierrokset.lisaa(pitsi12);
     *  kierrokset.lisaa(pitsi23);
     *  kierrokset.poistaPelaajanKierrokset(2) === 3;  kierrokset.getLkm() === 2;
     *  kierrokset.poistaPelaajanKierrokset(3) === 0;  kierrokset.getLkm() === 2;
     *  List<Kierros> h = kierrokset.annaKierrokset(2);
     *  h.size() === 0;
     *  h = kierrokset.annaKierrokset(1);
     *  h.get(0) === pitsi11;
     *  h.get(1) === pitsi12;
     * </pre>
     */
    public int poistaPelaajanKierrokset(int tunnusNro) {
        int n = 0;
        for (Iterator<Kierros> it = alkiot.iterator(); it.hasNext();) {
            Kierros ki = it.next();
            if (ki.getPelaajaNro() == tunnusNro) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
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
     *  Kierros k1 = new Kierros(); k1.vastaaKierros(2);
     *  Kierros k2 = new Kierros(); k2.vastaaKierros(1);
     *  Kierros k3 = new Kierros(); k3.vastaaKierros(2); 
     *  Kierros k4 = new Kierros(); k4.vastaaKierros(1); 
     *  Kierros k5 = new Kierros(); k5.vastaaKierros(2); 
     *  String tiedNimi = "koeKerho";
     *  File ftied = new File(tiedNimi + ".dat");
     *  ftied.delete();
     *  kierrokset.lueTiedostosta(tiedNimi); 
     *  kierrokset.lisaa(k1);
     *  kierrokset.lisaa(k2);
     *  kierrokset.lisaa(k3);
     *  kierrokset.lisaa(k4);
     *  kierrokset.lisaa(k5);
     *  kierrokset.tallenna();
     *  kierrokset = new Kierrokset();
     *  kierrokset.lueTiedostosta(tiedNimi);
     *  Iterator<Kierros> i = kierrokset.iterator();
     *  i.next().toString() === k1.toString();
     *  i.next().toString() === k2.toString();
     *  i.next().toString() === k3.toString();
     *  i.next().toString() === k4.toString();
     *  i.next().toString() === k5.toString();
     *  i.hasNext() === false;
     *  kierrokset.lisaa(k5);
     *  kierrokset.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            kokoNimi = fi.readLine();
            if (kokoNimi == null) throw new SailoException("Kerhon nimi puuttuu");
            String rivi = fi.readLine();
            if (rivi == null) throw new SailoException("Maksimikoko puuttuu");
            
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
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Kierrokset kierrokset = new Kierrokset();
     *  Kierros pitsi21 = new Kierros(2); kierrokset.lisaa(pitsi21);
     *  Kierros pitsi11 = new Kierros(1); kierrokset.lisaa(pitsi11);
     *  Kierros pitsi22 = new Kierros(2); kierrokset.lisaa(pitsi22);
     *  Kierros pitsi12 = new Kierros(1); kierrokset.lisaa(pitsi12);
     *  Kierros pitsi23 = new Kierros(2); kierrokset.lisaa(pitsi23);
     * 
     *  Iterator<Kierros> i2=kierrokset.iterator();
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
     *  for ( Kierros har:kierrokset ) { 
     *    har.getPelaajaNro() === jnrot[n]; n++;  
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
     * 
     * Kierrokset kierrokset = new Kierrokset();
     * Kierros k21 = new Kierros(2); kierrokset.lisaa(k21);
     * Kierros k11 = new Kierros(1); kierrokset.lisaa(k11);
     * Kierros k22 = new Kierros(2); kierrokset.lisaa(k22);
     * Kierros k12 = new Kierros(1); kierrokset.lisaa(k12);
     * Kierros k23 = new Kierros(2); kierrokset.lisaa(k23);
     * Kierros k51 = new Kierros(5); kierrokset.lisaa(k51);
     * 
     * List<Kierros> loytyneet;
     * loytyneet = kierrokset.annaKierrokset(3);
     * loytyneet.size() === 0;
     * loytyneet = kierrokset.annaKierrokset(1);
     * loytyneet.size() === 2;
     * loytyneet.get(0) == k11 === true;
     * loytyneet.get(1) == k12 === true;
     * loytyneet = kierrokset.annaKierrokset(5);
     * loytyneet.size() === 1;
     * loytyneet.get(0) == k51 === true;
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
     */
    public static void main(String[] args) {
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
