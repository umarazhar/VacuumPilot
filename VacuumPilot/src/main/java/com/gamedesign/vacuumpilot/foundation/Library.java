package com.gamedesign.vacuumpilot.foundation;

import android.graphics.Bitmap;

/**
 * Created by Lenovo-USER on 3/17/2015.
 */
public class Library {

    public static Bitmap resizedBitmap(Bitmap image, int width, int height) {
        int curWidth = image.getWidth();
        int curHeight = image.getHeight();

        float ratio = Math.min(((float)width) / curWidth, ((float)height) / curHeight);

//        Matrix matrix = new Matrix();
//        matrix.setScale(scaleWidth, scaleHeight);

        Bitmap newImage = Bitmap.createScaledBitmap(image, (int)(width * ratio), (int)(height * ratio), false);
        image.recycle();
        return newImage;

    }

    public static int randint(int low, int high) {
        int randnum = (int)(Math.random() * (high - low)) + low;
        return randnum;
    }

    public static void delay(long millis) {
        Thread current = Thread.currentThread();

        try {
            current.sleep(millis);
        } catch (Exception e) {

        }
    }
}
