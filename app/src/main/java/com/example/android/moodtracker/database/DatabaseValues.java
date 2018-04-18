package com.example.android.moodtracker.database;

/**
 * Created by Diego Fajardo on 16/01/2018.
 */



public class DatabaseValues {

    /** DATABASEVALUES class has the information
     * that will be used to fill the database:
     * days table,
     * states table */

    /** Different days that will be
     * displayed in the Recycler View
     * */
    public static String [] days = {
            "Today",
            "Yesterday",
            "2 days ago",
            "3 days ago",
            "4 days ago",
            "5 days ago",
            "6 days ago",
            "A week ago",
            "8 days ago",
            "9 days ago",
            "10 days ago",
            "11 days ago",
            "12 days ago",
            "13 days ago",
            "2 weeks ago",
            "15 days ago"
    };

    /** Possible states for each day.
     * The number next to the state is the id used in the
    states table to store each state
     */
    public static String [] states = {
            "Sad", //1
            "Disappointed", //2
            "Normal", //3
            "Happy", //4
            "Super Happy", //5
            "Nothing yet" //6S
    };

}
