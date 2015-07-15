package com.lslutnfra.futuramagame.domain.interactor;

public interface GetBestScoreInteractor extends Interactor {

	interface Callback{
		void onBestScoreIsLoaded(long bestScore);
	}
	
	void execute(Callback callback);
}
