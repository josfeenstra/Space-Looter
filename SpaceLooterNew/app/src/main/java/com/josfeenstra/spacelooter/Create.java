package com.josfeenstra.spacelooter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/*
    Ask for settings -> up to 15 x 15, name
    Build exactly that
    Manage the boardview
    make sure new things can be places

    New -> discard ?
    Cancel -> discard ?
    Save -> is it good ?



 */
public class Create extends AppCompatActivity {

    // make sure all functions can refer to these objects
    Board b;
    CsvReader csv;
    GridView boardView;
    AlertDialog dialog;

    // get the sprite ID links from game
    int sprites[] = Game.sprites;

    // Keep track of the generated Data
    String newLevelTitle;
    String newLevelData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // construct the necessary objects
        b     = new Board();
        csv   = new CsvReader();

        // Ask for settings -> up to 15 x 15, name


        // Build exactly that
        setupBoard();
        // Manage the boardview
        // make sure new things can be places


//        // Cancel -> discard ?
//        Button buttonCancel = findViewById(R.id.buttonCreateCancel);
//        buttonCancel.setOnClickListener(onCancelClick);
//
//        // New -> discard ?
//        Button buttonNew = findViewById(R.id.buttonCreateCancel);
//
//        // Save -> is it good ?
//        Button buttonSave = findViewById(R.id.buttonCreateCancel);
//
//
//


    }

    /*
    The boardview adapter. TODO warning copy paste
*/
    public class boardAdapter extends BaseAdapter {
        Context context;
        int sprites[];
        int items[];
        LayoutInflater inflter;
        public boardAdapter(Context applicationContext, int[] sprites, int[] items) {
            this.context = applicationContext;
            this.sprites = sprites;
            this.items = items;
            inflter = (LayoutInflater.from(applicationContext));
        }
        @Override
        public int getCount() {
            return items.length;
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            // inflate a view
            view = inflter.inflate(R.layout.grid_item, null);

            // select and assign correct image
            ImageView sprite = (ImageView) view.findViewById(R.id.sprite);
            int thisIndex = items[i];
            sprite.setImageResource(sprites[thisIndex]);

            return view;
        }

        // update data with new data
        public void refresh(int[] newData) {
            items = newData;
            notifyDataSetChanged();
        }
    }





    /*
        button 1:
        same as back button press, warn that data is lost, and proceed to go back

    */
    public class onCancelClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            gotoMenu();
        }
    }
    /*
        button 2:
        warn data is lost, then proceed to create a new board.
     */
    public class onNewClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    /*
        button 3:
        check if the level is valid, everything is present, and then procceed to permanently save the level
    */
    public class onSaveClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // goto the submenu
            Intent intent = new Intent(Create.this, Menu.class);
            startActivity(intent);
            finish();

        }
    }


    private void setupBoard() {

    }

    /*
        handle back button
    */
    @Override
    public void onBackPressed() {
        // do something on back.
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


}
