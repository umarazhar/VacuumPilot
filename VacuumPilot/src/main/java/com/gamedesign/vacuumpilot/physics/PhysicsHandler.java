package com.gamedesign.vacuumpilot.physics;

import com.gamedesign.vacuumpilot.game.PhysicsGameObject;

/**
 * Created by Lenovo-USER on 3/15/2015.
 */
public class PhysicsHandler {

    private static PhysicsHandler physicsHandler;

    public PhysicsHandler() {

    }

    public PhysicsHandler initPhysics() {
        if (physicsHandler == null)
            physicsHandler = new PhysicsHandler();

        return physicsHandler;
    }

    public void applyGravity(PhysicsGameObject object) {

    }
}
