package com.lydia.dontforget;

/**
 * Created by Lydia on 29/07/2017.
 */

public class Note {

    //private variables
    int _id;
    String _label;
    String _note;
    int _important;
    int _selected;

    // Empty constructor
    public Note(){}


    // constructor
    public Note(int id, String label, String entry, int imp, int selected){
        this._id = id;
        this._label = label;
        this._note = entry;
        this._important = imp;
        this._selected = selected;
    }

    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting label
    public String getLabel(){
        return this._label;
    }

    // setting label
    public void setLabel(String label){
        this._label = label;
    }

    // getting note
    public String getEntry(){
        return this._note;
    }

    // setting note
    public void setEntry(String note){
        this._note = note;
    }

    // check if important
    public int getImp(){
        return this._important;
    }

    // set if important
    public void setImp(int imp){
        this._important = imp;
    }

    // check if selected
    public int getSelected(){
        return this._selected;
    }

    // set selected
    public void setSelected(int selected){
        this._selected = selected;
    }


}