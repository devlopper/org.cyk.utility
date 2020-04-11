package org.cyk.utility.__kernel__.cache;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface CacheManager {

	Object get(Class<?> klass,Object identifier);
	
	void set(Class<?> klass,Object identifier,Object object);
	
	void clear();
	
	/**/
	
	static CacheManager getInstance() {
		return Helper.getInstance(CacheManager.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}
