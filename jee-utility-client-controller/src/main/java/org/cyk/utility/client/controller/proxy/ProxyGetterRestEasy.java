package org.cyk.utility.client.controller.proxy;

public interface ProxyGetterRestEasy extends ProxyGetter {

	ProxyGetterRestEasy setClazz(Class<?> aClass);
	
	ProxyGetterRestEasy execute(Class<?> aClass);
	
	ProxyGetterRestEasy execute();
}
