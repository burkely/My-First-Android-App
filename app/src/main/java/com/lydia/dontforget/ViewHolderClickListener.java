package com.lydia.dontforget;

import android.view.View;

public interface ViewHolderClickListener {

    void toggleImportant(View v, int position);

    void ToggleRowSelect(View v, int position);

    //void onPositionClicked(int position);

    //void onLongClicked(int position);
}