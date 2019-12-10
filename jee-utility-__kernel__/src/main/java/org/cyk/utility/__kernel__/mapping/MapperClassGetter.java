package org.cyk.utility.__kernel__.mapping;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface MapperClassGetter {

	Class<?> get(Class<?> klass);
	
	/**/
	
	static MapperClassGetter getInstance() {
		MapperClassGetter instance = (MapperClassGetter) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(MapperClassGetter.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", MapperClassGetter.class);
		return instance;
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}
