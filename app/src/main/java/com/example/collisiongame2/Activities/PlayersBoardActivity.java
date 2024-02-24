package com.example.collisiongame2.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.collisiongame2.Interfaces.CallBack_playerScoreClicked;
import com.example.collisiongame2.Model.Player;
import com.example.collisiongame2.R;
import com.example.collisiongame2.Views.ListFragment;
import com.example.collisiongame2.Views.MapFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class PlayersBoardActivity extends AppCompatActivity {

    private FrameLayout playerBoard_FRAME_list;
    private FrameLayout playerBoard_FRAME_map;
    private ListFragment listFragment;
    private MapFragment mapFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_board);

        findViews();

        String playerListJson = getIntent().getStringExtra("playerListJson");
        Gson gson = new Gson();
        ArrayList<Player> playerList = gson.fromJson(playerListJson, new TypeToken<ArrayList<Player>>(){}.getType());
        Log.d("InFragment", "getDataFromLastActivity: " + playerList.toString());




        listFragment = new ListFragment(playerList);
        listFragment.setCallBackPlayerScoreClicked(new CallBack_playerScoreClicked() {
            @Override
            public void playerScoreClicked(double lat, double lon) {
                mapFragment.zoom(lat, lon);
                //FOR EXAMPLE: 33.73151,85.09194
            }
        });


        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.playerBoard_FRAME_list,listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.playerBoard_FRAME_map,mapFragment).commit();

    }

    private void findViews(){
        playerBoard_FRAME_list = findViewById(R.id.playerBoard_FRAME_list);
        playerBoard_FRAME_map = findViewById(R.id.playerBoard_FRAME_map);
    }



}