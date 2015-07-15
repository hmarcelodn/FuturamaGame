package com.lslutnfra.futuramagame.presentation.view;

import java.util.Collection;

import com.lslutnfra.futuramagame.presentation.model.ChipModel;

public interface ScenarioView extends LoadDataView {

	void renderChipList(Collection<ChipModel> chipModelCollection);
	
	void changeTimer(int seconds);
	
	void notifyGameIsOver(int remainingSeconds);
	
	void getBestScore(int newScore);
	
	void getRankingScreen(long bestScore);
	
}
