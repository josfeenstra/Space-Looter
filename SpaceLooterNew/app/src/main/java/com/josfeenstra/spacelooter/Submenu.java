/*
    Activity: Submenu
    Author:   Jos Feenstra

    Purpose:  Level select, specific navigation, show and measure progress

    Structure: - 5 Catagories: Easy , Medium, Hard, Expert, Custom
                 - each giving a popup window, within it a scrollable list of levels
                 - Per level
                   L display if the level is completed, unlocked or locked
                   L if completed or unlocked, show the number of moves needed to reach a certain score
                   L if the level is completed, show stars according to the lowest "high"score reached on that level

                   L if the level is a custom level
                     L dont show any highscore stats, always unlock it
                     L make sure the levels can be deleted, by clicking and holding down de level.
 */

package com.josfeenstra.spacelooter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

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
        Button buttonCustom = findViewById(R.id.customButton);

        buttonEasy.setOnClickListener(  new onDifficultyClick( 1, 10, (String) buttonEasy.getText()));
        buttonMedium.setOnClickListener(new onDifficultyClick(11, 20, (String) buttonMedium.getText()));
        buttonHard.setOnClickListener(  new onDifficultyClick(21, 30, (String) buttonHard.getText()));
        buttonExpert.setOnClickListener(new onDifficultyClick(31, 40, (String) buttonExpert.getText()));
        buttonCustom.setOnClickListener(new onDifficultyClick(0, 0,   (String) buttonCustom.getText()));

    }

    /*
        show / hide all menu buttons for nice visuals
     */
    public void changeButtonStatus(boolean visible) {

        int[] ALL_ID = {R.id.easyButton, R.id.mediumButton, R.id.hardButton, R.id.expertButton, R.id.customButton};

        for (int id : ALL_ID) {
            Button button = findViewById(id);

            if (visible) {
                button.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.INVISIBLE);
            }

        }

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

            // add animation
            v.startAnimation(AnimationUtils.loadAnimation(Submenu.this, R.anim.image_click));

            // set up popup window
            AlertDialog.Builder suBuilder = new AlertDialog.Builder(Submenu.this);
            View suView = getLayoutInflater().inflate(R.layout.popup_submenu, null);

            // make a cancel button
            suBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    changeButtonStatus(true);
                }
            });

            // let the back button do the same
            suBuilder.setOnKeyListener(new Dialog.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface arg0, int keyCode,
                                     KeyEvent event) {
                    //
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        changeButtonStatus(true);
                        dialog.dismiss();
                    }
                    return true;
                }
            });




            // set up the title
            TextView titleView = suView.findViewById(R.id.popupTitle);
            titleView.setText(titleOfLevels);

            // set up list
            final String STANDARDTITLE = "Level ";
            ArrayList<ListItem> arrayOfListitems = new ArrayList<ListItem>();
            for (int i = firstLevel; i <= lastLevel && firstLevel != 0; i++) {

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
                ListItem newListItem = new ListItem(i, unlocked, completed, moves, null);
                arrayOfListitems.add(newListItem);
            }

            // if its the custom buttom, populate list differently
            if (lastLevel == 0) {

                // load some stuff
                SharedPreferences UserCreatedLevels = getSharedPreferences(Menu.PREFDATA_UCL, 0);

                Map<String, ?> allEntries = UserCreatedLevels.getAll();
                for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

                    // check if its been completed before
                    String name = entry.getKey();
                    SharedPreferences progress = getSharedPreferences(Menu.PREFDATA_NAME, 0);
                    boolean completed = progress.getBoolean("completed" + name, false);
                    int moves = progress.getInt("moves" + name, -1);

                    // make a listitem based upon that data
                    ListItem newListItem = new ListItem(0, true, completed, moves, name);
                    arrayOfListitems.add(newListItem);
                }
            }

            final ArrayList<ListItem> allItems = arrayOfListitems;

            // final preperations
            final subListAdapter adapter = new subListAdapter(getApplicationContext(), arrayOfListitems);
            ListView listView = (ListView) suView.findViewById(R.id.popupList);
            listView.setAdapter(adapter);

            // give it a listener
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // add animation
                    view.startAnimation(AnimationUtils.loadAnimation(Submenu.this, R.anim.image_click));

                    //figure out what thingie is pressed
                    final ListItem item = allItems.get(position);

                    // proceed only if the item is unlocked
                    if (item.unlocked) {

                        // go to that level
                        gotoGame(item.level, item.title);
                    }
                }
            });






            // give it a Long listener if its the custom view, to delete custom levels
            if (lastLevel == 0) {
                // dont make a new longclicklistener class, it needs to access allItems
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    // store the clicked item
                    ListItem item;

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                        //figure out what thingie is pressed
                        item = allItems.get(position);

                        // show alert if you really want to delete
                        AlertDialog.Builder suBuilder = new AlertDialog.Builder(Submenu.this);
                        suBuilder.setMessage("Delete " + item.title + "?").setPositiveButton("YES", dialogResetClickListener)
                                .setNegativeButton("NO", dialogResetClickListener).show();

                        return true;
                    }

                    DialogInterface.OnClickListener dialogResetClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:

                                    //Yes button clicked, remove item from database
                                    SharedPreferences ucl = getSharedPreferences(Menu.PREFDATA_UCL, 0);
                                    SharedPreferences.Editor editor = ucl.edit();
                                    editor.remove(item.title);
                                    editor.commit();

                                    // remove from arrayitems, and update the view
                                    allItems.remove(item);
                                    adapter.update();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                });

            }

            // create the dialog
            dialog = suBuilder.create();
            dialog.setView(suView);

            // change background color
            ColorDrawable dialogColor = new ColorDrawable(Color.BLACK);
            dialogColor.setAlpha(0xAA);
            dialog.getWindow().setBackgroundDrawable(dialogColor);

            // hide buttons
            dialog.setCanceledOnTouchOutside(false);
            changeButtonStatus(false);

            // show dialog
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
        public String title;

        public ListItem(int level, boolean unlocked, boolean completed, int moves, String title) {
            this.level = level;
            this.unlocked = unlocked;
            this.completed = completed;
            this.moves = moves;
            this.title = title;
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
            TextView lvlMoves = (TextView) convertView.findViewById(R.id.yourMoves);
            int yourMoves = item.moves;
            String lvlTitle  = "";

            // get indexes of star reward elements, default everything to invisible
            ImageView star1 = convertView.findViewById(R.id.star1);         star1.setVisibility(View.INVISIBLE);
            TextView starText1 = convertView.findViewById(R.id.star1text);  starText1.setVisibility(View.INVISIBLE);
            ImageView star2 = convertView.findViewById(R.id.star2);         star2.setVisibility(View.INVISIBLE);
            TextView starText2 = convertView.findViewById(R.id.star2text);  starText2.setVisibility(View.INVISIBLE);
            ImageView star3 = convertView.findViewById(R.id.star3);         star3.setVisibility(View.INVISIBLE);
            TextView starText3 = convertView.findViewById(R.id.star3text);  starText3.setVisibility(View.INVISIBLE);

            // Populate the data into the template view using the data object
            if (item.title == null) {
                lvlTitle = "Level " + item.level;
                lvlName.setText(lvlTitle);
            } else {
                lvlName.setText(item.title);
            }

            if(item.unlocked) {
                // item is unlocked but not completed
                lvlName.setAlpha((float) 1);
                lvlComp.setVisibility(View.INVISIBLE);
                lvlMoves.setVisibility(View.INVISIBLE);
            } else {
                // show that the item is locked
                lvlName.setAlpha((float) 0.3);
                lvlComp.setVisibility(View.INVISIBLE);
                lvlMoves.setVisibility(View.INVISIBLE);
            }

            if (item.completed) {

                // show completed stats
                lvlComp.setVisibility(View.VISIBLE);
                lvlMoves.setVisibility(View.VISIBLE);
                lvlName.setAlpha(1);
                lvlMoves.setText("" + yourMoves);
            }

            // show star info if the level is not custom, and the level is avalable
            if (item.title == null && item.unlocked) {

                // default all star stuff to show up
                star1.setVisibility(View.VISIBLE);
                star2.setVisibility(View.VISIBLE);
                star3.setVisibility(View.VISIBLE);
                starText1.setVisibility(View.VISIBLE);
                starText2.setVisibility(View.VISIBLE);
                starText3.setVisibility(View.VISIBLE);

                // get highscore values for stars & image id's
                SharedPreferences highscore = getSharedPreferences(Menu.PREFDATA_HIGHSCORE, 0);
                int silver = highscore.getInt(lvlTitle + "silver", -1);
                int gold   = highscore.getInt(lvlTitle + "gold", -1);
                int starOn = R.drawable.star_on;
                int starOff = R.drawable.star_off;

                // first star is always given. NOTE: listview image bug, image resource had to be manualy assigned
                if (item.completed) {
                    star1.setImageResource(starOn);
                    starText1.setVisibility(View.INVISIBLE);
                } else {
                    star1.setImageResource(starOff);
                    starText1.setText("-");
                }

                // second star is given if yourMoves < silver
                if (item.completed && yourMoves <= silver) {
                    star2.setImageResource(starOn);
                    starText2.setVisibility(View.INVISIBLE);
                } else {
                    star2.setImageResource(starOff);
                    starText2.setText("" + silver);
                }

                // third star is given if yourMoves < gold
                if (item.completed && yourMoves <= gold) {
                    star3.setImageResource(starOn);
                    starText3.setVisibility(View.INVISIBLE);
                } else {
                    star3.setImageResource(starOff);
                    starText3.setText("" + gold);
                }



            }


            // Return the completed view to render on screen
            return convertView;
        }

        public void update() {
            super.notifyDataSetChanged();
        }
    }

    /*
        Route to the game activity
    */
    public void gotoGame(int levelID, String title) {

        // next intent
        Intent intent = new Intent(Submenu.this, Game.class);

        // custom or regular
        if (title == null) {
            intent.putExtra("selectedLevel", levelID);
        } else {
            intent.putExtra("selectedCustomLevel", title);
        }
        startActivity(intent);
        finish();

        // if dialog is open, close dialog
        if (dialog != null) {
            dialog.cancel();
        }
    }

    /*
        Route to the game activity
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



