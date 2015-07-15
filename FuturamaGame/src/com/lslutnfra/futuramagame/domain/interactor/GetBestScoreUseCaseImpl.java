package com.lslutnfra.futuramagame.domain.interactor;

import java.sql.SQLException;

import com.lslutnfra.futuramagame.domain.executor.PostExecutionThread;
import com.lslutnfra.futuramagame.domain.executor.ThreadExecutor;
import com.lslutnfra.futuramagame.domain.repository.ScoreRepository;

public class GetBestScoreUseCaseImpl implements GetBestScoreInteractor {
	
	private final ScoreRepository scoreRepository;
	private final ThreadExecutor threadExecutor;
	private final PostExecutionThread postExecutionThread;
	private Callback callback;
	
	public GetBestScoreUseCaseImpl(
			ScoreRepository scoreRepository,
			ThreadExecutor threadExecutor, 
			PostExecutionThread postExecutionThread){
		this.scoreRepository = scoreRepository;
		this.threadExecutor = threadExecutor;
		this.postExecutionThread = postExecutionThread;
	}
	
	@Override public void run() {
		try {
			this.scoreRepository.getBestScore(this.scoreRepositoryCallback);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override public void execute(Callback callback) {
		this.threadExecutor.execute(this);
		this.callback = callback;
	}
	
	//Callbacks
	private final ScoreRepository.GetBestScoreCallback scoreRepositoryCallback = new ScoreRepository.GetBestScoreCallback() {
		@Override public void onBestScoreIsLoaded(long bestScore) {
			onBestScoreIsLoadedSuccessfully(bestScore);
		}		
	};
	
	private void onBestScoreIsLoadedSuccessfully(final long bestScore){
		this.postExecutionThread.post(new Runnable(){
			@Override public void run() {
				callback.onBestScoreIsLoaded(bestScore);				
			}			
		});
	}
}
