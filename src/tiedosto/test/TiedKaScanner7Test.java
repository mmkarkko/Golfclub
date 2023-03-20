package tiedosto.test;
// Generated by ComTest BEGIN
import java.io.IOException;
import fi.jyu.mit.ohj2.Suuntaaja;
import fi.jyu.mit.ohj2.VertaaTiedosto;
import static org.junit.Assert.*;
import org.junit.*;
import static tiedosto.TiedKaScanner7.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2023.03.15 17:39:05 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class TiedKaScanner7Test {


  // Generated by ComTest BEGIN
  /** 
   * testMain21 
   * @throws IOException when error
   */
  @Test
  public void testMain21() throws IOException {    // TiedKaScanner7: 21
    String tiednimi = "tiedka7koe.txt"; 
    VertaaTiedosto.kirjoitaTiedosto(tiednimi,"33\n11\nkissa\n5"); 
    Suuntaaja.StringOutput so = new Suuntaaja.StringOutput(); 
    main(new String[]{tiednimi}); 
    so.palauta(); 
    String tulos = "Lukuja oli 3 kappaletta.\n"+
    "Niiden summa oli 49.00\n"+
    "ja keskiarvo oli 16.33\n"; 
    { String _l_=so.toString(),_r_="(?s)"+tulos.replaceAll("\n","\\\\s*"); if ( !_l_.matches(_r_) ) fail("From: TiedKaScanner7 line: 34" + " does not match: ["+ _l_ + "] != [" + _r_ + "]");}; 
    assertEquals("From: TiedKaScanner7 line: 35", null, so.ero(tulos));  // yksinkertaisempi kuin edellä
    VertaaTiedosto.tuhoaTiedosto(tiednimi); 
    Suuntaaja.StringOutput se = new Suuntaaja.StringOutput(true); 
    main(new String[]{"xxx.xxx"}); 
    se.palauta(); 
    { String _l_=se.toString(),_r_="(?s)"+"Tiedosto ei aukea! xxx\\.xxx .*"; if ( !_l_.matches(_r_) ) fail("From: TiedKaScanner7 line: 41" + " does not match: ["+ _l_ + "] != [" + _r_ + "]");}; 
  } // Generated by ComTest END
}