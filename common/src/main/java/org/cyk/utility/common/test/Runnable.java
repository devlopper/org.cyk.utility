package org.cyk.utility.common.test;

public abstract class Runnable implements java.lang.Runnable {

	@Override
	public void run() {
		try {
			__run__();
		} catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}
	
	protected abstract void __run__() throws java.lang.Throwable;
	
}