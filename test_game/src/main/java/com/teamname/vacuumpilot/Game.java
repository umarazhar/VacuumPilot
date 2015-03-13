package com.teamname.vacuumpilot;

import android.view.View;
import android.graphics.Rect;

/**
 * Created by Lenovo-USER on 2/17/2015.
 */
public class Game extends Thread {

    private final int WIDTH, HEIGHT;

    private DrawView parentView;

    private final MainActivity mainActivity;

    private Rect rect;
    private int rectWidth;
    private int rectHeight;

    private double vx, vy;

    public Game(DrawView parent, MainActivity mainActivity, int w, int h) {
        this.parentView = parent;
        this.mainActivity = mainActivity;

        WIDTH = w;
        HEIGHT = h;

        rectWidth = 150;
        rectHeight = 150;

        rect = new Rect(0, 0, rectWidth, rectHeight);
    }

    public void moveX() {

        int dx = (int)vx;

        if (dx < 0) {
            dx = Math.max(0 - rect.left, dx);
        } else if (dx >= 0) {
            dx = Math.min(WIDTH - rect.right, dx);
        }
        rect.left += dx;
        rect.right += dx;
    }

    public void moveY() {

        int dy = (int)vy;

        if (dy < 0) {
            dy = Math.max(0 - rect.top, dy);
        } else if (dy >= 0) {
            dy = Math.min(HEIGHT - rect.bottom, dy);
        }
        rect.top += dy;
        rect.bottom += dy;
    }

    public int getX() {
        return rect.left;
    }

    public int getY() {
        return rect.top;
    }

    public void setVX(double vx) {
        this.vx = vx;
    }

    public void setVY(double vy) {
        this.vy = vy;
    }

    public double getVX() {
        return vx;
    }

    public double getVY() {
        return vy;
    }

    public int getRectWidth() {
        return rectWidth;
    }

    public int getRectHeight() {
        return rectHeight;
    }

    public void center() {
        int dx = WIDTH / 2 - rect.left;
        int dy = HEIGHT / 2 - rect.top;

        rect.left += dx;
        rect.right += dx;
        rect.top += dy;
        rect.bottom += dy;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (parentView) {
                mainActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        parentView.invalidate();
                    }
                });
            }
            delay(10);
        }
    }

    private void delay(long milliseconds) {
        Thread current = Thread.currentThread();
        try {
            current.sleep(milliseconds);
        } catch (Exception e) {

        }
    }
}
