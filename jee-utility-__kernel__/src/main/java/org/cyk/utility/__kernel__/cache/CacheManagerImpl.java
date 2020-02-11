package org.cyk.utility.__kernel__.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CacheManagerImpl extends AbstractCacheManagerImpl implements Serializable {

	private final Map<Class<?>,Map<Object,Object>> map = new HashMap<Class<?>, Map<Object,Object>>();
	
	@Override
	protected Object __get__(Class<?> klass, Object identifier) {
		Map<Object,Object> __map__ = map.get(klass);
		if(__map__ == null)
			return null;
		return __map__.get(identifier);
	}

	@Override
	protected void __set__(Class<?> klass, Object identifier, Object object) {
		Map<Object,Object> __map__ = map.get(klass);
		if(__map__ == null)
			map.put(klass, __map__ = new HashMap<>());
		__map__.put(identifier, object);
	}

	@Override
	public void clear() {
		map.clear();
	}

}
