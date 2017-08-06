package com.lydia.dontforget;

public interface RecyclerClickListener {

    void toggleImportant(Note note);

    void selectNote(int position);
}