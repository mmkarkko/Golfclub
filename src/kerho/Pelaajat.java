package kerho;

/**
 * - Pitää yllä varsinaista pelaajarekisteriä, eli
 *   osaa lisätä tai poistaa jäsenen
 * - Lukee ja kirjoittaa pelaajien tiedostoon
 * - Osaa etsiä ja lajitella pelaajia
 * - Avustajaluokat: pelaaja
 * 
 * @author Miia Arkko
 * @version 18.2.2023
 *
 */
public class Pelaajat {
    
    private static final int MAX_PELAAJIA = 10;
    
    private int lkm = 0;
    private Pelaaja[] alkiot;
    
    
    /**
     * Luodaan alustava taulukko
     */
    public Pelaajat() {
        alkiot = new Pelaaja[MAX_PELAAJIA];
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
     * pelaajat.lisaa(aku1);  #THROWS SailoException;
     * </pre>
     * TODO: poikkeuksen tilalle pitäisi kasvattaa taulukon kokoa, jotta mahdutaan lisäämään uusi jäsen
     */
    public void lisaa(Pelaaja pelaaja) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = pelaaja;
        lkm++;
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
     * 
     * @param args ei käytössä
     * @throws SailoException jos liikaa
     * 
     */
    public static void main(String[] args) throws SailoException {
        Pelaajat pelaajat = new Pelaajat();
        
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
            pelaajat.lisaa(p1);
            pelaajat.lisaa(p2);
            pelaajat.lisaa(p3);

        } catch (SailoException e) {
            System.err.println(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }
        
        System.out.println("============= Jäsenet testi =================");
        
        
        for (int i = 0; i < pelaajat.getLkm(); i++) {
            Pelaaja pelaaja = pelaajat.anna(i);
            System.out.println("Jäsen indeksi: " + i);
            pelaaja.tulosta(System.out);
        } 

        

    }
    
}
