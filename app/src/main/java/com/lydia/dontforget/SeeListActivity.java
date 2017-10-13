package com.lydia.dontforget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import static android.R.color.holo_blue_light;

public class SeeListActivity extends AppCompatActivity implements ViewHolderClickListener {

    private RecyclerView rv;
    private ShowNotesAdapter myAdapter;
    private List<Note> recyclerList;
    private Menu menu;
    private int itemsSelected;

    DatabaseHandler dbHandler;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_list);

        context = this;
        itemsSelected = 0;

        rv = (RecyclerView) findViewById(R.id.recyclerViewNotes);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);

        dbHandler = DatabaseHandler.getInstance(context);
        recyclerList = dbHandler.getAllNotesDB();
        myAdapter = new ShowNotesAdapter(this, recyclerList, this);

        rv.setAdapter(myAdapter);

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
            case R.id.deleteOne:
                //howToDeleteItem();
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
            row.setBackgroundColor(Color.WHITE);
            note.setSelected(0);
            itemsSelected--;

        }
        else {
            row.setBackgroundColor(Color.LTGRAY);
            note.setSelected(1);
            itemsSelected++;
        }

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
                .setMessage("Are you SURE you want to delete all of these important notes?")
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
