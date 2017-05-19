package nl.tudelft.b_b_w;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import nl.tudelft.b_b_w.Models.Block;
import nl.tudelft.b_b_w.Models.DatabaseHandler;

import static junit.framework.Assert.assertEquals;

public class DisplayActivityTest {
    /** Start the main activity for these tests */
    @Rule
    public ActivityTestRule<DisplayChainActivity> mActivityRule = new ActivityTestRule<>(
            DisplayChainActivity.class);

    /**
     * Verify the availability and workings of the Add block button.
     * It should go to the AddBlockActivity.
     */
    @Test
    public void displayChain() {
        DatabaseHandler handler = new DatabaseHandler(mActivityRule.getActivity());
        List<Block> blocks = handler.getAllBlocks("GENESIS"); // we do not have access to owner name so just hardcode

        // obtain all lines
        String res = mActivityRule.getActivity().findViewById(R.id.chain).toString();
        String[] lines = res.split("\n\n");

        // are all blocks there?
        assertEquals(blocks.size(), lines.length);
    }
}
