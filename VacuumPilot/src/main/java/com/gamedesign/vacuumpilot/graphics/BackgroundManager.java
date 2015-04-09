package com.gamedesign.vacuumpilot.graphics;

import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Lenovo-USER on 4/5/2015.
 */
public class BackgroundManager {

    private ConcurrentLinkedQueue<BackgroundImage> images;

    private int new_image_position = 0;

    private int frame_rate = 50;

    private int counter = 0;

    public BackgroundManager() {
       this.images = new ConcurrentLinkedQueue<BackgroundImage>();
    }

    public BackgroundManager(SpriteManager image) {
        this.images = new ConcurrentLinkedQueue<BackgroundImage>();
        addImage(image);
        addImage(image);
    }

    public void update() {
        if (!images.isEmpty()) {
            BackgroundImage tmp = images.peek();
            tmp.getImage().update();
            Log.d("background manager", "end of pic: " + tmp.getX() + tmp.getImage().getWidth());
            Log.d("Background Manager","Size of queue: " + images.size());
            if (tmp.getX() + tmp.getImage().getWidth() <= 0) {
                tmp = images.poll();

                if (!tmp.isExpired()) {
                    Log.d("background manager", "Adding image back to Queue");
                    addImage(tmp);
                }
            }
        }
    }

    private void addImage(SpriteManager newImage) {
        int y = 0;
        if (!images.isEmpty())
            y = images.peek().getY();

        images.add(new BackgroundImage(newImage, new_image_position, y));

        new_image_position += newImage.getWidth();
    }

    private void addImage(BackgroundImage newImage) {
        int y = 0;
        if (!images.isEmpty())
            y = images.peek().getY();

        newImage.setX(new_image_position);
        newImage.setY(y);

        images.add(newImage);

        new_image_position += newImage.getImage().getWidth();
    }

    public void translate(int dx, int dy) {
        int tmpSize = images.size();
        for (int i = 0; i < tmpSize; i++) {
            BackgroundImage tmp = images.poll();
            tmp.setX(tmp.getX() + dx);
            tmp.setY(tmp.getY() + dy);
            images.add(tmp);
        }

        new_image_position += dx;
    }

    public ConcurrentLinkedQueue<BackgroundImage> getImages() {
        return images;
    }

    public BackgroundImage getFirstImage() {
        return images.peek();
    }
}
