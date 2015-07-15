package com.lslutnfra.futuramagame.presentation.mapper;

import java.util.ArrayList;
import java.util.Collection;

import com.lslutnfra.futuramagame.domain.entity.Chip;
import com.lslutnfra.futuramagame.presentation.model.ChipModel;

public class ChipModelDataMapper {
	public ChipModel Transform(Chip chip){
		ChipModel chipModel = new ChipModel();
		chipModel.setChipImage(chip.getChipImage());
		chipModel.setStatusChip(chip.getStatusChip());
		
		return chipModel;
	}
	
	public Collection<ChipModel> Transform(Collection<Chip> chipsEntityCollection){
		Collection<ChipModel> chipsCollection = new ArrayList<ChipModel>();		
		for(Chip chip:chipsEntityCollection){
			chipsCollection.add(Transform(chip));
		}
		
		return chipsCollection;
	}
}
