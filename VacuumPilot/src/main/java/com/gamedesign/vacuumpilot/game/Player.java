package com.gamedesign.vacuumpilot.game;

import com.gamedesign.vacuumpilot.graphics.SpriteManager;

/**
 * Created by Lenovo-USER on 3/15/2015.
 */
public class Player extends PhysicsGameObject{

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

//    public Player(SpriteManager image, int x, int y, int width, int height) {
//        super(image, x, y, width, height);
//    }

}
