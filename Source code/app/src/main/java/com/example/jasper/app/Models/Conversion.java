package com.example.jasper.app.Models;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

/**
 * Class to convert values into a hashed value
 * Created by Ashay on 01/05/2017.
 */

public class Conversion {

    // All Static variables
    private static String HASHED_PK;
    // Variables which we need to create a hashed key
    private static String KEY_OWNER;
    private static String KEY_PUBLIC_KEY;


    /**
     * Instantiating the necessary variables
     * @param pk PublicKey of the block
     * @param owner Owner of the block
     */
    public Conversion(String pk, String owner) {
        KEY_OWNER = owner;
        KEY_PUBLIC_KEY = pk;
    }

    /**
     * Method for converting the PK and Owner to a hashed key
     * with a SHA-256 conversion
     * @return the Hashed Key
     */
    public void hashKey() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String text = KEY_OWNER+KEY_PUBLIC_KEY;
        md.update(text.getBytes("UTF-8"));
        byte[] digest = md.digest();
        HASHED_PK = String.format("%064x", new java.math.BigInteger(1, digest));
    }

    /**
     * Default getter for the HashedPK
     * @return the HashedPK
     */
    public static String getHashedPk() {
        return HASHED_PK;
    }
}
