package org.cyk.utility.__kernel__.system.action;

import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
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
		return Helper.getInstance(SystemActionFieldsGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}
