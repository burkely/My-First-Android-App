package com.lydia.mynote2self;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Map;


public class MainApp extends Application {

    private ArrayList<String> noteEntries = new ArrayList<String>();
    //private static Context mContext;

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
            noteEntries.add(NOTE);
            keyCount++;
        }

    }

    public void addNote(String note) {noteEntries.add(note);}

    public ArrayList<String> getEntryList(){
        return noteEntries;
    }

    public void clearList(){
        noteEntries.clear();
    }

    public void clearItem(int index){
        //remove from Note Array
        noteEntries.remove(index);

        String KEY = Integer.toString(index);

        //To read or write to SharedPreferences you first need to get/create SharedPrefs
        SharedPreferences prefs = getSharedPreferences("com.example.notetoself.notes",
                Context.MODE_PRIVATE);
        prefs.edit().remove(KEY).commit();

    }

}
