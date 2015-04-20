package org.cyk.utility.common;

public class RunnableAdapter<RUNNABLE extends Runnable> implements RunnableListener<RUNNABLE> {

	@Override
	public void started(RUNNABLE aRunnable, Long time) {
		
	}

	@Override
	public void stopped(RUNNABLE aRunnable, Long time) {
		
	}

	@Override
	public void throwable(RUNNABLE aRunnable, Throwable throwable) {
		
	}

}
