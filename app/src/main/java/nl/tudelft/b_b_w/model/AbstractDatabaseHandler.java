package nl.tudelft.b_b_w.model;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * AbstractDatabaseHandler class
 * Parent class for all database handlers
 */
public abstract class AbstractDatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "blockChain";

    // Table name
    public static final String TABLE_NAME = "blocks";

    // Contacts Table Columns names
    public static final String KEY_OWNER = "owner";
    public static final String KEY_SEQ_NO = "sequenceNumber";
    public static final String KEY_PREV_HASH_SENDER = "previousHashSender";
    public static final String KEY_OWN_HASH = "ownHash";
    public static final String KEY_PREV_HASH_CHAIN = "previousHashChain";
    public static final String KEY_PUBLIC_KEY = "publicKey";
    public static final String KEY_IBAN_KEY = "iban";
    public static final String KEY_TRUST_VALUE = "trustValue";
    public static final String KEY_REVOKE = "revoke";
    public static final String KEY_CREATED_AT = "created_at";

    // Persistence helpers
    public final String[] _columns = new String[]{
            KEY_OWNER, KEY_SEQ_NO, KEY_OWN_HASH, KEY_PREV_HASH_CHAIN, KEY_PREV_HASH_SENDER,
            KEY_PUBLIC_KEY, KEY_IBAN_KEY, KEY_TRUST_VALUE, KEY_REVOKE
    };

    /**
     * Constructor
     * creates a database connection
     *
     * @param context given context
     */
    public AbstractDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
                + KEY_OWN_HASH + " TEXT NOT NULL,"
                + KEY_PREV_HASH_CHAIN + " TEXT NOT NULL,"
                + KEY_PREV_HASH_SENDER + " TEXT NOT NULL,"
                + KEY_PUBLIC_KEY + " TEXT NOT NULL,"
                + KEY_IBAN_KEY + " TEXT NOT NULL,"
                + KEY_TRUST_VALUE + " INTEGER NOT NULL,"
                + KEY_REVOKE + " BOOLEAN DEFAULT FALSE NOT NULL,"
                + KEY_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,"
                + " PRIMARY KEY (owner, publicKey, sequenceNumber)"
                + ")";
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
        final String upgrade_script = "DROP TABLE IF EXISTS " + TABLE_NAME + ";"
                + "DROP TABLE IF EXISTS option;";

        // TODO: check if the db version is lower than the latest

        db.execSQL(upgrade_script);
    }

    /**
     * Find the last sequence number of the chain of a specific owner
     *
     * @param owner the owner of the chain which you want to get the last sequence number from
     *              There is no need for a public key because each block of the chain is
     *              supposed to have different public key from the contact
     */
    public int lastSeqNumberOfChain(String owner) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME,
                new String[]{"MAX(" + KEY_SEQ_NO + ")"},
                KEY_OWNER + " = ? ",
                new String[]{
                        owner
                }, null, null, null, null);


        try {
            c.moveToFirst();
            return c.getInt(0);
        } finally {
            c.close();
        }

    }

}
