package com.example.collisiongame2.Model;

import com.example.collisiongame2.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Objects;
import java.util.Random;

public class Obstacle extends Character {

    private ShapeableImageView shapeableImageView;

    private boolean causesDamage;
    public Obstacle(int positionX,ShapeableImageView shapeableImageView,boolean causesDamage ) {
        super(positionX, 0);
        this.shapeableImageView = shapeableImageView;
        this.causesDamage = causesDamage;
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.getPositionX(), this.getPositionY());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Obstacle obstacle = (Obstacle) obj;
        return this.getPositionX() == obstacle.getPositionX() && this.getPositionY() == obstacle.getPositionY();
    }

    public void setToStartOfRoad(){
        this.setPositionY(0);
        this.setPositionX(getRandomNumber());
        if(getRandomNumber() == 2){
             // Set your obstacle image resource here
            this.causesDamage = false;
        }else{
            this.causesDamage = true;
        }

    }
    private int getRandomNumber() {
        // Create a Random object
        Random random = new Random();
        int randomNumber = random.nextInt(5);
        return randomNumber;
    }


    public ShapeableImageView getShapeableImageView() {
        return shapeableImageView;
    }

    public Obstacle setShapeableImageView(ShapeableImageView shapeableImageView) {
        this.shapeableImageView = shapeableImageView;
        return this;
    }

    public boolean isCausesDamage() {
        return causesDamage;
    }

    public Obstacle setCausesDamage(boolean causesDamage) {
        this.causesDamage = causesDamage;
        return this;
    }
}
