package nl.tudelft.b_b_w;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nl.tudelft.b_b_w.Models.Block;
import nl.tudelft.b_b_w.Models.DatabaseHandler;

/**
 * When the user wants to add a block he enters into the AddBlockActivity, which contain
 * some entry fields and a button to confirm the addition.
 */
public class AddBlockActivity extends Activity {
    /**
     * Connection with block database
     */
    private DatabaseHandler handler;

    /**
     * On create we request a database connection
     * @param savedInstanceState unused, meant for serialisation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addblock);
        handler = new DatabaseHandler(this);
    }

    /**
     * Upon adding a block, information is extracted from the GUI and put into the fresh block,
     * which is added to the database.
     * @param view current view (AddBlockActivity)
     */
    public void addBlock(View view) {
        // extract information
        EditText ownerText = (EditText) findViewById(R.id.addContactName);
        EditText publicKeyText = (EditText) findViewById(R.id.addPublicKey);
        String owner = ownerText.getText().toString();
        String publicKey = publicKeyText.getText().toString();

        // create and add the block
        Block previous = handler.getLatestBlock(owner, publicKey);
        Block block = new Block(owner, handler.getLatestSeqNum(owner, publicKey), "temp", previous.getOwnHash(), previous.getPreviousHashSender(), publicKey, false);
        handler.addBlock(block);

        // confirm by showing a small text message
        Toast.makeText(this, "Block added", Toast.LENGTH_SHORT).show();
    }
}
