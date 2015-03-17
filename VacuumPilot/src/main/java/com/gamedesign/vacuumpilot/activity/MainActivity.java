package com.gamedesign.vacuumpilot.activity;

import android.app.Activity;
import android.os.Bundle;

import com.gamedesign.vacuumpilot.display.SurfaceManager;


/**
 * Created by Lenovo-USER on 3/14/2015.
 */
public class MainActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SurfaceManager surfaceManager = new SurfaceManager(this);
        setContentView(surfaceManager);

    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onStop() {
        super.onStop();
    }
}
