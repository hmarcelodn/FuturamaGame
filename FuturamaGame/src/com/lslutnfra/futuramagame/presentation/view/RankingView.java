package com.lslutnfra.futuramagame.presentation.view;

import java.util.Collection;

import com.lslutnfra.futuramagame.presentation.model.ScoreModel;

public interface RankingView extends LoadDataView {
	
	void renderRankingList(Collection<ScoreModel> scoreModelCollection);
	
}
