package org.cyk.utility.__kernel__.method;

import java.lang.reflect.Method;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface MethodGetter {

	default Method get(String identifier) {
		return null;
	}
	
	/**/
	
	static MethodGetter getInstance() {
		return Helper.getInstance(MethodGetter.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
}
