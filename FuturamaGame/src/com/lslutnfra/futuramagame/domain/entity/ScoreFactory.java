package com.lslutnfra.futuramagame.domain.entity;

import java.util.Date;

public class ScoreFactory {
	
	public Score Create(int points){
		
		Score score = new Score();
		score.setPoints(points);
		score.setTimeStamp(new Date());
		
		return score;
	}	
}
