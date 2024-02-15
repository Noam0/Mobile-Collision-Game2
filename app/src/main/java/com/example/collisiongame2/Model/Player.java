package com.example.collisiongame2.Model;

public class Player {

    private String name = "";
    private int score;
    private double lat = 0.0;
    private double lng = 0.0;

    public Player(){

    }

    public String getName() {
        return name;
    }

    public Player setName(String name) {
        this.name = name;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Player setScore(int score) {
        this.score = score;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Player setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public Player setLng(double lng) {
        this.lng = lng;
        return this;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
