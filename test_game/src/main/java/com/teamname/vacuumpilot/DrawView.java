package com.teamname.vacuumpilot;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.Display;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Canvas;
import android.graphics.Color;
import android.content.Context;

public class DrawView extends View implements SensorEventListener{

    private final int WIDTH, HEIGHT;

	private Paint paint;
    private Game game;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private MainActivity mainActivity;
	
	public DrawView(Context context, MainActivity mainActivity) {
		super(context);
        this.mainActivity = mainActivity;
		paint = new Paint();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        
        Point tmp = new Point();
        display.getSize(tmp);
        
        WIDTH = tmp.x;
        HEIGHT = tmp.y;

        game = new Game(this, mainActivity, WIDTH, HEIGHT);
        game.start();

        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        game.setVX(game.getVX() - x);
        game.setVY(game.getVY() + y);

        game.moveX();
        game.moveY();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void callInvalidate() {
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent e) {
        Log.d("Touch Screen", "Screen touched! At position: (" + e.getX() + "," + e.getY() + ")");

        game.center();

        return true;
    }

    public void onDraw(Canvas canvas) {
        Log.d("Testing loops", "testing");
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(game.getX(), game.getY(), game.getX() + game.getRectWidth(), game.getY() + game.getRectHeight(), paint);
    }

//    public void run() {
//        while (true) {
//            Log.d("Run Method", "Inside Run method!");
//            invalidate();
//            delay(20);
//        }
//    }

    private void delay(long milliseconds) {
        Thread current = Thread.currentThread();
        try {
            current.sleep(milliseconds);
        } catch (Exception e) {

        }
    }
}