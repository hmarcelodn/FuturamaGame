package com.lslutnfra.futuramagame.domain.interactor;

public interface PersistScoreInteractor extends Interactor {
	
	interface Callback {
		void onScoreSuccessfullySaved(int newScore);
	}
	
	void execute(int lives, int seconds, int chips, Callback callback);	
}
