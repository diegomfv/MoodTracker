package com.example.android.moodtracker.activitiesrest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.moodtracker.database.DatabaseHelper;
import com.example.android.moodtracker.R;
import com.example.android.moodtracker.adapters.RvAdapter;

/**
 * Created by Diego Fajardo on 13/01/2018.
 */

/** Mood History class --> Used to set the adapter and display the RecyclerView */
public class MoodHistory extends AppCompatActivity {

    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    //DATABASE VARIABLES
    SQLiteDatabase mdb;
    DatabaseHelper dbH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);

        dbH = new DatabaseHelper(this);
        mdb = dbH.getWritableDatabase();
        Cursor cursor = dbH.getAllData();

        // TODO: 15/03/2018 Reduce size of the text

        myRecyclerView = (RecyclerView) findViewById(R.id.rv_mood_history);
        myRecyclerView.setHasFixedSize(true);

        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);

        myAdapter = new RvAdapter(this, cursor);
        myRecyclerView.setAdapter(myAdapter);
    }

    //BACK BUTTON

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MoodHistory.this.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }
}
