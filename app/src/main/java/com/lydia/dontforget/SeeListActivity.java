package com.lydia.dontforget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.List;

public class SeeListActivity extends AppCompatActivity implements ViewHolderClickListener {

    private RecyclerView mRecyclerView;
    private ShowNotesAdapter myAdapter;
    private List<Note> recyclerList;
    private Menu menu;
    private int itemsSelected;
    private static Bundle mBundleRecyclerViewState;
    private final String KEY_RECYCLER_STATE = "recycler_state";

    DatabaseHandler dbHandler;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_list);

        context = this;
        itemsSelected = 0;

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewNotes);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        dbHandler = DatabaseHandler.getInstance(context);
        recyclerList = dbHandler.getAllNotesDB();
        myAdapter = new ShowNotesAdapter(this, recyclerList, this);

        mRecyclerView.setAdapter(myAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(SeeListActivity.this, AddEntryActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPause()
    {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_see_list, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.delete_ic:
                deleteItems();
                break;

            case R.id.deleteAll:
                deleteAll();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void toggleImportant(View v, int position) {
        // Star icon is clicked,
        // mark the message as important
        ImageButton mStar = (ImageButton) v.findViewById(R.id.fav_note);

        Note note = recyclerList.get(position);
        dbHandler.toggleImportantDB(note);

        if(note.getImp()==1)
            mStar.setImageResource(R.drawable.focused_star);
        else
            mStar.setImageResource(R.drawable.unfocused_star);
    }

    @Override
    public void ToggleRowSelect(View v, int position) {

        RelativeLayout row = (RelativeLayout) v.findViewById(R.id.row);

        Note note = recyclerList.get(position);

        if(note.getSelected()== 1) {
            row.setBackgroundColor(ResourcesCompat.getColor(
                    getResources(), R.color.default_cardview_bg, getTheme()));

            note.setSelected(0);
            itemsSelected--;

        }
        else {
            row.setBackgroundColor(Color.LTGRAY);
            note.setSelected(1);
            itemsSelected++;
        }

        toggleDeleteOption();
    }

    public void toggleDeleteOption(){
        MenuItem item = menu.findItem(R.id.delete_ic);
        if (itemsSelected != 0) {
            item.setVisible(true);
        }else
            item.setVisible(false);
    }

    public void deleteAll() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title of alert
        alertDialogBuilder.setTitle("HOLD UP!");

        alertDialogBuilder
                // set dialog message
                .setMessage("Are you SURE you want to delete ALL of these important notes?")
                //sets whether this dialog is cancelable with the back button
                .setCancelable(false)
                .setPositiveButton("Yes, definitely", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHandler.deleteAllNotesDB();
                        recyclerList.clear();
                        myAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No Way Jose", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }


    public void deleteItems() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        if(itemsSelected <= 0){
            // set title of alert
            alertDialogBuilder.setTitle("Delete Item?");
            alertDialogBuilder
                    // set dialog message
                    .setMessage("Whoops no items to delete!")
                    //sets whether this dialog is cancelable with the back button
                    .setCancelable(true)
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
        }

        else if(itemsSelected == 1){
            // set title of alert
            alertDialogBuilder.setTitle("Delete Item");
            alertDialogBuilder
                    // set dialog message
                    .setMessage("Are you sure you want to delete this note?")
                    //sets whether this dialog is cancelable with the back button
                    .setCancelable(false)
                    .setPositiveButton("Yep. Do it", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dbHandler.deleteNoteDB(recyclerList);
                            deleteAdapterItems(recyclerList);
                            itemsSelected = 0;
                            toggleDeleteOption();
                        }
                    })
                    .setNegativeButton("No, thumb slip", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
        }else{
            // set title of alert
            alertDialogBuilder.setTitle("Delete Items");
            alertDialogBuilder
                    // set dialog message
                    .setMessage("Are you sure you want to delete these note?")
                    //sets whether this dialog is cancelable with the back button
                    .setCancelable(false)
                    .setPositiveButton("Ah g'wan", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dbHandler.deleteNoteDB(recyclerList);
                            deleteAdapterItems(recyclerList);
                            itemsSelected = 0;
                            toggleDeleteOption();
                        }
                    })
                    .setNegativeButton("Nah", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });
        }

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    void deleteAdapterItems(List<Note> list) {
        int size = list.size();

        for(int i = 0; i<size; i++){
            if(list.get(i).getSelected()==1){
                list.remove(i);
                size--;
                i--;

                myAdapter.notifyItemRemoved(i);
                myAdapter.notifyItemRangeChanged(i, list.size());
            }
            myAdapter.notifyDataSetChanged();
        }



    }


    public void howToDeleteItem() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Delete List Item");

        alertDialogBuilder
                //set dialog message
                .setMessage("Tap and hold to Delete one list item at a time")
                //sets whether this dialog is cancelable with the back button
                .setCancelable(true)
                .setPositiveButton("Okay, got it", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked: clear alert dialog
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    } //end of deleteItem()


}
