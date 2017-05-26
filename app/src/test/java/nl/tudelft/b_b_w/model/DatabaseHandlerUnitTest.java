package nl.tudelft.b_b_w.model;

import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.b_b_w.BuildConfig;
import nl.tudelft.b_b_w.controller.BlockController;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,sdk= 21,  manifest = "src/main/AndroidManifest.xml")
public class DatabaseHandlerUnitTest {

    private GetDatabaseHandler getDatabaseHandler;
    private MutateDatabaseHandler mutateDatabaseHandler;
    private BlockController blockController;
    private final String TYPE_BLOCK = "BLOCK";
    private final String owner = "owner";
    private final int sequenceNumber = 1;
    private final String ownHash = "ownHash";
    private final String previousHashChain = "previousHashChain";
    private final String previousHashSender = "previousHashSender";
    private final String iban = "iban";
    private final String publicKey = "publicKey";
    private final int trustValue = 0;
    private Block _block;

    /**
     * setUp method
     * Does this method before every test
     * Initializes the database handler
     */
    @Before
    public void setUp() {
        this.blockController = new BlockController(RuntimeEnvironment.application);
        this.getDatabaseHandler = new GetDatabaseHandler(RuntimeEnvironment.application);
        this.mutateDatabaseHandler = new MutateDatabaseHandler(RuntimeEnvironment.application);
       _block =  BlockFactory.getBlock(TYPE_BLOCK, owner, blockController.getLatestSeqNumber(owner)+1,
               ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
    }

    /**
     * onAddBlock test
     * Tests adding a block
     */
    @Test
    public void addBlock() {
        mutateDatabaseHandler.addBlock(_block);
        assertEquals(_block, getDatabaseHandler.getBlock(owner, publicKey, sequenceNumber));
    }

    /**
     * addBlock2 test
     * Tests adding a block and a revoke block
     */
    @Test
    public void addBlock2() {
        String TYPE_REVOKE = "REVOKE";
        mutateDatabaseHandler.addBlock(_block);
        final Block newBlock = BlockFactory.getBlock(TYPE_REVOKE, owner, blockController.getLatestSeqNumber(owner)+1,
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        mutateDatabaseHandler.addBlock(newBlock);
        List<Block> list = new ArrayList<>();
        list.add(_block);
        list.add(newBlock);
        assertEquals(list, getDatabaseHandler.getAllBlocks(owner));
    }

    /**
     * getNullBlock test
     * Tests getting a non-existing block
     */
    @Test
    public void getNullBlock() {
        assertNull(getDatabaseHandler.getLatestBlock("null"));
    }

    /**
     * containsBlock/2 test
     * Tests whether a block exists
     */
    @Test
    public void containsBlock2() {
        mutateDatabaseHandler.addBlock(_block);
        assertTrue(getDatabaseHandler.containsBlock(owner));
    }

    /**
     * containsBlock/3 test
     * Tests whether a block exists
     */
    @Test
    public void containsBlock3() {
        mutateDatabaseHandler.addBlock(_block);
        assertTrue(getDatabaseHandler.containsBlock(owner, publicKey, sequenceNumber));
    }

    /**
     * containsBlock/2 test
     * Tests whether a block exists
     * Forces a false
     */
    @Test
    public void containsBlock2_false() {
        mutateDatabaseHandler.addBlock(_block);
        assertFalse(getDatabaseHandler.containsBlock(owner, "pub_key2", sequenceNumber));
    }

    /**
     * getLatestSeqNum test
     * Tests the latest sequence number of a block
     */
    @Test
    public void getLatestSeqNum() {

        final Block block2 = BlockFactory.getBlock(TYPE_BLOCK, owner, blockController.getLatestSeqNumber(owner)+1,
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        mutateDatabaseHandler.addBlock(_block);
        mutateDatabaseHandler.addBlock(block2);
        assertEquals(2, getDatabaseHandler.getLatestSeqNum(owner, publicKey));

    }

    /**
     * getLatestBlock test
     * Tests the latest block
     */
    @Test
    public void getLatestBlock() {
        final Block block2 = BlockFactory.getBlock(TYPE_BLOCK, "owner2", blockController.getLatestSeqNumber(owner)+1,
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        mutateDatabaseHandler.addBlock(_block);
        mutateDatabaseHandler.addBlock(block2);
        final Block expectBlock = BlockFactory.getBlock(TYPE_BLOCK, "owner2", blockController.getLatestSeqNumber(owner),
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        assertEquals(expectBlock, getDatabaseHandler.getLatestBlock("owner2"));
    }

    /**
     * getBlockAfter test
     * Tests the block after a block
     */
    @Test
    public void getBlockAfter() {
        final Block block2 = BlockFactory.getBlock(TYPE_BLOCK, owner, blockController.getLatestSeqNumber(owner)+1,
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        mutateDatabaseHandler.addBlock(_block);
        mutateDatabaseHandler.addBlock(block2);
        final Block expectBlock = BlockFactory.getBlock(TYPE_BLOCK, owner, blockController.getLatestSeqNumber(owner),
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        assertEquals(expectBlock, getDatabaseHandler.getBlockAfter(owner, sequenceNumber));
    }

    /**
     * getBlockBefore test
     * Tests the block before a block
     */
    @Test
    public void getBlockBefore() {
        final Block block2 = BlockFactory.getBlock(TYPE_BLOCK, owner, blockController.getLatestSeqNumber(owner)+1,
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        mutateDatabaseHandler.addBlock(_block);
        mutateDatabaseHandler.addBlock(block2);
        assertEquals(getDatabaseHandler.getBlockBefore(owner, 2), _block);
    }

    /**
     * getAllBlocks test
     * Tests getting all blocks
     */
    @Test
    public void getAllBlocks() {
        final String owner2 = "owner2";
        final Block block2 = BlockFactory.getBlock(TYPE_BLOCK, owner2, blockController.getLatestSeqNumber(owner)+1,
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        mutateDatabaseHandler.addBlock(_block);
        mutateDatabaseHandler.addBlock(block2);
        List<Block> result = new ArrayList<>();
        result.add(block2);
        assertEquals(result, getDatabaseHandler.getAllBlocks(owner2));
    }

    /**
     * updateBlockTest
     * Tests whether updating a block works
     */
    @Test
    public void updateBlockTest() {
        final Block block2 = BlockFactory.getBlock(TYPE_BLOCK, owner, blockController.getLatestSeqNumber(owner)+1,
                ownHash, previousHashChain, previousHashSender, publicKey, iban, trustValue);
        _block.setTrustValue(TrustValues.SUCCESFUL_TRANSACTION.getValue());
        mutateDatabaseHandler.updateBlock(_block);
        assertNotEquals(getDatabaseHandler.getBlock(owner, publicKey, sequenceNumber), block2);
    }

    /**
     * Test for onUpgrade
     */
    @Test
    public void onUpgrade() {
        SQLiteDatabase database = getDatabaseHandler.getReadableDatabase();
        getDatabaseHandler.onUpgrade(database, 0, 1);
        assertEquals(getDatabaseHandler.getReadableDatabase(), database);
    }

    /**
     * Closes database connection after test
     */
    @After
    public void tearDown() {
        getDatabaseHandler.close();
        mutateDatabaseHandler.close();
    }
}
