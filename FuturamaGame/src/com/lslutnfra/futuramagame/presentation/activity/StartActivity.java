package com.lslutnfra.futuramagame.presentation.activity;

import com.lslutnfra.futuramagame.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends ActionBarActivity {

	private MediaPlayer mpStartTheme;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		//Hide Action Bar
		getActionBar().hide();
		
		//Load fonts		
		//Get custom font from assets
		Typeface futuramaFont = Typeface.createFromAsset(getAssets(), "frtitle.ttf");
		
		//Media Players
		this.mpStartTheme = MediaPlayer.create(this, R.raw.theme);
		mpStartTheme.start();
		
		//Settings Buttons
		Button btnStart = (Button)findViewById(R.id.btnStart);
		Button btnExit = (Button)findViewById(R.id.btnExit);
		
		btnStart.setTypeface(futuramaFont);
		btnStart.setTextSize(70);

		btnExit.setTypeface(futuramaFont);
		btnExit.setTextSize(70);
		
		btnStart.setOnClickListener(new OnClickListener() {			
			@Override public void onClick(View v) {	
				finish();
				
				Handler scenarioHandler = new Handler();
				scenarioHandler.postDelayed(new Runnable(){
					@Override public void run() {
						startActivity(new Intent(StartActivity.this, ScenarioActivity.class));
					}					
				}, 500);				 
			}
		});
		
		btnExit.setOnClickListener(new OnClickListener(){
			@Override public void onClick(View v) {
				finish();
			}			
		});
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override protected void onDestroy() {		
		//Stop Theme
		this.mpStartTheme.stop();
		this.mpStartTheme.release();
		
		//Destroy
		super.onDestroy();
	}
}
