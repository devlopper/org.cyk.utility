package org.cyk.utility.__kernel__.identifier.resource;

import java.net.URI;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface ProxyGetter {

	<T> T get(Class<T> klass,URI uri);
	
	default <T> T get(Class<T> klass,ProxyUniformResourceIdentifierGetter uniformResourceIdentifierGetter) {
		if(klass == null)
			return null;
		if(uniformResourceIdentifierGetter == null)
			uniformResourceIdentifierGetter = ProxyUniformResourceIdentifierGetter.getInstance();
		return get(klass,uniformResourceIdentifierGetter.get(klass));
	}
	
	default <T> T get(Class<T> klass) {
		if(klass == null)
			return null;
		return get(klass,ProxyUniformResourceIdentifierGetter.getInstance());
	}
	
	/**/
	
	static ProxyGetter getInstance() {
		return Helper.getInstance(ProxyGetter.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);	
}