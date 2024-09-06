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
     *  tarkistaNimi("BridgesSam")           === "Virheellinen nimi";
     *  tarkistaNimi("bridges sam")          === null;
     *  tarkistaNimi("aa pp")                === "Virheellinen nimi";
     *  tarkistaNimi("Huuppa   Alli")        === "Poista ylimääräiset välilyönnit";
     *  tarkistaNimi("Mäki-Aho Pölhö-Kustaa")=== null;
     *  tarkistaNimi("Mällinen ")            === "Virheellinen etunimi";
     *  tarkistaNimi("Lörppö Li")            === null
     *  tarkistaNimi("Aho T")                === "Virheellinen etunimi";
     *  tarkistaNimi("N Torspo")             === "Virheellinen sukunimi";
     * </pre>
     */
    public String tarkistaNimi(String nimi) {
        if (nimi == null) return "Virheellinen nimi";
        if (nimi.length() == 0) return "Nimikenttä ei voi olla tyhjä";
        if (nimi.length() < 5) return "Virheellinen nimi";
        
        StringBuilder sukunimi = new StringBuilder();
        StringBuilder etunimi  = new StringBuilder();
        StringBuilder kokonimi = new StringBuilder();
        
        char sallittuMerkki  = '-';
        
        int valit = 0;
        for (int i = 0; i < nimi.length(); i++) {
            if (nimi.charAt(i) == ' ') valit++;
            if (valit <  1) sukunimi.append(nimi.charAt(i));
            if (valit >= 1) { 
                if (nimi.charAt(i) == ' ') continue;
                etunimi.append(nimi.charAt(i));
            }
           
        }
        if (valit == 0) return "Virheellinen nimi";
        
        kokonimi.append(sukunimi);
        kokonimi.append(etunimi);
        
        if (sukunimi.length() < 2 && etunimi.length() > 2) return "Virheellinen sukunimi";
        if (etunimi.length()  < 2 && sukunimi.length()> 2) return "Virheellinen etunimi";
        if (kokonimi.length() < 5) return "Virheellinen nimi";
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
    public void main(String[] args) {
        //
    }

}
