package com.gamedesign.vacuumpilot.physics;

/**
 * Created by Lenovo-USER on 3/18/2015.
 */
public interface PhysicsObject {

    public double getX();

    public double getY();

    public double getVX();

    public double getVY();

    public double getAX();

    public double getAY();

    public void setX(double x);

    public void setY(double y);

    public void setVX(double vx);

    public void setVY(double vy);

    public void setAX(double ax);

    public void setAY(double ay);
}
