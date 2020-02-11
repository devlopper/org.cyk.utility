package org.cyk.utility.__kernel__.cache;

import java.io.Serializable;

import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;

public abstract class AbstractCacheManagerImpl extends AbstractObject implements CacheManager,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Object get(Class<?> klass, Object identifier) {
		if(klass == null || identifier == null)
			return null;
		return __get__(klass, identifier);
	}
	
	@Override
	public void set(Class<?> klass, Object identifier, Object object) {
		if(klass == null || identifier == null || object == null)
			return;
		__set__(klass, identifier, object);
		LogHelper.logInfo(String.format("instance of %s with identifier %s has been cached.", klass,identifier), getClass());
	}
	
	protected abstract Object __get__(Class<?> klass, Object identifier);
	protected abstract void __set__(Class<?> klass, Object identifier, Object object);
}
