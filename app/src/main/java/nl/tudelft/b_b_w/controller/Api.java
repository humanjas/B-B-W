package nl.tudelft.b_b_w.controller;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.b_b_w.model.Block;

/** The Api class provides read and write access to our data without having to worry about
 * the blockchain and database.
 */
public class Api {
    private static Context context;
    private static BlockController blockController;

    /**
     * Initialize the API with a context
     * @param context A context object, needed for the database
     */
    public static void init(Context context) {
        Api.context = context;
        Api.blockController = new BlockController(context);
    }

    /**
     * Retrieve the keys of a user from the viewpoint of a blockchain owner
     * @param owner the owner of the blockchain to query
     * @param user the user of whom to retrieve non-revoked public keys
     * @return a list of public keys in string form
     */
    public static List<String> userGetKeys(String owner, String user) {
        List<Block> blocks = blockController.getBlocks(user);
        List<String> keys = new ArrayList<String>();

        // add public key of each block
        for (Block block : blocks)
            keys.add(block.getPublicKey());

        return keys;
    }
}
