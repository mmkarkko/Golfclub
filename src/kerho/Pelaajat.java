package kerho;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Pelaajat-luokka
 * - Pitää yllä varsinaista pelaajarekisteriä, eli
 *   osaa lisätä tai poistaa jäsenen
 * - Lukee ja kirjoittaa pelaajien tiedostoon
 * - Osaa etsiä ja lajitella pelaajia
 * - Avustajaluokat: pelaaja
 * 
 * @author Miia Arkko
 * @version 20.3.2023
 *
 */
public class Pelaajat {
    
    private static final int MAX_PELAAJIA = 10;
    
    private int lkm = 0;
    private Pelaaja[] alkiot = new Pelaaja[MAX_PELAAJIA];
    private boolean muutettu = false;
    
    
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
        if (i < 0 || this.lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }

    
    /**
     * Palauttaa kerhon pelaajien lukumäärän
     * @return pelaajien lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Tallentaa pelaajia tiedostoon
     * Tiedoston muoto:
     * <pre>
     * 1|Pelaaja Petteri|070819-5398|5,4|000-9999999|petepelaaja@golffari.fi|Pelimiehenkuja 1|11111 Pelilä|1|OK|1|
     * 2|Teetime Teemu|190895-943M |19,3 |111-2221111|tsteetime@golffari.fi|Tiikuja 1|11001 Tiiala||OK|1|
     * </pre>
     * @param hakemisto tallennettavan tiedoston hakemisto
     * @throws SailoException mikäli tallennus epäonnistuu
     */
    public void tallenna(String hakemisto) throws SailoException {
        if (!muutettu) return;
        
        File ftied = new File(hakemisto + "/pelaajat.dat");      
        try (PrintStream fo = new PrintStream(new FileOutputStream(ftied, false))) {
            for(int i = 0; i < getLkm(); i++) {
                Pelaaja pelaaja = anna(i);
                fo.println(pelaaja.toString());
            }               
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getAbsolutePath() +  " ei aukea");
        }
    }
    
    
    /**
     * Luetaan pelaajien tiedostosta (kesken)
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen ei onnistu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        String nimi = hakemisto + "/pelaajat.dat";
        File ftied = new File(nimi);
        
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) { // jottaUTF8/ISO-8859 toimii
            while (fi.hasNext()) {
                String s = fi.nextLine();
                if ( s==null || "".equals(s) || s.charAt(0) == ';') continue;
                Pelaaja pelaaja = new Pelaaja();
                pelaaja.parse(s);
                lisaa(pelaaja);
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Ei pystytä lukemaan tiedostoa " + nimi);
        }
        muutettu = false;
    }
    
    
    /**
     * Luokka j�senten iteroimiseksi.
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
         * Onko olemassa viel� seuraavaa j�sent�
         * @see java.util.Iterator#hasNext()
         * @return true jos on viel� j�seni�
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava j�sen
         * @return seuraava j�sen
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
     * @param args ei käytössä
     * @throws SailoException jos liikaa
     */
    public static void main(String[] args) throws SailoException {
        Pelaajat pelaajat = new Pelaajat();
        
        try {
            pelaajat.lueTiedostosta("pelaajat");
        } catch (SailoException ex) {
            System.err.println(ex.getMessage());
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
            pelaajat.lisaa(p1);
            pelaajat.lisaa(p2);
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }
        
        System.out.println("============= Jäsenet testi =================");
                
        for (int i = 0; i < pelaajat.getLkm(); i++) {
            Pelaaja p = pelaajat.anna(i);
            System.out.println("Pelaaja indeksi: " + i);
            p.tulosta(System.out);
        } 
        
        try {
            pelaajat.tallenna("pelaajat");
        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
}
