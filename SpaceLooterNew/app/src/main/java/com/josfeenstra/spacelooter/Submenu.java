/*
    Activity: Submenu
    Author:   Jos Feenstra
    Purpose:  Level select
              - 5 Catagories: Easy , Medium, Hard, Expert, Custom
              - each giving a popup window, within it a scrollable list of levels

              TODO back arrow must return to menu!
 */

package com.josfeenstra.spacelooter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Submenu extends AppCompatActivity {

    // the currently viewed dialog
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu);

        // setup buttons
        Button buttonEasy   = findViewById(R.id.easyButton);
        Button buttonMedium = findViewById(R.id.mediumButton);
        Button buttonHard   = findViewById(R.id.hardButton);
        Button buttonExpert = findViewById(R.id.expertButton);

        buttonEasy.setOnClickListener(  new onDifficultyClick( 1, 10, (String) buttonEasy.getText()));
        buttonMedium.setOnClickListener(new onDifficultyClick(11, 20, (String) buttonMedium.getText()));
        buttonHard.setOnClickListener(  new onDifficultyClick(21, 30, (String) buttonHard.getText()));
        buttonExpert.setOnClickListener(new onDifficultyClick(31, 40, (String) buttonExpert.getText()));

        // setup special custom buttom
        Button buttonCustom = findViewById(R.id.customButton);
        buttonCustom.setOnClickListener(new onCustomClick());

    }

    /*
        Make the back button go back in the app
     */
    @Override
    public void onBackPressed() {
        // do something on back.
        gotoMain();
        return;
    }

    /*
        Show a subsection of all standard levels
     */
    public class onDifficultyClick implements View.OnClickListener {

        int firstLevel;
        int lastLevel;
        String titleOfLevels;

        public onDifficultyClick(int from, int to, String title) {
            firstLevel = from;
            lastLevel = to;
            titleOfLevels = title;
        }

        @Override
        public void onClick(View v) {

            // set up popup window
            AlertDialog.Builder suBuilder = new AlertDialog.Builder(Submenu.this);
            View suView = getLayoutInflater().inflate(R.layout.popup_submenu, null);

            // make a cancel button
            suBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            // set up the title
            TextView titleView = suView.findViewById(R.id.popupTitle);
            titleView.setText(titleOfLevels);

            // set up list
            final String STANDARDTITLE = "Level ";
            ArrayList<ListItem> arrayOfListitems = new ArrayList<ListItem>();
            for (int i = firstLevel; i <= lastLevel; i++) {

                // find out if the level has been played before
                String name = STANDARDTITLE+i;
                SharedPreferences progress = getSharedPreferences(Menu.PREFDATA_NAME, 0);
                boolean completed = progress.getBoolean("completed" + name, false);
                int moves = progress.getInt("moves" + name, -1);

                // determine if the item should be locked or unlocked
                boolean unlocked;
                if (i == firstLevel) {
                    // unlock the level if its the first one in the sublist
                    unlocked = true;
                } else {
                    // unlock level if the previous one is completed
                    unlocked = arrayOfListitems.get(i- firstLevel - 1).completed;
                }

                // make a listitem based upon that data
                ListItem newListItem = new ListItem(i, unlocked, completed, moves);
                arrayOfListitems.add(newListItem);
            }
            final ArrayList<ListItem> allItems = arrayOfListitems;

            // final preperations
            subListAdapter adapter = new subListAdapter(getApplicationContext(), arrayOfListitems);
            ListView listView = (ListView) suView.findViewById(R.id.popupList);
            listView.setAdapter(adapter);

            // give it a listener
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //figure out what thingie is pressed
                    final ListItem item = allItems.get(position);
                    int selectedLevel = item.level;

                    // proceed only if the item is unlocked
                    if (item.unlocked) {

                        // go to that level
                        gotoGame(selectedLevel);
                    }
                }
            });

            // create and show the dialog
            dialog = suBuilder.create();
            dialog.setView(suView);
            dialog.show();
        }
    }
    /*
        Class representing 1 item in the level select adapter
     */
    public class ListItem {
        public int level;
        public boolean completed;
        public boolean unlocked;
        public int moves;

        public ListItem(int level, boolean unlocked, boolean completed, int moves) {
            this.level = level;
            this.unlocked = unlocked;
            this.completed = completed;
            this.moves = moves;
        }
    }


    /*
        Adapter of the level select list per difficulty
     */
    public class subListAdapter extends ArrayAdapter<ListItem> {
        public subListAdapter(Context context, ArrayList<ListItem> listItems) {
            super(context, 0, listItems);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Get the data item for this position
            ListItem item = getItem(position);

            // inflate screen if the item is not recycled
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.popup_submenu_listitem, parent, false);
            }

            // Lookup view for data population
            TextView lvlName = (TextView) convertView.findViewById(R.id.popupListitemText);
            TextView lvlComp = (TextView) convertView.findViewById(R.id.popupListitemCompleted);

            // Populate the data into the template view using the data object
            lvlName.setText("Level " + item.level);
            if (item.completed) {
                lvlComp.setText("Completed in " + item.moves + " moves!");
            } else if(!item.unlocked) {
                // show that the item is locked
                //lvlName.setTextColor(getResources().getColor(R.color.colorHalfAccent));
                lvlComp.setText("Locked");
                convertView.setEnabled(false);
            }
            // Return the completed view to render on screen
            return convertView;
        }
    }

    /*
        Show all customly created levels
     */
    public class onCustomClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "TODO", Toast.LENGTH_SHORT).show();
        }
    }

    /*
        Route to the game activity
    */
    public void gotoGame(int levelID) {

        // next intent
        Intent intent = new Intent(Submenu.this, Game.class);
        intent.putExtra("selectedLevel", levelID);
        startActivity(intent);
        finish();

        // if dialog is open, close dialog
        if (dialog != null) {
            dialog.cancel();
        }
    }

    public void gotoCustomGame(int levelID) {

        // next intent
        Intent intent = new Intent(Submenu.this, Game.class);
        intent.putExtra("selectedLevel", -1);
        startActivity(intent);
        finish();

        // if dialog is open, close dialog
        if (dialog != null) {
            dialog.cancel();
        }
    }

    /*
        Route to the game activity
        TODO WARNING THIS IS COPY PASTE
    */
    public void gotoMain() {

        // if dialog is open, close dialog
        if (dialog != null) {
            dialog.cancel();
        }

        // go back to menu
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);
        finish();
    }
}



