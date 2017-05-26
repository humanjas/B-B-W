package nl.tudelft.b_b_w.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import nl.tudelft.b_b_w.R;
import nl.tudelft.b_b_w.controller.BlockController;
import nl.tudelft.b_b_w.model.Block;
import nl.tudelft.b_b_w.model.BlockFactory;

public class MainActivity extends Activity {
    private BlockController blockController;
    private String ownerName;
    private String publicKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blockController = new BlockController(this);
        ownerName = "GENESIS";
        publicKey = "demokey";

        //test method
        final int n = blockController.getLatestSeqNumber(ownerName);
        Toast.makeText(this, "#" + n, Toast.LENGTH_LONG).show();

        // add genesis if we don't have any blocks
        if (blockController.getLatestSeqNumber(ownerName) == 0)
            addGenesis();
    }

    /**
     * Add a genesis block so that the database is not empty
     */
    private void addGenesis() {
        try {
            if (blockController.getBlocks(ownerName).isEmpty()) {

                Block block = BlockFactory.getBlock(
                        "BLOCK",
                        ownerName,
                        blockController.getLatestSeqNumber(ownerName)+1,
                        "ownHash",
                        "previoushash",
                        "senderhash",
                        "senderpubkey",
                        "senderIban",
                        0
                );
                blockController.addBlock(block);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Callback for when user clicks on 'add block'. Switch to ContactsActivity.
     *
     * @param view current view, which is always MainActivity
     */
    public void onContacts(View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        intent.putExtra("ownerName", ownerName);
        intent.putExtra("publicKey", publicKey);
        startActivity(intent);
    }
//    public void onAddBlock(View view) {
//        Intent intent = new Intent(this, AddBlockActivity.class);
//        intent.putExtra("ownerName", ownerName);
//        intent.putExtra("publicKey", publicKey);
//        startActivity(intent);
//    }


    /**
     * Callback for when user clicks on 'delete block'. Switch to RevokeBlockActivity.
     *
     * @param view current view, which is always MainActivity
     */
//    public void onRevokeBlock(View view) {
//        Intent intent = new Intent(this, RevokeBlockActivity.class);
//        intent.putExtra("ownerName", ownerName);
//        intent.putExtra("publicKey", publicKey);
//        startActivity(intent);
//    }

//    public void onDisplayChain(View view) {
//        Intent intent = new Intent(this, DisplayChainActivity.class);
//        intent.putExtra("ownerName", ownerName);
//        intent.putExtra("publicKey", publicKey);
//        startActivity(intent);
//    }
}
