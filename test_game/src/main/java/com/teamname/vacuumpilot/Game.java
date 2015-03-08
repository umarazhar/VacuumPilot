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

    public Game(DrawView parent, MainActivity mainActivity, int w, int h) {
        this.parentView = parent;
        this.mainActivity = mainActivity;

        WIDTH = w;
        HEIGHT = h;

        rectWidth = 150;
        rectHeight = 150;

        rect = new Rect(0, 0, rectWidth, rectHeight);
    }

    public void moveX(int dx) {

        if (dx < 0) {
            dx = Math.max(0 - rect.left, dx);
        } else if (dx >= 0) {
            dx = Math.min(WIDTH - rect.right, dx);
        }
        rect.left += dx;
        rect.right += dx;
    }

    public void moveY(int dy) {
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

    public int getRectWidth() {
        return rectWidth;
    }

    public int getRectHeight() {
        return rectHeight;
    }

    public void center() {
        int dx = WIDTH / 2 - rect.left;
        int dy = HEIGHT / 2 - rect.top;

        moveX(dx);
        moveY(dy);
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
            delay(20);
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
