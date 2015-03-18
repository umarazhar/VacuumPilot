package com.gamedesign.vacuumpilot.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.gamedesign.vacuumpilot.R;
import com.gamedesign.vacuumpilot.display.InputHandler;
import com.gamedesign.vacuumpilot.foundation.Library;
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

        loadImages();

        TRIGGER_OBSTACLE_GENERATION = inputHandler.width * 2;

        PLAYER_START_Y = inputHandler.height / 2;

        initPlayer();

        generateObstacles(inputHandler.width + DEFAULT_OBSTACLE_SPACING);
    }

    private void initPlayer() {
        player = new Player(BitmapFactory.decodeResource(ctx.getResources(), R.drawable.airplane1));

        player.setX((double)PLAYER_START_X);
        player.setY(PLAYER_START_Y - (double)player.getHeight() / 2);

        player_upper_bound = inputHandler.height / 4;
        player_lower_bound = 3 * inputHandler.height / 4;
        player_left_bound = 200;
        player_right_bound = player_left_bound;
    }

    private void loadImages() {
        Bitmap tmpImage;

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
        double max = obstacles.get(0).getX();
        for (int i = 0; i < obstacles.size(); i++) {
            if (obstacles.get(i).getX() + obstacles.get(i).getWidth() < 0) {
                obstacles.remove(i);
                continue;
            }
            max = Math.max(obstacles.get(i).getX(), max);
        }

        if (max < TRIGGER_OBSTACLE_GENERATION)
            generateObstacles((int)(max + DEFAULT_OBSTACLE_SPACING));

        for (GameObject tmp : obstacles) {
            tmp.setX(tmp.getX() - DEFAULT_SPEED);
        }

    }

    private void generateObstacles(int start_pos) {
        for (int i = start_pos; i < start_pos + OBSTACLE_GENERATION_DISTANCE; i += DEFAULT_OBSTACLE_SPACING) {
            int numObstacles = Library.randint(1, 3);

            int mid_screen = inputHandler.height / 2;

            for (int j = 0; j < numObstacles; j++) {
                int ypos = Library.randint(j * mid_screen, j * mid_screen + mid_screen);
                int xpos = Library.randint(i - OBSTACLE_POSITIONING_VARIANCE, i + OBSTACLE_POSITIONING_VARIANCE);

                int orient = Library.randint(1, 3);
                int obstacle_width = Library.randint(OBSTACLE_DEFAULT_WIDTH - OBSTACLE_WIDTH_VARIANCE, OBSTACLE_DEFAULT_WIDTH + OBSTACLE_WIDTH_VARIANCE);
                int obstacle_height = Library.randint(OBSTACLE_DEFAULT_HEIGHT - OBSTACLE_HEIGHT_VARIANCE, OBSTACLE_DEFAULT_HEIGHT + OBSTACLE_HEIGHT_VARIANCE);

                GameObject newObject;
                if (orient == 1) {
                    newObject = new GameObject(xpos, ypos, obstacle_width, obstacle_height);
                } else {
                    newObject = new GameObject(xpos, ypos, obstacle_height, obstacle_width);
                }

                int balloon_num = Library.randint(1,5);
                newObject = new GameObject(images.get("balloon" + balloon_num), xpos, ypos);

                obstacles.add(newObject);

            }
        }
    }

    public ArrayList<GameObject> getObstacles() {
        return obstacles;
    }

    public Player getPlayer() {
        return player;
    }
}
