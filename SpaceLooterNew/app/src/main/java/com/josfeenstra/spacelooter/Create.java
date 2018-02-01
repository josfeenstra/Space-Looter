/*
    Activity: Create
    Author:   Jos Feenstra

    Purpose:  Level creation, interact with gameCore, test and save the level.

    Structure: - when the 'New' button is pressed
                 - prompt the user to give up a name, and a boardsize
                 - if name is correct, make a new game, customly write a gameData string.
                 - Undertake similar steps as GameCore using this gameData, to eventually get a popupated boardView

               - populate a spinner with the items of the game.

               - if a tile of the board is pressed, change its type, according to the item selected in the spinner

               - when the 'save' button is pressed
                 - test if the board makes at least a bit sense (does it have a player, exit, 1 item of treasure)
                 - save the game to sharedpreferences if correct.

 */

package com.josfeenstra.spacelooter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Create extends AppCompatActivity {

    // make sure all functions can refer to these objects
    Board       b;
    CsvReader   csv;
    GridView    boardView;
    AlertDialog dialog;
    int[]       boardViewData;
    Spinner     itemSelect;
    Game.CustomAdapter boardViewAdapter;
    // get the sprite ID links from game
    int sprites[] = Game.sprites;
    String names[] = GeneralData.names;

    // Keep track of the generated Data
    String newLevelName;
    String newLevelData;
    int    newLevelWidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // construct the necessary objects
        b     = new Board();
        csv   = new CsvReader();

        // Cancel -> discard ?
        Button buttonCancel = findViewById(R.id.buttonCreateCancel);
        buttonCancel.setOnClickListener(new onCancelClick());

        // New -> discard ?
        Button buttonNew = findViewById(R.id.buttonCreateNew);
        buttonNew.setOnClickListener(new onNewClick());

        // Save -> is it good ?
        Button buttonSave = findViewById(R.id.buttonCreateSave);
        buttonSave.setOnClickListener(new onSaveClick());

        // set the item select spinner
        ArrayList<ItemData> list=new ArrayList<>();
        for (int i = 0; i < sprites.length; i++) {
            list.add(new ItemData(names[i], sprites[i]));
        }

        itemSelect = findViewById(R.id.spinnerItemSelect);
        SpinnerAdapter adapter=new SpinnerAdapter(this, R.layout.spinner_item, R.id.spinnerImage,list);
        itemSelect.setAdapter(adapter);
        itemSelect.setVisibility(View.GONE);
    }

    /*
        Step 1
        Create new board with a popup, asking for a title, and size.
        These values are checked, and then send to createLevel()
    */
    public void makeNewBoard() {

        // set up popup window view
        AlertDialog.Builder suBuilder = new AlertDialog.Builder(Create.this);
        View suView = getLayoutInflater().inflate(R.layout.popup_create_new, null);

        // get items
        final EditText editText = suView.findViewById(R.id.textNewLevel);
        final Spinner spinner = suView.findViewById(R.id.spinnerBoardSize);
        final Button submit = suView.findViewById(R.id.buttonSubmitNew);

        // set values for spinner adapter
        int MIN_SIZE = 7;
        int MAX_SIZE = 15;
        int lim = MAX_SIZE - MIN_SIZE;
        String[] spinnerEntries = new String[lim];
        final int[] spinnerVal = new int[lim];

        for (int i = 0; i < lim; i++) {
            int curVal = i+ MIN_SIZE;
            spinnerEntries[i] = "" + curVal + " X " + curVal;
            spinnerVal[i] = curVal;
        }

        // make a local clicklistener for the submit button
        class onSubmitClick implements View.OnClickListener {
            @Override
            public void onClick(View v) {

                // get entry
                String newLevelTitle = editText.getText().toString();

                // check if name is filled, then assign name
                if (newLevelTitle.equals("")) {
                    popup("Please enter a name");
                    return;
                }

                // check if the name is avalable by testing the user greated levels sharedpref.
                String ERROR = "error";
                int ERROR2 = -1;
                SharedPreferences ucl = getSharedPreferences(Menu.PREFDATA_UCL, 0);
                String testLevel = ucl.getString(newLevelTitle, ERROR);

                // check if the user hasnt filled in any of the standard 40 levels
                SharedPreferences highscore = getSharedPreferences(Menu.PREFDATA_HIGHSCORE, 0);
                int testLevel2 = highscore.getInt(newLevelTitle + "silver", ERROR2);

                if (!testLevel.equals("" + ERROR) || testLevel2 != ERROR2) {
                    popup("That name is taken.");
                    return;
                }

                // the name cant be too long
                int MAX_TITLE_LENGTH = 20;
                if (newLevelTitle.length() > MAX_TITLE_LENGTH) {
                    popup("That name is too long.");
                    return;
                }

                // assign the selected width to the new width
                int newLevelWidth = spinnerVal[spinner.getSelectedItemPosition()];
                dialog.cancel();

                // actually create the level
                createLevelData(newLevelTitle, newLevelWidth);
            }
        }

        // assign it to the spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, spinnerEntries);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);

        // get items
        submit.setOnClickListener(new onSubmitClick());

        // set up dialog
        dialog = suBuilder.create();
        dialog.setView(suView);
        dialog.show();
    }

    /*
        Step 2
        create the basics of a level by manipulating strings
     */
    public void createLevelData(String name, int width) {

        // make values global
        newLevelData = "";
        newLevelName = name;
        newLevelWidth = width;

        // create level data
        String wall = "" + GeneralData.wall;
        String sep  = "" + GeneralData.csvSep;
        String empty  = "" + GeneralData.empty;

        int height = width;

        // make empty sequence and make wall sequence
        String empSeq = "";
        String wallSeq = "";
        for (int i = 0; i < height - 2; i++ ) {
            wallSeq += wall + sep;
            empSeq += empty + sep;
        }

        for (int i = 0; i < height; i++) {

            // first column
            newLevelData += wall + sep;

            // make a top r & bottom
            if (i == 0 || i == height - 1) {
                newLevelData += wallSeq;
            } else {
                newLevelData += empSeq;
            }
            // last column
            newLevelData += wall + '\n';

        }

        initBoardAndView();
    }


    /*
        Step 3
        create the basics of a level by manipulating strings
    */
    public void initBoardAndView() {

        // set up GameCore
        b.loadStringFirstTime(newLevelData);

        // set up boardview
        boardViewData = b.getBoardViewData();
        boardView = (GridView) findViewById(R.id.boardView);
        boardView.setNumColumns(b.width);

        // set the width / height unit based on screen width
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int screen_width = outMetrics.widthPixels;
        int boardUnit = (screen_width) / b.width;

        // Create an object of CustomAdapter and set Adapter to boardView
        boardViewAdapter = new Game.CustomAdapter(getApplicationContext(), sprites, boardViewData, boardUnit);
        boardView.setAdapter(boardViewAdapter);
        boardView.setOnItemClickListener(new OnTileClick());
        boardView.setColumnWidth(boardUnit);

        // update text of boardtitle
        TextView boardTitle = findViewById(R.id.boardTitle);
        boardTitle.setText(newLevelName);

        // show the item select spinner
        itemSelect.setVisibility(View.VISIBLE);
    }

    // tile click, find spinner's selection, change this tile to that sprite, update board and boardview
    class OnTileClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // animate
            view.startAnimation(AnimationUtils.loadAnimation(Create.this, R.anim.image_click));

            // get the spinner's item, and it corresponding type
            int selId = itemSelect.getSelectedItemPosition();
            char selType = GeneralData.symbols[selId];

            // modulo-like operation to get coordinates
            int x = position;
            int y = 0;
            while (x >= newLevelWidth) {
                x-=newLevelWidth;
                y+=1;
            }

            // perform condition check, to ensure the game wont be broken
            Tile player = b.getObjectByType(GeneralData.player);
            if (player != null && selType == player.type) {
                // only one player can exist
                player.changeType(GeneralData.empty);
            }
            if (selType != GeneralData.exit && selType != GeneralData.wall &&
                    (x == 0 || x == newLevelWidth-1 || y == 0 || y == newLevelWidth-1)) {
                popup("Cannot place that there.");
                return;

            }
            // change type at coordinate
            b.tile[x][y].changeType(selType);

            // commit changes
            updateBoard();
        }
    }

    public void updateBoard() {
        boardViewAdapter.refresh(b.getBoardViewData());
    }

    /*
        The itemSelect adapter data.
    */
    public class ItemData {

        String text;
        Integer imageId;
        public ItemData(String text, Integer imageId){
            this.text=text;
            this.imageId=imageId;
        }

        public String getText(){
            return text;
        }

        public Integer getImageId(){
            return imageId;
        }
    }

    /*
        The itemSelect adapter.
    */
    public class SpinnerAdapter extends ArrayAdapter<ItemData> {
        int groupid;
        Activity context;
        ArrayList<ItemData> list;
        LayoutInflater inflater;
        public SpinnerAdapter(Activity context, int groupid, int id, ArrayList<ItemData>
                list){
            super(context,id,list);
            this.list=list;
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.groupid=groupid;
        }

        public View getView(int position, View convertView, ViewGroup parent ){
            View itemView = inflater.inflate(groupid,parent,false);

            ImageView imageView = itemView.findViewById(R.id.spinnerImage);
            TextView textView = itemView.findViewById(R.id.spinnerText);
            imageView.setImageResource(list.get(position).getImageId());
            textView.setText(list.get(position).getText());

            return itemView;
        }

        public View getDropDownView(int position, View convertView, ViewGroup
                parent){
            return getView(position,convertView,parent);

        }
    }

    /*
        button 1:
        same as back button press, warn that data is lost, and proceed to go back

    */
    public class onCancelClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // check if a board is instanciated yet
            if (newLevelData != null) {

                // send user a confirmation message
                android.support.v7.app.AlertDialog.Builder suBuilder = new android.support.v7.app.AlertDialog.Builder(Create.this, AlertDialog.THEME_HOLO_DARK);
                suBuilder.setMessage("Are you sure? Unsaved data will be lost!").setPositiveButton("YES", dialogResetClickListener)
                        .setNegativeButton("NO", dialogResetClickListener).show();
            } else {
                // go to menu right away
                gotoMenu();
            }

        }


        DialogInterface.OnClickListener dialogResetClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        gotoMenu();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
    }


    /*
        button 2:
        warn data is lost, then proceed to create a new board.
     */
    public class onNewClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // check if a board is instanciated yet
            if (newLevelData != null) {

                // send user a confirmation message
                android.support.v7.app.AlertDialog.Builder suBuilder = new android.support.v7.app.AlertDialog.Builder(Create.this, AlertDialog.THEME_HOLO_DARK);
                suBuilder.setMessage("Are you sure? Unsaved data will be lost!").setPositiveButton("YES", dialogResetClickListener)
                        .setNegativeButton("NO", dialogResetClickListener).show();
            } else {
                // Make new board right away
                makeNewBoard();
            }

        }


        DialogInterface.OnClickListener dialogResetClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        makeNewBoard();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
    }

    /*
        button 3:
        check if the level is valid, everything is present, and then procceed to permanently save the level
    */
    public class onSaveClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // check if a board is instanciated yet
            if (newLevelData != null && checkGameConditions()) {

                // send user a confirmation message
                android.support.v7.app.AlertDialog.Builder suBuilder = new android.support.v7.app.AlertDialog.Builder(Create.this, AlertDialog.THEME_HOLO_DARK);
                suBuilder.setMessage("Save Game?").setPositiveButton("YES", dialogResetClickListener)
                        .setNegativeButton("NO", dialogResetClickListener).show();
            } else {
                // ignore this was pressed
                return;
            }

        }


        DialogInterface.OnClickListener dialogResetClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        SaveNewBoard();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
    }

    public boolean checkGameConditions() {

        if (b.getObjectByType(GeneralData.player) == null) {
            popup("you should add a player");
            return false;
        }

        if (b.getObjectByType(GeneralData.exit) == null) {
            popup("you should add an exit");
            return false;
        }

        if (b.getObjectByType(GeneralData.treasure) == null) {
            popup("you should add some loot");
            return false;
        }

        // if code fell trough, level is valid
        return true;
    }

    /*
        handle back button
    */
    public void SaveNewBoard() {

        // final update for the gamedata
        newLevelData = b.saveToString();

        // save some stuff
        SharedPreferences UserCreatedLevels = getSharedPreferences(Menu.PREFDATA_UCL, 0);
        SharedPreferences.Editor editor = UserCreatedLevels.edit();
        editor.putString(newLevelName,newLevelData);
        editor.commit();

        // feedback
        popup(newLevelName + " saved");
    }

    /*
        handle back button
    */
    @Override
    public void onBackPressed() {
        gotoMenu();
    }

    /*
        go back to menu
    */
    private void gotoMenu() {
        // if dialog is open, close dialog
        if (dialog != null) {
            dialog.cancel();
        }

        // go back to menu
        Intent intent = new Intent(getApplicationContext(), Menu.class);
        startActivity(intent);
        finish();
    }

    /*
        hide toast sintax
     */
    public void popup(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

}
