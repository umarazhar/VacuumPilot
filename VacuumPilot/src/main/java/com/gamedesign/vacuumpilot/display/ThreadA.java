package com.gamedesign.vacuumpilot.display;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.gamedesign.vacuumpilot.game.GameObject;
import com.gamedesign.vacuumpilot.game.GravityWell;

import java.util.ArrayList;

/**
 * Created by Lenovo-USER on 3/23/2015.
 */
public class ThreadA extends Thread {

    private int width, height;

    private Thread mainThread;

    private ArrayList<GameObject> obstacles;

    private Canvas canvas;
    private Bitmap image;

    private Paint paint;

    private boolean running;
    private boolean threadAWaiting;
    private boolean mainThreadWaiting;

    public ThreadA(Thread mainThread, ArrayList<GameObject> obstacles, int width, int height) {
        this.mainThread = mainThread;
        this.obstacles = obstacles;
        this.width = width;
        this.height = height;

        this.canvas = new Canvas();
        running = true;
        threadAWaiting = true;
    }

    public void run() {
            while (running) {
                synchronized (this) {
                    try {
                        Log.d("ThreadA", "Waiting for main thread");
                        setThreadAWaiting(true);
                        wait();
                        setThreadAWaiting(false);
                        Log.d("ThreadA", "Main thread has sent notification.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                drawGameObjects(canvas);

                setThreadAWaiting(false);

                synchronized (mainThread) {
                    if (isMainThreadWaiting()) {
                        Log.d("ThreadA", "Stuck in loop...shouldn't be doing this.");
//                        if (mainThread.getState() == State.WAITING) {
//                            Log.d("ThreadA", "Notifying main thread");
//                            mainThread.notify();
//                        }
                        Log.d("ThreadA", "Notifying main thread");
                        setMainThreadWaiting(false);
//                        mainThread.notifyAll();
                    }
                }
            }
    }

    private void drawGameObjects(Canvas canvas) {
//        paint.setColor(Color.RED);

        ArrayList<GameObject> tmpList = obstacles;

        for (GameObject tmp : tmpList) {
            if (tmp.getClass() == GravityWell.class) {
//                double r = tmp.getWidth() * 3 / 4;
//                double h = (-2*r + Math.sqrt(Math.pow(2*r,2) + 4*Math.pow(r, 2))) / 2;
//                double factor = h / r;
//                int finalx, finaly;
//                double dist, newdist;
//                double angle;
//
//                for (int y = (int)(tmp.getY() - tmp.getHeight() / 2); y < tmp.getY() + tmp.getHeight() / 2; y++) {
//                    for (int x = (int)(tmp.getX() - tmp.getWidth() / 2); x < tmp.getX() + tmp.getWidth() / 2; x++) {
//                        if (x <= 0 || x >= width || y >= height || y <= 0)
//                            continue;
//                        dist = Math.pow(x - tmp.getX(), 2) + Math.pow(y - tmp.getY(), 2);
//                        if (dist < r*r) {
//                            newdist = dist * factor;
//                            angle = Math.atan2(y - tmp.getY(), x - tmp.getX());
//                            finalx = (int)(tmp.getX() + ((r + newdist) * Math.cos(angle)));
//                            finaly = (int)(tmp.getY() + ((r + newdist) * Math.sin(angle)));
//                            if (finalx >= 0 && finalx < image.getWidth() && finaly < image.getHeight() && finaly >= 0) {
//                                image.setPixel(finalx, finaly, image.getPixel(x, y));
//                            }
////                            canvasImage.setPixel(x, y, Color.BLACK);
//                            canvas.drawCircle((float)tmp.getX(), (float)tmp.getY(), (float)r, paint);
//                        }
//                    }
//                }
                Log.i("Second Thread", "Drawing Blackhole");
            } else {
                canvas.drawBitmap(tmp.getImage(), (float) (tmp.getX() - tmp.getWidth() / 2), (float) (tmp.getY() - tmp.getHeight() / 2), paint);
            }
        }

    }

//    public void hold() {
//        try {
//            this.wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public boolean isThreadAWaiting() {
        return threadAWaiting;
    }

    public void setThreadAWaiting(boolean threadAWaiting) {
        this.threadAWaiting = threadAWaiting;
    }

    public boolean isMainThreadWaiting() {
        return mainThreadWaiting;
    }

    public void setMainThreadWaiting(boolean mainThreadWaiting) {
        this.mainThreadWaiting = mainThreadWaiting;
    }

    public void setImage(Bitmap bitmap) {
        this.image = bitmap;
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        canvas.setBitmap(image);
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
