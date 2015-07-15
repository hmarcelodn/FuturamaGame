package com.lslutnfra.futuramagame.domain.interactor;

import com.lslutnfra.futuramagame.domain.entity.ChipStatusType;
import com.lslutnfra.futuramagame.presentation.adapter.ChipAdapter;
import com.lslutnfra.futuramagame.presentation.model.ChipModel;

import android.os.AsyncTask;

public class FailedChipProcessor extends AsyncTask<Void, String, Void> {

	private ChipModel previousChip; 
	private ChipModel currentChip;
	private ChipAdapter chipAdapter;
	
	public FailedChipProcessor(ChipModel previousChip, ChipModel currentChip, ChipAdapter chipAdapter){
		this.previousChip = previousChip;
		this.currentChip = currentChip;
		this.chipAdapter = chipAdapter;
	}	
	
	@Override protected Void doInBackground(Void... params) {				
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
				
		return null;
	}

	@Override protected void onPostExecute(Void result) {				
		this.previousChip.setStatusChip(ChipStatusType.BACK);
		this.currentChip.setStatusChip(ChipStatusType.BACK);
		this.chipAdapter.notifyDataSetChanged();		
	}
	
}
