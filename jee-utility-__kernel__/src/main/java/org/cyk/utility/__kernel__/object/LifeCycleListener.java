package org.cyk.utility.__kernel__.object;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
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
		LifeCycleListener instance = (LifeCycleListener) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(LifeCycleListener.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", LifeCycleListener.class);
		return instance;
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
