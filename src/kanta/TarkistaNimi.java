/**
 * 
 */
package kanta;

/**
 * Tarkistaa nimen oikeellisuuden
 * @author Miia Arkko
 * @version 21.3.2023
 *
 */
public class TarkistaNimi {


    /**
     * Tarkistaa nimen kirjoitusmuodon oikeellisuuden
     * @param nimi joka tarkistetaan
     * @return palauttaa null, jos nimi on validi. Muuten virheilmoitus
     * @example
     * <pre name="test">
     *  tarkistaNimi("Bridges Sam")         === null;
     *  tarkistaNimi("BridgesSam")          === "Syötä sekä suku-, että etunimi";
     *  tarkistaNumi("bridges sam")         === null;
     * </pre>
     */
    public static String tarkistaNimi(String nimi) {
        if (nimi == null) return "Virheellinen nimi";
        if (nimi.length() < 7) return "Virheellinen nimi";
        
        StringBuilder sukunimi = new StringBuilder();
        StringBuilder etunimi  = new StringBuilder();
        
        for (int i = 0; i < nimi.length() -1 ; i++) {
            do sukunimi.append(nimi.charAt(i));
            while (nimi.charAt(i)!= ' ');
        }
        System.out.println(etunimi);
        System.out.println(sukunimi);
        return null;
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        String nimi = "Sam Porter Bridges";
        
        String virhe = tarkistaNimi(nimi);
        
        System.out.println(nimi);
        if (virhe == null) System.out.println("Nimi " + nimi +  " on kirjoitettu oikein");
        else System.out.println("Nimi " + nimi + " oli virheellinen. Virhe oli: " + virhe);
    
    }

}
