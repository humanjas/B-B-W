package nl.tudelft.b_b_w.controller;

import android.content.res.Resources;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.b_b_w.BuildConfig;
import nl.tudelft.b_b_w.model.Block;
import nl.tudelft.b_b_w.model.BlockFactory;
import nl.tudelft.b_b_w.model.TrustValues;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,sdk= 21,  manifest = "src/main/AndroidManifest.xml")
public class BlockControllerUnitTest {

    /**
     * Attributes
     */
    private BlockController bc;
    private final String owner = "owner";
    private final String newOwner = owner+"2";
    private final String ownHash = "ownHash";
    private final String previousHashChain = "previousHashChain";
    private final String previousHashSender = "previousHashSender";
    private final String iban = "iban";
    private final String publicKey = "publicKey";
    private Block _block;
    private final int trustValue = 0;
    private final String TYPE_BLOCK = "BLOCK";

    /**
     * Initialize BlockController before every test
     * And initialize a dummy block _block
     */
    @Before
    public void setUp() {
        this.bc = new BlockController(RuntimeEnvironment.application);
        this._block = BlockFactory.getBlock(TYPE_BLOCK, owner, bc.getLatestSeqNumber(owner)+1,
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
    }

    /**
     * Tests adding a block
     * @throws Exception RuntimeException
     */
    @Test
    public void testAddBlock() throws Exception {
        bc.addBlock(_block);
        List<Block> list = new ArrayList<>();
        list.add(_block);
        assertEquals(bc.getBlocks(owner), list);
    }


    /**
     * Tests to return the latest block
     * @throws Exception RuntimeException
     */
    @Test
    public void testGetLatestBlock() throws Exception {
        final Block expected = BlockFactory.getBlock(TYPE_BLOCK, owner, bc.getLatestSeqNumber(owner)+1,
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        bc.addBlock(_block);
        assertEquals(expected, bc.getLatestBlock(owner));
    }

    /**
     * Tests returning the latest sequence number of chain
     * @throws Exception RuntimeException
     */
    @Test
    public void testGetLatestSeqNumber() throws Exception {
        final Block newBlock = BlockFactory.getBlock(TYPE_BLOCK, newOwner, bc.getLatestSeqNumber(newOwner)+1,
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        bc.addBlock(_block);
        bc.addBlock(newBlock);


        assertEquals(1, bc.getLatestSeqNumber(owner));
    }




    /**
     * Tests adding two blocks
     * @throws Exception RuntimeException
     */
    @Test
    public void testAddBlock2() throws Exception {
        final Block newBlock = BlockFactory.getBlock(TYPE_BLOCK, newOwner, bc.getLatestSeqNumber(newOwner)+1,
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        bc.addBlock(_block);
        bc.addBlock(newBlock);
        List<Block> list = new ArrayList<>();
        list.add(_block);
        list.add(newBlock);

        List<Block> newList = bc.getBlocks(owner);
        newList.addAll(bc.getBlocks(newOwner));

        assertEquals(newList, list);
}

    /**
     * Tests adding a duplicate block
     */
    @Test(expected=RuntimeException.class)
    public void testAddDupBlocks() {
        bc.addBlock(_block);
        bc.addBlock(_block);
    }

    /**
     * Tests adding an already revoked block
     */
    @Test(expected=RuntimeException.class)
    public void alreadyRevoked() {
        final Block newBlock = BlockFactory.getBlock(TYPE_BLOCK, owner, bc.getLatestSeqNumber(owner)+1,
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        bc.addBlock(newBlock);
        bc.addBlock(_block);
    }

    /**
     * Tests filtering duplicates out of a list
     */
    @Test
    public void testEmptyList() {
        bc.addBlock(_block);
        final Block newBlock = BlockFactory.getBlock(TYPE_BLOCK, owner, bc.getLatestSeqNumber(owner)+1,
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        bc.revokeBlock(newBlock);
        List<Block> list = new ArrayList<>();
        assertEquals(list, bc.getBlocks(owner));
    }

    /**
     * Test removeBlock if the specified revoked block has no match
     */
    @Test
    public void testRemoveWithNoMatch() throws Resources.NotFoundException{
        bc.addBlock(_block);
        final Block newBlock = BlockFactory.getBlock(TYPE_BLOCK, newOwner, bc.getLatestSeqNumber(newOwner)+1,
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        bc.revokeBlock(newBlock);
        List<Block> list = new ArrayList<>();
        list.add(_block);
        assertEquals(list, bc.getBlocks(owner));
    }

    /**
     * verifyIBAN test
     * Tests whether verifying the IBAN updates the trust value
     */
    @Test
    public void testVerifyIBAN() {
        bc.verifyIBAN(_block);
        assertEquals(TrustValues.VERIFIED.getValue(), _block.getTrustValue());
    }

    /**
     * successfulTransaction test
     * Tests whether a successful transaction updates the trust value
     */
    @Test
    public void testSuccessfulTransaction() {
        bc.successfulTransaction(_block);
        assertEquals(TrustValues.SUCCESFUL_TRANSACTION.getValue(), _block.getTrustValue());
    }

    /**
     * failedTransaction test
     * Tests whether a successful transaction updates the trust value
     */
    @Test
    public void testFailedTransaction() {
        bc.failedTransaction(_block);
        assertEquals(TrustValues.FAILED_TRANSACTION.getValue(), _block.getTrustValue());
    }

    /**
     * revokedTrustValue test
     * Tests whether a revoked transaction updates the trust value
     */
    @Test
    public void testRevokedTrustValue() {
        bc.revokedTrustValue(_block);
        assertEquals(TrustValues.REVOKED.getValue(), _block.getTrustValue());
    }

}