package com.gamedesign.vacuumpilot.game;

import com.gamedesign.vacuumpilot.graphics.SpriteManager;

/**
 * Created by Lenovo-USER on 3/15/2015.
 */
public class Player extends PhysicsGameObject{

    private SpriteManager crashImage;

    private SpriteManager pilotImage;

    private boolean alive = true;

    public Player() {
        super();
    }

    public Player(SpriteManager image) {
        super(image);
    }

    public Player(SpriteManager image, int x, int y) {
        super(image, x, y);
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

    public void crashed() {
        this.setAlive(false);
    }

    public void setAlive(boolean alive) {
        if (!alive) {
            super.setImage(crashImage);
            super.setState(GameObject.DESTROYED_STATE);
        }
    }

//    public Player(SpriteManager image, int x, int y, int width, int height) {
//        super(image, x, y, width, height);
//    }

}
