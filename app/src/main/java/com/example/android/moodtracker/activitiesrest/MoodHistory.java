package com.example.android.moodtracker.activitiesrest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.moodtracker.activitiesmoodstate.MainActivity;
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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mood_history_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(MoodHistory.this, MainActivity.class));
                break;
            case R.id.see_pie_chart:
                startActivity(new Intent(MoodHistory.this, PieChartActivity.class));
                break;
            case R.id.delete_comment_history:



        }
        return super.onOptionsItemSelected(item);
    }
}
