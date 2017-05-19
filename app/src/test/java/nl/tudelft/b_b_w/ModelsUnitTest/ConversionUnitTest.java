package nl.tudelft.b_b_w.ModelsUnitTest;

import org.junit.Test;
import nl.tudelft.b_b_w.model.Conversion;

import static org.junit.Assert.*;
/**
 * ConversionTest
 * Created by Ashay on 01/05/2017.
 */
public class ConversionUnitTest {

    /**
     * Test to check whether the hash function gives the
     * desired output.
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void hashCheck() throws Exception {
        Conversion co = new Conversion("key", "owner","2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824","d79d0573a5af1a8c8a77b10319b8dcb3e8370c3020ce3414437dfb7004ab3460");
        String check = "414594c96f6e16fc8684f10d687d712bdddce305242a465245e9e7a53fab8abb";

        assertEquals(check, co.hashKey());
    }
}