package com.gamedesign.vacuumpilot.game;

import android.graphics.Bitmap;

import com.gamedesign.vacuumpilot.physics.PhysicsObject;

/**
 * Created by Lenovo-USER on 3/18/2015.
 */
public class PhysicsGameObject extends GameObject implements PhysicsObject {

    private double vx, vy;
    private double ax, ay;

    public PhysicsGameObject() {

    }

    public PhysicsGameObject(Bitmap image) {
        super(image);
    }

    public PhysicsGameObject(Bitmap image, int x, int y) {
        super(image, x, y);
    }

    public PhysicsGameObject(int x, int y, int width, int height) {
        super(x, y, width, height);

    }

    public PhysicsGameObject(Bitmap image, int x, int y, int width, int height) {
        super(image, x, y, width, height);
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

}
