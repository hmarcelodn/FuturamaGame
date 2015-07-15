package com.lslutnfra.futuramagame.presentation.mapper;

import java.util.ArrayList;
import java.util.Collection;

import com.lslutnfra.futuramagame.domain.entity.Score;
import com.lslutnfra.futuramagame.presentation.model.ScoreModel;

public class ScoreModelDataMapper {
	
	public ScoreModel Transform(Score score){
		ScoreModel scoreModel = new ScoreModel();
		scoreModel.setScore(score.getPoints());
		scoreModel.setTimeStamp(score.getTimeStamp());
		
		return scoreModel;
	}
	
	public Collection<ScoreModel> Transform(Collection<Score> scoreEntityCollection){	
		Collection<ScoreModel> scoreCollection = new ArrayList<ScoreModel>();
		
		for(Score score:scoreEntityCollection)
			scoreCollection.add(this.Transform(score));
				
		return scoreCollection;
	}	
}
