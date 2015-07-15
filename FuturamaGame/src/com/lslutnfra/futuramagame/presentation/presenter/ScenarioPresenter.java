package com.lslutnfra.futuramagame.presentation.presenter;

import java.util.Collection;

import com.lslutnfra.futuramagame.domain.entity.Chip;
import com.lslutnfra.futuramagame.domain.interactor.GetBestScoreInteractor;
import com.lslutnfra.futuramagame.domain.interactor.GetChipListInteractor;
import com.lslutnfra.futuramagame.domain.interactor.PersistScoreInteractor;
import com.lslutnfra.futuramagame.domain.interactor.TimerGameInteractor;
import com.lslutnfra.futuramagame.presentation.mapper.ChipModelDataMapper;
import com.lslutnfra.futuramagame.presentation.model.ChipModel;
import com.lslutnfra.futuramagame.presentation.view.ScenarioView;

public class ScenarioPresenter implements Presenter{

	private final ScenarioView viewScenarioView;
	private final GetChipListInteractor getChipListInteractor;	
	private final TimerGameInteractor timerGameInteractor;
	private final PersistScoreInteractor persistScoreInteractor;
	private final GetBestScoreInteractor getBestScoreInteractor;
	private final ChipModelDataMapper chipModelDataMapper;
	
	public ScenarioPresenter(ScenarioView scenarioView, 
			GetChipListInteractor getChipListInteractor,
			TimerGameInteractor timerGameInteractor,
			PersistScoreInteractor persistScoreInteractor,
			GetBestScoreInteractor getBestScoreInteractor,
			ChipModelDataMapper chipModelDataMapper){
		this.viewScenarioView = scenarioView;
		this.getChipListInteractor = getChipListInteractor;
		this.timerGameInteractor = timerGameInteractor;
		this.persistScoreInteractor = persistScoreInteractor;
		this.getBestScoreInteractor = getBestScoreInteractor;
		this.chipModelDataMapper = chipModelDataMapper;
	}
	
	@Override public void resume() { }

	@Override public void pause() { }
	
	public void initialize(){
		this.initializeChipList();
		this.initializeGameTimer();
	}

	public void saveGameScore(int lives, int seconds, int chips){
		this.persistScoreInteractor.execute(lives, seconds, chips, this.persistScoreInteractorCallback);
	}
	
	public void getBestScore(){
		this.getBestScoreInteractor.execute(this.getBestScoreInteractorCallback);
	}
	
	private void initializeChipList(){
		this.getChipListInteractor.execute(this.chipListCallback);
	}
	
	private void initializeGameTimer(){
		this.timerGameInteractor.execute(this.timerGameCallback);
	}
	
	//Callbacks
	private final GetChipListInteractor.Callback chipListCallback = new GetChipListInteractor.Callback(){
		@Override public void onChipListLoaded(Collection<Chip> chipCollection) {
			final Collection<ChipModel> chipModelCollection = chipModelDataMapper.Transform(chipCollection);
			viewScenarioView.renderChipList(chipModelCollection);
		}		
	};
	
	private final TimerGameInteractor.Callback timerGameCallback = new TimerGameInteractor.Callback(){
		@Override public void onTimerChanged(int seconds) {
			viewScenarioView.changeTimer(seconds);			
		}

		@Override public void onGameIsOver(int remainingSeconds) {
			viewScenarioView.notifyGameIsOver(remainingSeconds);
		}		
	};
	
	private final PersistScoreInteractor.Callback persistScoreInteractorCallback = new PersistScoreInteractor.Callback() {		
		@Override public void onScoreSuccessfullySaved(int newScore) {
			viewScenarioView.getBestScore(newScore);			
		}
	};
	
	private final GetBestScoreInteractor.Callback getBestScoreInteractorCallback = new GetBestScoreInteractor.Callback() {		
		@Override public void onBestScoreIsLoaded(long bestScore) {
			viewScenarioView.getRankingScreen(bestScore);	
		}
	};
}
