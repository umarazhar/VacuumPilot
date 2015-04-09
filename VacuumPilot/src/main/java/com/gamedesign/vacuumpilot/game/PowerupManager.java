package com.gamedesign.vacuumpilot.game;

import com.gamedesign.vacuumpilot.foundation.Library;
import com.gamedesign.vacuumpilot.graphics.SpriteManager;

import java.util.ArrayList;

/**
 * Created by Lenovo-USER on 4/8/2015.
 */
public class PowerupManager {

    private ArrayList<Powerup> powerups;

    private SpriteManager invincibleImage;
    private SpriteManager doublePointsImage;
    private SpriteManager secondLifeImage;


    public PowerupManager() {
        powerups = new ArrayList<Powerup>();
    }

    public Powerup generatePowerup() {
        int randnum = Library.randint(0, 300);
        if (randnum <= 100) {
            randnum = Library.randint(0, Powerup.NUMBER_OF_POWERUPS);
            Powerup newPower = new Powerup(Powerup.INVINCIBLE);
            powerups.add(newPower);
            return newPower;
        }

        return null;
    }

    public void setInvincibleImage(SpriteManager invincibleImage) {
        this.invincibleImage = invincibleImage;
    }

}
