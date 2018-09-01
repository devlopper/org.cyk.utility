package org.cyk.utility.assertion;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;

@Singleton
public class AssertionsProviderClassMapImpl extends AbstractSingleton implements AssertionsProviderClassMap, Serializable {
	private static final long serialVersionUID = 1L;

	private Map<Class<?>,Class<? extends AssertionsProvider>> map;
	
	@Override
	public AssertionsProviderClassMap set(Class<?> domainClass, Class<? extends AssertionsProvider> providerClass) {
		if(domainClass!=null) {
			if(map == null)
				map = new HashMap<>();
			map.put(domainClass, providerClass);	
		}
		return this;
	}

	@Override
	public <P extends AssertionsProvider> Class<P> get(Object domain) {
		Class<P> result = null;
		if(map!=null) {
			result = (Class<P>) map.get(domain instanceof Class<?> ? (Class<?>) domain : domain.getClass());
		}
		return result;
	}

}
