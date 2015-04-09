package com.gamedesign.vacuumpilot.physics;

import com.gamedesign.vacuumpilot.game.GravityWell;
import com.gamedesign.vacuumpilot.game.PhysicsGameObject;
import com.gamedesign.vacuumpilot.game.Player;

import java.util.ArrayList;

/**
 * Created by Lenovo-USER on 3/15/2015.
 */
public class PhysicsHandler {

    private final double GRAVITY_ACCELERATION = 0.04;

    private static PhysicsHandler physicsHandler;

    private ArrayList<PhysicsGameObject> objects;
    private ArrayList<GravityWell> gravityWells;

    public PhysicsHandler() {
        objects = new ArrayList<PhysicsGameObject>();
        gravityWells = new ArrayList<GravityWell>();
    }

    public static PhysicsHandler initPhysics() {
        if (physicsHandler == null)
            physicsHandler = new PhysicsHandler();

        return physicsHandler;
    }

    public void update() {
        //reset acceleration for all objects
        for (PhysicsGameObject tmp : objects) {
            tmp.setAX(0);
            tmp.setAY(0);
        }

        int axcoef, aycoef;
        for (PhysicsGameObject tmp : objects) {
            if (tmp.isStandard()) {
                applyGravity(tmp);
            }

            if (tmp.isSpecial()) {
                for (GravityWell tmpWell : gravityWells) {
                    aycoef = 1;
                    axcoef = 1;
                    if (tmpWell.getY() - tmp.getY() < 0)
                        aycoef = -1;
                    if (tmpWell.getX() - tmp.getX() < 0)
                        axcoef = -1;

//                    Log.d("Gravity Well", "Strength: " + tmpWell.getGravity());
//                    Log.d("Gravity Well", "X: " + tmpWell.getX() + " Y: " + tmpWell.getY());

                    tmp.setAY(tmp.getAY() + tmpWell.getGravity() * aycoef);
                    tmp.setAX(tmp.getAX() + tmpWell.getGravity() * axcoef);
                }
            }

            if (tmp.getClass() != Player.class)
                tmp.update();
        }

        updateGravityWells();

    }

    private void updateGravityWells() {
        for (GravityWell tmp : gravityWells) {
            tmp.update();
        }
    }

    public void deleteGravityWells(GravityWell toDelete) {
        gravityWells.remove(toDelete);
    }

    private void applyGravity(PhysicsGameObject object) {
        object.setAY(object.getAY() + GRAVITY_ACCELERATION);
    }

    public void addObject(PhysicsGameObject object) {
        this.objects.add(object);
    }

    public void addGravityWell(GravityWell well) {
        this.gravityWells.add(well);
    }

    public ArrayList<GravityWell> getGravityWells() {
        return gravityWells;
    }
}
