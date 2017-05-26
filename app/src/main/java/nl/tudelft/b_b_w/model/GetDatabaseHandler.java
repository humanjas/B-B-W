package nl.tudelft.b_b_w.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

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
    public Block getBlock(String owner, String publicKey, int sequenceNumber) {
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

        final String blockType = (cursor.getInt(7) > 0) ?  "REVOKE" : "BLOCK";
        Block block = BlockFactory.getBlock(
                blockType,cursor.getString(0),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getInt(7));

        // Close database connection
        db.close();

        // Close cursor
        cursor.close();

        // return block
        return block;
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
    public boolean containsBlock(String owner, String publicKey, int sequenceNumber) {
        return this.getBlock(owner, publicKey, sequenceNumber) != null;
    }

    /**
     * Method to check whether the blockchain contains a specific block,
     * uses the getBlock method to avoid duplication
     *
     * @param owner     the owner of the block you want
     * @param publicKey the publickey of the block you want
     * @return true if the blockchain contains the specified block, otherwise false
     */
    public boolean containsBlock(String owner, String publicKey) {
        return this.getLatestBlock(owner) != null;
    }

    /**
     * Method to get the latest sequence number of a block with a
     * certain owner and publickey
     *
     * @param owner     the owner of the block
     * @param publicKey the owner of the sequence number
     * @return the latest sequence number of the specified block
     */
    public int getLatestSeqNum(String owner, String publicKey) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_NAME,
                new String[]{"MAX(" + KEY_SEQ_NO + ")"},
                KEY_OWNER + " = ? AND " + KEY_PUBLIC_KEY + " = ?",
                new String[]{
                        owner, publicKey
                }, null, null, null, null);

        if (c.getCount() < 1) return -1;
        c.moveToFirst();

        int result = c.getInt(0);
        db.close();
        c.close();

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

        final String blockType = (cursor.getInt(7) > 0) ?  "REVOKE" : "BLOCK";
        Block block = BlockFactory.getBlock(
                blockType,cursor.getString(0),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getInt(7));

        // Close database connection
        db.close();

        // Close cursor
        cursor.close();

        // return block
        return block;
    }

    /**
     * Method to get the block after a specified block
     *
     * @param owner          the owner of the block before
     * @param sequenceNumber the sequencenumber of the block before
     * @return the block after the specified one
     */

    public Block getBlockAfter(String owner, int sequenceNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                _columns,
                KEY_OWNER + " = ? AND " + KEY_SEQ_NO + " > ?",
                new String[]{
                        owner, String.valueOf(sequenceNumber)
                }, null, null, null, null);

        if (cursor.getCount() < 1) throw new NotFoundException();

        cursor.moveToFirst();

        final String blockType = (cursor.getInt(7) > 0) ?  "REVOKE" : "BLOCK";
        Block block = BlockFactory.getBlock(
                blockType,cursor.getString(0),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getInt(7));

        // Close database connection
        db.close();

        // Close cursor
        cursor.close();

        // return block
        return block;
    }

    /**
     * Method to get the block before a specified block
     *
     * @param owner          the owner of the block after
     * @param sequenceNumber the sequencenumber of the block after
     * @return the block before the specified one
     */

    public Block getBlockBefore(String owner, int sequenceNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                _columns,
                KEY_OWNER + " = ? AND " + KEY_SEQ_NO + " < ?",
                new String[]{
                        owner, String.valueOf(sequenceNumber)
                }, null, null, null, null);

        if (cursor.getCount() < 1) throw new NotFoundException();

        cursor.moveToFirst();

        final String blockType = (cursor.getInt(7) > 0) ?  "REVOKE" : "BLOCK";
        Block block = BlockFactory.getBlock(
                blockType,cursor.getString(0),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getInt(7));

        // Close database connection
        db.close();

        // Close cursor
        cursor.close();

        // return block
        return block;
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
                final String blockType = (cursor.getInt(7) > 0) ?  "REVOKE" : "BLOCK";
                Block block = BlockFactory.getBlock(
                        blockType,cursor.getString(0),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7));
                blocks.add(block);
            } while (cursor.moveToNext());
        }

        // Close database connection
        db.close();

        // Close cursor
        cursor.close();

        return blocks;
    }
}
