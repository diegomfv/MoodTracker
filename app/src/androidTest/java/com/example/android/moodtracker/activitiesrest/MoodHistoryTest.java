package com.example.android.moodtracker.activitiesrest;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.android.dx.command.Main;
import com.example.android.moodtracker.R;
import com.example.android.moodtracker.activitiesmoodstate.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by Diego Fajardo on 28/03/2018.
 */
public class MoodHistoryTest {

    @Rule
    public ActivityTestRule<MoodHistory> moodHistoryActivityTestRule =
            new ActivityTestRule<MoodHistory>(MoodHistory.class);

    private MoodHistory mActivity = null;

    Instrumentation.ActivityMonitor pieChartActivitymonitor =
            getInstrumentation().addMonitor(
                    PieChartActivity.class.getName(),
                    null,
                    false);

    Instrumentation.ActivityMonitor mainActivityMonitor =
            getInstrumentation().addMonitor(
                    MainActivity.class.getName(),
                    null,
                    false);


    @Before
    public void setUp() throws Exception {

        mActivity = moodHistoryActivityTestRule.getActivity();

    }

    @Test
    public void testLaunchOfPieChartActivityOnButtonClick () {

        mActivity.dbH.updateDataDays(5, "", 14);

        assertNotNull(mActivity.findViewById(R.id.see_pie_chart));

        onView(withId(R.id.see_pie_chart)).perform(click());

        Activity pieChartActivity = getInstrumentation().waitForMonitorWithTimeout(pieChartActivitymonitor, 5000);

        assertNotNull(pieChartActivity);

        pieChartActivity.finish();

    }

    @Test
    public void testLaunchOfMainActivityOnBackArrowButtonClick () {

        onView(withContentDescription(R.string.go_back_main_activity)).perform(click());

        Activity mainActivity = getInstrumentation().waitForMonitorWithTimeout(mainActivityMonitor, 5000);

        assertNotNull(mainActivity);

        mainActivity.finish();

    }

    @Test
    public void testDatabaseIsDeletedOnButtonClick () {

        mActivity.dbH.updateDataDaysStateInToday(3);

        assertNotNull(mActivity.findViewById(R.id.delete_comment_history));

        onView(withId(R.id.delete_comment_history)).perform(click());

        onView(withText(R.string.delete_history_positive_button)).perform(click());

        onView(withId(R.id.delete_comment_history)).perform(click()).check(matches(isDisplayed()));

    }


    @After
    public void tearDown() throws Exception {

        mActivity = null;

    }

}