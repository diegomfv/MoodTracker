package com.example.android.moodtracker.activitiesrest;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import com.example.android.moodtracker.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by Diego Fajardo on 28/03/2018.
 */
public class MoodHistoryTest {

    @Rule
    public ActivityTestRule<MoodHistory> moodHistoryActivityTestRule =
            new ActivityTestRule<MoodHistory>(MoodHistory.class);

    private MoodHistory mActivity = null;

    Instrumentation.ActivityMonitor monitor =
            getInstrumentation().addMonitor(
                    PieChartActivity.class.getName(),
                    null,
                    false);


    @Before
    public void setUp() throws Exception {

        mActivity = moodHistoryActivityTestRule.getActivity();

    }

    @Test
    public void testLaunchOfSecondActivityOnButtonClick() {

        //View view1 = mActivity.findViewById(R.id.see_pie_chart);
        //assertNotNull(view1);

        /** We first check thay the button is not null */

        assertNotNull(mActivity.findViewById(R.id.see_pie_chart));

        /** We then perform a click on it */

        onView(withId(R.id.see_pie_chart)).perform(click());

        /** The monitor returns the second activity (the one launched after the click when
         * the view is clicked) */

        Activity pieChartActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);

        /** We check that the called activity is not null */

        assertNotNull(pieChartActivity);

        pieChartActivity.finish();

    }


    @After
    public void tearDown() throws Exception {


    }

}