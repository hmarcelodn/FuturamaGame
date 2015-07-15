package com.lslutnfra.futuramagame.domain.repository;

import java.util.ArrayList;

import com.lslutnfra.futuramagame.domain.entity.Chip;

public interface ChipRepository {
	
	interface Callback{
		void onChipListLoaded(ArrayList<Chip> chipItemList);
	}
	
	void getChipList(Callback callback);
	
}
