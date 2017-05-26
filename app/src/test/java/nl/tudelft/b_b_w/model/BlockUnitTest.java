package nl.tudelft.b_b_w.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import nl.tudelft.b_b_w.BuildConfig;
import nl.tudelft.b_b_w.controller.BlockController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,sdk= 21,  manifest = "src/main/AndroidManifest.xml")
public class BlockUnitTest {

    private Block _block;
    private BlockController blockController;
    private final String blockType = "BLOCK";
    private final String owner = "owner";
    private final String ownHash = "ownHash";
    private final String previousHashChain = "previousHashChain";
    private final String previousHashSender = "previousHashSender";
    private final String publicKey = "publicKey";
    private final String iban = "iban";
    private final int trustValue = 0;


    /**
     * This method runs before each test to initialize the test object
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Before
    public void makeNewBlock() throws Exception {
        blockController = new BlockController(RuntimeEnvironment.application);
        _block = BlockFactory.getBlock(blockType, owner, blockController.getLatestSeqNumber(owner)+1,
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
    }

    /**
     * Test to check whether the getOwner() method returns the owner name
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void getOwnerTest() throws Exception {
        final String check = "owner";
        assertEquals(check, _block.getOwner());
    }


    /**
     * Test to check whether the getOwnHash() method returns the right hash
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void getOwnHashTest() throws Exception {
        final String check = "ownHash";
        assertEquals(check, _block.getOwnHash());
    }

    /**
     * Test to check whether the getPreviousHashChain() returns the hash of the previous block in the chain
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void getPreviousHashChainTest() throws Exception {
        final String check = "previousHashChain";
        assertEquals(check, _block.getPreviousHashChain());
    }

    /**
     * Test to check whether the getPreviousHashSender() returns the hash of the previous block of the sender
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void getPreviousHashSenderTest() throws Exception {
        final String check = "previousHashSender";
        assertEquals(check, _block.getPreviousHashSender());
    }

    /**
     * Test to check whether the getSequence_number() returns the right sequence number of the block
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void getSequenceNumberTest() throws Exception {
        final int check = 1;
        assertEquals(check, _block.getSequenceNumber());
    }

    /**
     * Test to check whether the getPublic_key() function returns the right public key of the contact of the block
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void getPublicKeyTest() throws Exception {
        final String check = "publicKey";
        assertEquals(check, _block.getPublicKey());
    }

    /**
     * Test to check whether the getIban() function returns the right public key of the contact of the block
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void getIbanTest() throws Exception {
        final String check = "iban";
        assertEquals(check, _block.getIban());
    }

    /**
     * Test to check whether the isRevoked() method returns the right boolean value indicating if the block is revoked or not.
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void isRevokedTest() throws Exception {
        assertFalse(_block.isRevoked());
    }

    /**
     * Test to check whether getting and setting the trust value actually works
     */
    @Test
    public void testTrustValue() {
        final int SET_VALUE = 10;
        _block.setTrustValue(SET_VALUE);
        assertEquals(SET_VALUE, _block.getTrustValue());
    }

    /**
     * Test to check whether initializing the trustvalue works
     */
    @Test
    public void testTrustValueInit() {
        assertEquals(0, _block.getTrustValue());
    }

    /**
     * Test to check whether the equals() method returns the right boolean value indicating if this block is equal to the parameter block.
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void equalsTest() throws Exception {
        final Block check = BlockFactory.getBlock(blockType, owner, blockController.getLatestSeqNumber(owner)+1,
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        assertTrue(_block.equals(check));
    }

    /**
     * Test to check whether the equals() method returns the right boolean value indicating if this block is equal to the parameter block.
     * Forces a false
     * @throws Exception Catches error when the MessageDigest
     * gets an error.
     */
    @Test
    public void equalsFalseTest() throws Exception {
        final String _owner = "NOTOWNER";
        final Block check = BlockFactory.getBlock(blockType, _owner, blockController.getLatestSeqNumber(owner),
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        assertFalse(_block.equals(check));
    }

    /**
     * Test whether the toString method is correct
     */
    @Test
    public void toStringTest() {
        final boolean isRevoked = false;
        final int sequenceNumber = 1;
        final String result = "Block{" +
                "owner='" + owner + '\'' +
                ", sequenceNumber=" + sequenceNumber +
                ", ownHash='" + ownHash + '\'' +
                ", previousHashChain='" + previousHashChain + '\'' +
                ", previousHashSender='" + previousHashSender + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", iban='" + iban + '\'' +
                ", trustValue='" + 0 + '\'' +
                ", isRevoked=" + isRevoked +
                '}';
        assertEquals(result, _block.toString());
    }

    /**
     * Testing whether the hashcode method yields the same
     * output with the same input
     */
    @Test
    public void testHashCode() {
        final String owner2 = "owner2";
        final Block x = BlockFactory.getBlock(blockType, owner2, blockController.getLatestSeqNumber(owner2),
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        final Block y = BlockFactory.getBlock(blockType, owner2, blockController.getLatestSeqNumber(owner2),
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }



}
