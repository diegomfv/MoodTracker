package com.example.android.moodtracker.activitiesmoodstate;

import android.animation.Animator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.moodtracker.R;
import com.example.android.moodtracker.activitiesrest.MoodHistory;
import com.example.android.moodtracker.broadcastreceiver.BroadcastDataUpdate;
import com.example.android.moodtracker.database.DatabaseContract;
import com.example.android.moodtracker.database.DatabaseHelper;
import com.example.android.moodtracker.database.DatabaseValues;
import com.example.android.moodtracker.alertdialog.AlertDialogCreator;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements GestureDetector.OnGestureListener {

    //GESTURE DETECTOR VARIABLE
    GestureDetectorCompat mGestureDetector;

    //BUTTONS
    ImageButton image_button_happy_face;
    ImageButton image_button_history;
    ImageButton image_button_add_note;

    //DATABASE HELPER
    DatabaseHelper dbH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Database Helper
        dbH = new DatabaseHelper(this);

        //GESTURES
        //Parameters: 1.App's context
        //2. Invoked for callbacks
        this.mGestureDetector = new GestureDetectorCompat(this, this);

        //CODE FOR CREATING THE DATABASE (FIRST TIME) and
        // for RUNNING THE SERVICE (only first time also) --> ALARM MANAGER
        if (dbH.isTableEmpty(DatabaseContract.Database.DAYS_TABLE_NAME)) {
            for (int i = 0; i < DatabaseValues.days.length; i++){
                dbH.insertFirstDataDays(DatabaseValues.days[i],6,"");
            }
            createAlarm();
        }

        if (dbH.isTableEmpty(DatabaseContract.Database.STATES_TABLE_NAME)) {
            for (int i = 0; i < DatabaseValues.states.length; i++) {
                dbH.insertFirstDataStates(DatabaseValues.states[i]);
            }
        }

        image_button_happy_face = (ImageButton) findViewById(R.id.happy_face_button);
        image_button_happy_face.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) //Used for animation (Animator animator)

            @Override
            public void onClick(View v) {

                //CODE FOR FADING WHEN PRESSING

                // get the center for the clipping circle
                int cx = (image_button_happy_face.getLeft() + image_button_happy_face.getRight()) / 2;
                int cy = (image_button_happy_face.getTop() + image_button_happy_face.getBottom()) / 2;

                // get the final radius for the clipping circle
                int dx = Math.max(cx, image_button_happy_face.getWidth() - cx);
                int dy = Math.max(cy, image_button_happy_face.getHeight() - cy);
                float finalRadius = (float) Math.hypot(dx, dy);

                // Android native animator
                Animator animator =
                        ViewAnimationUtils.createCircularReveal(image_button_happy_face, cx, cy, 0, finalRadius);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(500);
                animator.start();

            }
        });

        //MOOD HISTORY BUTTON

        image_button_history = findViewById(R.id.mood_history_button_main);
        image_button_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, getString(R.string.mood_history_toast), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MoodHistory.class);
                startActivity(intent);

            }
        });

        //ADDING COMMENTS BUTTON

        image_button_add_note = findViewById(R.id.custom_note_button_main);
        image_button_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogCreator alertDialog = new AlertDialogCreator();
                alertDialog.createAlertDialog(MainActivity.this, dbH);

            }
        });

    }

    //BACK BUTTON

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.this.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
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

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > 200) //to differentiate from a tap
        {
            Intent intent = new Intent(MainActivity.this, SuperHappySmiley.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        }
        if (e1.getX() - e2.getX() < -200) //to differentiate from a tap
        {
            Intent intent = new Intent(MainActivity.this, NormalSmiley.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        }
        return true;
    }


    private void createAlarm () {

        //CREATION OF A CALENDAR to get time in millis and pass it to the AlarmManager to set
        //the time when the alarm has to start working (same day the app runs for the first time
        //at midnight).
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        //DECLARATION OF the AlarmManager and
        // the Intent and PendingIntent necessary for the AlarmManager.setRepeating method.
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, BroadcastDataUpdate.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        //timeInMillis: specifies when we have to start the alarm (calendar gives this information).
        //INTERVAL_DAY: makes the alarm be repeated every day.

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        }

    }

}
