package com.example.android.moodtracker.activitiesmoodstate;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.example.android.moodtracker.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Diego Fajardo on 28/03/2018.
 */
public class MainActivityTest {

    /** This RULE specifies that this activity is launched */
    //Always make this public
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;


    @Before
    public void setUp() throws Exception {

        /** With this, we get the context! */
        mActivity = mainActivityActivityTestRule.getActivity();

    }

    @Test
    public void TestLaunch() {

        /** If we can find the views, we can conclude that the activity is launched successfully */

        View view1 = mActivity.findViewById(R.id.happy_face_button);
        View view2 = mActivity.findViewById(R.id.mood_history_button_main);
        View view3 = mActivity.findViewById(R.id.custom_note_button_main);


        assertNotNull(view1);
        assertNotNull(view2);
        assertNotNull(view3);
    }

    @After
    public void tearDown() throws Exception {

        /** With this, we nullify the activity (that was launched) */
        mActivity = null;

    }

}