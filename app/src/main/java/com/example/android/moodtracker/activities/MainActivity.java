package com.example.android.moodtracker.activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.moodtracker.R;

public class MainActivity extends AppCompatActivity {

    //BUTTONS
    ImageButton image_button_happy_face;
    ImageButton image_button_history;
    ImageButton image_button_add_note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

            }
        });

        //ADDING COMMENTS BUTTON

        image_button_add_note = findViewById(R.id.custom_note_button_main);
        image_button_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }



}
