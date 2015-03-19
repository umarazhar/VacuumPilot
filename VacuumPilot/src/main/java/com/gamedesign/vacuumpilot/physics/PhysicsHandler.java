package com.gamedesign.vacuumpilot.physics;

import com.gamedesign.vacuumpilot.game.PhysicsGameObject;

import java.util.ArrayList;

/**
 * Created by Lenovo-USER on 3/15/2015.
 */
public class PhysicsHandler {

    private final double GRAVITY_ACCELERATION = 0.04;

    private static PhysicsHandler physicsHandler;

    private ArrayList<PhysicsGameObject> objects;

    public PhysicsHandler() {
        objects = new ArrayList<PhysicsGameObject>();
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
        for (PhysicsGameObject tmp : objects) {
            if (tmp.isStandard()) {
                applyGravity(tmp);
                tmp.update();
            }
        }
    }

    private void applyGravity(PhysicsGameObject object) {
        object.setAY(object.getAY() + GRAVITY_ACCELERATION);
    }

    public void addObject(PhysicsGameObject object) {
        this.objects.add(object);
    }
}
