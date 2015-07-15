package com.lslutnfra.futuramagame.domain.interactor;

import java.util.Collection;

import com.lslutnfra.futuramagame.domain.entity.Chip;

public interface GetChipListInteractor extends Interactor {

	interface Callback {
		void onChipListLoaded(Collection<Chip> chipCollection);
	}
	
	void execute(Callback callback);
}
