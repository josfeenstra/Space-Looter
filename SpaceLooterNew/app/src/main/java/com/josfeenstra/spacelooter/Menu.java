/*
    Activity: Menu
    Author:   Jos Feenstra
    Purpose:  Main Menu
              - 3 buttons, Select Level, Make Your Own, Settings
                - Select Level: route to Submenu activity
                - Make your own: route to Createlevel activity
                - Settings: settings popup window.
 */

package com.josfeenstra.spacelooter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    public static final String PREFDATA_NAME = "progress";
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
    }


    // button 1
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

    // button 2
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


    // button 3
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
