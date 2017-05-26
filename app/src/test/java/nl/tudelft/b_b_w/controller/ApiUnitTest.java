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
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static nl.tudelft.b_b_w.controller.Api.createUser;

/**
 * Test the API.
 * TODO revoke does not work yet, Luat is working on it. Also extra unittests.
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

        userA = createUser("Antro", "NL81INGB0000000000");
        userB = createUser("Besse", "NL81INGB0000000000");
        userC = createUser("Cacao", "NL81INGB0000000000");
        userD = createUser("Draffyg", "NL81INGB0000000000");
        userE = createUser("Erwti", "NL81INGB0000000000");

        // public static Block getBlock(String type, String _owner, String _ownHash, String _previousHashChain, String _previousHashSender, String _publicKey, String _iban, int _trustValue) throws IllegalArgumentException {
        // public static Block getBlock(User owner, User user, String ownHash, String previousHashChain, String previousHashSender, String publicKey, boolean revoke) {

        // A: add A
        bc.addBlock(BlockFactory.getBlock("BLOCK", userA.getName(), "roothashA", "prevhashchain", "root", "pkroot", "NL81...", 0));
        bc.addBlock(BlockFactory.getBlock("BLOCK", userA.getName(), "hashA", "roothash", "N/A", "pka", "NL81...", 0));

        // B: add A
        bc.addBlock(BlockFactory.getBlock("BLOCK", userB.getName(), "roothashB", "prevhashchain", "root", "pkroot", "NL81...", 0));
        bc.addBlock(BlockFactory.getBlock("BLOCK", userB.getName(), "hashB", "roothashB", "roothashA", "pka", "NL81...", 0));

        // C
        bc.addBlock(BlockFactory.getBlock("BLOCK", userC.getName(), "roothashC", "prevhashchain", "root", "pkroot", "NL81...", 0));

        // E: add A, revoke A
        bc.addBlock(BlockFactory.getBlock("BLOCK", userE.getName(), "roothashE", "prevhashchain", "root", "pkroot", "NL81...", 0));
        bc.addBlock(BlockFactory.getBlock("BLOCK", userE.getName(), "hashE1", "roothashE", "roothashA", "pka", "NL81...", 0));
        bc.addBlock(BlockFactory.getBlock("REVOKE", userE.getName(), "hashE2", "hashE1", "roothashA", "pka", "NL81...", 0));
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
     * User without keys
     */
    @Test
    public void userWithoutKeys() {
        List<String> keys = Api.getUserKeys(userC, userA);
        assertTrue(keys.isEmpty());
    }

    ///////////////////////////////////////////////////////////// WRITE UNIT TESTS

    /**
     * Add key to empty user C.
     */
    @Test
    public void addOwnKey() {
        Api.addKey(userC, userC, "pkc");
        List<String> keys = Api.getUserKeys(userC, userC);
        List<String> expected = new ArrayList<String>();
        expected.add("pkc");
        assertEquals(expected, keys);
    }

    /**
     * Add key to of A to C.
     */
    @Test
    public void addOtherKey() {
        Api.addKey(userC, userA, "pka");
        List<String> keys = Api.getUserKeys(userC, userA);
        List<String> expected = new ArrayList<String>();
        expected.add("pka");
        assertEquals(expected, keys);
    }

    ///////////////////////////////////////////////////////////// USER UNIT TESTS
    @Test
    public void uniqueUsers() {
        User a,b,c;
        a = Api.createUser("A", "IBAN1");
        b = Api.createUser("B", "IBAN2");
        c = Api.createUser("C", "IBAN3");
        assertNotSame(a.getID(), b.getID());
        assertNotSame(b.getID(), c.getID());
    }
}

