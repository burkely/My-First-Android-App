package com.lydia.dontforget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

/**
 * Created by Lydia on 29/07/2017.
 */

public class MainActivity extends AppCompatActivity implements
            FragmentDatabaseInteraction{

    public DatabaseHandler db;

    private static final String ADD_NOTE_TAB = "add_notes";
    private static final String SEE_NOTES_TAB = "see_notes";
    private static final String IMPORTANT_TAB = "important_notes";

    public static String CURR_TAB;


    // Create new Fragments to be placed in the main_activity layout
    AddNoteFragment addFragment = new AddNoteFragment();
    DisplayNotesFragment seeFragment = new DisplayNotesFragment();
    ImportantNotesFragment importantFragment = new ImportantNotesFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        db = new DatabaseHandler(this);

        // If being restored from a previous state then don't need to do
        // anything or else we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            return;
        }

        //Default to add note tab
        CURR_TAB = ADD_NOTE_TAB;

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, addFragment, ADD_NOTE_TAB)
                .addToBackStack(ADD_NOTE_TAB)
                .commit();

        // Actionbar Operations -----------------------------------
        //disable the app icon as the Up (back) button
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //get rid of the title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ele_actionbar_icon);


        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bmenu_add:
                                if(CURR_TAB != ADD_NOTE_TAB)
                                    switchFrag(addFragment);
                                    CURR_TAB = ADD_NOTE_TAB;
                                break;
                            case R.id.bmenu_see:
                                if(CURR_TAB != SEE_NOTES_TAB)
                                    switchFrag(seeFragment);
                                    CURR_TAB = SEE_NOTES_TAB;
                                break;
                            case R.id.bmenu_important:
                                if(CURR_TAB != IMPORTANT_TAB)
                                    switchFrag(importantFragment);
                                    CURR_TAB = IMPORTANT_TAB;
                                break;
                        }
                        return true;
                    }
                });
    }


    protected void switchFrag(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();

            if (ft != null) {
                    ft.replace(R.id.main_container, fragment)
                            .commit();
                }
            }

        }


    @Override
    public void onDestroy(){
        super.onDestroy();
        // clean up database connection
        db.close();
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
            //goToNotes();
        }

        return super.onOptionsItemSelected(item);
    }


    /*
    Implementing Interface methods
     */

    @Override
    public int getNotesCount(){return db.getNotesCountDB();}

    @Override
    public void addNote(Note note){
        db.addNoteDB(note);
    List<Note> notey = db.getAllNotesDB();
        Log.d("tag", notey.get(0).getEntry().toString());
    }

    @Override
    public void updateNote(Note note){db.updateNoteDB(note);}

    @Override
    public Note getNote(Note note){return db.getNoteDB(note._id);}

    @Override
    public List<Note> getAllNotes(){return db.getAllNotesDB();}

    @Override
    public void deleteAllNotes(){db.deleteAllNotesDB();}

    @Override
    public void deleteNote(Note note){db.deleteNoteDB(note);}

    @Override
    public void toggleImportantStatus(Note note){db.toggleImportantDB(note);}

}

/*  --- debugging ----
        db = new DatabaseHandler(this);
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addNote(new Note("shopping", "9100000000"));
        db.addNote(new Note("shopping", "tampons"));
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Note> contacts = db.getAllNotes();
        for (Note cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getLabel() + " ,Phone: " + cn.getNote();
            // Writing Contacts to log
            Log.d("Name: ", log);        }
*/