package com.josfeenstra.spacelooter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class Game extends AppCompatActivity {

    int thisLevel;
    int[] boardViewData;
    String levelName;
    AlertDialog dialog;
    GridView boardView;
    TextView movesUsed;
    boolean isCustom;
    CustomAdapter boardViewAdapter;
    Board b;
    CsvReader csv;

    static int sprites[] = {R.drawable.tile, R.drawable.wall,
                     R.drawable.player, R.drawable.treasure,
                     R.drawable.blockh, R.drawable.blockv,
                     R.drawable.exit};

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // construct the necessary objects from the gameCore
        b     = new Board();
        csv   = new CsvReader();

        // get the level data
        String level = findLevel();

        // only procceed if we have found something
        if (level == null) {
            popup("Couldn't read the file.");
            gotoMain();
        }

        // set up GameCore
        b.loadStringFirstTime(level);

        // set up boardview
        boardViewData = b.getBoardViewData();
        boardView = (GridView) findViewById(R.id.boardView);

        // set the width / height unit based on screen width
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int screen_width = outMetrics.widthPixels;
        int BOARD_PADDING = 40;
        int boardUnit = (screen_width - BOARD_PADDING) / b.width;

        // Create an object of CustomAdapter and set Adapter to boardView
        boardViewAdapter = new CustomAdapter(getApplicationContext(), sprites, boardViewData, boardUnit);
        boardView.setAdapter(boardViewAdapter);
        boardView.setNumColumns(b.width);
        boardView.setColumnWidth(boardUnit);

        // set up title
        TextView title = findViewById(R.id.boardTitle);
        if (!isCustom) {
            // set the name of the level
            levelName = "Level " + thisLevel;
        }
        title.setText(levelName);

        // set up controls
        int[] allButtonId = {R.id.buttonUp, R.id.buttonLeft, R.id.buttonDown, R.id.buttonRight, R.id.buttonBack}; // R.id.buttonMenu
        for (int id : allButtonId) {
            ImageButton button = findViewById(id);
            button.setOnClickListener(new onGameClick());
        }

        ImageButton reset = findViewById(R.id.buttonReset);
        reset.setOnClickListener(new onResetBoard());

        // set up tracker of moves
        movesUsed = findViewById(R.id.textUsedMoves);
        movesUsed.setText("0");

    }

    private String findLevel() {

        // find the level
        Intent intent = getIntent();
        thisLevel = intent.getIntExtra("selectedLevel", 0);

        if (thisLevel != 0) {

            isCustom = false;

            //convert it to a level name
            String levelName = "raw/level" + thisLevel;

            // find the corresponding resource id
            Context context = getApplicationContext();

            // read the corresponding csv file
            int selectedLevel = getResources().getIdentifier(levelName, "raw", context.getPackageName());
            Log.d("tag", "" + selectedLevel);
            InputStream inputStream = getResources().openRawResource(selectedLevel);

            // convert the raw csv data to a csv-like string which the gameCore can use
            return csv.load(inputStream);

        } else {

            isCustom = true;
            // custom procedures
            String thisLevelTitle = intent.getStringExtra("selectedCustomLevel");

            // load User created levels data
            SharedPreferences ucl = getSharedPreferences(Menu.PREFDATA_UCL, 0);

            //
            levelName = thisLevelTitle;

            // get the level out of the preferences
            return ucl.getString(thisLevelTitle, "");



        }
    }

    // handle back button
    @Override
    public void onBackPressed() {
        // do something on back.
        gotoSubmenu();
    }

    /*
        The boardview adapter.
    */
    public static class CustomAdapter extends BaseAdapter {
        Context context;
        int sprites[];
        int logos[];
        LayoutInflater inflter;
        int boardUnit;
        public CustomAdapter(Context applicationContext, int[] sprites, int[] logos, int boardUnit) {
            this.context = applicationContext;
            this.sprites = sprites;
            this.logos = logos;
            this.boardUnit = boardUnit;
            inflter = (LayoutInflater.from(applicationContext));
        }
        @Override
        public int getCount() {
            return logos.length;
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
            view = inflter.inflate(R.layout.board_item, null);

            // select and assign correct image
            ImageView sprite = (ImageView) view.findViewById(R.id.sprite);
            int thisIndex = logos[i];
            sprite.setImageResource(sprites[thisIndex]);

            // resize imageview according to screen
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(boardUnit, boardUnit);
            view.setLayoutParams(layoutParams);

            return view;
        }

        // update data with new data
        public void refresh(int[] newData) {
            logos = newData;
            notifyDataSetChanged();
        }
    }

    /*
        Handle the win condition events
     */
    public void checkWincondition() {
        if(b.isGameWon()) {
           
            // save the fact that the game is won, and with how many steps
            int moves = b.boardHistoryState;
            SharedPreferences progress = getSharedPreferences(Menu.PREFDATA_NAME, 0);
            SharedPreferences.Editor editor = progress.edit();
            editor.putBoolean("completed" + levelName, true);

            // only update moves if its better or if it doesnt exist yet
            int existingMoves = progress.getInt("moves" + levelName, -1);
            if (moves < existingMoves || existingMoves == -1) {
                editor.putInt("moves" + levelName, moves);
            }
            editor.commit();

            // set up popup window view
            AlertDialog.Builder suBuilder = new AlertDialog.Builder(Game.this);
            View suView = getLayoutInflater().inflate(R.layout.popup_game_completion, null);

            // set up title
            TextView title = suView.findViewById(R.id.completeTitle);
            title.setText("Level " + thisLevel + " complete!");

            // set up subtitle
            TextView textMovesUsed = suView.findViewById(R.id.textMovesUsed);
            textMovesUsed.setText("You've used " + moves + " moves.");

            // set up buttons
            Button nextLevel = suView.findViewById(R.id.buttonNextLevel);
            Button retry = suView.findViewById(R.id.buttonRetry);
            Button backToMenu = suView.findViewById(R.id.buttonBackToMenu);
            nextLevel.setOnClickListener(new onGoToNextLevel());
            backToMenu.setOnClickListener(new onGoToMain());
            retry.setOnClickListener(new onRetryLevel());

            // hide buttons for the custom games
            if (isCustom) {
                title.setText("Custom level complete!");
                nextLevel.setVisibility(View.GONE);
                retry.setVisibility(View.GONE);
            } else {

                // express high score stats in stars
                String lvlTitle = "Level " + thisLevel;
                SharedPreferences highscore = getSharedPreferences(Menu.PREFDATA_HIGHSCORE, 0);
                int silver = highscore.getInt(lvlTitle + "silver", -1);
                int gold   = highscore.getInt(lvlTitle + "gold", -1);

            }




            // set up dialog
            dialog = suBuilder.create();
            dialog.setView(suView);
            dialog.show();
        }
    }

    public class onResetBoard implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            AlertDialog.Builder suBuilder = new AlertDialog.Builder(Game.this );
            suBuilder.setMessage("RESET LEVEL?").setPositiveButton("YES", dialogResetClickListener)
                    .setNegativeButton("NO", dialogResetClickListener).show();
        }

        DialogInterface.OnClickListener dialogResetClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        //Yes button clicked
                        handleGameInput(b.reset);

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

    }

    /*
        Go back to main menu.
     */
    public class onGoToMain implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // go to the main screen
            gotoSubmenu();
        }
    }

    /*
        Go to the next level listener
     */
    public class onGoToNextLevel implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // make last level condition
            gotoGame(thisLevel + 1);

        }
    }

    /*
        Retry this level
    */
    public class onRetryLevel implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // re-initiate screen but with the same level
            gotoGame(thisLevel);
        }
    }

    /*
         handle all game related button presses
     */
    public class onGameClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // find out which button is pressed
            int buttonID;
            switch (v.getId()) {
                case R.id.buttonUp:
                    buttonID = b.up;
                    break;
                case R.id.buttonDown:
                    buttonID = b.down;
                    break;
                case R.id.buttonLeft:
                    buttonID = b.left;
                    break;
                case R.id.buttonRight:
                    buttonID = b.right;
                    break;
                case R.id.buttonBack:
                    buttonID = b.back;
                    break;
                default:
                    buttonID = -1;
            }

            // now that we know which button is pressed, use the general game input screen
            handleGameInput(buttonID);
        }
    }

    /*
        hide toast sintax
    */
    public void handleGameInput(int buttonID) {

        // put buttonID into the game input
        int feedback = b.gameInput(buttonID);

        // play a sound according to the feedback
        switch(feedback) {
            case -1:   b.print("error");
                break;
            case 0:    b.print("*tok*");
                break;
            case 1:    b.print("*step...*");
                break;
            case 2:    b.print("*schhuif...*");
                break;
            case 3:    b.print("*CA-CHING!*");
                break;
            case 10:   b.print("*Wha wha*");
                break;
            case 11:   b.print("*Whoooshh*");
                break;

        }
        // update the board
        boardViewData = b.getBoardViewData();
        boardViewAdapter.refresh(boardViewData);

        // update the movesused tracker
        String moves = "" + b.boardHistoryState;
        movesUsed.setText(moves);

        // check if the game is won
        checkWincondition();

    }

    /*
        hide toast sintax
     */
    public void popup(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    /*
        Route to the game activity
    */
    public void gotoGame(int levelID) {

        // next intent
        Intent intent = new Intent(getApplicationContext(), Game.class);
        intent.putExtra("selectedLevel", levelID);
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

    /*
        Route to the game activity
    */
    private void gotoSubmenu() {
        // if dialog is open, close dialog
        if (dialog != null) {
            dialog.cancel();
        }

        // go back to menu
        Intent intent = new Intent(getApplicationContext(), Submenu.class);
        startActivity(intent);
        finish();
    }



}
