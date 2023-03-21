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
     *  tarkistaNimi("Bridges Sam")          === null;
     *  tarkistaNimi("BridgesSam")           === "Syötä sekä suku-, että etunimi";
     *  tarkistaNimi("bridges sam")          === null;
     *  tarkistaNimi("aa pp")                === "Virheellinen nimi";
     *  tarkistaNimi("Huuppa   Alli")        === "Poista ylimääräiset välilyönnit";
     *  tarkistaNimi("Mäki-Aho Pölhö-Kustaa")=== null;
     * </pre>
     */
    public static String tarkistaNimi(String nimi) {
        if (nimi == null) return "Virheellinen nimi";
        if (nimi.length() < 7) return "Virheellinen nimi";
        
        StringBuilder sukunimi = new StringBuilder();
        StringBuilder etunimi  = new StringBuilder();
        StringBuilder kokonimi = new StringBuilder();
        
        char sallittuMerkki  = '-';
        
        int valit = 0;
        for (int i = 0; i < nimi.length(); i++) {
            if (nimi.charAt(i) == ' ') valit++;
            if (valit <  1) sukunimi.append(nimi.charAt(i));
            if (valit == 1) { 
                if (nimi.charAt(i) == ' ') continue;
                etunimi.append(nimi.charAt(i));
            }
           
        }
        
        kokonimi.append(sukunimi);
        kokonimi.append(etunimi);

        if (valit > 1) return "Poista ylimääräiset välilyönnit";
        if (valit < 1) return "Syötä sekä suku-, että etunimi";
        for (int i = 0; i < kokonimi.length(); i++) {
            if (!Character.isAlphabetic(kokonimi.charAt(i)) && (kokonimi.charAt(i) != sallittuMerkki) ) return "Virheellinen nimi";
        }
        return null;
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        //
   
    }

}
