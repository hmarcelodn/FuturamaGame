
package com.lslutnfra.futuramagame.domain.repository;

import java.sql.SQLException;

import com.lslutnfra.futuramagame.domain.entity.Score;

public interface ScoreRepository {
	
	interface PersistScoreCallback{
		void onNewScoreIsSaved(int newScore);
	}
	
	interface GetBestScoreCallback{
		void onBestScoreIsLoaded(long bestScore);
	}
	
	void saveScore(Score score, PersistScoreCallback callback) throws SQLException;
	
	void getBestScore(GetBestScoreCallback callback) throws SQLException;	
}
