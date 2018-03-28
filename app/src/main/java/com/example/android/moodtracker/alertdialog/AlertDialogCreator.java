package com.example.android.moodtracker.alertdialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moodtracker.Data.DatabaseHelper;
import com.example.android.moodtracker.R;

/**
 * Created by Diego Fajardo on 27/01/2018.
 */

/** AlertDialogCreator is used to create an alert dialog when the user taps the custom_note_button
 * in any of the layouts that display face buttons */
class AlertDialogCreator {

    //Constructor
    AlertDialogCreator() { }

    //METHOD USED TO CREATE THE ALERT DIALOG when the button is tapped
    public void createAlertDialog (final Context context, final DatabaseHelper dbH) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);

        //This way, we get a reference to alert_dialog layout INSIDE the activity
        LayoutInflater inflater = LayoutInflater.from(context);
        View mView = inflater.inflate(R.layout.alert_dialog_comment, null);
        //Displays alert_dialog_comment layout

        //Creating the references to each view.
        TextView alertDialogTitle = (TextView) mView.findViewById(R.id.alertDialogTitle);
        final EditText alertDialogComment = (EditText) mView.findViewById(R.id.alertDialogComment);
        Button alertDialogButtonOK = (Button) mView.findViewById(R.id.alertDialogBoxOK);
        final Button alertDialogButtonCANCEL = (Button) mView.findViewById(R.id.alertDialogBoxCANCEL);
        //You cannot reference directly without mView because you would be calling a TextView
        //in the Activity, which does not exist (and you'll get an exception).That is why you call
        //mView and then findViewById.

        //SHOWING the Alert Dialog
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        //SETTING WHAT OK BUTTON DOES
        alertDialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We check is the comment box is not empty.
                if (!alertDialogComment.getText().toString().isEmpty()) {
                    Toast.makeText(context,
                            context.getString(R.string.quotation_marks)
                                    + alertDialogComment.getText().toString()
                                    + context.getString(R.string.has_been_saved),
                            Toast.LENGTH_SHORT).show();
                    dbH.updateDataDaysCommentInToday(alertDialogComment.getText().toString());
                    dialog.dismiss();
                } else {
                    Toast.makeText(context,
                            context.getString(R.string.fill_empty_space),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //SETTING WHAT CANCEL BUTTON DOES
        alertDialogButtonCANCEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,
                        context.getString(R.string.no_string_added),
                        Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}