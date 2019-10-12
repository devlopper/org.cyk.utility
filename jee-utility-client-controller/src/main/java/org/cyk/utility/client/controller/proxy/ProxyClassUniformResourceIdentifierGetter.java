package org.cyk.utility.client.controller.proxy;

import java.net.URI;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

@Deprecated
public interface ProxyClassUniformResourceIdentifierGetter extends FunctionWithPropertiesAsInput<URI> {

	Class<?> getClazz();
	ProxyClassUniformResourceIdentifierGetter setClazz(Class<?> aClass);
	
	ProxyClassUniformResourceIdentifierStringBuilder getStringBuilder();
	ProxyClassUniformResourceIdentifierStringBuilder getStringBuilder(Boolean injectIfNull);
	ProxyClassUniformResourceIdentifierGetter setStringBuilder(ProxyClassUniformResourceIdentifierStringBuilder stringBuilder);
	
	ProxyClassUniformResourceIdentifierGetter execute(Class<?> aClass);
	
	ProxyClassUniformResourceIdentifierGetter execute();
}
