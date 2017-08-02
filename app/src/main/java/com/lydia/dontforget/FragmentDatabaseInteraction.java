package com.lydia.dontforget;

import java.util.List;

public interface FragmentDatabaseInteraction {

    int getNotesCount();
    void addNote(Note note);
    void updateNote(Note note);
    Note getNote(Note note);
    List<Note> getAllNotes();
    void deleteAllNotes();
    void deleteNote(Note note);
    void toggleImportantStatus(Note note);

}

