package com.lslutnfra.futuramagame.domain.interactor;

import java.sql.SQLException;

import com.lslutnfra.futuramagame.domain.entity.Score;
import com.lslutnfra.futuramagame.domain.entity.ScoreCalculator;
import com.lslutnfra.futuramagame.domain.entity.ScoreFactory;
import com.lslutnfra.futuramagame.domain.executor.PostExecutionThread;
import com.lslutnfra.futuramagame.domain.executor.ThreadExecutor;
import com.lslutnfra.futuramagame.domain.repository.ScoreRepository;

public class PersistScoreUseCaseImpl implements PersistScoreInteractor {

	private final ThreadExecutor threadExecutor;
	private final PostExecutionThread postExecutionThread;
	private final ScoreRepository scoreRepository;
	private final ScoreCalculator scoreCalculator;
	private Callback callback;	
	private int lives;
	private int seconds;
	private int chips;
	
	public PersistScoreUseCaseImpl(ThreadExecutor threadExecutor, 
								   PostExecutionThread postExecutionThread,
								   ScoreRepository scoreRepository, 
								   ScoreCalculator scoreCalculator){
		this.threadExecutor = threadExecutor;
		this.postExecutionThread = postExecutionThread;
		this.scoreRepository = scoreRepository;
		this.scoreCalculator = scoreCalculator;
	}
		
	@Override public void run() {
		try {
			int scorePoints = (int) this.scoreCalculator.Calculate(lives, seconds, chips);
			Score score = new ScoreFactory().Create(scorePoints);		
			this.scoreRepository.saveScore(score, this.persistScoreCallback);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override public void execute(int lives, int seconds, int chips, Callback callback) {
		this.lives = lives;
		this.seconds = seconds;
		this.chips = chips;
		this.callback = callback;
		this.threadExecutor.execute(this);
	}
	
	private final ScoreRepository.PersistScoreCallback persistScoreCallback = new ScoreRepository.PersistScoreCallback() {		
		@Override public void onNewScoreIsSaved(int newScore) {
			notifyScoreWasSavedSucessfully(newScore);
		}
	};
	
	private void notifyScoreWasSavedSucessfully(final int newScore){
		this.postExecutionThread.post(new Runnable(){
			@Override public void run() {
				callback.onScoreSuccessfullySaved(newScore);			
			}			
		});
	}
}
