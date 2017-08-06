package com.lydia.dontforget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class ShowNotesAdapter extends RecyclerView.Adapter<ShowNotesAdapter.ViewHolder> {

    private List<Note> mDataset;
    private RecyclerClickListener clickInterface;

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
    public ShowNotesAdapter(RecyclerClickListener listener, List<Note> myDataset) {
        clickInterface = listener;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ShowNotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_entry_row, parent, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).getEntry());

        if(mDataset.get(position).getImp()==1)
            holder.mStar.setImageResource(R.drawable.focused_star);
        else
            holder.mStar.setImageResource(R.drawable.unfocused_star);


        holder.mStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton star = (ImageButton) v.findViewById(R.id.fav_note);

                if(mDataset.get(position).getImp()==1){
                    star.setImageResource(R.drawable.unfocused_star);
                }
                else{
                    star.setImageResource(R.drawable.focused_star);
                }

                clickInterface.toggleImportant(mDataset.get(position));
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}