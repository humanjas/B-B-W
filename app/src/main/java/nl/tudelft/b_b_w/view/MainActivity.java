package nl.tudelft.b_b_w.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import nl.tudelft.b_b_w.R;
import nl.tudelft.b_b_w.controller.BlockController;
import nl.tudelft.b_b_w.controller.ConversionController;
import nl.tudelft.b_b_w.model.Block;
import nl.tudelft.b_b_w.model.BlockFactory;
import nl.tudelft.b_b_w.model.TrustValues;
import nl.tudelft.b_b_w.model.User;

public class MainActivity extends Activity {
    private BlockController blockController;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blockController = new BlockController(this);


        // add genesis if we don't have any blocks
        if (user == null) {
            user = getUser();
            addGenesis();
        }
    }

    /**
     * Add a genesis block so that the database is not empty
     */
    private void addGenesis() {
        try {
            if (blockController.getBlocks(user.getName()).isEmpty()) {
                ConversionController converse = new ConversionController(user.getName(), blockController.getLatestSeqNumber(user.getName())+1, user.generatePublicKey(), "", "", user.getIBAN());

                Block block = BlockFactory.getBlock(
                        "BLOCK",
                        user.getName(),
                        blockController.getLatestSeqNumber(user.getName())+1,
                        converse.hashKey(),
                        "",
                        "",
                        user.generatePublicKey(),
                        user.getName(),
                        TrustValues.INITIALIZED.getValue()
                );
                blockController.addBlock(block);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public User getUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Welcome!");
        builder.setMessage("Fill in your information");

        final EditText nameBox = new EditText(this);
        final EditText ibanBox = new EditText(this);
        nameBox.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        nameBox.setHint("Name");
        ibanBox.setInputType(InputType.TYPE_CLASS_TEXT);
        ibanBox.setHint("IBAN");

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(nameBox);
        ll.addView(ibanBox);
        builder.setView(ll);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user = new User(nameBox.getText().toString(), ibanBox.getText().toString());
                System.out.print(user.toString());
            }
        });
        builder.show();
        return user;
    }

    /**
     * Callback for when user clicks on 'add block'. Switch to ContactsActivity.
     *
     * @param view current view, which is always MainActivity
     */
    public void onContacts(View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        intent.putExtra("owner", user.getName());
        startActivity(intent);
    }


    public void onFriends(View view) {
        Intent intent = new Intent(this, FriendsActivity.class);
        intent.putExtra("owner", user.getName());
        startActivity(intent);
    }

    public void onDisplayChain(View view) {
        Intent intent = new Intent(this, DisplayChainActivity.class);
        intent.putExtra("owner", user.getName());
        startActivity(intent);
    }
}
