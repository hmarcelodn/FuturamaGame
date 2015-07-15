package com.lslutnfra.futuramagame.domain.interactor;

import com.lslutnfra.futuramagame.domain.executor.PostExecutionThread;
import com.lslutnfra.futuramagame.domain.executor.ThreadExecutor;

public class TimerGameUseCaseImpl implements TimerGameInteractor {

	private final ThreadExecutor threadExecutor;
	private final PostExecutionThread postExecutionThread;
	private Callback callback;
	
	private int timerSeconds;
	private boolean stopThread;
	
	public TimerGameUseCaseImpl(ThreadExecutor threadExecutor, 
			PostExecutionThread postExecutionThread){		
		this.threadExecutor = threadExecutor;
		this.postExecutionThread = postExecutionThread;
		this.stopThread = false;
		this.timerSeconds = 60;
	}
	
	@Override public void run() {
		while(true)
		{			
			if(this.stopThread){
				this.stopThread = false;
				try {
					synchronized(this){
						wait();	
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}			
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally{				
				//Decrease time
				this.timerSeconds--;						
				
				if(this.timerSeconds == 0){
					notifyGameIsOver(this.timerSeconds);
					this.stopThread = true;
				}
					
				notifyTimerChangedSuccessfully(timerSeconds);			
			}	
		}		
	}

	@Override public void stopExecution() {
		this.stopThread = true;
		notifyGameIsOver(this.timerSeconds);
	}

	@Override public void execute(Callback callback) {		
		this.callback = callback;
		this.threadExecutor.execute(this);
	}
	
	private void notifyTimerChangedSuccessfully(final int seconds){
		this.postExecutionThread.post(new Runnable(){
			@Override public void run() {
				callback.onTimerChanged(seconds);
			}			
		});
	}
	
	private void notifyGameIsOver(final int remainingSeconds){
		this.postExecutionThread.post(new Runnable(){
			@Override public void run() {
				callback.onGameIsOver(remainingSeconds);
			}			
		});
	}
}
