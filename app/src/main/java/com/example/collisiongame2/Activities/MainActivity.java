package com.example.collisiongame2.Activities;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.collisiongame2.Logic.GameManager;

import com.example.collisiongame2.Model.AllPlayers;
import com.example.collisiongame2.Model.Obstacle;
import com.example.collisiongame2.Model.Player;
import com.example.collisiongame2.R;
import com.example.collisiongame2.Utilities.SharedPreferencesManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private ShapeableImageView main_IMG_character;
    private MaterialTextView main_LBL_score;
    private ShapeableImageView[] main_IMG_hearts;
    private GridLayout MAIN_LAYOUT_GRID;
    private RelativeLayout[][] relativeLayouts;
    private FloatingActionButton main_button_left;
    private FloatingActionButton main_button_right;

    //cardVIEW Variables:
    private MaterialButton MAIN_CARDVIEW_BTN_OK;
    private CardView MAIN_GAMEOVER_CARDVIEW;
    private MaterialTextView MAIN_LBLCARD_FINALSCORE;
    private AppCompatEditText MAIN_EDITTEXT_NAME;
    private GameManager gameManager;
    AllPlayers allPlayers;
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean isGameRunning = true;
    private static final String TAG = "ObstacleMovement";
    private boolean sensors;
    private boolean slow;
    private int speedOfObjects;
    private final int SLOWMODE = 800;
    private final int FASTMODE = 450;
    private final int VIBRATIONTIMING = 300;

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        relativeLayouts = getAllRelativeLayouts(); //find all relativeLayouts from the grid layout matrix
        getGameMode();
        adjustGameMode();
        showCardOfEndGame(false);
        main_button_left.setOnClickListener(view -> moveLeft());
        main_button_right.setOnClickListener(view -> moveRight());
        MAIN_CARDVIEW_BTN_OK.setOnClickListener(view -> savePlayerScoreAndGoToPlayersActivity());
        gameManager = new GameManager();
        AllPlayers allPlayers = AllPlayers.getInstance();
        startGameLoop();


    }


    public void findViews() {
        main_IMG_character = findViewById(R.id.main_IMG_character);
        // main_IMG_obstacle = findViewById(R.id.main_IMG_Obstacle);
        MAIN_LAYOUT_GRID = findViewById(R.id.MAIN_LAYOUT_GRID);
        main_button_left = findViewById(R.id.main_button_left);
        main_button_right = findViewById(R.id.main_button_right);
        main_LBL_score = findViewById(R.id.main_LBL_score);

        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
        MAIN_GAMEOVER_CARDVIEW = findViewById(R.id.MAIN_GAMEOVER_CARDVIEW);
        MAIN_LBLCARD_FINALSCORE = findViewById(R.id.MAIN_LBLCARD_FINALSCORE);
        MAIN_CARDVIEW_BTN_OK = findViewById(R.id.MAIN_CARDVIEW_BTN_OK);
        MAIN_EDITTEXT_NAME = findViewById(R.id.MAIN_EDITTEXT_NAME);
    }

    private void getGameMode() {
        Intent intent = getIntent();
        String jsonString = intent.getStringExtra("jsonString");
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            sensors = jsonObject.getBoolean("sensors");
            slow = jsonObject.getBoolean("slow");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void adjustGameMode() {
        if (slow) {
            speedOfObjects = SLOWMODE;
        } else {
            speedOfObjects = FASTMODE;
        }
        if (sensors) {

        }
    }


    private RelativeLayout[][] getAllRelativeLayouts() {
        int rowCount = MAIN_LAYOUT_GRID.getRowCount();
        int columnCount = MAIN_LAYOUT_GRID.getColumnCount();

        RelativeLayout[][] relativeLayouts = new RelativeLayout[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                int linearIndex = i * columnCount + j;
                View childView = MAIN_LAYOUT_GRID.getChildAt(linearIndex);

                if (childView instanceof RelativeLayout) {
                    relativeLayouts[i][j] = (RelativeLayout) childView;
                    Log.d(TAG, "i = " + i + "j = " + j);

                }
            }
        }

        return relativeLayouts;
    }


    public void moveRight() {
        int currentLane = gameManager.getMainCharacter().getPositionX();
        int newLane = currentLane + 1;


        if (newLane < gameManager.getNumberOfLanes()) {
            changeLayoutOfMainCharacterIMG(currentLane, newLane);
            // Update the lane in the game manager
            gameManager.getMainCharacter().setPositionX(newLane);
            //check if the main character gets into one of the obstacles
            if (gameManager.checkCollision()) {
                collisionHappendUI(gameManager.isLastCollisionByTerrorist());
            }

        }
    }

    public void moveLeft() {

        int currentLane = gameManager.getMainCharacter().getPositionX();
        int newLane = currentLane - 1;


        if (newLane >= 0) {
            changeLayoutOfMainCharacterIMG(currentLane, newLane);
            // Update the lane in the game manager
            gameManager.getMainCharacter().setPositionX(newLane);
            //check if the main character gets into one of the obstacles

            if (gameManager.checkCollision()) {
                collisionHappendUI(gameManager.isLastCollisionByTerrorist());
            }

        }


    }


    private void startGameLoop() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isGameRunning) {
                    return; // Stop the loop if the game is not running
                }
                ObstacleViewMovement(); // Move the obstacle one line below each time
                handler.postDelayed(this, speedOfObjects); // Schedule the next iteration
                int randomDisplay = generateRandomNumber(5);
                if (randomDisplay == 1 && gameManager.getObstacles().size() < gameManager.getMaxNumberOfObstacles()) {
                    createNewObstacle();
                }
            }
        }, speedOfObjects); // Initial delay
    }

    public void ObstacleViewMovement() {
        removeAllObstaclesViews();
        gameManager.obstaclesMovement();
        addAllObstaclesViews();
        //check for collisions
        boolean collision = gameManager.checkCollision();
        if (collision) {
            collisionHappendUI(gameManager.isLastCollisionByTerrorist());
        }
    }

    public void createNewObstacle() {
        ShapeableImageView obstacleImageView = new ShapeableImageView(this);
        obstacleImageView.setImageResource(R.drawable.terrorist2); // Set your obstacle image resource here
        Obstacle obstacle = new Obstacle(generateRandomNumber(gameManager.getNumberOfLanes()), obstacleImageView, true);

        // Set layout parameters as needed
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        obstacleImageView.setLayoutParams(layoutParams);

        // Get the position of the obstacle
        int posX = obstacle.getPositionX();
        int posY = obstacle.getPositionY();

        // Add the obstacle to the game manager
        gameManager.addObstacle(obstacle);

        // Add the ShapeableImageView to the layout at the appropriate position
        if (posY < relativeLayouts.length) {
            RelativeLayout newRelativeLayout = relativeLayouts[posY][posX];
            newRelativeLayout.addView(obstacleImageView);
        }


    }


    public void changeLayoutOfMainCharacterIMG(int currentLane, int newLane) {

        RelativeLayout currentRelativeLayout = relativeLayouts[4][currentLane];
        currentRelativeLayout.removeView(main_IMG_character);
        RelativeLayout newRelativeLayout = relativeLayouts[4][newLane];
        newRelativeLayout.addView(main_IMG_character);

    }


    public void collisionHappendUI(boolean lastCollision) {

        if (lastCollision == true) {
            refreshHeartImages();
            vibration();
            createToast("oops");
        } else {
            changeScore();
            removeCookie();
            createBiteSound();
        }

        if(gameManager.getLife()== 0){
            gameOver();
        }

    }


    public void refreshHeartImages() {

        if (gameManager.getLife() >= 0) {
            main_IMG_hearts[gameManager.getNumOfCollosions() - 1].setVisibility(View.INVISIBLE);
        }

    }

    public int generateRandomNumber(int max) {

        Random random = new Random();
        int randomNumber = random.nextInt(max);
        return randomNumber;
    }


    public void vibration() {

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(VIBRATIONTIMING, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(VIBRATIONTIMING);
        }

    }

    public void createToast(String message) {
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isGameRunning = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isGameRunning) {
            isGameRunning = true;
            startGameLoop();
        }
    }

    public void removeAllObstaclesViews() {
        for (Obstacle obstacle : gameManager.getObstacles()) {
            int posX = obstacle.getPositionX();
            int posY = obstacle.getPositionY();
            relativeLayouts[posY][posX].removeView(obstacle.getShapeableImageView());
        }

    }

    public void addAllObstaclesViews() {
        for (Obstacle obstacle : gameManager.getObstacles()) {
            int posX = obstacle.getPositionX();
            int posY = obstacle.getPositionY();
            ShapeableImageView obstacleImageView = obstacle.getShapeableImageView();
            if (posY == 0 && !obstacle.isCausesDamage()) {
                obstacleImageView.setImageResource(R.drawable.cookie);
            } else if (posY == 0 && obstacle.isCausesDamage()) {
                obstacleImageView.setImageResource(R.drawable.terrorist2);
            }
            if (posY == 0) {
                obstacleImageView.setVisibility(View.VISIBLE);
            }
            relativeLayouts[posY][posX].addView(obstacle.getShapeableImageView());
        }

    }

    public void changeScore() {
        main_LBL_score.setText(gameManager.getScore() + "");
        Log.d(TAG, "changeScore: " + gameManager.getScore() + "numberofcollisions:" + gameManager.getNumOfCollosions());

    }

    private void removeCookie() {
        for (Obstacle obstacle : gameManager.getObstacles()) {
            int posX = obstacle.getPositionX();
            int posY = obstacle.getPositionY();
            if (gameManager.getMainCharacter().getPositionX() == posX && !obstacle.isCausesDamage()) {
                ShapeableImageView obstacleImageView = obstacle.getShapeableImageView();
                obstacleImageView.setVisibility(View.INVISIBLE);
                //relativeLayouts[posY][posX].removeView(obstacle.getShapeableImageView());
            }
        }

    }

    private void createBiteSound() {
        int random = generateRandomNumber(3);
        switch (random) {
            case 0:
                mp = MediaPlayer.create(MainActivity.this, R.raw.bite1);
                break;
            case 1:
                mp = MediaPlayer.create(MainActivity.this, R.raw.bite2);
                break;
            case 2:
                mp = MediaPlayer.create(MainActivity.this, R.raw.bite3);
                break;
        }
        mp.start();
    }


    private void gameOver(){
        isGameRunning = false;
        main_button_left.setVisibility(View.INVISIBLE);
        main_button_right.setVisibility(View.INVISIBLE);
        showCardOfEndGame(true);
        MAIN_LBLCARD_FINALSCORE.setText("Final Score: " + gameManager.getScore());

    }

    private void showCardOfEndGame(boolean gameOver){
        if(gameOver){
            MAIN_GAMEOVER_CARDVIEW.setVisibility(View.VISIBLE);
        }else{
            MAIN_GAMEOVER_CARDVIEW.setVisibility(View.INVISIBLE);
        }

    }


    private void savePlayerScoreAndGoToPlayersActivity(){
        if(MAIN_EDITTEXT_NAME.length() != 0){
            Player newPlayer = new Player().setName(MAIN_EDITTEXT_NAME.getText().toString()).setScore(gameManager.getScore()).setLat(0).setLng(0);
            AllPlayers.getInstance().addPlayer(newPlayer);
            Log.d(TAG, "allPlayers: " + allPlayers.getInstance().toString());

            Intent intent = new Intent(this, PlayersBoardActivity.class);
            startActivity(intent);
            //go to other activity

        }else {
            createToast("please add your name");
        }

    }



}


