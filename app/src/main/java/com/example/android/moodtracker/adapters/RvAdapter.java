package com.example.android.moodtracker.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moodtracker.R;
import com.example.android.moodtracker.database.DatabaseContract;

/**
 * Created by Diego Fajardo on 14/01/2018.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {

    //Logs
    private static final String TAG = RvAdapter.class.getSimpleName();

    private Cursor mCursor;
    private Context mContext;

    /** Constructor of the Adapter
     *  */
    public RvAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        mCursor = cursor;
    }

    /** Creation of the ViewHolders of the recyclerView
     *  */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        return new MyViewHolder(view);
    }

    /** Manipulation of each view in each viewHolder
     *  */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) { //bind == encuadernar

        Log.d(TAG, "#" + position);

        if (!mCursor.moveToPosition(position)) //check to see if its in the bounds
            return;

        //SETTING THE BACKGROUND OF VIEW HOLDER

        int state = mCursor.getInt(mCursor.getColumnIndex(DatabaseContract.Database.STATE_ID));
        int color;
        LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams paramsRight = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.MATCH_PARENT);

        switch (state) {
            case 1: {
                color = ContextCompat.getColor(mContext, R.color.faded_red);
                holder.left_bar.setBackgroundColor(color);
                paramsLeft.weight = 0.2f;
                holder.left_bar.setLayoutParams(paramsLeft);
                paramsRight.weight = 0.8f;
                holder.right_bar.setLayoutParams(paramsRight);
            } break;
            case 2: {
                color = ContextCompat.getColor(mContext, R.color.warm_grey);
                holder.left_bar.setBackgroundColor(color);
                paramsLeft.weight = 0.4f;
                holder.left_bar.setLayoutParams(paramsLeft);
                paramsRight.weight = 0.6f;
                holder.right_bar.setLayoutParams(paramsRight);
            } break;
            case 3: {
                color = ContextCompat.getColor(mContext, R.color.cornflower_blue_65);
                holder.left_bar.setBackgroundColor(color);
                paramsLeft.weight = 0.6f;
                holder.left_bar.setLayoutParams(paramsLeft);
                paramsRight.weight = 0.4f;
                holder.right_bar.setLayoutParams(paramsRight);
            } break;
            case 4: {
                color = ContextCompat.getColor(mContext, R.color.light_sage);
                holder.left_bar.setBackgroundColor(color);
                paramsLeft.weight = 0.8f;
                holder.left_bar.setLayoutParams(paramsLeft);
                paramsRight.weight = 0.2f;
                holder.right_bar.setLayoutParams(paramsRight);
            } break;
            case 5: {
                color = ContextCompat.getColor(mContext, R.color.banana_yellow);
                holder.left_bar.setBackgroundColor(color);
                paramsLeft.weight = 1;
                holder.left_bar.setLayoutParams(paramsLeft);
                paramsRight.weight = 0;
                holder.right_bar.setLayoutParams(paramsRight);
            } break;
            case 6: {
                color = ContextCompat.getColor(mContext, R.color.whiteColor);
                holder.left_bar.setBackgroundColor(color);
                paramsLeft.weight = 1;
                holder.left_bar.setLayoutParams(paramsLeft);
                paramsRight.weight = 0;
                holder.right_bar.setLayoutParams(paramsRight);

            } break;
            default: {
                color = ContextCompat.getColor(mContext, R.color.whiteColor);
                holder.left_bar.setBackgroundColor(color);
            } break;
        }

        /** Setting the text of the textView
         * */
        String day = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.Database.DAY));
        holder.daysTextView.setText(day);


        /** Setting the comment button if needed
         *  */
        final String comment = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.Database.COMMENT));

        if (comment == null) {} //Do nothing
        else {
            if (comment.isEmpty()) { //if string is empty, true
                holder.noteButton.setVisibility(View.INVISIBLE);
                holder.noteButton.setOnClickListener(null);
            }
            else {
                holder.noteButton.setVisibility(View.VISIBLE);
                holder.noteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, comment, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    /** ViewHolder class.
     * It will have all views that will be shown
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView daysTextView;
        public final ImageButton noteButton;
        public final FrameLayout left_bar;
        public final FrameLayout right_bar;


        public MyViewHolder(View view) {
            super(view);

            daysTextView = view.findViewById(R.id.tv_list_item);

            noteButton = view.findViewById(R.id.read_note_button);
            noteButton.setVisibility(View.INVISIBLE);

            left_bar = view.findViewById(R.id.left_bar);
            right_bar = view.findViewById(R.id.right_bar);

        }
    }

}
