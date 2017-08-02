package com.lydia.dontforget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;


public class AddNoteFragment extends Fragment {

    FragmentDatabaseInteraction fListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_add_note, container, false);

        FloatingActionButton fButton = (FloatingActionButton) view.findViewById(R.id.add_note_fab);

        fButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                saveEntry();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fListener = (FragmentDatabaseInteraction) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FragmetDatabaseInteraction");
        }
    }


    @Override
    public void onStop(){
        super.onStop();
        // TODO maybe save edittext data to shared prefs?
    }


    /*
     * Called when the user clicks the Save button
    */

    public void saveEntry() {
        Log.d("tag", "saving note");

        View view = getView();

        //get message entered by finding EditText element ID
        EditText textBox = (EditText) view.findViewById(R.id.edit_message);

        //Assign the text to a local message variable
        String entry = textBox.getText().toString().trim();

        if ((entry.length() != 0) && (entry != " ")) {
            // if there is any text in the text box

            Note note = new Note();
            //default to not important
            note.setImp(0);
            note.setEntry(entry);

            if(view.findViewById(R.id.add_label_rb) != null){
                RadioButton rb = (RadioButton) view.findViewById(R.id.add_label_rb);
                if (rb.isChecked() != false){
                    // get text from label box
                    //note.setLabel(label)
                }
            }

            fListener.addNote(note);

            //clear the input for more notes
            textBox.getText().clear();
        }
    }


}