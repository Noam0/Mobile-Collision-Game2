package com.example.collisiongame2;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.collisiongame2.Logic.GameManager;

import com.example.collisiongame2.Model.Obstacle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ShapeableImageView main_IMG_character;
    private  ShapeableImageView main_IMG_obstacle;
    private MaterialTextView main_LBL_score;
    private ShapeableImageView[] main_IMG_hearts;
    private GridLayout MAIN_LAYOUT_GRID;
    private RelativeLayout[][] relativeLayouts;
    private FloatingActionButton main_button_left;
    private FloatingActionButton main_button_right;
    private GameManager gameManager;
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean isGameRunning = true;
    private static final String TAG = "ObstacleMovement";

    private final int MAINLOOPTIMING = 750;
    private final int VIBRATIONTIMING = 300;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        relativeLayouts = getAllRelativeLayouts(); //find all relativeLayouts from the grid layout matrix
        main_button_left.setOnClickListener(view -> moveLeft());
        main_button_right.setOnClickListener(view -> moveRight());
        gameManager = new GameManager();
        startGameLoop();


    }




    public void findViews(){
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
            if(gameManager.checkCollision()){
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

            if(gameManager.checkCollision()){
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
                handler.postDelayed(this, MAINLOOPTIMING); // Schedule the next iteration
                int randomDisplay = generateRandomNumber(5);
                if (randomDisplay == 1 && gameManager.getObstacles().size() < gameManager.getMaxNumberOfObstacles()){
                    createNewObstacle();
                }
            }
        }, MAINLOOPTIMING); // Initial delay
    }

    public void ObstacleViewMovement() {
        removeAllObstaclesViews();
        gameManager.obstaclesMovement();
        addAllObstaclesViews();
        //check for collisions
        boolean collision =  gameManager.checkCollision();
        if(collision) {
            collisionHappendUI(gameManager.isLastCollisionByTerrorist());
        }
    }

    public void createNewObstacle(){
        ShapeableImageView obstacleImageView = new ShapeableImageView(this);
        obstacleImageView.setImageResource(R.drawable.terrorist2); // Set your obstacle image resource here
        Obstacle obstacle = new Obstacle(generateRandomNumber(gameManager.getNumberOfLanes()),obstacleImageView, true);

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



    public void changeLayoutOfMainCharacterIMG(int currentLane, int newLane){

        RelativeLayout currentRelativeLayout = relativeLayouts[4][currentLane];
        currentRelativeLayout.removeView(main_IMG_character);
        RelativeLayout newRelativeLayout = relativeLayouts[4][newLane];
        newRelativeLayout.addView(main_IMG_character);

    }



    public void collisionHappendUI(boolean lastCollision){

        if(lastCollision == true){
            refreshHeartImages();
            vibration();
            createToast();
        }else {
            changeScore();
        }
        //sound
    }



    public void refreshHeartImages(){

        if(gameManager.getLife() >= 0) {
            main_IMG_hearts[gameManager.getNumOfCollosions()-1].setVisibility(View.INVISIBLE);
        }

    }

    public int generateRandomNumber(int max) {

        Random random = new Random();
        // Generate a random number between 0 and 4
        int randomNumber = random.nextInt(max);
        return randomNumber;
    }


    public void vibration(){

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(VIBRATIONTIMING, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(VIBRATIONTIMING);
        }

    }

    public void createToast(){
        CharSequence text = "oops!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this , text, duration);
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
    public void removeAllObstaclesViews(){
        for(Obstacle obstacle : gameManager.getObstacles()){
            int posX = obstacle.getPositionX();
            int posY = obstacle.getPositionY();
            relativeLayouts[posY][posX].removeView(obstacle.getShapeableImageView());
        }

    }

    public void addAllObstaclesViews(){
        for(Obstacle obstacle : gameManager.getObstacles()){
            int posX = obstacle.getPositionX();
            int posY = obstacle.getPositionY();
            ShapeableImageView obstacleImageView = obstacle.getShapeableImageView();
            if(posY == 0 && obstacle.isCausesDamage() == false) {
                obstacleImageView.setImageResource(R.drawable.cookie);
            }else if(posY == 0 && obstacle.isCausesDamage() == true){
                obstacleImageView.setImageResource(R.drawable.terrorist2);
            }
            relativeLayouts[posY][posX].addView(obstacle.getShapeableImageView());
        }

    }

    public void changeScore(){
        main_LBL_score.setText(gameManager.getScore() + "");
        Log.d(TAG, "changeScore: " +gameManager.getScore() + "numberofcollisions:" + gameManager.getNumOfCollosions());

    }


}


