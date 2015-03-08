package com.teamname.vacuumpilot;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;

public class MainActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DrawView drawView = new DrawView(this, MainActivity.this);
		drawView.setBackgroundColor(Color.WHITE);

		setContentView(drawView);
	}

}