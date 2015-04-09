package com.gamedesign.vacuumpilot.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.gamedesign.vacuumpilot.R;
import com.gamedesign.vacuumpilot.display.InputHandler;
import com.gamedesign.vacuumpilot.foundation.Library;
import com.gamedesign.vacuumpilot.graphics.BackgroundImage;
import com.gamedesign.vacuumpilot.graphics.BackgroundManager;
import com.gamedesign.vacuumpilot.graphics.SpriteManager;
import com.gamedesign.vacuumpilot.physics.PhysicsHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

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
    private final int DEFAULT_OBSTACLE_SPACING = 700;
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
    private final int DEFAULT_SPEED = 0;

    private final HashMap<String, Bitmap[]> images = new HashMap<String, Bitmap[]>();

    private Context ctx;

    //singleton pattern variable
    private static GameHandler gameHandler;

    private PhysicsHandler physicsHandler;
    private InputHandler inputHandler;

    private PowerupManager powerupManager;

    private Player player;

    private BackgroundManager background;

    private int score = 0;

    //the absolute positioning on the screen which binds the player
    private int player_upper_bound, player_lower_bound, player_left_bound, player_right_bound;

    private ArrayList<GameObject> obstacles;
//    private ArrayList<GravityWell> gravityWells;

    private GravityWell currentWell = null;

    private GameHandler(Context ctx) {
        this.ctx = ctx;

        obstacles = new ArrayList<GameObject>();
//        gravityWells = new ArrayList<GravityWell>();

        WindowManager wm = (WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        inputHandler = InputHandler.initHandler(size.x, size.y);
        physicsHandler = PhysicsHandler.initPhysics();

        Bitmap[] tmpImage = new Bitmap[1];
        tmpImage[0] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.mainbackground);

        background = new BackgroundManager(new SpriteManager(tmpImage));

        loadImages();

        TRIGGER_OBSTACLE_GENERATION = inputHandler.width * 2;

        PLAYER_START_Y = inputHandler.height / 2;

        powerupManager = new PowerupManager();

        initPlayer();

        generateObstacles(inputHandler.width + DEFAULT_OBSTACLE_SPACING);
    }

    private void initPlayer() {
//        Bitmap[] tmp = {images.get("player1")};
        player = new Player(new SpriteManager(images.get("player1")));

//        Bitmap[] tmp = new Bitmap[1];
//
//        tmp[0] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.damaged_airplane);
        SpriteManager explosionSprite = new SpriteManager(images.get("explosion1"));
        explosionSprite.setSwitchTime(1);
        player.setCrashImage(explosionSprite);
        player.setInvincibleImage(new SpriteManager(images.get("invincible_airplane")));

        player.setX((double)PLAYER_START_X);
        player.setY(PLAYER_START_Y - (double)player.getHeight() / 2);

        player_upper_bound = inputHandler.height / 2 - 100;
        player_lower_bound = inputHandler.height / 2 + 100;
        player_left_bound = 100;
        player_right_bound = 300;

        physicsHandler.addObject(player);
    }

    private void loadImages() {
        Bitmap[] tmpImage;

//        tmpImage = new Bitmap[1];
//        tmpImage[0] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.mainbackground);
//
//        backgroundImage = new SpriteManager(tmpImage);

        tmpImage = new Bitmap[1];
        tmpImage[0] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.airplane1);


        images.put("player1", tmpImage);

        tmpImage = new Bitmap[18];
        int imageres;
        for (int i = 1; i <= 18; i++) {
            imageres = ctx.getResources().getIdentifier("drawable/sat" + i, null, ctx.getPackageName());
            tmpImage[i - 1] = BitmapFactory.decodeResource(ctx.getResources(), imageres);
        }
        images.put("satellite1", tmpImage);

        tmpImage = new Bitmap[18];
        for (int i = 1; i <= 18; i++) {
            imageres = ctx.getResources().getIdentifier("drawable/blackhole" + i, null, ctx.getPackageName());
            Bitmap unsizedImage = BitmapFactory.decodeResource(ctx.getResources(), imageres);
            tmpImage[i - 1] = Library.resizedBitmap(unsizedImage, 400, 400);
        }

        images.put("blackhole1", tmpImage);

        tmpImage = new Bitmap[81];
        for (int i = 1; i <= 81; i++) {
            imageres = ctx.getResources().getIdentifier("drawable/explosion" + i, null, ctx.getPackageName());
            tmpImage[i - 1] = BitmapFactory.decodeResource(ctx.getResources(), imageres);
        }

        images.put("explosion1", tmpImage);

        tmpImage = new Bitmap[1];
        tmpImage[0] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.invincible_airplane);

        images.put("invincible_airplane", tmpImage);

        tmpImage = new Bitmap[8];
        for (int i = 1; i <= 8; i++) {
            imageres = ctx.getResources().getIdentifier("drawable/invincible" + i, null, ctx.getPackageName());
            tmpImage[i - 1] = BitmapFactory.decodeResource(ctx.getResources(), imageres);
        }

        images.put("invincible", tmpImage);

        tmpImage = new Bitmap[1];

        tmpImage[0] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.balloon1);
        images.put("balloon1", tmpImage);

        tmpImage = new Bitmap[1];
        tmpImage[0] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.balloon2);
        images.put("balloon2", tmpImage);

        tmpImage = new Bitmap[1];
        tmpImage[0] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.balloon3);
        images.put("balloon3", tmpImage);

        tmpImage = new Bitmap[1];
        tmpImage[0] = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.balloon4);
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

        background.update();

//        Log.d("Player Position", "X: " + player.getX() + "\tY: " + player.getY());
        if (!player.isDestroyed())
            physicsHandler.update();

        player.update();
        createGravityWells();

        int offset_x = -DEFAULT_SPEED;
        int offset_y = 0;

        if (player.getY() > player_lower_bound && background.getFirstImage().getY() + background.getFirstImage().getImage().getHeight() > 1080) {
            offset_y += (int)(player.getY() - player_lower_bound);
            player.setY((double)player_lower_bound);
        } else if (player.getY() < player_upper_bound && background.getFirstImage().getY() < 0) {
            offset_y += (int)(player.getY() - player_upper_bound);
            player.setY((double)player_upper_bound);
        }

        if (player.getX() > player_right_bound) {
            offset_x -= (int)(player.getX() - player_right_bound);
            player.setX((double)player_right_bound);
        } else if (player.getX() < player_left_bound) {
            offset_x -= (int)(player.getX() - player_left_bound);
            player.setX((double)player_left_bound);
        }

        score += -offset_x;

        for (GameObject tmp : obstacles) {
            tmp.setX(tmp.getX() + offset_x);
            tmp.setY(tmp.getY() - offset_y);
        }

        background.translate(offset_x, -offset_y);

//        backgroundx += offset_x;
//        backgroundy -= offset_y;

        ArrayList<GameObject> toDelete = new ArrayList<GameObject>();

        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).update();
            if (obstacles.get(i).isDestroyed()) {
                toDelete.add(obstacles.get(i));
            }
            max = Math.max(obstacles.get(i).getX(), max);
        }

        for (GameObject tmp: toDelete) {
            if (tmp.getClass() == GravityWell.class)
                physicsHandler.deleteGravityWells((GravityWell)tmp);
            obstacles.remove(tmp);
        }

        if (max < TRIGGER_OBSTACLE_GENERATION)
            generateObstacles((int)(max + DEFAULT_OBSTACLE_SPACING));

        checkCollisions();

    }

    private void createGravityWells() {
//        Log.d("Gravity Well", "Pressed: " + (inputHandler.touch_m == MotionEvent.ACTION_DOWN));
        if (inputHandler.touch_m == MotionEvent.ACTION_DOWN && currentWell == null) {
            GravityWell tmp = new GravityWell(new SpriteManager(images.get("blackhole1")), (int)inputHandler.touch_x, (int)inputHandler.touch_y);
//            GravityWell tmp = new GravityWell((int)inputHandler.touch_x, (int)inputHandler.touch_y);
            physicsHandler.addGravityWell(tmp);
            currentWell = tmp;
        } else if (inputHandler.touch_m == MotionEvent.ACTION_UP && currentWell != null) {
            currentWell.setAlive();
            addObject(currentWell);
            currentWell = null;
        }

        if (currentWell != null) {
//            Log.d("Gravity Well", "Strength: " + currentWell.getGravity());
            currentWell.increaseStrength();
        }
    }

    private void generateObstacles(int start_pos) {
        BackgroundImage tmpImage = background.getFirstImage();
        int numObstacles;
        int ypos;
        int xpos;
        for (int i = start_pos; i < start_pos + OBSTACLE_GENERATION_DISTANCE; i += DEFAULT_OBSTACLE_SPACING) {
            numObstacles = Library.randint(1, 3);

//            int mid_screen = inputHandler.height / 2;

            for (int j = 0; j < numObstacles; j++) {
                //eventually change this because the screen will be able to shift up and down
//                int ypos = Library.randint(j * mid_screen, j * mid_screen + mid_screen);
//                int xpos = Library.randint(i - OBSTACLE_POSITIONING_VARIANCE, i + OBSTACLE_POSITIONING_VARIANCE);
                ypos = Library.randint(tmpImage.getY(), tmpImage.getY() + tmpImage.getImage().getHeight());
                xpos = Library.randint(i - OBSTACLE_POSITIONING_VARIANCE, i + OBSTACLE_POSITIONING_VARIANCE);

//                int orient = Library.randint(1, 3);
//                int obstacle_width = Library.randint(OBSTACLE_DEFAULT_WIDTH - OBSTACLE_WIDTH_VARIANCE, OBSTACLE_DEFAULT_WIDTH + OBSTACLE_WIDTH_VARIANCE);
//                int obstacle_height = Library.randint(OBSTACLE_DEFAULT_HEIGHT - OBSTACLE_HEIGHT_VARIANCE, OBSTACLE_DEFAULT_HEIGHT + OBSTACLE_HEIGHT_VARIANCE);

                Powerup newPowerup = powerupManager.generatePowerup();
                if (newPowerup != null) {
//                    if (newPowerup.getType() == Powerup.INVINCIBLE) {
//                        newPowerup.setImage(new SpriteManager(images.get("invincible")));
//                    }

                    newPowerup.setImage(new SpriteManager(images.get("invincible")));

                    newPowerup.setX(xpos);
                    newPowerup.setY(ypos);

                    obstacles.add(newPowerup);
                    continue;
                }

                PhysicsGameObject newObject;
//                if (orient == 1) {
//                    newObject = new PhysicsGameObject(xpos, ypos, obstacle_width, obstacle_height);
//                } else {
//                    newObject = new PhysicsGameObject(xpos, ypos, obstacle_height, obstacle_width);
//                }

                int balloon_num = Library.randint(1,5);

                Bitmap[] tmp;
//                tmp[0] = images.get("satellite1" + balloon_num);

                if (ypos < tmpImage.getY() + 1200) {
                    tmp = images.get("satellite1");
                }
                else {
                    tmp = images.get("balloon" + balloon_num);
                }

                newObject = new PhysicsGameObject(new SpriteManager(tmp), xpos, ypos);
                newObject.setStandard(false);
                newObject.setSpecial(false);

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

    private void checkCollisions() {
        if (player.getY() + player.getHeight() < 0 || player.getY() > inputHandler.height - 200) {
            if (player.isInvincible()) {
                player.setVY(-1 * player.getVY());
            }
            player.crashed();
            return;
        }

        Rect playerRect = new Rect((int)player.getX(), (int)player.getY(), (int)player.getX() + player.getWidth(), (int)player.getY() + player.getHeight());
        Rect tmpRect;
        for (GameObject tmpObject : obstacles) {
            tmpRect = new Rect((int)tmpObject.getX(), (int)tmpObject.getY(), (int)tmpObject.getX() + tmpObject.getWidth(), (int)tmpObject.getY() + tmpObject.getHeight());
            if (playerRect.intersect(tmpRect)) {
                if (tmpObject.getClass() == Powerup.class) {
                    Powerup collectedPowerup = (Powerup)tmpObject;
                    collectPowerup(collectedPowerup);
                    tmpObject.setState(GameObject.DESTROYED_STATE);
                    continue;
                }
                player.crashed();
                tmpObject.setState(GameObject.DESTROYED_STATE);
            }
        }
    }

    private void collectPowerup(Powerup powerup) {
        if (powerup.getType() == Powerup.INVINCIBLE) {
            player.setInvincible(Powerup.INVINCIBLE_TIME);
        }
    }

    public ArrayList<GravityWell> getGravityWell() {
        return physicsHandler.getGravityWells();
    }

    public ArrayList<GameObject> getObstacles() {
        return obstacles;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<GravityWell> getGravityWells() {
        return physicsHandler.getGravityWells();
    }

    public int getPlayerUpperBound() {
        return player_upper_bound;
    }

    public int getPlayerLowerBound() {
        return player_lower_bound;
    }

    public int getPlayerRightBound() {
        return player_right_bound;
    }

    public int getPlayerLeftBound() {
        return player_left_bound;
    }

    public ConcurrentLinkedQueue<BackgroundImage> getBackgroundImages() {
        return background.getImages();
    }

    public int getScore() {
        return score;
    }

}
