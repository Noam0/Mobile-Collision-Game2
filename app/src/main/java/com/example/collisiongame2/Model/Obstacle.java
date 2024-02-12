package com.example.collisiongame2.Model;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.Objects;
import java.util.Random;

public class Obstacle extends Character {

    ShapeableImageView shapeableImageView;
    public Obstacle(int positionX,ShapeableImageView shapeableImageView ) {
        super(positionX, 0);
        this.shapeableImageView = shapeableImageView;
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
}
