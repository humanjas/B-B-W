package nl.tudelft.b_b_w.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import nl.tudelft.b_b_w.R;
import nl.tudelft.b_b_w.controller.BlockController;
import nl.tudelft.b_b_w.model.DatabaseHandler;

public class FriendsPageActivity extends Activity {

    private DatabaseHandler databaseHandler;

    private BlockController blockController;

    private String ownerName;

    private String ibanNumber;

    private String publicKey;

    /**
     * On create method, here we request a database connection
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_page);

        Bundle extra = getIntent().getExtras();

        databaseHandler = new DatabaseHandler(this);
        blockController = new BlockController(this);
        ownerName   = extra.getString("ownerName");
        publicKey = extra.getString("publicKey");
        ibanNumber = extra.getString("ibanNumber");

        Toast.makeText(this, ownerName + ", " + publicKey + " " + ibanNumber , Toast.LENGTH_SHORT).show();

    }




}
