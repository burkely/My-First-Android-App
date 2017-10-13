package com.lydia.dontforget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class AddEntryActivity extends AppCompatActivity {

    DatabaseHandler dbHandler;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        context = this;

        dbHandler = DatabaseHandler.getInstance(context);

        CheckBox toggleImportant = (CheckBox) findViewById(R.id.important_radioButton);
        toggleImportant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox rv = (CheckBox) v.findViewById(R.id.important_radioButton);
                if (rv.isChecked())
                    rv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.grey_black));
                else
                    rv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
            }
        });


        ImageButton save = (ImageButton) findViewById(R.id.add_note_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textBox = (EditText) findViewById(R.id.edit_message);
                //Assign the text to a local message variable
                String entry = textBox.getText().toString().trim();

                if ((entry.length() != 0) && (entry != " ")) {

                    // is imortant checked?
                    CheckBox cBox = (CheckBox) findViewById(R.id.important_radioButton);
                    Boolean important = cBox.isChecked();

                    saveEntry(entry, important);

                    //clear the input for more notes
                    textBox.getText().clear();
                    cBox.setChecked(false);
                    cBox.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.light_grey));
                }
            }
        });

        RelativeLayout see_all_button = (RelativeLayout) findViewById(R.id.see_all_button);
        see_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEntryActivity.this, SeeListActivity.class);
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


    public void saveEntry(String entry, Boolean important) {
        Log.d("tag", "saving note");

        int imp = (important) ? 1 : 0;

        if ((entry.length() != 0) && (entry != " ")) {
            // if there is any text in the text box

            Note note = new Note();
            //default to not important
            note.setImp(imp);
            note.setEntry(entry);

            dbHandler.addNoteDB(note);
        }
    }

}










