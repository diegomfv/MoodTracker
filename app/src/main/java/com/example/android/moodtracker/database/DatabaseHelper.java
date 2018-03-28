package com.example.android.moodtracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Diego Fajardo on 16/01/2018.
 */


/** DatabaseHelper is used for creating and modifying the database */
public class DatabaseHelper extends SQLiteOpenHelper {

    //Logcat
    private static final String LOG = "DatabaseHelper";

    //DATABASE NAME. Name of the file that will store all the data
    private static final String DATABASE_NAME = "MoodTracker.db";

    //DATABASE VERSION. Version of the table
    private static final int DATABASE_VERSION = 1;

    //Constructor of the DatabaseHelper
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** TBALE CREATION STATEMENTS. We use them
     *  to create the database*/
    private static final String CREATE_DAYS_TABLE =
            "CREATE TABLE " + DatabaseContract.Database.DAYS_TABLE_NAME
                    + " ("
                    + DatabaseContract.Database.DAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DatabaseContract.Database.DAY + " TEXT NOT NULL,"
                    + DatabaseContract.Database.STATE_ID + " INTEGER NOT NULL,"
                    + DatabaseContract.Database.COMMENT + " TEXT,"
                    + " FOREIGN KEY (" + DatabaseContract.Database.STATE_ID + ") REFERENCES "
                    + DatabaseContract.Database.STATES_TABLE_NAME
                    + "(" + DatabaseContract.Database.STATE_ID + "));";

    private static final String CREATE_STATE_TABLE = "CREATE TABLE "
            + DatabaseContract.Database.STATES_TABLE_NAME
            + " ("
            + DatabaseContract.Database.STATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DatabaseContract.Database.STATE + " TEXT NOT NULL"
            + ")";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //creating required tables
        sqLiteDatabase.execSQL(CREATE_DAYS_TABLE);
        sqLiteDatabase.execSQL(CREATE_STATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //On upgrade drop older versions
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Database.DAYS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Database.STATES_TABLE_NAME);

        //Create new table
        onCreate(db);
    }

    /** METHOD FOR INSERTING ALL THE INFORMATION IN THE DATABASE
     * the first time the app runs (days table) */
    public boolean insertFirstDataDays(String day, int state_id, String comment) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Database.DAY, day);
        contentValues.put(DatabaseContract.Database.STATE_ID, state_id);
        contentValues.put(DatabaseContract.Database.COMMENT, comment);

        long result = db.insert(
                DatabaseContract.Database.DAYS_TABLE_NAME,
                null,
                contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /** METHOD FOR INSERTING ALL THE INFORMATION IN THE DATABASE
     * the first time the app runs (states table) */
    public boolean insertFirstDataStates(String state) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Database.STATE, state);

        long result = db.insert(
                DatabaseContract.Database.STATES_TABLE_NAME,
                null,
                contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    /** METHOD FOR UPDATING TODAY'S STATE
     * (when a face button has been clicked)*/
    public boolean updateDataDaysStateInToday(int state) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Database.STATE_ID, state);

        return db.update(DatabaseContract.Database.DAYS_TABLE_NAME,
                contentValues, DatabaseContract.Database.DAY_ID + "= 1",
                null) > 0;
    }

    /** METHOD FOR UPDATING TODAY'S COMMENT
     * (in ALERT DIALOG) */
    public boolean updateDataDaysCommentInToday(String comment) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Database.COMMENT, comment);

        return db.update(DatabaseContract.Database.DAYS_TABLE_NAME,
                contentValues, DatabaseContract.Database.DAY_ID + "= 1",
                null) > 0;
    }

    /** METHOD USED IN BROADCAST RECEIVER
     *  to UPDATE the database when a DAY ends */
    public boolean updateDataDays(int state, String comment, int position) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Database.STATE_ID, state);
        contentValues.put(DatabaseContract.Database.COMMENT, comment);

        return db.update(DatabaseContract.Database.DAYS_TABLE_NAME,
                contentValues, DatabaseContract.Database.DAY_ID + "= " + position,
                null) > 0;
    }

    /** METHOUS USED TO FIL THE CURSOR with all the data from
     * the days table */
    public Cursor getAllDataFromDaysTable() {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + DatabaseContract.Database.DAYS_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;

    }

    /**
     * METHOD FOR RESETTING AUTOINCREMENT ID from each table
     * */
    public void resetAutoIncrement (String table_name) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + table_name + "'");

    }

    /** METHOD USED TO CHECK IF A TABLE IS EMPTY (used to avoid the app
    // to run insertFirstDays and inserfFirstDataStates more than once)*/
    public boolean isTableEmpty(String table_name) {

        boolean flag;
        String quString = "select exists(select 1 from " + table_name + ");";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(quString, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (count == 1) {
            flag = false;
        } else {
            flag = true;
        }
        cursor.close();
        db.close();

        return flag;
    }

    /** METHOD FOR DATABASE MANAGER --> CAN BE REMOVED */
    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }

}