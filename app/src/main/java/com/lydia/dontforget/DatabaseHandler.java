package com.lydia.dontforget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lydia on 29/07/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // make sure we only have on instance to avoid leaks/no writes
    private static DatabaseHandler mInstance = null;
    private Context mCtx;
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    public static final String DATABASE_NAME = "notes_db.db";
    // Table name
    public static final String TABLE_NAME = "notes";
    // Database column names
    public static final String PRIMARY_ID = "pk_id";
    public static final String LABEL = "label";
    public static final String NOTE_ENTRY = "note_entry";
    public static final String IMPORTANT = "important";

    public static DatabaseHandler getInstance(Context ctx) {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://developer.android.com/resources/articles/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new DatabaseHandler(ctx.getApplicationContext());
        }
        return mInstance;
    }


    // No default constructor, must explicitly define db name & version
    private DatabaseHandler(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.mCtx = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + PRIMARY_ID + " INTEGER PRIMARY KEY," + LABEL + " TEXT,"
                + NOTE_ENTRY + " TEXT," + IMPORTANT + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    // region CRUD methods -----------------------------------------------------

    // Adding new note
    public void addNoteDB(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LABEL, note.getLabel()); // note label
        values.put(NOTE_ENTRY, note.getEntry()); // note content
        values.put(IMPORTANT, note.getImp());
        // no need to add id -- SQLite auto increments and adds PK id

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        //db.close(); // Closing database connection
    }

    // Getting single note
    // gets specific row via row id
    public Note getNoteDB(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { PRIMARY_ID,
                        LABEL, NOTE_ENTRY, IMPORTANT}, PRIMARY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            Log.d("tag cursor", "moving to first");
        }

        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)));

        // return note
        return note;

    }

    // Getting all notes
    public List<Note> getAllNotesDB() {
        List<Note> noteList = new ArrayList<Note>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // loop through all rows adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID(Integer.parseInt(cursor.getString(0)));
                note.setLabel(cursor.getString(1));
                note.setEntry(cursor.getString(2));
                note.setImp(Integer.parseInt(cursor.getString(3)));

                // Adding contact to list
                noteList.add(note);
            } while (cursor.moveToNext());
        }

        //db.close(); // Closing database connection
        // return notes list
        return noteList;
    }

    // Getting notes Count
    public int getNotesCountDB() {

        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single note
    public int updateNoteDB(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LABEL, note.getLabel());
        values.put(NOTE_ENTRY, note.getEntry());
        values.put(IMPORTANT, note.getImp());

        // updating row
        return db.update(TABLE_NAME, values, PRIMARY_ID + " = ?",
                new String[] { String.valueOf(note.getID()) });
    }

    // Deleting single note
    public void deleteNoteDB(List<Note> notes) {

        SQLiteDatabase db = this.getWritableDatabase();

        int size = notes.size();

        for(int i=0; i<size; i++){
            db.delete(TABLE_NAME, PRIMARY_ID + " = ?",
                    new String[] { String.valueOf(notes.get(i).getID()) });
        }
    }

    // Deleting all notes
    public void deleteAllNotesDB() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        //db.close();
    }

    // toggle important status
    public void toggleImportantDB(Note note) {
        Log.d("Tag1", "toggling note importance");

        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("tag2", "NB status: " + Integer.toString(note.getImp()));

        if(note._important==0)
            note.setImp(1);
        else
            note.setImp(0);

        updateNoteDB(note);

    }


    // endregion ---------------------------------------------------------------
}
