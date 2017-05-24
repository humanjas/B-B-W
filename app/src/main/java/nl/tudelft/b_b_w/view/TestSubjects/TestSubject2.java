package nl.tudelft.b_b_w.view.TestSubjects;

import android.app.Activity;
import android.os.Bundle;

import nl.tudelft.b_b_w.R;
import nl.tudelft.b_b_w.controller.BlockController;
import nl.tudelft.b_b_w.model.Block;
import nl.tudelft.b_b_w.model.BlockFactory;

/**
 * This class represents a test subject. All it's values are predefined. It is used to simulate a transaction and
 * update the friends page.
 */
public class TestSubject2 extends Activity {

    /**
     * A block controller.
     */
    private BlockController blockController;

    /**
     * It's own block.
     */
    private Block block1;
    /**
     * A block.
     */
    private Block block2;

    /**
     * A block.
     */
    private Block block3;

    /**
     * A block.
     */
    private Block block4;

    /**
     * A block.
     */
    private Block block5;

    /**
     * The owner name of the blocks.
     */
    private String ownerName;


    /**
     * On create method of this activity. Should create a chain for test subject 1.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_subject2);

        ownerName = "TestSubject2";
        blockController = new BlockController(this);
        block1 = BlockFactory.getBlock("BLOCK", ownerName, "HASH1","N/A","N/A","TestSubject_PUBKEY","IBANTestSubject1");
        block2 = BlockFactory.getBlock("BLOCK", ownerName, "HASH2","HASH1","HASHfromContact1","Contact1_PUBKEY","IBANContact1");
        block3 = BlockFactory.getBlock("BLOCK", ownerName, "HASH3","HASH2","HASHfromContact2","Contact2_PUBKEY","IBANContact2");
        block4 = BlockFactory.getBlock("BLOCK", ownerName, "HASH4","HASH3","HASHfromContact3","Contact3_PUBKEY","IBANContact3");
        block5 = BlockFactory.getBlock("BLOCK", ownerName, "HASH5","Hash4","HASHfromContact4","Contact4_PUBKEY","IBANContact4");

    }

}
