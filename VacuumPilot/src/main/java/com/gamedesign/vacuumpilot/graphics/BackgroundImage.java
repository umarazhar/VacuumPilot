package com.gamedesign.vacuumpilot.graphics;

/**
 * Created by Lenovo-USER on 4/5/2015.
 */
public class BackgroundImage {

    private SpriteManager image;

    private boolean expired;

    private int x, y;

    public BackgroundImage(SpriteManager image) {
        this.image = image;
        this.x = 0;
        this.y = 0;
        this.expired = false;
    }

    public BackgroundImage(SpriteManager image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.expired = false;
    }

    public SpriteManager getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setImage(SpriteManager image) {
        this.image = image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
