package kerho.test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Ajetaan kaikki testit
 * @author Miia Arkko
 * @version 24.2.2023
 *
 */
@Suite
@SelectClasses({ KerhoTest.class, KierroksetTest.class, KierrosTest.class,
        PelaajaTest.class, PelaajatTest.class })
public class AllTests {
    //
}
