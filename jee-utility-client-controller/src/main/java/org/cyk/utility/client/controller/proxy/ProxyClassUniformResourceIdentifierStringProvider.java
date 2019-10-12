package org.cyk.utility.client.controller.proxy;

import org.cyk.utility.string.StringFunction;
@Deprecated
public interface ProxyClassUniformResourceIdentifierStringProvider extends StringFunction {

	Object getRequest();
	ProxyClassUniformResourceIdentifierStringProvider setRequest(Object request);
	
	Class<?> getClazz();
	ProxyClassUniformResourceIdentifierStringProvider setClazz(Class<?> aClass);
	
}
