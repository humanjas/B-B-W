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
import nl.tudelft.b_b_w.model.BlockFactory;
import nl.tudelft.b_b_w.model.User;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Test the API
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,sdk= 21,  manifest = "src/main/AndroidManifest.xml")
public class ApiUnitTest {
    private BlockController bc;
    private User userA, userB, userC, userD, userE;

    /**
     * User A has one public key, user B two
     * The blockchain of each user:
     * A: add pkA
     * B: add pkA
     * C: empty
     * D: add pkA, revoke pkA
     * E: add pkE, revoke pkE
     * F: add pkF1, add pkF2
     * G: add pkF1, add pkF2, add pkG
     * All is tested from viewpoint of A.
     */
    @Before
    public void setUp() {
        bc = new BlockController(RuntimeEnvironment.application);
        Api.init(RuntimeEnvironment.application);

        userA = Api.createUser("Antro", "NL81INGB0000000000");
        userB = Api.createUser("Besse", "NL81INGB0000000000");
        userC = Api.createUser("Cacao", "NL81INGB0000000000");
        userD = Api.createUser("Draffyg", "NL81INGB0000000000");
        userE = Api.createUser("Erwti", "NL81INGB0000000000");

        // public static Block getBlock(String type, String _owner, String _ownHash, String _previousHashChain, String _previousHashSender, String _publicKey, String _iban, int _trustValue) throws IllegalArgumentException {
        // public static Block getBlock(User owner, User user, String ownHash, String previousHashChain, String previousHashSender, String publicKey, boolean revoke) {

        // A: add A
        bc.addBlock(BlockFactory.getBlock("BLOCK", userA.getName(), "roothashA", "prevhashchain", "root", "pkroot", "NL81...", 0));
        bc.addBlock(BlockFactory.getBlock(userA, userA, "hashA", "roothash", "N/A", "pka", false));

        // B: add A
        bc.addBlock(BlockFactory.getBlock("BLOCK", userB.getName(), "roothashB", "prevhashchain", "root", "pkroot", "NL81...", 0));
        bc.addBlock(BlockFactory.getBlock(userB, userA, "hashB", "roothashB", "roothashA", "pka", false));

        // C
        bc.addBlock(BlockFactory.getBlock("BLOCK", userC.getName(), "roothashC", "prevhashchain", "root", "pkroot", "NL81...", 0));

        // E: add A, revoke A
        bc.addBlock(BlockFactory.getBlock("BLOCK", userE.getName(), "roothashE", "prevhashchain", "root", "pkroot", "NL81...", 0));
        bc.addBlock(BlockFactory.getBlock(userE, userA, "hashE1", "roothashE", "roothashA", "pka", false));
        bc.addBlock(BlockFactory.getBlock(userE, userA, "hashE2", "hashE1", "roothashA", "pka", true));

        // F: add pkf1
//        bc.addBlock(BlockFactory.getBlock("BLOCK", userE.getName(), "roothash", "prevhashchain", "root", "pkroot", "NL81...", 0));
 //       bc.addBlock(BlockFactory.getBlock(userE, userA, "hashA", "roothash", "N/A", "pka", false));
  //      bc.addBlock(BlockFactory.getBlock(userE, userA, "hashA", "roothash", "N/A", "pka", true));

        // ERROR IS: ALLES HEEFT N/A MAAR MOET ALLEEN ROOT ZIJN
        // user A has pka
    }


    /**
     * Add one own key
     */
    @Test
    public void ownKey() {
        List<String> keysA = Api.getUserKeys(userA, userA);
        List<String> expectedA = new ArrayList<String>();
        expectedA.add("pka");
        assertEquals(expectedA, keysA);
    }

    /**
     * Add key of another user
     */
    @Test
    public void otherKey() {
        // is the other key there
        List<String> keysA = Api.getUserKeys(userB, userA);
        List<String> expectedA = new ArrayList<String>();
        expectedA.add("pka");
        assertEquals(expectedA, keysA);

        // does it really filter
        List<String> keysB = Api.getUserKeys(userB, userB);
        List<String> expectedB = new ArrayList<String>();
        assertTrue(keysB.isEmpty());
    }

    /**
     * User with one key of another user that is revoked
     */
    @Test
    public void revokedKey() {
        List<String> keys = Api.getUserKeys(userE, userA);
        assertTrue(keys.isEmpty());
    }

    /**
     * User without keys
     */
    @Test
    public void userWithoutKeys() {
        List<String> keys = Api.getUserKeys(userC, userA);
        assertTrue(keys.isEmpty());
    }

    /**
     * User with multiple keys
     */
//    @Test
//    public void userMultipleKeys() {
//        List<String> keys = Api.getUserKeys(userA, userD);
//        Collections.sort(keys);
//        List<String> expected = new ArrayList<>();
//        expected.add("pkd1");
//        expected.add("pkd2");
//        expected.add("pkd3");
//        assertEquals(expected, keys);
//    }
}

