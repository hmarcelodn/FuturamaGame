package com.lslutnfra.futuramagame.presentation.activity;

import java.util.Collection;

import com.lslutnfra.futuramagame.R;
import com.lslutnfra.futuramagame.presentation.model.ScoreModel;
import com.lslutnfra.futuramagame.presentation.presenter.RankingPresenter;
import com.lslutnfra.futuramagame.presentation.view.RankingView;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RankingActivity 
	extends ActionBarActivity 
	implements RankingView, OnClickListener {
	
	@SuppressWarnings("unused")
	private RankingPresenter rankingPresenter;
	
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking);
		
		//Get Widgets		
		TextView txtGameOverTitle = (TextView)findViewById(R.id.txtGameOver);
		TextView txtScore = (TextView)findViewById(R.id.txtScoreView);
		TextView txtYourScore = (TextView)findViewById(R.id.txtBestScoreView);
		Button btnTryAgain = (Button)findViewById(R.id.btnTryAgain);
		Button btnExit = (Button)findViewById(R.id.btnExit);
		Button btnMenu = (Button)findViewById(R.id.btnMenu);
		
		//Get custom font from assets
		Typeface futuramaFont = Typeface.createFromAsset(getAssets(), "frtitle.ttf");
		
		//Set Fonts
		txtGameOverTitle.setTypeface(futuramaFont);
		txtScore.setTypeface(futuramaFont);
		txtYourScore.setTypeface(futuramaFont);
		btnTryAgain.setTypeface(futuramaFont);
		btnExit.setTypeface(futuramaFont);
		btnMenu.setTypeface(futuramaFont);
		
		//Set Sizes
		txtGameOverTitle.setTextSize(70);
		txtScore.setTextSize(50);
		txtYourScore.setTextSize(50);
		btnTryAgain.setTextSize(50);
		btnMenu.setTextSize(50);
		btnExit.setTextSize(50);
		
		//On Click Listener
		btnExit.setOnClickListener(this);
		btnMenu.setOnClickListener(this);
		btnTryAgain.setOnClickListener(this);
		
		//Workaround for API 11 and lower
		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();		
		
		//Presenter
		this.rankingPresenter = new RankingPresenter(this);
		
		//Get Scores
		Intent rankingIntent = getIntent();
		int yourScoreData = rankingIntent.getIntExtra("scorePointsExtra", 0);
		long bestScoreData = rankingIntent.getLongExtra("bestScorePointsExtra", 0);
		
		txtScore.setText(txtScore.getText() + " " + Integer.toString(yourScoreData));
		txtYourScore.setText(txtYourScore.getText() + " " + Long.toString(bestScoreData));
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.ranking, menu);
		return true;
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override public Context getContext() {
		return getApplicationContext();
	}

	@Override public void renderRankingList(Collection<ScoreModel> scoreModelCollection) {
		//TODO: Return Best Score from DB		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btnExit:
				finish();
				break;
			case R.id.btnMenu:
				Intent menuIntent = new Intent(this, StartActivity.class);
				startActivity(menuIntent);
				finish();
				break;
			case R.id.btnTryAgain:
				Intent scenarioIntent = new Intent(this, ScenarioActivity.class);
				startActivity(scenarioIntent);
				finish();
				break;
		}		
	}
}
