/**
 * 
 */
package kerho;

/**
 * Kerho-luokka
 * - hoitaa kaiken näyttöön tulevan tekstin 
 * - hoitaa kaiken tiedon pyytämisen käyttäjältä
 *   (ei tiedä kerhon eikä pelaajan yksityiskohtia)
 * Avustajaluokat:  Kerho
 *                  Kierros
 *                  Pelaaja
 *                               
 * @author Miia Arkko
 * @version 21.2.2023
 *
 */
public class Kerho {

    private Pelaajat pelaajat = new Pelaajat();
    private Kierrokset kierrokset = new Kierrokset();
    
    
    /**
     * Kertoo i:nnen kierroksen
     * @param i kierroksen indeksi
     * @return palauttaa i:nnen kierroksen
     */
    public Kierros annaKierros(int i) {
        return kierrokset.anna(i);
    }
    
    
    /**
     * Tarkistaa kierrosten lukumääräm
     * @return palauttaa kierrosten lukumäärän
     */
    public int getKierroksia() {
        return kierrokset.getLkm();
    }
    
    
    /**
     * Lisätään uusi kierros
     * @param kierros lisättävä kierros
     * @throws SailoException jos lisääminen ei onnistu
     */
    public void lisaaKierros(Kierros kierros) throws SailoException {
        kierrokset.lisaa(kierros);
    }
    
    
    /**
     * Kertoo i:nnen pelaajan
     * @param i pelaajan indeksi
     * @return palauttaa i:nnen pelaajan
     */
    public Pelaaja annaPelaaja(int i) {
        return pelaajat.anna(i);
    }
    
 
    /**
     * Tarkistaa pelaajien lukumäärän
     * @return palauttaa pelaajien lukumäärän
     */
    public int getPelaajia() {
        return pelaajat.getLkm();
    }
    
    
    /**
     * Lisätään uusi pelaaja
     * @param pelaaja lisättävä pelaaja
     * @throws SailoException jos lisääminen ei onnistu
     * @example
     * <pre name="test">
     * 
     * </pre>
     */
    public void lisaaPelaaja(Pelaaja pelaaja) throws SailoException {
        pelaajat.lisaa(pelaaja);
    }
    
    
    /**
     * @param args ei käytössä
     */ 
    public static void main(String[] args) {
        Kerho kerho = new Kerho();
        
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
            kerho.lisaaPelaaja(p1);
            kerho.lisaaPelaaja(p2);
            kerho.lisaaPelaaja(p3);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        for (int i = 0; i < kerho.getPelaajia(); i++) {
            Pelaaja pelaaja = kerho.annaPelaaja(i);
            pelaaja.tulosta(System.out);
        }
        
        
        // ========================================
        
        
        // TODO: lisää kierrokselle samat, kuin pelaajalle
        Kierros k1 = new Kierros();
        Kierros k2 = new Kierros();
        Kierros k3 = new Kierros();
        
        k1.rekisteroi();
        k1.vastaaKierros();
        
        
        try {
            kerho.lisaaKierros(k1);
            kerho.lisaaKierros(k2);
            kerho.lisaaKierros(k3);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        for (int i = 0; i < kerho.getKierroksia(); i++) {
            Kierros kierros = kerho.annaKierros(i);
            kierros.tulosta(System.out);
        }
    }
    
}
