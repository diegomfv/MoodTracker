package com.example.android.moodtracker.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.example.android.moodtracker.database.DatabaseContract;
import com.example.android.moodtracker.database.DatabaseHelper;

/**
 * Created by Diego Fajardo on 28/03/2018.
 */

public class BroadcastDataUpdate extends BroadcastReceiver {

    /** The broadcast receiver is called by the alarm at midnight. It updates the days table
     * moving the information of each row to the next one. The information of the last
     * row is deleted permanently. The first row becomes empty (state = 6, comment = "") */

    DatabaseHelper dbH;
    Cursor mCursor;

    @Override
    public void onReceive(Context context, Intent intent) {

        dbH = new DatabaseHelper(context);

        mCursor = dbH.getAllDataFromDaysTable();

        if (mCursor.getCount() != 0) {

            /**
             * The loop iterates through the database table using the cursor
             * We have to take into consideration that
             * the position of the Cursor and
             * the variable position in "changeRow(state,comment,position) function
             * ARE NOT RELATED TO THE SAME ROW.
             * Cursor position is related this way --> Cursor position = x; id = x+1;
             * Position is changeRow is this way --> Position = id;
             * */

            //loop that updates the info of every row except row 1 (id 1)
            for (int i = 0; i < mCursor.getCount() - 1; i++) {

                mCursor.moveToPosition(i);

                int state = mCursor.getInt(mCursor.getColumnIndex(DatabaseContract.Database.STATE_ID));
                String comment = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.Database.COMMENT));

                dbH.updateDataDays(state, comment, i + 2);

            }

            //deleting the info from row 1 (id 1) when the loop is finished
            int state = 6;
            String comment = "";

            dbH.updateDataDays(state, comment, 1);

        }
    }
}
