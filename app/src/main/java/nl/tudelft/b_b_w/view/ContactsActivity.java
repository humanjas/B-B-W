package nl.tudelft.b_b_w.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import nl.tudelft.b_b_w.R;
import nl.tudelft.b_b_w.controller.BlockController;
import nl.tudelft.b_b_w.model.Block;

import static nl.tudelft.b_b_w.R.id.contacts;

/**
 * When the user wants to add a block he enters into the ContactsActivity, which contain
 * some entry fields and a button to confirm the addition.
 */
public class ContactsActivity extends Activity {
    public class ContactAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<Block> blocks;

        public ContactAdapter(List<Block> blocks) {
            super();
            inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.blocks = blocks;
        }

        @Override
        public String getItem(int position) {
            return blocks.get(position).toString();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return blocks.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return inflater.inflate(R.layout.simple_list_item_1, null);
        }
    }
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

        try {
            // list
            ListView view = (ListView) findViewById(contacts);
            ContactAdapter adapter = new ContactAdapter(blocks);
            view.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

