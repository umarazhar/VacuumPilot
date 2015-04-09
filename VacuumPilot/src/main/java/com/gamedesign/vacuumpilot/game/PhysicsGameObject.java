package com.gamedesign.vacuumpilot.game;

import com.gamedesign.vacuumpilot.graphics.SpriteManager;
import com.gamedesign.vacuumpilot.physics.PhysicsObject;

/**
 * Created by Lenovo-USER on 3/18/2015.
 */
public class PhysicsGameObject extends GameObject implements PhysicsObject {

    private boolean standard = true;
    private boolean special = true;

    private double vx = 0, vy = 0;
    private double ax = 0, ay = 0;

    public PhysicsGameObject() {
        super();
    }

    public PhysicsGameObject(SpriteManager image) {
        super(image);
    }

    public PhysicsGameObject(SpriteManager image, int x, int y) {
        super(image, x, y);
    }

    public PhysicsGameObject(int x, int y, int width, int height) {
        super(x, y, width, height);

    }

//    public PhysicsGameObject(SpriteManager image, int x, int y, int width, int height) {
//        super(image, x, y, width, height);
//    }

    public void update() {
        super.update();

        if (!getState().equals(GameObject.DESTROYED_STATE)) {
            this.vx += this.ax;
            this.vy += this.ay;

            this.setX(this.getX() + this.vx);
            this.setY(this.getY() + this.vy);

            if (getX() + getWidth() < 0) {
                setState(DESTROYED_STATE);
            }
        }
    }

    @Override
    public double getVX() {
        return vx;
    }

    @Override
    public double getVY() {
        return vy;
    }

    @Override
    public double getAX() {
        return ax;
    }

    @Override
    public double getAY() {
        return ay;
    }

    public boolean isStandard() {
        return standard;
    }

    public boolean isSpecial() {
        return special;
    }

    @Override
    public void setVX(double vx) {
        this.vx = vx;
    }

    @Override
    public void setVY(double vy) {
        this.vy = vy;
    }

    @Override
    public void setAX(double ax) {
        this.ax = ax;
    }

    @Override
    public void setAY(double ay) {
        this.ay = ay;
    }

    public void setStandard(boolean standard) {
        this.standard = standard;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }
}
