package org.cyk.utility.__kernel__.object;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface LifeCycleListener {

	default void listen(Object object,Action action,When when) {}
	
	default void listen(Object object,Action action) {
		if(object == null || action == null)
			return;
		listen(object, action, When.NOW);
	}
	
	default void listenConstructing(Object object) {
		if(object == null)
			return;
		listen(object, Action.CONSTRUCT, When.NOW);
	}
	
	/**/
	
	static LifeCycleListener getInstance() {
		return Helper.getInstance(LifeCycleListener.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	/**/
	
	public static enum Action {
		CONSTRUCT,EXECUTE
	}
	
	public static enum When {
		BEFORE,NOW,AFTER
	}
}
