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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by Diego Fajardo on 29/03/2018.
 */
public class SuperHappySmileyTest {

    @Rule
    public ActivityTestRule<SuperHappySmiley> superHappySmileyActivityTestRule =
            new ActivityTestRule<SuperHappySmiley>(SuperHappySmiley.class);

    private SuperHappySmiley mActivity = null;

    Instrumentation.ActivityMonitor moodHistoryMonitor =
            getInstrumentation().addMonitor(
                    MoodHistory.class.getName(),
                    null,
                    false);

    Instrumentation.ActivityMonitor mainActivityMonitor =
            getInstrumentation().addMonitor(
                    MainActivity.class.getName(),
                    null,
                    false);


    @Before
    public void setUp() throws Exception {

        mActivity = superHappySmileyActivityTestRule.getActivity();

    }

    @Test
    public void testThatViewsAreNotNull() {

        /** If we can find the views, we can conclude that the activity is launched successfully */

        View view1 = mActivity.findViewById(R.id.super_happy_face_button);
        View view2 = mActivity.findViewById(R.id.mood_history_button_super_happy);
        View view3 = mActivity.findViewById(R.id.custom_note_button_super_happy);

        assertNotNull(view1);
        assertNotNull(view2);
        assertNotNull(view3);

    }

    @Test
    public void testThatMainActivityIsLaunched () {

        onView(withId(R.id.container_super_happy))
                .perform(ViewActions.swipeRight());

        Activity mainActivity = getInstrumentation().waitForMonitorWithTimeout(mainActivityMonitor, 5000);

        assertNotNull(mainActivity);

        mainActivity.finish();

    }

    @Test
    public void testThatMoodHistoryIsLaunched () {

        View view2 = mActivity.findViewById(R.id.mood_history_button_super_happy);
        assertNotNull(view2);

        onView(withId(R.id.mood_history_button_super_happy)).perform(click());

        Activity moodHistory = getInstrumentation().waitForMonitorWithTimeout(moodHistoryMonitor, 5000);

        assertNotNull(moodHistory);

        moodHistory.finish();

    }


    @After
    public void tearDown() throws Exception {

        mActivity = null;



    }

}