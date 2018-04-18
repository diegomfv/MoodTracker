package com.example.android.moodtracker.activitiesmoodstate;

import android.app.Activity;
import android.app.Instrumentation;
import android.database.Cursor;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.example.android.moodtracker.R;
import com.example.android.moodtracker.activitiesrest.MoodHistory;
import com.example.android.moodtracker.database.DatabaseContract;
import com.example.android.moodtracker.database.DatabaseHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Diego Fajardo on 29/03/2018.
 */
public class SadSmileyTest {

    @Rule
    public ActivityTestRule<SadSmiley> sadSmileyActivityTestRule =
            new ActivityTestRule<SadSmiley>(SadSmiley.class);

    private SadSmiley mActivity = null;

    Instrumentation.ActivityMonitor moodHistoryMonitor =
            getInstrumentation().addMonitor(
                    MoodHistory.class.getName(),
                    null,
                    false);

    Instrumentation.ActivityMonitor disappointedSmileyMonitor =
            getInstrumentation().addMonitor(
                    DisappointedSmiley.class.getName(),
                    null,
                    false);

    DatabaseHelper dbH;
    Cursor mCursor;

    @Before
    public void setUp() throws Exception {

        /** With this, we get the context! */
        mActivity = sadSmileyActivityTestRule.getActivity();

        dbH = new DatabaseHelper(mActivity);
        mCursor = dbH.getAllDataFromDaysTable();

    }

    @Test
    public void testThatViewsAreNotNull() {

        /** If we can find the views, we can conclude that the activity is launched successfully */

        View view1 = mActivity.findViewById(R.id.sad_face_button);
        View view2 = mActivity.findViewById(R.id.mood_history_button_sad);
        View view3 = mActivity.findViewById(R.id.custom_note_button_sad);

        assertNotNull(view1);
        assertNotNull(view2);
        assertNotNull(view3);

    }

    @Test
    public void testThatDisappointedSmileyIsLaunched() {

        onView(withId(R.id.container_sad))
                .perform(ViewActions.swipeLeft());

        Activity dissapointedSmiley = getInstrumentation().waitForMonitorWithTimeout(disappointedSmileyMonitor, 5000);

        assertNotNull(dissapointedSmiley);

        dissapointedSmiley.finish();

    }

    @Test
    public void testThatMoodHistoryIsLaunched() {

        View view2 = mActivity.findViewById(R.id.mood_history_button_sad);
        assertNotNull(view2);

        onView(withId(R.id.mood_history_button_sad)).perform(click());

        Activity moodHistory = getInstrumentation().waitForMonitorWithTimeout(moodHistoryMonitor, 5000);

        assertNotNull(moodHistory);

        moodHistory.finish();

    }

    @Test
    public void testThatCommentDialogIsLaunched () {

        View view3 = mActivity.findViewById(R.id.custom_note_button_sad);
        assertNotNull(view3);

        onView(withId(R.id.custom_note_button_sad)).perform(click());

        onView(withText(R.string.alert_dialog_box_ok)).check(matches(isDisplayed()));
        onView(withText(R.string.alert_dialog_box_cancel)).check(matches(isDisplayed()));

    }

    @Test
    public void testThatClickingTheFaceButtonUpdatesTheDatabase (){

        DatabaseHelper dbH = new DatabaseHelper(mActivity);
        Cursor mCursor = dbH.getAllDataFromDaysTable();

        onView(withId(R.id.sad_face_button)).perform(click());

        mCursor.moveToFirst();
        int state = mCursor.getInt(mCursor.getColumnIndex(DatabaseContract.Database.STATE_ID));

        assertTrue(state == 1);

    }

    @After
    public void tearDown() throws Exception {

        dbH.updateDataDaysStateInToday(6);

        dbH = null;
        mCursor = null;

        /** With this, we nullify the activity (that was launched) */
        mActivity = null;
    }

}