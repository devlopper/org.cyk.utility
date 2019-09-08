package org.cyk.utility.__kernel__;

import java.io.Serializable;

public abstract class AbstractObjectLifeCycleListenerImpl implements ObjectLifeCycleListener,Serializable {
	private static final long serialVersionUID = 1L;

	private static ObjectLifeCycleListener INSTANCE;
	public static ObjectLifeCycleListener getInstance(Boolean isNew) {
		//if(INSTANCE == null || Boolean.TRUE.equals(isNew))
			INSTANCE = DependencyInjection.inject(ObjectLifeCycleListener.class);
		return INSTANCE;
	}
	
	public static ObjectLifeCycleListener getInstance() {
		return getInstance(null);
	}
	
}
