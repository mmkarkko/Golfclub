/**
 * 
 */
package tiedosto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Lukujen lukeminen tiedostosta Scanner-luokkaa käyttäen
 * @author miiaa Miia Arkko
 * @version 28.2.2023
 *
 */
public class TiedKaScanner {
    
    /**
     * 
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Scanner fi;
        
        try {
            fi = new Scanner(new FileInputStream(new File("luvut.dat")));
        } catch (FileNotFoundException ex) {
           System.err.println("Tiedosto ei aukea! " + ex.getMessage()); 
           return;
        }
        double summa = 0;
        int n = 0;
        

        try {
            while(fi.hasNext()) {
                String s;
                double luku;
                s = fi.next();
                try {
                    luku = Double.parseDouble(s);
                } catch (NumberFormatException ex) {
                    continue;
                }
                summa += luku;
                n++;
            }
        
        } finally {
                fi.close(); 
        }

        
        double ka = 0;
        if ( n > 0 ) ka = summa/n;
        System.out.println("Lukuja oli " + n + " kappaletta.");
        System.out.println("Niiden summa oli " + Mjonot.fmt(summa,4,2));
        System.out.println("ja keskiarvo oli " + Mjonot.fmt(ka,4,2));

        
        
        
    }
}
