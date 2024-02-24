package com.example.collisiongame2.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.collisiongame2.Model.AllPlayers;
import com.example.collisiongame2.Model.Player;
import com.example.collisiongame2.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {

    private AppCompatButton MENU_BUTTON_SLOW;
    private AppCompatButton MENU_BUTTON_FAST;

    private AppCompatButton MENU_BUTTON_SENSOR;

    private AppCompatButton MENU_BUTTON_BUTTONS;
    private AppCompatButton MENU_BUTTON_PLAYGAME;
    private AppCompatButton MENU_BUTTON_SCOREBOARD;
    private boolean  sensors;
    private boolean slow;

    //location of the user:
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    //for the player location:
    private double lat;
    private double lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getLocationPermissionFromUser();

        findViewsById();
        MENU_BUTTON_SLOW.setOnClickListener(v -> slowButtonClicked());
        MENU_BUTTON_FAST.setOnClickListener(v -> fastButtonClicked());
        MENU_BUTTON_SENSOR.setOnClickListener(v -> sensorsButtonClicked());
        MENU_BUTTON_BUTTONS.setOnClickListener(v -> buttonsButtonClicked());
        MENU_BUTTON_PLAYGAME.setOnClickListener(v -> goToGameActivity());
        MENU_BUTTON_SCOREBOARD.setOnClickListener(v -> goToPlayersBoardActivity());
    }



    private void getLocationPermissionFromUser() {
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request the permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }else{

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                // Got last known location. In some rare situations, this can be null.
                                lat = location.getLatitude();
                                lon = location.getLongitude();

                                //Log.d("lat and lon", "lat" + lat + "lon" + lon);
                            }
                        }
                    });
                }

        }


    private void findViewsById(){


        MENU_BUTTON_SLOW = findViewById(R.id.MENU_BUTTON_SLOW);
        MENU_BUTTON_FAST = findViewById(R.id.MENU_BUTTON_FAST);
        MENU_BUTTON_SENSOR = findViewById(R.id.MENU_BUTTON_SENSOR);
        MENU_BUTTON_BUTTONS = findViewById(R.id.MENU_BUTTON_BUTTONS);
        MENU_BUTTON_PLAYGAME = findViewById(R.id.MENU_BUTTON_PLAYGAME);
        MENU_BUTTON_SCOREBOARD = findViewById(R.id.MENU_BUTTON_SCOREBOARD);

    }

    private void slowButtonClicked() {
        slow = true;
        // Change background color of MENU_BUTTON_SLOW when clicked
        MENU_BUTTON_SLOW.setBackgroundColor(ContextCompat.getColor(this, R.color.menu_buttons_color_clicked));
        // Reset background color of MENU_BUTTON_FAST
        MENU_BUTTON_FAST.setBackgroundColor(ContextCompat.getColor(this, R.color.menu_buttons_color));
    }

    private void fastButtonClicked() {
        slow = false;
        // Change background color of MENU_BUTTON_FAST when clicked
        MENU_BUTTON_FAST.setBackgroundColor(ContextCompat.getColor(this, R.color.menu_buttons_color_clicked));
        // Reset background color of MENU_BUTTON_SLOW
        MENU_BUTTON_SLOW.setBackgroundColor(ContextCompat.getColor(this, R.color.menu_buttons_color));
    }

    private void sensorsButtonClicked() {
        sensors = true;
        // Change background color of MENU_BUTTON_SENSOR when clicked
        MENU_BUTTON_SENSOR.setBackgroundColor(ContextCompat.getColor(this, R.color.menu_buttons_color_clicked));
        // Reset background color of MENU_BUTTON_BUTTONS
        MENU_BUTTON_BUTTONS.setBackgroundColor(ContextCompat.getColor(this, R.color.menu_buttons_color));
    }

    private void buttonsButtonClicked() {
        sensors = false;
        // Change background color of MENU_BUTTON_BUTTONS when clicked
        MENU_BUTTON_BUTTONS.setBackgroundColor(ContextCompat.getColor(this, R.color.menu_buttons_color_clicked));
        // Reset background color of MENU_BUTTON_SENSOR
        MENU_BUTTON_SENSOR.setBackgroundColor(ContextCompat.getColor(this, R.color.menu_buttons_color));
    }

    private void goToGameActivity(){
        JSONObject valuesToMainActivity = new JSONObject();
        try {
            valuesToMainActivity.put("sensors", sensors);
            valuesToMainActivity.put("slow", slow);
            valuesToMainActivity.put("lat", lat);
            valuesToMainActivity.put("lon", lon);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonString = valuesToMainActivity.toString();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("jsonString", jsonString);
        startActivity(intent);

    }

    private void goToPlayersBoardActivity(){

        ArrayList<Player> allPlayers;
        AllPlayers.getInstance().sortPlayersByScore();
        allPlayers = AllPlayers.getInstance().getAllPlayersList();

        Gson gson = new Gson();
        String AllPlayers = gson.toJson(allPlayers);
        Intent intent = new Intent(this, PlayersBoardActivity.class);
        intent.putExtra("playerListJson", AllPlayers);
        startActivity(intent);

    }



}