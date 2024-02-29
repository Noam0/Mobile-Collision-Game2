package com.example.collisiongame2.Logic;



import com.example.collisiongame2.Model.Obstacle;
import com.example.collisiongame2.Model.mainCharacter;
import java.util.HashSet;
import java.util.Set;


public class GameManager {

    private int life = 3;
    private int numOfCollosions = 0;
    private int numberOfLanes = 5;
    private int numberOfRows = 6;
    private int score = 0;
    private mainCharacter mainCharacter;
    private Set<Obstacle> obstacles;
    private int numberOfObstacles = 0;
    private int maxNumberOfObstacles = 4;

    public boolean lastCollisionByTerrorist = false;

    public GameManager() {

        obstacles = new HashSet<>();
        mainCharacter = new mainCharacter();
    }

    public mainCharacter getMainCharacter() {
        return mainCharacter;
    }

    public int getLife() {
        return life;
    }

    public GameManager setLife(int life) {
        this.life = life;
        return this;
    }

    public void addObstacle (Obstacle obstacle){
        if(numberOfObstacles < maxNumberOfObstacles ) {
            obstacles.add(obstacle);
            numberOfObstacles++;
        }
    }

    public Set<Obstacle> getObstacles() {
        return obstacles;
    }

    public GameManager setObstacles(Set<Obstacle> obstacles) {
        this.obstacles = obstacles;
        return this;
    }

    public int getNumberOfLanes() {
        return numberOfLanes;
    }

    public GameManager setNumberOfLanes(int numberOfLanes) {
        this.numberOfLanes = numberOfLanes;
        return this;
    }

    public int getMaxNumberOfObstacles() {
        return maxNumberOfObstacles;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumOfCollosions() {
        return numOfCollosions;
    }

    public GameManager setNumOfCollosions(int numOfCollosions) {
        this.numOfCollosions = numOfCollosions;
        return this;
    }

    public void removeObstacle (Obstacle obstacle){
        obstacles.remove(obstacle);
        numberOfObstacles--;
    }



    public void obstaclesMovement() {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getPositionY() != numberOfRows-1) {
                obstacle.setPositionY(obstacle.getPositionY()+1);
            } else {
                obstacle.setToStartOfRoad();
            }


        }
    }


    public boolean checkCollision() {
        for (Obstacle obstacle : obstacles) {
            if (this.mainCharacter.getPositionX() == obstacle.getPositionX() && this.mainCharacter.getPositionY() == obstacle.getPositionY() && obstacle.isCausesDamage()) {
                numOfCollosions++;
                life--;
                lastCollisionByTerrorist= true;
                return true;
            }else if(this.mainCharacter.getPositionX() == obstacle.getPositionX() && this.mainCharacter.getPositionY() == obstacle.getPositionY() && !obstacle.isCausesDamage()){
                score += 5;
                lastCollisionByTerrorist= false;
                return true;
            }
        }
        return false;
    }

    public int getScore() {
        return score;
    }

    public GameManager setScore(int score) {
        this.score = score;
        return this;
    }

    public boolean isLastCollisionByTerrorist() {
        return lastCollisionByTerrorist;
    }

    public GameManager setLastCollisionByTerrorist(boolean lastCollisionByTerrorist) {
        this.lastCollisionByTerrorist = lastCollisionByTerrorist;
        return this;
    }
}





