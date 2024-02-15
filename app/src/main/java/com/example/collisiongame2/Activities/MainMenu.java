package com.example.collisiongame2.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import com.example.collisiongame2.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MainMenu extends AppCompatActivity {

    private Button MENU_BUTTON_SLOW;
    private Button MENU_BUTTON_FAST;

    private Button MENU_BUTTON_SENSOR;

    private Button MENU_BUTTON_BUTTONS;
    private Button MENU_BUTTON_PLAYGAME;

    private boolean  sensors;
    private boolean slow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        findViewsById();
        MENU_BUTTON_SLOW.setOnClickListener(v -> slowButtonClicked());
        MENU_BUTTON_FAST.setOnClickListener(v -> fastButtonClicked());
        MENU_BUTTON_SENSOR.setOnClickListener(v -> sensorsButtonClicked());
        MENU_BUTTON_BUTTONS.setOnClickListener(v -> buttonsButtonClicked());
        MENU_BUTTON_PLAYGAME.setOnClickListener(v -> goToGameActivity());
    }


    private void findViewsById(){


        MENU_BUTTON_SLOW = findViewById(R.id.MENU_BUTTON_SLOW);
        MENU_BUTTON_FAST = findViewById(R.id.MENU_BUTTON_FAST);
        MENU_BUTTON_SENSOR = findViewById(R.id.MENU_BUTTON_SENSOR);
        MENU_BUTTON_BUTTONS = findViewById(R.id.MENU_BUTTON_BUTTONS);
        MENU_BUTTON_PLAYGAME = findViewById(R.id.MENU_BUTTON_PLAYGAME);

    }

    private void slowButtonClicked() {
        slow = true;
        // Change background color of MENU_BUTTON_SLOW when clicked
        MENU_BUTTON_SLOW.setBackgroundColor(Color.RED);
        // Reset background color of MENU_BUTTON_FAST
        MENU_BUTTON_FAST.setBackgroundColor(Color.TRANSPARENT);
    }

    private void fastButtonClicked() {
        slow = false;
        // Change background color of MENU_BUTTON_FAST when clicked
        MENU_BUTTON_FAST.setBackgroundColor(Color.BLUE);
        // Reset background color of MENU_BUTTON_SLOW
        MENU_BUTTON_SLOW.setBackgroundColor(Color.TRANSPARENT);
    }

    private void sensorsButtonClicked() {
        sensors = true;
        // Change background color of MENU_BUTTON_SENSOR when clicked
        MENU_BUTTON_SENSOR.setBackgroundColor(Color.GREEN);
        // Reset background color of MENU_BUTTON_BUTTONS
        MENU_BUTTON_BUTTONS.setBackgroundColor(Color.TRANSPARENT);
    }

    private void buttonsButtonClicked() {
        sensors = false;
        // Change background color of MENU_BUTTON_BUTTONS when clicked
        MENU_BUTTON_BUTTONS.setBackgroundColor(Color.YELLOW);
        // Reset background color of MENU_BUTTON_SENSOR
        MENU_BUTTON_SENSOR.setBackgroundColor(Color.TRANSPARENT);
    }

    private void goToGameActivity(){
        JSONObject valuesToMainActivity = new JSONObject();
        try {
            valuesToMainActivity.put("sensors", sensors);
            valuesToMainActivity.put("slow", slow);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonString = valuesToMainActivity.toString();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("jsonString", jsonString);
        startActivity(intent);

    }

}