package org.cyk.utility.assertion;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import org.cyk.utility.__kernel__.object.dynamic.AbstractSingleton;

@ApplicationScoped
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
	public Class<AssertionsProvider> get(Object domain) {
		Class<AssertionsProvider> result = null;
		if(map!=null) {
			result = (Class<AssertionsProvider>) map.get(domain instanceof Class<?> ? (Class<?>) domain : domain.getClass());
		}
		return result;
	}

	@Override
	public AssertionsProvider inject(Object domain) {
		AssertionsProvider assertionsProvider = null;
		Class<AssertionsProvider> aClass = get(domain);
		if(aClass != null)
			assertionsProvider = __inject__(aClass);
		return assertionsProvider;
	}
}
