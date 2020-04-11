package org.cyk.utility.__kernel__.value;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;

public interface Checker {

	default Boolean isNull(Object value) {
		if(value == null)
			return Boolean.TRUE;
		return Boolean.FALSE; 
	}
	
	/**/
	
	static Checker getInstance() {
		Checker instance = (Checker) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(Checker.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", Checker.class);
		return instance;
	}
	
	Value INSTANCE = new Value();
	
}
