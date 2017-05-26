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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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
     * Adapter to add the different blocks dynamically
     */
    public class ContactAdapter extends BaseAdapter implements ListAdapter {

        //Variables which we use for getting the block information
        private BlockController blcController;
        Context context;
        //Images for displaying trust
        private Integer images[] = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5};

        /**
         * Default constructor to initiate the Adapter
         * @param bc BlockController which is passed on
         * @param context Context which is passed on
         */
        public ContactAdapter(BlockController bc, Context context) {
            this.context = context;
            this.blcController = bc;
        }

        /**
         *{@inheritDoc}
         */
        @Override
        public Object getItem(int position) {
            return blcController.getBlocks("GENESIS").get(position);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public long getItemId(int position) {
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getCount() {
            return blcController.getBlocks("GENESIS").size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.simple_list_item_1, null);
            }

            //Setting the name field
            TextView nameItemText = (TextView)view.findViewById(R.id.list_item_name);
            nameItemText.setText(blcController.getBlocks("GENESIS").get(position).getOwner()); //use function to backtrack owner
            //Setting the IBAN field
            TextView ibanItemText = (TextView)view.findViewById(R.id.list_item_iban);
            ibanItemText.setText(blcController.getBlocks("GENESIS").get(position).getIban());

            //Setting the trust image
            ImageView pic = (ImageView)view.findViewById(R.id.trust_image);
            int trust = blcController.getBlocks("GENESIS").get(position).getTrustValue();

            //Switch case for deciding which image needs be picked for the trust value
            switch (trust) {
                case 100: pic.setImageResource(images[0]);
                    break;
                case 90: pic.setImageResource(images[0]);
                    break;
                case 80: pic.setImageResource(images[0]);
                    break;
                case 70: pic.setImageResource(images[1]);
                    break;
                case 60: pic.setImageResource(images[1]);
                    break;
                case 50: pic.setImageResource(images[2]);
                    break;
                case 40: pic.setImageResource(images[2]);
                    break;
                case 30: pic.setImageResource(images[3]);
                    break;
                case 20: pic.setImageResource(images[3]);
                    break;
                case 10: pic.setImageResource(images[4]);
                    break;
                case 0: pic.setImageResource(images[4]);
                    break;
            }

            //Setting the button to revoke
            Button revokeButton = (Button)view.findViewById(R.id.revoke_btn);

            //Listener for the revoke button
            revokeButton.setOnClickListener(new View.OnClickListener() {
               @Override
                public void onClick(View v) {
                   AlertDialog.Builder builder = new AlertDialog.Builder(context);

                   builder.setTitle("Confirm");
                   builder.setMessage("Are you sure you want to revoke "+ blcController.getBlocks("GENESIS").get(position).getOwner()+ " IBAN?");

                   builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                       public void onClick(DialogInterface dialog, int which) {
                           blcController.revokeBlock(blcController.getBlocks("GENESIS").get(position));
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
        setTitle("Contacts");


        // get contacts
        BlockController blcController = new BlockController(this);
        List<Block> blocks = blcController.getBlocks("GENESIS");

        //Setting up the graph
        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(blocks.size());

        DataPoint[] points = new DataPoint[blocks.size()];
        for (int i = 0; i < blocks.size(); i++) {
            points[i] = new DataPoint(i, blocks.get(i).getTrustValue());
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        graph.addSeries(series);

        ContactAdapter adapter = new ContactAdapter(blcController, this);
        ListView lView = (ListView)findViewById(R.id.contacts);
        lView.setAdapter(adapter);
    }
}

