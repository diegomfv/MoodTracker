package com.example.android.moodtracker.activitiesrest;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.moodtracker.activitiesmoodstate.MainActivity;
import com.example.android.moodtracker.database.DatabaseContract;
import com.example.android.moodtracker.database.DatabaseHelper;
import com.example.android.moodtracker.R;
import com.example.android.moodtracker.adapters.RvAdapter;

/**
 * Created by Diego Fajardo on 13/01/2018.
 */


public class MoodHistory extends AppCompatActivity {

    /** Mood History class is used to show the information of the database.
     * It displays it in a recyclerView */

    /**
     * RecyclerView variables
     * */
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    DatabaseHelper dbH;
    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_history);
        setTitle("Mood History");

        /**
         * Sets the back arrow icon in the top bar
         * */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        dbH = new DatabaseHelper(this);

        mCursor = dbH.getAllDataFromDaysTable();

        /** We get a reference to the recyclerView, set that the size won't change
         * and set its layout. Then, we set the adapter that it will use
         * */

        myRecyclerView = (RecyclerView) findViewById(R.id.rv_mood_history);
        myRecyclerView.setHasFixedSize(true);

        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);

        myAdapter = new RvAdapter(this, mCursor);
        myRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MoodHistory.this.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    /** Used to inflate the menu in the top bar
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mood_history_menu, menu);
        return true;
    }

    /** Used to respond to click events on the top menu
     *  */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                startActivity(new Intent(MoodHistory.this, MainActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
                break;

            case R.id.see_pie_chart:

                /** If there is no data in days table, the user can't go to
                 * PieChartActivity
                 * */
                if (returnTrueIfThereIsStateDataInDaysTable(mCursor)){
                    startActivity(new Intent(MoodHistory.this, PieChartActivity.class));
                    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                }
                else {
                    Toast.makeText(MoodHistory.this,
                            getResources().getString(R.string.no_data_no_pie_chart),
                            Toast.LENGTH_SHORT)
                            .show();
                }
                break;

            case R.id.delete_comment_history:

                /** Button used to delete the mood history */
                alertDialogDeleteHistory();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Creates an alert dialog to tell the user if truly wants to delete the complete
     * mood history
     * */
    private void alertDialogDeleteHistory () {

        AlertDialog.Builder builder = new AlertDialog.Builder(MoodHistory.this);
        builder.setMessage(getResources().getString(R.string.delete_history_message))
                .setTitle(getResources().getString(R.string.delete_history_title))
                .setPositiveButton(getResources().getString(R.string.delete_history_positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i = 0; i < mCursor.getCount() ; i++) {
                            dbH.updateDataDays(6, "", i+1);
                        }

                        Toast.makeText(MoodHistory.this, getResources().getString(R.string.delete_history_toast), Toast.LENGTH_SHORT).show();

                        //Code used to restart the activity
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        MoodHistory.this.overridePendingTransition(R.anim.fade_in,
                                R.anim.fade_out);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.delete_history_negative_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Nothing happens
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /** Returns true if the days table is empty
     * (all state_id in all rows = 6, which means "no mood")
     * It's used to prevent the user from going to the Pie Chart Activity
     * if there is no data in the table */
    public boolean returnTrueIfThereIsStateDataInDaysTable (Cursor mCursor) {

        int counter = 0;
        mCursor.moveToFirst();

        for (int i = 0; i < mCursor.getCount() ; i++) {
            if (mCursor.getInt(mCursor.getColumnIndex(DatabaseContract.Database.STATE_ID)) == 6){
                counter++;
                if (i != mCursor.getCount()-1) {
                    mCursor.moveToNext();
                }
            }
        }

        //If counter is equal to the number of rows, then it means that all are 6,
        //which means there is no information in the database
        if (counter != mCursor.getCount())return true;
        else return false;

    }

}
