package org.cyk.utility.client.controller.proxy;

import org.cyk.utility.identifier.resource.UniformResourceIdentifierStringBuilder;
import org.cyk.utility.string.StringFunction;

@Deprecated
public interface ProxyClassUniformResourceIdentifierStringBuilder extends StringFunction {

	UniformResourceIdentifierStringBuilder getUniformResourceIdentifierString();
	UniformResourceIdentifierStringBuilder getUniformResourceIdentifierString(Boolean injectIfNull);
	ProxyClassUniformResourceIdentifierStringBuilder setUniformResourceIdentifierString(UniformResourceIdentifierStringBuilder uniformResourceIdentifierString);
	
	Object getRequest();
	ProxyClassUniformResourceIdentifierStringBuilder setRequest(Object request);
	
	Class<?> getClazz();
	ProxyClassUniformResourceIdentifierStringBuilder setClazz(Class<?> aClass);
	
	ProxyClassUniformResourceIdentifierStringBuilder execute(Class<?> aClass);
	
	ProxyClassUniformResourceIdentifierStringBuilder execute();
}
