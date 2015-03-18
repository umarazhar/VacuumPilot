package com.gamedesign.vacuumpilot.display;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;

import com.gamedesign.vacuumpilot.foundation.Library;
import com.gamedesign.vacuumpilot.game.GameHandler;
import com.gamedesign.vacuumpilot.game.GameObject;

import java.util.ArrayList;

public class DisplayThread extends Thread {

    private SurfaceHolder sh;
    private Context ctx;
    private Handler handler;

    private GameHandler gameHandler;
    private InputHandler inputHandler;

    private Paint paint;

    private boolean running;

    public DisplayThread(SurfaceHolder sh, Context context, Handler hl) {
        this.sh = sh;
        this.ctx = context;
        this.handler = hl;

        gameHandler = GameHandler.initGame(ctx);
        inputHandler = InputHandler.initHandler(0, 0);

        paint = new Paint();

        this.running = true;
    }

    private void drawGameObjects(Canvas canvas) {
        paint.setColor(Color.RED);

        ArrayList<GameObject> tmpList = gameHandler.getObstacles();

        for (GameObject tmp : tmpList) {
            canvas.drawBitmap(tmp.getImage(), (float)tmp.getX(), (float)tmp.getY(), paint);
        }

//        Rect tmpRect;
//        for (GameObject tmp : tmpList) {
//            tmpRect = new Rect();
//            tmpRect.left = (int)tmp.getX();
//            tmpRect.top = (int)tmp.getY();
//            tmpRect.right = (int)(tmp.getX() + tmp.getWidth());
//            tmpRect.bottom = (int)(tmp.getY() + tmp.getHeight());
//
//            canvas.drawRect(tmpRect, paint);
//
//        }
    }

    private void drawPlayer(Canvas canvas) {
        GameObject player = gameHandler.getPlayer();
        canvas.drawBitmap(player.getImage(), (float)player.getX(), (float)player.getY(), paint);
//        Log.d("Player Position", "X: " + player.getX());
//        Log.d("Player Position", "Y: " + player.getY());
    }

    public void doDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        drawGameObjects(canvas);

        drawPlayer(canvas);

    }

    public void run() {

        while (running) {
            Canvas c = null;

            try {
                c = sh.lockCanvas(null);
                synchronized(sh) {
                    doDraw(c);
                    gameHandler.update();
                    Library.delay(30);
                }
            } finally {
                if (c != null) {
                    sh.unlockCanvasAndPost(c);
                }
            }
        }
    }

}