package com.example.android.moodtracker.activitiesrest;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;

import com.example.android.moodtracker.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

    int[] COLORS;


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

    @Test
    public void testGetColors () {

        int red = mActivity.getResources().getColor(R.color.faded_red);
        int grey = mActivity.getResources().getColor(R.color.warm_grey);
        int blue = mActivity.getResources().getColor(R.color.cornflower_blue_65);
        int green = mActivity.getResources().getColor(R.color.light_sage);
        int yellow = mActivity.getResources().getColor(R.color.banana_yellow);

       Random random = new Random();

        int[] states_of_the_day = new int[15];

        //max range 6
        for (int i = 0; i < states_of_the_day.length; i++) {
            states_of_the_day[i] = (random.nextInt(6)) + 1;
        }

        for (int i = 0; i < states_of_the_day.length; i++) {
            if (states_of_the_day[i] == 1) {

            }
        }

        int[] COLORS_TO_MATCH_1 = {
                red, blue, yellow, -1
        };

        int[] COLORS_TO_MATCH_2 = {
                red, blue, -1
        };

        int[] COLORS_TO_MATCH_3 = {
                red, green, yellow, -1
        };

        int[] COLORS_TO_MATCH_4 = {
                blue, green, yellow, -1
        };

        int[] COLORS_TO_MATCH_5 = {
                grey, yellow, -1
        };

        //1
        int[] COLORS = getColors(1, 0, 1, 0, 1, 12);

        for (int i = 0; i < COLORS.length ; i++) {
            assertTrue(COLORS[i] == COLORS_TO_MATCH_1[i]);
        }

        //2
        COLORS = getColors(1, 0, 1, 0, 0, 13);

        for (int i = 0; i < COLORS.length ; i++) {
            assertTrue(COLORS[i] == COLORS_TO_MATCH_2[i]);
        }

        //3
        COLORS = getColors(1, 0, 0, 1, 1, 12);

        for (int i = 0; i < COLORS.length ; i++) {
            assertTrue(COLORS[i] == COLORS_TO_MATCH_3[i]);
        }

        //4
        COLORS = getColors(0, 0, 1, 1, 1, 12);

        for (int i = 0; i < COLORS.length ; i++) {
            assertTrue(COLORS[i] == COLORS_TO_MATCH_4[i]);
        }

        //5
        COLORS = getColors(0, 1, 0, 0, 1, 13);

        for (int i = 0; i < COLORS.length ; i++) {
            assertTrue(COLORS[i] == COLORS_TO_MATCH_5[i]);
        }

    }

    @After
    public void tearDown() throws Exception {

        mActivity = null;

    }

    public int[] preparationGetColors () {

        int red = mActivity.getResources().getColor(R.color.faded_red);
        int grey = mActivity.getResources().getColor(R.color.warm_grey);
        int blue = mActivity.getResources().getColor(R.color.cornflower_blue_65);
        int green = mActivity.getResources().getColor(R.color.light_sage);
        int yellow = mActivity.getResources().getColor(R.color.banana_yellow);

        int daysSad = 0;
        int daysDisappointed = 0;
        int daysNormal = 0;
        int daysHappy = 0;
        int daysSuperHappy = 0;
        int daysNoMood = 0;

        Random random = new Random();

        int[] states_of_the_day = new int[15];

        //max range 6
        for (int i = 0; i < states_of_the_day.length; i++) {
            states_of_the_day[i] = (random.nextInt(6)) + 1;
        }

        for (int i = 0; i < states_of_the_day.length; i++) {
            switch (states_of_the_day[i]){
                case 1: daysSad++;
                case 2: daysDisappointed++;
                case 3: daysNormal++;
                case 4: daysHappy++;
                case 5: daysSuperHappy++;
                case 6: daysNoMood++;
            }

        }

        int counter = 0;
        if (daysSad != 0) counter++;
        if (daysDisappointed != 0) counter++;
        if (daysNormal != 0) counter++;
        if (daysHappy != 0) counter++;
        if (daysSuperHappy != 0) counter++;
        if (daysNoMood != 0) counter++;

        int COLORS[] = new int[counter];

        counter = 0;
        if (daysSad != 0) COLORS[counter] = red; counter++;
        if (daysDisappointed != 0) COLORS[counter] = grey; counter++;
        if (daysNormal != 0) COLORS[counter] = blue; counter++;
        if (daysHappy != 0) COLORS[counter] = green; counter++;
        if (daysSuperHappy != 0) COLORS[counter] = yellow; counter++;
        if (daysNoMood != 0) COLORS[counter] = -1;

        return COLORS;

    }

    public int[] getColors (float daysSad,
                            float daysDisappointed,
                            float daysNormal,
                            float daysHappy,
                            float daysSuperHappy,
                            float daysNoMood) {

        List<Integer> ArrayForColors = new ArrayList<>();

        //We used a List to het the colors and the size of the array
        if (daysSad != 0) { ArrayForColors.add(mActivity.getResources().getColor(R.color.faded_red)); }
        if (daysDisappointed != 0) { ArrayForColors.add(mActivity.getResources().getColor(R.color.warm_grey)); }
        if (daysNormal != 0) { ArrayForColors.add(mActivity.getResources().getColor(R.color.cornflower_blue_65)); }
        if (daysHappy != 0) { ArrayForColors.add(mActivity.getResources().getColor(R.color.light_sage)); }
        if (daysSuperHappy != 0) { ArrayForColors.add(mActivity.getResources().getColor(R.color.banana_yellow)); }
        if (daysNoMood != 0) { ArrayForColors.add(mActivity.getResources().getColor(R.color.whiteColor)); }

        //We set the size of the array as the size of the list
        COLORS = new int[ArrayForColors.size()];

        int counter = 0;

        //We set the order of the colors according
        // to the states of the days we have in the database
        if (daysSad != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }
        if (daysDisappointed != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }
        if (daysNormal != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }
        if (daysHappy != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }
        if (daysSuperHappy != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }
        if (daysNoMood != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }

        return COLORS;

    }

}