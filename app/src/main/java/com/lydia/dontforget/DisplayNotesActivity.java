package com.lydia.dontforget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayNotesActivity extends AppCompatActivity {


    private ArrayAdapter<String> adapter;
    final Context context = this;



    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notes);

            //disable the app icon as the Up (back) button
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //get rid of the title
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //set logo instead
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.home_icon);

        //access notes and methods from our globalApp Class
        final MainApp myApplication = (MainApp) getApplicationContext();

        //populate adapater with our shared array in MainApp
        //NOTE: simple list item 1 = Android predefined TextView resource id
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myApplication.getEntryList());

        lv = (ListView) findViewById(R.id.listView1);
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> a, View v, final int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(context);

                adb.setTitle("Delete Item?");

                adb.setMessage("Are you sure you want to delete this note? ");

                adb.setNegativeButton("Cancel", null);

                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //position gives position of item view in adapter, will happen to  equal id also as the first row is also the first item in the list view
                        final int index = position;
                        myApplication.clearItem(index);

                        //repopulate listview adpater with item removed
                        adapter.notifyDataSetChanged();


                    }
                });

                // create alert dialog
                AlertDialog alertDialog = adb.create();

                // show it
                alertDialog.show();

                /**the View hierarchy in Android is represented by a tree. When you
                return true from the onItemLongClick() - it means that the View that
                currently received the event is the true event receiver and the event
                should not be propagated to the other Views in the tree; when you
                return false - you let
                the event be passed to the other Views that may consume it.**/

                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.deleteOne:
                howToDeleteItem();
                break;

            case R.id.deleteAll:
                deleteAll();
                break;

            case R.id.add_note:
                NavUtils.navigateUpFromSameTask(this);
                break;

            case R.id.action_settings:
                settings();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    public void howToDeleteItem() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title
        alertDialogBuilder.setTitle("Delete List Item");

        alertDialogBuilder
                //set dialog message
                .setMessage("Tap and hold to Delete one list item at a time")
                //sets whether this dialog is cancelable with the back button
                .setCancelable(true)
                .setPositiveButton("Okay, got it", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked: clear alert dialog
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    } //end of deleteItem()


    public void deleteAll() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title of alert
        alertDialogBuilder.setTitle("HOLD UP!");

        alertDialogBuilder
                // set dialog message
                .setMessage("Are you SURE you want to delete all of these important notes?")
                //sets whether this dialog is cancelable with the back button
                .setCancelable(false)
                .setPositiveButton("Yes, definitely", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //clear our current list & prefs
                        MainApp myApplication = (MainApp) getApplicationContext();
                        myApplication.clearList();

                        ListView listView1 = (ListView) findViewById(R.id.listView1);
                        listView1.setAdapter(null);
                    }
                })
                .setNegativeButton("No Way Jose", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    } //end of deleteAll()



    public void settings() {

        CharSequence options[] = new CharSequence[] {"\nAdjust Font Size","Order Notes"};

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title of alert
        alertDialogBuilder.setTitle("Settings");

        alertDialogBuilder
                 //sets whether this dialog is cancelable with the back button
                .setCancelable(true)
                .setItems(options, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //which is which of the options user chose

                        switch (which) {
                            //if they chose adjust font size
                            case 0:
                                adjustFont();
                                break;

                            case 1:
                                //user chose to reorder notes
                                order();
                                break;
                        }
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                } //end of settings()


    public void adjustFont(){
        CharSequence fontOptions[] = new CharSequence[] {"\nSmall","Medium", "Large"};

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title of alert
        alertDialogBuilder.setTitle("Font Settings");

        alertDialogBuilder
                //sets whether this dialog is cancelable with the back button
                .setCancelable(true)
                .setItems(fontOptions, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //which is which of the options user chose

                        switch (which) {
                            //if they chose smallt size
                            case 0:
                                //change font to 14sp
                                break;

                            case 1:
                                //user chose medium
                                //change font to 17/18
                                break;

                            case 2:
                                //user chose large
                                //change font to 22/22 sp
                                break;
                        }
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    } //end of adjustFont()


    public void order(){
        //access notes and methods from our globalApp Class
        final MainApp myApplication = (MainApp) getApplicationContext();
        final int state = myApplication.getOrderState();

        CharSequence orderOptions[] = new CharSequence[] {"\nNew to Old","Old to New"};

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title of alert
        alertDialogBuilder.setTitle("Note Settings");

        alertDialogBuilder
                //sets whether this dialog is cancelable with the back button
                .setCancelable(true)
                .setItems(orderOptions, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //which is which of the options user chose

                        switch (which) {
                            //new to old
                            case 0:
                                //if curretnly ordered old to new, reorder
                                if(state==0){
                                 myApplication.reOrder(state);
                                 adapter.notifyDataSetChanged();
                                 }
                                 //else do nothing as its in the right order already!
                                 break;

                            case 1:
                                //user old to new
                                if(state==1){
                                 myApplication.reOrder(state);
                                    adapter.notifyDataSetChanged();
                                 }
                                break;

                        }
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

} // end of class