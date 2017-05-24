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

    public static void init(Context context) {
        Api.context = context;
        Api.blockController = new BlockController(context);
    }

    /**
     * Retrieve the (non-revoked) public keys of a user
     * @param user the user of whom the key is
     * @return a list of public keys as strings
     */
    public static List<String> userGetKeys(String user) {
        List<Block> blocks = blockController.getBlocks(user);
        List<String> keys = new ArrayList<String>();

        for (Block block : blocks)
            keys.add(block.getPublicKey());

        return keys;
    }
}
