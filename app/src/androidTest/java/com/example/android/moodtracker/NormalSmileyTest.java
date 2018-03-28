package com.example.android.moodtracker;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.example.android.moodtracker.activitiesmoodstate.NormalSmiley;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Diego Fajardo on 08/02/2018.
 */

public class NormalSmileyTest {
    //This specifies this activity is launched
    @Rule
    public ActivityTestRule<NormalSmiley> normalSmileyActivityTestRule = new ActivityTestRule<NormalSmiley>(NormalSmiley.class);
    private NormalSmiley normalSmiley = null;


    //Executed before executing the test. Preconditions go here.
    @Before
    public void setUp() throws Exception {

        normalSmiley = normalSmileyActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){

        /** This means: if I am able to find the view and the view is not null, that means my activity is successful. */
        View view = normalSmiley.findViewById(R.id.normal_face_button);
        assertNotNull(view);

        View view1 = normalSmiley.findViewById(R.id.custom_note_button_normal);
        assertNotNull(view1);

        View view2 = normalSmiley.findViewById(R.id.mood_history_button_normal);
        assertNotNull(view2);

    }

    //Executed after executing the test. Clean up goes here.
    @After
    public void tearDown() throws Exception {

        normalSmiley = null; //here, we nullify the activity
    }
}