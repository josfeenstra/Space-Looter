/*
    Activity: Menu
    Author:   Jos Feenstra
    Purpose:  Main Menu, Navigation
              - 3 buttons, Select Level, Make Your Own, remove progress
                - Select Level: route to Submenu activity
                - Make your own: route to Createlevel activity
                - Remove progress: removes the progress
 */

package com.josfeenstra.spacelooter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Menu extends AppCompatActivity {

    public static final String PREFDATA_NAME = "progress";
    public static final String PREFDATA_HIGHSCORE = "high_score";
    public static final String PREFDATA_UCL = "user_created_levels";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // setup buttons
        Button buttonSubmenu = (Button) findViewById(R.id.submenuButton);
        Button buttonCreateLevel = (Button) findViewById(R.id.createLevelButton);
        Button buttonSettings = (Button) findViewById(R.id.settingsButton);

        buttonSubmenu.setOnClickListener(new onSubmenuClick());
        buttonCreateLevel.setOnClickListener(new onCreateLevelClick());
        buttonSettings.setOnClickListener(new onSettingsClick());

        /*  NOTE: for now, the highscore is always loaded into cache, in case its in need of an update)
            The highscore is written and recalled with a csv, because there are a little bit too many variables to hardcore this data.
        */
        // load and save high score
        int[][] vals = loadHighscoreValues();
        SharedPreferences highscore = getSharedPreferences(Menu.PREFDATA_HIGHSCORE, 0);
        SharedPreferences.Editor editor = highscore.edit();
        for(int i = 0; i < vals.length;i++) {
            int[] row = vals[i];
            String name = "Level " + (i + 1);
            editor.putInt(name + "silver", row[0]);
            editor.putInt(name + "gold" , row[1]);
        }
        editor.commit();
    }

    public int[][] loadHighscoreValues() {

        // get the csv, and prepare for reading the file
        int path = R.raw.scores;
        InputStream inputStream = getResources().openRawResource(path);
        String rawLine = "";

        // init limits and data
        int MAX_ROW = 40;
        int MAX_COLUMNS = 3;
        int[][] data = new int[MAX_ROW][MAX_COLUMNS];

        // try to read the file
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        try {

            // per row
            int row = 0;

            while ((rawLine = br.readLine()) != null) {

                // update values
                String num = "";
                int column = 0;

                // per character
                for (int i = 0; i < rawLine.length(); i++) {
                    char c = rawLine.charAt(i);
                    String cc = "" + c;

                    // if its a stop character, save and reset num
                    if (GeneralData.csvSep.equals(cc)) {

                        data[row][column] = Integer.parseInt(num);
                        num = "";
                        column+=1;

                    } else {
                        // continue to build a new number
                        num += cc;
                    }

                }
                row += 1;

            }
        } catch(IOException tartaarsaus) {
            System.out.println("COUDNT READ FILE");
        }
        return data;
    }

    /*
        button 1
     */
    public class onSubmenuClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // add animation
            v.startAnimation(AnimationUtils.loadAnimation(Menu.this, R.anim.image_click));

            // goto the submenu
            Intent intent = new Intent(Menu.this, Submenu.class);
            startActivity(intent);
            finish();
        }
    }

    /*
        button 2
      */
    public class onCreateLevelClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // add animation
            v.startAnimation(AnimationUtils.loadAnimation(Menu.this, R.anim.image_click));

            // goto the submenu
            Intent intent = new Intent(Menu.this, Create.class);
            startActivity(intent);
            finish();

        }
    }


    /*
        button 3
      */
    public class onSettingsClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // add animation
            v.startAnimation(AnimationUtils.loadAnimation(Menu.this, R.anim.image_click));

            AlertDialog.Builder suBuilder = new AlertDialog.Builder(Menu.this);
            suBuilder.setMessage("RESET PROGESS?").setPositiveButton("YES", dialogResetClickListener)
                    .setNegativeButton("NO", dialogResetClickListener).show();
        }

        DialogInterface.OnClickListener dialogResetClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        //Yes button clicked
                        SharedPreferences progress = getSharedPreferences(Menu.PREFDATA_NAME, 0);
                        SharedPreferences.Editor editor = progress.edit();
                        editor.clear();
                        editor.commit();
                        popup("Progress deleted");
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        //No button clicked
                        break;
                }
            }
        };
    }
        /*
         hide toast sintax
    */

    public void popup(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
