package com.lydia.dontforget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class SeeListActivity extends AppCompatActivity implements RecyclerClickListener {

    private RecyclerView rv;
    private RecyclerView.Adapter adapter;

    DatabaseHandler dbHandler;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_list);

        rv = (RecyclerView) findViewById(R.id.recyclerViewNotes);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);

        dbHandler = DatabaseHandler.getInstance(context);
        adapter = new ShowNotesAdapter(this, dbHandler.getAllNotesDB());

        rv.setAdapter(adapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                mLayoutManager.getOrientation());

        dividerItemDecoration.setDrawable(getDrawable(R.drawable.recycler_view_divider));

        rv.addItemDecoration(dividerItemDecoration);

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
                howToDeleteItem();
                break;

            case R.id.deleteAll:
                //deleteAll();
                break;

        }

        return super.onOptionsItemSelected(item);
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



    @Override
    public void toggleImportant(Note note) {
        dbHandler.toggleImportantDB(note);
    }

    @Override
    public void selectNote(int position) {
        //Toast.makme(), Toast.LENGTH_SHORT).show();
    }

}
