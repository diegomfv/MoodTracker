package com.example.android.moodtracker.activitiesmoodstate;

import android.animation.Animator;
import android.content.Intent;
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

public class DisappointedSmiley extends AppCompatActivity
        implements GestureDetector.OnGestureListener {

    /** To see comments about variables and methods, see MainActivity */

    GestureDetectorCompat mGestureDetector;

    ImageButton image_button_disappointed_face;
    ImageButton image_button_history;
    ImageButton image_button_add_note;

    DatabaseHelper dbH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smiley_disappointed);

        dbH = new DatabaseHelper(this);

        this.mGestureDetector = new GestureDetectorCompat(this, this);


        image_button_disappointed_face = findViewById(R.id.disappointed_face_button);
        image_button_disappointed_face.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                int cx = (image_button_disappointed_face.getLeft() + image_button_disappointed_face.getRight()) / 2;
                int cy = (image_button_disappointed_face.getTop() + image_button_disappointed_face.getBottom()) / 2;

                int dx = Math.max(cx, image_button_disappointed_face.getWidth() - cx);
                int dy = Math.max(cy, image_button_disappointed_face.getHeight() - cy);
                float finalRadius = (float) Math.hypot(dx, dy);

                Animator animator =
                        ViewAnimationUtils.createCircularReveal(image_button_disappointed_face, cx, cy, 0, finalRadius);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(500);
                animator.start();

                Toast.makeText(DisappointedSmiley.this, getString(R.string.day_set_disappointed), Toast.LENGTH_SHORT).show();

                dbH.updateDataDaysStateInToday(2);

            }
        });

        image_button_history = findViewById(R.id.mood_history_button_disappointed);
        image_button_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DisappointedSmiley.this, getString(R.string.mood_history_toast), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DisappointedSmiley.this, MoodHistory.class);
                startActivity(intent);
            }
        });

        image_button_add_note = findViewById(R.id.custom_note_button_disappointed);
        image_button_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialogCreator alertDialog = new AlertDialogCreator();
                alertDialog.createAlertDialog(DisappointedSmiley.this, dbH);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DisappointedSmiley.this.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > 200) //to differentiate from a tap
        {
            Intent intent = new Intent(DisappointedSmiley.this, NormalSmiley.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        }
        if (e1.getX() - e2.getX() < -200) //to differentiate from a tap
        {
            Intent intent = new Intent(DisappointedSmiley.this, SadSmiley.class);
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

