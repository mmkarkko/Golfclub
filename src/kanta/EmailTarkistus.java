/**
 * 
 */
package kanta;

/**
 * Luokka sähköpostiosoitteen oikeellisuuden tarkistamiseksi
 * @author Miia Arkko
 * @version 20.3.2023
 *
 */
public class EmailTarkistus {
    
    /**
     * Tarkistaa sähköpostin oikeellisuuden
     * @param email joka tarkistetaan
     * @return palauttaa virheilmoituksen. Null, jos virhettä ei löydy
     * @example
     * <pre name="test">
     *  tarkistaOsoite("osoite@osoite.fi")  === null;
     *  tarkistaOsoite("pspote&osoite.fi")  === "Osoitteen täytyy sisältää @-merkki";
     *  tarkistaOsoite("@gmail.com")        === "Virheellinen sähköpostiosoite";
     *  tarkistaOsoite("m@j.fi")            === "Liian lyhyt sähköpostiosoite";
     *  tarkistaOsoite("uusi_osoite@")      === "Virheellinen sähköpostiosoite";
     *  tarkistaOsoite("1ossa_@osoite.")    === "Virheellinen sähköpostiosoite";
     *  tarkistaOsoite("12_jee@jee.com")    === null;
     *  tarkistaOsoite("oso@ite@jee.fi")    === "Virheellinen sähköpostiosoite";
     *  tarkistaOsoite("oho@23.fi")         === "Virheellinen sähköpostiosoite";
     *  tarkistaOsoite("")                  === "Liian lyhyt sähköpostiosoite";
     *  tarkistaOsoite("o.fa_jo34@gmail.di")=== null
     *  tarkistaOsoite("osoite@_gmail.di")  === "Virheellinen sähköpostiosoite";
     *  tarkistaOsoite("oma osoite@ossa.fi")=== "Virheellinen sähköpostiosoite";
     *  tarkistaOsoite("etu_suku@osote .fi")=== "Virheellinen sähköpostiosoite";
     *  tarkistaOsoite("2095@sapo.fi")      === null;
     *  tarkistaOsoite("oho!o@sapo.fi")     === "Virheellinen sähköpostiosoite";
     * </pre>
     */
    public String tarkistaOsoite(String email) {
        if (email == null) return "Virheellinen sähköpostiosoite";
        
        String osoite = email.toLowerCase();
        
        String alkuosa  = "";
        String loppuosa = "";
        
        char miuku = '@';
        int pituus = osoite.length();
        int laskuri = 0;
        int loppuosanPituus = -1;
        int indeksi;

        if (pituus < 8) return "Liian lyhyt sähköpostiosoite";
        if (osoite.charAt(0) == miuku) return "Virheellinen sähköpostiosoite";
        
        for (indeksi =0; indeksi < osoite.length(); indeksi++) {
            if (osoite.charAt(indeksi) == ' ') return "Virheellinen sähköpostiosoite";
            if (osoite.charAt(indeksi) == miuku) {
                laskuri++;
            }
            if (laskuri < 1) alkuosa += osoite.charAt(indeksi);
            if (laskuri >= 1) {
                loppuosanPituus++;
                if (osoite.charAt(indeksi) != miuku) loppuosa += osoite.charAt(indeksi);
                
            }
        }
        
        if (laskuri  < 1)  return  "Osoitteen täytyy sisältää @-merkki";
        if (laskuri  > 1)  return "Virheellinen sähköpostiosoite";
        if (loppuosanPituus <= 4) return "Virheellinen sähköpostiosoite";

        laskuri = 0;
        int pisteenPaikka = 0;
        for (int i = 0; i < loppuosa.length(); i++) {
            if (loppuosa.charAt(i) == '.') {
                pisteenPaikka = i;
                laskuri++;
            }
        }
        
        if ( laskuri > 1 ) return "Virheellinen sähköpostiosoite";
        if (pisteenPaikka < 3) return "Virheellinen sähköpostiosoite"; 
        if (loppuosa.length() - pisteenPaikka < 2 ) return "Virheellinen sähköpostiosoite";

        for (int i = 0; i < loppuosa.length(); i++) {
            if (i == pisteenPaikka) break;
            if (!Character.isAlphabetic(loppuosa.charAt(i))) return "Virheellinen sähköpostiosoite";
        }
        
        String merkit = "._-";
        for (int i = 0; i < alkuosa.length(); i++) {
            char merkki = alkuosa.charAt(i);
            //if ( (!(merkki >= 'a') && !(merkki <= 'z')) || !((merkki >= '0') && !(merkki <= '9')) || !(merkit.contains(Character.toString(merkki)))) {
            if (!Character.isAlphabetic(alkuosa.charAt(i)) && (!Character.isDigit(alkuosa.charAt(i)))&& !(merkit.contains(Character.toString(merkki))))
                return "Virheellinen sähköpostiosoite";
            }       
        return null;
    }

    
    /**
     * Pääohjelma sähköpostiosoitteen validiuden testaamiseksi
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        //
    }

}
