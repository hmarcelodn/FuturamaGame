package com.lslutnfra.futuramagame.presentation.activity;

import com.lslutnfra.futuramagame.domain.entity.ChipStatusType;
import com.lslutnfra.futuramagame.domain.interactor.FailedChipProcessor;
import com.lslutnfra.futuramagame.domain.interactor.TimerGameInteractor;
import com.lslutnfra.futuramagame.presentation.adapter.ChipAdapter;
import com.lslutnfra.futuramagame.presentation.model.ChipModel;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;


public class ChipClickEventListener implements OnItemClickListener  {

	private int clickCounter;	
	private int previousPosition;
	private int wonChips;
	private int lives;
	private ChipModel previousChip;	
	private FailedChipProcessor failedChipProcessor;
	private TimerGameInteractor timerGameInteractor;		
	private MediaPlayer mpItemsDisabledSound;
	private MediaPlayer mpVictorySound;
	private MediaPlayer mpChipClickSound;
	private TextView txtLives;
	
	public ChipClickEventListener(
			MediaPlayer mpItemsDisabledSound, 
			MediaPlayer mpVictorySound, 
			MediaPlayer mpChipClickSound,
			TimerGameInteractor timerGameInteractor,
			TextView txtLives){
		this.lives = 12;
		this.clickCounter = 0;
		this.previousPosition = Integer.MIN_VALUE;
		this.previousChip = null;
		this.mpItemsDisabledSound = mpItemsDisabledSound;
		this.mpVictorySound = mpVictorySound;
		this.mpChipClickSound = mpChipClickSound;
		this.timerGameInteractor = timerGameInteractor;
		this.txtLives = txtLives;
	}
	
	@Override public void onItemClick(AdapterView<?> parent, View view, int position,long id) {								
		ChipAdapter chipAdapter = (ChipAdapter) parent.getAdapter();
		ChipModel selectedItemChip = (chipAdapter).getItem(position);		
		
		if(this.previousPosition != position && selectedItemChip.getStatusChip() != ChipStatusType.DISABLED){		
			mpChipClickSound.start();
			this.clickCounter++;
			this.previousPosition = position;
			selectedItemChip.setStatusChip(ChipStatusType.FRONT);
			
			if(clickCounter == 1){
				this.previousChip = selectedItemChip;
			}
			else{
				this.clickCounter = 0;
				if(selectedItemChip.getChipImage() == this.previousChip.getChipImage()){
					wonChips+=2;
					
					if(wonChips != chipAdapter.getCount()){
						mpItemsDisabledSound.start();	
					}
					
					selectedItemChip.setStatusChip(ChipStatusType.DISABLED);
					this.previousChip.setStatusChip(ChipStatusType.DISABLED);
										
					if(wonChips == chipAdapter.getCount()){
						mpItemsDisabledSound.stop();
						mpVictorySound.start();
						
						synchronized(this.timerGameInteractor){
							this.timerGameInteractor.stopExecution();								
						}
					}										
				}
				else{
					//Decreasing lives
					this.lives--;
					
					txtLives.setText(" " + this.lives + " x ");
					
					selectedItemChip.setStatusChip(ChipStatusType.FRONT);
					chipAdapter.notifyDataSetChanged();	
					
					this.failedChipProcessor = new FailedChipProcessor(previousChip, selectedItemChip, chipAdapter);
					this.failedChipProcessor.execute();

					//Lives Management
					if(this.lives == 0){						
						synchronized(this.timerGameInteractor){
							this.timerGameInteractor.stopExecution();								
						}
					}
				}
			}				
			
			chipAdapter.notifyDataSetChanged();	
		}
	}
	
	public int getLives(){
		return this.lives;
	}
	
	public int getWonChips(){
		return this.wonChips;
	}
}
