/**
 * 
 */
package kanta;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Luokka sähköpostiosoitteen oikeellisuuden tarkistamiseksi
 * @author Miia Arkko
 * @version 20.3.2023
 *
 */
public class EmailTarkistus {
    
    //private static final String TARKISTUSMERKIT = "";
    
    
    /**
     * Tarkistaa sähköpostin oikeellisuuden
     * @param osoite joka tarkistetaan
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
     * </pre>
     */
    public static String tarkistaOsoite(String osoite) {
        
       // String alkuosa  = "";
        String loppuosa = "";
        
        char miuku = '@';
        int pituus = osoite.length();
        int laskuri = 0;
        int loppuosanPituus = -1;
        //int alkuosanPituus = 0;
        int indeksi;
        //int miukuPaikka = 0;

        if (pituus < 8) return "Liian lyhyt sähköpostiosoite";
        if (osoite.charAt(0) == miuku) return "Virheellinen sähköpostiosoite";
        
        for (indeksi =0; indeksi < osoite.length(); indeksi++) {
            
            if (osoite.charAt(indeksi) == miuku) {

                //miukuPaikka = indeksi;
                laskuri++;
            }
            //if (laskuri < 1) alkuosa += osoite.charAt(indeksi);
            if (laskuri >= 1) {
                loppuosanPituus++;
                loppuosa += osoite.charAt(indeksi);
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
        
//        laskuri = 0;
//        int pisteenPaikka = miukuPaikka;
//        for (int i = miukuPaikka; i < osoite.length(); i++) {
//            if (osoite.charAt(i) == '.') {
//                pisteenPaikka++;
//                laskuri++;
//            }
//            
//        }
//        
//        if (pisteenPaikka <=  miukuPaikka + 2) return "Virheellinen sähköpostiosoite";
//        if (pisteenPaikka <= osoite.length()-2) return "Virheellinen sähköpostiosoite";
//        if (laskuri > 1) return "Virheellinen sähköpostiosoite";
        
        if (loppuosa.length() - pisteenPaikka < 2 ) return "Virheellinen sähköpostiosoite";
        
        //System.out.println(alkuosa); 
        
        return null;
    }

    
    
    
    
    
    
    
    /**
     * Pääohjelma sähköpostiosoitteen validiuden testaamiseksi
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        String sapo  = "osoite@sapo.fi";
        String sapo2 = "osoite%sapo.fi";
        String sapo3 = "@gmail.com";
        String sapo4  = "uusi_osoite@";
        String sapo5 = "1ossa_@osoite.";
        String virhe;
        
        virhe = tarkistaOsoite(sapo); 
        System.out.println("Osoite oli: " + sapo + ", virhe oli: " + virhe);
        
        virhe = tarkistaOsoite(sapo2);
        System.out.println("Osoite oli: " + sapo2 + ", virhe oli: " + virhe);
        
        virhe = tarkistaOsoite(sapo3);
        System.out.println("Osoite oli: " + sapo3 + ", virhe oli: " + virhe);
        
        virhe = tarkistaOsoite(sapo4);
        System.out.println("Osoite oli: " + sapo4 + ", virhe oli: " + virhe);
        
        
        virhe = tarkistaOsoite(sapo5);
        System.out.println("Osoite oli: " + sapo5 + ", virhe oli: " + virhe);
        
    }

}
