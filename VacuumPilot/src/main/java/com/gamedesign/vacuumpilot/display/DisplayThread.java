package com.gamedesign.vacuumpilot.display;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

        gameHandler = GameHandler.initGame();
        inputHandler = InputHandler.initHandler(0, 0);

        paint = new Paint();

        this.running = true;
    }

    private void drawGameObjects(Canvas canvas) {
        paint.setColor(Color.RED);

        ArrayList<GameObject> tmpList = gameHandler.getObstacles();

        Rect tmpRect;
        for (GameObject tmp : tmpList) {
            tmpRect = new Rect();
            tmpRect.left = (int)tmp.getX();
            tmpRect.top = (int)tmp.getY();
            tmpRect.right = (int)(tmp.getX() + tmp.getWidth());
            tmpRect.bottom = (int)(tmp.getY() + tmp.getHeight());

            canvas.drawRect(tmpRect, paint);

        }
    }

    public void doDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        drawGameObjects(canvas);

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