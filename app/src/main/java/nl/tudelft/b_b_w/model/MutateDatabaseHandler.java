package nl.tudelft.b_b_w.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Class to create and handle the Database for mutation requests
 */

public class MutateDatabaseHandler extends AbstractDatabaseHandler {

    /**
     * Constructor
     * creates a database connection
     *
     * @param context given context
     */
    public MutateDatabaseHandler(Context context) {
        super(context);
    }

    /**
     * Adds a block to the blockchain
     *
     * @param block the block you want to add
     */
    public void addBlock(Block block) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String owner = block.getOwner();
        values.put(KEY_OWNER, owner);
        values.put(KEY_SEQ_NO, lastSeqNumberOfChain(owner) + 1);
        values.put(KEY_OWN_HASH, block.getOwnHash());
        values.put(KEY_PREV_HASH_CHAIN, block.getPreviousHashChain());
        values.put(KEY_PREV_HASH_SENDER, block.getPreviousHashSender());
        values.put(KEY_IBAN_KEY, block.getIban());
        values.put(KEY_PUBLIC_KEY, block.getPublicKey());
        values.put(KEY_TRUST_VALUE, block.getTrustValue());
        values.put(KEY_REVOKE, block.isRevoked());

        // Inserting Row
        long res = db.insert(TABLE_NAME, null, values);
        if (res == -1) throw new RuntimeException("Block cannot be added - " + block.toString());
        db.close(); // Closing database connection
    }

    /**
     * updateBlock function
     * updates the values of the block
     * @param block block that needs to be updated
     * @throws RuntimeException if the block cannot be updated
     */
    public void updateBlock(Block block) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_OWNER, block.getOwner());
        values.put(KEY_SEQ_NO, block.getSequenceNumber());
        values.put(KEY_OWN_HASH, block.getOwnHash());
        values.put(KEY_PREV_HASH_CHAIN, block.getPreviousHashChain());
        values.put(KEY_PREV_HASH_SENDER, block.getPreviousHashSender());
        values.put(KEY_IBAN_KEY, block.getIban());
        values.put(KEY_PUBLIC_KEY, block.getPublicKey());
        values.put(KEY_REVOKE, block.isRevoked());
        values.put(KEY_TRUST_VALUE, block.getTrustValue());

        // updating row
        int result = db.update(TABLE_NAME,
                values,
                KEY_OWNER + " = ? AND " + KEY_PUBLIC_KEY + " = ? AND " + KEY_SEQ_NO + " = ?",
                new String[] {
                        block.getOwner(),
                        block.getPublicKey(),
                        String.valueOf(block.getSequenceNumber()),
                });
        if (result == -1) throw new RuntimeException("Block cannot be updated - " + block.toString());
        db.close(); // Closing database connection
    }
}
