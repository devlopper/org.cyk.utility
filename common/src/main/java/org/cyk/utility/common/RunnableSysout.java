package org.cyk.utility.common;

public class RunnableSysout<RUNNABLE extends Runnable> implements RunnableListener<RUNNABLE> {

	@Override
	public void started(RUNNABLE aType, Long time) {
		System.out.println(aType.getClass().getName()+" started");
	}

	@Override
	public void stopped(RUNNABLE aType, Long time) {
		System.out.println(aType.getClass().getName()+" stopped");
	}

	@Override
	public void throwable(RUNNABLE aType, Throwable throwable) {
		throwable.printStackTrace();
	}

}
