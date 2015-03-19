package com.gamedesign.vacuumpilot.game;

import android.graphics.Bitmap;

import com.gamedesign.vacuumpilot.graphics.SpriteManager;

/**
 * Created by Lenovo-USER on 3/15/2015.
 */
public class GameObject {

    private final String DEFAULT_STATE = "Alive";

    private int width, height;
    private double x, y;
    private SpriteManager image;

    private String state;

    public GameObject() {

    }

    public GameObject(SpriteManager image) {
        width = image.getCurrentImage().getWidth();
        height = image.getCurrentImage().getHeight();

        this.image = image;
    }

    public GameObject(SpriteManager image, int x, int y) {
        this.x = x;
        this.y = y;

        this.width = image.getCurrentImage().getWidth();
        this.height = image.getCurrentImage().getHeight();

        this.image = image;
    }

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

//    public GameObject(SpriteManager image, int x, int y, int width, int height) {
//        this.x = x;
//        this.y = y;
//        this.width = width;
//        this.height = height;
//
//        this.image = Library.resizedBitmap(image, width, height);
//    }

    public void update() {

    }

    public void setWidth(int width) {
        this.width = width;
//        this.image = Library.resizedBitmap(this.image, width, this.height);
    }

    public void setHeight(int height) {
        this.height = height;
//        this.image = Library.resizedBitmap(this.image, height, this.width);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setImage(SpriteManager image) {
        this.image = image;
//        this.image = Library.resizedBitmap(image, width, height);

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Bitmap getImage() {
        return image.getCurrentImage();
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {

        return state;
    }
}
