package com.lydia.mynote2self;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowNotesAdapter extends RecyclerView.Adapter<ShowNotesAdapter.ViewHolder> {

    private ArrayList<String> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ImageButton mStar;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.note_entry);
            mStar = (ImageButton) view.findViewById(R.id.fav_note);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ShowNotesAdapter(ArrayList<String> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ShowNotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_note_row, parent, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position));

        holder.mStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton star = (ImageButton) v.findViewById(R.id.fav_note);
                if(star.getTag() == "star_off"){
                    star.setTag("star_on");
                    star.setImageResource(android.R.drawable.star_on);
                }
                else{
                    star.setTag("star_off");
                    star.setImageResource(android.R.drawable.star_off);
                }

            }
        });



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}