package com.lslutnfra.futuramagame.data.repository;

import java.util.ArrayList;
import java.util.Collections;

import com.lslutnfra.futuramagame.R;
import com.lslutnfra.futuramagame.domain.entity.Chip;
import com.lslutnfra.futuramagame.domain.entity.ChipStatusType;
import com.lslutnfra.futuramagame.domain.repository.ChipRepository;

public class ChipDataRepository implements ChipRepository{


	@Override
	public void getChipList(Callback callback) {
		ArrayList<Chip> chipItemList = new ArrayList<Chip>();		
		
		for(int i=0;i < 2;i++)
		{		
			Chip chip = new Chip(ChipStatusType.BACK, R.drawable.amy);
			chipItemList.add(chip);
			
			chip = new Chip(ChipStatusType.BACK, R.drawable.bender);
			chipItemList.add(chip);
			
			chip = new Chip(ChipStatusType.BACK, R.drawable.farnsworth);
			chipItemList.add(chip);
			
			chip = new Chip(ChipStatusType.BACK, R.drawable.fry);
			chipItemList.add(chip);
			
			chip = new Chip(ChipStatusType.BACK, R.drawable.hermes);
			chipItemList.add(chip);
			
			chip = new Chip(ChipStatusType.BACK, R.drawable.nibbler);
			chipItemList.add(chip);	
			
			chip = new Chip(ChipStatusType.BACK, R.drawable.scruffy);
			chipItemList.add(chip);	
			
			chip = new Chip(ChipStatusType.BACK, R.drawable.zoidberg);
			chipItemList.add(chip);	
			
			//List Shuffling
			Collections.shuffle(chipItemList);
			Collections.shuffle(chipItemList);
		}
		
		callback.onChipListLoaded(chipItemList);		
	}

}
