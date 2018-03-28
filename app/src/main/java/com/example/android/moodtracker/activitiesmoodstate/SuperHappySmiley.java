package com.example.android.moodtracker.activitiesmoodstate;

import android.animation.Animator;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.moodtracker.activitiesrest.MoodHistory;
import com.example.android.moodtracker.alertdialog.AlertDialogCreator;
import com.example.android.moodtracker.database.DatabaseHelper;
import com.example.android.moodtracker.R;

/**
 * Created by Diego Fajardo on 13/01/2018.
 */

/** Super Happy Smiley activity class --> Displays Super Happy Face;  activity_smiley_super_happy layout */
public class SuperHappySmiley extends AppCompatActivity implements GestureDetector.OnGestureListener{

    //GESTURE DETECTOR VARIABLE
    GestureDetectorCompat mGestureDetector;

    //BUTTONS
    ImageButton image_button_super_happy_face;
    ImageButton image_button_history;
    ImageButton image_button_add_note;

    //DATABASE HELPER
    DatabaseHelper dbH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smiley_super_happy);
        setTitle(getString(R.string.app_name)); //Title displayed in the action bar !!!! Change in other activities!

        //Database Helper
        dbH = new DatabaseHelper(this);

        //GESTURES
        //Parameters:
        //1.App's context
        //2. Invoked for callbacks
        this.mGestureDetector = new GestureDetectorCompat(this, this);


        /** ONCLICK LISTENERS */

        //FACE BUTTON

        image_button_super_happy_face = findViewById(R.id.super_happy_face_button);
        image_button_super_happy_face.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                //CODE FOR FADING WHEN PRESSING THE BUTTON

                // get the center for the clipping circle
                int cx = (image_button_super_happy_face.getLeft() + image_button_super_happy_face.getRight()) / 2;
                int cy = (image_button_super_happy_face.getTop() + image_button_super_happy_face.getBottom()) / 2;

                // get the final radius for the clipping circle
                int dx = Math.max(cx, image_button_super_happy_face.getWidth() - cx);
                int dy = Math.max(cy, image_button_super_happy_face.getHeight() - cy);
                float finalRadius = (float) Math.hypot(dx, dy);

                // Android native animator
                Animator animator =
                        ViewAnimationUtils.createCircularReveal(image_button_super_happy_face, cx, cy, 0, finalRadius);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(500);
                animator.start();

                //Used for feeback to the user
                Toast.makeText(SuperHappySmiley.this, getString(R.string.day_set_superhappy), Toast.LENGTH_LONG).show();

                //UPDATES the state of the day
                dbH.updateDataDaysStateInToday(5);

            }
        });

        //MOOD HISTORY BUTTON

        image_button_history = findViewById(R.id.mood_history_button_super_happy);
        image_button_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SuperHappySmiley.this, getString(R.string.mood_history_toast), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SuperHappySmiley.this, MoodHistory.class);
                startActivity(intent);
            }
        });

        //ADDING COMMENTS BUTTON

        image_button_add_note = findViewById(R.id.custom_note_button_super_happy);
        image_button_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialogCreator alertDialog = new AlertDialogCreator();
                alertDialog.createAlertDialog(SuperHappySmiley.this, dbH);

            }
        });
    }

    //BACK BUTTON
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SuperHappySmiley.this.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    //CODE FOR MANAGING TOUCH EVENTS
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.mGestureDetector.onTouchEvent(event);
        //return super.onTouchEvent(event);
    }

    //CODE FOR ANIMATION WHEN CHANGING BETWEEN ACTIVITIES
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() < -200) //to differentiate from a tap
        {
            Intent intent = new Intent(SuperHappySmiley.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }
}

