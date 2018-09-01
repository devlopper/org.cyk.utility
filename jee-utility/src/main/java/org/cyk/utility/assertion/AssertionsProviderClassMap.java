package org.cyk.utility.assertion;

import org.cyk.utility.__kernel__.object.dynamic.Singleton;

public interface AssertionsProviderClassMap extends Singleton {

	AssertionsProviderClassMap set(Class<?> domainClass,Class<? extends AssertionsProvider> providerClass);
	<P extends AssertionsProvider> Class<P> get(Object domain);
}
