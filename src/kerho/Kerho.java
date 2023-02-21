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
    
        kerho.lisaa(p1);
        kerho.lisaa(p2);
        kerho.lisaa(p3);
        
        for (int i = 0; i < kerho.getPelaajia(); i++) {
            Pelaaja pelaaja = kerho.annaPelaaja(i);
            pelaaja.tulosta(System.out);
    }

}
