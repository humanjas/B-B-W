package nl.tudelft.b_b_w.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import nl.tudelft.b_b_w.R;
import nl.tudelft.b_b_w.controller.BlockController;
import nl.tudelft.b_b_w.model.DatabaseHandler;

/**
 * This class displays the Friend Page. Here we want to be able to see the
 * iban number, the name and the rating of the person you paired with. Also
 * you are able to add this person to your contact list.
 */
public class FriendsPageActivity extends Activity {

    /**
     * Used to create connection with database
     */
    private DatabaseHandler databaseHandler;

    /**
     * block controller
     */
    private BlockController blockController;

    /**
     * the owner of the block
     */
    private String ownerName;

    /**
     * the IBAN nummer of the owner
     */
    private String ibanNumber;

    /**
     * the public key of the block
     */
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


    public void addBlock(View view) {

        EditText senderNameText = (EditText) findViewById(R.id.senderName);


    }
}