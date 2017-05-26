package nl.tudelft.b_b_w.model;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to create and handle the Database for get requests
 */

public class GetDatabaseHandler extends AbstractDatabaseHandler {

    /**
     * Constructor
     * creates a database connection
     *
     * @param context given context
     */
    public GetDatabaseHandler(Context context) {
        super(context);
    }

    /**
     * Method to get a specific block
     *
     * @param owner          The owner of the block you want
     * @param publicKey      The owner of the block you want
     * @param sequenceNumber The number of the block in the sequence
     * @return The block you were searching for
     */
    Block getBlock(String owner, String publicKey, int sequenceNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                _columns,
                KEY_OWNER + " = ? AND " + KEY_PUBLIC_KEY + " = ? AND " + KEY_SEQ_NO + " = ?",
                new String[]{
                        owner, publicKey, String.valueOf(sequenceNumber)
                }, null, null, null, null);

        //When returning an exception the whole program crashes,
        //but we want to preserve the state.
        if (cursor.getCount() < 1) return null;
        cursor.moveToFirst();

        final Block returnBlock = cursorToBlock(cursor);

        // Close database connection
        db.close();

        // Close cursor
        cursor.close();

        // return block
        return returnBlock;
    }

    /**
     * Method to check whether the blockchain contains a specific block,
     * uses the getBlock method to avoid duplication
     *
     * @param owner          the owner of the block you want
     * @param publicKey      the publickey of the block you want
     * @param sequenceNumber The number of the block in the sequence
     * @return true if the blockchain contains the specified block, otherwise false
     */
    boolean containsBlock(String owner, String publicKey, int sequenceNumber) {
        return this.getBlock(owner, publicKey, sequenceNumber) != null;
    }

    /**
     * Method to get the latest sequence number of a block with a
     * certain owner and publickey
     *
     * @param owner     the owner of the block
     * @param publicKey the owner of the sequence number
     * @return the latest sequence number of the specified block
     */
    int getLatestSeqNum(String owner, String publicKey) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{"MAX(" + KEY_SEQ_NO + ")"},
                KEY_OWNER + " = ? AND " + KEY_PUBLIC_KEY + " = ?",
                new String[]{
                        owner, publicKey
                }, null, null, null, null);

        if (cursor.getCount() < 1) return -1;
        cursor.moveToFirst();

        int result = cursor.getInt(0);
        db.close();
        cursor.close();

        return result;
    }

    /**
     * Method to get the latest block in a blockchain using the
     * owner and publickey
     *
     * @param owner the owner of the block
     * @return the latest block
     */
    public Block getLatestBlock(String owner) {
        int maxSeqNum = this.lastSeqNumberOfChain(owner);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                _columns,
                KEY_OWNER + " = ? AND " + KEY_SEQ_NO + " = ?",
                new String[]{
                        owner, String.valueOf(maxSeqNum)
                }, null, null, null, null);

        //When returning an exception the whole program crashes,
        //but we want to preserve the state.
        if (cursor.getCount() < 1) return null;
        cursor.moveToFirst();

        final Block returnBlock = cursorToBlock(cursor);

        // Close database connection
        db.close();

        // Close cursor
        cursor.close();

        // return block
        return returnBlock;
    }

    /**
     * Method to get the block after a specified block
     *
     * @param owner          the owner of the block before
     * @param sequenceNumber the sequencenumber of the block before
     * @return the block after the specified one
     */

    Block getBlockAfter(String owner, int sequenceNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                _columns,
                KEY_OWNER + " = ? AND " + KEY_SEQ_NO + " > ?",
                new String[]{
                        owner, String.valueOf(sequenceNumber)
                }, null, null, null, null);

        if (cursor.getCount() < 1) throw new NotFoundException();
        cursor.moveToFirst();

        final Block returnBlock = cursorToBlock(cursor);

        // Close database connection
        db.close();

        // Close cursor
        cursor.close();

        // return block
        return returnBlock;
    }

    /**
     * Method to get the block before a specified block
     *
     * @param owner          the owner of the block after
     * @param sequenceNumber the sequencenumber of the block after
     * @return the block before the specified one
     */

    Block getBlockBefore(String owner, int sequenceNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                _columns,
                KEY_OWNER + " = ? AND " + KEY_SEQ_NO + " < ?",
                new String[]{
                        owner, String.valueOf(sequenceNumber)
                }, null, null, null, null);

        if (cursor.getCount() < 1) throw new NotFoundException();
        cursor.moveToFirst();

        final Block returnBlock = cursorToBlock(cursor);

        // Close database connection
        db.close();

        // Close cursor
        cursor.close();

        // return block
        return returnBlock;
    }

    /**
     * Method which puts all the blocks currently in the
     * blockchain into a list
     *
     * @param owner the owner of the blocks that are going to be fetched
     * @return List of all the blocks
     */
    public List<Block> getAllBlocks(String owner) {
        List<Block> blocks = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                _columns,
                KEY_OWNER + " = ?",
                new String[]{
                        owner
                }, null, null, null, null);


        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                final Block block = cursorToBlock(cursor);
                blocks.add(block);
            } while (cursor.moveToNext());
        }

        // Close database connection
        db.close();

        // Close cursor
        cursor.close();

        return blocks;
    }

    /**
     * getByHashOwner function
     * Gets a block by its hash and owner value
     * @param hash given hash value
     * @param owner given owner value
     * @return block that matches it
     */
     Block getByHashOwner(String hash, String owner) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                _columns,
                KEY_OWNER + " = ? AND " + KEY_OWN_HASH + " = ?",
                new String[]{
                        owner, hash
                }, null, null, null, null);

        // Preserves the state
        if (cursor.getCount() < 1) return null;
        cursor.moveToFirst();

        Block returnBlock = cursorToBlock(cursor);

        // Close database connection
        db.close();

        // Close cursor
        cursor.close();

        // return block
        return returnBlock;
    }

    /**
     * cursorToBlock function
     * Converts a cursor to a block
     * @param cursor given cursor
     * @return generated block
     */
    private Block cursorToBlock(Cursor cursor) {
        final String blockType = (cursor.getInt(8) > 0) ?  "REVOKE" : "BLOCK";
        return BlockFactory.getBlock(
                blockType,
                cursor.getString(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getInt(7));
    }
}
