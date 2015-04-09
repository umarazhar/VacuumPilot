package com.gamedesign.vacuumpilot.game;

import com.gamedesign.vacuumpilot.graphics.SpriteManager;

/**
 * Created by Lenovo-USER on 4/8/2015.
 */
public class Powerup extends GameObject{

    public static final int INVINCIBLE = 0;
    public static final int DOUBLE_POINTS = 1;
    public static final int ANOTHER_CHANCE = 2;

    public static final int INVINCIBLE_TIME = 250;

    public static final int NUMBER_OF_POWERUPS = 3;

    private int type;

    public Powerup(int type) {
        super();
        this.type = type;
    }

    public Powerup(int type, SpriteManager image, int x, int y) {
        super(image, x, y);
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
