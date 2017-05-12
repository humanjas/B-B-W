package nl.tudelft.b_b_w.ModelsUnitTest;

import org.junit.Before;
import org.junit.Test;
import nl.tudelft.b_b_w.Models.Block;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BlockUnitTest {

    Block co;

    /**
     * This method runs before each test to initialize the test object
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Before
    public void makeNewBlock() throws Exception {
        co = new Block("owner", 1, "1234PreviousHash1234", "1234PublicKey1234", false);
    }

    /**
     * Test to check whether the getOwner() method returns the owner name
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void getOwnerTest() throws Exception {
        String check = "owner";
        assertEquals(check, co.getOwner());
    }

    /**
     * Test to check whether the getPrevious_hash() returns the hash of the previous block
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void getPreviousHashTest() throws Exception {
        String check = "1234PreviousHash1234";
        assertEquals(check, co.getPrevious_hash());
    }

    /**
     * Test to check whether the getSequence_number() returns the right sequence number of the block
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void getSequenceNumberTest() throws Exception {
        int check = 1;
        assertEquals(check, co.getSequence_number());
    }

    /**
     * Test to check whether the getPublic_key() function returns the right public key of the contact of the block
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void getPublicKeyTest() throws Exception {
        String check = "1234PublicKey1234";
        assertEquals(check, co.getPublic_key());
    }

    /**
     * Test to check whether the isRevoked() method returns the right boolean value indicating if the block is revoked or not.
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void isRevokedTest() throws Exception {
        assertFalse(co.isRevoked());
    }

    /**
     * Test to check whether the equals() method returns the right boolean value indicating if this block is equal to the parameter block.
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void equalsTest() throws Exception {
        Block check = new Block("owner", 1, "1234PreviousHash1234", "1234PublicKey1234", false);
        assertTrue(co.equals(check));
    }



}