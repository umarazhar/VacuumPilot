package com.gamedesign.vacuumpilot.game;

import android.graphics.Bitmap;

import com.gamedesign.vacuumpilot.graphics.SpriteManager;

/**
 * Created by Lenovo-USER on 3/19/2015.
 */
public class GravityWell {

    private final int DEFAULT_LIFE_COUNTER = 100;

    private final int DEFAULT_WIDTH = 50;
    private final int DEFAULT_HEIGHT = 50;

    private final String DESTROYED_STATE = "Destroyed";
    private final String GROWING_STATE = "Growing";
    private final String ALIVE_STATE = "Alive";

    private SpriteManager image;
    private int x, y;

    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;

    private String state;

    private int life_counter = DEFAULT_LIFE_COUNTER;

    public GravityWell(SpriteManager image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;

        this.state = GROWING_STATE;
    }

    public void update() {
        if (state.equals(ALIVE_STATE)) {
            life_counter--;

            if (life_counter == 0) {
                setState(DESTROYED_STATE);
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getState() {
        return state;
    }

    public Bitmap getImage() {
        return image.getCurrentImage();
    }

    private void setState(String state) {
        this.state = state;
    }

    public void setLifeCounter(int life_counter) {
        this.life_counter = life_counter;
    }
}
