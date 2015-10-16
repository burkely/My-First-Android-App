package com.lydia.mynote2self;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class DisplayNotesActivity extends AppCompatActivity {


    private ArrayAdapter<String> adapter;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notes);

            //disable the app icon as the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //get rid of the title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //set logo instead
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.home_icon);

        //access notes and methods from our globalApp Class
        MainApp myApplication = (MainApp) getApplicationContext();

        ArrayList<String> NOTES = new ArrayList<String>(myApplication.getEntryList());

        //simple list item 1 = Android predefined TextView resource id
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, NOTES);

        ListView listView1 = (ListView) findViewById(R.id.listView1);
        listView1.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.deleteAll:
                deleteAll();
                break;

            case R.id.add_note:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the user clicks the Delete button

    public void deleteNotes(View view) {
        //clear prefs
        SharedPreferences prefs = getSharedPreferences("com.example.notetoself.notes",
                Context.MODE_PRIVATE);
        prefs.edit().clear().commit();

        //clear our current list
        MainApp myApplication = (MainApp) getApplicationContext();
        myApplication.clearList();

        ListView listView1 = (ListView) findViewById(R.id.listView1);
        listView1.setAdapter(null);

    }
}*/

    public void deleteAll() {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            // set title
            alertDialogBuilder.setTitle("HOLD UP!");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Are you SURE you want to delete all of these important notes?")
                    .setCancelable(false)
                    .setPositiveButton("Yes, definitely", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked
                            SharedPreferences prefs = getSharedPreferences("com.example.notetoself.notes",
                                    Context.MODE_PRIVATE);
                            prefs.edit().clear().commit();

                            //clear our current list
                            MainApp myApplication = (MainApp) getApplicationContext();
                            myApplication.clearList();

                            ListView listView1 = (ListView) findViewById(R.id.listView1);
                            listView1.setAdapter(null);

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
}