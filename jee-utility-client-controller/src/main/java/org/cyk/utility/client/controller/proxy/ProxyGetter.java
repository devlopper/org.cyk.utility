package org.cyk.utility.client.controller.proxy;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ProxyGetter extends FunctionWithPropertiesAsInput<Object> {

	String getUri();
	ProxyGetter setUri(String uri);
	
	Object getRequest();
	ProxyGetter setRequest(Object request);
	
	Object getScheme();
	ProxyGetter setScheme(Object scheme);
	
	Object getHost();
	ProxyGetter setHost(Object host);
	
	Object getPort();
	ProxyGetter setPort(Object port);
	
	String getContext();
	ProxyGetter setContext(String context);
	
	Class<?> getClazz();
	ProxyGetter setClazz(Class<?> aClass);
	
	ProxyGetter execute(String uri,Class<?> aClass);
	
	ProxyGetter execute();
}
