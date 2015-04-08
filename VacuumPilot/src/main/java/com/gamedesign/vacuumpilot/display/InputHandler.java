package com.gamedesign.vacuumpilot.display;

import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.hardware.Sensor;
import android.view.View;
import android.view.MotionEvent;

import java.lang.Override;

public class InputHandler implements SensorEventListener, View.OnTouchListener {

    private static InputHandler inputHandler;

    public int width;
    public int height;

    public float touch_x, touch_y;
    public int touch_m = MotionEvent.ACTION_UP;

    public float accel_x, accel_y, accel_z;


    private InputHandler(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static InputHandler initHandler(int width, int height) {
        if (inputHandler == null) {
            inputHandler = new InputHandler(width, height);
        }

        return inputHandler;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        touch_x = event.getX();
        touch_y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_m = MotionEvent.ACTION_DOWN;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                touch_m = MotionEvent.ACTION_UP;
                break;

        }

        return (touch_m == MotionEvent.ACTION_DOWN);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        accel_x = event.values[0];
        accel_y = event.values[1];
        accel_z = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}