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
import java.util.Iterator;
import java.util.NoSuchElementException;

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
     * Luodaan alustava taulukko
     */
    public Pelaajat() {
        
    }
    
    
    /**
     * Lisää uuden jäsenen tietorakenteeseen.  Ottaa jäsenen omistukseensa.
     * @param pelaaja lisätäävän jäsenen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
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
    public void lisaa(Pelaaja pelaaja) throws SailoException {         
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+20); 
        alkiot[lkm] = pelaaja;
        lkm++;
        muutettu = true;

    }
    
    
    /**
     * 
     * @param pelaaja ...
     * TODO: täytä
     * @throws SailoException jos ei onnistu
     * <pre name="test">
     *  #import java.util.Iterator;
     *  #THROWS SailoException,CloneNotSupportedException
     *  #PACKAGEIMPORT
     *  Pelaajat pelaajat = new Pelaajat();
     *  Pelaaja aku1 = new Pelaaja(), aku2 = new Pelaaja();
     *  aku1.rekisteroi(); aku2.rekisteroi();
     *  pelaajat.getLkm() === 0;
     *  pelaajat.korvaaTaiLisaa(aku1); pelaajat.getLkm() === 1;
     *  pelaajat.korvaaTaiLisaa(aku2); pelaajat.getLkm() === 2;
     *  Pelaaja aku3 = aku1.clone();
     *  aku3.setPostios("00130 Kylä");
     *  Iterator<Pelaaja> it = pelaajat.iterator();
     *  it.next() == aku1 === true;
     *  pelaajat.korvaaTaiLisaa(aku3); pelaajat.getLkm() === 2;
     *  it = pelaajat.iterator();
     *  Pelaaja j0 = it.next();
     *  j0 === aku3;
     *  j0 == aku3 === true;
     *  j0 == aku1 === false;
     * </pre>
     */
    public void korvaaTaiLisaa(Pelaaja pelaaja) throws SailoException {
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
     *  String hakemisto = "testikelmit";
     *  String tiedNimi = hakemisto+"/nimet";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
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
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä pelaajista 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Pelaajat pelaajat = new Pelaajat(); 
     *   Pelaaja p1 = new Pelaaja(); p1.parse("1|Ankka Aku|030201-115H|Paratiisitie 13|"); 
     *   Pelaaja p2 = new Pelaaja(); p2.parse("2|Ankka Tupu||030552-123B|"); 
     *   Pelaaja p3 = new Pelaaja(); p3.parse("3|Susi Sepe|121237-121V||131313|Perämetsä"); 
     *   Pelaaja p4 = new Pelaaja(); p4.parse("4|Ankka Iines|030245-115V|Ankkakuja 9"); 
     *   Pelaaja p5 = new Pelaaja(); p5.parse("5|Ankka Roope|091007-408U|Ankkakuja 12"); 
     *   pelaajat.lisaa(p1); pelaajat.lisaa(p2); pelaajat.lisaa(p3); pelaajat.lisaa(p4); pelaajat.lisaa(p5);
     *   // TODO: toistaiseksi palauttaa kaikki pelaajat 
     * </pre> 
     */ 
    @SuppressWarnings("unused")
    public Collection<Pelaaja> etsi(String hakuehto, int k) { 
        Collection<Pelaaja> loytyneet = new ArrayList<Pelaaja>(); 
        for (Pelaaja p : this) { 
            loytyneet.add(p);  
        } 
        return loytyneet; 
    }
       
    
    /**
     * @param args ei käytössä
     * @throws SailoException jos liikaa
     */
    public static void main(String[] args) throws SailoException {
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
