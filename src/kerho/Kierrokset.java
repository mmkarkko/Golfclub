/**
 * 
 */
package kerho;

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
 * @version 21.2.2023
 *
 */
public class Kierrokset {

    private static final int MAX_KIERROKSIA = 20;
    
    private int lkm = 0;
    private Kierros[] alkiot;


    /**
     * Palauttaa viitteen i:teen kierrokseen
     * @param i monennenko kierroksen viite halutaan
     * @return viite kierrokseen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Kierros anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || this.lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    /**
     * Palauttaa kerhon kierrosten lukumäärän
     * @return kierrosten lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Luodaan alustava taulukko
     */
    public Kierrokset() {
        alkiot = new Kierros[MAX_KIERROKSIA];
    }
    
    
    /**
     * Lisää uuden kierroksen tietorakenteeseen.  Ottaa kierroksen omistukseensa.
     * @param kierros lisättävän kierroksen viite. Huom. tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Kierrokset kierrokset = new Kierrokset();
     * Kierros ki1 = new Kierros(), ki2 = new Kierros();
     * kierrokset.getLkm() === 0;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 1;
     * kierrokset.lisaa(ki2); kierrokset.getLkm() === 2;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 3;
     * kierrokset.anna(0) === ki1;
     * kierrokset.anna(1) === ki2;
     * kierrokset.anna(2) === ki1;
     * kierrokset.anna(1) == ki1 === false;
     * kierrokset.anna(1) == ki2 === true;
     * kierrokset.anna(3) === ki1; #THROWS IndexOutOfBoundsException 
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 4;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 5;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 6;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 7;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 8;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 9;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 10;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 11;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 12;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 13;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 14;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 15;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 16;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 17;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 18;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 19;
     * kierrokset.lisaa(ki1); kierrokset.getLkm() === 20;
     * kierrokset.lisaa(ki1);  #THROWS SailoException
     * </pre>
     */
    public void lisaa(Kierros kierros) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        alkiot[lkm] = kierros;
        lkm++;
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
        k1.vastaaKierros();
        
        k2.rekisteroi();
        k2.vastaaKierros();
        
        k3.rekisteroi();
        k3.vastaaKierros();
        
        try {
            kierrokset.lisaa(k1);
            kierrokset.lisaa(k2);
            kierrokset.lisaa(k3);

        } catch (SailoException e) {
            System.err.println(e.getMessage());
        }   
        
        System.out.println("============= Jäsenet testi =================");
        
        
        for (int i = 0; i < kierrokset.getLkm(); i++) {
            Kierros kierros = kierrokset.anna(i);
            System.out.println("Jäsen indeksi: " + i);
            kierros.tulosta(System.out);
        } 
    }

}
