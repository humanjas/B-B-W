package nl.tudelft.b_b_w.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.tudelft.b_b_w.BuildConfig;
import nl.tudelft.b_b_w.model.Block;
import nl.tudelft.b_b_w.model.BlockFactory;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Test the API
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,sdk= 21,  manifest = "src/main/AndroidManifest.xml")
public class ApiUnitTest {
    private BlockController bc;
    private final String userA = "Antro";
    private final String userB = "Besse";
    private final String userC = "Cacao";
    private final String keyA = "pka";
    private final String keyB = "pkb";

    /**
     * User A has one public key, user B two
     * The blockchain of each user:
     * A:   adds key A, adds key B
     * B:   adds key B, revokes key B
     * C:   empty
     */
    @Before
    public void setUp() {
        bc = new BlockController(RuntimeEnvironment.application);
        Api.init(RuntimeEnvironment.application);

        // user A
        Block aa = BlockFactory.getBlock("BLOCK", userA, "ownHashA1", "previousHashChainA1",
                "previousHashSenderA", keyA, "NL81INGB0000000000");
        Block ab = BlockFactory.getBlock("BLOCK", userA, "ownHashA2", "previousHashChainA2",
                "previousHashSenderB1", keyB, "NL81INGB0000000000");

        // user B
        Block bb = BlockFactory.getBlock("BLOCK", userB, "ownHashB1", "previousHashChainB",
                "previousHashSenderA", keyB, "NL81INGB0000000000");
        Block bbr = BlockFactory.getBlock("REVOKE", userB, "ownHashB2", "previousHashChainB2",
                "previousHashSenderB1", keyB, "NL81INGB0000000000");

        // add all blocks
        bc.addBlock(aa);
        bc.addBlock(ab);
        bc.addBlock(bb);
        bc.addBlock(bbr);
    }

    /**
     * Simple API test to obtain a list of keys from user A
     */
    @Test
    public void simpleUser() {
        List<String> keys = Api.userGetKeys(userA, userA);
        List<String> expectedKeys = new ArrayList<String>();
        expectedKeys.add("pka");
        expectedKeys.add("pkb");
        Collections.sort(keys);
        assertEquals(expectedKeys, keys);
    }

    /**
     * User with one key that is revoked
     */
    @Test
    public void revokedKey() {
        List<String> keys = Api.userGetKeys(userA, userB);
        assertTrue(keys.isEmpty());
    }

    /**
     * User without keys
     */
    @Test
    public void userWithoutKeys() {
        List<String> keys = Api.userGetKeys(userA, userC);
        assertTrue(keys.isEmpty());
    }

}

