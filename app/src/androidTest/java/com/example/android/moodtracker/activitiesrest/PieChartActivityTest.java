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
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Diego Fajardo on 29/03/2018.
 */
public class PieChartActivityTest {

    @Rule
    public ActivityTestRule<PieChartActivity> pieChartActivityActivityTestRule =
            new ActivityTestRule<PieChartActivity>(PieChartActivity.class);

    private PieChartActivity mActivity = null;

    Instrumentation.ActivityMonitor moodHistoryMonitor =
            getInstrumentation().addMonitor(
                    MoodHistory.class.getName(),
                    null,
                    false);


    @Before
    public void setUp() throws Exception {

        mActivity = pieChartActivityActivityTestRule.getActivity();

    }

    @Test
    public void testLaunchMoodHistoryOnBackArrowButtonClick () {

        onView(withContentDescription(R.string.go_back_mood_history)).perform(click());

        Activity moodHistory = getInstrumentation().waitForMonitorWithTimeout(moodHistoryMonitor, 5000);

        assertNotNull(moodHistory);

        moodHistory.finish();

    }

    @Test
    public void testCheckThatPieCanMoveWhenSwipe () {

        onView(withId(R.id.pie_chart)).perform(swipeRight()).check(matches(isDisplayed()));

    }

    @After
    public void tearDown() throws Exception {

        mActivity = null;

    }

}