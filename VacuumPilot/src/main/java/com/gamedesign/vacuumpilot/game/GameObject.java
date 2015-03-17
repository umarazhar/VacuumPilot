package com.gamedesign.vacuumpilot.game;

import android.graphics.Bitmap;

import com.gamedesign.vacuumpilot.foundation.Library;

/**
 * Created by Lenovo-USER on 3/15/2015.
 */
public class GameObject {

    private final String DEFAULT_STATE = "Alive";

    private int width, height;
    private double x, y;
    private Bitmap image;

    private String state;

    public GameObject() {

    }

    public GameObject(Bitmap image) {
        width = image.getWidth();
        height = image.getHeight();

        this.image = image;
    }

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;


    }

    public GameObject(Bitmap image, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.image = Library.resizedBitmap(image, width, height);
    }

    public void setWidth(int width) {
        this.width = width;
        this.image = Library.resizedBitmap(this.image, width, this.height);
    }

    public void setHeight(int height) {
        this.height = height;
        this.image = Library.resizedBitmap(this.image, height, this.width);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setImage(Bitmap image) {

        this.image = Library.resizedBitmap(image, width, height);

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
        return image;
    }
}
