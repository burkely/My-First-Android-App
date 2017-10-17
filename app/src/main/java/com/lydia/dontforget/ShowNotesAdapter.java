package com.lydia.dontforget;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class ShowNotesAdapter extends RecyclerView.Adapter<ShowNotesAdapter.MyShowNotesViewHolder> {

    private List<Note> mDataset;
    public ViewHolderClickListener listener;
    Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ShowNotesAdapter(Context context, List<Note> myDataset, ViewHolderClickListener clickListener) {
        this.mDataset = myDataset;
        this.listener = clickListener;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyShowNotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_entry_row, parent, false);

        MyShowNotesViewHolder vh = new MyShowNotesViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyShowNotesViewHolder holder, final int position) {

        holder.mTextView.setText(mDataset.get(position).getEntry());

        if(mDataset.get(position).getImp()==1)
            holder.mStar.setImageResource(R.drawable.focused_star);
        else
            holder.mStar.setImageResource(R.drawable.unfocused_star);

        if(mDataset.get(position).getSelected()==1){
            holder.row.setBackgroundColor(Color.LTGRAY);
        }else
            holder.row.setBackgroundColor(
                ResourcesCompat.getColor(context.getResources(),
                                R.color.default_cardview_bg, context.getTheme()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public class MyShowNotesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{

        public TextView mTextView;
        public ImageButton mStar;
        public RelativeLayout row;

        public MyShowNotesViewHolder(final View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.note_entry);
            mStar = (ImageButton) itemView.findViewById(R.id.fav_note);
            row = (RelativeLayout) itemView.findViewById(R.id.row);

            itemView.setOnClickListener(this);
            mStar.setOnClickListener(this);
        }


        // onClick Listener for view
        @Override
        public void onClick(View v) {

            if (v.getId() == mStar.getId()){
                listener.toggleImportant(v, getAdapterPosition());

            } else {
                listener.ToggleRowSelect(v, getAdapterPosition());
            }
        }


        //onLongClickListener for view
        @Override
        public boolean onLongClick(View v) {
            //
            return true;
        }
    }



}