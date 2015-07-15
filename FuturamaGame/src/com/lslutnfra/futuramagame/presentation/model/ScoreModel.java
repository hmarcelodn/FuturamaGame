package com.lslutnfra.futuramagame.presentation.model;

import java.util.Date;

public class ScoreModel {
	
	private Date timeStamp;	
	private int score;
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}	
	
}
