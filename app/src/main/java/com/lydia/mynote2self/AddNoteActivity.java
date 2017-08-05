package com.lydia.mynote2self;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;


public class AddNoteActivity extends AppCompatActivity {

    private int intKey;
    private String KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        CheckBox toggleImportant = (CheckBox) findViewById(R.id.important_radioButton);
        toggleImportant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox rv = (CheckBox) v.findViewById(R.id.important_radioButton);

                if(rv.isChecked()){
                    rv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.grey_black));
                }else{
                    rv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                }
                //rv.toggle();
            }});



        ImageButton save = (ImageButton) findViewById(R.id.add_note_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMessage(v);
            }
        });

        RelativeLayout see_all_button = (RelativeLayout) findViewById(R.id.see_all_button);
        see_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNoteActivity.this, DisplayNotesActivity.class);
                startActivity(intent);
            }
        });
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
            return true;
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

            //clear the input for more notes
            saveText.getText().clear();
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

}