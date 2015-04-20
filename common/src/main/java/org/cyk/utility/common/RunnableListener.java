package org.cyk.utility.common;

public interface RunnableListener<TYPE extends Runnable> {

	void started(TYPE aType,Long time);
	
	void stopped(TYPE aType,Long time);

	void throwable(TYPE aType, Throwable throwable);	
	  
}
