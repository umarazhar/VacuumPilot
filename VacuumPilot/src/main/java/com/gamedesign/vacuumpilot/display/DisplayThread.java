package com.gamedesign.vacuumpilot.display;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

import com.gamedesign.vacuumpilot.game.GameHandler;
import com.gamedesign.vacuumpilot.game.GameObject;
import com.gamedesign.vacuumpilot.game.GravityWell;
import com.gamedesign.vacuumpilot.graphics.BackgroundImage;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DisplayThread extends Thread {

    private SurfaceHolder sh;
    private Context ctx;
    private Handler handler;

    private GameHandler gameHandler;
    private InputHandler inputHandler;

    private Paint paint;

    private Canvas c;
    private Canvas bitmapCanvas;
    private Bitmap canvasImage;

    private int[][][] pixel_offset;

    private boolean running;

    private ThreadA drawThread;

    public DisplayThread(SurfaceHolder sh, Context context, Handler hl) {
        this.sh = sh;
        this.ctx = context;
        this.handler = hl;

        gameHandler = GameHandler.initGame(ctx);
        inputHandler = InputHandler.initHandler(0, 0);

        pixel_offset = setPixelOffset();

        canvasImage = Bitmap.createBitmap(inputHandler.width, inputHandler.height, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(canvasImage);
        paint = new Paint();

//        drawThread = new ThreadA(Thread.currentThread(), gameHandler.getObstacles(), inputHandler.width, inputHandler.height);
//        drawThread.setImage(canvasImage);
//        drawThread.setPaint(paint);
//        drawThread.start();

        this.running = true;
    }

    private int[][][] setPixelOffset() {

        int r = 50;
        double h = (-2*r + Math.sqrt(Math.pow(2*r,2) + 4*Math.pow(r, 2))) / 2;
        int total_r = r + (int)h;
        int[][][] arr = new int[2 * total_r + 1][2 * total_r + 1][2];

        int center = (2 * total_r + 1) / 2;

        double factor = h / r;
        int finalx, finaly;
        double dist, newdist;
        double angle;

        int[] tmp;

        for (int y = center - r; y < center + r; y++) {
            for (int x = center - r; x < center + r; x++) {
                tmp = new int[2];
//                if (x <= 0 || x >= width || y >= height || y <= 0)
//                    continue;
                dist = Math.sqrt(Math.pow(x - center, 2) + Math.pow(y - center, 2));
                if (dist < r) {
                    newdist = dist * factor;
                    angle = Math.atan2(y - center, x - center);
                    finalx = (int)(center + ((r + newdist) * Math.cos(angle)));
                    finaly = (int)(center + ((r + newdist) * Math.sin(angle)));
//                    if (finalx >= 0 && finalx < image.getWidth() && finaly < image.getHeight() && finaly >= 0) {
//                        image.setPixel(finalx, finaly, image.getPixel(x, y));
//                    }

                    tmp[0] = -finalx;
                    tmp[1] = -finaly;
                    arr[finalx][finaly] = tmp;

//                            canvasImage.setPixel(x, y, Color.BLACK);
//                    canvas.drawCircle((float)tmp.getX(), (float)tmp.getY(), (float)r, paint);
                }
            }
        }

        return arr;
    }

    private synchronized void drawGameObjects(Canvas canvas) {
        paint.setColor(Color.BLACK);
//        Log.i("Main Thread", "I shouldnt be here");

        ArrayList<GameObject> tmpList = gameHandler.getObstacles();

        for (GameObject tmp : tmpList) {
            if (tmp.getClass() == GravityWell.class) {
                drawGravityWells(canvas, (GravityWell)tmp);
//                double r = tmp.getWidth() * 3 / 4;
//                double h = (-2*r + Math.sqrt(Math.pow(2*r,2) + 4*Math.pow(r, 2))) / 2;
//                double factor = h / r;
//                int finalx, finaly;
//                double dist, newdist;
//                double angle;
//
//                for (int y = (int)(tmp.getY() - tmp.getHeight() / 2); y < tmp.getY() + tmp.getHeight() / 2; y++) {
//                    for (int x = (int)(tmp.getX() - tmp.getWidth() / 2); x < tmp.getX() + tmp.getWidth() / 2; x++) {
//                        if (x <= 0 || x >= inputHandler.width || y >= inputHandler.height || y <= 0)
//                            continue;
//                        dist = Math.sqrt(Math.pow(x - tmp.getX(), 2) + Math.pow(y - tmp.getY(), 2));
//                        if (dist < r) {
//                            newdist = dist * factor;
//                            angle = Math.atan2(y - tmp.getY(), x - tmp.getX());
//                            finalx = (int)(tmp.getX() + ((r + newdist) * Math.cos(angle)));
//                            finaly = (int)(tmp.getY() + ((r + newdist) * Math.sin(angle)));
//                            if (finalx >= 0 && finalx <= inputHandler.width && finaly <= inputHandler.height && finaly >= 0)
//                                canvasImage.setPixel(finalx, finaly, canvasImage.getPixel(x, y));
////                            canvasImage.setPixel(x, y, Color.BLACK);
//                        }
//                    }
//                }
//                canvas.drawCircle((float)tmp.getX(), (float)tmp.getY(), (float)r, paint);
            } else {
                canvas.drawBitmap(tmp.getImage(), (float) (tmp.getX() - tmp.getWidth() / 2), (float) (tmp.getY() - tmp.getHeight() / 2), paint);
            }
        }


    }

    private void drawPlayer(Canvas canvas) {
        GameObject player = gameHandler.getPlayer();
        canvas.drawBitmap(player.getImage(), (float)player.getX(), (float)player.getY(), paint);
    }

    private void drawGravityWells(Canvas canvas, GravityWell well) {
        int radius = pixel_offset.length / 2;
        if ((well.getX() - radius < 0 && well.getY() - radius < 0) && (well.getX() + radius < 0 && well.getY() + radius < 0))
            return;

        int xval;
        int yval;

        for (int y = 0; y < pixel_offset.length; y++) {
            for (int x = 0; x < pixel_offset.length; x++) {
                xval = (int)well.getX() - radius + x;
                yval = (int)well.getY() - radius + y;
                if (xval < 0 || yval < 0 || xval + pixel_offset[y][x][0] < 0 || yval + pixel_offset[y][x][1] < 0)
                    continue;
                canvasImage.setPixel(xval, yval, canvasImage.getPixel((xval + pixel_offset[y][x][0]), (yval + pixel_offset[y][x][1])));
            }
        }

        canvas.drawCircle((int)well.getX(), (int)well.getY(), 50, paint);

//        ArrayList<GravityWell> wells = gameHandler.getGravityWells();
//        for (int i = 0; i < wells.size(); i++) {
//            canvas.drawBitmap(wells.get(i).getImage(), (float)wells.get(i).getX() - (float)wells.get(i).getWidth() / 2, (float)wells.get(i).getY() - (float)wells.get(i).getHeight() / 2, paint);
//        }
    }

    public void doDraw(Canvas canvas) {

//        bitmapCanvas.drawColor(Color.WHITE);

//        bitmapCanvas.drawBitmap(gameHandler.getBackgroundImage(), gameHandler.getBackgroundX(), gameHandler.getBackgroundY(), paint);

        ConcurrentLinkedQueue<BackgroundImage> backgroundImages = gameHandler.getBackgroundImages();
        int tmpSize = backgroundImages.size();
        BackgroundImage tmpImage;
        for (int i = 0; i < tmpSize; i++) {
            tmpImage = backgroundImages.poll();
            bitmapCanvas.drawBitmap(tmpImage.getImage().getCurrentImage(), tmpImage.getX(), tmpImage.getY(), paint);
            backgroundImages.add(tmpImage);
        }

        drawGameObjects(bitmapCanvas);

        drawPlayer(bitmapCanvas);

//        for (int y = 400; y < 600; y++) {
//            for (int x = 800; x < 1000; x++) {
//                canvasImage.setPixel(x, y, 0);
//            }
//        }


//        drawGravityWells(canvas);

        bitmapCanvas.drawLine(gameHandler.getPlayerRightBound(), 0, gameHandler.getPlayerRightBound(), inputHandler.height, paint);
        bitmapCanvas.drawLine(gameHandler.getPlayerLeftBound(), 0, gameHandler.getPlayerLeftBound(), inputHandler.height, paint);
        bitmapCanvas.drawLine(0, gameHandler.getPlayerUpperBound(), inputHandler.width, gameHandler.getPlayerUpperBound(), paint);
        bitmapCanvas.drawLine(0, gameHandler.getPlayerLowerBound(), inputHandler.width, gameHandler.getPlayerLowerBound(), paint);
//        synchronized (this) {
//            if (!drawThread.isThreadAWaiting()) {
////                try {
//                    Log.d("Main Thread", "Waiting for ThreadA to finish.");
//                    drawThread.setMainThreadWaiting(true);
//                    while (drawThread.isMainThreadWaiting());
////                    drawThread.setMainThreadWaiting(false);
//                    Log.d("Main Thread", "ThreadA has finished.");
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//            }
//        }

        canvas.drawBitmap(canvasImage, 0, 0, null);
    }

    public void run() {

        long beginTime;
        long timeDiff;
        int sleepTime;
        int framesSkipped;

        int max_fps = 25;
        int max_frame_skips = 5;
        int frame_period = 1000 / max_fps;

        while (running) {
            c = null;

            sleepTime = 0;

            try {

                synchronized(sh) {
                    c = sh.lockCanvas(null);
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;

                    if (c != null) {
//                        synchronized (drawThread) {
//                            if (drawThread.isThreadAWaiting()) {
//                                Log.d("Main Thread", "ThreadA is waiting...About to notify.");
//                                drawThread.notifyAll();
//                            }
//                        }
                        gameHandler.update();
                        doDraw(c);
//                        synchronized (drawThread) {
//                            if (drawThread.getState() != State.WAITING) {
//                                try {
//                                    drawThread.wait();
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
                    }

                    timeDiff = System.currentTimeMillis() - beginTime;
                    sleepTime = (int)(frame_period - timeDiff);

                    if (sleepTime > 0) {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e){}
                    }

                    while (sleepTime < 0 && framesSkipped < max_frame_skips) {
                        gameHandler.update();
                        sleepTime += frame_period;
                        framesSkipped++;
                    }
                }
            } finally {
                if (c != null) {
//                    c.setBitmap(null);
                    sh.unlockCanvasAndPost(c);
                }
            }

            Log.d("Game Loop", "Frames Skipped: " + framesSkipped);
        }
    }

}