package com.example.android.moodtracker.activitiesrest;

import android.app.Activity;
import android.app.Instrumentation;
import android.database.Cursor;
import android.support.test.rule.ActivityTestRule;

import com.example.android.moodtracker.R;
import com.example.android.moodtracker.activitiesmoodstate.MainActivity;
import com.example.android.moodtracker.database.DatabaseContract;
import com.example.android.moodtracker.database.DatabaseHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;

import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

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

    DatabaseHelper dbH;
    Cursor mCursor;


    @Before
    public void setUp() throws Exception {

        mActivity = moodHistoryActivityTestRule.getActivity();

        dbH = new DatabaseHelper(mActivity);
        mCursor = dbH.getAllDataFromDaysTable();

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

    @Test
    public void testCommentButtonIsShownInRecyclerViewAndWorks () {

        onView(withContentDescription(R.string.go_back_main_activity)).perform(click());

        onView(withId(R.id.custom_note_button_main)).perform(click());

        onView(withText(R.string.alert_dialog_box_ok)).check(matches(isDisplayed()));
        onView(withText(R.string.alert_dialog_box_cancel)).check(matches(isDisplayed()));

        onView(withId(R.id.alertDialogComment)).perform(typeText("This is a test"));

        onView(withText(R.string.alert_dialog_box_ok)).perform(click());

        onView(withId(R.id.mood_history_button_main)).perform(click());

        onView(withId(R.id.read_note_button)).perform(click());

        mCursor.moveToFirst();
        onView(withText(mCursor.getString(mCursor.getColumnIndex(DatabaseContract.Database.COMMENT))))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

    }

    @After
    public void tearDown() throws Exception {

        dbH = null;
        mCursor = null;

        mActivity = null;

    }

}