package com.lslutnfra.futuramagame.presentation.activity;

import java.util.ArrayList;
import java.util.Collection;

import com.lslutnfra.futuramagame.R;
import com.lslutnfra.futuramagame.data.executor.JobExecutor;
import com.lslutnfra.futuramagame.data.repository.ChipDataRepository;
import com.lslutnfra.futuramagame.data.repository.ScoreDataRepository;
import com.lslutnfra.futuramagame.domain.entity.ScoreCalculator;
import com.lslutnfra.futuramagame.domain.executor.PostExecutionThread;
import com.lslutnfra.futuramagame.domain.executor.ThreadExecutor;
import com.lslutnfra.futuramagame.domain.interactor.GetBestScoreInteractor;
import com.lslutnfra.futuramagame.domain.interactor.GetBestScoreUseCaseImpl;
import com.lslutnfra.futuramagame.domain.interactor.GetChipListInteractor;
import com.lslutnfra.futuramagame.domain.interactor.GetChipListUseCaseImpl;
import com.lslutnfra.futuramagame.domain.interactor.PersistScoreInteractor;
import com.lslutnfra.futuramagame.domain.interactor.PersistScoreUseCaseImpl;
import com.lslutnfra.futuramagame.domain.interactor.TimerGameInteractor;
import com.lslutnfra.futuramagame.domain.interactor.TimerGameUseCaseImpl;
import com.lslutnfra.futuramagame.domain.repository.ChipRepository;
import com.lslutnfra.futuramagame.domain.repository.ScoreRepository;
import com.lslutnfra.futuramagame.presentation.adapter.ChipAdapter;
import com.lslutnfra.futuramagame.presentation.executor.UIThread;
import com.lslutnfra.futuramagame.presentation.mapper.ChipModelDataMapper;
import com.lslutnfra.futuramagame.presentation.model.ChipModel;
import com.lslutnfra.futuramagame.presentation.presenter.ScenarioPresenter;
import com.lslutnfra.futuramagame.presentation.view.ScenarioView;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

public class ScenarioActivity 
	extends ActionBarActivity
	implements ScenarioView {

	private ChipClickEventListener chipClickEventListener;
	private MediaPlayer mpStartTheme;
	private ChipAdapter chipAdapter;
	private int yourScore;
	
	//Presenter
	private ScenarioPresenter scenarioPresenter;
	
	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scenario);	
		
		//Screen Orientation
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		//Workaround for API 11 and lower
		ActionBar actionBar = getSupportActionBar();
		actionBar.hide();
		
		//Views
		TextView txtLives = (TextView)findViewById(R.id.txtLives);
		
		//Media Players
		MediaPlayer mpItemsDisabled = MediaPlayer.create(this, R.raw.itemup);
		MediaPlayer mpVictory = MediaPlayer.create(this, R.raw.victory);
		MediaPlayer mpChipClick = MediaPlayer.create(this, R.raw.chipclick);
		
		//Media Players
		this.mpStartTheme = MediaPlayer.create(this, R.raw.theme);				
		
		//Application Background Queue Thread Pool		
		ThreadExecutor threadExecutor = new JobExecutor();
		PostExecutionThread postExecutionThread = UIThread.getInstance();
		
		//Data Mappers
		ChipModelDataMapper chipModelDataMapper = new ChipModelDataMapper();
		
		//Repositories
		ChipRepository chipDataRepository = new ChipDataRepository();
		ScoreRepository scoreRepository = new ScoreDataRepository(this.getContext());
		
		//Collaborators
		ScoreCalculator scoreCalculator = new ScoreCalculator();
		
		//Interactor
		GetChipListInteractor getChipListInteractor = new GetChipListUseCaseImpl(
				chipDataRepository, 
				threadExecutor, 
				postExecutionThread);				
		
		TimerGameInteractor timerGameInteractor = new TimerGameUseCaseImpl(
				threadExecutor, 
				postExecutionThread);
		
		PersistScoreInteractor persistScoreInteractor = new PersistScoreUseCaseImpl(
				threadExecutor, 
				postExecutionThread,
				scoreRepository,
				scoreCalculator);
		
		GetBestScoreInteractor getBestScoreInteractor = new GetBestScoreUseCaseImpl(
				scoreRepository,
				threadExecutor, 
				postExecutionThread);
		
		//Presenter
		this.scenarioPresenter = new ScenarioPresenter(
											this, 
											getChipListInteractor, 
											timerGameInteractor,
											persistScoreInteractor,
											getBestScoreInteractor,
											chipModelDataMapper);
		
		//Initialize Presenter
		this.scenarioPresenter.initialize();	
		
		//Listeners
		this.chipClickEventListener = 
				new ChipClickEventListener(mpItemsDisabled, 
						mpVictory, 
						mpChipClick, 
						timerGameInteractor,
						txtLives);
		
		//Adapters
		chipAdapter = new ChipAdapter(ScenarioActivity.this);
		GridView chipGridView = (GridView) findViewById(R.id.gridViewChips);		
		chipGridView.setAdapter(chipAdapter);
		chipGridView.setOnItemClickListener(chipClickEventListener);
		
		//Game Sound	
		this.mpStartTheme.start();
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.scenario, menu);			
		return true;
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override protected void onDestroy() {
		this.mpStartTheme.stop();
		this.mpStartTheme.release();
		
		super.onDestroy();
	}

	@Override public Context getContext() {
		return this.getApplicationContext();
	}

	@Override public void renderChipList(Collection<ChipModel> chipModelCollection) {
		ArrayList<ChipModel> chipItems = (ArrayList<ChipModel>) chipModelCollection;
		this.chipAdapter.addAll(chipItems);
	}

	@Override public void changeTimer(int seconds) {
		TextView txtView = (TextView) findViewById(R.id.txtTimer);		
		txtView.setText(Integer.toString(seconds));
	}

	//1
	@Override public void notifyGameIsOver(int remainingSeconds) {
		this.scenarioPresenter.saveGameScore(
				this.chipClickEventListener.getLives(),
				remainingSeconds,
				this.chipClickEventListener.getWonChips());		
	}

	//2
	@Override public void getBestScore(int newScore) {
		this.yourScore = newScore;
		
		Log.d("Your Score", Integer.toString(newScore));
		this.scenarioPresenter.getBestScore();	
	}

	//3
	@Override public void getRankingScreen(long bestScore) {
		Log.d("Best Score", Long.toString(bestScore));
		
		Intent rankingIntent = new Intent(this, RankingActivity.class);
		
		rankingIntent.putExtra("scorePointsExtra", this.yourScore);
		rankingIntent.putExtra("bestScorePointsExtra", bestScore);
		
		startActivity(rankingIntent);
		finish();
	}
}
