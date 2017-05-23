package nl.tudelft.b_b_w.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

import nl.tudelft.b_b_w.R;
import nl.tudelft.b_b_w.controller.BlockController;
import nl.tudelft.b_b_w.model.Block;

/**
 * When the user wants to add a block he enters into the ContactsActivity, which contain
 * some entry fields and a button to confirm the addition.
 */
public class ContactsActivity extends Activity {
    /**
     * On create we request a database connection
     *
     * @param savedInstanceState unused, meant for serialisation
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // get contacts
        BlockController bc = new BlockController(this);
        List<Block> blocks = bc.getBlocks("GENESIS");

        // fill list
        List<String> contacts = new LinkedList<String>();
        for (Block block : blocks)
            contacts.add(block.getPublicKey());

        // list
        ListView view = (ListView) findViewById(R.id.contacts);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1, contacts);
        view.setAdapter(adapter);
    }
}

