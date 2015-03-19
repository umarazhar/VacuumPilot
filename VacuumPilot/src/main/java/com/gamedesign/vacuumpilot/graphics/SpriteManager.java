package com.gamedesign.vacuumpilot.graphics;

import android.graphics.Bitmap;

/**
 * Created by Lenovo-USER on 3/17/2015.
 */
public class SpriteManager {

    private final int DEFAULT_INC_TIME = 100;

    private Bitmap[] sprite;
    private Bitmap currentImage;

    private int index = 0;
    private int counter = 0;

    private int inc_time = DEFAULT_INC_TIME;

    public SpriteManager(Bitmap[] arr) {
        this.sprite = arr;
        this.currentImage = this.sprite[index];
    }

    public SpriteManager(Bitmap[] arr, int inc_time) {
        this.sprite = arr;
        this.currentImage = this.sprite[index];
        this.inc_time = inc_time;
    }

    public void update() {
        counter++;

        if (counter % inc_time == 0) {
            index = (index + 1) % sprite.length;
            currentImage = sprite[index];
        }
    }

    public Bitmap getCurrentImage() {
        return currentImage;
    }

    public void setSprite(Bitmap[] sprite) {
        this.sprite = sprite;
    }
}
