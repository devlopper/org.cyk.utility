package org.cyk.utility.client.controller.proxy;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ProxyGetter extends FunctionWithPropertiesAsInput<Object> {

	String getUri();
	ProxyGetter setUri(String uri);
	
	Class<?> getClazz();
	ProxyGetter setClazz(Class<?> aClass);
	
	ProxyGetter execute(String uri,Class<?> aClass);
	
	ProxyGetter execute();
}
