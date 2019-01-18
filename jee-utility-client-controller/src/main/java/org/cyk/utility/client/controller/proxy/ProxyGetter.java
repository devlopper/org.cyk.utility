package org.cyk.utility.client.controller.proxy;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ProxyGetter extends FunctionWithPropertiesAsInput<Object> {
	
	ProxyClassUniformResourceIdentifierStringBuilder getClassUniformResourceIdentifierString();
	ProxyClassUniformResourceIdentifierStringBuilder getClassUniformResourceIdentifierString(Boolean injectIfNull);
	ProxyGetter setClassUniformResourceIdentifierString(ProxyClassUniformResourceIdentifierStringBuilder classUniformResourceIdentifierString);
	ProxyGetter setClassUniformResourceIdentifierStringRequest(Object request);
	Object getProxyClassUniformResourceIdentifierStringBuilderRequest();
	ProxyGetter setProxyClassUniformResourceIdentifierStringBuilderRequest(Object proxyClassUniformResourceIdentifierStringBuilderRequest);
	
	Class<?> getClazz();
	ProxyGetter setClazz(Class<?> aClass);
	
	
	
}
