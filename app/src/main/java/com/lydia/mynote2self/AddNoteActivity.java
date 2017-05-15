package com.lydia.mynote2self;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class AddNoteActivity extends AppCompatActivity {

    private int intKey;
    private String KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        //get rid of the title
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.home_icon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            goToNotes();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the user clicks the Save button
     */
    public void saveMessage(View view) {
        //get message entered by finding EditText element ID
        EditText saveText = (EditText) findViewById(R.id.edit_message);

        //Assign the text to a local message variable
        String newNote = saveText.getText().toString().trim();

        //clear the input for more notes
        saveText.getText().clear();

        if ((newNote.length() != 0) && (newNote != " ")) {
            //find/create our SP where our notes are saved/will be saved
            SharedPreferences prefs = getSharedPreferences("com.example.notetoself.notes",
                    Context.MODE_PRIVATE);

            //use this to find how many currently exist and let that be newKey,
            // ie if there were zero elements our key is 0 for the first element
            intKey = prefs.getAll().size();

            KEY = Integer.toString(intKey);
            /*once you have the SharedPreferences you can write to them*/
            prefs.edit().putString(KEY, (newNote)).commit();

            //and we add to our list so we dont have to access/load SP moving between activities
            MainApp myApplication = (MainApp) getApplicationContext();
            myApplication.addNote((newNote));
        }
    }

    /**
     * Called when the user clicks the See Notes button
     */
    public void seeNotes(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayNotesActivity.class);
        startActivity(intent);
    }


    public void goToNotes( ){
        Intent intent = new Intent(this, DisplayNotesActivity.class);
        startActivity(intent);
    }

}