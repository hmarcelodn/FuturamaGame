package com.lslutnfra.futuramagame.domain.interactor;

import java.util.ArrayList;

import com.lslutnfra.futuramagame.domain.entity.Chip;
import com.lslutnfra.futuramagame.domain.executor.PostExecutionThread;
import com.lslutnfra.futuramagame.domain.executor.ThreadExecutor;
import com.lslutnfra.futuramagame.domain.repository.ChipRepository;

public class GetChipListUseCaseImpl implements GetChipListInteractor{
		
	private final ChipRepository chipDataRepository;
	private final ThreadExecutor threadExecutor;
	private final PostExecutionThread postExecutionThread;
	private Callback callback;
	
	public GetChipListUseCaseImpl(ChipRepository chipDataRepository, 
			ThreadExecutor threadExecutor, 
			PostExecutionThread postExecutionThread){		
		this.chipDataRepository = chipDataRepository;
		this.threadExecutor = threadExecutor;
		this.postExecutionThread = postExecutionThread;
	}
	
	@Override public void run() {
		this.chipDataRepository.getChipList(this.repositoryCallback);				
	}

	@Override public void execute(Callback callback) {
		this.threadExecutor.execute(this);
		this.callback = callback;
	}
	
	private final ChipRepository.Callback repositoryCallback = new ChipRepository.Callback() {
		@Override public void onChipListLoaded(ArrayList<Chip> chipItemList) {
			notifyGetChipListSuccessfully(chipItemList);
		}
	};

	private void notifyGetChipListSuccessfully(final ArrayList<Chip> chipItemList){
		this.postExecutionThread.post(new Runnable(){
			@Override public void run() {
				callback.onChipListLoaded(chipItemList);
			}			
		});
	}
}
