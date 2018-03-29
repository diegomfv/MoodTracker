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
import com.github.mikephil.charting.components.Legend;
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

    private float daysSad = 0;
    private float daysDisappointed = 0;
    private float daysNormal = 0;
    private float daysHappy = 0;
    private float daysSuperHappy = 0;
    private float daysNoMood = 0;

    int[] COLORS;

    public List<Integer> ArrayForColors;

    private DatabaseHelper dbH;
    private Cursor mCursor;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart_layout);
        setTitle(R.string.title_pie_chart_activity);

        dbH = new DatabaseHelper(this);
        mCursor = dbH.getAllDataFromDaysTable();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeActionContentDescription(R.string.go_back_mood_history);
        }

        ArrayForColors = new ArrayList<>();

        mCursor.moveToFirst();

        for (int i = 0; i < mCursor.getCount(); i++) {

            int state = mCursor.getInt(mCursor.getColumnIndex(DatabaseContract.Database.STATE_ID));

            switch (state){
                case 1: daysSad++; if (i != mCursor.getCount()) { mCursor.moveToNext(); } break;
                case 2: daysDisappointed++; if (i != mCursor.getCount()) { mCursor.moveToNext(); } break;
                case 3: daysNormal++; if (i != mCursor.getCount()) { mCursor.moveToNext(); } break;
                case 4: daysHappy++; if (i != mCursor.getCount()) { mCursor.moveToNext(); } break;
                case 5: daysSuperHappy++; if (i != mCursor.getCount()) { mCursor.moveToNext(); } break;
                case 6: daysNoMood++; if (i != mCursor.getCount()) { mCursor.moveToNext(); } break;

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

        /** We get the array of colors that we are going to use in the Pie Chart
         *  */
        COLORS = getColors(
                daysSad,
                daysDisappointed,
                daysNormal,
                daysHappy,
                daysSuperHappy,
                daysNoMood);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        if (daysSad != 0) {
            yValues.add(new PieEntry(daysSad, "Sad"));
        }

        if (daysDisappointed != 0) {
            yValues.add(new PieEntry(daysDisappointed, "Disappointed"));
        }

        if (daysNormal != 0) {
            yValues.add(new PieEntry(daysNormal, "Normal"));
        }

        if (daysHappy != 0) {
            yValues.add(new PieEntry(daysHappy, "Happy"));
        }

        if (daysSuperHappy != 0) {
            yValues.add(new PieEntry(daysSuperHappy, "Super Happy"));
        }

        //if (daysNoMood != 0) {
        //    yValues.add(new PieEntry(daysNoMood, "No Mood"));
        //}

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(6f);
        dataSet.setSelectionShift(1f);
        dataSet.setColors(COLORS);
        dataSet.setValueFormatter(new MyValueFormatter());

        PieData data = new PieData(dataSet);
        data.setValueTextSize(18f);
        data.setValueTextColor(Color.BLACK);

        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);
        //legend.setFormSize(10f);
        //legend.setTextSize(12f);
        //legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        //legend.setForm(Legend.LegendForm.CIRCLE);

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

    /** Method used to get the colors for the Pie chart.
     * The Pie Charts uses an array of "int" for the colors but needs "float" variables to
     * set the size of each pie slice.
     * Firstly, we use a List to store the colors we are going to use according to the states
     * we have in the database.
     * Secondly, we use the List's size to determine the size of the array of "int".
     * Lastly, we fill the array with the colors.
     * */
    public int[] getColors (float daysSad,
                            float daysDisappointed,
                            float daysNormal,
                            float daysHappy,
                            float daysSuperHappy,
                            float daysNoMood) {

        List <Integer> ArrayForColors = new ArrayList<>();

        //We used a List to het the colors and the size of the array
        if (daysSad != 0) { ArrayForColors.add(getResources().getColor(R.color.faded_red)); }
        if (daysDisappointed != 0) { ArrayForColors.add(getResources().getColor(R.color.warm_grey)); }
        if (daysNormal != 0) { ArrayForColors.add(getResources().getColor(R.color.cornflower_blue_65)); }
        if (daysHappy != 0) { ArrayForColors.add(getResources().getColor(R.color.light_sage)); }
        if (daysSuperHappy != 0) { ArrayForColors.add(getResources().getColor(R.color.banana_yellow)); }
        if (daysNoMood != 0) { ArrayForColors.add(getResources().getColor(R.color.whiteColor)); }

        //We set the size of the array as the size of the list
        COLORS = new int[ArrayForColors.size()];

        int counter = 0;

        //We set the order of the colors according
        // to the states of the days we have in the database
        if (daysSad != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }
        if (daysDisappointed != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }
        if (daysNormal != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }
        if (daysHappy != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }
        if (daysSuperHappy != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }
        if (daysNoMood != 0) { COLORS [counter] = ArrayForColors.get(counter); counter++; }

        return COLORS;

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
