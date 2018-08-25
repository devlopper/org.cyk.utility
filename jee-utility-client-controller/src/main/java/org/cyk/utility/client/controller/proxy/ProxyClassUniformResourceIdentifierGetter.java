package org.cyk.utility.client.controller.proxy;

import java.net.URI;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface ProxyClassUniformResourceIdentifierGetter extends FunctionWithPropertiesAsInput<URI> {

	Class<?> getClazz();
	ProxyClassUniformResourceIdentifierGetter setClazz(Class<?> aClass);
	
	ProxyClassUniformResourceIdentifierGetter execute(Class<?> aClass);
	
	ProxyClassUniformResourceIdentifierGetter execute();
}
