package com.lslutnfra.futuramagame.domain.executor;

public interface PostExecutionThread {
	
	void post(Runnable runnable);
	
}
