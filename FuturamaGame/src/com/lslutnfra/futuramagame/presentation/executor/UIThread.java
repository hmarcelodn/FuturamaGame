package com.lslutnfra.futuramagame.presentation.executor;

import android.os.Handler;
import android.os.Looper;

import com.lslutnfra.futuramagame.domain.executor.PostExecutionThread;

public class UIThread implements PostExecutionThread {

	private static class LazyHolder {
	    private static final UIThread INSTANCE = new UIThread();
	}

	public static UIThread getInstance() {
        return LazyHolder.INSTANCE;
    }
	
	private final Handler handler;
	
	private UIThread() {
		this.handler = new Handler(Looper.getMainLooper());
	}
	
	@Override
	public void post(Runnable runnable) {
		this.handler.post(runnable);	
	}

}
