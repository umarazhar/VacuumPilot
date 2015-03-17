package com.gamedesign.vacuumpilot.game;

import android.graphics.Bitmap;

/**
 * Created by Lenovo-USER on 3/15/2015.
 */
public class Player extends GameObject{

    public Player() {
        super();
    }

    public Player(Bitmap image) {
        super(image);
    }

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Player(Bitmap image, int x, int y, int width, int height) {
        super(image, x, y, width, height);
    }

}
