package com.gamedesign.vacuumpilot.game;

import com.gamedesign.vacuumpilot.graphics.SpriteManager;

/**
 * Created by Lenovo-USER on 3/19/2015.
 */
public class GravityWell extends GameObject{

    private final int DEFAULT_LIFE_COUNTER = 100;

    private final int DEFAULT_WIDTH = 50;
    private final int DEFAULT_HEIGHT = 50;

    private final double INC_AMOUNT = 0.02;

    private double gravity = 0;

    private int life_counter = DEFAULT_LIFE_COUNTER;

    public GravityWell(SpriteManager image, int x, int y) {
        super(image, x, y);

        setState(GROWING_STATE);
    }

    public void update() {
        super.update();

        if (getState().equals(ALIVE_STATE)) {
            life_counter--;

            if (life_counter == 0) {
                setState(DESTROYED_STATE);
            }
        }

    }

    public void setAlive() {
        setState(ALIVE_STATE);
    }

    public void increaseStrength() {
        if (Math.abs(gravity) < 0.7) {
            gravity += INC_AMOUNT;
        }
    }

    public double getGravity() {
        return this.gravity;
    }

    public void setLifeCounter(int life_counter) {
        this.life_counter = life_counter;
    }
}
