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

//import android.test.mock.MockContext;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
public class BlockControllerUnitTest {

    private BlockController bc;
    private String _owner;
    private String _previous_hash;
    private String _public_key;
    private Boolean _isRevoked;
    private int _sequence_number;
    Block _block;

    @Before
    public void setUp() {
        this.bc = new BlockController(RuntimeEnvironment.application);
        this._owner = "owner";
        this._previous_hash = "previous_hash";
        this._public_key = "public_key";
        this._isRevoked = false;
        this._sequence_number = 0;
        this._block = new Block(_owner, _sequence_number, _previous_hash, _public_key, _isRevoked);
    }

    @Test
    public void testAddBlock() throws Exception {
        bc.addBlock(_block);
        List<Block> list = new ArrayList<>();
        list.add(_block);
        assertEquals(bc.getBlocks(), list);
    }

    @Test(expected=RuntimeException.class)
    public void testAddDupBlocks() {
        bc.addBlock(_block);
        bc.addBlock(_block);
    }

    @Test(expected=RuntimeException.class)
    public void alreadyRevoked() {
        bc.addBlock(new Block(_owner,_sequence_number,_previous_hash, _public_key, true));
        bc.addBlock(_block);
    }

    @Test
    public void testRevokeBlock() throws Exception {
        bc.addBlock(_block);
        bc.revokeBlock(_block);
        List<Block> list = new ArrayList<>();
        list.add(new Block(_owner,_sequence_number,_previous_hash, _public_key, true));
        assertEquals(list, bc.getBlocks());
    }

    @Test
    public void testEmpList() {
        bc.addBlock(_block);
        bc.addBlock(new Block(_owner,_sequence_number,_previous_hash, _public_key, true));
        List<Block> list = new ArrayList<>();
        assertEquals(list, bc.getBlocks());
    }


}