package nl.tudelft.b_b_w.view.TestSubjects;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import nl.tudelft.b_b_w.R;
import nl.tudelft.b_b_w.controller.BlockController;
import nl.tudelft.b_b_w.model.Block;
import nl.tudelft.b_b_w.model.BlockFactory;

/**
 * This class represents a test subject. All it's values are predefined. It is used to simulate a transaction and
 * update the friends page.
 */
public class TestSubject3 extends Activity {

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
        setContentView(R.layout.activity_test_subject3);

        ownerName = "TestSubject2";
        blockController = new BlockController(this);
        block1 = BlockFactory.getBlock("BLOCK", ownerName, "HASH1","N/A","N/A","TestSubject_PUBKEY","IBANTestSubject1");
        block2 = BlockFactory.getBlock("BLOCK", ownerName, "HASH2","HASH1","HASHfromContact1","Contact1_PUBKEY","IBANContact1");


        blockController.addBlock(block1);
        //blockController.addBlock(block2);


        List<Block> list = blockController.getBlocks(ownerName);

        Toast.makeText(this, list.get(0).getPublicKey() + ", " +
                list.get(1).getPublicKey() + ", " +
                list.get(2).getPublicKey(), Toast.LENGTH_SHORT).show();
    }

}
