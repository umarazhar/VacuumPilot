package com.gamedesign.vacuumpilot.display;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class SurfaceManager extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder sh;

    private Context ctx;

    private InputHandler inputHandler;

    private DisplayThread displayThread;

    public SurfaceManager(Context context) {
        super(context);

        setDrawingCacheEnabled(true);
        sh = getHolder();

        sh.addCallback(this);

        this.ctx = context;

        WindowManager wm = (WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();

        Point tmp = new Point();
        disp.getSize(tmp);

        inputHandler = InputHandler.initHandler(tmp.x, tmp.y);

        SensorManager mSensorManager = (SensorManager)ctx.getSystemService(Context.SENSOR_SERVICE);
        Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(inputHandler, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        setOnTouchListener(inputHandler);
    }

    public void surfaceCreated(SurfaceHolder holder) {


        displayThread = new DisplayThread(sh, ctx, new Handler());
        displayThread.start();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}