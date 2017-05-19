package nl.tudelft.b_b_w;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import nl.tudelft.b_b_w.Controllers.BlockController;
import nl.tudelft.b_b_w.Models.Block;
import nl.tudelft.b_b_w.Models.DatabaseHandler;

public class MainActivity extends Activity {
    private DatabaseHandler handler;
    private BlockController blockController;
    private String ownerName;
    private String publicKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new DatabaseHandler(this);
        blockController = new BlockController(this);
        addGenesis();
        ownerName = "GENESIS";
        publicKey = "demokey";
    }

    /**
     * Add a genesis block so that the database is not empty
     */
    private void addGenesis() {
        if (handler.getAllBlocks(ownerName).isEmpty()) {
            Block block = new Block(ownerName, 0, "", "", "", "", false);
            blockController.addBlock(block);
        }
    }

    /**
     * Callback for when user clicks on 'add block'. Switch to AddBlockActivity.
     * @param view current view, which is always MainActivity
     */
    public void onAddBlock(View view) {
        Intent intent = new Intent(this, AddBlockActivity.class);
        intent.putExtra("ownerName", ownerName);
        intent.putExtra("publicKey", publicKey);
        startActivity(intent);
    }


    /**
     * Callback for when user clicks on 'delete block'. Switch to DeleteBlockActivity.
     * @param view current view, which is always MainActivity
     */
    public void onDeleteBlock(View view) {
        Intent intent = new Intent(this, DeleteBlockActivity.class);
        startActivity(intent);
    }

    public void onDisplayChain(View view) {
    }
}
