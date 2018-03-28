package com.example.android.moodtracker.activitiesrest;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;

import com.example.android.moodtracker.database.DatabaseContract;
import com.example.android.moodtracker.database.DatabaseHelper;
import com.example.android.moodtracker.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego Fajardo on 27/03/2018.
 */

public class PieChartActivity extends AppCompatActivity{

    /** PieChartActivity displays the data
     * of days table in a Pie Chart*/

    private PieChart pieChart;

    private float sad = 0;
    private float disappointed= 0;
    private float normal = 0;
    private float happy = 0;
    private float superHappy = 0;
    private float no_mood = 0;

    int[] COLORS;

    public List<Integer> ArrayForColors;

    private DatabaseHelper dbH;
    private Cursor mCursor;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart_layout);
        setTitle("Mood Pie Chart");

        dbH = new DatabaseHelper(this);
        mCursor = dbH.getAllDataFromDaysTable();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ArrayForColors = new ArrayList<>();

        mCursor.moveToFirst();

        for (int i = 0; i < mCursor.getCount(); i++) {

            int state = mCursor.getInt(mCursor.getColumnIndex(DatabaseContract.Database.STATE_ID));

            switch (state){
                case 1: sad++; if (i != mCursor.getCount()) { mCursor.moveToNext(); } break;
                case 2: disappointed++; if (i != mCursor.getCount()) { mCursor.moveToNext(); } break;
                case 3: normal++; if (i != mCursor.getCount()) { mCursor.moveToNext(); } break;
                case 4: happy++; if (i != mCursor.getCount()) { mCursor.moveToNext(); } break;
                case 5: superHappy++; if (i != mCursor.getCount()) { mCursor.moveToNext(); } break;
                case 6: no_mood++; if (i != mCursor.getCount()) { mCursor.moveToNext(); } break;

            }

        }

        pieChart = (PieChart) findViewById(R.id.pie_chart);

        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.15f);
        pieChart.setEntryLabelColor(Color.BLACK);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(55f);


        if (sad != 0) { ArrayForColors.add(getResources().getColor(R.color.faded_red)); }
        if (disappointed != 0) { ArrayForColors.add(getResources().getColor(R.color.warm_grey)); }
        if (normal != 0) { ArrayForColors.add(getResources().getColor(R.color.cornflower_blue_65)); }
        if (happy != 0) { ArrayForColors.add(getResources().getColor(R.color.light_sage)); }
        if (superHappy != 0) { ArrayForColors.add(getResources().getColor(R.color.banana_yellow)); }
        if (no_mood != 0) { ArrayForColors.add(getResources().getColor(R.color.whiteColor)); }

        COLORS = new int[ArrayForColors.size()];

        int counter = 0;

        if (sad != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }
        if (disappointed != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }
        if (normal != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }
        if (happy != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }
        if (superHappy != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }
        if (no_mood != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }


        ArrayList<PieEntry> yValues = new ArrayList<>();

        if (sad != 0) {
            yValues.add(new PieEntry(sad, "Sad"));
        }

        if (disappointed != 0) {
            yValues.add(new PieEntry(disappointed, "Disappointed"));
        }

        if (normal != 0) {
            yValues.add(new PieEntry(normal, "Normal"));
        }

        if (happy != 0) {
            yValues.add(new PieEntry(happy, "Happy"));
        }

        if (superHappy != 0) {
            yValues.add(new PieEntry(superHappy, "Super Happy"));
        }

        //if (no_mood != 0) {
        //    yValues.add(new PieEntry(no_mood, "No Mood"));
        //}

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "MOODS");
        dataSet.setSliceSpace(6f);
        dataSet.setSelectionShift(1f);
        dataSet.setColors(COLORS);
        dataSet.setValueFormatter(new MyValueFormatter());

        PieData data = new PieData(dataSet);
        data.setValueTextSize(18f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PieChartActivity.this.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                startActivity (new Intent(PieChartActivity.this, MoodHistory.class));
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Changes the format the data
     *  is displayed in the PieChart */

    public class MyValueFormatter implements IValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0");
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

            if (value == 1) return mFormat.format(value) + " day";
            else return mFormat.format(value) + " days";

        }
    }

}
