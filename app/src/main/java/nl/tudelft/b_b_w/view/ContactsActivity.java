package nl.tudelft.b_b_w.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import nl.tudelft.b_b_w.R;
import nl.tudelft.b_b_w.controller.BlockController;

/**
 * When the user wants to add a block he enters into the ContactsActivity, which contain
 * some entry fields and a button to confirm the addition.
 */
public class ContactsActivity extends Activity {
    public class ContactAdapter extends BaseAdapter implements ListAdapter {
        private BlockController bc;
        Context context;

        public ContactAdapter(BlockController bc, Context context) {
            this.context = context;
            this.bc = bc;
        }

        @Override
        public Object getItem(int position) {
            return bc.getBlocks("GENESIS").get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getCount() {
            return bc.getBlocks("GENESIS").size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.simple_list_item_1, null);
            }

            TextView nameItemText = (TextView)view.findViewById(R.id.list_item_name);
            nameItemText.setText(bc.getBlocks("GENESIS").get(position).getOwner()); //use function to backtrack owner
            TextView ibanItemText = (TextView)view.findViewById(R.id.list_item_iban);
            ibanItemText.setText(bc.getBlocks("GENESIS").get(position).getPublicKey()); //change to IBAN

            Button revokeButton = (Button)view.findViewById(R.id.revoke_btn);

            revokeButton.setOnClickListener(new View.OnClickListener() {
               @Override
                public void onClick(View v) {
                   AlertDialog.Builder builder = new AlertDialog.Builder(context);

                   builder.setTitle("Confirm");
                   builder.setMessage("Are you sure you want to revoke "+ bc.getBlocks("GENESIS").get(position).getOwner()+ " IBAN?");

                   builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                       public void onClick(DialogInterface dialog, int which) {
                           bc.revokeBlock(bc.getBlocks("GENESIS").get(position));
                           notifyDataSetChanged();
                           dialog.dismiss();
                       }
                   });

                   builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                       @Override
                       public void onClick(DialogInterface dialog, int which) {

                           // Do nothing
                           dialog.dismiss();
                       }
                   });

                   AlertDialog alert = builder.create();
                   alert.show();
               }
            });

            return view;
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
//        bc.addBlock(BlockFactory.getBlock("BLOCK", "GENESIS", 2,
//                "Hash", "prevhash", "prevhashsender",
//                "pubkey"));

        ContactAdapter adapter = new ContactAdapter(bc, this);
        ListView lView = (ListView)findViewById(R.id.contacts);
        lView.setAdapter(adapter);

//        try {
//            // list
//            ListView view = (ListView) findViewById(contacts);
//            ContactAdapter adapter = new ContactAdapter(bc);
//            view.setAdapter(adapter);
//        } catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//        }
    }
}

