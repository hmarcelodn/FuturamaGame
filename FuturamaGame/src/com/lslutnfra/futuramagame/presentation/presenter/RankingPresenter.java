package com.lslutnfra.futuramagame.presentation.presenter;

import com.lslutnfra.futuramagame.presentation.view.RankingView;

public class RankingPresenter implements Presenter {

	@SuppressWarnings("unused")
	private final RankingView rankingView;
	
	public RankingPresenter(
			RankingView rankingView){
		this.rankingView = rankingView;
	}
	
	@Override public void resume() { }

	@Override public void pause() { }	
}
