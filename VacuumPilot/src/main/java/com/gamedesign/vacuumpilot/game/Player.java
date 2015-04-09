package com.gamedesign.vacuumpilot.game;

import com.gamedesign.vacuumpilot.graphics.SpriteManager;

/**
 * Created by Lenovo-USER on 3/15/2015.
 */
public class Player extends PhysicsGameObject{

    private SpriteManager crashImage;
    private SpriteManager invincibleImage;
    private SpriteManager pilotImage;
    private SpriteManager regularImage;

    private boolean invincible = true;
    private int invincible_counter = 50;

    private boolean alive = true;
    private boolean gameOver = false;

    public Player() {
        super();
    }

    public Player(SpriteManager image) {
        super(image);
        regularImage = image;
    }

    public Player(SpriteManager image, int x, int y) {
        super(image, x, y);
        regularImage = image;
    }

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void setCrashImage(SpriteManager crashImage) {
        this.crashImage = crashImage;
    }

    public void setPilotImage(SpriteManager pilotImage) {
        this.pilotImage = pilotImage;
    }

    public void setInvincibleImage(SpriteManager invincibleImage) {
        this.invincibleImage = invincibleImage;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void crashed() {
        if (!invincible) {
            this.setAlive(false);
        }
    }

    public void setAlive(boolean alive) {
        if (!alive) {
            super.setImage(crashImage);
            super.setState(GameObject.DESTROYED_STATE);
        }
    }

    public void update() {
        super.update();

        if (invincible) {
            invincible_counter--;
            if (invincible_counter == 0) {
                invincible = false;
                setImage(regularImage);
            }
        }
    }

    public void setInvincible(int count) {
        this.invincible_counter = count;
        this.invincible = true;
        this.setImage(invincibleImage);
    }

    public boolean isGameOver() {
        return gameOver;
    }

//    public Player(SpriteManager image, int x, int y, int width, int height) {
//        super(image, x, y, width, height);
//    }

}
