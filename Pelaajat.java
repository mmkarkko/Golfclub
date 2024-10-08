package kerho;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

import fi.jyu.mit.ohj2.WildChars;

/**
 * Pelaajat-luokka
 * - Pitää yllä varsinaista pelaajarekisteriä, eli
 *   osaa lisätä tai poistaa jäsenen
 * - Lukee ja kirjoittaa pelaajien tiedostoon
 * - Osaa etsiä ja lajitella pelaajia
 * - Avustajaluokat: pelaaja
 * 
 * @author Miia Arkko
 * @version 3.4.2023
 *
 */
public class Pelaajat implements Iterable<Pelaaja> {
    
    private static final int MAX_PELAAJIA = 10;
    private boolean muutettu = false;
    private int lkm = 0;
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "";
    private Pelaaja[] alkiot = new Pelaaja[MAX_PELAAJIA];

    
    /**
     * Oletusmuodostaja
     */
    public Pelaajat() {
        
    }
    
    
    /**
     * Lisää uuden pelaajan tietorakenteeseen.  Ottaa pelaaja omistukseensa.
     * @param pelaaja lisätäävän pelaajan viite.  Huom tietorakenne muuttuu omistajaksi
     * @example
     * <pre name="test">
     * Pelaajat pelaajat = new Pelaajat();
     * Pelaaja aku1 = new Pelaaja(), aku2 = new Pelaaja();
     * pelaajat.getLkm() === 0;
     * pelaajat.lisaa(aku1); pelaajat.getLkm() === 1;
     * pelaajat.lisaa(aku2); pelaajat.getLkm() === 2;
     * pelaajat.lisaa(aku1); pelaajat.getLkm() === 3;
     * pelaajat.anna(0) === aku1;
     * pelaajat.anna(1) === aku2;
     * pelaajat.anna(2) === aku1;
     * pelaajat.anna(1) == aku1 === false;
     * pelaajat.anna(1) == aku2 === true;
     * pelaajat.anna(3) === aku1; #THROWS IndexOutOfBoundsException 
     * pelaajat.lisaa(aku1); pelaajat.getLkm() === 4;
     * pelaajat.lisaa(aku1); pelaajat.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Pelaaja pelaaja)  {         
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+20); 
        alkiot[lkm] = pelaaja;
        lkm++;
        muutettu = true;

    }
    
    
    /**
     * Korvaa pelaajan tietorakenteessa. Ottaa pelaajan omistukseensa
     * Etsitään samalla pelaajanumerolla oleva pelaaja. Jos ei löydy, lisätään
     * uutena pelaajana.
     * @param pelaaja lisättävän pelaajan viite. Huom, tietorakenne muuttuu omistajaksi
     * 
     * <pre name="test">
     *  #import java.util.Iterator;
     *  #THROWS SailoException,CloneNotSupportedException
     *  #PACKAGEIMPORT
     *  Pelaajat pelaajat = new Pelaajat();
     *  Pelaaja har1 = new Pelaaja(), har2 = new Pelaaja();
     *  har1.rekisteroi(); har2.rekisteroi();
     *  pelaajat.getLkm() === 0;
     *  pelaajat.korvaaTaiLisaa(har1); pelaajat.getLkm() === 1;
     *  pelaajat.korvaaTaiLisaa(har2); pelaajat.getLkm() === 2;
     *  try {
     *      Pelaaja har3 = har1.clone();
     *      har3.aseta(2,"kkk");
     *      Iterator<Pelaaja> i2=pelaajat.iterator();
     *      i2.next() === har1;
     *      pelaajat.korvaaTaiLisaa(har3); pelaajat.getLkm() === 2;
     *      i2=pelaajat.iterator();
     *      Pelaaja h = i2.next();
     *      h === har3;
     *      h == har3 === true;
     *      h == har1 === false;
     *      } catch (CloneNotSupportedException e) {
     *      System.out.println("Tulee poikkeus");
     *  } 
     * </pre>
     */
    public void korvaaTaiLisaa(Pelaaja pelaaja) {
        int id = pelaaja.getpelaajaNro();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getpelaajaNro() == id) {
                alkiot[i] = pelaaja;
                muutettu  = true;
                return;
            }
        }
        lisaa(pelaaja);
    }
    
    
    /**
     * Poistaa pelaajan, jolla on valittu pelaajanumero
     * @param id poistettavan pelaajan pelaajaID
     * @return 1 jos jotakin poistettiin, 0 jos ei löydy
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Pelaajat pelaajat = new Pelaajat();
     * Pelaaja p1 = new Pelaaja();
     * Pelaaja p2 = new Pelaaja();
     * pelaajat.lisaa(p1);
     * pelaajat.lisaa(p2);
     * pelaajat.poista(1) === 1;
     * </pre>
     */
    public int poista(int id) {
        int ind = etsiId(id);
        if (ind < 0) return 0;
        lkm--;
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i+1];
        alkiot[lkm] = null;
        muutettu = true;
        return 1;        
    }
    
    
    /**
     * Etsii pelaajan id:n perusteella
     * @param id pelaajaID, jonka mukaan etsitään
     * @return löytyneen pelaajan inteksi tai -1 jos ei löydy
     */
    public int etsiId(int id) {
        for(int i = 0; i < lkm; i++)
            if (id == alkiot[i].getpelaajaNro()) return i;
        return -1;
    }
    
    
    /**
     * Palauttaa viitteen i:teen pelaajaan.
     * @param i monennenko pelaajan viite halutaan
     * @return viite pelaajaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Pelaaja anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || this.lkm <= i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
    /**
     * Luetaan pelaajien tiedostosta (kesken)
     * @param tied tiedoston perusnimi
     * @throws SailoException jos lukeminen ei onnistu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Pelaajat pelaajat = new Pelaajat();
     *  Pelaaja aku1 = new Pelaaja(), aku2 = new Pelaaja();
     *  aku1.vastaaAkuAnkka();
     *  aku2.vastaaAkuAnkka();
     *  String hakemisto = "koeKerho";
     *  String tiedNimi = hakemisto+"/pelaajat";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  
     *  pelaajat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  pelaajat.lisaa(aku1);
     *  pelaajat.lisaa(aku2);
     *  pelaajat.tallenna();
     *  pelaajat = new Pelaajat();            // Poistetaan vanhat luomalla uusi
     *  pelaajat.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Pelaaja> i = pelaajat.iterator();
     *  i.next() === aku1;
     *  i.next() === aku2;
     *  i.hasNext() === false;
     *  pelaajat.lisaa(aku2);
     *  pelaajat.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre> 
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            kokoNimi = fi.readLine();
            if ( kokoNimi == null ) throw new SailoException("Kerhon nimi puuttuu");
            String rivi = fi.readLine();
            if ( rivi == null ) throw new SailoException("Maksimikoko puuttuu");
            // int maxKoko = Mjonot.erotaInt(rivi,10); // tehdään jotakin

            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Pelaaja pelaaja = new Pelaaja();
                pelaaja.parse(rivi); // voisi olla virhekäsittely
                lisaa(pelaaja);
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
     * Tallentaa pelaajia tiedostoon
     * Tiedoston muoto:
     * <pre>
     * 1|Pelaaja Petteri|070819-5398|5,4|000-9999999|petepelaaja@golffari.fi|Pelimiehenkuja 1|11111 Pelilä|1|OK|1|
     * 2|Teetime Teemu|190895-943M |19,3 |111-2221111|tsteetime@golffari.fi|Tiikuja 1|11001 Tiiala||OK|1|
     * </pre>
     * @throws SailoException mikäli tallennus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if (!muutettu) return;
        
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(getKokoNimi());
            fo.println(alkiot.length);
            for (Pelaaja pelaaja : this) {
                fo.println(pelaaja.toString());
            }
            //} catch ( IOException e ) { // ei heitä poikkeusta
            //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;

    }
    
    
    /**
     * Palauttaa kerhon koko nimen
     * @return kerhon nimi merkkijonona
     */
    public String getKokoNimi() {
        return kokoNimi;
    }
    
    
    /**
     * Palauttaa kerhon pelaajien lukumäärän
     * @return pelaajien lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }

    
    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi + "/pelaajat";
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
     * Luokka pelaajien iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Pelaajat pelaajat = new Pelaajat();
     * Pelaaja aku1 = new Pelaaja(), aku2 = new Pelaaja();
     * aku1.rekisteroi(); aku2.rekisteroi();
     *
     * pelaajat.lisaa(aku1); 
     * pelaajat.lisaa(aku2); 
     * pelaajat.lisaa(aku1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Pelaaja pelaaja:pelaajat)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+pelaaja.getpelaajaNro());           
     * 
     * String tulos = " " + aku1.getpelaajaNro() + " " + aku2.getpelaajaNro() + " " + aku1.getpelaajaNro();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Pelaaja>  i=pelaajat.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Pelaaja pelaaja = i.next();
     *   ids.append(" "+pelaaja.getpelaajaNro());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Pelaaja>  i=pelaajat.iterator();
     * i.next() == aku1  === true;
     * i.next() == aku2  === true;
     * i.next() == aku1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class PelaajatIterator implements Iterator<Pelaaja> {
        private int kohdalla = 0;


        /**
         * Onko olemassa viel� seuraavaa pelaajaa
         * @see java.util.Iterator#hasNext()
         * @return true jos on viel� j�seni�
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava pelaaja
         * @return seuraava pelaaja
         * @throws NoSuchElementException jos seuraava alkiota ei en�� ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Pelaaja next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }
    
    
    /**
     * Palautetaan iteraattori jäsenistään.
     * @return jäsen iteraattori
     */
    @Override
    public Iterator<Pelaaja> iterator() {
        return new PelaajatIterator();
    }


    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien pelaajien viitteet 
     * @param ehto mitä etsitään
     * @param k kentän indeksi jonka mukaan etsitään
     * @return löytyneet
     */ 
    public Collection<Pelaaja> etsi(String ehto, int k) { 
        ArrayList<Pelaaja> loytyneet = new ArrayList<Pelaaja>(); 
        int hk = k;
        if (hk < 0) hk = 1;
        for (int i = 0; i < getLkm(); i++) { 
            Pelaaja pelaaja = anna(i);
            String sisalto = pelaaja.anna(hk);
            if (WildChars.onkoSamat(sisalto, ehto))
                loytyneet.add(pelaaja);
        } 
        Collections.sort(loytyneet, new Pelaaja.Vertailija(k));
        return loytyneet; 
    }
       
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Pelaajat pelaajat = new Pelaajat();
        
        Pelaaja p1 = new Pelaaja();
        Pelaaja p2 = new Pelaaja();

        p1.rekisteroi();
        p1.rekisteroiOsake();
        p1.vastaaAkuAnkka();
        
        p2.rekisteroi();
        p2.rekisteroiOsake();
        p2.vastaaAkuAnkka();
        
        pelaajat.lisaa(p1);
        pelaajat.lisaa(p2);
        
        System.out.println("============= Pelaajat testi =================");
                
        for (int i = 0; i < pelaajat.getLkm(); i++) {
            Pelaaja pelaaja = pelaajat.anna(i);
            System.out.println("Jäsen nro: " + i);
            pelaaja.tulosta(System.out);
        }
        
    }
    
}
