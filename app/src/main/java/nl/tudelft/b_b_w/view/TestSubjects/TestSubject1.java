package nl.tudelft.b_b_w.view.TestSubjects;

import android.app.Activity;
import android.os.Bundle;

import nl.tudelft.b_b_w.R;
import nl.tudelft.b_b_w.controller.BlockController;
import nl.tudelft.b_b_w.model.Block;
import nl.tudelft.b_b_w.model.BlockFactory;

public class TestSubject1 extends Activity {

    private BlockController blockController;
    private Block block1;
    private Block block2;
    private Block block3;
    private Block block4;

//

    /**
     * String type, String _owner, String _ownHash, String _previousHashChain, String _previousHashSender, String _publicKey, String _iban)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_subject1);

        blockController = new BlockController(this);
        block1 = BlockFactory.getBlock("BLOCK", "TestSubject1", "HASH1","N/A","N/A","TestSubject_PUBKEY","IBANTestSubject1");
        block2 = BlockFactory.getBlock("BLOCK", "TestSubject1", "HASH2","HASH1","HASHfromContact1","Contact1_PUBKEY","IBANContact1");
        block3 = BlockFactory.getBlock("BLOCK", "TestSubject1", "HASH3","HASH2","HASHfromContact2","Contact2_PUBKEY","IBANContact2");
        block4 = BlockFactory.getBlock("BLOCK", "TestSubject1", "HASH4","HASH3","HASHfromContact3","Contact3_PUBKEY","IBANContact3");



    }




}
