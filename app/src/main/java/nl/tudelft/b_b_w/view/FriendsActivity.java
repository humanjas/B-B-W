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
public class FriendsActivity extends Activity {

    /**
     * Adapter to add the different blocks dynamically
     */
    public class ContactAdapter extends BaseAdapter implements ListAdapter {

        //Variables which we use for getting the block information
        private BlockController blcController;
        Context context;
        private final int image1 = 0;
        private final int image2 = 1;
        private final int image3 = 2;
        private final int image4 = 3;
        private final int image5 = 4;
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
         * Method to get the right image number
         * @param trust The trust value
         * @return Image number
         */
        public int getImageNo(int trust) {

            if (trust >= 80) return image1;
            if (trust >= 60) return image2;
            if (trust >= 40) return image3;
            if (trust >= 20) return image4;
            else return image5;
        }

        /**
         * Method to create a dialog
         * @param position Current position of the view
         * @return The listener
         */
        public View.OnClickListener createDialog(final int position) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure you want to add "+ blcController.getBlocks("GENESIS").get(position).getOwner()+ " IBAN?");


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
            };
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.simple_list_item_2, null);
            }

            //Setting the name field
            TextView nameItemText = (TextView)view.findViewById(R.id.list_item_name2);
            nameItemText.setText(blcController.getBlocks("GENESIS").get(position).getOwner()); //use function to backtrack owner
            //Setting the IBAN field
            TextView ibanItemText = (TextView)view.findViewById(R.id.list_item_iban2);
            ibanItemText.setText(blcController.getBlocks("GENESIS").get(position).getIban());

            //Setting the trust image
            ImageView pic = (ImageView)view.findViewById(R.id.trust_image2);
            int picNo = getImageNo(blcController.getBlocks("GENESIS").get(position).getTrustValue());
            pic.setImageResource(images[picNo]);

            //Setting the button to revoke
            Button addButton = (Button)view.findViewById(R.id.add_btn);

            //Listener for the revoke button
            addButton.setOnClickListener(createDialog(position));

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
        setContentView(R.layout.activity_friends);
        setTitle("Friend");


        // get contacts
        BlockController blcController = new BlockController(this);
        List<Block> blocks = blcController.getBlocks("GENESIS");

        //Setting up the graph
        GraphView graph = (GraphView) findViewById(R.id.graph2);
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
        ListView lView = (ListView)findViewById(R.id.friends);
        lView.setAdapter(adapter);
    }
}

