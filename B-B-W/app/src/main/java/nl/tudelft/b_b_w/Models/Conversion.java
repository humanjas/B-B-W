package nl.tudelft.b_b_w.Models;

import java.security.MessageDigest;

/**
 * Class to convert values into a hashed value
 * Created by Ashay on 01/05/2017.
 */

public class Conversion {

    // All Static variables
    private static String CURRENT_BLOCK_HASH;
    // Variables which we need to create a hashed key
    private static String KEY_OWNER;
    private static String KEY_PUBLIC_KEY;
    private static String PREVIOUS_BLOCK_HASH;
    private static String CONTACT_BLOCK_HASH;



    /**
     * Instantiating the necessary variables
     * @param pk PublicKey of the block
     * @param owner Owner of the block
     */
    public Conversion(String owner, String pk, String previousBlockHash, String contactBlockHash) {
        KEY_OWNER = owner;
        KEY_PUBLIC_KEY = pk;
        PREVIOUS_BLOCK_HASH = previousBlockHash;
        CONTACT_BLOCK_HASH = contactBlockHash;
    }

    /**
     * Method for converting the PK, Owner, its previous block's Hash  and its new contact hash to a hashed key
     * with a SHA-256 conversion
     * @return the Hashed Key
     */
    public void hashKey() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String text = KEY_OWNER+KEY_PUBLIC_KEY+PREVIOUS_BLOCK_HASH+CONTACT_BLOCK_HASH;
        md.update(text.getBytes("UTF-8"));
        byte[] digest = md.digest();
        CURRENT_BLOCK_HASH = String.format("%064x", new java.math.BigInteger(1, digest));
    }

    /**
     * Default getter for the HashedPK
     * @return the HashedPK
     */
    public static String getHash() {
        return CURRENT_BLOCK_HASH;
    }
}
