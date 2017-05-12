package nl.tudelft.b_b_w.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to create and handle the Database.
 * Created by jasper on 30/04/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler _instance;

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "blockChain";

    // Table name
    private static final String TABLE_NAME = "blocks";

    // Contacts Table Columns names
    private static final String KEY_OWNER = "owner";
    private static final String KEY_SEQ_NO = "sequence_number";
    private static final String KEY_PREV_HASH = "previous_hash";
    private static final String KEY_PUBLIC_KEY = "public_key";
    private static final String KEY_REVOKE = "revoke";
    private static final String KEY_CREATED_AT = "created_at";

    // Persistence helpers
    final String _columns = KEY_OWNER + ", "
                + KEY_SEQ_NO + ", "
                + KEY_PREV_HASH + ", "
                + KEY_PUBLIC_KEY + ", "
                + KEY_REVOKE + ", "
                + KEY_CREATED_AT;
    final String[] _columns2 = new String[] {
            KEY_OWNER, KEY_SEQ_NO, KEY_PREV_HASH, KEY_PUBLIC_KEY, KEY_REVOKE
    };
    final String _header = "SELECT " + _columns + " FROM "+ TABLE_NAME +";";

    /**
     * Constructor
     * creates a database connection
     * @param context given context
     */
    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Singleton for creating only on instance of the database
     * @param context context of the current state of the database
     * @return the current state of the database
     */
    public static synchronized DatabaseHandler getInstance(Context context) {
        if (_instance == null) _instance = new DatabaseHandler(context);
        return _instance;
    }

    /**
    * Called when the database is created for the first time. This is where the
    * creation of tables and the initial population of the tables should happen.
    *
    * @param db The database.
    */
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_BLOCKS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + KEY_OWNER + " TEXT NOT NULL,"
                + KEY_SEQ_NO + " INTEGER NOT NULL,"
                + KEY_PREV_HASH + " TEXT NOT NULL,"
                + KEY_PUBLIC_KEY + " TEXT NOT NULL,"
                + KEY_REVOKE + " BOOLEAN DEFAULT FALSE NOT NULL,"
                + KEY_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,"
                + " PRIMARY KEY (owner, public_key, sequence_number)"
                + ")";
        final String CREATE_OPTION_TABLE = "CREATE TABLE option(key TEXT PRIMARY KEY, value BLOB);"
                + "INSERT INTO option(key, value) "
                + "VALUES('database_version', '" + DATABASE_VERSION + "');";
        db.execSQL(CREATE_BLOCKS_TABLE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String upgrade_script = "DROP TABLE IF EXISTS "+ TABLE_NAME +";"
                +"DROP TABLE IF EXISTS option;";

        // TODO: check if the db version is lower than the latest

        db.execSQL(upgrade_script);
    }

    /**
     * Adds a block to the blockchain
     * @param block the block you want to add
     */
    public void addBlock(Block block) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_OWNER, block.getOwner());
        values.put(KEY_SEQ_NO, block.getSequence_number());
        values.put(KEY_PREV_HASH, block.getPrevious_hash());
        values.put(KEY_PUBLIC_KEY, block.getPublic_key());
        values.put(KEY_REVOKE, block.isRevoked());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Method to get a specific block
     * @param owner The owner of the block you want
     * @param publicKey The owner of the block you want
     * @param sequenceNumber The number of the block in the sequence
     * @return The block you were searching for
     */
    public Block getBlock(String owner, String publicKey, int sequenceNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                _columns2,
                KEY_OWNER + " = ? AND " + KEY_PUBLIC_KEY + " = ? AND " + KEY_SEQ_NO + " = ?",
                new String[] {
                        owner, publicKey, String.valueOf(sequenceNumber)
                }, null, null, null, null);

        if (cursor.getCount() < 1) return null;

        cursor.moveToFirst();

        Block block = new Block(
                cursor.getString(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4) > 0
        );

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
     * @param owner the owner of the block you want
     * @param publicKey the publickey of the block you want
     * @param sequenceNumber The number of the block in the sequence
     * @return true if the blockchain contains the specified block, otherwise false
     */
    public boolean containsBlock(String owner, String publicKey, int sequenceNumber) {
        return this.getBlock(owner, publicKey, sequenceNumber) != null;
    }

    /**
     * Method to check whether the blockchain contains a specific block,
     * uses the getBlock method to avoid duplication
     * @param owner the owner of the block you want
     * @param publicKey the publickey of the block you want
     * @return true if the blockchain contains the specified block, otherwise false
     */
    public boolean containsBlock(String owner, String publicKey) {
        return this.getLatestBlock(owner, publicKey) != null;
    }

    /**
     * Method to get the latest sequence number of a block with a
     * certain owner and publickey
     * @param owner the owner of the block
     * @param publicKey the owner of the sequence number
     * @return the latest sequence number of the specified block
     */
    public int getLatestSeqNum(String owner, String publicKey) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_NAME,
                new String[] {"MAX(" + KEY_SEQ_NO + ")"},
                KEY_OWNER + " = ? AND " + KEY_PUBLIC_KEY + " = ?",
                new String[] {
                        owner, publicKey
                }, null, null, null, null);
        try {
            c.moveToFirst();
            return c.getInt(0);
        } finally {
            db.close();
            c.close();
        }
    }

    /**
     * Method to get the latest block in a blockchain using the
     * owner and publickey
     * @param owner the owner of the block
     * @param publicKey the publickey of the block
     * @return the latest block
     */
    public Block getLatestBlock(String owner, String publicKey) {
        int maxSeqNum = this.getLatestSeqNum(owner, publicKey);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                _columns2,
                KEY_OWNER + " = ? AND " + KEY_PUBLIC_KEY + " = ? AND " + KEY_SEQ_NO + " = ?",
                new String[] {
                        owner, publicKey, String.valueOf(maxSeqNum)
                }, null, null, null, null);

        if (cursor.getCount() < 1) return null;

        cursor.moveToFirst();

        Block block = new Block(
                cursor.getString(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4) > 0
        );

        // Close database connection
        db.close();

        // Close cursor
        cursor.close();

        // return block
        return block;
    }

    /**
     * Method to get the block after a specified block
     * @param owner the owner of the block before
     * @param publicKey the owner of the block before
     * @param sequenceNumber the sequencenumber of the block before
     * @return the block after the specified one
     */
    public Block getBlockAfter(String owner, String publicKey, int sequenceNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                _columns2,
                KEY_OWNER + " = ? AND " + KEY_PUBLIC_KEY + " = ? AND " + KEY_SEQ_NO + " > ?",
                new String[] {
                        owner, publicKey, String.valueOf(sequenceNumber)
                }, null, null, null, null);

        if (cursor.getCount() < 1) return null;

        cursor.moveToFirst();

        Block block = new Block(
                cursor.getString(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4) > 0
        );

        // Close database connection
        db.close();

        // Close cursor
        cursor.close();

        // return block
        return block;
    }

    /**
     * Method to get the block before a specified block
     * @param owner the owner of the block after
     * @param publicKey the owner of the block after
     * @param sequenceNumber the sequencenumber of the block after
     * @return the block before the specified one
     */
    public Block getBlockBefore(String owner, String publicKey, int sequenceNumber) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                _columns2,
                KEY_OWNER + " = ? AND " + KEY_PUBLIC_KEY + " = ? AND " + KEY_SEQ_NO + " < ?",
                new String[] {
                        owner, publicKey, String.valueOf(sequenceNumber)
                }, null, null, null, null);

        if (cursor.getCount() < 1) return null;

        cursor.moveToFirst();

        Block block = new Block(
                cursor.getString(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4) > 0
        );

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
     * @return List of all the blocks
     */
    public List<Block> getAllBlocks() {
        List<Block> blocks = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Block block = new Block(
                        cursor.getString(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4) > 0
                );
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
