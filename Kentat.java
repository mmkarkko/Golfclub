package kerho;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.PrintWriter;
import java.io.File;


/**
 * 
 * @author Miia Arkko
 * @version 14.4.2023
 *
 */
public class Kentat implements Iterable<Kentta> {
    
    private static final int MAX_KENTTIA = 5;
    private int lkm = 0;
    private String kokonimi = "";
    private String tiedostonPerusNimi = "";
    private Kentta[] alkiot = new Kentta[MAX_KENTTIA];
    private boolean muutettu = false;
    
    /**
     * Oletusmuodostaja
     */
    public Kentat() {
        
    }
    
    
    /**
     * Palauttaa kenttien lukumäärän
     * @return kenttien lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    

    
    
    /**
     * Palauttaa kerhon koko nimen
     * @return kerhon nimi merkkijonona
     */
    public String getKokoNimi() {
        return kokonimi;
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
        tiedostonPerusNimi = nimi + "/kentat";
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
     * Palauttaa viitteen i:nteen kenttään
     * @param i monennenko kentän viite halutaan
     * @return viite kenttään, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Kentta anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || this.lkm <= i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
    /**
     * Lisää uuden kentän tietorakenteeseen. Ottaa kentän omistukseensa
     * @param kentta lisättävä kenttä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Kentat kentat = new Kentat();
     * Kentta aku1 = new Kentta(), aku2 = new Kentta();
     * kentat.getLkm() === 0;
     * kentat.lisaa(aku1); kentat.getLkm() === 1;
     * kentat.lisaa(aku2); kentat.getLkm() === 2;
     * kentat.lisaa(aku1); kentat.getLkm() === 3;
     * kentat.anna(0) === aku1;
     * kentat.anna(1) === aku2;
     * kentat.anna(2) === aku1;
     * kentat.anna(1) == aku1 === false;
     * kentat.anna(1) == aku2 === true;
     * kentat.anna(3) === aku1; #THROWS IndexOutOfBoundsException 
     * kentat.lisaa(aku1); kentat.getLkm() === 4;
     * kentat.lisaa(aku1); kentat.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Kentta kentta) {
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+5);
        alkiot[lkm] = kentta;
        lkm++;
        muutettu = true;
    }
    
    
    /**
     * 
     * @param kentta joka lisätään
     * @throws SailoException jos ei onnistu
     * 
     * <pre name="test">
     *  #import java.util.Iterator;
     *  #THROWS SailoException,CloneNotSupportedException
     *  #PACKAGEIMPORT
     *  Kentat kentat = new Kentat();
     *  Kentta har1 = new Kentta(), har2 = new Kentta();
     *  har1.rekisteroiKentta(); har2.rekisteroiKentta();
     *  kentat.getLkm() === 0;
     *  kentat.korvaaTaiLisaa(har1); kentat.getLkm() === 1;
     *  kentat.korvaaTaiLisaa(har2); kentat.getLkm() === 2;
     *  Kentta har3 = har1.clone();
     *  har3.aseta(2,"kkk");
     *  Iterator<Kentta> i2=kentat.iterator();
     *  i2.next() === har1;
     *  kentat.korvaaTaiLisaa(har3); kentat.getLkm() === 2;
     *  i2=kentat.iterator();
     *  Kentta h = i2.next();
     *  h === har3;
     *  h == har3 === true;
     *  h == har1 === false;
     */
    public void korvaaTaiLisaa(Kentta kentta) throws SailoException {
        int id = kentta.getKerhoId();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getKerhoId() == id) {
                alkiot[i] = kentta;
                muutettu  = true;
                return;
            }
        }
        lisaa(kentta);
    }
    
    
    /**
     * Poistaa kentan, jolla on valittu tunnusnumero
     * @param id poistettavan kentän kenttäID
     * @return 1 jos jotakin poistettiin, 0 jos ei löydy
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
            if (id == alkiot[i].getKerhoId()) return i;
        return -1;
    }
    
    
    /**
     * Luetaan kenttien tiedostosta (kesken)
     * @param tied tiedoston perusnimi
     * @throws SailoException jos ei onnistu
     * 
     *      * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Kentat kentat = new Kentat();
     *  Kentta aku1 = new Kentta(), aku2 = new Kentta();
     *  aku1.vastaaAkuAnkka();
     *  aku2.vastaaAkuAnkka();
     *  String hakemisto = "koekerho";
     *  String tiedNimi = hakemisto+"/kentat";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  kentat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  kentat.lisaa(aku1);
     *  kentat.lisaa(aku2);
     *  kentat.tallenna();
     *  kentat = new Kentat();            // Poistetaan vanhat luomalla uusi
     *  kentat.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Kentta> i = kentat.iterator();
     *  i.next() === aku1;
     *  i.next() === aku2;
     *  i.hasNext() === false;
     *  kentat.lisaa(aku2);
     *  kentat.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre> 
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            kokonimi = fi.readLine();
            if ( kokonimi == null ) throw new SailoException("Kerhon nimi puuttuu");
            String rivi = fi.readLine();
            if ( rivi == null ) throw new SailoException("Maksimikoko puuttuu");
            // int maxKoko = Mjonot.erotaInt(rivi,10); // tehdään jotakin

            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Kentta kentta = new Kentta();
                kentta.parse(rivi); // voisi olla virhekäsittely
                lisaa(kentta);
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
     * Tallentaa kenttiä tiedostoon
     * @throws SailoException jos ei onnistu
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
            for (Kentta kentta : this) {
                fo.println(kentta.toString());
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


    @Override
    public Iterator<Kentta> iterator() {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    /**
     * @author Miia Arkko
     * @version 14.4.2023
     *
     */
    public class KentatIterator implements Iterator<Kentta> {
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
        public Kentta next() throws NoSuchElementException {
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
     */
    public static void main(String[] args) {
        Kentat kentat = new Kentat();
        
        Kentta ke1 = new Kentta();
        Kentta ke2 = new Kentta();
        Kentta ke3 = new Kentta();
        Kentta ke4 = new Kentta();
        Kentta ke5 = new Kentta();
        Kentta ke6 = new Kentta();
        
        
        ke1.rekisteroiKentta();
        ke2.rekisteroiKentta();
        ke3.rekisteroiKentta();
        ke4.rekisteroiKentta();
        ke5.rekisteroiKentta();
        ke6.rekisteroiKentta();
        
        ke1.vastaaAkuAnkka();
        ke2.vastaaAkuAnkka();
        ke3.vastaaAkuAnkka();
        ke4.vastaaAkuAnkka();
        ke5.vastaaAkuAnkka();
        ke6.vastaaAkuAnkka();
        
        kentat.lisaa(ke1);
        kentat.lisaa(ke2);
        kentat.lisaa(ke3);
        kentat.lisaa(ke4);
        kentat.lisaa(ke5);
        kentat.lisaa(ke6);
        
        System.out.println("============= Pelaajat testi =================");
        
        for (int i = 0; i < kentat.getLkm(); i++) {
            Kentta kentta = kentat.anna(i);
            System.out.println("Kerhon nro: " + i);
            kentta.tulosta(System.out);
        }
    }
}

