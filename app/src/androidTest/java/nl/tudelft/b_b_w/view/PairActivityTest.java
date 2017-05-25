package nl.tudelft.b_b_w.view;

import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import nl.tudelft.b_b_w.R;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class PairActivityTest {


    @Rule
    public ActivityTestRule<PairActivity> rule = new ActivityTestRule<PairActivity>(PairActivity.class);

    @Test
    public void ensureViewIsPresent() throws Exception {
        PairActivity pairActivity = rule.getActivity();
        View textViewById = pairActivity.findViewById(R.id.textView3);
        assertThat(textViewById, notNullValue());
        assertThat(textViewById, instanceOf(TextView.class));

    }

}
