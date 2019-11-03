package org.cyk.utility.__kernel__.system.action;

import java.util.Collection;

public interface SystemActionXXXGetter<T> {

	Collection<T> get(Class<? extends SystemAction> systemActionClass,Object systemActionIdentifier,Class<?> entityClass);
	
	default Collection<T> get(SystemAction systemAction) {
		if(systemAction == null)
			return null;
		return get(systemAction.getClass(),systemAction.getIdentifier(),systemAction.getEntityClass());
	}
	
}
