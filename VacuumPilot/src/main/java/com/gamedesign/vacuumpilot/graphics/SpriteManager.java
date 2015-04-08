package com.gamedesign.vacuumpilot.graphics;

import android.graphics.Bitmap;

/**
 * Created by Lenovo-USER on 3/17/2015.
 */
public class SpriteManager {

    private final int DEFAULT_INC_TIME = 10;

    private Bitmap[] sprite;
    private Bitmap currentImage;

    private int width, height;

    private int index = 0;
    private int counter = 0;

    private int inc_time = DEFAULT_INC_TIME;

    public SpriteManager(Bitmap[] arr) {
        this.sprite = arr;
        this.currentImage = this.sprite[index];
        calcSize();
    }

    public SpriteManager(Bitmap[] arr, int inc_time) {
        this.sprite = arr;
        this.currentImage = this.sprite[index];
        this.inc_time = inc_time;
        calcSize();
    }

    private void calcSize() {
        for (Bitmap tmp : sprite) {
            width = Math.max(width, tmp.getWidth());
            height = Math.max(height, tmp.getHeight());
        }
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

    /**
     * This method returns the width of the largest image from its sprites.
     * If you want the width of the current image, then use the getCurrentImage method
     * in conjunction with getWidth of a Bitmap object.
     *
     * @return the width of largest sprite in this object.
     */
    public int getWidth() {
        return width;
    }

    /**
     * This method returns the height of the largest image from its sprites.
     * If you want the height of the current image, then use the getCurrentImage method
     * in conjunction with getHeight of a Bitmap object.
     *
     * @return the height of largest sprite in this object.
     */
    public int getHeight() {
        return height;
    }
}
