package kerho.test;
// Generated by ComTest BEGIN
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import kerho.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2023.03.06 12:18:12 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class KerhoTest {



  // Generated by ComTest BEGIN
  /** testAnnaKierrokset53 */
  @Test
  public void testAnnaKierrokset53() {    // Kerho: 53
    Kerho kerho = new Kerho(); 
    Pelaaja p1 = new Pelaaja(), p2 = new Pelaaja(), p3 = new Pelaaja(); 
    p1.rekisteroi(); p2.rekisteroi(); p3.rekisteroi(); 
    int id1 = p1.getpelaajaNro(); 
    int id2 = p2.getpelaajaNro(); 
    Kierros k1 = new Kierros(id1); kerho.lisaa(k1); 
    Kierros k2 = new Kierros(id1); kerho.lisaa(k2); 
    Kierros k3 = new Kierros(id2); kerho.lisaa(k3); 
    Kierros k4 = new Kierros(id2); kerho.lisaa(k4); 
    Kierros k5 = new Kierros(id2); kerho.lisaa(k5); 
    List<Kierros> loytyneet; 
    loytyneet = kerho.annaKierrokset(p3); 
    assertEquals("From: Kerho line: 68", 0, loytyneet.size()); 
    loytyneet = kerho.annaKierrokset(p1); 
    assertEquals("From: Kerho line: 70", 2, loytyneet.size()); 
    assertEquals("From: Kerho line: 71", true, loytyneet.get(0) == k1); 
    assertEquals("From: Kerho line: 72", true, loytyneet.get(1) == k2); 
    loytyneet = kerho.annaKierrokset(p2); 
    assertEquals("From: Kerho line: 74", 3, loytyneet.size()); 
    assertEquals("From: Kerho line: 75", true, loytyneet.get(0) == k3); 
  } // Generated by ComTest END
}