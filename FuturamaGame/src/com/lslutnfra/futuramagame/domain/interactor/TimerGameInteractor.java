package com.lslutnfra.futuramagame.domain.interactor;

public interface TimerGameInteractor extends Interactor {
	
	interface Callback {
		void onTimerChanged(int seconds);
		
		void onGameIsOver(int seconds);
	}	
	
	void execute(Callback callback);
	
	void stopExecution();
}
