package com.lslutnfra.futuramagame.data.repository;

import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.lslutnfra.futuramagame.data.infraestructure.DatabaseHelper;
import com.lslutnfra.futuramagame.domain.entity.Score;
import com.lslutnfra.futuramagame.domain.repository.ScoreRepository;

public class ScoreDataRepository implements ScoreRepository {
	
	private DatabaseHelper helper;
	private Context context;
	private RuntimeExceptionDao<Score, Integer> runtimeExceptionScoreDao = null;
	
	public ScoreDataRepository(Context context){	
		this.context = context;
		this.helper = OpenHelperManager.getHelper(this.context, DatabaseHelper.class);				 		
		this.runtimeExceptionScoreDao = helper.getRuntimeExceptionScoreDao();		
	}

	@Override public void saveScore(Score score, PersistScoreCallback callback) {				
		this.runtimeExceptionScoreDao.create(score);
		callback.onNewScoreIsSaved(score.getPoints());
	}

	@Override
	public void getBestScore(GetBestScoreCallback callback) throws SQLException {
		
		long maxScore = this.runtimeExceptionScoreDao
				.queryRawValue("select max(score) from Score");				
		
		callback.onBestScoreIsLoaded(maxScore);
	}
}
