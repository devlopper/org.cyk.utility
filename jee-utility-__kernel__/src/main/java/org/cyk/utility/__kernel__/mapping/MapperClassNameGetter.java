package org.cyk.utility.__kernel__.mapping;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface MapperClassNameGetter {

	String get(String className);
	
	default String get(Class<?> klass) {
		if(klass == null)
			return null;
		return get(klass.getName());
	}
	
	/**/
	
	static MapperClassNameGetter getInstance() {
		MapperClassNameGetter instance = (MapperClassNameGetter) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(MapperClassNameGetter.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", MapperClassNameGetter.class);
		return instance;
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}
