package nl.tudelft.b_b_w.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import nl.tudelft.b_b_w.BuildConfig;
import nl.tudelft.b_b_w.model.Block;
import nl.tudelft.b_b_w.model.BlockFactory;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,sdk= 21,  manifest = "src/main/AndroidManifest.xml")
public class ApiUnitTest {
    private BlockController bc;
    private final String userA = "Antro";
    private final String userB = "Besse";
    private final String keyA = "pka";
    private final String keyB1 = "pkb1";
    private final String keyB2 = "pkb2";

    /**
     * User A has one public key, user B two
     * The blockchain of each user:
     * A:   approves key A, approves key B1
     * B:   approves key B1, approves key A, approves key B2, revokes B1
     */
    @Before
    public void setUp() {
        bc = new BlockController(RuntimeEnvironment.application);
        Api.init(RuntimeEnvironment.application);

        // user A
        Block aa = BlockFactory.getBlock("BLOCK", userA, 1, "ownHashA1", "previousHashChainA1",
                "previousHashSenderA", keyA);
        Block ab1 = BlockFactory.getBlock("BLOCK", userA, 1, "ownHashA2", "previousHashChainA2",
                "previousHashSenderB1", keyB1);

        // user B
        Block ba = BlockFactory.getBlock("BLOCK", userB, 1, "ownHash", "previousHashChain",
                "previousHashSender", keyB1);
        Block bb1 = BlockFactory.getBlock("BLOCK", userB, 1, "ownHash", "previousHashChain",
                "previousHashSender", keyA);
        Block bb2 = BlockFactory.getBlock("BLOCK", userB, 1, "ownHash", "previousHashChain",
                "previousHashSender", keyB2);
        Block brb1 = BlockFactory.getBlock("REVOKE", userB, 1, "ownHash", "previousHashChain",
                "previousHashSender", keyB2);

        // add block to BlockController of A
        bc.addBlock(aa);
        bc.addBlock(ab1);
    }

    /**
     * Simple API test to obtain a list of keys from user A
     */
    @Test
    public void testGetUsers() {
        List<String> keys = Api.userGetKeys(userA);
        List<String> expectedKeys = new ArrayList<String>();
        expectedKeys.add("pka");
        expectedKeys.add("pkb1");
        assertEquals(keys, expectedKeys);
    }
}
