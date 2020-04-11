package org.cyk.utility.__kernel__.system.action;

import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface SystemActionFieldsGetter {

	Collection<Field> get(Class<? extends SystemAction> systemActionClass,Object systemActionIdentifier,Class<?> entityClass);
	
	default Collection<Field> get(SystemAction systemAction) {
		if(systemAction == null)
			return null;
		return get(systemAction.getClass(),systemAction.getIdentifier(),systemAction.getEntityClass());
	}
	
	/**/
	
	static SystemActionFieldsGetter getInstance() {
		SystemActionFieldsGetter instance = (SystemActionFieldsGetter) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(SystemActionFieldsGetter.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", SystemActionFieldsGetter.class);
		return instance;
	}
	
	Value INSTANCE = new Value();
}
