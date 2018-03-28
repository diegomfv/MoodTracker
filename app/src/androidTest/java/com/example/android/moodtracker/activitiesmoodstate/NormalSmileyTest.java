package com.example.android.moodtracker.activitiesmoodstate;

import android.content.Intent;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.moodtracker.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by Diego Fajardo on 28/03/2018.
 */
@RunWith(AndroidJUnit4.class)
public class NormalSmileyTest {

    /** @RunWith(AndroidJUnit4.class) allows to run JUnit4 tests */

    @Rule
    public ActivityTestRule rule = new ActivityTestRule(
            NormalSmiley.class,
            true,
            false);

    @Test
    public void shouldRenderView() throws Exception {

        //We don't pass anything in the activity because the Rule already nows that the
        //activity is the NormalSmileyActivity

        rule.launchActivity(new Intent());

        //This can be used to check that a View has a specific text
        //onView(withText(R.string._____)).check(matches(isDisplayed()));

        onView(withId(R.id.mood_history_button_normal))
                .perform(click())
                .check(matches(isEnabled()));




    }

}