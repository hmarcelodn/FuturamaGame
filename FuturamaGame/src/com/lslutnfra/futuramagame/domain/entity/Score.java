package com.lslutnfra.futuramagame.domain.entity;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Score {
	
	private static final String ID = "_id";
	private static final String SCORE = "score";
	private static final String TIMESTAMP = "timestamp";
	
	@DatabaseField(generatedId = true, columnName = ID)
	private int id;
	
	@DatabaseField(columnName = SCORE)
	private int points;		
	
	@DatabaseField(columnName = TIMESTAMP)
	private Date timeStamp; 
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
		
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
}
