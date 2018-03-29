package com.example.android.moodtracker.activitiesmoodstate;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.example.android.moodtracker.R;
import com.example.android.moodtracker.activitiesrest.MoodHistory;

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
 * Created by Diego Fajardo on 28/03/2018.
 */


public class NormalSmileyTest {

    @Rule
    public ActivityTestRule<NormalSmiley> normalSmileyActivityTestRule =
            new ActivityTestRule<NormalSmiley>(NormalSmiley.class);

    private NormalSmiley mActivity = null;

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

    Instrumentation.ActivityMonitor mainActivityMonitor =
            getInstrumentation().addMonitor(
                    MainActivity.class.getName(),
                    null,
                    false);

    @Before
    public void setUp() throws Exception {

        mActivity = normalSmileyActivityTestRule.getActivity();

    }

    @Test
    public void testThatViewsAreNotNull() {

        View view1 = mActivity.findViewById(R.id.normal_face_button);
        View view2 = mActivity.findViewById(R.id.mood_history_button_normal);
        View view3 = mActivity.findViewById(R.id.custom_note_button_normal);

        assertNotNull(view1);
        assertNotNull(view2);
        assertNotNull(view3);

    }

    @Test
    public void testThatMainActivityIsLaunched () {

        onView(withId(R.id.container_normal))
                .perform(ViewActions.swipeLeft());

        Activity mainActivity = getInstrumentation().waitForMonitorWithTimeout(mainActivityMonitor, 5000);

        assertNotNull(mainActivity);

        mainActivity.finish();

    }

    @Test
    public void testThatDisappointedSmileyIsLaunched() {

        onView(withId(R.id.container_normal))
                .perform(ViewActions.swipeRight());

        Activity disappointedSmiley = getInstrumentation().waitForMonitorWithTimeout(disappointedSmileyMonitor, 5000);

        assertNotNull(disappointedSmiley);

        disappointedSmiley.finish();

    }

    @Test
    public void testThatMoodHistoryIsLaunched () {

        View view2 = mActivity.findViewById(R.id.mood_history_button_normal);
        assertNotNull(view2);

        onView(withId(R.id.mood_history_button_normal)).perform(click());

        Activity moodHistory = getInstrumentation().waitForMonitorWithTimeout(moodHistoryMonitor, 5000);

        assertNotNull(moodHistory);

        moodHistory.finish();

    }

    @Test
    public void testThatCommentDialogIsLaunched () {

        View view3 = mActivity.findViewById(R.id.custom_note_button_normal);
        assertNotNull(view3);

        onView(withId(R.id.custom_note_button_normal)).perform(click());

        onView(withText(R.string.alert_dialog_box_ok)).check(matches(isDisplayed()));
        onView(withText(R.string.alert_dialog_box_cancel)).check(matches(isDisplayed()));

    }

    @Test
    public void testThatClickingTheFaceButtonUpdatesTheDatabase () {

        assertTrue(mActivity.dbH.updateDataDaysStateInToday(3));

    }

    @After
    public void tearDown() throws Exception {

        mActivity = null;

    }

}