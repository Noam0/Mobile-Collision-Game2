package com.example.collisiongame2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.collisiongame2.R;

public class MainMenu extends AppCompatActivity {

    private Button MENU_BUTTON_SLOW;
    private Button MENU_BUTTON_FAST;

    private Button MENU_BUTTON_SENSOR;

    private Button MENU_BUTTON_BUTTONS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }


    private void findViewsById(){


        MENU_BUTTON_SLOW = findViewById(R.id.MENU_BUTTON_SLOW);
        MENU_BUTTON_FAST = findViewById(R.id.MENU_BUTTON_FAST);
        MENU_BUTTON_SENSOR = findViewById(R.id.MENU_BUTTON_SENSOR);
        MENU_BUTTON_BUTTONS = findViewById(R.id.MENU_BUTTON_BUTTONS);

    }
}