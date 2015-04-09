package com.gamedesign.vacuumpilot.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gamedesign.vacuumpilot.R;


/**
 * Created by Lenovo-USER on 3/14/2015.
 */
public class MainActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        SurfaceManager surfaceManager = new SurfaceManager(this);
        setContentView(R.layout.activity_main);

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

    public void startGame(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
