package com.lydia.mynote2self;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class MainApp extends Application {

    private ArrayList<String> noteEntries = new ArrayList<String>();
    //order of note array - default=1 - ordered from newest to oldest
    private int orderState = 1;
    //default fontsize is MEDIUM=1, small=0, large=2
    private int fontSize = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        //To read or write to SharedPreferences you first need to get/create SharedPrefs
        SharedPreferences prefs = getSharedPreferences("com.example.notetoself.notes",
                Context.MODE_PRIVATE);

        //This is where we read current SP and populate our list of notes
        Map<String, ?> entryMap = prefs.getAll();

        int keyCount = 0;
        String KEY, NOTE;
        //Iterator iter = entryMap.entrySet().iterator();

        while (keyCount<entryMap.size()) {
            KEY = Integer.toString(keyCount);
            //method returns the defaultedValue if KEY does not exist.
            NOTE = prefs.getString(KEY, "No Notes to Display");
            //myApplication.addNote(NOTE);
            noteEntries.add(0, NOTE);
            keyCount++;
        }

    }

    public void addNote(String note) {noteEntries.add(note);}

    public ArrayList<String> getEntryList(){return noteEntries;}

    public void clearList(){
        //clear note array
        noteEntries.clear();

        // clear prefs
        SharedPreferences prefs = getSharedPreferences("com.example.notetoself.notes",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear().commit();
    }

    public void clearItem(int index){

        //remove from Note Array
        noteEntries.remove(index);

        String KEY = Integer.toString(index);

        //To read or write to SharedPreferences you first need to get/create SharedPrefs
        SharedPreferences prefs = getSharedPreferences("com.example.notetoself.notes",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear().commit();

        // now push our new array into cleared prefs
        int keyCount = 0;
        while (keyCount<noteEntries.size()) {
            KEY = Integer.toString(keyCount);
            editor.putString(KEY, noteEntries.get(keyCount));
            editor.commit();
            keyCount++;
        }
    }

    public int getOrderState() {return orderState;}

    public void reOrder(int state){
        //reorder
        Collections.reverse(noteEntries);

        //and change state
        if (state==0) {
            orderState=1;
        }
        else{
            orderState=0;
        }
    }// end of reorder function
}
