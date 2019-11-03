package org.cyk.utility.__kernel__.system.action;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public abstract class AbstractSystemActionXXXGetterImpl<T> implements SystemActionXXXGetter<T>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<T> get(Class<? extends SystemAction> systemActionClass,Object systemActionIdentifier,Class<?> entityClass) {
		if(systemActionClass == null || entityClass == null)
			return null;
		Map<String,Collection<T>> cache = __getCache__();
		String key = null;
		if(cache != null) {
			key = systemActionClass.getName()+"."+systemActionIdentifier+"."+entityClass.getName();
			if(cache.containsKey(key))
				return cache.get(key);
		}
		Collection<T> fields = __get__(systemActionClass,systemActionIdentifier,entityClass);
		if(cache != null) {
			cache.put(key, fields);
		}
		return fields;
	}
	
	protected abstract Collection<T> __get__(Class<? extends SystemAction> systemActionClass,Object systemActionIdentifier,Class<?> entityClass);
	
	protected abstract Map<String,Collection<T>> __getCache__();
	
}
