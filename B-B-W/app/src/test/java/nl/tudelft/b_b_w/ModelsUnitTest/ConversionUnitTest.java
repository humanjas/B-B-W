package nl.tudelft.b_b_w.ModelsUnitTest;

import org.junit.Test;
import nl.tudelft.b_b_w.Models.Conversion;

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
        Conversion co = new Conversion("key", "owner");
        co.hashKey();
        String check = "54058e5f4c25b4d670ee8f37f2a1fa1941d9f0d8f3442dd4605040b5268e8056";

        assertEquals(check, co.getHashedPk());
    }
}