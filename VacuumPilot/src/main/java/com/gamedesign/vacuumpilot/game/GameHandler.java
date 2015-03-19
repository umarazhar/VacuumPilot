package com.gamedesign.vacuumpilot.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.MotionEvent;

import com.gamedesign.vacuumpilot.R;
import com.gamedesign.vacuumpilot.display.InputHandler;
import com.gamedesign.vacuumpilot.foundation.Library;
import com.gamedesign.vacuumpilot.graphics.SpriteManager;
import com.gamedesign.vacuumpilot.physics.PhysicsHandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lenovo-USER on 3/15/2015.
 */

/**
 * TO DO:
 * -correct size of balloon images
 * -add more obstacle images
 * -change key name from balloon# to obstacle#
 * -instead of storing bitmaps, instead store spritemanager objects
 * -change all occurances of bitmap references to spritemanager objects
 * -implement physics
 * -implement gravity wells
 * -add powerups
 */
public class GameHandler {

    //if there are no more obstacles after this distance then trigger generation of more
    //obstacles
    private final int TRIGGER_OBSTACLE_GENERATION;

    /**
     * Increase game difficulty by:
     * -increasing the scrolling speed
     * -decrease the distance between successive obstacles
     */
    //default spacing between obstacles
    private final int DEFAULT_OBSTACLE_SPACING = 500;
    //the distance up to which to generate obstacles
    private final int OBSTACLE_GENERATION_DISTANCE = 6000;
    //from the obstacles intended position a random function can be used to vary its position
    //by up to OBSTACLE_POSITIONING_VARIANCE
    private final int OBSTACLE_POSITIONING_VARIANCE = 100;

    //default width and height for obstacles
    private final int OBSTACLE_DEFAULT_WIDTH = 100;
    private final int OBSTACLE_DEFAULT_HEIGHT = 150;
    //the amount of variance each obstacle can have for its width and height
    //this is selected by a random function
    private final int OBSTACLE_WIDTH_VARIANCE = 50;
    private final int OBSTACLE_HEIGHT_VARIANCE = 50;

    private final int PLAYER_START_X = 200;
    private final int PLAYER_START_Y;

    //default scrolling speed of the game
    private final int DEFAULT_SPEED = 10;

    private final HashMap<String, Bitmap> images = new HashMap<String, Bitmap>();

    private Context ctx;

    //singleton pattern variable
    private static GameHandler gameHandler;

    private PhysicsHandler physicsHandler;
    private InputHandler inputHandler;

    private Player player;

    //the absolute positioning on the screen which binds the player
    private int player_upper_bound, player_lower_bound, player_left_bound, player_right_bound;

    private ArrayList<GameObject> obstacles;

    private GameHandler(Context ctx) {
        this.ctx = ctx;

        obstacles = new ArrayList<GameObject>();
        inputHandler = InputHandler.initHandler(0, 0);
        physicsHandler = PhysicsHandler.initPhysics();

        loadImages();

        TRIGGER_OBSTACLE_GENERATION = inputHandler.width * 2;

        PLAYER_START_Y = inputHandler.height / 2;

        initPlayer();

        generateObstacles(inputHandler.width + DEFAULT_OBSTACLE_SPACING);
    }

    private void initPlayer() {
        Bitmap[] tmp = {images.get("player1")};
        player = new Player(new SpriteManager(tmp));

        player.setX((double)PLAYER_START_X);
        player.setY(PLAYER_START_Y - (double)player.getHeight() / 2);

        player_upper_bound = inputHandler.height / 2 - 100;
        player_lower_bound = inputHandler.height / 2 + 100;
        player_left_bound = 200;
        player_right_bound = player_left_bound;

        physicsHandler.addObject(player);
    }

    private void loadImages() {
        Bitmap tmpImage;

        tmpImage = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.airplane1);
        images.put("player1", tmpImage);

        tmpImage = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.balloon1);
        images.put("balloon1", tmpImage);
        tmpImage = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.balloon2);
        images.put("balloon2", tmpImage);
        tmpImage = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.balloon3);
        images.put("balloon3", tmpImage);
        tmpImage = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.balloon4);
        images.put("balloon4", tmpImage);
    }

    public static GameHandler initGame(Context ctx) {
        if (gameHandler == null) {
            gameHandler = new GameHandler(ctx);
        }

        return gameHandler;
    }

    public void update() {
        double max = 0;

        Log.d("Player Position", "X: " + player.getX() + "\tY: " + player.getY());
        physicsHandler.update();

        int offset_x = -DEFAULT_SPEED;
        int offset_y = 0;

        if (player.getY() > player_lower_bound) {
            offset_y = (int)(player.getY() - player_lower_bound);
            player.setY((double)player_lower_bound);
        } else if (player.getY() < player_upper_bound) {
            offset_y = (int)(player.getY() - player_upper_bound);
            player.setY((double)player_upper_bound);
        }

        for (GameObject tmp : obstacles) {
            tmp.setX(tmp.getX() + offset_x);
            tmp.setY(tmp.getY() - offset_y);
        }

        for (int i = 0; i < obstacles.size(); i++) {
            if (obstacles.get(i).getX() + obstacles.get(i).getWidth() < 0) {
                obstacles.remove(i);
                continue;
            }
            max = Math.max(obstacles.get(i).getX(), max);
        }

        if (max < TRIGGER_OBSTACLE_GENERATION)
            generateObstacles((int)(max + DEFAULT_OBSTACLE_SPACING));

    }

    private void createGravityWells() {
        if (inputHandler.touch_m == MotionEvent.ACTION_DOWN) {

        }
    }

    private void generateObstacles(int start_pos) {
        for (int i = start_pos; i < start_pos + OBSTACLE_GENERATION_DISTANCE; i += DEFAULT_OBSTACLE_SPACING) {
            int numObstacles = Library.randint(1, 3);

            int mid_screen = inputHandler.height / 2;

            for (int j = 0; j < numObstacles; j++) {
                //eventually change this because the screen will be able to shift up and down
                int ypos = Library.randint(j * mid_screen, j * mid_screen + mid_screen);
                int xpos = Library.randint(i - OBSTACLE_POSITIONING_VARIANCE, i + OBSTACLE_POSITIONING_VARIANCE);

//                int orient = Library.randint(1, 3);
//                int obstacle_width = Library.randint(OBSTACLE_DEFAULT_WIDTH - OBSTACLE_WIDTH_VARIANCE, OBSTACLE_DEFAULT_WIDTH + OBSTACLE_WIDTH_VARIANCE);
//                int obstacle_height = Library.randint(OBSTACLE_DEFAULT_HEIGHT - OBSTACLE_HEIGHT_VARIANCE, OBSTACLE_DEFAULT_HEIGHT + OBSTACLE_HEIGHT_VARIANCE);

                PhysicsGameObject newObject;
//                if (orient == 1) {
//                    newObject = new PhysicsGameObject(xpos, ypos, obstacle_width, obstacle_height);
//                } else {
//                    newObject = new PhysicsGameObject(xpos, ypos, obstacle_height, obstacle_width);
//                }

                int balloon_num = Library.randint(1,5);

                Bitmap[] tmp = new Bitmap[1];
                tmp[0] = images.get("balloon" + balloon_num);

                newObject = new PhysicsGameObject(new SpriteManager(tmp), xpos, ypos);
                newObject.setStandard(false);

//                obstacles.add(newObject);
                addPhysicsObject(newObject);
            }
        }
    }

    private void addObject(GameObject object) {
        this.obstacles.add(object);
    }

    private void addPhysicsObject(PhysicsGameObject object) {
        physicsHandler.addObject(object);
        addObject(object);
    }

    public ArrayList<GameObject> getObstacles() {
        return obstacles;
    }

    public Player getPlayer() {
        return player;
    }
}
