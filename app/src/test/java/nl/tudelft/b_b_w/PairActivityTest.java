package nl.tudelft.b_b_w;

//import android.support.test.filters.MediumTest;
//import android.support.test.runner.AndroidJUnit4;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import nl.tudelft.b_b_w.view.FriendsPageActivity;
import nl.tudelft.b_b_w.view.PairActivity;

import static junit.framework.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

//import android.test.mock.MockContext;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
//@Config(constants = BuildConfig.class,sdk= 21,  manifest = "src/main/AndroidManifest.xml")
public class PairActivityTest {


    private PairActivity pairActivity;

    @Before
    public void setup() {
        pairActivity = new PairActivity();
        pairActivity.onCreate(null);

    }

    @Test
    public void testCreateLayOut(){


        //does not work
        assertEquals(R.id.pair_layout_root, shadowOf(pairActivity).getContentView().getId());


    }


    @Test
    public void clickTSstartsFriendsPage(){
        PairActivity pairActivity = Robolectric.setupActivity(PairActivity.class);
        pairActivity.findViewById(R.id.testSubject1Button).performClick();

        Intent expect = new Intent(pairActivity, FriendsPageActivity.class);
     //   assertThat(shadowOf(pairActivity).getNextStartedActivity()).isEqualTo(expect);
    }




//
//    @Rule
//    public ActivityTestRule<PairActivity> rule = new ActivityTestRule<PairActivity>(PairActivity.class);
//
//    @Test
//    public void ensureViewIsPresent() throws Exception {
//        PairActivity pairActivity = rule.getActivity();
//        View textViewById = pairActivity.findViewById(R.id.textView3);
//        assertThat(textViewById, notNullValue());
//        assertThat(textViewById, instanceOf(TextView.class));
//
//
//    }
//
//    @Test
//    public void testGoingToFriendsPage() throws Exception {
//        PairActivity pairActivity = rule.getActivity();
//        View button = pairActivity.findViewById(R.id.testSubject1Button);
//
//
//    }

}
