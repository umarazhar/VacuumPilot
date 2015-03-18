package com.gamedesign.vacuumpilot.foundation;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by Lenovo-USER on 3/17/2015.
 */
public class Library {

    public static Bitmap resizedBitmap(Bitmap image, int width, int height) {
        int curWidth = image.getWidth();
        int curHeight = image.getHeight();

        float scaleWidth = ((float)width) / curWidth;
        float scaleHeight = ((float)height) / curHeight;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap newImage = Bitmap.createBitmap(image, 0, 0, width, height, matrix, false);
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
