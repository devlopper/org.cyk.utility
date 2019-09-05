package org.cyk.utility.__kernel__;

public interface ObjectLifeCycleListener {

	void listen(Object object,Action action,When when);
	
	default void listenPostConstruct(Object object) {
		listen(object, Action.CONSTRUCT, When.AFTER);
	}
	
	default void listenExecuteBefore(Object object) {
		listen(object, Action.EXECUTE, When.BEFORE);
	}
	
	default void listenExecuteAfter(Object object) {
		listen(object, Action.EXECUTE, When.AFTER);
	}
	
	/**/
	
	public static enum Action {
		CONSTRUCT,EXECUTE
	}
	
	public static enum When {
		BEFORE,AFTER
	}
}
