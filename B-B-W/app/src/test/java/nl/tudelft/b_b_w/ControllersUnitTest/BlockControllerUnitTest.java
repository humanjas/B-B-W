package nl.tudelft.b_b_w.ControllersUnitTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.b_b_w.Controllers.BlockController;
import nl.tudelft.b_b_w.Models.Block;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

//import android.test.mock.MockContext;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
public class BlockControllerUnitTest {

    /**
     * Attributes
     */
    private BlockController bc;
    final String owner = "owner";
    final int sequenceNumber = 0;
    final String ownHash = "ownHash";
    final String previousHashChain = "previousHashChain";
    final String previousHashSender = "previousHashSender";
    final String publicKey = "publicKey";
    final boolean isRevoked = false;
    Block _block;

    /**
     * Initialize BlockController before every test
     * And initialize a dummy block _block
     */
    @Before
    public void setUp() {
        this.bc = new BlockController(RuntimeEnvironment.application);
        this._block = new Block(owner, sequenceNumber, ownHash, previousHashChain, previousHashSender, publicKey, isRevoked);
    }

    /**
     * Tests adding a block
     * @throws Exception
     */
    @Test
    public void testAddBlock() throws Exception {
        bc.addBlock(_block);
        List<Block> list = new ArrayList<>();
        list.add(_block);
        assertEquals(bc.getBlocks(), list);
    }

    /**
     * Tests adding two blocks
     * @throws Exception
     */
    @Test
    public void testAddBlock2() throws Exception {
        Block newBlock = new Block(owner+"2", sequenceNumber, ownHash, previousHashChain, previousHashSender, publicKey, isRevoked);
        bc.addBlock(_block);
        bc.addBlock(newBlock);
        List<Block> list = new ArrayList<>();
        list.add(_block);
        list.add(newBlock);
        assertEquals(bc.getBlocks(), list);
    }

    /**
     * Tests adding a duplicate block
     * @throws Exception RuntimeException
     */
    @Test(expected=RuntimeException.class)
    public void testAddDupBlocks() {
        bc.addBlock(_block);
        bc.addBlock(_block);
    }

    /**
     * Tests adding an already revoked block
     * @throws Exception RuntimeException
     */
    @Test(expected=RuntimeException.class)
    public void alreadyRevoked() {
        bc.addBlock(new Block(owner, sequenceNumber, ownHash, previousHashChain, previousHashSender, publicKey, true));
        bc.addBlock(_block);
    }

    /**
     * Tests adding a revoke block
     * @throws Exception
     */
    @Test
    public void testRevokeBlock() throws Exception {
        bc.addBlock(_block);
        bc.revokeBlock(_block);
        List<Block> list = new ArrayList<>();
        list.add(new Block(owner, sequenceNumber, ownHash, previousHashChain, previousHashSender, publicKey, true));
        assertNotEquals(list, bc.getBlocks());
    }

    /**
     * Tests filtering duplicates out of a list
     * @throws Exception
     */
    @Test
    public void testEmpList() {
        bc.addBlock(_block);
        bc.addBlock(new Block(owner, sequenceNumber, ownHash, previousHashChain, previousHashSender, publicKey, true));
        List<Block> list = new ArrayList<>();
        assertEquals(list, bc.getBlocks());
    }

}