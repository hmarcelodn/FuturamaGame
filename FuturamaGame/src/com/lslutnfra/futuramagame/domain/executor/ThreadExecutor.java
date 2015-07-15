package com.lslutnfra.futuramagame.domain.executor;

public interface ThreadExecutor {
	
	void execute(final Runnable runnable);
	
}
