package com.lslutnfra.futuramagame.domain.entity;

public class ScoreCalculator {

	public long Calculate(int lives, int remainingSeconds, int chipsQty){		
		long points = 0;
				
		points += (chipsQty * 2);
		
		if(remainingSeconds != 0){
			points += (lives * 5);	
		}
		
		if(lives != 0){
			points += (remainingSeconds * 6);
		}
		
		return points;
	}
	
}
